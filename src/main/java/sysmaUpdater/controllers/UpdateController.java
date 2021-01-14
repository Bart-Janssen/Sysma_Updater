package sysmaUpdater.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import sysmaUpdater.customExceptions.UpdateException;
import sysmaUpdater.logic.Updater;
import sysmaUpdater.messageBox.CustomErrorMessage;
import sysmaUpdater.messageBox.CustomOkMessage;
import sysmaUpdater.messageBox.FXML_MessageBoxStatus;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateController implements Initializable, IUpdateController
{
    @FXML private Label labelVersion;
    @FXML private ProgressBar progressBarUpdating;
    private Updater updater;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(() ->
        {
            stage = (Stage)progressBarUpdating.getScene().getWindow();
            updater = new Updater(this);
            try
            {
                updater.update();
                new CustomOkMessage(stage, "Update klaar!").show();
            }
            catch (UpdateException e)
            {
                progressBarUpdating.setStyle("-fx-accent: red;");
                if (new CustomErrorMessage(stage, e.getError()).showAndAwaitStatus() == FXML_MessageBoxStatus.CONTINUE) exit(false);
                return;
            }
            exit(true);
        });
    }

    private void exit(boolean runSysma)
    {
        try
        {
            if (runSysma) updater.runSysma();
            updater.delete();
        }
        catch (UpdateException e)
        {
            new CustomErrorMessage(stage, e.getError()).showAndAwaitStatus();
        }
        Runtime.getRuntime().exit(1);
    }

    @Override
    public void performStep()
    {
        progressBarUpdating.setProgress(progressBarUpdating.getProgress() + 0.25d);
    }

    @Override
    public void setVersion(String version)
    {
        labelVersion.setText("Versie: " + version);
    }
}