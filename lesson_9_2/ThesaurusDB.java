package lesson_9_2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/**
 * @author László Hágó
 * @version 1.0
 * @since 2016-06-22
 */
public class ThesaurusDB
{
    public static void main(String[] args) throws IOException {
        String url = "jdbc:derby:thesaurus;create=true";

        try (Connection con = DriverManager.getConnection(url))
        {
            try (Statement stmt = con.createStatement())
            {
                String sql = "CREATE TABLE THESAURUS(SEARCHKEY VARCHAR(40), SYNONYMS VARCHAR(1000))";
                stmt.executeUpdate(sql);

                for (String line : Files.readAllLines(Paths.get("thesaurus-sv_utf8.txt")))
                {
                    String searchkey = line.substring(0, line.indexOf(":"));
                    String synonyms = line.substring(line.indexOf(":") + 1);
                    sql = "INSERT INTO THESAURUS VALUES('" + searchkey + "','" + synonyms + "')";
                    stmt.executeUpdate(sql);
                }
            }
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
}
