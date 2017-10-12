package ru.myapp.services;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.myapp.dto.Price;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikita on 12.10.2017.
 */
public class PoolPricesServiceTest {

    @InjectMocks
    private PoolPricesService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPoolPrices() {
        List<Price> list1 = new ArrayList<Price>();
        list1.add(new Price(1L, "123", 1, 1, new Date(), new Date(), 500));
        List<Price> list2 = new ArrayList<Price>();
        list2.add(new Price(1L, "456", 2, 2, new Date(), new Date(), 1500));
        service.getPoolPrices(list1, list2);
    }
}
