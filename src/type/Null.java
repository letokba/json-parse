package type;

/**
 * @author Wait
 */
public class Null {

    @Override
    public String toString() {
        return "null";
    }


    @Override
    protected Object clone() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == null || obj == this;
    }
}
