package pl.polsl.bdis.fuzzyQueries;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TriangleFuzzyFunctionStrategy implements FuzzyFunctionStrategy {
    private Pattern parametersPattern = Pattern.compile("\\(([^)]+)\\)\\s*(\\W*)(\\d.?\\d?)");

    public String convertFuzzyToSql(String fuzzyQuery) {
        Matcher matcher = parametersPattern.matcher(fuzzyQuery);
        if(matcher.find()){
            String [] parameters = matcher.group(1).split(",");
            EqualitySign equalitySign = EqualitySign.fromString(matcher.group(2));
            String affiliationCoefficient = matcher.group(3);
            String translatedQuery = "";
            if(parameters.length == 4) {
                // fuzzyQuery should look like this: triangle(colName, a, b, c) > affiliationCoefficient
                translatedQuery = TriangleFunction.triangle(
                        parameters[0],
                        Double.parseDouble(parameters[1]),
                        Double.parseDouble(parameters[2]),
                        Double.parseDouble(parameters[3]),
                        equalitySign,
                        Double.parseDouble(affiliationCoefficient)
                );
            }
            else if(parameters.length == 4) {
                // fuzzyQuery should look like this: triangle(colName, a, b, c)
            }

            return translatedQuery;

        }

        return fuzzyQuery;
    }


}
