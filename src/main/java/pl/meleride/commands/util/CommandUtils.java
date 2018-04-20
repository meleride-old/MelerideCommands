package pl.meleride.commands.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.commands.exception.CommandConsoleException;

public class CommandUtils {

  public static Player toPlayer(CommandSender sender) {
    Validate.notNull(sender);

    if (sender instanceof Player) {
      return (Player) sender;
    }

    throw new CommandConsoleException();
  }

}
