import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import api.Courier;
import api.CourierClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierBadCreateTest {
    private final String login;
    private final String password;
    private final String firstName;
    private final CourierClient courierClient = new CourierClient();

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {"", "password123", "firstName123"},
                {"login123", "", "firstName123"},
                {"", "password123", ""},
                {"login123", "", ""},
                {"", "", ""},
        };
    }

    public CourierBadCreateTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Test
    @DisplayName("Проверяем что если одного из полей нет, запрос возвращает ошибку")
    public void badCreateTest() {
        Courier courier = new Courier(login, password, firstName);
        Response response = courierClient.create(courier);
        assertEquals(SC_BAD_REQUEST,response.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", response.path("message"));
    }
}
