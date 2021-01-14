package sysmaUpdater.models;

public class JsonUpdateObject extends JsonObject
{
    private byte[] file = new byte[0];

    public JsonUpdateObject() //Json
    {
        super();
    }

    public JsonUpdateObject(Error error)
    {
        super(error);
    }

    public JsonUpdateObject(String message, byte[] file)
    {
        super(message);
        this.file = file;
    }

    public byte[] getFileAsBytes()
    {
        return file;
    }
}