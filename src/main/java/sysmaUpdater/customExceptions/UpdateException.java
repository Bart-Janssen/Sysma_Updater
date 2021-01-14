package sysmaUpdater.customExceptions;

import sysmaUpdater.models.Error;

public class UpdateException extends Exception
{
    private Error error;

    public UpdateException(Error error)
    {
        super(error.toString() + " : " + error.getError());
        this.error = error;
    }

    public Error getError()
    {
        return error;
    }
}