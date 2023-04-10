import api.Courier;
import api.CourierClient;
import api.CourierCredentials;
import api.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CourierCreateAndLoginTest {
    private static CourierClient courierClient;
    private static Courier courier;
    private static int id;

    @BeforeClass
    public static void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
    }

    @Test
    @DisplayName("Проверяем что курьера можно создать")
    public void test1CourierCanBeCreated() {
        Response response = courierClient.create(courier);
        int statusCode = response.statusCode();
        assertEquals(SC_CREATED, statusCode);
        assertTrue(response.path("ok"));
    }

    @Test
    @DisplayName("Проверяем что курьер может авторизоваться")
    public void test2CanLogin() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        Response response = courierClient.login(credentials);
        int statusCode = response.statusCode();
        assertEquals(SC_OK, statusCode);

        id = response.path("id");
        assertNotEquals(0, id);
    }

    @Test
    @DisplayName("Проверяем что нельзя создать двух одинаковых курьеров")
    public void test3CannotCreateDuplicateCouriers() {
        // Создаем второго курьера и проверяем, что запрос завершается с ошибкой
        Response response = courierClient.create(courier);
        assertEquals(SC_CONFLICT, response.statusCode());
        assertEquals(SC_CONFLICT, (int) response.path("code"));
        assertEquals("Этот логин уже используется. Попробуйте другой.", response.path("message"));
    }

    @Test
    @DisplayName("Проверяем что система вернёт ошибку, если неправильно указать пароль")
    public void test4CannotLoginWithWrongPassword() {
        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), "test");
        Response response = courierClient.login(credentials);
        assertEquals(SC_NOT_FOUND, response.statusCode());
        assertEquals( "Учетная запись не найдена", response.path("message"));
    }

    @Test
    @DisplayName("Проверяем что система вернёт ошибку, если неправильно указать логин")
    public void test5CannotLoginWithWrongLogin() {
        CourierCredentials credentials = new CourierCredentials("test", courier.getPassword());
        Response response = courierClient.login(credentials);
        assertEquals(SC_NOT_FOUND, response.statusCode());
        assertEquals( "Учетная запись не найдена", response.path("message"));
    }

    @AfterClass
    public static void cleanUp() {
        courierClient.delete(id);
    }
}
