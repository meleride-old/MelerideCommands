package pl.meleride.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;
import pl.meleride.commands.context.CommandContext;

public class Command {

  private final String[] name;
  private final String[] flags;
  private final Method method;
  private final Object classObject;
  private final Method completer;
  private String description;
  private int min;
  private String usage;
  private boolean userOnly;
  private String permission;

  public Command(String[] name, String description, int min, String usage, boolean userOnly,
      String[] flags,
      String permission, Method method, Object classObject, Method completer) {
    this.name = name;
    this.description = description;
    this.min = min;
    this.usage = usage;
    this.userOnly = userOnly;
    this.flags = flags;
    this.permission = permission;
    this.method = method;
    this.classObject = classObject;
    this.completer = completer;
  }

  public String getCommand() {
    return this.getName()[0];
  }

  public String[] getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getMin() {
    return this.min;
  }

  public void setMin(int min) {
    this.min = min;
  }

  public String getUsage() {
    return ("/" + this.getCommand() + " " + this.usage).trim();
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public String[] getFlags() {
    return this.flags;
  }

  public String getPermission() {
    return this.permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public Method getMethod() {
    return this.method;
  }

  public Object getClassObject() {
    return this.classObject;
  }

  public Method getCompleter() {
    return this.completer;
  }

  public void handleCommand(CommandSender sender, CommandContext context) throws Throwable {
    if (this.getMethod() == null) {
      return;
    }

    try {
      this.getMethod().setAccessible(true);
      this.getMethod().invoke(this.getClassObject(), sender, context);
    } catch (InvocationTargetException ex) {
      throw ex.getTargetException();
    }
  }

  public List<String> handleCompleter(CommandSender sender, CommandContext context)
      throws Throwable {
    if (this.getCompleter() == null) {
      return new ArrayList<>();
    }

    try {
      this.getCompleter().setAccessible(true);
      Object result = this.getCompleter().invoke(this.getClassObject(), sender, context);

      if (result == null) {
        return new ArrayList<>();
      }

      if (result instanceof List) {
        return (List<String>) result;
      } else {
        return new ArrayList<>();
      }
    } catch (InvocationTargetException ex) {
      throw ex.getTargetException();
    }
  }

  public boolean hasCompleter() {
    return this.completer != null;
  }

  public boolean hasFlag(String flag) {
    for (String f : this.flags) {
      if (f.equals(flag)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasPermission() {
    return this.permission != null;
  }

  public boolean isUserOnly() {
    return this.userOnly;
  }

  public void setUserOnly(boolean userOnly) {
    this.userOnly = userOnly;
  }

}
