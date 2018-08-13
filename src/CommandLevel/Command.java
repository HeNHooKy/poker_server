package CommandLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Command {
    private static List<Command> commands = new ArrayList<>();

    private String command;
    private Consumer<String> consumer;

    private void handle(String value) {
        this.consumer.accept(value);
    }

    public Command(String command, Consumer<String> consumer) {
        this.command = command;
        this.consumer = consumer;
        commands.add(this);
    }

    public void emitter(String command, String value) {
        for(Command c : commands) {
            command = command.trim();
            if(c.command.equals(command))
                c.handle(value);
        }
    }
}
