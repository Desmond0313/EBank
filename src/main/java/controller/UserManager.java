/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
 *
 * @author takacsd
 */
public class UserManager {
    
    private String filePath = System.getProperty("user.home") + "/.users/users.xml";
    private int defaultBalance = 30000;
    
    public String getFilePath() {
        
        return this.filePath;
    }
    
    public int getDefaultBalance() {
        
        return this.defaultBalance;
    }
    
    public void setFilePath(String filePath) {
        
        this.filePath = filePath;
    }
    
    public void setDefaultBalance(int defaultBalance) {
        
        this.defaultBalance = defaultBalance;
    }
    
    public boolean checkForXML() {
        
        File checker = new File(filePath);
                
        return checker.exists();
    }
    
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
    
    public String accountInfo(User u) {
        
        String info = "";
        
        String number = Integer.toString(u.getAccountNumber());
        
        info = "Account info:\n\nName: " + u.getUserName() + "\nAccount number: " +
                number;
        
        return info;
    }
    
    public String balanceInfo(User u) {
        
        String info = "";
        
        info = "Your current balance is:\t\t" + String.format("%,d", u.getAccountBalance());
        
        return info;
    }
    
    public String loanInfo(User u) {
        
        String info = "";
        
        if(u.getLoan()) {
            
            info = "You already have an active loan!\n\n You can't take a new loan until your current one is completed.";
        } else {
            
            info = "You currently have no active loans.\n\n Available loan: 50 000\n\n Payment: 5 000 / day";
        }
        
        return info;
    }
    
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
        
        info = "Amount left to pay back: " + String.format("%,d", left);
        
        return info;
    }
    
    public void processTransaction(User from, User to, String amount) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        int a = Integer.parseInt(amount);
        
        from.setAccountBalance(from.getAccountBalance() - a);
        to.setAccountBalance(to.getAccountBalance() + a);
        
        this.modifyUserData(from);
        this.modifyUserData(to);
    }
    
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
    
    public boolean isLoanCompleted(User u) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(filePath);
        
        Node root = doc.getFirstChild();
        
        NodeList users = doc.getElementsByTagName("user");
        
        Node user = this.findUserNode(users, Integer.toString(u.getAccountNumber()));
        
        if(user.getLastChild().getLastChild().getTextContent().equals("0")) {
            
            doc.removeChild(user.getLastChild());
            
            TransformerFactory trf = TransformerFactory.newInstance();
            Transformer tr = trf.newTransformer();

            DOMSource src = new DOMSource(doc);
            StreamResult res = new StreamResult(new File(filePath));

            tr.transform(src, res);
            
            return false;
        }
        
        return true;
    }
    
    public void changePassword(User u, String newpass) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        u.setPassword(newpass);
        
        this.modifyUserData(u);
    }
   
}
