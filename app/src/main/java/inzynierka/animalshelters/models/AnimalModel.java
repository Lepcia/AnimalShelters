package inzynierka.animalshelters.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import inzynierka.animalshelters.helpers.DataHelper;
import inzynierka.animalshelters.helpers.DateFormatHelper;

public class AnimalModel {
    private int Id;
    private String Name;
    private int Age;
    private String AgeAccuracy;
    private String AgeString;
    private String Species;
    private String Breed;
    private String Sex;
    private String Size;
    private String Description;
    private int[] Photos;
    private Date InShelterFrom;
    private int AnimalShelter;
    private String Avatar;

    public AnimalModel()
    {}

    public AnimalModel(JSONObject object)
    {
        try {
            this.Id = object.getInt("id");
            this.Name = object.getString("name");
            this.Age = object.getInt("age");
            this.AgeAccuracy = object.getString("ageAccuracy");
            this.AgeString = object.getString("ageString");
            this.Species = object.getString("species");
            this.Breed = object.getString("breed");
            this.Sex = object.getString("sex");
            this.Size = object.getString("size");
            this.Description = object.getString("description");
            this.Photos = DataHelper.JSONObjectToIntArray(object.getJSONArray("photos"));
            String dateS = object.getString("inShelterFrom");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.InShelterFrom = dateFormat.parse(dateS);
            this.AnimalShelter = object.getInt("animalsToAnimalShelter");
            this.Avatar = object.getString("avatar");
        } catch (JSONException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public AnimalModel(int Id, String Name, int Age, String AgeAccuracy, String AgeString, String Species,
                       String Breed, String Sex, String Size, String Description, int[] Photos, Date InShelterFrom,
                       int AnimalShelter, String Avatar)
    {
        this.Id = Id;
        this.Name = Name;
        this.Age = Age;
        this.AgeAccuracy = AgeAccuracy;
        this.AgeString = AgeString;
        this.Species = Species;
        this.Breed = Breed;
        this.Sex = Sex;
        this.Size = Size;
        this.Description = Description;
        this.Photos = Photos;
        this.InShelterFrom = InShelterFrom;
        this.AnimalShelter = AnimalShelter;
        this.Avatar = Avatar;
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

    public int getAge()
    {
        return Age;
    }

    public void setAge(int Age)
    {
        this.Age = Age;
    }

    public String getAgeAccuracy()
    {
        return AgeAccuracy;
    }

    public void setAgeAccuracy(String AgeAccuracy)
    {
        this.AgeAccuracy = AgeAccuracy;
    }

    public String getAgeString()
    {
        return AgeString;
    }

    public void setAgeString(String AgeString)
    {
        this.AgeString = AgeString;
    }

    public String getSpecies()
    {
        return Species;
    }

    public void setSpecies(String Species)
    {
        this.Species = Species;
    }

    public String getBreed()
    {
        return Breed;
    }

    public void setBreed(String Breed)
    {
        this.Breed = Breed;
    }

    public String getSex()
    {
        return Sex;
    }

    public void setSex(String Sex)
    {
        this.Sex = Sex;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String Description)
    {
        this.Description = Description;
    }

    public String getSize()
    {
        return Size;
    }

    public void setSize(String Size)
    {
        this.Size = Size;
    }

    public int[] getPhotos()
    {
        return Photos;
    }

    public void setPhotos(int[] Photos)
    {
        this.Photos = Photos;
    }

    public Date getInShelterFrom() {return InShelterFrom;}

    public void setInShelterFrom(Date InShelterFrom) {this.InShelterFrom = InShelterFrom;}

    public int getAnimalShelter() {return AnimalShelter;}

    public void setAnimalShelter(int Animalshelter) {this.AnimalShelter = Animalshelter;}

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }
}
