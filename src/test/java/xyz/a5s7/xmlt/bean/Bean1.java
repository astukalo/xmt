package xyz.a5s7.xmlt.bean;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import xyz.a5s7.xmlt.NeedMigration;

import java.util.Deque;

@NeedMigration
public class Bean1 {
	private int priority;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@SuppressWarnings("unused")
	private void migrate1(DefaultElement dom, Deque<Integer> versions) {
		Element element = dom.element("prioritized");
		element.setName("priority");
		if (element.getText().equals("true"))
			element.setText("10");
		else
			element.setText("1");
	}
}
