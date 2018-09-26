package inzynierka.animalshelters.models;

public class PhotoModel {
    private int Id;
    private String Content;

    public PhotoModel(int Id, String Content)
    {
        this.Id = Id;
        this.Content = Content;
    }

    public int getId()
    {
        return this.Id;
    }

    public void setId(int Id)
    {
        this.Id = Id;
    }

    public String getContent()
    {
        return Content;
    }

    public void setContent(String Content)
    {
        this.Content = Content;
    }
}
