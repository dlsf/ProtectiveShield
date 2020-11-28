package net.seliba.protectiveshield;

import net.seliba.protectiveshield.commands.ShieldCommand;
import net.seliba.protectiveshield.configuration.Config;
import net.seliba.protectiveshield.scheduler.ShieldScheduler;
import net.seliba.protectiveshield.utils.ActiveShields;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectiveShield extends JavaPlugin implements Listener {

  private ActiveShields activeShields;
  private int shieldRadius;
  private long schedulerInterval;
  private String activatedMessage;
  private String deactivatedMessage;
  private boolean ignoreOtherShields;

  @Override
  public void onEnable() {
    initializeConfig();
    startScheduler();
    registerCommand();

    getLogger().info("Erfolgreich gestartet!");
  }

  private void initializeConfig() {
    Config config = new Config("config.yml", this);
    config.setDefault("shield-radius", 3);
    config.setDefault("scheduler-interval-in-seconds", 0.5);
    config.setDefault("ignore-other-shields", true);
    config.setDefault("messages.prefix", "&c&lSchutzSchild &c&f» &c");
    config.setDefault("messages.activated", "&aDu hast das Schutzschild aktiviert!");
    config.setDefault("messages.deactivated", "&aDu hast das Schutzschild deaktiviert!");
    config.save();

    shieldRadius = config.getInt("shield-radius");
    schedulerInterval = (long) (config.getDouble("scheduler-interval-in-seconds") * 20);
    activatedMessage = ChatColor.translateAlternateColorCodes('&', config.getString("messages.prefix") + config.getString("messages.activated"));
    deactivatedMessage = ChatColor.translateAlternateColorCodes('&', config.getString("messages.prefix") + config.getString("messages.deactivated"));
    ignoreOtherShields = config.getBoolean("ignore-other-shields");
  }

  private void startScheduler() {
    activeShields = new ActiveShields();
    new ShieldScheduler(this, activeShields, shieldRadius, schedulerInterval, ignoreOtherShields).start();
  }

  private void registerCommand() {
    getCommand("schutzschild").setExecutor(new ShieldCommand(activeShields, activatedMessage, deactivatedMessage));
  }

}
