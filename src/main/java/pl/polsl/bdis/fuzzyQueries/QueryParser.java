package pl.polsl.bdis.fuzzyQueries;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class QueryParser {
    private String configFilePath="linguisticVariables.json";
    //just for now, should be read from json file
    private String lingVariables = "\"{\\\"colName\\\":\\\"temperature\\\",\\\"types\\\":[{\\\"name\\\":\\\"normal\\\",\\\"type\\\":\\\"isoscelesTriangle\\\",\\\"a\\\":36.6,\\\"b\\\":0.4},{\\\"name\\\":\\\"high\\\",\\\"type\\\":\\\"triangle\\\",\\\"a\\\":37,\\\"b\\\":38,\\\"c\\\":45}]}\"";
    private Map<Pattern, FuzzyFunctionStrategy> fuzzyFunctionByExpression = new HashMap<Pattern, FuzzyFunctionStrategy>();

    public QueryParser () {
        fuzzyFunctionByExpression.put(Pattern.compile("triangle\\(([^)]+)\\)\\s*\\W*\\d.?\\d?"), new TriangleFuzzyFunctionStrategy());
        fuzzyFunctionByExpression.put(Pattern.compile("trapeze\\(([^)]+)\\)"), new TrapezeFuzzyFunctionStrategy());
        // initialize this map with all fuzzy expressions. Probably json file containing linguistic expressions should
        // be read here as well and initialized
    }

public String parse(String initQuery) {
    String query = initQuery;
    for(Map.Entry<Pattern, FuzzyFunctionStrategy> entry : fuzzyFunctionByExpression.entrySet()) {
        Matcher matcher = entry.getKey().matcher(initQuery);

        while (matcher.find()){
            try {
                String fuzzyExpression = matcher.group(0);
                FuzzyFunctionStrategy strategy = entry.getValue();
                query = query.replace(fuzzyExpression, strategy.convertFuzzyToSql(fuzzyExpression));
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
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
