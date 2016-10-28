package com.pmease.commons.xmt.bean;

import com.pmease.commons.xmt.MigrationHelper;
import com.pmease.commons.xmt.VersionedDocument;
import org.dom4j.Element;

import java.util.Stack;

public class AbstractCompileTask {
	public int priority;

	public String options = "-debug";

	@SuppressWarnings("unused")
	private void migrate1(VersionedDocument dom, Stack<Integer> versions) {
		int taskVersion = versions.pop();
		MigrationHelper.migrate(String.valueOf(taskVersion),
				TaskMigrator.class, dom);
	}

	public static class TaskMigrator {

		@SuppressWarnings("unused")
		private void migrate1(VersionedDocument dom, Stack<Integer> versions) {
			Element element = dom.getRootElement().element("prioritized");
			element.setName("priority");
			if (element.getText().equals("true"))
				element.setText("HIGH");
			else
				element.setText("LOW");
		}

		@SuppressWarnings("unused")
		private void migrate2(VersionedDocument dom, Stack<Integer> versions) {
			Element element = dom.getRootElement().element("priority");
			if (element.getText().equals("HIGH"))
				element.setText("10");
			else if (element.getText().equals("MEDIUM"))
				element.setText("5");
			else
				element.setText("1");
		}
	}
}