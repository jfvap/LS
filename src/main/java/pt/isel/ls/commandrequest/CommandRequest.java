package pt.isel.ls.commandrequest;

import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.input.Parameters;
import pt.isel.ls.input.Path;

public class CommandRequest {
    private final Path path;
    private final Parameters parameters;

    public CommandRequest(Path path, Parameters parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public Object getParam(String key) {
        if (parameters == null) {
            return null;
        }
        return parameters.getValueParam(key);
    }

    public Object getPathParam(String key) throws IllegalPathParametersException {
        return path.getValueInt(key);
    }

    /*
    public int getSizeMap() {
        return parameters.getSize();
    }
     */

    public Parameters getParametersInstance() {
        return parameters;
    }

    public void setPathParam(String key, Integer value) {
        path.put(key, value);
    }

    public void setParam(String key, Integer value) {
        parameters.put(key, value);
    }
}
