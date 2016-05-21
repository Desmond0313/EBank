/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author takacsd
 */
public class User {
    
    private String userName;
    private String password;
    private int accountNumber;
    private int accountBalance;
    private boolean hasLoan;
    
    public User() {}

    public User(String userName, String password, int accountNumber, int accountBalance, boolean hasLoan) {
        
        this.userName = userName;
        this.password = password;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.hasLoan = hasLoan;
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
    
    public boolean getLoan() {
        return hasLoan;
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
    
    public void setLoan(boolean hasLoan) {
        this.hasLoan = hasLoan;
    }
    
    @Override
    public String toString() {
        
        return "Name: " + this.userName + "\nAccount number: " + this.accountNumber + "\nBalance: " + this.accountBalance + "\n Active loan: " + this.hasLoan;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.userName);
        hash = 41 * hash + Objects.hashCode(this.password);
        hash = 41 * hash + this.accountNumber;
        hash = 41 * hash + this.accountBalance;
        hash = 41 * hash + (this.hasLoan ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.accountNumber != other.accountNumber) {
            return false;
        }
        if (this.accountBalance != other.accountBalance) {
            return false;
        }
        if (this.hasLoan != other.hasLoan) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    
    
}
