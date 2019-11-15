
import java.io.*;
import java.io.IOException;
import java.net.Socket;

import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.util.IteratorIterable;
import org.omg.CORBA.portable.OutputStream;
 
public class JdomUsingSAXBuilder {
 
    private static String file1 = "/home/grads/raheela.afzal1/eclipse/Proxy/Movie.xml";
 
    public static void main(String[] args) throws JDOMException, IOException {
 
        // Use a SAX builder
        SAXBuilder builder = new SAXBuilder();
        // build a JDOM2 Document using the SAXBuilder.
        Document jdomDoc = builder.build(new File(file1));
		
        // get the document type
        System.out.println(jdomDoc.getDocType());
 
        //get the root element
        Element object = jdomDoc.getRootElement();
        System.out.println(object.getName());
         
        // get the first child with the name 'servlet'
        Element ele = object.getChild("Movie");
 
        // iterate through the descendants and print non-Text and non-Comment values
        IteratorIterable<Content> contents = object.getDescendants();
        while (contents.hasNext()) {
            Content content = contents.next();
            if (!content.getCType().equals(CType.Text) && !content.getCType().equals(CType.Comment)) {
                System.out.println("     " + content.toString() + "     Value: " +content.getValue());
            }

        }
 
        // get comments using a Comment filter
        IteratorIterable<Comment> comments = object.getDescendants(Filters.comment());
        while (comments.hasNext()) {
            Comment comment = comments.next();
            System.out.println(comment);
        }
 
    }
}