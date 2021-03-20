package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.Options;
import pt.isel.ls.model.Result;

public class ResultOptions extends ResultsHandler {
    private Options options = null;
    @Override
    public void addResult(Result result) {
        this.options = (Options) result;
    }

    @Override
    public Result getResults() {
        return options;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
