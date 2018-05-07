
package badcoinfxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class ViewUsersController implements Initializable
{

    private BlockChain application;
    
    
    @FXML
    Hyperlink backLink;
    @FXML
    TextArea userDetails;
    
    public void setApp(BlockChain application){
        this.application = application;
        String details = "";
        for (User user: BlockChain.usersList)
        {
            if (!user.id.equals(application.loggedUser.id))
                details += "Username: " + user.id + "\nPublic Key: " + Utilities.getStringFromKey(user.wallet.publicKey) + "\n\n";
        }
        userDetails.setText(details);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }    
    
    @FXML
    public void handleLogout(MouseEvent event) {
        application.loggedUser = null;
        application.gotoLogin();
    }
    
    @FXML
    public void handleBack(MouseEvent event) {
        application.gotoProfile();
    }
    
    
}
