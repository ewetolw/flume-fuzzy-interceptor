package pl.polsl.bdis.models;

import java.io.Serializable;

public class Ling implements Serializable {
    public String key;
    public LinguisticVariable variable;

    public Ling(String key, LinguisticVariable variable) {
        this.key = key;
        this.variable = variable;
    }

    public Ling() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LinguisticVariable getVariable() {
        return variable;
    }

    public void setVariable(LinguisticVariable variable) {
        this.variable = variable;
    }
}
