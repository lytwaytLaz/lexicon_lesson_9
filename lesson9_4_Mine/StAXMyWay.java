package lesson9_4_Mine;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author László Hágó
 * @version 1.0
 * @since 2016-07-02
 */
public class StAXMyWay
{

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException
    {
        String level = null;
        String key = null;
        String synonyms;

        String url = "jdbc:derby:thesaurusXML;create=true";
        try (Connection con = DriverManager.getConnection(url);
             Statement stmt = con.createStatement())
        {
            String sql = "CREATE TABLE THESAURUSXML(LEVEL VARCHAR(3), SEARCHKEY VARCHAR(40), SYNONYMS VARCHAR(40))";
            stmt.executeUpdate(sql);

            XMLInputFactory factory = XMLInputFactory.newFactory();
            XMLStreamReader streamReader = factory.createXMLStreamReader(new FileInputStream("thesaurus-sv.xml"));

            while (streamReader.hasNext())
            {
                streamReader.next();
                if (streamReader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    String eventName = streamReader.getLocalName();
                    if ("syn".equals(eventName))
                    {
                        level = streamReader.getAttributeValue(null, "level").trim();
                    } else if ("w1".equals(eventName))
                    {
                        streamReader.next();
                        key = streamReader.getText().trim();
                    } else if ("w2".equals(eventName))
                    {
                        streamReader.next();
                        synonyms = streamReader.getText().trim();
                        sql = "INSERT INTO THESAURUSXML VALUES('" + level + "','" + key + "','" + synonyms +
                                "')";
                        stmt.executeUpdate(sql);
                    }
                }
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (XMLStreamException e)
        {
            e.printStackTrace();
        } catch (SQLException sqlex)
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
}
