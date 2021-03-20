package pt.isel.ls.tree;

import pt.isel.ls.handlers.CommandHandler;

import java.util.HashMap;

public class WrapperHandlerRes {
    public CommandHandler handler;
    public HashMap<String, Object> map;
    public String template;

    public WrapperHandlerRes(CommandHandler handler, HashMap<String, Object> map, String template) {
        this.handler = handler;
        this.map = map;
        this.template = template;
    }
}
