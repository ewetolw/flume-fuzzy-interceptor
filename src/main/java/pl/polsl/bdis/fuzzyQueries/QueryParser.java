package pl.polsl.bdis.fuzzyQueries;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;
import pl.polsl.bdis.models.Ling;

public class QueryParser {
    private String configFilePath="linguisticVariables.json";
    private Map<Pattern, FuzzyFunctionStrategy> fuzzyFunctionByExpression = new HashMap<Pattern, FuzzyFunctionStrategy>();

    public QueryParser () {
        fuzzyFunctionByExpression.put(Pattern.compile("triangle\\(([^)]+)\\)\\s*\\W*\\d.?\\d?"), new TriangleFuzzyFunctionStrategy());
        fuzzyFunctionByExpression.put(Pattern.compile("trapeze\\(([^)]+)\\)\\s*\\W*\\d.?\\d?"), new TrapezeFuzzyFunctionStrategy());
        // initialize this map with all fuzzy expressions. Probably json file containing linguistic expressions should
        // be read here as well and initialized
    }

public String parse(String initQuery) throws IOException {
    String query = initQuery;
    for(Map.Entry<Pattern, FuzzyFunctionStrategy> entry : fuzzyFunctionByExpression.entrySet()) {
        query = readLinguisticVariables(query);
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

    public String readLinguisticVariables(String query) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(
                System.getProperty("variablePath")
        );
        Ling[] listLing = mapper.readValue(file, Ling[].class);
        for(Ling l: listLing) {
            if(query.contains(l.key)){
                query = query.replaceAll(l.key, l.variable.toString());
            }
        }
    return query;
}

}
