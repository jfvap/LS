package pt.isel.ls.input;

import pt.isel.ls.exception.IllegalParametersException;

import java.util.HashMap;

public class Separator {

    public HashMap<String, Object> mapInfo(String info, String sep1, String sep2)
            throws IllegalParametersException {//name=1&val=2
        HashMap<String, Object> map = new HashMap<>();
        String[] pairs = info.split(sep1);
        for (String pair : pairs) {
            String[] keyValPair = pair.split(sep2);
            if (keyValPair.length != 2) {
                throw new IllegalParametersException(
                        "Parameters are invalid -> key1" + sep1 +"val1" + sep2 + "key2"+ sep1 +"val2");
            }
            map.put(keyValPair[0], keyValPair[1].replaceAll("\\+", " "));
        }
        return map;
    }
}
