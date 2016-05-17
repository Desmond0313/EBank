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


public class MainScreenControl implements Initializable {
    
    UserManager um = new UserManager();
    
    User current = null;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private PasswordField pwField;
    
    @FXML
    private Button setButton;
    
    @FXML
    private void setButtonPressed(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        current.setUserName(nameField.getText());
        current.setPassword(pwField.getText());
        
        um.modifyUserData(current);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setCurrentUser(User u) {
        
        current = u;
    }
    
}
