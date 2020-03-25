package commands;

import collection.CollectionManager;
import communication.TransferObject;

/**
 * Класс, реализующий команду min_by_population
 */
public class MinByPopulationCommand extends Command {
    public MinByPopulationCommand(){
        name = "min_by_population";
        helpString = "вывести любой объект из коллекции, значение поля population которого является минимальным";
        argLen = 0;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        return cm.minByPopulation();
    }
}
