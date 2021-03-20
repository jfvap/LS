package pt.isel.ls;

import pt.isel.ls.exception.*;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.input.Header;
import pt.isel.ls.input.Method;
import pt.isel.ls.input.Router;
import pt.isel.ls.input.ViewsRouter;
import pt.isel.ls.view.View;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    private static final int METHOD_IDX = 0;
    private static final int PATH_IDX = 1;
    private final ViewsRouter viewsRouter =  new ViewsRouter();
    private final Services services;
    private final Scanner in = new Scanner(System.in, StandardCharsets.UTF_8);

    public App(Router router) {
        //this.router = router;
        this.services = new Services(router);
    }

    public static void main(String[] args) {
        //App app = new App(new Router());
        //app.run(args);
        //Path currentRelativePath = Paths.get("");
        //String s = currentRelativePath.toAbsolutePath().toString();
        //Path p = Paths.get("").getParent();
        //, ele fornece null. Em vez isso funciona:
        //Path s1 = Paths.get("2021-1-LI41N-G2").toAbsolutePath().getParent();
        //System.out.println("Current relative path is: " + s1);
    }

    public void run(String[] args) {
        boolean interactiveMode = args.length == 0;

        do {
            String[] userInput = interactiveMode ? getCommandInserted(in) : args;

            if (Method.EXIT.isEnum(userInput[METHOD_IDX])) {
                return;
            }

            try {
                CommandResult commandResult = services.run(userInput);
                Header header = new Header();
                header.findParameters(userInput);
                printResult(commandResult, header);
            } catch (NoCommandException e) {
                System.out.println(e.getMsg());
            } catch (InternalServerSqlQueryException e) {
                System.err.println(e.getMsg());
            } catch (ResourceNotFoundException e) {
                System.err.println(e.getMsg());
            } catch (ForeignkeyException e) {
                System.out.println(e.getMsg());
            } catch (IllegalParametersException e) {
                System.out.println(e.getMsg());
            } catch (IllegalPathParametersException e) {
                System.out.println(e.getMsg());
            } catch (IOException | SQLException e) {
                //System.err.println("IOException");
                e.printStackTrace();
            }
        } while (interactiveMode);
    }


    /**
     * Searches the view (depending on command and if it is html or plain text) and then
     * correctly prints the result(s)
     */
    public void printResult(CommandResult commandResult, Header header) throws FileNotFoundException {
        View view = viewsRouter.findView(commandResult, header.typePrint);
        Writer writer = getWriter(header.fileName != null ? new PrintStream(header.fileName) : System.out);
        boolean isSuccess = view.printResult(commandResult, writer);

        if (isSuccess) {
            System.out.println("\nWriting successful!!!");
        } else {
            System.out.println("\nSomething went wrong when writing!!!");
        }
    }

    /**
     * @param ps if filename is null then print to console, if not automatically creates a file with that name
     *                 and writes to it.
     * @return returns the stream to write on file or console .
     */
    public OutputStreamWriter getWriter(PrintStream ps) {
            return new OutputStreamWriter(ps);
    }

    private String[] getCommandInserted(Scanner in) {
        System.out.print("\nINSERT COMMAND:\n>");
        return in.nextLine().trim().split(" ");
    }
}