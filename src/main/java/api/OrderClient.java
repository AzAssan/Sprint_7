package api;
import io.restassured.response.Response;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

public class OrderClient extends Client{

    private static final String PATH = "/api/v1/orders";

    public OrderClient() {
    }

    public Response create (Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then().extract().response();
    }

    public Response getList(int limit) {
        return given()
                .spec(getSpec())
                .log().all()
                .param("limit", limit)
                .when()
                .get(PATH)
                .then().extract().response();
    }


    public Response cancel(int trackId) {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("track", trackId);

        return given()
                .spec(getSpec())
                .body(requestBody)
                .log().all()
                .when()
                .put(PATH + "/cancel")
                .then().extract().response();
    }


}
