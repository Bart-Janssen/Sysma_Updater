package sysmaUpdater.models;

public enum Error
{
    A_0x00("No error : Normal operation"),

    U_0x01("CustomMessage.show() : has failed"),
    U_0x02("Updater.getNewest() : Cannot get new version"),
    U_0x03("Updater.writeNewVersionFile() : Cannot write new version"),
    U_0x04("Updater.deleteOldVersion() : Cannot delete old version"),
    U_0x05("Updater.runSysma() : Cannot run new version"),
    U_0x06("Updater.delete() : Cannot delete updater");

    private final String error;

    Error(String error)
    {
        this.error = error;
    }

    public String getError()
    {
        return error;
    }
}