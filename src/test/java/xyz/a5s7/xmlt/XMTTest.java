package xyz.a5s7.xmlt;

import xyz.a5s7.xmlt.bean.Bean1;
import xyz.a5s7.xmlt.bean.Bean2;
import xyz.a5s7.xmlt.bean.Bean3;
import xyz.a5s7.xmlt.bean.Bean4;
import xyz.a5s7.xmlt.bean.Bean5;
import xyz.a5s7.xmlt.bean.Bean7;
import xyz.a5s7.xmlt.bean.CompileTask;
import org.junit.Test;

import static xyz.a5s7.xmlt.Util.readXML;
import static org.junit.Assert.assertEquals;

public class XMTTest {
	
	@Test
	public void testMigration() {
		Bean1 bean = (Bean1) VersionedDocument.fromXML(readXML("bean1.xml")).toBean();
		assertEquals(bean.getPriority(), 10);
	}
	
	@Test
	public void testClassRenamedMigration() {
		Bean2 bean = (Bean2) VersionedDocument.fromXML(readXML("bean1.xml")).toBean(Bean2.class);
		assertEquals(bean.getPriority(), 10);
	}
	
	@Test
	public void testCompositeVersion() {
		assertEquals(MigrationHelper.getVersion(Bean3.class), "1.3");
	}

	@Test
	public void testNoVersion() {
		assertEquals(MigrationHelper.getVersion(Bean4.class), "0");
	}

	@Test
	public void testCompositeMigration() {
		Bean5 bean = (Bean5) VersionedDocument.fromXML(readXML("bean2.xml")).toBean();
		assertEquals(bean.getPriority(), 10);
		assertEquals(bean.getFirstName(), "Robin");
	}

	@Test
	public void testAddClass() {
		Bean7 bean = (Bean7) VersionedDocument.fromXML(readXML("bean2.xml")).toBean(Bean7.class);
		assertEquals(bean.getPriority(), 10);
		assertEquals(bean.getFirstName(), "Robin");
		assertEquals(bean.getAge(), 34);
	}
	
	@Test
	public void testRemoveClass() {
		CompileTask task = (CompileTask) VersionedDocument.fromXML(readXML("task.xml")).toBean();
		assertEquals(task.priority, 10);
		assertEquals(task.destDir, "classes");
		assertEquals(task.options, "-debug");
		assertEquals(task.srcFiles.size(), 2);
	}
}
