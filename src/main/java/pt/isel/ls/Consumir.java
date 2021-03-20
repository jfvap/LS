package pt.isel.ls;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

class Consumir {
     public   /*<T>*/ String paraCada(Consumer consumidor, String  con/*, Collection<T> fonte*/) {
        /*for (T elemento : fonte)
            consumidor.accept(elemento);*/
        return consumidor.accept(con);
    }

    public String abc(String s) {
        System.out.println("ENTROU!!!!!!!");
        System.out.println(s);
        return "foda-se";
    }

    public static void main(String[] args) {
        //List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);
        Consumir consumir = new Consumir();
        String s = consumir.paraCada((n) -> { return consumir.abc(n); }, "ola mundo");
        System.out.println(s);
    }
}
