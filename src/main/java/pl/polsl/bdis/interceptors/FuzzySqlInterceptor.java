package pl.polsl.bdis.interceptors;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.query.parser.sql.SQLParser;
import com.googlecode.cqengine.resultset.ResultSet;
import com.rits.cloning.Cloner;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.codehaus.jackson.map.ObjectMapper;


import static com.googlecode.cqengine.codegen.AttributeBytecodeGenerator.createAttributes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FuzzySqlInterceptor implements Interceptor {

    //private static final Logger logger = LoggerFactory.getLogger(FuzzySqlInterceptor.class);
    private final Class stream;
    private final String query;

    private SQLParser parser;
    private ObjectMapper mapper = new ObjectMapper();

    public FuzzySqlInterceptor(Class streamClass, String query) {
        this.stream = streamClass;
        //logger.info("Created stream: " + this.stream);
        System.out.println("Created stream for data structure: " + this.stream);
        this.query = query;
        //logger.info("Set query: " + this.query);
        System.out.println("Set query: " + this.query);
        this.parser = SQLParser.forPojoWithAttributes(this.stream, createAttributes(this.stream));
    }

    public void initialize() {

    }

    public Event intercept(Event event) {
        byte[] eventBody = event.getBody();
        IndexedCollection ic = new ConcurrentIndexedCollection();
        ResultSet results;
        String stringResult = null;
        try {
            ic.add(mapper.readValue(eventBody, stream));
            results = parser.retrieve(ic, this.query);
            if (results.isEmpty()) {
                return null;
            }
            stringResult = mapper.writeValueAsString(results.uniqueResult());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert stringResult != null;
        event.setBody(stringResult.getBytes());
        return event;
    }

    public List<Event> intercept(List<Event> list) {
        List<Event> events = new ArrayList<Event>();
        Event temp;
        for (Event event : list) {
            temp = intercept(event);
            if (temp != null) {
                events.add(doDeepCopy(temp));
            }
        }
        return events;
    }

    public void close() {

    }

    public static Event doDeepCopy(Event event) {
        Cloner cloner = new Cloner();
        return cloner.deepClone(event);
    }

    public static class Constants {
        public static String STREAM_CLASS = "streamClass";
        public static String QUERY = "query";

        public Constants() {
        }
    }

    public static class Builder implements org.apache.flume.interceptor.Interceptor.Builder {
        private String stream;
        private String query;

        public Interceptor build() {
            Class<?> cls = null;
            try {
                cls = Class.forName(this.stream);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return new FuzzySqlInterceptor(cls, query);
        }

        /*
         * służy do przekazania ustawień z pliku flume.properties tutaj -> patrz inne interceptory
         * może można wykorzystać do przekazania SQL zamiast w headerze.
         * */
        public void configure(Context context) {
            this.stream = context.getString(Constants.STREAM_CLASS);
            this.query = context.getString(Constants.QUERY);
        }
    }

}
