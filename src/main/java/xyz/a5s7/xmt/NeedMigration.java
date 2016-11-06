package xyz.a5s7.xmt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation to indicate that instance of class need migrate[Version](DefaultElement dom, Deque<Integer> versions) {} methods.
 *
 * <pre> {@code
 *   @NeedMigration
 *   public class Bean1 {
 *       private int priority;
 *
 *        public int getPriority() {
 *            return priority;
 *        }
 *
 *        public void setPriority(int priority) {
 *            this.priority = priority;
 *        }
 *
 *        @SuppressWarnings("unused")
 *        private void migrate1(DefaultElement dom, Deque<Integer> versions) {
 *            Element element = dom.element("prioritized");
 *            element.setName("priority");
 *            if (element.getText().equals("true"))
 *                element.setText("10");
 *            else
 *                element.setText("1");
 *        }
 *    }
 * }</pre>
 *
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedMigration {
}
