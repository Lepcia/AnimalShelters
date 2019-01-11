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
    private String RoleName;
    private RoleModel Role;
    private String Password;
    private String ShelterName;

    public UserModel()
    {}

    public UserModel(JSONObject object)
    {
        try {
            this.Id = object.getInt("id");
            if(object.has("firstName"))
                this.FirstName = object.getString("firstName");
            if(object.has("lastName"))
                this.LastName = object.getString("lastName");
            if(object.has("dateOfBirth"))
                this.DateOfBirth = DateFormatHelper.dateFromString(object.getString("dateOfBirth"), DateFormatHelper.FORMAT_DATE);
            if(object.has("email"))
                this.Email = object.getString("email");
            if(object.has("avatar"))
                this.Avatar = object.getString("avatar");
            if(object.has("roleName"))
                this.RoleName = object.getString("roleName");
            if(object.has("role"))
                this.Role = new RoleModel(object.getJSONObject("role"));
            if(object.has("favoriteAnimals"))
                this.FavoriteAnimals = DataHelper.JSONObjectToIntArray(object.getJSONArray("favoriteAnimals"));
            if(object.has("password"))
                this.Password = object.getString("password");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public UserModel(int Id, String FirstName, String LastName, Date DateOfBirth, String Email,
                     String Avatar, int[] FavoriteAnimals, String RoleName, RoleModel Role)
    {
        this.Id = Id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.DateOfBirth = DateOfBirth;
        this.Email = Email;
        this.Avatar = Avatar;
        this.FavoriteAnimals = FavoriteAnimals;
        this.RoleName = RoleName;
        this.Role = Role;
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

    public RoleModel getRole() {
        return Role;
    }

    public void setRole(RoleModel role) {
        Role = role;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getShelterName() {
        return ShelterName;
    }

    public void setShelterName(String shelterName) {
        ShelterName = shelterName;
    }
    public void setPassword(String password){this.Password = password;}
}
