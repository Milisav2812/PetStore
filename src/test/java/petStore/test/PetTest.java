package petStore.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import petStore.REST.RESTfulRequests;
import petStore.addNewPet.AddPetRequest;
import petStore.addNewPet.Category;

public class PetTest {

    public static String baseUrlPath = "https://petstore.swagger.io/v2/";
    public static String addNewPetPath = "pet";

    @Test
    void testAddNewPet() throws JsonProcessingException {

        // Create new requestBody
        AddPetRequest petRequest = new AddPetRequest();
        petRequest.setId(123987);
        petRequest.setName("Heroj");
        petRequest.setCategory(new Category());
        petRequest.getCategory().setId(1);
        petRequest.getCategory().setName("Avlijaner");
        petRequest.setStatus("available");

        // Used to convert Java class to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(petRequest);

        Response response = RESTfulRequests.post(baseUrlPath+addNewPetPath, requestBody);

        response.prettyPrint();
    }

}
