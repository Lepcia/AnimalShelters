package inzynierka.animalshelters.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import inzynierka.animalshelters.helpers.DataHelper;
import inzynierka.animalshelters.helpers.DateFormatHelper;

public class RightsModel {
    public int Id;
    public String Symbol;
    public String Name;
    public int IdModule;

    public RightsModel()
    {}

    public RightsModel(JSONObject object)
    {
        try {
            this.Id = object.getInt("id");
            this.Symbol = object.getString("symbol");
            this.Name = object.getString("name");
            this.IdModule = object.getInt("idModule");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public RightsModel(int Id, String Symbol, String Name, int IdModule)
    {
        this.Id = Id;
        this.Symbol = Symbol;
        this.Name = Name;
        this.IdModule = IdModule;
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

    public int getIdModule() { return IdModule; }

    public void setIdModule(int IdModule) { this.IdModule = IdModule; }
}
