package pl.polsl.bdis.models;

import java.io.Serializable;
import java.util.ArrayList;

public class LinguisticVariable implements Serializable {
    public String type;
    public ArrayList<Double> values;
    public Double affiliationCoefficient;
    public String comparationMark;
    public String affiliationFunction;

    public LinguisticVariable(String type, ArrayList<Double> values, Double affiliationCoefficient, String comparationMark, String affiliationFunction) {
        this.type = type;
        this.values = values;
        this.affiliationCoefficient = affiliationCoefficient;
        this.comparationMark = comparationMark;
        this.affiliationFunction = affiliationFunction;
    }

    public LinguisticVariable() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    public void setValues(ArrayList<Double> values) {
        this.values = values;
    }

    public Double getAffiliationCoefficient() {
        return affiliationCoefficient;
    }

    public void setAffiliationCoefficient(Double affiliationCoefficient) {
        this.affiliationCoefficient = affiliationCoefficient;
    }

    public String getComparationMark() {
        return comparationMark;
    }

    public void setComparationMark(String comparationMark) {
        this.comparationMark = comparationMark;
    }

    public String getAffiliationFunction() {
        return affiliationFunction;
    }

    public void setAffiliationFunction(String affiliationFunction) {
        this.affiliationFunction = affiliationFunction;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(affiliationFunction + "(" + type);
        for(Double v: values) {
            s.append(", ");
            s.append(v);
        }
        s.append(") ").append(comparationMark).append(" ").append(affiliationCoefficient);

        return s.toString();
    }

}
