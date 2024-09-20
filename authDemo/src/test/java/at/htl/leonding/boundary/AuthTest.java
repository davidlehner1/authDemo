package at.htl.leonding.boundary;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthTest {

    @Test
    public void given_correctPassword_when_exampleResourceCalled_then_200() {
        // Arrange
        Header header = new Header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
        //Act
        Response response = RestAssured
                .given()
                .header(header)
                .when()
                .get("/hello");    // Perform the GET request

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void given_wrongPassword_when_exampleResourceCalled_then_403() {
        // Arrange
        Header header = new Header("Authorization", "Basic YWRtaW46cGFzc3dv");

        // Act
        Response response = RestAssured
                .given()
                .header(header)
                .when()
                .get("/hello");    // Perform the GET request

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(403);
    }

    @Test
    public void given_noPassword_when_exampleResourceCalled_then_401() {
        // Act
        Response response = RestAssured
                .when()
                .get("/hello");    // Perform the GET request

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(401);
    }
}
