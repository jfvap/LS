package pt.isel.ls.input;

import pt.isel.ls.exception.IllegalParametersException;

import java.util.HashMap;

public class Header /*extends Separator*/ {
    private static final String ACCEPT = "accept";
    private static final String FILENAME = "file-name";
    private static final String sep1 = "\\|";
    private static final String sep2 = ":";
    private final HashMap<String, TypePrint> typePrintMap;
    HashMap<String, Object> map;
    public TypePrint typePrint;
    public String fileName;

    public Header() {
        this.typePrintMap = new HashMap<>(2);
        typePrintMap.put("text/plain", TypePrint.PLAIN);
        typePrintMap.put("text/html", TypePrint.HTML);
    }

    public boolean findParameters(String[] args) throws IllegalParametersException {
        boolean b = false;
        this.map = new HashMap<>();
        for (var arg : args ) {
            if (arg.contains(sep2)) {
                this.map = new Separator().mapInfo(arg, sep1, sep2);
                b =  true;
            }
        }
        defineTypePrintAndFileName();
        return b;
    }

    private void defineTypePrintAndFileName() {
        String accept;
        typePrint = (accept = (String) map.get(ACCEPT)) != null ? typePrintMap.get(accept) : TypePrint.PLAIN;
        fileName = (fileName = (String) map.get(FILENAME)) != null ? (String) map.get(fileName) : null;
        /*
        if ((accept = (String) map.get(ACCEPT)) != null) {
            typePrint = typePrintMap.get(accept);
        } else {
            typePrint = TypePrint.PLAIN;
        }
        if ((fileName = (String) map.get(FILENAME)) != null) {
            fileName = (String) map.get(fileName);
        }*/
    }
}
