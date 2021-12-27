package petStore.petStoreModels;

import lombok.Data;

import java.util.List;

@Data
public class PetModel {
    private List<String> photoUrls;
    private String name;
    private Integer id;
    private Category category;
    private List<TagsItem> tags;
    private String status;
}