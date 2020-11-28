package net.seliba.protectiveshield.configuration;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config extends YamlConfiguration {

  private final String name;
  private final JavaPlugin javaPlugin;

  private File file;

  public Config(String name, JavaPlugin javaPlugin) {
    this.name = name;
    this.javaPlugin = javaPlugin;

    reload();
  }

  private void reload() {
    file = new File(javaPlugin.getDataFolder(), name);

    try {
      if (!file.exists())
        if (!file.createNewFile())
          throw new RuntimeException("Could not create ${NAME} " + name);

      load(file);
    } catch (IOException | InvalidConfigurationException e) {
      //Do nothing
    }
  }

  public void save() {
    try {
      save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setDefault(String path, Object value) {

    if(!isSet(path)) {
      set(path, value);
    }

  }

  public void setLocation(String path, Location location) {

    set(path + ".world", location.getWorld().getName());
    set(path + ".x", location.getX());
    set(path + ".y", location.getY());
    set(path + ".z", location.getZ());
    set(path + ".yaw", location.getYaw());
    set(path + ".pitch", location.getPitch());

  }

  public Location getLocation(String path) {

    return getConfigLocation(path, null);

  }

  private Location getConfigLocation(String path, Location def) {

    if (!isSet(path + ".world"))
      return def;

    return new Location(
        Bukkit.getWorld(getString(path + ".world")),
        getDouble(path + ".x"),
        getDouble(path + ".y"),
        getDouble(path + ".z"),
        (float) getDouble(path + ".yaw"),
        (float) getDouble(path + ".pitch")
    );

  }

}