import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest
{
    @BeforeAll
    static void setup()
    {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnOKWithValidToken()
    {
        // 1. Arrange
        // 2. act
        // 3. assert

        String Payload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;

        Response response = given()
                .contentType("application/json")
                .body(Payload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token",notNullValue())
                .extract()
                .response();

        String token = response.getBody().jsonPath().getString("token");
        System.out.println("Generated token : " + token);

    }

    @Test
    public void shouldReturnUnauthorizedOnInValidToken()
    {
        // 1. Arrange
        // 2. act
        // 3. assert

        String Payload = """
                    {
                        "email": "invalid_user@test.com",
                        "password": "wrongpassword"
                    }
                """;

        given()
                .contentType("application/json")
                .body(Payload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);


    }

}
