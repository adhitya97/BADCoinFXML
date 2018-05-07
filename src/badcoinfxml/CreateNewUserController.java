package badcoinfxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;


public class CreateNewUserController implements Initializable
{
    @FXML
    Label newUsernameMessage;
    @FXML
    TextField username;
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    private static boolean flag;
    
    private BlockChain application;
    
    private static boolean isNewUsernameValid = false, isEmailValid = false;
    
    @FXML
    public void handleLogin(MouseEvent event) {
        application.gotoLogin();
    }

    public void setApp(BlockChain application){
        this.application = application;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        newUsernameMessage.setText("");
        isNewUsernameValid = false;
        isEmailValid = false;
    }
    
    @FXML
    public void handleUsernameModified(MouseEvent event){
        String userid = username.getText();
        if ("".equals(userid)){
            newUsernameMessage.setText("");
            isNewUsernameValid = false;
        }
        else{
            isNewUsernameValid = true;
            for (User user: BlockChain.usersList)
            {
                if (user.id.equals(userid))
                {
                    newUsernameMessage.setText(userid + " already exists.");
                    flag = true;
                    return;
                }
            }
            flag = false;
            newUsernameMessage.setText(userid + " is available.");
        }        
    }
    
    @FXML
    public void handleUsernameModifiedKey(KeyEvent event){
        if (event.getCode() == KeyCode.TAB)
        {
            String userid = username.getText();
            if ("".equals(userid)){
                isNewUsernameValid = false;
                newUsernameMessage.setText("");
            }
            else{
                isNewUsernameValid = true;
                for (User user: BlockChain.usersList)
                {
                    if (user.id.equals(userid))
                    {
                        newUsernameMessage.setText(userid + " already exists.");
                        flag = true;
                        return;
                    }
                }
                flag = false;
                newUsernameMessage.setText(userid + " is available.");
            }
        }
    }
    
    
    @FXML
    public void handleEmailValidKey(KeyEvent event){
        String mail = email.getText();
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(mail);
        if (m.matches()){
            isEmailValid = true;
        }
        else{
            isEmailValid = false;
        }
    }
    
    @FXML
    public void handleSubmitCreateUser(MouseEvent event){
        String userid = username.getText();
        String passwd = password.getText();
        String mail = email.getText();
        if (!flag && !"".equals(userid) && !"".equals(passwd) && !"".equals(mail) && isEmailValid)
        {
            User user = new User(userid, passwd, mail);
            BlockChain.usersList.add(user);
            application.loggedUser = user;
            for (User user1: BlockChain.usersList)
                System.out.println(user1.id);
            application.gotoProfile();
        }
    }
}
