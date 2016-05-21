/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.time.temporal.ChronoUnit;

/**
 * This class provides connection between the {@code User}
 * class and the {@code FXML Controllers}. Every calculation
 * and data manipulation is done by this class. The {@code
 * FXML Controllers} only have to call the methods of this class.
 * 
 * @author Takács Ferenc Dániel
 */
public class UserManager {
    
    private String filePath = System.getProperty("user.home") + "/.users/users.xml";
    private int defaultBalance = 30000;
    
    /**
     * Returns the file path that the {@code users.xml} will be saved to.
     * @return the file path of the {@code users.xml}
     */
    public String getFilePath() {
        
        return this.filePath;
    }
    
    /**
     * Returns the default value of money that a new customer's
     * account gets upon registration.
     * @return the default starting balance
     */
    public int getDefaultBalance() {
        
        return this.defaultBalance;
    }
    
    /**
     * Changes the path where the {@code users.xml} file will be saved.
     * @param filePath the new path of the {@code users.xml}
     */
    public void setFilePath(String filePath) {
        
        this.filePath = filePath;
    }
    
    /**
     * Alters the default amount of money on a new account.
     * @param defaultBalance the new default balance
     */
    public void setDefaultBalance(int defaultBalance) {
        
        this.defaultBalance = defaultBalance;
    }
    
    /**
     * Checks if wether or not the {@code users.xml} exists.
     * 
     * @return {@code true} if the file already exists, {@code false} otherwise
     */
    public boolean checkForXML() {
        
        File checker = new File(filePath);
                
        return checker.exists();
    }
    
    /**
     * Creates the {@code users.xml} at the location contained by the
     * {@code filePath} variable. The initial file will consist only
     * of an empty {@code <users/>} tag.
     * 
     * @throws ParserConfigurationException if there is an error while configuring the parser
     * @throws TransformerConfigurationException if there is an error while configuring the transformer
     * @throws TransformerException if there is an error while transforming the DOM document
     */
    public void createXML() throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        
        File output = new File(filePath);
        
        output.getParentFile().mkdirs();
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.newDocument();
        
        Element root = doc.createElement("users");
        doc.appendChild(root);
        
        TransformerFactory trf = TransformerFactory.newInstance();
        Transformer tr = trf.newTransformer();
        
        DOMSource src = new DOMSource(doc);
        StreamResult res = new StreamResult(output);
        
