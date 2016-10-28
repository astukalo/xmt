package xyz.a5s7.xmlt.bean;

import xyz.a5s7.xmlt.VersionedDocument;

import java.util.List;
import java.util.Stack;

public class CompileTask extends AbstractCompileTask {
	public List<String> srcFiles;

	public String destDir = "classes";

	@SuppressWarnings("unused")
	private void migrate1(VersionedDocument dom, Stack<Integer> versions) {
		dom.getRootElement().addElement("destDir").setText("classes");
	}

	@SuppressWarnings("unused")
	private void migrate2(VersionedDocument dom, Stack<Integer> versions) {
		versions.push(0);
		dom.getRootElement().addElement("options").setText("-debug");
	}
}