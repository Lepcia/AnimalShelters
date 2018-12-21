package inzynierka.animalshelters.models;

public class AnimalShelterSearchModel {
    private String Name;
    private String City;
    private String Street;

    public AnimalShelterSearchModel()
    {}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }
}
