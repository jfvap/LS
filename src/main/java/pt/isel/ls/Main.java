package pt.isel.ls;

import pt.isel.ls.http.ServletApp;
import pt.isel.ls.input.Method;
import pt.isel.ls.input.Router;
import pt.isel.ls.input.ViewsRouter;

//listen/port=8080
public class Main {

    public static void main(String[] args) throws Exception {
        Router router = new Router();
        if (args.length > 0) {
            String[] s = args[0].split("/");
            if (Method.LISTEN.isEnum(s[0])) {
                try {
                    String[] p = s[1].split("=");
                    int port = Integer.parseInt(p[1]);
                    ServletApp.run(port, router, new ViewsRouter());
                } catch (IllegalArgumentException e) {
                    System.err.println("PORT NUMBER NOT CORRECTLY DEFINED!!!!!");
                }
            } else {
                new App(router).run(args);// com args
            }
        } else {
            new App(router).run(args);// sem args - o args vai vazio
        }
    }
}
