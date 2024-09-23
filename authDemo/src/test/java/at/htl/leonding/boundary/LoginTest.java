package at.htl.leonding.boundary;

import at.htl.leonding.auth.Credentials;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class LoginTest {

    @Test
    public void given_validCredentials_when_login_then_returned200() {
        // Arrange
        Credentials credentials = new Credentials("admin", "password");
        //Act
        Response response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(credentials)
                .when()
                .post("/login");
        // Perform the GET request

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void given_invalidCredentials_when_login_then_returned401() {
        // Arrange
        Credentials credentials = new Credentials("admin", "wrong");
        //Act
        Response response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(credentials)
                .when()
                .post("/login");
        // Perform the GET request

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(401);
    }

    @Test
    public void given_noCredentials_when_login_then_returned401() {
        //Act
        Response response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/login");
        // Perform the GET request

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(401);
    }
}
