import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import api.OrderClient;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class OrderListTest {
    private final int limit = 10;
    private final OrderClient  client = new OrderClient();

    @Test
    @DisplayName("Тело ответа возвращается список заказов")
    public void getListTest() {
        Response response = client.getList(limit);
        assertEquals(SC_OK, response.statusCode());
        List<Object> orders = response.jsonPath().getList("orders");
        assertNotNull(orders);
        assertTrue(orders.size() > 0);
    }
}
