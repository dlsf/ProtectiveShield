package net.seliba.protectiveshield.scheduler;

import java.util.Objects;
import net.seliba.protectiveshield.animation.ShieldAnimation;
import net.seliba.protectiveshield.utils.ActiveShields;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ShieldScheduler {

  private final JavaPlugin plugin;
  private final ActiveShields shields;
  private final int shieldRadius;
  private final long interval;
  private final boolean ignoreOtherShields;

  public ShieldScheduler(JavaPlugin javaPlugin, ActiveShields shields, int shieldRadius, long interval, boolean ignoreOtherShields) {
    this.plugin = javaPlugin;
    this.shields = shields;
    this.shieldRadius = shieldRadius;
    this.interval = interval;
    this.ignoreOtherShields = ignoreOtherShields;

    ShieldAnimation.initialize(shieldRadius);
  }

  public void start() {
    Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
      shields.getActivatedShields().stream()
          .filter(Objects::nonNull)
          .filter(Player::isOnline)
          .forEach(player -> {
            ShieldAnimation.play(player);
            Bukkit.getOnlinePlayers().stream()
                .filter(otherPlayer -> otherPlayer != player)
                .filter(otherPlayer -> otherPlayer.getWorld() == player.getWorld())
                .filter(otherPlayer -> otherPlayer.getLocation().distance(player.getLocation()) < shieldRadius)
                .filter(otherPlayer -> !(ignoreOtherShields && otherPlayer.hasPermission("schutzschild.use")))
                .forEach(otherPlayer -> otherPlayer.setVelocity(otherPlayer.getLocation().toVector().subtract(player.getLocation().toVector()).setY(0.5)));
          });
    }, interval, interval);
  }

}
