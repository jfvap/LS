package pt.isel.ls.view.text;

import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.Result;
import pt.isel.ls.view.View;

import java.io.Writer;

public class TextView implements View {
    /**
     * First we print the "section" for better readibility , then we print the single result (if exists)
     * and then the plural results that come in a list.
     *
     * @param commandResult instance of CommandResult where we get our results (class Result)
     * @param writer        Write destination stream
     * @return true if writing was sucessful.
     */
    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        Result results = commandResult.getResults();
        try {
            writer.write(results.toString());
            writer.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
