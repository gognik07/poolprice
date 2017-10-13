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
                updatePrice(price, listNewPriceInTimePrice);
            }
            if (price != null) {
                poolPrices.add(price);
            }

        }

        return poolPrices;
    }

    /**
     * Обновить существующую цену(удалить или изменить begin и end)
     *
     * @param price
     * @param listNewPriceInTimePrice
     */
    private void updatePrice(Price price, List<Price> listNewPriceInTimePrice) {
        Collections.sort(listNewPriceInTimePrice, new Comparator<Price>() {
            public int compare(Price o1, Price o2) {
                return o1.getBegin().compareTo(o2.getBegin());
            }
        });

        for (Price newPrice: listNewPriceInTimePrice) {

        }
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
                (beginNewPrice <= beginPrice && endNewPrice >= endPrice) ||
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
