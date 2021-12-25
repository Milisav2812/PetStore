package petStore.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import petStore.REST.RESTfulRequests;
import petStore.petStoreModels.AddPetModel;
import petStore.petStoreModels.Category;

public class PetTest {

    public static String baseUrlPath = "https://petstore.swagger.io/v2/";
    public static String addNewPetPath = "pet";

    @Test
    void testAddNewPet() throws JsonProcessingException {

        // Create new requestBody
        AddPetModel petRequest = new AddPetModel();
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
        // Status code validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status is not the same!");

        // Convert response back to AddPetModel
        AddPetModel petResponse = response.as(AddPetModel.class);

        // Name validation
        System.out.println("Name Response: " + petResponse.getName() + " Request: " + petRequest.getName());
        Assert.assertEquals(petResponse.getName(), petRequest.getName(), "Name is not the same!");
        // ID validation
        System.out.println("ID Response: " + petResponse.getId() + " Request: " + petRequest.getId());
        Assert.assertEquals(petResponse.getId(), petRequest.getId(), "ID is not the same!");
        // Status validation
        System.out.println("Pet status Response: " + petResponse.getStatus() + " Request: " + petRequest.getStatus());
        Assert.assertEquals(petResponse.getStatus(), petRequest.getStatus(), "Pet status is not the same!");
    }

}
