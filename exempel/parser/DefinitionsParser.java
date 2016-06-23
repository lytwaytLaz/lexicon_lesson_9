package exempel.parser;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
/**
 * @author László Hágó
 * @version 1.0
 * @since 2016-06-23
 */


public interface DefinitionsParser extends Function<Path, Map<String, Set<String>>> {

    Map<String, Set<String>> apply(final Path definitions);
}
