package pl.polsl.bdis.interceptors;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import java.util.List;

public class FuzzySqlInterceptor implements Interceptor {

    public void initialize() {

    }

    public Event intercept(Event event) {
        byte[] eventBody = event.getBody();
        byte[] modifiedEvent = "super interceptor dziala".getBytes();
        event.setBody(modifiedEvent);
        return event;
    }

    public List<Event> intercept(List<Event> list) {
        for(Event event : list) {
            System.out.println(new String(intercept(event).getBody()));
        }
        return list;
    }

    public void close() {

    }

    public static class Builder implements org.apache.flume.interceptor.Interceptor.Builder {
        public Interceptor build() {
            return new FuzzySqlInterceptor();
        }

        /*
        * służy do przekazania ustawień z pliku flume.properties tutaj -> patrz inne interceptory
        * może można wykorzystać do przekazania SQL zamiast w headerze.
        * */
        public void configure(Context context) {

        }
    }

}
