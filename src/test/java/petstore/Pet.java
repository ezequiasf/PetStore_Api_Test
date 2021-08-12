package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class Pet {

    private String uri = "https://petstore.swagger.io/v2/pet"; //Endereço de entidade pet

    public String readJson (String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    void incluirPet () throws IOException{
        String jsonBody = readJson("src/test/resources/pet1.json");

        given().contentType("application/json").log().all().body(jsonBody)
                .when().post(uri).then().log().all().statusCode(200);
    }
}
