package inzynierka.animalshelters.models;

import java.util.Collection;
import java.util.Date;

public class UserDetailsModel {
    private int Id;
    private String FirstName;
    private String LastName;
    private Date DateOfBirth;
    private String Email;
    private String Avatar;
    private Collection<AnimalModel> FavoriteAnimals;

    public UserDetailsModel()
    {}

    public UserDetailsModel(int Id, String FirstName, String LastName, Date DateOfBirth, String Email, String Avatar, Collection<AnimalModel> FavoriteAnimals)
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

    public Collection<AnimalModel> getFavoriteAnimals()
    {
        return FavoriteAnimals;
    }

    public void setFavoriteAnimals(Collection<AnimalModel> FavoriveAnimals)
    {
        this.FavoriteAnimals = FavoriveAnimals;
    }
}
