package petStore.test;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import petStore.addNewPet.AddPetRequest;

public class PetTest {

    @Test
    void testAddNewPet(){

        AddPetRequest petRequest = new AddPetRequest();
        petRequest.setId(123987);
        petRequest.setName("Heroj");
        petRequest.setStatus("available");

        RestAssured.post("https://petstore.swagger.io/v2/pet", petRequest);
    }

}
