/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import model.*;
import org.xml.sax.SAXException;

/**
 *
 * @author takacsd
 */
public class RegisterScreenControl implements Initializable {
    
    UserManager um = new UserManager();
    User u = new User();
    
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
    private Label label;
    
    @FXML
    private void regButtonPressed(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        u.setUserName(nameField.getText());
        u.setPassword(pwField.getText());
        u.setAccountNumber(Integer.parseInt(numberField.getText()));
        u.setAccountBalance(30000);
        
        um.createUserData(u);
        
        label.setText("Succesful registration!");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        //TODO
    }
    
}
