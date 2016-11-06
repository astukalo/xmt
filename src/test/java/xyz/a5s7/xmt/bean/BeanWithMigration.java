package xyz.a5s7.xmt.bean;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.dom4j.tree.DefaultElement;
import xyz.a5s7.xmt.NeedMigration;

import java.util.Deque;

@NeedMigration
public class BeanWithMigration {
    private String str;
    private double dbl;
    private NestedBean nestedBean;

    //    private int i; field was removed in v1
    private String newStringFieldInV2;

    public BeanWithMigration() {
    }

    public BeanWithMigration(final String str, final double dbl, final NestedBean nestedBean) {
        this.str = str;
        this.dbl = dbl;
        this.nestedBean = nestedBean;
    }

    public String getStr() {
        return str;
    }

    public void setStr(final String str) {
        this.str = str;
    }

    public double getDbl() {
        return dbl;
    }

    public void setDbl(final double dbl) {
        this.dbl = dbl;
    }

    public NestedBean getNestedBean() {
        return nestedBean;
    }

    public void setNestedBean(final NestedBean nestedBean) {
        this.nestedBean = nestedBean;
    }

    public String getNewStringFieldInV2() {
        return newStringFieldInV2;
    }

    public void setNewStringFieldInV2(final String newStringFieldInV2) {
        this.newStringFieldInV2 = newStringFieldInV2;
    }

    @SuppressWarnings("unused")
    private void migrate1(DefaultElement dom, Deque<Integer> versions) {
        //get the old value from the
        Element element = dom.element("i");
        Element str = dom.element("str");
        str.setText(str.getText() + element.getText());
        dom.remove(element);
    }

    //Example on how migrate methods are called consequently
    @SuppressWarnings("unused")
    private void migrate2(DefaultElement dom, Deque<Integer> versions) {
        Element element = dom.element("str");
        DOMElement fieldInV2 = new DOMElement("newStringFieldInV2");
        fieldInV2.setText(element.getText());
        dom.add(fieldInV2);
    }
}
