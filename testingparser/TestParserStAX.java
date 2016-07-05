package testingparser;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author László Hágó
 * @version 1.0
 * @since 2016-07-05
 */
public class TestParserStAX
{
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, XMLStreamException
    {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLEventReader eventReader = factory.createXMLEventReader(new InputStreamReader((new FileInputStream("thesaurus-cut-sv.xml")), "ISO-8859-1"));
        XMLStreamReader streamReader = factory.createXMLStreamReader(new InputStreamReader((new FileInputStream("thesaurus-cut-sv.xml")), "ISO-8859-1"));

        while (eventReader.hasNext())
        {
            Object event = eventReader.next();
//            System.out.println(event);
            System.out.print(event + " ");
            System.out.print(streamReader.next() + " ");
//            if (eventReader)
//            System.out.println(streamReader.getAttributeName(0));
////            System.out.println(eventReader.nextEvent());
        }
    }
}
