package petStore.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import petStore.REST.RESTfulRequests;
import petStore.petStoreModels.*;
import petStore.utility.ExcelUtility;

import java.util.HashMap;
import java.util.Map;

public class PetTest {

    public static String baseUrlPath = "https://petstore.swagger.io/v2/";
    public static String addNewPetPath = "pet";
    public static String getPetByIDPath = "pet/{petId}";
    public static String deletePetByIDPath = "pet/{petId}";

    public static String baseDataPath = "src/test/java/petStore/testData/";
    public static String petModelDataPath = baseDataPath + "PetModelData.xlsx";
    public static String tagModelDataPath = baseDataPath + "PetTagData.xlsx";
    public static String updatePetDataPath = baseDataPath + "UpdatePetData.xlsx";

    public static String petModelSheet = "PetModel";
    public static String petTagModelSheet = "PetTag";
    public static String updatePetSheet = "UpdatePet";

    public static int petModelCols = 6;
    public static int updatePetCols = 6;
    public static int petTagModelCols = 3;

    @Test
    void randomTest(){
        System.out.println("Entered Test");
        String[][] x = (String[][]) ExcelUtility.getTableArray(
                tagModelDataPath,
                petTagModelSheet,
                petTagModelCols
        );

        for(int i=0;i<x.length;i++){
            for(int j = 0; j<x[i].length; j++){
                System.out.println(x[i][j]);
            }
        }
    }

    /* PET STORE TESTS */

    @Test(dataProvider = "PetModelProvider")
    void testAddNewPet(String id, String categoryName, String categoryId, String name, String status) throws JsonProcessingException {
        // Create new requestBody
        PetModel petRequest = new PetModel();

        int petID = (int)Double.parseDouble(id);
        int categoryID = (int)Double.parseDouble(categoryId);

        petRequest.setId(petID);
        petRequest.setName(name);
        petRequest.setCategory(new Category());
        petRequest.getCategory().setId(categoryID);
        petRequest.getCategory().setName(categoryName);
        petRequest.setStatus(status);

        // Used to convert Java class to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(petRequest);

        Response response = RESTfulRequests.post(baseUrlPath+addNewPetPath, requestBody);

        response.prettyPrint();
        // Status code validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status is not the same!");

        // Convert response back to AddPetModel
        PetModel petResponse = response.as(PetModel.class);

        // Name validation
        System.out.println("Name Response: " + petResponse.getName() + " Request: " + petRequest.getName());
        Assert.assertEquals(petResponse.getName(), petRequest.getName(), "Name is not the same!");
        // ID validation
        System.out.println("ID Response: " + petResponse.getId() + " Request: " + petRequest.getId());
        Assert.assertEquals(petResponse.getId(), petRequest.getId(), "ID is not the same!");

        // Category ID validation
        System.out.println("Category ID Response: " + petResponse.getCategory().getId() + " Request: " + petRequest.getCategory().getId());
        Assert.assertEquals(petResponse.getCategory().getId(), petRequest.getCategory().getId(), "Category ID is not the same!");
        // Category Name validation
        System.out.println("Category Name Response: " + petResponse.getCategory().getName() + " Request: " + petRequest.getCategory().getName());
        Assert.assertEquals(petResponse.getCategory().getName(), petRequest.getCategory().getName(), "Category Name is not the same!");

        // Status validation
        System.out.println("Pet status Response: " + petResponse.getStatus() + " Request: " + petRequest.getStatus());
        Assert.assertEquals(petResponse.getStatus(), petRequest.getStatus(), "Pet status is not the same!");
    }

    @Test(dataProvider = "UpdatePetProvider")
    void testUpdatePet(String id, String categoryName, String categoryId, String name, String status){
        // Create new requestBody
        PetModel petRequest = new PetModel();

        int petID = (int)Double.parseDouble(id);
        int categoryID = (int)Double.parseDouble(categoryId);

        petRequest.setId(petID);
        petRequest.setName(name);
        petRequest.setCategory(new Category());
        petRequest.getCategory().setId(categoryID);
        petRequest.getCategory().setName(categoryName);
        petRequest.setStatus(status);

        // Used to convert Java class to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(petRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Response response = RESTfulRequests.put(baseUrlPath+addNewPetPath, requestBody);

        response.prettyPrint();
        // Status code validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status is not the same!");
    }

    @Test(dataProvider = "PetModelProvider")
    void testFindPetByID(String id, String categoryName, String categoryId, String name, String status){

        int petId = (int)Double.parseDouble(id);
        Map<String, String> params = new HashMap<>();
        params.put("petId", petId + "");

        Response response = RESTfulRequests.get(baseUrlPath+getPetByIDPath, params);

        response.prettyPrint();
        // Status code validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status is not the same!");
    }

    @Test(dataProvider = "PetModelProvider")
    void testDeletePet(String id, String categoryName, String categoryId, String name, String status){

        int petId = (int)Double.parseDouble(id);
        Map<String, String> params = new HashMap<>();
        params.put("petId", petId + "");

        Response response = RESTfulRequests.delete(baseUrlPath+deletePetByIDPath, params);

        response.prettyPrint();
        // Status code validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status is not the same!");
    }

    /* DATA PROVIDERS */

    @DataProvider(name = "PetModelProvider")
    public static Object[][] petModelProvider(){
        Object[][] newPetData = ExcelUtility.getTableArray(
                petModelDataPath,
                petModelSheet,
                petModelCols
        );
        return newPetData;
    }

    @DataProvider(name = "UpdatePetProvider")
    public static Object[][] updatePetProvider(){
        Object[][] newPetData = ExcelUtility.getTableArray(
                updatePetDataPath,
                updatePetSheet,
                updatePetCols
        );

        return newPetData;
    }
}
