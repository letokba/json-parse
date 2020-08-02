package type;

import json.Null;


/**
 * @author Wait
 */
public class JsonValueConfigurableResolver implements ValueResolver {
    private BaseParser baseParser;


    /**
     * set the Value Parser.
     * @param baseParser
     *                  a parser chain
     */
    public void setResolver(BaseParser baseParser) {
        this.baseParser = baseParser;
    }

    /**
     * default config a parser.
     * parser chain is string -> boolean -> null -> number
     */
    private void defaultConfig() {
        if(baseParser != null) {
            return;
        }
        NumberParser numberParser = new NumberParser(null);
        NullParser nullParser = new NullParser(numberParser);
        BooleanParser booleanParser = new BooleanParser(nullParser);
        StringParser parser = new StringParser(booleanParser);
        setResolver(parser);
    }

    @Override
    public Object resolve(String value) {
        if(baseParser == null) {
            defaultConfig();
        }
        return baseParser.execute(value);
    }

    /**
     * a Base Parser include three methods that is Execute, Check, parse.
     * if check pass, it will execute the parse method.
     * else will call the next Parser's execute method.
     */
    public abstract class BaseParser{

        private BaseParser next;

        /**
         * construct a BaseParser Object
         * @param next
         *          next BaseParser
         */
        BaseParser(BaseParser next) {
            this.next = next;
        }

        /**
         * the Parser main Method.
         * @param value
         *          a parsed string
         * @return if check pass, return parse object, else return null
         */
        public Object execute(String value){
            if(check(value)){
                return parse(value);
            }
            else if (next == null) {
                return null;
            }
            return this.next.execute(value);
        }

        /**
         * parse the string
         * @param value
         *          a parsed string.
         * @return a object
         */
        public abstract Object parse(String value);

        /**
         * check the string.
         * @param value
         *          a checked string
         * @return if check pass, return true
         */
        public abstract boolean check(String value);
    }

    /**
     * default String Parser.
     */
    private  class StringParser extends BaseParser{

        StringParser(BaseParser next) {
            super(next);
        }

        @Override
        public Object parse(String value) {
            int minLength = 2;
            if(value.length() == minLength){
                // the value is an empty String
                return value;
            }
            return value.substring(1, value.length() - 1);
        }

        @Override
        public boolean check(String value) {
            int lastIndex = value.length() - 1;
            return value.charAt(0) == FLAG_STRING && value.charAt(lastIndex) == FLAG_STRING;
        }
    }


    /**
     * default Boolean Parser.
     */
    private  class BooleanParser extends BaseParser{
        BooleanParser(BaseParser next) {
            super(next);
        }

        @Override
        public Object parse(String value) {
            if(value.equals(LITERAL_TRUE)){
                return true;
            }
            if(value.equals(LITERAL_FALSE)) {
                return false;
            }
            return null;
        }

        @Override
        public boolean check(String value) {
            return value.equals(LITERAL_TRUE) || value.equals(LITERAL_FALSE);
        }
    }

    /**
     * default Null Parser.
     */
    private class NullParser extends BaseParser {

        NullParser(BaseParser next) {
            super(next);
        }

        @Override
        public Object parse(String value) {
            if(value.equals(LITERAL_NULL)){
                return new Null();
            }
            return null;
        }

        @Override
        public boolean check(String value) {
            return value.equals(LITERAL_NULL);
        }
    }


    /**
     * default Number Parser.
     */
    private class NumberParser extends BaseParser{

        NumberParser(BaseParser next) {
            super(next);
        }

        @Override
        public Object parse(String value) {
            if(value.contains(FLAG_FLOUT)){
                // the number string include The decimal point.
                return Double.parseDouble(value);
            }else{
                return Integer.parseInt(value);
            }
        }

        @Override
        public boolean check(String value) {
            for (int i = 0; i < value.length(); i++) {
                if(value.charAt(i) < '0'
                        || value.charAt(i) > '9'
                        || value.charAt(i) != FLAG_FLOUT.charAt(0)) {
                    return false;
                }
            }
            return true;
        }
    }

}
