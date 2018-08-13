package CommandLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class Command {
    private static List<Command> commands = new ArrayList<>();

    private String command;
    private Consumer<CommandPacket> consumer;

    private void handle(CommandPacket packet) {
        this.consumer.accept(packet);
    }

    Command(String command, Consumer<CommandPacket> consumer) {
        this.command = command;
        this.consumer = consumer;
        commands.add(this);
    }

    static void emitter(String command, CommandPacket packet) {
        for(Command c : commands) {
            command = command.trim();
            if(c.command.equals(command))
                c.handle(packet);
        }
    }
}
