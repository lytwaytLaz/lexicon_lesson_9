package lesson_9_2;

import java.sql.*;

/**
 * @author László Hágó
 * @version 1.0
 * @since 2016-06-22
 */
public class SearchDB {
    public static void main(String[] args) throws SQLException {
        if (args.length != 1)
        {
            System.err.println("usage: java SearchDB <word>");
            System.exit(1);
        }
        String searchKey = args[0];
        String url = "jdbc:derby:thesaurus";
        try (Connection con = DriverManager.getConnection(url))
        {
            try (Statement stmt = con.createStatement())
            {
                ResultSet rs = stmt.executeQuery("SELECT * FROM THESAURUS WHERE SEARCHKEY = '" + searchKey + "'");
//                System.out.println("SELECT * FROM THESAURUS WHERE SEARCHKEY = '" + searchKey + "'");
                rs.next();
                System.out.println("Synonymer till " + rs.getString("SEARCHKEY") + ":\n" + rs.getString("SYNONYMS") );
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
