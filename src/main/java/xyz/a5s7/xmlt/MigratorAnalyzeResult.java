package xyz.a5s7.xmlt;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MigratorAnalyzeResult {
	private Map<String, Integer> migrateVersions = new HashMap<>();
	
	private List<Method> migrateMethods = new ArrayList<>();

	public Map<String, Integer> getMigrateVersions() {
		return migrateVersions;
	}
	
	public List<Method> getMigrateMethods() {
		return migrateMethods;
	}
	
	public int getDataVersion() {
		int size = migrateMethods.size();
        return size != 0 ? migrateVersions.get(migrateMethods.get(size - 1).getName()) : 0;
	}
}	
