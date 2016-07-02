package lesson_9_4;

import java.sql.*;

/**
 * @author László Hágó
 * @version 1.0
 * @since 2016-06-27
 */
public class SearchXMLDB {
    public static void main(String[] args) {
        if (args.length != 1)
        {
            System.err.println("usage: java SearchXMLDB <word>");
            System.exit(1);
        }
        String searchKey = args[0];
        String url = "jdbc:derby:thesaurusXML";
        try (Connection con = DriverManager.getConnection(url))
        {
            try (Statement stmt = con.createStatement())
            {
                ResultSet rs = stmt.executeQuery("SELECT * FROM THESAURUSXML WHERE SEARCHKEY = '" + searchKey + "' ORDER BY LEVEL DESC");
//                System.out.println("SELECT * FROM THESAURUSXML WHERE SEARCHKEY = '" + searchKey + "' ORDER BY LEVEL DESC");
                System.out.println("Synonymer i betydelseordning till " + searchKey + ":");
                while (rs.next())
                {
                    System.out.print(rs.getString("SYNONYMS") + ", ");
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
