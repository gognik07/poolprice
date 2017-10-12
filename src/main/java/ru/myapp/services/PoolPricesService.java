package ru.myapp.services;

import ru.myapp.dto.Price;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для объединения цен
 */
public class PoolPricesService {

    public List<Price> getPoolPrices(List<Price> listExistingPrices, List<Price> listNewPrices) {
        List<Price> poolPrices = new ArrayList<Price>();

        for (Price price : listExistingPrices) {
            if (hasPriceInListNewPrices(price, listNewPrices)) {

            } else {
                poolPrices.add(price);
            }
        }

        return poolPrices;
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
