package pt.isel.ls.tree;

import pt.isel.ls.handlers.CommandHandler;

import java.util.HashMap;

public class TreeNode {
    public String path;
    public String template;
    public CommandHandler handler;
    public boolean isLabel;
    public HashMap<String, TreeNode> nodes;

    public TreeNode() {
        this.nodes = new HashMap<>();
    }
}
