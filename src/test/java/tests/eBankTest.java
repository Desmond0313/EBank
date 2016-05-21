/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import model.UserManager;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Takács Ferenc Dániel
 */
public class eBankTest {
    
    private static UserManager um = new UserManager();
    private User u = new User("Teszt Előd", "titkos", 123456, 1000, false);
    
    public eBankTest() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        
        um.setFilePath(System.getProperty("user.home") + "/.users/test/users.xml");
        um.createXML();
        um.createUserData(u);
    }
    
    @Test
    public void testXMLCheck() {
        
        System.out.println(um.getFilePath());
        File f = new File(um.getFilePath());
        
        if(f.exists()) {
            
            assertEquals(true, um.checkForXML());
        } else {
            
            assertEquals(false, um.checkForXML());
        }
    }
    
    @Test
    public void testNodeFinder() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(um.getFilePath());
        
        Node root = doc.getFirstChild();
        
        NodeList users = doc.getElementsByTagName("user");
        
        String acc1 = "654321";
        String acc2 = Integer.toString(u.getAccountNumber());
        
        Node user = users.item(0);
        
        assertEquals(null, um.findUserNode(users, acc1));
        assertEquals(user, um.findUserNode(users, acc2));
    }
    
    @Test
    public void testUserFinder() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        int acc = u.getAccountNumber();
        
        assertEquals(u, um.findUserByAccountNumber(acc));
    }
    
    @Test
    public void testNumberList() throws SAXException, IOException, ParserConfigurationException, TransformerException {
        
        ArrayList<String> numbers = new ArrayList<>();
        
        numbers.add(Integer.toString(u.getAccountNumber()));
        
        assertEquals(numbers, um.getAccountNumbers());
    }
    
    @Test
    public void testAccountInfo() {
        
        String info = "Account info:\n\nName: " + u.getUserName() + "\nAccount number: " + u.getAccountNumber();
        
        assertEquals(info, um.accountInfo(u));
    }
    
    @Test
    public void testBalanceInfo() {
        
        String info = "Your current balance is:\t\t" + String.format("%,d", u.getAccountBalance());
        
        assertEquals(info, um.balanceInfo(u));
    }
    
    @Test
    public void testLoanInfo() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        String info1 = "You already have an active loan!\n\n You can't take a new loan until your current one is completed.";
        String info2 = "You currently have no active loans.\n\n Available loan: 50 000\n\n Payment: 5 000 / day";
        
        assertEquals(info2, um.loanInfo(u));
        u.setLoan(true);
        assertEquals(info1, um.loanInfo(u));
        u.setLoan(false);
    }
    
    @Test
    public void testPaymentInfo() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        assertEquals("", um.paymentInfo(u));
        u.setLoan(true);
        um.openLoan(u);
        String info = "Amount left to pay back:\t\t" + String.format("%,d", 50000);
        assertEquals(info, um.paymentInfo(u));
    }
    
    @Test
    public void testOpenLoan() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        assertEquals(50000, um.openLoan(u));
    }
    
    @Test
    public void testProcessLoan() throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        
        um.openLoan(u);
        
        assertEquals(u.getAccountBalance(), um.processLoan(u, u.getAccountBalance()));
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(um.getFilePath());
        
        Node root = doc.getFirstChild();
        
        NodeList users = doc.getElementsByTagName("user");
        
        Node user = um.findUserNode(users, Integer.toString(u.getAccountNumber()));
        
        LocalDate dummy = LocalDate.now().minusDays(2);
        
        user.getLastChild().getFirstChild().setTextContent(dummy.toString());
        
        TransformerFactory trf = TransformerFactory.newInstance();
        Transformer tr = trf.newTransformer();
        
        DOMSource src = new DOMSource(doc);
        StreamResult res = new StreamResult(new File(um.getFilePath()));
        
        tr.transform(src, res);
        
        assertEquals(u.getAccountBalance() - 10000, um.processLoan(u, u.getAccountBalance()));
    }
    
    @Test
    public void testLoanComplete() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        um.openLoan(u);
        
        assertEquals(true, um.isLoanCompleted(u));
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(um.getFilePath());
        
        Node root = doc.getFirstChild();
        
        NodeList users = doc.getElementsByTagName("user");
        
        Node user = um.findUserNode(users, Integer.toString(u.getAccountNumber()));
        
        Node left = user.getLastChild().getLastChild();
        
        left.setTextContent("0");
        
        TransformerFactory trf = TransformerFactory.newInstance();
        Transformer tr = trf.newTransformer();
        
        DOMSource src = new DOMSource(doc);
        StreamResult res = new StreamResult(new File(um.getFilePath()));
        
        tr.transform(src, res);
        
        assertEquals(false, um.isLoanCompleted(u));
    }
    
    
}
