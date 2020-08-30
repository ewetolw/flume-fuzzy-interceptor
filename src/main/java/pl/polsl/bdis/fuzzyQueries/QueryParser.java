package pl.polsl.bdis.fuzzyQueries;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mortbay.util.ajax.JSON;

public class QueryParser {
    private String configFilePath="linguisticVariables.json";
    //just for now, should be read from json file
    private String lingVariables = "\"{\\\"colName\\\":\\\"temperature\\\",\\\"types\\\":[{\\\"name\\\":\\\"normal\\\",\\\"type\\\":\\\"isoscelesTriangle\\\",\\\"a\\\":36.6,\\\"b\\\":0.4},{\\\"name\\\":\\\"high\\\",\\\"type\\\":\\\"triangle\\\",\\\"a\\\":37,\\\"b\\\":38,\\\"c\\\":45}]}\"";
    private Map<Pattern, FuzzyFunctionStrategy> fuzzyFunctionByExpression = new HashMap<Pattern, FuzzyFunctionStrategy>();
    private FuzzyFunctionStrategy strategy;
    private String fuzzyExpression;

    public QueryParser () {
        fuzzyFunctionByExpression.put(Pattern.compile("triangle\\(([^)]+)\\)"), new TriangleFuzzyFunctionStrategy());
    }

public String parse(String initQuery) {
    String query = initQuery;
    // read JSON
    // String json = readJson(lingVariables);
    // check if query contains some linguistic expressions -> check in json
    boolean containsLinguisticExpression = true; //TODO

    // in case it does, translate it to fuzzy functions
    if(containsLinguisticExpression) {
        for(Map.Entry<Pattern, FuzzyFunctionStrategy> entry : fuzzyFunctionByExpression.entrySet()) {
            Matcher matcher = entry.getKey().matcher(initQuery);

            if(matcher.find()){
                try {
                fuzzyExpression = matcher.group(0);
                strategy = entry.getValue();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }

                break;
            }
        }
    }

    // use fuzzy functions to translate query to pure SQL
    if(strategy != null) {
        query = initQuery.replace(fuzzyExpression, strategy.convertFuzzyToSql(fuzzyExpression));
    }

    return query;
}
// TODO: use filePath instead of string variable
//    private String readJson(String filePath) {
    private String readJson(String lingVariables) {
        JSONParser jsonParser = new JSONParser();
        try
        {
//            JSONObject test = new JSONObject(lingVariables);
            Object arr =  jsonParser.parse(lingVariables);
//            JSONObject axd = (JSONObject) arr;
            JSONArray array = new JSONArray();
            array.add(arr);
            JSONObject variables = (JSONObject) jsonParser.parse(lingVariables);
            for(int i = 0; i<variables.size(); i++){
                System.out.println(variables.get(i));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    return "";
}

}
