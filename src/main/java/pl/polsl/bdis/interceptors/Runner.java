package pl.polsl.bdis.interceptors;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.JSONEvent;
import org.apache.flume.interceptor.Interceptor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* odpalić klasę runner żeby zobaczyć jak działa interceptor, ten poglądowy podmienia tylko wartość body.
* */
public class Runner {
    private static List<Event> list = new ArrayList();

    public static void main(String[] args){
        Event e = new JSONEvent();
        Map<String, String> m = new HashMap<String, String>();
        e.setHeaders(m);
        e.setBody("body".getBytes());
        list.add(e);

        Context context = new Context();
        FuzzySqlInterceptor.Builder builder = new FuzzySqlInterceptor.Builder();
        builder.configure(context);
        Interceptor interceptor = builder.build();
        interceptor.intercept(list);
    }
}
