package ru.myapp.services;

import ru.myapp.dto.Price;

import java.util.*;

/**
 * Сервис для объединения цен
 */
public class PoolPricesService {

    public List<Price> getPoolPrices(List<Price> listExistingPrices, List<Price> listNewPrices) {
        List<Price> poolPrices = new ArrayList<Price>();

        for (Price price : listExistingPrices) {
            List<Price> listNewPriceInTimePrice = getListNewPriceInTimePrice(price, listNewPrices);
            if (listNewPriceInTimePrice != null) {
                List<Price> updatedPrices = updatePrice(price, listNewPriceInTimePrice);
                if (!updatedPrices.isEmpty()) {
                    poolPrices.addAll(updatedPrices);
                }
            } else {
                poolPrices.add(price);
            }

        }
        poolPrices.addAll(listNewPrices);
        return poolPrices;
    }


    /**
     * Обновить существующую цену(удалить или изменить begin и end)
     *  @param price
     * @param listNewPriceInTimePrice
     */
    private List<Price> updatePrice(Price price, List<Price> listNewPriceInTimePrice) {
        List<Price> updatedPrices = new ArrayList<Price>();
        updatedPrices.add(price);
        Collections.sort(listNewPriceInTimePrice, new Comparator<Price>() {
            public int compare(Price o1, Price o2) {
                return o1.getBegin().compareTo(o2.getBegin());
            }
        });

        for (Price newPrice : listNewPriceInTimePrice) {
            Price actualExistingPrice = getActualExistingPrice(updatedPrices);
            if (isNewPriceCoversFullTimeExistingPrice(newPrice, actualExistingPrice)) {
                updatedPrices.remove(actualExistingPrice);
                break;
            }

            if(isNewPriceInBeginExistingPrice(newPrice, actualExistingPrice)) {
                actualExistingPrice.setBegin(newPrice.getEnd());
                continue;
            }

            if (isNewPriceInEndExistingPrice(newPrice, actualExistingPrice)) {
                actualExistingPrice.setEnd(newPrice.getBegin());
                break;
            }

            if (isNewPriceWithinExistingPrice(newPrice, actualExistingPrice)) {
                Date endExistingPrice = actualExistingPrice.getEnd();
                actualExistingPrice.setEnd(newPrice.getBegin());
                Price addingPrice = new Price(actualExistingPrice);
                addingPrice.setBegin(newPrice.getEnd());
                addingPrice.setEnd(endExistingPrice);
                updatedPrices.add(addingPrice);
                continue;
            }
        }
        return updatedPrices;
    }

    /**
     * Метод проверяет входит ли диапазон новой цены в диапазон существующей
     *
     * @param newPrice
     * @param existingPrice
     * @return
     */
    private boolean isNewPriceWithinExistingPrice(Price newPrice, Price existingPrice) {
        long newPriceTimeBegin = newPrice.getBegin().getTime();
        long newPriceTimeEnd = newPrice.getEnd().getTime();
        long existingPriceTimeEnd = existingPrice.getEnd().getTime();
        long existingPriceTimeBegin = existingPrice.getBegin().getTime();
        return newPriceTimeBegin > existingPriceTimeBegin && newPriceTimeEnd < existingPriceTimeEnd;
    }

    /**
     * Проверяет временной диапазон новой цены полностью покрывает диапазон существующей
     *
     * @param newPrice
     * @param existingPrice
     * @return
     */
    private boolean isNewPriceCoversFullTimeExistingPrice(Price newPrice, Price existingPrice) {
        long newPriceTimeBegin = newPrice.getBegin().getTime();
        long newPriceTimeEnd = newPrice.getEnd().getTime();
        long existingPriceTimeEnd = existingPrice.getEnd().getTime();
        long existingPriceTimeBegin = existingPrice.getBegin().getTime();
        return newPriceTimeBegin <= existingPriceTimeBegin && newPriceTimeEnd >= existingPriceTimeEnd;
    }

    /**
     * Проверяет новая цена входит в конец существующей цены
     *
     * @param newPrice
     * @param existingPrice
     * @return
     */
    private boolean isNewPriceInEndExistingPrice(Price newPrice, Price existingPrice) {
        long newPriceTimeBegin = newPrice.getBegin().getTime();
        long newPriceTimeEnd = newPrice.getEnd().getTime();
        long existingPriceTimeEnd = existingPrice.getEnd().getTime();
        return newPriceTimeBegin <= existingPriceTimeEnd && newPriceTimeEnd >= existingPriceTimeEnd;
    }

    /**
     * Проверяет новая цена входит в начало существующей цены
     *
     * @param newPrice
     * @param existingPrice
     * @return
     */
    private boolean isNewPriceInBeginExistingPrice(Price newPrice, Price existingPrice) {
        long newPriceTimeBegin = newPrice.getBegin().getTime();
        long newPriceTimeEnd = newPrice.getEnd().getTime();
        long existingPriceTimeBegin = existingPrice.getBegin().getTime();
        return newPriceTimeBegin <= existingPriceTimeBegin && newPriceTimeEnd >= existingPriceTimeBegin;
    }

    private Price getActualExistingPrice(List<Price> listExistingPrice) {
        return listExistingPrice.get(listExistingPrice.size() - 1);
    }

    /**
     * Получить список новых цен пересекающихся по времени с существующей ценой
     *
     * @param price         существующая цена
     * @param listNewPrices список новых цен
     * @return список новых цен пересекающихся по времени с существующей ценой
     */
    private List<Price> getListNewPriceInTimePrice(Price price, List<Price> listNewPrices) {
        List<Price> listNewPriceInTimePrice = new ArrayList<Price>();
        for (Price newPrice : listNewPrices) {
            if (newPrice.getProductCode().equals(price.getProductCode()) &&
                    newPrice.getNumber() == price.getNumber() && newPrice.getDepart() == price.getDepart()) {
                if (isNewPriceInTimePrice(price, newPrice)) {
                    listNewPriceInTimePrice.add(newPrice);
                }
            }
        }
        return listNewPriceInTimePrice;
    }

    /**
     * Проверить пересекается ли время существующей и новой цен
     *
     * @param price    существующая цена
     * @param newPrice новая цена
     * @return
     */
    private boolean isNewPriceInTimePrice(Price price, Price newPrice) {
        long beginNewPrice = newPrice.getBegin().getTime();
        long endNewPrice = newPrice.getEnd().getTime();
        long beginPrice = price.getBegin().getTime();
        long endPrice = price.getEnd().getTime();

        return (beginNewPrice >= beginPrice && endNewPrice <= endPrice) ||
                (beginNewPrice <= beginPrice && endNewPrice >= endPrice) ||
                (beginNewPrice <= beginPrice && endNewPrice >= beginPrice) ||
                (beginNewPrice <= endPrice && endNewPrice >= endPrice);
    }

    /**
     * Проверить наличие цены в списке новых цен
     *
     * @param price         Проверяемая цена
     * @param listNewPrices Список новых цен
     * @return
     */
    private boolean hasPriceInListNewPrices(Price price, List<Price> listNewPrices) {
        for (Price newPrice : listNewPrices) {
            if (newPrice.getProductCode().equals(price.getProductCode()) &&
                    newPrice.getNumber() == price.getNumber() && newPrice.getDepart() == price.getDepart()) {
                return true;
            }
        }

        return false;
    }

}
