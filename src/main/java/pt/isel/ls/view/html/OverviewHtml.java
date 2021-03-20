package pt.isel.ls.view.html;

import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;
import pt.isel.ls.view.View;

import java.io.Writer;

public class OverviewHtml implements View {

    /**
     * @param writer Write destination stream
     * @param html   Element (String) to save on stream
     * @return true if writing was sucessful, false otherwise.
     */
    public boolean printResultHtml(Writer writer, Element html) {
        try {
            writer.write(html.toString());
            writer.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        return false;
    }
}