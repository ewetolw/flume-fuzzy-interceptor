package pl.polsl.bdis.fuzzyQueries;

import java.util.Locale;

public class TriangleFunction {
    public static String triangle(String colName, double a, double b, double c, EqualitySign eqSign, double affiliationCoefficient) {
        double xMin = affiliationCoefficient * (b - a) + a;
        double xMax = affiliationCoefficient * (b - c) + c;
        EqualitySign contrarySign = EqualitySign.getContrary(eqSign);

        String logicalOperator = eqSign.equals(EqualitySign.GreaterOrEqual) || eqSign.equals(EqualitySign.GreaterThan) ? "AND" : "OR";

        return String.format(Locale.US, "(%s %s %.5f %s %s %s %.5f)", colName, eqSign.toString(), xMin, logicalOperator, colName, contrarySign.toString(), xMax);
    }

    public static String triangle(String colName, double a, double b, double c) {
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
