package api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private static final String PATH = "/api/v1/courier";
    public Response create(Courier courier) {
        return given()
                .spec(getSpec())
                .log().all()
                .body(courier)
                .when()
                .post(PATH)
                .then().extract().response();
    }

    public Response login(CourierCredentials credentials) {
        return given()
                .spec(getSpec())
                .log().all()
                .body(credentials)
                .when()
                .post(PATH + "/login")
                .then().extract().response();
    }


    public Response delete(int id) {
        return given()
                .spec(getSpec())
                .log().all() // логгирование запроса
                .when()
                .delete(PATH + "/" + id)
                .then().extract().response();
    }

}
