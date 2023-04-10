import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import constants.Color;
import api.Order;
import api.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private OrderClient orderClient;
    private int trackId;
    @Parameterized.Parameter
    public List<Color> colors;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Collections.singletonList(Color.BLACK)},
                {Collections.singletonList(Color.GREY)},
                {Arrays.asList(Color.BLACK, Color.GREY)},
                {Collections.emptyList()}
        });
    }

    @Test
    @DisplayName("Создание заказа с разными вариантами цветами")
    public void createOrderWithColor() {
        Order order = new Order();
        order.setColors(colors);

        Response response = orderClient.create(order);
        int statusCode = response.statusCode();

        assertEquals(SC_CREATED, statusCode);
        assertNotNull(response.path("track"));
        trackId = response.path("track");
    }

    @After
    public void cleanUp() {
        orderClient.cancel(trackId);
    }
}

