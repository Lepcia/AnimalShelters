package inzynierka.animalshelters.models;

public class AnimalSearchModel {
    private String Name;
    private int AgeFrom;
    private int AgeTo;
    private String AgeAccuracy;
    private String Species;
    private String Breed;
    private String Sex;
    private String Size;

    public AnimalSearchModel()
    {}

    public String getName()
    {
        return Name;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public int getAgeFrom()
    {
        return AgeFrom;
    }

    public void setAgeFrom(int AgeFrom)
    {
        this.AgeFrom = AgeFrom;
    }

    public int getAgeTo()
    {
        return AgeTo;
    }

    public void setAgeTo(int AgeTo)
    {
        this.AgeTo = AgeTo;
    }

    public String getAgeAccuracy()
    {
        return AgeAccuracy;
    }

    public void setAgeAccuracy(String AgeAccuracy)
    {
        this.AgeAccuracy = AgeAccuracy;
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

    public String getSize()
    {
        return Size;
    }

    public void setSize(String Size)
    {
        this.Size = Size;
    }

}
