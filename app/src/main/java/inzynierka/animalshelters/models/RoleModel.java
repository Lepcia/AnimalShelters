package inzynierka.animalshelters.models;

import org.json.JSONException;
import org.json.JSONObject;

public class RoleModel {
    private int Id;
    private String Name;
    private String Symbol;

    public RoleModel()
    {}

    public RoleModel(JSONObject object)
    {
        try {
            this.Id = object.getInt("id");
            this.Symbol = object.getString("symbol");
            this.Name = object.getString("name");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public RoleModel(int Id, String Name, String Symbol)
    {
        this.Id = Id;
        this.Name = Name;
        this.Symbol = Symbol;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }
}
