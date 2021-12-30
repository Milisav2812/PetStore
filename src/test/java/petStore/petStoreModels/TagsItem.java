package petStore.petStoreModels;

import lombok.Data;

@Data
public class TagsItem{
    private String name;
    private Integer id;

    TagsItem(String name, Integer id){
        this.name = name;
        this.id = id;
    }
}
