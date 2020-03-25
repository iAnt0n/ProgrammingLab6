package commands;

import communication.TransferObject;
import exceptions.InvalidArgumentsException;
import utils.UserInterface;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Класс, служащий для построения объекта TransferObject на основе введеной команды
 */
public class CommandBuilder {
    private HashMap<String, Command> commands = new HashMap<>();

    public CommandBuilder(){
        addCmd(new InfoCommand());
        addCmd(new ShowCommand());
        addCmd(new ClearCommand());
        addCmd(new InsertCommand());
        addCmd(new RemoveKeyCommand());
        addCmd(new UpdateIdCommand());
        addCmd(new SaveCommand());
        addCmd(new CountByGovernorCommand());
        addCmd(new RemoveLowerCommand());
        addCmd(new RemoveLowerKeyCommand());
        addCmd(new ReplaceIfLowerCommand());
        addCmd(new MinByPopulationCommand());
        addCmd(new MaxByStandardOfLivingCommand());
        addCmd(new ExecuteScriptCommand());
        addCmd(new HelpCommand());
    }

    private void addCmd(Command cmd){
        commands.put(cmd.getName(), cmd);
    }

    /**
     * Строит объект на основе команды и аргументов, введенных пользователем
     * @param ui интерфейс, служащий для взаимодействия с пользователем
     * @param s строка, на основе которой строится объект
     * @return объект типа {@link TransferObject}
     * @throws IOException при ошибке, которая может произойти, если команда работает с потоками ввода-вывода
     */
    public TransferObject buildCommand(UserInterface ui, String s) throws IOException {
        String[] input = s.trim().split("\\s+");
        String[] simpleArgs = Arrays.copyOfRange(input, 1, input.length);
        Command cmd = commands.get(input[0].toLowerCase());
        if (cmd.getSimpleArgLen() != simpleArgs.length){
            throw new InvalidArgumentsException("Неверные аргументы команды");
        }
        return new TransferObject(cmd.getName(), simpleArgs, cmd.buildArgs(ui, simpleArgs));
    }
}
