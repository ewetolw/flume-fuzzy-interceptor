package pl.polsl.bdis.fuzzyQueries;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrapezeFuzzyFunctionStrategy implements FuzzyFunctionStrategy {
    private Pattern parametersPattern = Pattern.compile("\\(([^)]+)\\)\\s*(\\W*)(\\d.?\\d?)");

    public String convertFuzzyToSql(String fuzzyQuery) {
        Matcher matcher = parametersPattern.matcher(fuzzyQuery);
        if(matcher.find()){
            String [] parameters = matcher.group(1).split(",");
            EqualitySign equalitySign = EqualitySign.fromString(matcher.group(2));
            String affiliationCoefficient = matcher.group(3);
            String translatedQuery = "";

            if(parameters.length == 5) {
                // fuzzyQuery should look like this: trapeze(colName, a, b, c, d) >= affiliationCoefficient
                translatedQuery = trapeze(
                        parameters[0],
                        Double.parseDouble(parameters[1]),
                        Double.parseDouble(parameters[2]),
                        Double.parseDouble(parameters[3]),
                        Double.parseDouble(parameters[4]),
                        equalitySign,
                        Double.parseDouble(affiliationCoefficient)
                );
            }

            return translatedQuery;

        }

        return fuzzyQuery;
    }

    private String trapeze(String colName, double a, double b, double c, double d, EqualitySign eqSign, double affiliationCoefficient) {
        double xMin = affiliationCoefficient * (b - a) + a;
        double xMax = affiliationCoefficient * (c - d) + d;

        EqualitySign contrarySign = EqualitySign.getContrary(eqSign);

        String logicalOperator = eqSign.equals(EqualitySign.GreaterOrEqual) || eqSign.equals(EqualitySign.GreaterThan) ? "AND" : "OR";

        return String.format(Locale.US, "(%s %s %.5f %s %s %s %.5f)", colName, eqSign.toString(), xMin, logicalOperator, colName, contrarySign.toString(), xMax);
    }
}
