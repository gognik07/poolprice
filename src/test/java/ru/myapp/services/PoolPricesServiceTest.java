package ru.myapp.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.myapp.dto.Price;
import ru.myapp.exception.ValidateException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Тесты для сервиса объединения цен
 */
public class PoolPricesServiceTest {

    @InjectMocks
    private PoolPricesService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Тестирование метода получения объединенного списка
     *
     * @throws ValidateException
     */
    @Test
    public void testGetPoolPrices() throws ValidateException {
        //given
        List<Price> existingPrices = createListExistingPrices();
        List<Price> newPrices = createListNewPrices();

        //when
        List<Price> poolPrices = service.getPoolPrices(existingPrices, newPrices);

        //then
        assertNotNull(poolPrices);
        assertFalse(poolPrices.isEmpty());
        assertEquals(poolPrices.size(), 11);
    }

    /**
     * Тестирование метода получения объединенного списка при некорректном списке цен
     *
     * @throws ValidateException
     */
    @Test(expected = ValidateException.class)
    public void testGetPoolPricesNotValidateListPrices() throws ValidateException {
        //given
        List<Price> existingPrices = new ArrayList<Price>();
        existingPrices.add(new Price(1L, "111111", 1, 1, new Date("12/10/2017"), new Date("12/5/2017"), 111111));
        List<Price> newPrices = createListNewPrices();

        //when
        List<Price> poolPrices = service.getPoolPrices(existingPrices, newPrices);

        //then
        assertNull(poolPrices);
    }

    /**
     * Создать корректный список существующих цен
     *
     * @return корректный список существующих цен
     */
    private List<Price> createListExistingPrices() {
        List<Price> listExistingPrices = new ArrayList<Price>();
        listExistingPrices.add(new Price(1L, "111111", 1, 1, new Date("10/15/2017"), new Date("10/25/2017"), 111111));
        listExistingPrices.add(new Price(2L, "222222", 2, 2, new Date("11/15/2017"), new Date("11/25/2017"), 222222));
        listExistingPrices.add(new Price(3L, "333333", 3, 3, new Date("10/10/2017"), new Date("10/15/2017"), 333333));
        listExistingPrices.add(new Price(4L, "333333", 3, 3, new Date("11/10/2017"), new Date("11/15/2017"), 333333));
        listExistingPrices.add(new Price(5L, "555555", 5, 5, new Date("12/10/2017"), new Date("12/15/2017"), 555555));
        listExistingPrices.add(new Price(6L, "555555", 5, 5, new Date("12/15/2017"), new Date("12/20/2017"), 565656));
        listExistingPrices.add(new Price(7L, "777777", 7, 7, new Date("12/01/2017"), new Date("12/30/2017"), 777777));
        listExistingPrices.add(new Price(9L, "999999", 9, 9, new Date("12/05/2017"), new Date("12/15/2017"), 999999));
        return listExistingPrices;
    }

    /**
     * Создать корректный список новых цен
     *
     * @return корректный список новых цен
     */
    private List<Price> createListNewPrices() {
        List<Price> listNewPrices = new ArrayList<Price>();
        listNewPrices.add(new Price(8L, "888888", 8, 8, new Date("10/15/2017"), new Date("10/25/2017"), 888888));
        listNewPrices.add(new Price(2L, "222222", 2, 2, new Date("12/15/2017"), new Date("12/25/2017"), 10000));
        listNewPrices.add(new Price(3L, "333333", 3, 3, new Date("10/01/2017"), new Date("10/12/2017"), 343434));
        listNewPrices.add(new Price(4L, "333333", 3, 3, new Date("11/12/2017"), new Date("11/17/2017"), 343434));
        listNewPrices.add(new Price(5L, "555555", 5, 5, new Date("12/12/2017"), new Date("12/17/2017"), 55665566));
        listNewPrices.add(new Price(7L, "777777", 7, 7, new Date("11/01/2017"), new Date("01/30/2018"), 789789));
        listNewPrices.add(new Price(9L, "999999", 9, 9, new Date("12/01/2017"), new Date("12/06/2017"), 999999));
        listNewPrices.add(new Price(9L, "999999", 9, 9, new Date("12/08/2017"), new Date("12/10/2017"), 999999));
        listNewPrices.add(new Price(9L, "999999", 9, 9, new Date("12/13/2017"), new Date("12/20/2017"), 999999));
        return listNewPrices;
    }
}
