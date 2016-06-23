import exempel.parser.XMLParser;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

/**
 * @author László Hágó
 * @version 1.0
 * @since 2016-06-23
 */
public class ThesaurusXML {
    public static void main(String[] args) {
        XMLParser parser = new XMLParser();
        Map<String, Set<String>> map = parser.apply(Paths.get("thesaurus-sv.xml"));
//        System.out.println(map.toString());
    }
}
