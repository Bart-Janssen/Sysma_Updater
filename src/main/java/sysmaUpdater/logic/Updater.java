package sysmaUpdater.logic;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sysmaUpdater.controllers.IUpdateController;
import sysmaUpdater.customExceptions.UpdateException;
import sysmaUpdater.messageBox.CustomOkMessage;
import sysmaUpdater.models.Error;
import sysmaUpdater.models.JsonUpdateObject;
import java.io.*;
import java.net.URISyntaxException;

public class Updater
{
    private static final String HTTP = "http";
//    private static final String SERVER = "localhost";
        private static final String SERVER = "192.168.1.2";
    private static final int PORT = 51445;
    private static final String CONTENT_TYPE  = "content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String PATH = "/update";

    private IUpdateController updater;
    private File newVersion;
    private byte[] fileAsBytes;

    public Updater(IUpdateController updater)
    {
        this.updater = updater;
    }

    private void getNewestVersion() throws UpdateException
    {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTP).setHost(SERVER).setPath(PATH + "/getNewest").setPort(PORT);
        try (CloseableHttpClient httpClient = HttpClients.createDefault())
        {
            HttpGet httpGet = new HttpGet(builder.build());
            httpGet.addHeader(CONTENT_TYPE, APPLICATION_JSON);
            JsonUpdateObject jsonUpdateObject = new Gson().fromJson(EntityUtils.toString(httpClient.execute(httpGet).getEntity()), JsonUpdateObject.class);
            if (jsonUpdateObject.hasError()) throw new UpdateException(jsonUpdateObject.getError());
            fileAsBytes = jsonUpdateObject.getFileAsBytes();
        }
        catch (URISyntaxException | IOException e)
        {
            throw new UpdateException(Error.U_0x02);
        }
    }

    public void update() throws UpdateException
    {
        updater.performStep();
        getNewestVersion();
        updater.performStep();
        deleteOldVersion();
        updater.performStep();
        writeNewVersionFile();
        updater.performStep();
    }

    private void writeNewVersionFile() throws UpdateException
    {
        try
        {
            newVersion = new File(System.getProperty("user.home") + "\\Desktop\\Sysma.exe");
            FileOutputStream fileOutputStream = new FileOutputStream(newVersion);
            fileOutputStream.write(fileAsBytes);
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            throw new UpdateException(Error.U_0x03);
        }
    }

    private void killSysma()
    {
        try
        {
            File SysmaPID = new File(System.getProperty("java.io.tmpdir") + "SysmaPID.txt");
            BufferedReader reader = new BufferedReader(new FileReader(SysmaPID));
            String pid = reader.readLine();
            reader.close();
            Runtime.getRuntime().exec("cmd /c taskkill /F /PID " + pid);
            SysmaPID.delete();
        }
        catch (Exception e)
        {
            new CustomOkMessage(null, "Cannot kill Sysma").show();
            e.printStackTrace();
        }
    }

    private void deleteOldVersion() throws UpdateException
    {
        this.killSysma();
        File oldSysma = new File(System.getProperty("user.home") + "\\Desktop\\Sysma.exe");
        if (!oldSysma.delete()) throw new UpdateException(Error.U_0x04);
    }

    public void runSysma() throws UpdateException
    {
        try
        {
            Runtime.getRuntime().exec(newVersion.getPath());
        }
        catch (IOException e)
        {
            throw new UpdateException(Error.U_0x05);
        }
    }

    public void delete() throws UpdateException
    {
        try
        {
            Runtime.getRuntime().exec("cmd /c ping localhost -n 6 > nul && del "+ System.getProperty("java.io.tmpdir") + "SysmaUpdater.exe");
        }
        catch (IOException e)
        {
            throw new UpdateException(Error.U_0x06);
        }
    }
}