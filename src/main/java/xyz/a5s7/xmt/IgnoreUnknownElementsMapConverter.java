package xyz.a5s7.xmt;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Map converter that ignores unknown classes.
 *
 * For example, it ignores obsolete view classes which were removed in a new version.
 */
public class IgnoreUnknownElementsMapConverter extends MapConverter {
    private static final Logger logger = Logger.getLogger(IgnoreUnknownElementsMapConverter.class.getCanonicalName());

    public IgnoreUnknownElementsMapConverter(final XStream xstream) {
        super(xstream.getMapper());
    }

    @Override
    protected void putCurrentEntryIntoMap(final HierarchicalStreamReader reader, final UnmarshallingContext context, final Map map, final Map target) {
        reader.moveDown();
        try {
            Object key = this.readItem(reader, context, map);
            reader.moveUp();
            reader.moveDown();
            Object value = this.readItem(reader, context, map);
            target.put(key, value);
        } catch (CannotResolveClassException e) {
            logger.info("Probably, some classes were removed " + e.getMessage());
        } finally {
            reader.moveUp();
        }
    }
}
