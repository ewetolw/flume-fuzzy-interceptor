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
    private static String body1 =
            "  {\n" +
            "    \"id\": 131,\n" +
            "    \"age\": 47,\n" +
            "    \"gender\": \"female\",\n" +
            "    \"temperature\": 35.5314\n" +
            "  }";
    private static String body2 =
            "{\n" +
            "    \"id\": 179,\n" +
            "    \"age\": 21,\n" +
            "    \"gender\": \"male\",\n" +
            "    \"temperature\": 38.5879\n" +
            "  }";

//    private static String arrayBody = "[\n" +
//            "  {\n" +
//            "    \"id\": 139,\n" +
//            "    \"age\": 17,\n" +
//            "    \"gender\": \"female\",\n" +
//            "    \"temperature\": 39.8152\n" +
//            "  },\n" +
//            "  {\n" +
//            "    \"id\": 102,\n" +
//            "    \"age\": 39,\n" +
//            "    \"gender\": \"female\",\n" +
//            "    \"temperature\": 37.6774\n" +
//            "  }]";

    public static void main(String[] args){
        Event e1 = new JSONEvent();
        Event e2 = new JSONEvent();
        Map<String, String> m = new HashMap<String, String>();
        e1.setHeaders(m);
        e1.setBody(body1.getBytes());
        list.add(e1);
        e2.setHeaders(m);
        e2.setBody(body2.getBytes());
        list.add(e2);

        Map<String, String> settings = new HashMap<String, String>();
        settings.put("streamClass", "pl.polsl.bdis.models.Patient");
//        settings.put("query", "SELECT * FROM stream WHERE trapeze(temperature, 35.7, 36.6, 37.2, 38.0, 0.5)");
//        settings.put("query", "SELECT * FROM stream WHERE triangle(temperature, 35.7, 36.6, 37.2) < 0.5");
//        settings.put("query", "SELECT * FROM stream WHERE (triangle(temperature, 35.7, 36.6, 37.2) < 0.5 OR triangle(temperature, 35.0, 35.5, 36.6) > 0.8)");
//        settings.put("query", "SELECT * FROM stream WHERE (triangle(temperature, 34.0, 36.6, 37.2) > 0.5 OR triangle(age, 23, 40, 60) > 0.8)");
//        settings.put("query", "select * from stream where triangle(temperature, 35.0, 35.5, 36.6) > 0.8");
//        settings.put("query", "select * from stream where cold");
        settings.put("query", "select * from stream where (fever OR cold)");
        Context context = new Context(settings);
        FuzzySqlInterceptor.Builder builder = new FuzzySqlInterceptor.Builder();
        builder.configure(context);
        Interceptor interceptor = builder.build();
        List<Event> l = interceptor.intercept(list);
        for(Event e : l ) {
            System.out.println(new String(e.getBody()));
        }
    }
}

////// recommend for data generation!
//https://www.json-generator.com/
//
//[
//  '{{repeat(5, 7)}}',
//  {
//    id: '{{integer(100, 200)}}',
//    age: '{{integer(16,50)}}',
//    gender: '{{gender()}}',
//    temperature: '{{floating(35, 40)}}'
//  }
//]