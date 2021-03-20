package pt.isel.ls.input;

import pt.isel.ls.exception.IllegalParametersException;

import java.util.HashMap;

public class Parameters {
    private static final String sep1 = "&";
    private static final String sep2 =  "=";
    HashMap<String, Object> map;

    public Parameters() {}

    public boolean setParameters(String[] args) throws IllegalParametersException {
        for (var arg : args ) {
            if (arg != null && arg.contains(sep2)) {
                this.map = new Separator().mapInfo(arg, sep1, sep2);
                return true;
            }
        }
        this.map = new HashMap<>();
        return false;
    }

    public Object getValueParam(String key) {
        return map.get(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }
}
