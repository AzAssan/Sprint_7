import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import api.CourierClient;
import api.CourierCredentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierBadLoginTest {
    private final String login;
    private final String password;
    private final CourierClient courierClient = new CourierClient();

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {"", "password123"},
                {"login123", ""},
                {"", ""},
        };
    }

    public CourierBadLoginTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Test
    @DisplayName("Проверяем что для авторизации нужно передать все обязательные поля")
    public void badLoginTest() {
        CourierCredentials credentials = new CourierCredentials(login, password);
        Response response = courierClient.login(credentials);
        assertEquals(SC_BAD_REQUEST,response.statusCode());
        assertEquals("Недостаточно данных для входа", response.path("message"));
    }
}
