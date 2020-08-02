package json;

import resolve.JsonSerialize;

/**
 * @author Wait
 */
public interface Json {

    /**
     * Json Object serialize to Json string.
     * @return
     *      a json string
     */
    default public String toJsonString() {
        return new JsonSerialize().serialize(this);
    }
}
