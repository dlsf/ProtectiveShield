package net.seliba.protectiveshield.animation;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;

public class ShieldAnimation {

  private static final DustOptions RED_DUST = new DustOptions(Color.RED, 0.5f);
  private static int shieldRadius;
  private static final double[] x = new double[356];
  private static final double[] z = new double[356];
  private static final double[] sin = new double[356];
  private static final double[] cos = new double[356];

  public static void initialize(int radius) {
    shieldRadius = radius;
    for (int i = 0; i < 360; i += 10) {
      x[i] = Math.cos(i) * radius;
      z[i] = Math.sin(i) * radius;
      sin[i] = Math.sin(i);
      cos[i] = Math.cos(i);
    }
  }

  public static void play(Player player) {
    for (int i = 0; i < 360; i += 10) {
      for (double j = -shieldRadius; j < shieldRadius; j += 0.1) {
        double t = Math.acos(j / shieldRadius) * 180 / Math.PI;
        double sinT = Math.sin(t);
        double xAddition = x[i] * sinT;
        double yAddition = z[i] * sinT;
        double zAddition = shieldRadius * Math.cos(t);

        Location particleLocation = player.getLocation().clone().add(xAddition, yAddition, zAddition);
        Location particleLocationInverted = player.getLocation().clone().add(zAddition, yAddition, xAddition);
        particleLocation.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 10, RED_DUST);
        particleLocation.getWorld().spawnParticle(Particle.REDSTONE, particleLocationInverted, 10, RED_DUST);
      }
    }
  }

}
