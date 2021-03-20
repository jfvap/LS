package pt.isel.ls.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.input.Router;
import pt.isel.ls.input.ViewsRouter;

public class ServletApp {

    private static final Logger log = LoggerFactory.getLogger(ServletApp.class);

    private static final int LISTEN_PORT = 8080;

    public static void main(String[] args) throws Exception {
        run(new Router());
        /*String portDef = System.getenv("PORT");
        int port = portDef != null ? Integer.parseInt(portDef) : LISTEN_PORT;
        run(port, new Router(),new ViewsRouter());*/
    }

    public static void run(int port, Router router, ViewsRouter viewsRouter) throws Exception {
        log.info("main started");

        log.info("configured listening port is {}", port);

        Server server = new Server(port);
        ServletHandler handler = new ServletHandler();
        Servlet servlet = new Servlet(router, viewsRouter);

        handler.addServletWithMapping(new ServletHolder(servlet), "/*");
        log.info("registered {} on all paths", servlet);

        server.setHandler(handler);
        server.start();

        log.info("server started listening on port {}", port);

        server.join();

        log.info("main is ending");
    }

    public static void run(Router router) throws Exception {
        /*String portDef = System.getenv("PORT");
        int port = portDef != null ? Integer.parseInt(portDef) : LISTEN_PORT;
        run(port, router);*/

        String portDef = System.getenv("PORT");
        int port = portDef != null ? Integer.parseInt(portDef) : LISTEN_PORT;
        run(port, new Router(), new ViewsRouter());
    }
}
