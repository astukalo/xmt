package xyz.a5s7.xmt;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.Dom4JReader;

import java.util.Objects;

public class NeedMigrationConverter implements Converter {
    private final XStream xstream;
    private final MigrationListener listener;

    public NeedMigrationConverter(final XStream xstream) {
        this(xstream, null);
    }

    public NeedMigrationConverter(final XStream xstream, MigrationListener listener) {
        Objects.requireNonNull(xstream);
        this.xstream = xstream;
        this.listener = listener;
    }

    @Override
    public void marshal(final Object o, final HierarchicalStreamWriter hierarchicalStreamWriter, final MarshallingContext marshallingContext) {
        String version = MigrationHelper.getVersion(o.getClass());

        Converter converter = getBaseConverter();
        marshallingContext.convertAnother(o, converter);
        hierarchicalStreamWriter.addAttribute("version", version);
    }

    @Override
    public Object unmarshal(final HierarchicalStreamReader hierarchicalStreamReader, final UnmarshallingContext unmarshallingContext) {
        String version = hierarchicalStreamReader.getAttribute("version");
        if (version == null || version.trim().equals("")) {
            version = "0";
        }
        Object o1;
        Object instance;
        Class<?> beanClass = unmarshallingContext.getRequiredType();
        try {
            instance = beanClass.newInstance();
            boolean migrated = MigrationHelper.migrate(version, beanClass, ((Dom4JReader) hierarchicalStreamReader.underlyingReader()).getCurrent());

            o1 = unmarshallingContext.convertAnother(instance, beanClass,
                    getBaseConverter()
            );
            if (migrated && listener != null) {
                listener.migrated(o1);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("No-args constructor for class " + beanClass.getName() + " is not accessible", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("No-args constructor for class " + beanClass.getName() + " doesn't exist or class is abstract", e);
        }

        return o1;
    }

    private Converter getBaseConverter() {
        return xstream.getConverterLookup().lookupConverterForType(Object.class);
    }

    @Override
    public boolean canConvert(final Class aClass) {
        return aClass.getAnnotation(NeedMigration.class) != null;
    }
}
