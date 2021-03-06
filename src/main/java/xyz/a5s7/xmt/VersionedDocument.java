package xyz.a5s7.xmt;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.HierarchicalStreams;
import com.thoughtworks.xstream.io.xml.Dom4JReader;
import com.thoughtworks.xstream.io.xml.Dom4JWriter;
import org.dom4j.Branch;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.InvalidXPathException;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.QName;
import org.dom4j.Visitor;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.EntityResolver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class is the bridge between bean and XML. It implements dom4j Documentation interface 
 * and can be operated with dom4j API. It also implements Serializable interface and can be 
 * serialized/deserialized in XML form.
 * @author robin
 *
 */
@SuppressWarnings("unchecked")
public final class VersionedDocument implements Document, Serializable {

	private static final long serialVersionUID = 1L;

	private static final SAXReader reader = new SAXReader();

	private static XStream defaultXstream = new XStream();
	
	private transient String xml;
	
	private transient Document wrapped;
	
	static {
		reader.setStripWhitespaceText(true);
		reader.setMergeAdjacentText(true);

		defaultXstream.registerConverter(new NeedMigrationConverter(defaultXstream));
		defaultXstream.registerConverter(new IgnoreUnknownElementsMapConverter(defaultXstream));
	}

	public VersionedDocument() {
		this.wrapped = DocumentHelper.createDocument();
	}
	
	public VersionedDocument(Document wrapped) {
		this.wrapped = wrapped;
	}
	
	public void setWrapped(Document wrapped) {
		this.wrapped = wrapped;
		this.xml = null;
	}
	
	public VersionedDocument(Element wrapped) {
		wrapped.detach();
		this.wrapped = DocumentHelper.createDocument(wrapped);
	}
	
	public void setWrapped(Element wrapped) {
		wrapped.detach();
		this.wrapped = DocumentHelper.createDocument(wrapped);
		this.xml = null;
	}
	
	public Document addComment(String comment) {
		return getWrapped().addComment(comment);
	}

	public Document addDocType(String name, String publicId, String systemId) {
		return getWrapped().addDocType(name, publicId, systemId);
	}

	public Document addProcessingInstruction(String target, String text) {
		return getWrapped().addProcessingInstruction(target, text);
	}

	public Document addProcessingInstruction(String target, Map data) {
		return getWrapped().addProcessingInstruction(target, data);
	}

	public DocumentType getDocType() {
		return getWrapped().getDocType();
	}

	public EntityResolver getEntityResolver() {
		return getWrapped().getEntityResolver();
	}

	public Element getRootElement() {
		return getWrapped().getRootElement();
	}

	public String getXMLEncoding() {
		return getWrapped().getXMLEncoding();
	}

	public void setDocType(DocumentType docType) {
		getWrapped().setDocType(docType);
	}

	public void setEntityResolver(EntityResolver entityResolver) {
		getWrapped().setEntityResolver(entityResolver);
	}

	public void setRootElement(Element rootElement) {
		getWrapped().setRootElement(rootElement);
	}

	public void setXMLEncoding(String encoding) {
		getWrapped().setXMLEncoding(encoding);
	}

	public void add(Node node) {
		getWrapped().add(node);
	}

	public void add(Comment comment) {
		getWrapped().add(comment);
	}

	public void add(Element element) {
		getWrapped().add(element);
	}

	public void add(ProcessingInstruction pi) {
		getWrapped().add(pi);
	}

	public Element addElement(String name) {
		return getWrapped().addElement(name);
	}

	public Element addElement(QName qname) {
		return getWrapped().addElement(qname);
	}

	public Element addElement(String qualifiedName, String namespaceURI) {
		return getWrapped().addElement(qualifiedName, namespaceURI);
	}

	public void appendContent(Branch branch) {
		getWrapped().appendContent(branch);
	}

	public void clearContent() {
		getWrapped().clearContent();
	}

	public List content() {
		return getWrapped().content();
	}

	public Element elementByID(String elementID) {
		return getWrapped().elementByID(elementID);
	}

	public int indexOf(Node node) {
		return getWrapped().indexOf(node);
	}

	public Node node(int index) throws IndexOutOfBoundsException {
		return getWrapped().node(index);
	}

	public int nodeCount() {
		return getWrapped().nodeCount();
	}

