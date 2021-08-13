package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class Pet {

    private String uri = "https://petstore.swagger.io/v2/pet"; //Endereço de entidade pet

    public String readJson (String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test (priority = 1)
    void incluirPet () throws IOException{
        String jsonBody = readJson("src/test/resources/pet1.json");

        given().contentType("application/json").log().all().body(jsonBody)
                .when().post(uri).then().log().all().statusCode(200)
                .body("name", is("Charlote"))
                .body("status", is("available"));
    }

    @Test (priority = 2)
    public void consultarPet () {
        String id = "/3212345612";
        given().log().all().when().get(uri+id).then().log()
                .all().statusCode(200)
                .body("name", is("Charlote"))
                .body("tags[0].name", is("vacinado"));
    }

    @Test (priority = 3)
    public void alterarPet () throws IOException {
        String jsonBody = readJson("src/test/resources/pet1Update.json");
        given().contentType("application/json").log().all().body(jsonBody)
                .when().put(uri).then().log().all()
                .statusCode(200).body("status", is("solded"));
    }

    @Test (priority = 4)
    public void deletarPet () {
        given().contentType("application/json").log().all()
                .when().delete(uri+"/3212345612").then()
                .log().all().statusCode(200);
    }
}
