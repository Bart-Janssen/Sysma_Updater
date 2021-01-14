package sysmaUpdater.messageBox;

import javafx.stage.Stage;
import sysmaUpdater.models.Error;
import java.util.ArrayList;
import java.util.List;

public class CustomErrorMessage extends CustomMessage
{
    public CustomErrorMessage(Stage owner, Error error)
    {
        super(owner, ERROR, new ArrayList<>());
        List<String> messages = new ArrayList<>();
        messages.add("Error: " + error.name());
        messages.add("\n");
        messages.add("Noteer dit bovenstaande en vertel wat er gebeurde.");
        messages.add("Tip: Maak foto of printscreen.");
        super.setMessages(messages);
    }

    public CustomErrorMessage(Error error)
    {
        super(null, ERROR, new ArrayList<>());
        List<String> messages = new ArrayList<>();
        messages.add("Error: " + error.name());
        messages.add("\n");
        messages.add("Noteer dit bovenstaande en vertel wat er gebeurde.");
        messages.add("Tip: Maak foto of printscreen.");
        super.setMessages(messages);
    }

    @Override
    public FXML_MessageBoxStatus showAndAwaitStatus()
    {
        super.show();
        return super.getStatus();
    }

    @Override
    public void show()
    {
        super.show();
    }
}