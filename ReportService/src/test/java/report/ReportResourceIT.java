package report;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
class ReportResourceIT extends ReportResourceTest {
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
