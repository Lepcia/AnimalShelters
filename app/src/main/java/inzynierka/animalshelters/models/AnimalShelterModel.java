package inzynierka.animalshelters.models;

import org.json.JSONException;
import org.json.JSONObject;

import inzynierka.animalshelters.helpers.DataHelper;

public class AnimalShelterModel {
    private int Id;
    private String Name;
    private String PostalCode;
    private String City;
    private String Street;
    private String Number;
    private String FullAdres;
    private int[] Animals;
    private String Phone;
    private String Email;
    private String Avatar;
    private String BankAccountNumber;

    public AnimalShelterModel()
    {}

    public AnimalShelterModel(JSONObject object)
    {
        try {
            this.Id = object.getInt("id");
            this.Name = object.getString("name");
            this.PostalCode = object.getString("postalCode");
            this.City = object.getString("city");
            this.Street = object.getString("street");
            this.Number = object.getString("number");
            this.FullAdres = object.getString("fullAdres");
            this.Email = object.getString("email");
            this.Phone = object.getString("phone");
            this.Avatar = object.getString("avatar");
            this.Animals = DataHelper.JSONObjectToIntArray(object.getJSONArray("animals"));
            this.BankAccountNumber = object.getString("bankAccountNumber");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public AnimalShelterModel(int Id, String Name, String PostalCode, String City, String Street,
                              String Number, String FullAdres, int[] Animals, String Phone, String Email,
                              String Avatar, String BankAccountNumber)
    {
        this.Id = Id;
        this.Name = Name;
        this.PostalCode = PostalCode;
        this.City = City;
        this.Street = Street;
        this.Number = Number;
        this.FullAdres = FullAdres;
        this.Animals = Animals;
        this.Email = Email;
        this.Phone = Phone;
        this.Avatar = Avatar;
        this.BankAccountNumber = BankAccountNumber;
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

    public int[] getAnimals() {
        return Animals;
    }

    public void setAnimals(int[] animals) {
        Animals = animals;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBankAccountNumber() {
        return BankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumer) {
        BankAccountNumber = bankAccountNumer;
    }
}
