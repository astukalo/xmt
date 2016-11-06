package xyz.a5s7.xmt.bean;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import xyz.a5s7.xmt.MigrationHelper;
import xyz.a5s7.xmt.NeedMigration;

import java.util.Deque;

@NeedMigration
public class AbstractCompileTask {
	public int priority;

	public String options = "-debug";

	@SuppressWarnings("unused")
	private void migrate1(DefaultElement dom, Deque<Integer> versions) {
		int taskVersion = versions.pop();
		MigrationHelper.migrate(String.valueOf(taskVersion),
				TaskMigrator.class, dom);
	}

	public static class TaskMigrator {

		@SuppressWarnings("unused")
		private void migrate1(DefaultElement dom, Deque<Integer> versions) {
			Element element = dom.element("prioritized");
			element.setName("priority");
			if (element.getText().equals("true"))
				element.setText("HIGH");
			else
				element.setText("LOW");
		}

		@SuppressWarnings("unused")
		private void migrate2(DefaultElement dom, Deque<Integer> versions) {
			Element element = dom.element("priority");
			if (element.getText().equals("HIGH"))
				element.setText("10");
			else if (element.getText().equals("MEDIUM"))
				element.setText("5");
			else
				element.setText("1");
		}
	}
}