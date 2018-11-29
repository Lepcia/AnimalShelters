package inzynierka.animalshelters.models;

import java.util.Collection;
import java.util.Date;

public class AnimalDetailsModel {
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
    private Collection<PhotoModel> Photos;
    private Date InShelterFrom;

    public AnimalDetailsModel()
    {}

    public AnimalDetailsModel(int Id, String Name, int Age, String AgeAccuracy, String AgeString, String Species, String Breed,
                              String Sex, String Size, String Description, Collection<PhotoModel> Photos, Date InShelterFrom)
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

    public Collection<PhotoModel> getPhotos()
    {
        return Photos;
    }

    public void setPhotos(Collection<PhotoModel> Photos)
    {
        this.Photos = Photos;
    }

    public Date getInShelterFrom() {return InShelterFrom;}

    public void setInShelterFrom(Date InShelterFrom) {this.InShelterFrom = InShelterFrom;}
}
