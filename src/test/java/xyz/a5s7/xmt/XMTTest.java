package xyz.a5s7.xmt;

import org.junit.Test;
import xyz.a5s7.xmt.bean.Bean1;
import xyz.a5s7.xmt.bean.Bean2;
import xyz.a5s7.xmt.bean.Bean3;
import xyz.a5s7.xmt.bean.Bean4;
import xyz.a5s7.xmt.bean.Bean5;
import xyz.a5s7.xmt.bean.Bean7;
import xyz.a5s7.xmt.bean.BeanWithMigration;
import xyz.a5s7.xmt.bean.Child;
import xyz.a5s7.xmt.bean.CompileTask;

import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static xyz.a5s7.xmt.Util.getFile;
import static xyz.a5s7.xmt.Util.readXML;

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

	@Test
	//Field exists in xml, but type changed - was double, became int.
	public void shouldSucceedWhenFieldTypeChangedButBeanHasMigrateMethod() throws Exception {
		try (FileReader fr = new FileReader(getFile("bean3.xml"))) {
			BeanWithMigration o = (BeanWithMigration) VersionedDocument.fromXML(fr).toBean();
			assertNotNull(o);
			assertEquals(35, o.getNestedBean().getI().intValue());
		}
	}

	@Test
	//Field exists in xml, but type changed - was double, became int.
	public void shouldSucceedWhenNestedBeanHasVersion() throws Exception {
		try (FileReader fr = new FileReader(getFile("bean4.xml"))) {
			BeanWithMigration o = (BeanWithMigration) VersionedDocument.fromXML(fr).toBean();
			assertNotNull(o);
			assertEquals(35, o.getNestedBean().getI().intValue());
		}
	}

	@Test
	//Field exists in xml, but type changed. Should migrate with parent method
	public void shouldSucceedWhenFieldTypeChangedButParentBeanHasMigrateMethod() throws Exception {
		try (FileReader fr = new FileReader(getFile("bean6.xml"))) {
			final boolean[] migrated = {false};
			Child o = (Child) VersionedDocument.fromXML(fr).toBean(new MigrationListener() {
				@Override
				public void migrated(final Object bean) {
					migrated[0] = true;
				}
			});
			assertNotNull(o);
			assertTrue(migrated[0]);
			assertEquals(10, o.getPriority());
		}
	}

	@Test
	public void shouldNotCallMigrateMethodsForLatestVersion() throws Exception {
		try (FileReader fr = new FileReader(getFile("bean7.xml"))) {
			final boolean[] migrated = {false};
			Child o = (Child) VersionedDocument.fromXML(fr).toBean(new MigrationListener() {
				@Override
				public void migrated(final Object bean) {
					migrated[0] = true;
				}
			});
			assertNotNull(o);
			assertFalse(migrated[0]);
		}
	}
}
