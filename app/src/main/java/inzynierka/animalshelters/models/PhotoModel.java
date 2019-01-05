package inzynierka.animalshelters.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class PhotoModel {
    private int Id;
    private int AnimalId;
    private String Content;
    private String Title;

    public PhotoModel()
    {}

    public PhotoModel(JSONObject object)
    {
        try {
            this.Id = object.getInt("id");
            this.Content = object.getString("content");
            this.Title = object.getString("title");
            this.AnimalId = object.getInt("animalId");
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public PhotoModel(int Id, String Content, String Title, int AnimalId)
    {
        this.Id = Id;
        this.Content = Content;
        this.Title = Title;
        this.AnimalId = AnimalId;
    }

    public int getId()
    {
        return this.Id;
    }

    public void setId(int Id)
    {
        this.Id = Id;
    }

    public String getContent()
    {
        return Content;
    }

    public void setContent(String Content)
    {
        this.Content = Content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getAnimalId() {
        return AnimalId;
    }

    public void setAnimalId(int animalId) {
        AnimalId = animalId;
    }
}
