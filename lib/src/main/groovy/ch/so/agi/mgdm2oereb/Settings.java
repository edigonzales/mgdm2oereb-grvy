package ch.so.agi.mgdm2oereb;

import java.util.HashMap;

public class Settings {

    private HashMap<String,String> values = new HashMap<>();

    public String getValue(String name) {
        String value = values.get(name);
        return value;
    }

    public void setValue(String name, String value) {
        values.put(name, value);
    }
}
