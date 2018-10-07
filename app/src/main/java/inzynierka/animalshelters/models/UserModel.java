package inzynierka.animalshelters.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import inzynierka.animalshelters.helpers.DataHelper;
import inzynierka.animalshelters.helpers.DateFormatHelper;

public class UserModel {
    private int Id;
    private String FirstName;
    private String LastName;
    private Date DateOfBirth;
    private String Email;
    private String Avatar;
    private int[] FavoriteAnimals;

    public UserModel(JSONObject object)
    {
        try {
            this.Id = object.getInt("id");
            this.FirstName = object.getString("firstName");
            this.LastName = object.getString("lastName");
            this.DateOfBirth = DateFormatHelper.dateFromString(object.getString("dateOfBirth"), DateFormatHelper.FORMAT_DATE);
            this.Email = object.getString("email");
            this.Avatar = object.getString("avatar");
            this.FavoriteAnimals = DataHelper.JSONObjectToIntArray(object.getJSONArray("favoriteAnimals"));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public UserModel(int Id, String FirstName, String LastName, Date DateOfBirth, String Email, String Avatar, int[] FavoriteAnimals)
    {
        this.Id = Id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.DateOfBirth = DateOfBirth;
        this.Email = Email;
        this.Avatar = Avatar;
        this.FavoriteAnimals = FavoriteAnimals;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int Id)
    {
        this.Id = Id;
    }

    public String getFirstName()
    {
        return FirstName;
    }

    public void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getLastName()
    {
        return LastName;
    }

    public void setLastName(String LastName)
    {
        this.LastName = LastName;
    }

    public Date getDateOfBirth()
    {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date DateOfBirth)
    {
        this.DateOfBirth = DateOfBirth;
    }

    public String getEmail()
    {
        return Email;
    }

    public void setEmail(String Email)
    {
        this.Email = Email;
    }

    public String getAvatar()
    {
        return Avatar;
    }

    public void setAvatar(String Avatar)
    {
        this.Avatar = Avatar;
    }

    public int[] getFavoriteAnimals()
    {
        return FavoriteAnimals;
    }

    public void setFavoriteAnimals(int[] FavoriveAnimals)
    {
        this.FavoriteAnimals = FavoriveAnimals;
    }
}
