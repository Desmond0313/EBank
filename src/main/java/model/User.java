/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author takacsd
 */
public class User {
    
    private String userName;
    private String password;
    private int accountNumber;
    private int accountBalance;
    
    public User() {}

    public User(String userName, String password, int accountNumber, int accountBalance) {
        
        this.userName = userName;
        this.password = password;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }
    
    @Override
    public String toString() {
        
        return "Name: " + this.userName + "\nAccount number: " + this.accountNumber + "\nBalance: " + this.accountBalance;
    }
}
