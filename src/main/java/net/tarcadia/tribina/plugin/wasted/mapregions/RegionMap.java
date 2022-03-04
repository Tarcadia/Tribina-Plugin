package net.tarcadia.tribina.plugin.wasted.mapregions;

import net.tarcadia.tribina.plugin.util.type.Pair;
import net.tarcadia.tribina.plugin.util.func.Bitmaps;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public class RegionMap {
	private final String pathConfig;
	private final String pathMaps;
	private final File fileConfig;

	private final Map<Pair<Integer, Integer>, String> regionMap;

	private YamlConfiguration config;
	private ConfigurationSection configRegions;

	private String world;
	private int x_offset;
	private int z_offset;
	private int x_length;
	private int z_length;

	public RegionMap(@NonNull String pathConfig, @NonNull String pathMaps) {
		this.pathConfig = pathConfig;
		this.pathMaps = pathMaps;
		this.fileConfig = new File(this.pathConfig);
		this.regionMap = new HashMap<>();
		try {
			this.load();
		} catch (Exception e) {
			Main.logger.log(
					Level.SEVERE,
					"Cannot load region map at " +
							"pathConfig = '" + pathConfig + "', " +
							"pathMaps = '" + pathMaps + "'.",
					e
			);
		}
	}

	public void load() throws Exception {
		this.loadConfigs();
		this.loadAllMaps();
	}

	public void loadConfigs() throws Exception {
		this.config = YamlConfiguration.loadConfiguration(this.fileConfig);
		this.configRegions = Objects.requireNonNullElseGet(
				this.config.getConfigurationSection("regions"),
				() -> this.config.createSection("regions")
		);
		try {
			this.world = this.config.getString("world");
			this.x_offset = this.config.getInt("x_offset");
			this.z_offset = this.config.getInt("z_offset");
			this.x_length = this.config.getInt("x_length");
			this.z_length = this.config.getInt("z_length");
		} catch (Exception e) {
			this.world = "";
			this.x_offset = 0;
			this.x_length = 0;
			this.z_offset = 0;
			this.z_length = 0;
			throw new Exception("Load config file failed.", e);
		}
	}

	public void loadMap(@NonNull String regionId) throws Exception {
		try {
			File fileImage = new File(this.pathMaps + "/" + regionId + ".bmp");
			var posSet = Bitmaps.loadBmpToSet(fileImage);
			this.addToRegion(posSet, regionId);
		} catch (Exception e) {
			throw new Exception("Load map file failed.", e);
		}
	}

	public void loadAllMaps() throws Exception {
		List<Exception> es = new LinkedList<>();
		for (String regionId : this.configRegions.getKeys(false)) {
			try {
				this.loadMap(regionId);
			} catch (Exception e) {
				es.add(e);
			}
		}
		if (!es.isEmpty()) {
			throw new Exception("Load map files failed.", es.get(0));
		}
	}

	public void save() throws Exception {
		this.saveConfigs();
		this.saveAllMaps();
	}

	public void saveConfigs() throws Exception {
		if (this.world != null && Main.plugin.getServer().getWorld(this.world) != null) {
			try {
				this.config.set("world", this.world);
				this.config.set("x_offset", this.x_offset);
				this.config.set("z_offset", this.z_offset);
				this.config.set("x_length", this.x_length);
				this.config.set("z_length", this.z_length);
				this.config.save(this.fileConfig);
			} catch (Exception e) {
				throw new Exception("Save config file failed.", e);
			}
		} else {
			throw new Exception("Save config invalid.");
		}
	}

	public void saveMap(@NonNull String regionId) throws Exception {
		Set<Pair<Integer, Integer>> posSet = new HashSet<>();

		for (var e : this.regionMap.entrySet()) {
			if (regionId.equals(e.getValue())) {
				posSet.add(e.getKey());
			}
		}
		try {
			File fileImage = new File(this.pathMaps + "/" + regionId + ".bmp");
			Bitmaps.saveSetToBmp(posSet, fileImage);
		} catch (Exception e) {
			throw new Exception("Save map file failed.", e);
		}
	}

	public void saveAllMaps() throws Exception {
		List<Exception> es = new LinkedList<>();

		Map<String, Set<Pair<Integer, Integer>>> posSets = new HashMap<>();
		for (var e : this.regionMap.entrySet()) {
			var regionId = e.getValue();
			var posSet = posSets.computeIfAbsent(regionId, k -> new HashSet<>());
			posSet.add(e.getKey());
		}

		for (String regionId : this.configRegions.getKeys(false)) {
			var posSet = posSets.get(regionId);
			try {
				File fileImage = new File(this.pathMaps + "/" + regionId + ".bmp");
				Bitmaps.saveSetToBmp(posSet, fileImage);
			} catch (Exception e) {
				es.add(e);
			}
		}
		if (!es.isEmpty()) {
			throw new Exception("Save map files failed.", es.get(1));
		}
	}

	public YamlConfiguration getConfig() { return this.config; }

	public void setValue(@NonNull String regionId, @NonNull String key, Object obj) {
		Objects.requireNonNull(this.configRegions.getConfigurationSection(regionId)).set(key, obj);
	}

	public Object getValue(@NonNull String regionId, @NonNull String key) {
		return Objects.requireNonNull(this.configRegions.getConfigurationSection(regionId)).get(key);
	}

	public void createRegion(@NonNull String regionId) {
		// TODO: check if regionId is a suitable string for id;
		ConfigurationSection configSection = this.configRegions.createSection(regionId);
	}

	public boolean inRegionList(String regionId) { return this.configRegions.getKeys(false).contains(regionId); }

	public List<String> getRegionList() {
		return List.copyOf(this.configRegions.getKeys(false));
	}

	public boolean inRegion(int x, int z, @NonNull String regionId) {
		return regionId.equals(this.regionMap.get(new Pair<>(x - this.x_offset, z - this.z_offset)));
	}

	public boolean inRegion(@NonNull String world, int x, int z, @NonNull String regionId) {
		return world.equals(this.world) && this.inRegion(x, z, regionId);
	}

	public boolean inRegion(@NonNull Pair<Integer, Integer> pos, @NonNull String regionId) {
		return this.inRegion(pos.x(), pos.y(), regionId);
	}

	public boolean inRegion(@NonNull Location loc, @NonNull String regionId) {
		return this.inRegion(Objects.requireNonNull(loc.getWorld()).getName(), loc.getBlockX(), loc.getBlockZ(), regionId);
	}

	public String getRegion(int x, int z) {
		return this.regionMap.get(new Pair<>(x - this.x_offset, z - this.z_offset));
	}

	public String getRegion(@NonNull String world, int x, int z) {
		return (world.equals(this.world)) ? this.getRegion(x, z) : null;
	}

	public String getRegion(@NonNull Pair<Integer, Integer> pos) {
		return this.getRegion(pos.x(), pos.y());
	}

	public String getRegion(@NonNull Location loc) {
		return this.getRegion(Objects.requireNonNull(loc.getWorld()).getName(), loc.getBlockX(), loc.getBlockZ());
	}

	public void addToRegion(int x, int z, @NonNull String regionId) {
		int _x = x - this.x_offset;
		int _z = z - this.z_offset;
		if (_x >= 0 && _z >= 0 && _x < this.x_length && _z < this.z_length) {
			this.regionMap.putIfAbsent(new Pair<>(_x, _z), regionId);
		}
	}

	public void addToRegion(@NonNull String world, int x, int z, @NonNull String regionId) {
		if (world.equals(this.world)) {
			this.addToRegion(x, z, regionId);
		}
	}

	public void addToRegion(@NonNull Pair<Integer, Integer> pos, @NonNull String regionId) {
		this.addToRegion(pos.x(), pos.y(), regionId);
	}

	public void addToRegion(@NonNull String world, @NonNull Pair<Integer, Integer> pos, @NonNull String regionId) {
		this.addToRegion(world, pos.x(), pos.y(), regionId);
	}

	public void addToRegion(@NonNull Collection<Pair<Integer, Integer>> posSet, @NonNull String regionId) {
		for (var pos : posSet) {
			this.addToRegion(pos.x(), pos.y(), regionId);
		}
	}

	public void addToRegion(@NonNull String world, @NonNull Collection<Pair<Integer, Integer>> posSet, @NonNull String regionId) {
		if (world.equals(this.world)) {
			this.addToRegion(posSet, regionId);
		}
	}

	public void addToRegion(@NonNull Location loc, @NonNull String regionId) {
		this.addToRegion(Objects.requireNonNull(loc.getWorld()).getName(), loc.getBlockX(), loc.getBlockZ(), regionId);
	}

	public void addCoverToRegion(int x, int z, @NonNull String regionId) {
		int _x = x - this.x_offset;
		int _z = z - this.z_offset;
		if (_x >= 0 && _z >= 0 && _x < this.x_length && _z < this.z_length) {
			this.regionMap.put(new Pair<>(_x, _z), regionId);
		}
	}

	public void addCoverToRegion(@NonNull String world, int x, int z, @NonNull String regionId) {
		if (world.equals(this.world)) {
			this.addCoverToRegion(x, z, regionId);
		}
	}

	public void addCoverToRegion(@NonNull Pair<Integer, Integer> pos, @NonNull String regionId) {
		this.addCoverToRegion(pos.x(), pos.y(), regionId);
	}

	public void addCoverToRegion(@NonNull String world, @NonNull Pair<Integer, Integer> pos, @NonNull String regionId) {
		this.addCoverToRegion(world, pos.x(), pos.y(), regionId);
	}

	public void addCoverToRegion(@NonNull Collection<Pair<Integer, Integer>> posSet, @NonNull String regionId) {
		for (var pos : posSet) {
			this.addCoverToRegion(pos.x(), pos.y(), regionId);
		}
	}

	public void addCoverToRegion(@NonNull String world, @NonNull Collection<Pair<Integer, Integer>> posSet, @NonNull String regionId) {
		if (world.equals(this.world)) {
			this.addCoverToRegion(posSet, regionId);
		}
	}

	public void addCoverToRegion(@NonNull Location loc, @NonNull String regionId) {
		this.addCoverToRegion(Objects.requireNonNull(loc.getWorld()).getName(), loc.getBlockX(), loc.getBlockZ(), regionId);
	}
}
