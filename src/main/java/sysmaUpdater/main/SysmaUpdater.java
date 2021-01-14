package sysmaUpdater.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sysmaUpdater.controllers.IUpdateController;

public class SysmaUpdater extends Application
{
    private static final String VERSION = "1.0";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/update.fxml"));
        Parent parent = loader.load();
        ((IUpdateController)loader.getController()).setVersion(VERSION);
        primaryStage.setTitle("SysmaUpdater - Updating");
        Scene scene = new Scene(parent);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}