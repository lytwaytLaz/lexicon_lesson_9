package lesson_9_4;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * @author László Hágó
 * @version 1.0
 * @since 2016-06-26
 */
class StAXParser
{
    boolean key = false;
    boolean synonym = false;
    String level;
    String searchKey;
    String synonymValue;

    public StAXParser()
    {
        String url = "jdbc:derby:thesaurusXML;create=true";
        try (Connection con = DriverManager.getConnection(url);
             Statement stmt = con.createStatement())
        {
            String sql = "CREATE TABLE THESAURUSXML(LEVEL VARCHAR(8), SEARCHKEY VARCHAR(40), SYNONYMS VARCHAR(10000))";
            stmt.executeUpdate(sql);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader("thesaurus-sv.xml"));

            while (eventReader.hasNext())
            {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType())
                {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("syn"))
                        {
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            level = attributes.next().getValue();
                            System.out.print(level);
                        } else if (qName.equalsIgnoreCase("w1"))
                        {
                            key = true;
                        } else if (qName.equalsIgnoreCase("w2"))
                        {
                            synonym = true;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (key)
                        {
                            searchKey = characters.getData();
                            key = false;
                            System.out.print(": " + key);
                        } else if (synonym)
                        {
                            synonymValue = characters.getData();
                            sql = "INSERT INTO THESAURUSXML VALUES('" + level + "','" + searchKey + "','" +
                                    synonymValue +
                                    "')";
                            stmt.executeUpdate(sql);
                            synonym = false;
                            System.out.println(" " + synonymValue);
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equalsIgnoreCase("syn"))
                        {
                        }
                        break;


                }
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (XMLStreamException e)
        {
            e.printStackTrace();
        }
        catch (SQLException sqlex)
        {
            while (sqlex != null)
            {
                System.err.println("SQL error: " + sqlex.getMessage());
                System.err.println("SQL state: " + sqlex.getSQLState());
                System.err.println("Error code. " + sqlex.getErrorCode());
                System.err.println("Cause: " + sqlex.getCause());
                sqlex = sqlex.getNextException();
            }
        }
    }




    public static void main(String[] args)
    {
        StAXParser parser = new StAXParser();
    }
}

