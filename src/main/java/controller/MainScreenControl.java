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
    private Button balanceButton;
    
    @FXML
    private Button transButton;
    
    @FXML
    private Button loanButton;
    
    @FXML
    private Button infoButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button goButton;
    
    @FXML
    private Label bigLabel;
    
    @FXML
    private Label smallLabel;
    
    @FXML
    private Label userInfo;
    
    @FXML
    private void balanceButtonPressed(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        goButton.setManaged(false);
        goButton.setVisible(false);
        clear();
        
        bigLabel.setText(um.accountInfo(current));
        smallLabel.setText("Your balance:\t\t" + String.format("%,d", current.getAccountBalance()));
    }
    
    @FXML
    private void transButtonPressed(ActionEvent event) {
        
        
    }
    
    @FXML
    private void loanButtonPressed(ActionEvent event) {
        
        clear();
        goButton.setManaged(true);
        goButton.setVisible(true);
        
    
    }
    
    @FXML
    private void infoButtonPressed(ActionEvent event) {
        
        
    }
    
    @FXML
    private void logoutButtonPressed(ActionEvent event) throws IOException {
        
                Stage stage;
                Parent root;

                stage = (Stage) logoutButton.getScene().getWindow();

                FXMLLoader fl = new FXMLLoader(getClass().getResource("/fxml/LoginScreen.fxml"));

                root = fl.load();

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
    }
    
    @FXML
    private void goButtonPressed(ActionEvent event) {
        
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        goButton.setManaged(false);
        goButton.setVisible(false);
    }

    public void setCurrentUser(User u) {
        
        current = u;
        userInfo.setText("Logged in as " + current.getUserName());
    }
    
    public void clear() {
        
        bigLabel.setText("");
        smallLabel.setText("");
    }
    
}
