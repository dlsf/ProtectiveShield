package net.seliba.protectiveshield.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public class ActiveShields {

  private final List<Player> activatedShields = new ArrayList<>();

  public void addPlayer(Player player) {
    activatedShields.add(player);
  }

  public void removePlayer(Player player) {
    activatedShields.remove(player);
  }

  public List<Player> getActivatedShields() {
    return activatedShields;
  }

}
