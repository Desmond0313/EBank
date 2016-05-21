package model;

import java.util.Objects;

/**
 * This class provides a {@code User} object to store
 * information at runtime about the current e-Bank user.
 * 
 * @author Takács Ferenc Dániel
 */
public class User {
    
    private String userName;
    private String password;
    private int accountNumber;
    private int accountBalance;
    private boolean hasLoan;
    
    /**
     * Empty constructor for the {@code User} class.
     * 
     */
    public User() {}

    /**
     * Parameterized constructor for the {@code User} class.
     * Every user is identified by their unique account number.
     * 
     * @param userName the user's forename and surname
     * @param password the user's login password
     * @param accountNumber the user's unique account number
     * @param accountBalance the user's current account balance
     * @param hasLoan specifies if the user has an active loan or not
     */
    public User(String userName, String password, int accountNumber, int accountBalance, boolean hasLoan) {
        
        this.userName = userName;
        this.password = password;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.hasLoan = hasLoan;
    }

    /**
     *  Returns the user's forename and surname.
     * @return the user's forename and surname
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the user's password.
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's unique account number.
     * @return the user's account number
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Returns the user's current account balance.
     * @return the user's account balance
     */
    public int getAccountBalance() {
        return accountBalance;
    }
    
    /**
     * Returns a {@code boolean} indicating an active loan.
     * If {@code hasLoan} is {@code false}, the user is loan-free.
     * If {@code hasLoan} is {@code true}, the user has an active loan.
     * @return if the user has an active loan
     */
    public boolean getLoan() {
        return hasLoan;
    }

    /**
     * Sets the user's name.
     * @param userName the new name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets a new password.
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets a new account number.
     * This method is not used because every user is
     * identified by their account number. It is randomly
     * generated upon registration.
     * @param accountNumber the new account number
     */
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Sets a new account balance.
     * @param accountBalance new account balance
     */
    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }
    
    /**
     * Sets if the user has an active loan or not
     * @param hasLoan new state of having a loan
     */
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
