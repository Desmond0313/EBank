// CHECKSTYLE:OFF
package controller;

import model.UserManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import model.*;
import org.xml.sax.SAXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author takacsd
 */
public class RegisterScreenControl implements Initializable {
    
    private static Logger logger = LoggerFactory.getLogger(RegisterScreenControl.class);
    private UserManager um = new UserManager();
    private User u = new User();
    
    boolean gen = false;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField numberField;
    
    @FXML
    private PasswordField pwField;
    
    @FXML
    private PasswordField pwConfField;
    
    @FXML
    private Button regButton;
    
    @FXML
    private Button genButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Label dialogLabel;
    
    @FXML
    private void regButtonPressed(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        if(nameField.getText().equals("")) {
            
            dialogLabel.setText("Please enter your name!");
            return;
        }
        
        if(!gen) {
            
            dialogLabel.setText("Please generate an account number!");
            return;
        }
        
        if(pwField.getText().equals("")) {
            
            dialogLabel.setText("Please provide a password!");
            return;
        }
        
        if(pwConfField.getText().equals("")) {
            
            dialogLabel.setText("Please confirm your password!");
            return;
        }
        
        if(!pwField.getText().equals(pwConfField.getText())) {
            
            dialogLabel.setText("The passwords don't match!");
            return;
        }
        
        u.setUserName(nameField.getText());
        u.setPassword(pwField.getText());
        u.setAccountNumber(Integer.parseInt(numberField.getText()));
        u.setAccountBalance(um.getDefaultBalance());
        
        um.createUserData(u);
        
        dialogLabel.setText("Succesful registration!");
        
        regButton.setDisable(true);
        
        cancelButton.setText("Login");
        
        logger.info("A new user was registered with data:\n" + u.toString());
    }
    
    @FXML
    private void cancelButtonPressed(ActionEvent event) throws IOException {
        
                Stage stage;
                Parent root;

                stage = (Stage) cancelButton.getScene().getWindow();

                FXMLLoader fl = new FXMLLoader(getClass().getResource("/fxml/LoginScreen.fxml"));

                root = fl.load();

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
    }
    
    @FXML
    private void genButtonPressed(ActionEvent event) throws SAXException, IOException, ParserConfigurationException {
        
        numberField.setText(um.generateAccountNumber());
        
        gen = true;
        
        genButton.setDisable(true);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        logger.info("User changed to the register screen.");
        numberField.setDisable(true);
        numberField.setText("Please generate");
    }
    
}