	public Iterator nodeIterator() {
		return getWrapped().nodeIterator();
	}

	public void normalize() {
		getWrapped().normalize();
	}

	public ProcessingInstruction processingInstruction(String target) {
		return getWrapped().processingInstruction(target);
	}

	public List processingInstructions() {
		return getWrapped().processingInstructions();
	}

	public List processingInstructions(String target) {
		return getWrapped().processingInstructions(target);
	}

	public boolean remove(Node node) {
		return getWrapped().remove(node);
	}

	public boolean remove(Comment comment) {
		return getWrapped().remove(comment);
	}

	public boolean remove(Element element) {
		return getWrapped().remove(element);
	}

	public boolean remove(ProcessingInstruction pi) {
		return getWrapped().remove(pi);
	}

	public boolean removeProcessingInstruction(String target) {
		return getWrapped().removeProcessingInstruction(target);
	}

	public void setContent(List content) {
		getWrapped().setContent(content);
	}

	public void setProcessingInstructions(List listOfPIs) {
		getWrapped().setProcessingInstructions(listOfPIs);
	}

	public void accept(Visitor visitor) {
		getWrapped().accept(visitor);
	}

	public String asXML() {
		return toXML();
	}

	public Node asXPathResult(Element parent) {
		return getWrapped().asXPathResult(parent);
	}

	public XPath createXPath(String xpathExpression)
			throws InvalidXPathException {
		return getWrapped().createXPath(xpathExpression);
	}

	public Node detach() {
		return getWrapped().detach();
	}

	public Document getDocument() {
		return getWrapped().getDocument();
	}

	public String getName() {
		return getWrapped().getName();
	}

	public short getNodeType() {
		return getWrapped().getNodeType();
	}

	public String getNodeTypeName() {
		return getWrapped().getNodeTypeName();
	}

	public Element getParent() {
		return getWrapped().getParent();
	}

	public String getPath() {
		return getWrapped().getPath();
	}

	public String getPath(Element context) {
		return getWrapped().getPath(context);
	}

	public String getStringValue() {
		return getWrapped().getStringValue();
	}

	public String getText() {
		return getWrapped().getText();
	}

	public String getUniquePath() {
		return getWrapped().getUniquePath();
	}

	public String getUniquePath(Element context) {
		return getWrapped().getUniquePath(context);
	}

	public boolean hasContent() {
		return getWrapped().hasContent();
	}

	public boolean isReadOnly() {
		return getWrapped().isReadOnly();
	}

	public boolean matches(String xpathExpression) {
		return getWrapped().matches(xpathExpression);
	}

	public Number numberValueOf(String xpathExpression) {
		return getWrapped().numberValueOf(xpathExpression);
	}

	public List selectNodes(String xpathExpression) {
		return getWrapped().selectNodes(xpathExpression);
	}

	public List selectNodes(String xpathExpression,
                            String comparisonXPathExpression) {
		return getWrapped().selectNodes(xpathExpression, comparisonXPathExpression);
	}

	public List selectNodes(String xpathExpression,
                            String comparisonXPathExpression, boolean removeDuplicates) {
		return getWrapped().selectNodes(xpathExpression, comparisonXPathExpression, 
				removeDuplicates);
	}

	public Object selectObject(String xpathExpression) {
		return getWrapped().selectObject(xpathExpression);
	}

	public Node selectSingleNode(String xpathExpression) {
		return getWrapped().selectSingleNode(xpathExpression);
	}

	public void setDocument(Document document) {
		getWrapped().setDocument(document);
	}

	public void setName(String name) {
		getWrapped().setName(name);
	}

	public void setParent(Element parent) {
		getWrapped().setParent(parent);
	}

	public void setText(String text) {
		getWrapped().setText(text);
	}

	public boolean supportsParent() {
		return getWrapped().supportsParent();
	}

	public String valueOf(String xpathExpression) {
		return getWrapped().valueOf(xpathExpression);
	}

	public void write(Writer writer) throws IOException {
		getWrapped().write(writer);
	}
	
	/**
	 * Clone this versioned document as another versioned document.
	 */
    public Object clone() {
    	return new VersionedDocument((Document) getWrapped().clone());
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
    	oos.defaultWriteObject();
    	if (xml != null)
    		oos.writeObject(xml);
    	else
    		oos.writeObject(toXML());
    }

