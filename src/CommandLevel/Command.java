package CommandLevel;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private static List<Command> commands = new ArrayList<Command>();

    private String command;
    private int id = number++;
    private static int number = 0;

    public Command(String command) {
        this.command = command;
        commands.add(this);
    }

    public void emitter(String value) {
        
    }
}
