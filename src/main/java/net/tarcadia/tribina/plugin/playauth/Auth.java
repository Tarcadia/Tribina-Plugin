package net.tarcadia.tribina.plugin.playauth;

import net.tarcadia.tribina.plugin.util.Configuration;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Auth {

    private final String filePath;
    private final Configuration config;

    public Auth(@NotNull String name, @NotNull String fileRoot, int ttl) {
        this.filePath = fileRoot + "/" + name;
        this.config = new Configuration(this.filePath + ".yml", ttl);
    }

    public Auth(@NotNull String filePath, int ttl) {
        this.filePath = filePath;
        this.config = new Configuration(this.filePath + ".yml", ttl);
    }

    public Auth(@NotNull String name, @NotNull String fileRoot) {
        this.filePath = fileRoot + "/" + name;
        this.config = new Configuration(this.filePath + ".yml");
    }

    public Auth(@NotNull String filePath) {
        this.filePath = filePath;
        this.config = new Configuration(this.filePath + ".yml");
    }

    @NotNull
    private Collection<String> getStrings(@NotNull String key) {
        if (this.config.isString(key)) {
            return Set.of(this.config.getString(key));
        } else if (this.config.isList(key)) {
            return this.config.getStringList(key);
        } else if (this.config.isConfigurationSection(key)) {
            Set<String> ret = new HashSet<>();
            for (var subKey : this.config.getConfigurationSection(key).getKeys(true)) {
                ret.addAll(this.getStrings(key + "." + subKey));
            }
            return ret;
        } else {
            return new HashSet<>();
        }
    }

    @NotNull
    public Set<String> getAuth(@NotNull String key) {
        Set<String> ret = new HashSet<>();
        List<String> lst = new LinkedList<>(this.getStrings(key));
        while (!lst.isEmpty()) {
            var s = lst.remove(0);
            if (this.config.contains(s)) {
                lst.addAll(this.getStrings(s));
            } else {
                ret.add(s);
            }
        }
        return ret;
    }

    @NotNull
    public Set<String> getAuth(@NotNull Collection<String> keys) {
        Set<String> ret = new HashSet<>();
        for (var key : keys) {
            ret.addAll(this.getAuth(key));
        }
        return ret;
    }

}
