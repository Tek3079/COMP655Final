package report;

import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.json.JsonObject;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ReportResourceTest {
    @Test
    void testGetAllOrdersEndpoint() {
        given()
                .when().get("/orders")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetOrderByIdEndpoint() {

        given()
                .when().get("/orders/10")
                .then()
                .statusCode(200);
    }

    @Test
    void testDeleteOrderByIdEndpoint() {
        given()
                .when().delete("/orders/1")
                .then()
                .statusCode(204);
    }

}