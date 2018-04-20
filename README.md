# MelerideCommands [![Build Status](https://travis-ci.org/meleride/MelerideCommands.svg?branch=master)](https://travis-ci.org/meleride/MelerideCommands)

API for Commands in Bukkit.

## Example usage
```java
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.commands.CommandInfo;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.commands.util.CommandUtils;

public final class ExamplePlugin extends JavaPlugin {

  private Commands commands;

  @Override
  public void onLoad() {
  }

  @Override
  public void onEnable() {
    this.commands = new BukkitCommands(this);
    this.commands.registerCommandObjects(new ExampleCommand(this));
  }

  @Override
  public void onDisable() {
  }

  public final static class ExampleCommand {

    private final ExamplePlugin plugin;

    public ExampleCommand(ExamplePlugin plugin) {
      this.plugin = plugin;
    }

    @CommandInfo(
        name = {"tpto"},
        description = "Teleportuj siebie do innego gracza",
        min = 1,
        flags = "silent",
        usage = "[-silent] <player>",
        userOnly = true,
        permission = "tpto.use",
        completer = "teleportCompleter")
    public void teleportCommand(CommandSender sender, CommandContext context) {
      Player player = CommandUtils.toPlayer(sender);
      Player target = this.plugin.getServer().getPlayer(context.getParam(0));
      if (target == null) {
        return;
      }

      target.teleport(player);
      player.sendMessage(ChatColor.RED + "Przeteleportowano gracza " + target.getName() + " do Ciebie.");

      if (!context.hasFlag("silent")) {
        target.sendMessage(ChatColor.RED + "Przeleleportowano Ciebie do gracza o nicku " + player.getName() + ".");
      }
    }

    public List<String> teleportCompleter(CommandSender sender, CommandContext context) {
      if (context.getParamsLength() == 1) {
        return this.plugin.getServer().getOnlinePlayers()
            .stream()
            .map(Player::getName)
            .filter(name -> !name.equals(sender.getName()))
            .collect(Collectors.toList());
      }

      return Collections.emptyList();
    }

  }

}

```

## License
[MIT](LICENSE)

## Credits
[TheMolkaPL](https://github.com/TheMolkaPL) for [BukkitCommands](https://github.com/TheMolkaPL/BukkitCommands)
