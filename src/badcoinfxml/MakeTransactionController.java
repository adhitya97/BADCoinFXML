package badcoinfxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;

public class MakeTransactionController implements Initializable
{
    private BlockChain application;
    private float transferAmount;
    private User recipient;
    private static boolean isUsernameCorrect = false, isAmountValid = false, isConfirmation = false;
    
    @FXML
    TextField balance;
    @FXML
    TextArea publicKey;
    @FXML
    TextField username;
    @FXML
    CheckBox confirmation;
    @FXML
    Button submit;
    @FXML
    TextField amount;
    @FXML
    Label validAmount;
    @FXML
    Label transactionStatus;
    
    public void setApp(BlockChain application){
        this.application = application;
        balance.setText(application.loggedUser.wallet.getBalance() + "");
        isUsernameCorrect = false;
        isAmountValid = false;
        isConfirmation = false;

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        transactionStatus.setText("");
        submit.setDisable(true);
    }    
    
    @FXML
    public void handleLogout(MouseEvent event) {
        application.loggedUser = null;
        application.gotoLogin();
    }
    
    @FXML
    public void handleBack(MouseEvent event) {
        transactionStatus.setText("");
        application.gotoProfile();
    }
    
    @FXML
    public void handleUsername(MouseEvent event) {
        transactionStatus.setText("");
        String publickey = publicKey.getText();
        for (User user: BlockChain.usersList)
        {
            if (Utilities.getStringFromKey(user.wallet.publicKey).equals(publickey))
            {
                username.setText(user.id);
                recipient = user;
                isUsernameCorrect = true;
                if (isUsernameCorrect && isAmountValid && isConfirmation)
                    
                    submit.setDisable(false);
                else
                {
                    submit.setDisable(true);
                }
                return;
            }
        }
        submit.setDisable(true);
        recipient = null;
        username.setText("Invalid Public Key. No User Found");  
        isUsernameCorrect = false;
    }
    
    @FXML
    public void handleUsernameKey(KeyEvent event) {
        transactionStatus.setText("");
        if (event.getCode() == KeyCode.TAB)
        {
            String publickey = publicKey.getText();
            for (User user: BlockChain.usersList)
            {
                if (Utilities.getStringFromKey(user.wallet.publicKey).equals(publickey))
                {
                    username.setText(user.id);
                    recipient = user;
                    isUsernameCorrect = true;
                    if (isUsernameCorrect && isAmountValid && isConfirmation)
                        submit.setDisable(false);
                    else
                        submit.setDisable(true);
                    return;
                }
            }
            username.setText("Invalid Public Key. No User Found");
            recipient = null;
            submit.setDisable(true);
            isUsernameCorrect = false;
        }
    }
    
    
    @FXML
    public void handleAmountValidKey(KeyEvent event){
        transactionStatus.setText("");
        //if (event.getCode() == KeyCode.TAB)
        {
            validAmount.setText("");
            User sender = application.loggedUser;
            String bal = amount.getText();
            float balFloat;
            try{
                balFloat = Float.valueOf(bal);
                System.out.println(bal);
                if (balFloat >= 0.1 && sender.wallet.getBalance() >= balFloat)
                {
                    isAmountValid = true;
                    transferAmount = balFloat;
                    if (isUsernameCorrect && isAmountValid && isConfirmation)
                        submit.setDisable(false);
                    else
                        submit.setDisable(true);
                }
                else
                {
                    if (sender.wallet.getBalance() < balFloat)
                        validAmount.setText("Insufficient funds");
                    if (balFloat < 0.1)
                        validAmount.setText("Amount must be atleast 0.1 BADCoins");
                    isAmountValid = false;
                    submit.setDisable(true);
                }

            } catch(Exception ex) 
            {
                validAmount.setText("Enter a numeric value");
                isAmountValid = false;
                submit.setDisable(true);
            }
        }
    }
    
    @FXML
    public void handleConfirmation(MouseEvent event) {
        transactionStatus.setText("");
        if (confirmation.isSelected())
            isConfirmation = true;
        else
            isConfirmation = false;
        
        if (isConfirmation && isAmountValid && isUsernameCorrect)
        {
            System.out.println(confirmation.isSelected());
            submit.setDisable(false);
        }
        else
        {
            System.out.println(confirmation.isSelected());
            submit.setDisable(true);
        }
    }
    
    @FXML
    public void handleSubmit(MouseEvent event) {
        Block block = new Block(BlockChain.blockchain.get(BlockChain.blockchain.size()-1).hash);

        boolean transactionStatusFlag = block.addTransaction(application.loggedUser.wallet.makeTransaction(recipient.wallet.publicKey, transferAmount));
        BlockChain.addBlock(block);
        if (transactionStatusFlag)
        {
            transactionStatus.setText("Transaction Successful");
            balance.setText(application.loggedUser.wallet.getBalance() + "");
        }
        else
            transactionStatus.setText("Transaction Unsuccessful");
        submit.setDisable(true);
        
    }
}
    