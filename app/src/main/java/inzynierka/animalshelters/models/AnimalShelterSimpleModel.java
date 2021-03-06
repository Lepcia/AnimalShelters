package inzynierka.animalshelters.models;

import org.json.JSONException;
import org.json.JSONObject;

public class AnimalShelterSimpleModel {
    private int Id;
    private String Name;

    public AnimalShelterSimpleModel()
    {}

    public AnimalShelterSimpleModel(JSONObject object)
    {
        try{
            this.Id = object.getInt("id");
            this.Name = object.getString("name");
        }  catch (JSONException e){
            e.printStackTrace();
        }
    }

    public AnimalShelterSimpleModel(int Id, String Name)
    {
        this.Id = Id;
        this.Name = Name;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int Id)
    {
        this.Id = Id;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }
}
