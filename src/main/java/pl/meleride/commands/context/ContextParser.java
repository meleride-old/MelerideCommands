package pl.meleride.commands.context;

import pl.meleride.commands.Command;

public interface ContextParser {

  CommandContext parse(Command command, String label, String[] args);

}
