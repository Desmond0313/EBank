/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author takacsd
 */
public class UserManager {
    
    public String filePath = System.getProperty("user.home") + "/users/users.xml";
    
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
        
        needed.setUserName(name.getTextContent());
        needed.setAccountNumber(Integer.parseInt(accNum.getTextContent()));
        needed.setAccountBalance(Integer.parseInt(balance.getTextContent()));
        needed.setPassword(pw.getTextContent());
        
        return needed;
    }
   
}
