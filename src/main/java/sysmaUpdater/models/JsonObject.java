package sysmaUpdater.models;

public abstract class JsonObject
{
    private String message;
    private Error error = Error.A_0x00;

    JsonObject(){} //Json

    JsonObject(Error error)
    {
        this.error = error;
        this.message = null;
    }

    JsonObject(String message)
    {
        this.message = message;
    }

    public boolean hasError()
    {
        return error != Error.A_0x00;
    }

    public Error getError()
    {
        return error;
    }

    public void setError(Error error)
    {
        this.error = error;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}