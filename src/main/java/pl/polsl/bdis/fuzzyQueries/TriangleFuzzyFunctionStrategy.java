package pl.polsl.bdis.fuzzyQueries;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TriangleFuzzyFunctionStrategy implements FuzzyFunctionStrategy {
    private Pattern parametersPattern = Pattern.compile("\\(([^)]+)\\)");

    public String convertFuzzyToSql(String fuzzyQuery) {

        Matcher matcher = parametersPattern.matcher(fuzzyQuery);
        if(matcher.find()){
            String [] parameters = matcher.group(1).split(",");
            String translatedQuery = "";
            if(parameters.length == 5) {
                // fuzzyQuery should look like this: triangle(colName, a, b, c, affiliationCoefficient)
                translatedQuery = triangle(
                        parameters[0],
                        Double.parseDouble(parameters[1]),
                        Double.parseDouble(parameters[2]),
                        Double.parseDouble(parameters[3]),
                        Double.parseDouble(parameters[4])
                );
            }
            else if(parameters.length == 4) {
                // fuzzyQuery should look like this: triangle(colName, a, b, c)
            }


            return translatedQuery;

        }

        return fuzzyQuery;
    }

    private String triangle(String colName, double a, double b, double c, double affiliationCoefficient) {
        double xMin = affiliationCoefficient * (b - a) + a;
        double xMax = affiliationCoefficient * (b - c) + c;

        return String.format(Locale.US, "(%s >= %.5f AND %s <= %.5f) ", colName, xMin, colName, xMax);
    }

    private String triangle(String colName, double a, double b, double c) {
        String conditionAsc = String.format("(%s > %.5f AND %s < %.5f)", colName, a, colName, b);
        String functionAsc = String.format("%.3f * %s - %.3f", 1/(b - a), colName, a/(b - a));
        String conditionDesc = String.format("(%s > %.5f AND %s < %.5f)", colName, b, colName, c);
        String functionDesc = String.format("%.3f * %s - %.3f", 1/(b - c), colName, c/(b - c));

        return String.format(
                Locale.US,
                "CASE WHEN %s THEN %s WHEN %s THEN %s ELSE 0 END",
                conditionAsc, functionAsc, conditionDesc, functionDesc, a);
    }
}
