package pt.isel.ls;

import java.sql.Connection;

public interface Consumer {
    String accept(String con);
}
