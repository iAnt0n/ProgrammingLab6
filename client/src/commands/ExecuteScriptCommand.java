package commands;

import utils.UserInterface;
import java.nio.file.Paths;

/**
 * Класс, реализующий команду execute_script
 */
public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand() {
        name = "execute_script";
        simpleArgLen = 1;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        return Paths.get(simpArgs[0]);
    }
}
