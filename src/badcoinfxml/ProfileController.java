
package badcoinfxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


public class ProfileController implements Initializable
{
    @FXML
    Label name;
    @FXML
    TextArea publicKey;
    @FXML
    TextArea privateKey;
    @FXML
    TextField balance;
    @FXML
    Label validityLabel;
    
    private BlockChain application;
    
    public void setApp(BlockChain application){
        this.application = application;
        User user = application.loggedUser;
        name.setText("Welcome " + user.id);
        publicKey.setText(Utilities.getStringFromKey(user.wallet.publicKey));   
        privateKey.setText(Utilities.getStringFromKey(user.wallet.privateKey));
        balance.setText(user.wallet.getBalance() + "");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        validityLabel.setText("");
    }
    
    @FXML
    public void handleLogout(MouseEvent event) {
        application.loggedUser = null;
        application.gotoLogin();
    }
    
    @FXML
    public void handleOtherUsersLink(MouseEvent event){
        try {
            ViewUsersController viewUsers = (ViewUsersController) application.replaceSceneContent("ViewUsers.fxml");
            viewUsers.setApp(this.application);
        } catch (Exception ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void handleMakeTransaction(MouseEvent event){
        try {
            MakeTransactionController makeTransaction = (MakeTransactionController) application.replaceSceneContent("MakeTransaction.fxml");
            makeTransaction.setApp(this.application);
        } catch (Exception ex) {
            ex.printStackTrace();
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void handleCheckValidity(MouseEvent event){
        if(BlockChain.isChainValid())
        {
            validityLabel.setText("Blockchain is valid");
        }
        else
        {
            validityLabel.setText("Blockchain is invalid");
        }
        
    }
}
