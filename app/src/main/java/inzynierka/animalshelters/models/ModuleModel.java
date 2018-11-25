package inzynierka.animalshelters.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ModuleModel {
    public int Id;
    public String Symbol;
    public String Name;
    public String Icon;
    public int Order;

    public ModuleModel()
    {}

    public ModuleModel(JSONObject object)
    {
        try {
            this.Id = object.getInt("id");
            this.Symbol = object.getString("symbol");
            this.Name = object.getString("name");
            this.Icon = object.getString("icon");
            this.Order = object.getInt("order");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public ModuleModel(int Id, String Symbol, String Name, String Icon, int Order)
    {
        this.Id = Id;
        this.Symbol = Symbol;
        this.Name = Name;
        this.Icon = Icon;
        this.Order = Order;
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
}
