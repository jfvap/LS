package pt.isel.ls.handlers;

import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.Options;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultOptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.LinkedList;

public class OptionsHandler implements CommandHandler {
    public static final String OPTIONS_PATH = "/options/options.txt";
    public static final String PROJECT_FILE = "2021-1-LI41N-G2";

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest) {
        LinkedList<String> list = new LinkedList<>();
        Path p = Paths.get(PROJECT_FILE).toAbsolutePath().getParent();
        //String s = p.toString() + OPTIONS_PATH;
        try (BufferedReader br = new BufferedReader(new FileReader(p.toString() + OPTIONS_PATH))) {
            String str;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResultOptions result = new ResultOptions();
        Options op = new Options(list);
        result.addResult(op);

        return result;
    }

    @Override
    public ResultError isValid(CommandRequest commandRequest, Connection con) {
        return null;
    }
}