        tr.transform(src, res);
    }
    
    /**
     * Creates a new entry in the {@code users.xml} with the
     * data of {@code User u}. Sample:
     * {@code 
     * 
     * <users>
     *      <user>
     *          <name>Username</name>
     *          <accountNumber>000000</accountNumber>
     *          <balance>420</balance>
     *          <password>sosecret</password>
     *      </user>
     * </users> }
     *
     * @param u the user whose data we're storing
     * @throws ParserConfigurationException if there is an error while configuring the parser
     * @throws SAXException if a SAX error occurs
     * @throws IOException if an I/O error occurs
     * @throws TransformerConfigurationException if there is an error while configuring the fransformer
     * @throws TransformerException if there is an error while transforming the DOM document
     */
    public void createUserData(User u) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(filePath);
        
        Element root = (Element) doc.getFirstChild();
        
        Element user = doc.createElement("user");
        
        root.appendChild(user);
        
        Element name = doc.createElement("name");
        
        user.appendChild(name);
        
        Element accNum = doc.createElement("accountNumber");
        
        user.appendChild(accNum);
        
        Element balance = doc.createElement("balance");
        
        user.appendChild(balance);
        
        Element pw = doc.createElement("password");

        user.appendChild(pw);
        
        name.appendChild(doc.createTextNode(u.getUserName()));
        accNum.appendChild(doc.createTextNode(Integer.toString(u.getAccountNumber())));
        balance.appendChild(doc.createTextNode(Integer.toString(u.getAccountBalance())));
        pw.appendChild(doc.createTextNode(u.getPassword()));
        
        TransformerFactory trf = TransformerFactory.newInstance();
        Transformer tr = trf.newTransformer();
        
        DOMSource src = new DOMSource(doc);
        StreamResult res = new StreamResult(new File(filePath));
        
        tr.transform(src, res);
        
    }
    
    /**
     * Finds a specific user's {@code Node} in a given {@code NodeList}
     * by the user's account number.
     * @param nl the {@code NodeList} to search in
     * @param an the user's account number we're searching for
     * @return the specific user's {@code Node}
     */
    public Node findUserNode(NodeList nl, String an) {
        
        Node user = null;
        
        for(int i = 0; i < nl.getLength(); i++) {
            
            Node us = nl.item(i);
            
            NodeList nl2 = us.getChildNodes();
            
            for(int j = 0; j < nl2.getLength(); j++) {
                
                Node usc = nl2.item(j);
                
                if("accountNumber".equals(usc.getNodeName())) {
                    
                    if(usc.getTextContent().equals(an)) {
                        
                        user = us;
                        i = nl.getLength() + 99;
                        break;
                    }
                }
            }
        }
        
        return user;
    }
    
    /**
     * When called, this method searches for {@code User u} in
     * the {@code users.xml} and saves the current data of him/her into
     * the file.
     * @param u the user whose data we want to save
     * @throws ParserConfigurationException if there is an error while configuring the parser
     * @throws SAXException if a SAX error occurs
     * @throws IOException if an I/O error occurs
     * @throws TransformerException if there is an error while transforming the DOM document
     */
    public void modifyUserData(User u) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        System.out.println(u);
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(filePath);
        
        String an = Integer.toString(u.getAccountNumber());
        
        Node root = doc.getFirstChild();
        
        NodeList users = doc.getElementsByTagName("user");
        
        Node user = this.findUserNode(users, an);
        
        Node name = user.getChildNodes().item(0);
      //Node accNum = user.getChildNodes().item(1);
        Node balance = user.getChildNodes().item(2);
        Node pw = user.getChildNodes().item(3);
        
        name.setTextContent(u.getUserName());
      //accNum.setTextContent(Integer.toString(u.getAccountNumber()));
        balance.setTextContent(Integer.toString(u.getAccountBalance()));
        pw.setTextContent(u.getPassword());
        
        TransformerFactory trf = TransformerFactory.newInstance();
        Transformer tr = trf.newTransformer();
        
        DOMSource src = new DOMSource(doc);
        StreamResult res = new StreamResult(new File(filePath));
        
        tr.transform(src, res);
    }
    
    /**
     * Finds a user by his/her account number and returns it as
     * a {@code User} object.
     * @param anum the account number of the sought user
     * @return the sought user as a {@code User} object
     * @throws ParserConfigurationException if there is an error while configuring the parser
     * @throws SAXException if a SAX error occurs
     * @throws IOException if an I/O error occurs
     */
    public User findUserByAccountNumber(int anum) throws ParserConfigurationException, SAXException, IOException {
        
        User needed = new User();
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(filePath);
        
        String an = Integer.toString(anum);
        
        Node root = doc.getFirstChild();
        
        NodeList users = doc.getElementsByTagName("user");
        
        Node user = this.findUserNode(users, an);
        
        if(user == null) return null;
        
        Node name = user.getChildNodes().item(0);
        Node accNum = user.getChildNodes().item(1);
        Node balance = user.getChildNodes().item(2);
        Node pw = user.getChildNodes().item(3);
        
        if(user.getLastChild().getNodeName().equals("loan")) {
            
            needed.setLoan(true);
        } else {
            
            needed.setLoan(false);
        }
        
        needed.setUserName(name.getTextContent());
        needed.setAccountNumber(Integer.parseInt(accNum.getTextContent()));
        needed.setAccountBalance(Integer.parseInt(balance.getTextContent()));
        needed.setPassword(pw.getTextContent());
        
        return needed;
    }
    
    /**
     * Returns an {@code ArrayList} containing every user's
     * account number. Used when generating a random account
     * number for a new user, in order to avoid two users having
     * the same number.
     * @return every user's account number in an {@code ArrayList}
     * @throws SAXException if a SAX error occurs
     * @throws IOException if an I/O error occurs
     * @throws ParserConfigurationException if there is an error while configuring the parser
     */
    public ArrayList<String> getAccountNumbers() throws SAXException, IOException, ParserConfigurationException {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(filePath);
        
        Node root = doc.getFirstChild();
        
        ArrayList<String> numbers = new ArrayList<>();
        
        NodeList users = doc.getElementsByTagName("user");
        
        for(int i = 0; i < users.getLength(); i++) {
            
            NodeList un = users.item(i).getChildNodes();
            
            for(int j = 0; j < un.getLength(); j++) {
                
                Node n = un.item(j);
                
                if("accountNumber".equals(n.getNodeName())) {
                    
                    numbers.add(n.getTextContent());
                }
            }
        }
        
        return numbers;
    }
    
    /**
     * Generates a random, 6-digit account number
     * in the range of 100,000 to 999,999 (both inclusive). Also
     * checks the other account numbers to make sure it is unique.
     * @return a unique, random, 6-digit account number
     * @throws SAXException if a SAX error occurs
     * @throws IOException if an I/O error occurs
     * @throws ParserConfigurationException if there is an error while configuring the parser
     */
    public String generateAccountNumber() throws SAXException, IOException, ParserConfigurationException {
        
        String generated;
        
        ArrayList<String> taken = this.getAccountNumbers();
        
        while(true) {
            
            Random ran = new Random();
            
            generated = Integer.toString(ran.nextInt(900000) + 100000);
            
            if(!taken.contains(generated)) break;
            
        }
        
        
        return generated;
    }
    
    /**
     * Assembles the {@code String} containing
     * the account information of {@code User u}.
     * @param u the user who's info is needed
     * @return a {@code String} with the user's information
     */
    public String accountInfo(User u) {
        
        String number = Integer.toString(u.getAccountNumber());
        
        String info = "Account info:\n\nName: " + u.getUserName() + "\nAccount number: " +number;
        
        return info;
    }
    
    /**
     * Assembles the {@code String} containing
     * the info of the user's current account balance.
     * @param u the user who's balance we need
     * @return the balance of {@code User u}
     */
    public String balanceInfo(User u) {
        
        String info = "Your current balance is:\t\t" + String.format("%,d", u.getAccountBalance());
        
        return info;
    }
    
    /**
     * Assembles the {@code String} that tells
     * if {@code User u} is allowed to take a loan.
     * @param u the user in question
     * @return if {@code User u} can take a loan or not
     */
    public String loanInfo(User u) {
        
        String info = "";
        
        if(u.getLoan()) {
            
            info = "You already have an active loan!\n\n You can't take a new loan until your current one is completed.";
        } else {
            
            info = "You currently have no active loans.\n\n Available loan: 50 000\n\n Payment: 5 000 / day";
        }
        
        return info;
    }
    
    /**
     * Assembles the {@code String} about the user's remaining
     * payments if he/she has an active loan. If the user has no
     * active loans, the method will return an empty {@code String}.
     * @param u the user who's remainig payments we need
     * @return {@code User u}'s payments to be payed
     * @throws ParserConfigurationException if there is an error while configuring the parser
     * @throws SAXException if a SAX error occurs
     * @throws IOException if an I/O error occurs
     */
    public String paymentInfo(User u) throws ParserConfigurationException, SAXException, IOException {
        
        String info = "";
        
        if(!u.getLoan()) return info;
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(filePath);
        
        Node root = doc.getFirstChild();
        
        NodeList users = doc.getElementsByTagName("user");
        
        Node user = this.findUserNode(users, Integer.toString(u.getAccountNumber()));
        
        Node loan = user.getLastChild();
        
        Node amountLeft = loan.getLastChild();
        
        int left = Integer.parseInt(amountLeft.getTextContent());
        
        info = "Amount left to pay back:\t\t" + String.format("%,d", left);
        
        return info;
    }
    
    /**
     * Handles a monetary transaction between two users. The {@code amount}
     * gets subtracted from {@code User from}'s balance and gets added to
     * {@code User to}'s balance.
     * @param from the user sending money
     * @param to the user receiving money
     * @param amount the amount transferred
     * @throws ParserConfigurationException if there is an error while configuring the parser
     * @throws SAXException if a SAX error occurs
     * @throws IOException if an I/O error occurs
     * @throws TransformerException if there is an error while transforming the DOM document
     */
    public void processTransaction(User from, User to, String amount) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        int a = Integer.parseInt(amount);
        
        from.setAccountBalance(from.getAccountBalance() - a);
        to.setAccountBalance(to.getAccountBalance() + a);
        
        this.modifyUserData(from);
        this.modifyUserData(to);
    }
    
    /**
     * Opens a new loan for {@code User u}. The default loan is
     * 50,000 which gets added to the user's balance. In the
     * {@code users.xml}, 3 new tags will be created, so the
     * user's data will look like this:
     * {@code 
     * 
     * <users>
     *      <user>
     *          <name>Username</name>
     *          <accountNumber>000000</name>
     *          <balance>420</balance>
     *          <password>sosecret</password>
     *          <loan>
     *              <lastChecked>2000-01-01</lastChecked>
     *              <amountLeft>50000</amountLeft>
     *          </loan>
     *      </user>
     * </users> }
     * 
     * where {@code lastChecked} is the date of the last time a payment was
     * done, and {@code amountLeft} is the overall amount left to pay.
     * @param u the user who takes a loan
     * @return the loan amount
     * @throws ParserConfigurationException if there is an error while configuring the parser
     * @throws SAXException if a SAX error occurs
     * @throws IOException if an I/O error occurs
     * @throws TransformerException if there is an error while transforming the DOM document
     */
    public int openLoan(User u) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(filePath);
        
        Node root = doc.getFirstChild();
        
        NodeList users = doc.getElementsByTagName("user");
        
        Node user = this.findUserNode(users, Integer.toString(u.getAccountNumber()));
        
        Element loan = doc.createElement("loan");
        
        user.appendChild(loan);
        
        Element lastChecked = doc.createElement("lastChecked");
        
        loan.appendChild(lastChecked);
        
        Element amountLeft = doc.createElement("amountLeft");
        
        loan.appendChild(amountLeft);
        
        lastChecked.appendChild(doc.createTextNode(LocalDate.now().toString()));
        amountLeft.appendChild(doc.createTextNode("50000"));
        
        Node balance = user.getChildNodes().item(2);
        
        balance.setTextContent(Integer.toString(Integer.parseInt(balance.getTextContent()) + 50000));
        
        TransformerFactory trf = TransformerFactory.newInstance();
        Transformer tr = trf.newTransformer();
        
        DOMSource src = new DOMSource(doc);
        StreamResult res = new StreamResult(new File(filePath));
        
        tr.transform(src, res);
        
        return 50000;
    }
    
    /**
     * This method checks the number of days that have passed sinced
     * the {@code lastChecked} date and subtracts the amount of payment
     * for those days (number of days * 5000). If the date hasn't changed,
     * the balance is left unchanged.
     * @param u the user who's loan is being processed
     * @param currentBalance the user's account balance before processing
     * @return the user's new account balance
     * @throws SAXException if a SAX error occurs
     * @throws ParserConfigurationException if there is an error while configuring the parser
     * @throws IOException if an I/O error occurs
     * @throws TransformerException if there is an error while transforming the DOM document
     */
    public int processLoan(User u, int currentBalance) throws SAXException, ParserConfigurationException, IOException, TransformerException {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(filePath);
        
        Node root = doc.getFirstChild();
        
        NodeList users = doc.getElementsByTagName("user");
        
        Node user = this.findUserNode(users, Integer.toString(u.getAccountNumber()));
        
        Node balance = user.getChildNodes().item(2);
        
        Node loan = user.getLastChild();
        
        Node lastChecked = loan.getFirstChild();
        
        Node amountLeft = loan.getLastChild();
        
        LocalDate lc = LocalDate.parse(lastChecked.getTextContent());
        
        LocalDate today = LocalDate.now();
        
        if(ChronoUnit.DAYS.between(lc, today) != 0) {
        
            currentBalance -= ChronoUnit.DAYS.between(lc, today) * 5000;
            balance.setTextContent(Integer.toString(Integer.parseInt(balance.getTextContent()) - 5000));
            lastChecked.setTextContent(LocalDate.now().toString());
            amountLeft.setTextContent(Integer.toString(Integer.parseInt(amountLeft.getTextContent()) - 5000 * (int)ChronoUnit.DAYS.between(lc, today)));
            
            TransformerFactory trf = TransformerFactory.newInstance();
            Transformer tr = trf.newTransformer();
        
            DOMSource src = new DOMSource(doc);
            StreamResult res = new StreamResult(new File(filePath));
        
            tr.transform(src, res);
            
            return currentBalance;
        }
        
        return currentBalance;
    }
    
    /**
     * Checks wether or not the user's loan has been settled. If the
     * {@code amountLeft} tag in the {@code users.xml} is 0, then the
     * loan is considered settled, and the {@code <loan>} tag gets
     * deleted together with it's children.
     * @param u the user who's loan's state is checked
     * @return {@code false} if the loan is settled ({@code u.hasLoan will be false}), {@code true} otherwise
     * @throws ParserConfigurationException if there is an error while configuring the parser
     * @throws SAXException if a SAX error occurs
     * @throws IOException if an I/O error occurs
     * @throws TransformerConfigurationException if there is an error while configuring the fransformer
     * @throws TransformerException if there is an error while transforming the DOM document
     */
    public boolean isLoanCompleted(User u) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(filePath);
        
        Node root = doc.getFirstChild();
        
        NodeList users = doc.getElementsByTagName("user");
        
        Node user = this.findUserNode(users, Integer.toString(u.getAccountNumber()));
        
        if(user.getLastChild().getLastChild().getTextContent().equals("0")) {
            
            user.removeChild(user.getLastChild());
            
            TransformerFactory trf = TransformerFactory.newInstance();
            Transformer tr = trf.newTransformer();

            DOMSource src = new DOMSource(doc);
            StreamResult res = new StreamResult(new File(filePath));

            tr.transform(src, res);
            
            return false;
        }
        
        return true;
    }
    
    /**
     * Performs a password change on {@code User u}'s account.
     * @param u the user who's password is changed
     * @param newpass the new password
     * @throws ParserConfigurationException if there is an error while configuring the parser
     * @throws SAXException if a SAX error occurs
     * @throws IOException if an I/O error occurs
     * @throws TransformerException if there is an error while transforming the DOM document
     */
    public void changePassword(User u, String newpass) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        u.setPassword(newpass);
        
        this.modifyUserData(u);
    }
   
}
