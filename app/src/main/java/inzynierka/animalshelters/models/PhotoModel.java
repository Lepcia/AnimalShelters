package inzynierka.animalshelters.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class PhotoModel {
    private int Id;
    private String Content;

    public PhotoModel()
    {}

    public PhotoModel(JSONObject object)
    {
        try {
            this.Id = object.getInt("id");
            this.Content = object.getString("content");
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public PhotoModel(int Id, String Content)
    {
        this.Id = Id;
        this.Content = Content;
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
}
