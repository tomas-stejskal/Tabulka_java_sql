/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabulka;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author tomi
 */
public class SaveToXml {
    
    
    public void save(Object[][] data){
        
        try{
            DocumentBuilderFactory docFactory =  DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("schedule");
            doc.appendChild(root);
            
            for (int i=0;i<data.length;i++){
                Element record = doc.createElement("record");
                
                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(data[i][0].toString()));
                record.appendChild(name);
                
                Element description = doc.createElement("description");
                description.appendChild(doc.createTextNode((String) data[i][1]));
                record.appendChild(description);
                
                Element date = doc.createElement("date");
                date.appendChild(doc.createTextNode(data[i][2].toString()));
                record.appendChild(date);
                
                root.appendChild(record); 
            }
            
            TransformerFactory transFact = TransformerFactory.newInstance();
            Transformer trans = transFact.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("schedule.xml"));
            trans.transform(source, result);
             
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
}
