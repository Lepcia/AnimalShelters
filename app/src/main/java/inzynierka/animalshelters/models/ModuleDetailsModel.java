package inzynierka.animalshelters.models;

import android.support.annotation.IdRes;

import org.json.JSONException;
import org.json.JSONObject;

public class ModuleDetailsModel {
    public int Id;
    public String Symbol;
    public String Name;
    public String Icon;
    public int Order;
    public int IdUser;
    public String UserFirstName;
    public String UserLastName;
    public String UserEmail;
    private int IdRole;

    public ModuleDetailsModel()
    {}

    public ModuleDetailsModel(JSONObject object)
    {
        try {
            this.Id = object.getInt("idModule");
            this.Symbol = object.getString("moduleSymbol");
            this.Name = object.getString("moduleName");
            this.Icon = object.getString("moduleIcon");
            this.Order = object.getInt("moduleOrder");
            this.IdRole = object.getInt("idRole");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public ModuleDetailsModel(int Id, String Symbol, String Name, String Icon, int Order, int IdUser, String UserFirstName, String UserLastName, String UserEmail)
    {
        this.Id = Id;
        this.Symbol = Symbol;
        this.Name = Name;
        this.Icon = Icon;
        this.Order = Order;
        this.IdUser = IdUser;
        this.UserFirstName = UserFirstName;
        this.UserLastName = UserLastName;
        this.UserEmail = UserEmail;
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

    public String getSymbol()
    {
        return Symbol;
    }

    public void setSymbol(String Symbol)
    {
        this.Symbol = Symbol;
    }

    public String getIcon()
    {
        return Icon;
    }

    public void setIcon(String Icon)
    {
        this.Icon = Icon;
    }

    public int getOrder() { return Order; }

    public void setOrder(int Order) { this.Order = Order; }

    public int getIdUser()
    {
        return IdUser;
    }

    public void setIdUser(int IdUser)
    {
        this.IdUser = IdUser;
    }

    public String getUserFirstName() { return  UserFirstName; }

    public void setUserFirstName(String UserFirstName)
    {
        this.UserFirstName = UserFirstName;
    }

    public String getUserLastName()
    {
        return UserLastName;
    }

    public void setUserLastName(String UserLastName) { this.UserLastName = UserLastName; }

    public String getUserEmail()
    {
        return UserEmail;
    }

    public void setUserEmail(String UserEmail) { this.UserEmail = UserEmail; }

    public int getIdRole() {
        return IdRole;
    }

    public void setIdRole(int idRole) {
        IdRole = idRole;
    }
}
