package net.seliba.protectiveshield.commands;

import net.seliba.protectiveshield.utils.ActiveShields;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShieldCommand implements CommandExecutor {

  private final ActiveShields shields;
  private final String activatedMessage;
  private final String deactivatedMessage;

  public ShieldCommand(ActiveShields shields, String activatedMessage, String deactivatedMessage) {
    this.shields = shields;
    this.activatedMessage = activatedMessage;
    this.deactivatedMessage = deactivatedMessage;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      return false;
    }

    Player player = (Player) sender;
    if (shields.getActivatedShields().contains(player)) {
      shields.removePlayer(player);
      player.sendMessage(deactivatedMessage);
    } else {
      shields.addPlayer(player);
      player.sendMessage(activatedMessage);
    }
    return true;
  }

}
