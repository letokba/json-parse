package json;

/**
 * @author Wait
 */
public interface Element {

    public void setAttribute(String name, Object value);

    public Object getAttribute(String name);

    public void removeAttribute(String name);
}
