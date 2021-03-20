package pt.isel.ls.view;

import pt.isel.ls.commandrequest.CommandResult;

import java.io.Writer;

public interface View {

    boolean printResult(CommandResult commandResult, Writer writer);
}

