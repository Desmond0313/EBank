package controller;

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
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

public class LoginScreenControl implements Initializable {
    
    UserManager um = new UserManager();
    
    User current = null;
    
    @FXML
    private Label label;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Button regButton;
    
    @FXML
    private TextField accountField;
    
    @FXML
    private PasswordField pwField;
    
    @FXML
    private void loginButtonPressed(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        if(StringUtils.isNumeric(accountField.getText())) {
            
            current = um.findUserByAccountNumber(Integer.parseInt(accountField.getText()));
        } else {
            
            label.setText("Please enter a valid account number!");
            return;
        }
        
        
        if(current == null) {
            
            label.setText("Account not found! Please check the number you entered!");
        } else {
            
            if(current.getPassword().equals(pwField.getText())) {
                
                Stage stage;
                Parent root;

                stage = (Stage) regButton.getScene().getWindow();

                FXMLLoader fl = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));

                root = fl.load();
                
                fl.<MainScreenControl>getController().setCurrentUser(current);

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
                
            } else {
                
                label.setText("Wrong password! Please try again.");
            }            
            
        }
    }
    
    @FXML
    private void regButtonPressed(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;
        
        stage = (Stage) regButton.getScene().getWindow();
        
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/fxml/RegScreen.fxml"));
        
        root = fl.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
