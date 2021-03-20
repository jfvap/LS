package pt.isel.ls.input;

import pt.isel.ls.exception.IllegalPathParametersException;

import java.util.HashMap;

public class Path {
    public String currPath;
    public HashMap<String, Object> pathParams;

    public Path(String currPath) {
        this.currPath = currPath;
    }

    public Integer getValueInt(String key) throws IllegalPathParametersException {
        try {
            return Integer.parseInt((String) pathParams.get(key));
        } catch (NumberFormatException e) {
            throw new IllegalPathParametersException("The resource that you have access not exist" +
                    " - the id should be an integer!!!");
        }
    }

    public Object getValueString(String key) throws IllegalPathParametersException {
        try {
            return (String) pathParams.get(key);
        } catch (Exception e) {
            throw new IllegalPathParametersException("The resource that you have access not exist" +
                    " - the id should be a String!!!");
        }
    }

    public void setPathParams(HashMap<String, Object> hashMap) {
        this.pathParams = hashMap;
    }

    public void put(String key, Object value) {
        pathParams.put(key, value);
    }
}
