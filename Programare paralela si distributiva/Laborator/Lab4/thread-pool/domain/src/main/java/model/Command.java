package model;

import java.io.Serializable;

/**
 * Created by mtoader on 12/13/2017.
 */
public class Command implements Serializable {
    public String command;
    public Object payload;

    public Command(String command, Object payload) {
        this.command = command;
        this.payload = payload;
    }

    public Command() {
    }
}
