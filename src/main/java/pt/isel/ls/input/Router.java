package pt.isel.ls.input;

import pt.isel.ls.exception.NoCommandException;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.OptionsHandler;
import pt.isel.ls.handlers.RootHandler;
import pt.isel.ls.tree.CommandsTree;
import pt.isel.ls.tree.WrapperHandlerRes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

public class Router {
    public static final String PROJECT_FILE = "2021-1-LI41N-G2";
    private static final String METHODS_FILE = "/options/methods.txt";
    CommandsTree manager = new CommandsTree();

    public Router() {
        obtainAllRoutesFromFile();
        manager.addCommandTree("OPTIONS", new OptionsHandler(), "OPTIONS");
        manager.addCommandTree("GET/", new RootHandler(), "/");
    }

    /**
     * @param method , @param path Finds the handler corresponding to this method and  path. If the pathtemplate
     *               matches we also need to set the path params HashMap returned from isMatch() call
     * @return returns the handler
     */
    public CommandHandler findHandler(Method method, Path path) throws NoCommandException {
        WrapperHandlerRes handlerRes = manager.findHandler(method.name() + path.currPath);
        if (handlerRes.handler != null) {
            path.setPathParams(handlerRes.map);
            return handlerRes.handler;
        }
        throw new NoCommandException();
    }

    public String findPathTemplate(Method method, Path path) throws NoCommandException {
        WrapperHandlerRes handlerRes = manager.findHandler(method.name() + path.currPath);
        if (handlerRes != null) {
            return handlerRes.template;
        }
        throw new NoCommandException();
    }

    /**
     * Reads the METHODS_FILE file and add to the LinkedList named "availableRoutes" of all available Routes
     */
    public void obtainAllRoutesFromFile() {
        java.nio.file.Path p = Paths.get(PROJECT_FILE).toAbsolutePath().getParent();
        try (BufferedReader br = new BufferedReader(new FileReader(p.toString() + METHODS_FILE))) {
            String separador = "_";
            String str;
            String[] parts;
            while ((str = br.readLine()) != null) {
                parts = str.split(separador);
                Method method = Method.valueOf(parts[0]);
                PathTemplate pathTemplate = new PathTemplate(parts[1]);
                String aux = "pt.isel.ls.handlers." + parts[2];
                Class<?> clazz = Class.forName(aux);
                Constructor constructor = clazz.getDeclaredConstructor();
                CommandHandler handler = (CommandHandler) constructor.newInstance();
                manager.addCommandTree(
                        method.name() + pathTemplate.getPathTemplate(),
                        handler,
                        pathTemplate.getPathTemplate()
                );
            }
        } catch (IOException
                | IllegalAccessException
                | InstantiationException
                | ClassNotFoundException
                | NoSuchMethodException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static class Route {
        public Method method;
        public PathTemplate pathTemplate;
        public CommandHandler commandHandler;
        public String description;

        public Route(Method method, PathTemplate pathTemplate, CommandHandler commandHandler, String description) {
            this.method = method;
            this.pathTemplate = pathTemplate;
            this.commandHandler = commandHandler;
            this.description = description;
        }
    }
}