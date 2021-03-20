package pt.isel.ls.tree;

import pt.isel.ls.handlers.CommandHandler;

import java.util.HashMap;

public class CommandsTree {
    TreeNode head = new TreeNode();

    public void addCommandTree(String cmd, CommandHandler handler, String template) {
        String[] splitCmd = cmd.split("/");
        TreeNode root = head;
        for (var p : splitCmd) {
            String aux = p;
            p = p.contains("{") ? "{id}" : p;
            if (!root.nodes.containsKey(p)) {
                TreeNode curr = new TreeNode();
                if (curr.isLabel = p.contains("{")) {
                    //p = "id";
                    aux = aux.replaceAll("[{}]", "");
                }
                curr.path = aux;
                root.nodes.put(p, curr);
                root = curr;
            } else {
                root = root.nodes.get(p);
            }
        }
        root.template = template;
        root.handler = handler;
    }

    public WrapperHandlerRes findHandler(String cmd) {
        String[] splitCmd = cmd.split("/");
        TreeNode root = head;
        HashMap<String, Object> labels = new HashMap<>();
        for (var p : splitCmd) {
            if (root.nodes.containsKey(p)) {
                root = root.nodes.get(p);
            } else {
                root = root.nodes.get("{id}");
                if (root != null && root.isLabel) {
                    labels.put(root.path, p);
                } else {
                    return new WrapperHandlerRes(null, null, null);
                }
            }
        }
        return new WrapperHandlerRes(root.handler, labels, root.template);
    }
}