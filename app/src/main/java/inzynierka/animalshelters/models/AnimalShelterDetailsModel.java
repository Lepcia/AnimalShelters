package inzynierka.animalshelters.models;

import java.util.Collection;

public class AnimalShelterDetailsModel {
    private int Id;
    private String Name;
    private String PostalCode;
    private String City;
    private String Street;
    private String Number;
    private String FullAdres;
    private Collection<AnimalModel> Animals;
    private String Avatar;
    private String BankAccountNumer;

    public AnimalShelterDetailsModel()
    {}

    public AnimalShelterDetailsModel(int Id, String Name, String PostalCode, String City, String Street, String Number,
                                     String FullAdres, Collection<AnimalModel> Animals, String Avatar,
                                     String BankAccountNumber)
    {
        this.Id = Id;
        this.Name = Name;
        this.PostalCode = PostalCode;
        this.City = City;
        this.Street = Street;
        this.Number = Number;
        this.FullAdres = FullAdres;
        this.Animals = Animals;
        this.Avatar = Avatar;
        this.BankAccountNumer = BankAccountNumber;
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

    public String getPostalCode()
    {
        return PostalCode;
    }

    public void setPostalCode(String PostalCode)
    {
        this.PostalCode = PostalCode;
    }

    public String getCity()
    {
        return City;
    }

    public void setCity(String City)
    {
        this.City = City;
    }

    public String getStreet()
    {
        return Street;
    }

    public void setStreet(String Street)
    {
        this.Street = Street;
    }

    public String getNumber()
    {
        return Number;
    }

    public void setNumber(String Number)
    {
        this.Number = Number;
    }

    public String getFullAdres() {
        return FullAdres;
    }

    public void setFullAdres(String fullAdres) {
        FullAdres = fullAdres;
    }

    public Collection<AnimalModel> getAnimals() {
        return Animals;
    }

    public void setAnimals(Collection<AnimalModel> animals) {
        Animals = animals;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getBankAccountNumer() {
        return BankAccountNumer;
    }

    public void setBankAccountNumer(String bankAccountNumer) {
        BankAccountNumer = bankAccountNumer;
    }
}
