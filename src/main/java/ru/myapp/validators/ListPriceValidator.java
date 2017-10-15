package ru.myapp.validators;

import ru.myapp.dto.Price;
import ru.myapp.exception.ValidateException;

import java.util.List;

/**
 * Класс дляпроверки списка цен
 */
public class ListPriceValidator {

    /**
     * Метод проверяет корректность списка цен
     *
     * @param listPrice Список цен
     * @throws ValidateException
     */
    public static void validateListPrice(List<Price> listPrice) throws ValidateException {
        for (int i = 0; i < listPrice.size() - 1; i++) {
            Price currentPrice = listPrice.get(i);
            if (!isCorrectTimeRangePrice(currentPrice)) {
                throw new ValidateException();
            }
            for (int j = i + 1; j < listPrice.size(); j++) {
                if (isIntersectValue(currentPrice, listPrice.get(j))) {
                    throw new ValidateException();
                }
            }
        }

        if (!isCorrectTimeRangePrice(listPrice.get(listPrice.size() - 1))) {
            throw new ValidateException();
        }
    }

    /**
     * Проверить временной диапазон цены(дата начала должна начинаться раньше даты окончания цены и они не должны быть равны)
     *
     * @param price проверяемая цена
     * @return
     */
    private static boolean isCorrectTimeRangePrice(Price price) {
        long beginTime = price.getBegin().getTime();
        long endTime = price.getEnd().getTime();
        return beginTime < endTime;
    }

    /**
     * Проверить пересекается ли время цен
     *
     * @param price1 цена 1
     * @param price2 цена 2
     * @return
     */
    private static boolean isIntersectValue(Price price1, Price price2) {
        long beginNewPrice = price2.getBegin().getTime();
        long endNewPrice = price2.getEnd().getTime();
        long beginPrice = price1.getBegin().getTime();
        long endPrice = price1.getEnd().getTime();
        if (!(price1.getProductCode().equals(price2.getProductCode()) && price1.getNumber() == price2.getNumber()
                && price1.getDepart() == price2.getDepart())) {
            return false;
        }

        return (beginNewPrice >= beginPrice && endNewPrice <= endPrice) ||
                (beginNewPrice <= beginPrice && endNewPrice >= endPrice) ||
                (beginNewPrice < beginPrice && endNewPrice > beginPrice) ||
                (beginNewPrice < endPrice && endNewPrice > endPrice);
    }

}
