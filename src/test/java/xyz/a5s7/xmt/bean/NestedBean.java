package xyz.a5s7.xmt.bean;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import xyz.a5s7.xmt.NeedMigration;

import java.util.Deque;

@NeedMigration
public class NestedBean {
    private Integer i;
    private String str;

    public NestedBean() {
    }

    public NestedBean(final int i, final String str) {
        this.i = i;
        this.str = str;
    }

    public Integer getI() {
        return i;
    }

    public void setI(final Integer i) {
        this.i = i;
    }

    public String getStr() {
        return str;
    }

    public void setStr(final String str) {
        this.str = str;
    }

    @SuppressWarnings("unused")
    private void migrate1(DefaultElement dom, Deque<Integer> versions) {
        Element iElem = dom.element("i");
        //get the old value from the
        String cur = iElem.getText();
        //convert double to int
        iElem.setText("" + Math.round(Float.parseFloat(cur)));
    }
}
