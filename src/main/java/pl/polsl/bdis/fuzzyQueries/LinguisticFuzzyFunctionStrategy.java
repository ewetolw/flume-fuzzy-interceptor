package pl.polsl.bdis.fuzzyQueries;

public class LinguisticFuzzyFunctionStrategy implements FuzzyFunctionStrategy {
    public String convertFuzzyToSql(String fuzzyQuery) {
        // read these values from json config file
        String colName;
        double a;
        double b;
        double c;
        EqualitySign eqSign;
        double affiliationCoefficient;

        return "";
        // return TriangleFunction.triangle(colName, a, b, c, eqSign, affiliationCoefficient);
    }
}