    private void readObject(ObjectInputStream ois)
    		throws ClassNotFoundException, IOException {
    	ois.defaultReadObject();
    	xml = (String) ois.readObject();
    }
    
    /**
     * Convert the versioned document to UTF8 encoded XML.
     * @return XML as string
     */
	public String toXML() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputFormat format = new OutputFormat();
		format.setEncoding("UTF8");
		format.setIndent(true);
        format.setNewlines(true);
		try {
			new XMLWriter(baos, format).write(getWrapped());
			return baos.toString("UTF8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Construct the document from a XML text.
	 * @param xml UTF8 encoded XML text
	 * @return dom document
	 */
	public static VersionedDocument fromXML(String xml) {
		synchronized (reader) {
			try {
				return new VersionedDocument(reader.read(new ByteArrayInputStream(xml.getBytes("UTF8"))));
			} catch (IOException | DocumentException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private Document getWrapped() {
		if (wrapped == null) {
			if (xml != null) {
				wrapped = fromXML(xml).getWrapped();
				xml = null;
			}
		}
		return wrapped;
	}
	
	/**
	 * Construct the versioned document from specified bean object.
	 * @param bean specified object
	 * @return versioned document
	 */
	public static VersionedDocument fromBean(Object bean) {
		Document dom = DocumentHelper.createDocument();
		defaultXstream.marshal(bean, new Dom4JWriter(dom));

		return  new VersionedDocument(dom);
	}

	/**
	 * Convert this document to bean. Migration will performed if necessary.
	 * During the migration, content of the document will also get updated 
	 * to reflect current migration result.
	 * @return bean deserialized from xml and migrated
	 */
	public Object toBean() {
		return toBean(null, null);
	}
	
	/**
	 * Convert this document to bean. Migration will performed if necessary.
	 * During the migration, content of the document will also get updated 
	 * to reflect current migration result.
	 * @param listener the migration listener to receive migration events. Set to 
	 * null if you do not want to receive migration events.
	 * @return object deserialized from document
	 */
	public Object toBean(MigrationListener listener) {
		return toBean(listener, null);
	}

	/**
	 * Convert this document to bean. Migration will performed if necessary.
	 * During the migration, content of the document will also get updated 
	 * to reflect current migration result.
	 * @param beanClass class of the bean. Class information in current document 
	 * will be used if this param is set to null
	 * @return object deserialized from document
	 */
	public Object toBean(Class<?> beanClass) {
		return toBean(null, beanClass);
	}

	/**
	 * Convert this document to bean. Migration will performed if necessary.
	 * During the migration, content of the document will also get updated 
	 * to reflect current migration result.
	 * @param listener the migration listener to receive migration events.
	 * With listener this method works a little bit slower, as it doesn't use precreated xml serializer.
	 * Set to null if you do not want to receive migration events.
	 * @param beanClass class of the bean. Class information in current document 
	 * will be used if this param is set to null
	 * @return object deserialized from document
	 */
	public Object toBean(MigrationListener listener, Class<?> beanClass) {
		Dom4JReader domReader = new Dom4JReader(this);

		XStream xstream;
		final boolean[] migrated = {false};
		if (listener != null) {
			//can't use default in case of listener as it is shared, creating a new one
			xstream = new XStream();
			xstream.registerConverter(new NeedMigrationConverter(xstream, new MigrationListener() {
				@Override
				public void migrated(final Object bean) {
					migrated[0] = true;
				}
			}));
			xstream.registerConverter(new IgnoreUnknownElementsMapConverter(xstream));
		} else {
			xstream = defaultXstream;
		}

		Class<?> origBeanClass = HierarchicalStreams.readClassType(domReader, xstream.getMapper());
		if (origBeanClass == null) return null;

		if (beanClass != null) {
			getRootElement().setName(xstream.getMapper().serializedClass(beanClass));
		}
		Object result = xstream.unmarshal(domReader);
		if (listener != null && migrated[0]) {
			listener.migrated(result);
		}

		return result;
	}

	public static VersionedDocument fromXML(final FileReader fr) {
		synchronized (reader) {
			try {
				return new VersionedDocument(reader.read(fr));
			} catch (DocumentException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
