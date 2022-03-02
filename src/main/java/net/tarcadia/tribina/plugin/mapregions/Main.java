package net.tarcadia.tribina.plugin.mapregions;

import net.tarcadia.tribina.plugins.mapregions.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

	public static Main plugin = null;
	public static FileConfiguration config = null;
	public static PluginDescriptionFile descrp = null;
	public static Logger logger = null;
	public static String dataPath = null;

	private RegionMaps regionMaps;
	private List<BaseCommand> commands;

	@Override
	public void onLoad() {
		Main.plugin = this;
		Main.config = this.getConfig();
		Main.descrp = this.getDescription();
		Main.logger = this.getLogger();
		Main.dataPath = this.getDataFolder().getPath();
		Main.logger.info("Loaded " + Main.descrp.getName() + " v" + Main.descrp.getVersion() + ".");
	}

	@Override
	public void onEnable() {
		this.regionMaps = new RegionMaps(Main.config, Main.dataPath);
		this.commands = new LinkedList<>();
		this.commands.add(new CommandListMaps("tribina-mr-list-maps"));
		this.commands.add(new CommandListRegions("tribina-mr-list-regions"));
		this.commands.add(new CommandReloadConfigs("tribina-mr-reload-configs"));
		this.commands.add(new CommandReloadMap("tribina-mr-reload-map"));
		Main.logger.info("Enabled " + Main.descrp.getName() + " v" + Main.descrp.getVersion() + ".");
	}

	@Override
	public void onDisable() {
		this.saveDefaultConfig();
		this.regionMaps.save();
		Main.logger.info("Disabled " + Main.descrp.getName() + " v" + Main.descrp.getVersion() + ".");
	}

	public void reloadConfigs() {
		this.reloadConfig();
		Main.config = this.getConfig();
		this.regionMaps = new RegionMaps(Main.config, Main.dataPath);
		Main.logger.info("Reloaded configs.");
	}

	public void reloadMap(String mapId) {
		if (this.regionMaps.inMapList(mapId)) {
			this.regionMaps.reloadMap(mapId);
			Main.logger.info("Reloaded map " + mapId + ".");
		} else {
			Main.logger.info("Not found map " + mapId + ".");
		}
	}

	public List<String> getMapList() {
		return this.regionMaps.getMapList();
	}

	public List<String> getRegionList(String mapId) {
		return this.regionMaps.getRegionList(mapId);
	}
}
