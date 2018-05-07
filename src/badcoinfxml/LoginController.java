package badcoinfxml;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable
{
    @FXML
    TextField userId;
    @FXML
    PasswordField password;
    @FXML
    Button login;
    @FXML
    Label errorMessage;
    @FXML
    Hyperlink createUserLink;

    private BlockChain application;
    
    
    public void setApp(BlockChain application){
        this.application = application;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
    }
    @FXML
    public void handleLoginSubmit(MouseEvent event) {
        if (application == null){
            errorMessage.setText("Hello " + userId.getText());
        } else {
            if (!application.userLogging(userId.getText(), password.getText())){
                errorMessage.setText("Incorrect username or password!");
            }
        }
    }
    
    @FXML
    public void handleCreateUser(MouseEvent event){
        try {
            CreateNewUserController newUser = (CreateNewUserController) application.replaceSceneContent("CreateNewUser.fxml");
            newUser.setApp(this.application);
        } catch (Exception ex) {
        }
    }
    
}
