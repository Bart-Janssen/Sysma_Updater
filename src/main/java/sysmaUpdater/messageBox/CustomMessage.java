package sysmaUpdater.messageBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sysmaUpdater.models.Error;

import java.util.List;

public abstract class CustomMessage
{
    final static String OK = "messageBoxOk";
    final static String CONTINUE_CANCEL = "messageBoxContinueCancel";
    final static String SAVE = "messageBoxSaveDont_SaveCancel";
    final static String ERROR = "messageBoxError";

    private Stage owner;
    private List<String> messages;
    private String messageBoxType;
    private FXML_MessageBoxStatus status;

    public abstract FXML_MessageBoxStatus showAndAwaitStatus();

    protected void show()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/customElements/messageBox/" + messageBoxType + ".fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            stage.setTitle(this instanceof CustomErrorMessage ? "SysmaUpdater - Error" : "SysmaUpdater - Melding");
            ((FXML_MessageBoxBase)loader.getController()).initData(messages);
            if (owner == null) stage.show();
            else stage.showAndWait();
            status = ((FXML_MessageBoxBase)loader.getController()).getStatus();
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ernstige foutmelding!");
            alert.setHeaderText(Error.U_0x01.toString());
            alert.show();
            e.printStackTrace();
        }
    }

    FXML_MessageBoxStatus getStatus()
    {
        return status;
    }

    void setMessages(List<String> messages)
    {
        this.messages = messages;
    }

    CustomMessage(Stage owner, String messageBoxType, List<String> messages)
    {
        this.owner = owner;
        this.messages = messages;
        this.messageBoxType = messageBoxType;
    }
}