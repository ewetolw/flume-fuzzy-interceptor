package pl.polsl.bdis.fuzzyQueries;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrapezeFuzzyFunctionStrategy implements FuzzyFunctionStrategy {
    private Pattern parametersPattern = Pattern.compile("\\(([^)]+)\\)");

    public String convertFuzzyToSql(String fuzzyQuery) {
        Matcher matcher = parametersPattern.matcher(fuzzyQuery);
        if(matcher.find()){
            String [] parameters = matcher.group(1).split(",");
            String translatedQuery = "";
            if(parameters.length == 6) {
                // fuzzyQuery should look like this: trapeze(colName, a, b, c, d, affiliationCoefficient)
                translatedQuery = trapeze(
                        parameters[0],
                        Double.parseDouble(parameters[1]),
                        Double.parseDouble(parameters[2]),
                        Double.parseDouble(parameters[3]),
                        Double.parseDouble(parameters[4]),
                        Double.parseDouble(parameters[5])
                );
            }

            return translatedQuery;

        }

        return fuzzyQuery;
    }

    private String trapeze(String colName, double a, double b, double c, double d, double affiliationCoefficient) {
        double xMin = affiliationCoefficient * (b - a) + a;
        double xMax = affiliationCoefficient * (c - d) + d;

        return String.format(Locale.US, "(%s >= %.5f AND %s <= %.5f)", colName, xMin, colName, xMax);
    }
}
