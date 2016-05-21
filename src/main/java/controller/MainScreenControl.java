/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.apache.commons.lang3.StringUtils;


public class MainScreenControl implements Initializable {
    
    private UserManager um = new UserManager();
    
    private User current = null;
    
    private int mode = 0;
    
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
    private Label targetLabel;
    
    @FXML
    private Label amountLabel;
    
    @FXML
    private Label pwLabel;
    
    @FXML
    private TextField targetField;
    
    @FXML
    private TextField amountField;
    
    @FXML
    private PasswordField oldpwField;
    
    @FXML
    private PasswordField newpwField;
    
    @FXML
    private PasswordField newpwConfField;
    
    @FXML
    private void balanceButtonPressed(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        mode = 1;
        
        clear();
        
        bigLabel.setText(um.accountInfo(current));
        smallLabel.setText(um.balanceInfo(current));
    }
    
    @FXML
    private void transButtonPressed(ActionEvent event) {
        
        mode = 2;
        
        clear();
        
        goButton.setVisible(true);
        goButton.setText("Proceed");
        targetLabel.setVisible(true);
        amountLabel.setVisible(true);
        pwLabel.setVisible(true);
        
        targetField.setVisible(true);
        amountField.setVisible(true);
        newpwConfField.setVisible(true);
        
        targetLabel.setText("Target account:");
        amountLabel.setText("Amount:");
        pwLabel.setText("Your password:");
        
        bigLabel.setText("Enter transaction details:");
        
    }
    
    @FXML
    private void loanButtonPressed(ActionEvent event) throws ParserConfigurationException, SAXException, IOException {
        
        mode = 3;
        
        clear();
        goButton.setVisible(true);
        goButton.setText("Take loan");
        
        if(current.getLoan()) goButton.setDisable(true);
        
        bigLabel.setText(um.loanInfo(current));
        smallLabel.setText(um.paymentInfo(current));
    
    }
    
    @FXML
    private void infoButtonPressed(ActionEvent event) {
        
        mode = 4;
        
        clear();
        
        goButton.setVisible(true);
        goButton.setText("Save");
        targetLabel.setVisible(true);
        amountLabel.setVisible(true);
        pwLabel.setVisible(true);
        
        oldpwField.setVisible(true);
        newpwField.setVisible(true);
        newpwConfField.setVisible(true);
        
        targetLabel.setText("Old password:");
        amountLabel.setText("New password:");
        pwLabel.setText("Confirm password:");
        
        
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
    private void goButtonPressed(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        if(mode == 2) {
            
            if(!(StringUtils.isNumeric(targetField.getText()) && StringUtils.isNumeric(amountField.getText()))) {
                
                bigLabel.setText("Please provide a valid target and amount!");
                return;
            }
            
            if(Integer.parseInt(amountField.getText()) > current.getAccountBalance()) {
                
                bigLabel.setText("Insufficient account balance!");
                return;
            }
            
            if(Integer.parseInt(targetField.getText()) == current.getAccountNumber()) {
                
                bigLabel.setText("Error! The provided target is your own account!");
                return;
            }
            
            if(newpwConfField.getText().equals(current.getPassword())) {
                
                User to = um.findUserByAccountNumber(Integer.parseInt(targetField.getText()));
                if(to == null) {
                    
                    bigLabel.setText("The specified target account does not exist!");
                    return;
                }
                
                um.processTransaction(current, to, amountField.getText());
                bigLabel.setText("Succesful transaction!");
                
            } else {
                
                bigLabel.setText("Wrong password, please try again!");
                return;
            }
            
            targetField.setText("");
            amountField.setText("");
            newpwConfField.setText("");
            
            return;
        }
        
        if(mode == 3) {
        
            current.setAccountBalance(current.getAccountBalance() + um.openLoan(current));
            current.setLoan(true);
            bigLabel.setText(um.loanInfo(current));
            smallLabel.setText(um.paymentInfo(current));
            goButton.setDisable(true);
        }
        
        if(mode == 4) {
            
            if(oldpwField.getText().equals("")) {
                
                bigLabel.setText("Please provide your old password!");
                return;
            }
            
            if(newpwField.getText().equals("")) {
                
                bigLabel.setText("Please provide a new password!");
                return;
            }
            
            if(newpwConfField.getText().equals("")) {
                
                bigLabel.setText("Please confirm your new password!");
                return;
            }
            
            if(!oldpwField.getText().equals(current.getPassword())) {
                
                bigLabel.setText("Wrong old password, try again!");
                return;
            }
            
            if(!newpwField.getText().equals(newpwConfField.getText())) {
                
                bigLabel.setText("The password and the confirmation don't match!");
                return;
            }
            
            um.changePassword(current, newpwField.getText());
            bigLabel.setText("Password change successful!");
            
            oldpwField.setText("");
            newpwField.setText("");
            newpwConfField.setText("");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        targetLabel.managedProperty().bind(targetLabel.visibleProperty());
        amountLabel.managedProperty().bind(amountLabel.visibleProperty());
        pwLabel.managedProperty().bind(pwLabel.visibleProperty());
        
        targetField.managedProperty().bind(targetField.visibleProperty());
        amountField.managedProperty().bind(amountField.visibleProperty());
        
        oldpwField.managedProperty().bind(oldpwField.visibleProperty());
        newpwField.managedProperty().bind(newpwField.visibleProperty());
        newpwConfField.managedProperty().bind(newpwConfField.visibleProperty());
        
        goButton.managedProperty().bind(goButton.visibleProperty());
        
        clear();
        
        bigLabel.setText("Use the buttons to the left to perform actions.");
        
        
    }

    public void setCurrentUser(User u) throws SAXException, ParserConfigurationException, IOException, TransformerException {
        
        current = u;
        userInfo.setText("Logged in as " + current.getUserName());
        
        if(current.getLoan()) {
            
            current.setAccountBalance(um.processLoan(current, current.getAccountBalance()));
            current.setLoan(um.isLoanCompleted(current));
        }
    }
    
    private void clear() {
        
        bigLabel.setText("");
        smallLabel.setText("");
        
        targetLabel.setVisible(false);
        amountLabel.setVisible(false);
        pwLabel.setVisible(false);
        
        targetField.setVisible(false);
        amountField.setVisible(false);
        
        oldpwField.setVisible(false);
        newpwField.setVisible(false);
        newpwConfField.setVisible(false);
        
        
        goButton.setVisible(false);
        goButton.setDisable(false);
    }
    
}
