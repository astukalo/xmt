package xyz.a5s7.xmlt.bean;

import org.dom4j.tree.DefaultElement;

import java.util.Deque;
import java.util.List;

public class CompileTask extends AbstractCompileTask {
	public List<String> srcFiles;

	public String destDir = "classes";

	@SuppressWarnings("unused")
	private void migrate1(DefaultElement dom, Deque<Integer> versions) {
		dom.addElement("destDir").setText("classes");
	}

	@SuppressWarnings("unused")
	private void migrate2(DefaultElement dom, Deque<Integer> versions) {
		versions.push(0);
		dom.addElement("options").setText("-debug");
	}
}