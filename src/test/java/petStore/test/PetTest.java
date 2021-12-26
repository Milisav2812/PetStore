package petStore.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import petStore.REST.RESTfulRequests;
import petStore.petStoreModels.AddPetModel;
import petStore.petStoreModels.Category;
import petStore.utility.ExcelUtility;

public class PetTest {

    public static String baseUrlPath = "https://petstore.swagger.io/v2/";
    public static String addNewPetPath = "pet";

    public static String addNewPetDataPath = "src/test/java/petStore/testData/addNewPetData.xlsx";

    @Test
    void randomTest(String id, String name, String status){

        double ID = Double.parseDouble(id);
        int x = (int) ID;
        System.out.println( x + " " + name + " " + status);

/*        String[][] x = (String[][]) ExcelUtility.getTableArray("src/test/java/petStore/testData/addNewPetData.xlsx", "Sheet1");;

        for(int i=0;i<x.length;i++){
            for(int j = 0; j<x[i].length; j++){
                System.out.println(x[i][j]);
            }
        }*/

    }

    @Test(dataProvider = "AddNewPet")
    void testAddNewPet(String id, String name, String status) throws JsonProcessingException {

        // Create new requestBody
        AddPetModel petRequest = new AddPetModel();

        int petID = (int)Double.parseDouble(id);
        petRequest.setId(petID);
        petRequest.setName(name);
        petRequest.setStatus(status);

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

    @DataProvider(name = "AddNewPet")
    public static Object[][] addNewPetProvider(){
        Object[][] newPetData = ExcelUtility.getTableArray(addNewPetDataPath, "Sheet1");;

        return newPetData;
    }

}
