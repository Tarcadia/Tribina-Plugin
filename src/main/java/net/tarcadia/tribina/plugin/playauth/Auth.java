package net.tarcadia.tribina.plugin.playauth;

import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class Auth {

    private final File file;
    private final Configuration config;

    public Auth(@NotNull String name, @NotNull String fileRoot) {
        this.file = new File(fileRoot + "/" + name + ".yml");
        this.config = Configuration.getConfiguration(file);
    }

    public Auth(@NotNull File file) {
        this.file = file;
        this.config = Configuration.getConfiguration(file);
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
        Set<String> vis = new HashSet<>();
        List<String> lst = new LinkedList<>(this.getStrings(key));
        while (!lst.isEmpty()) {
            var s = lst.remove(0);
            if (!vis.contains(s)) {
                vis.add(s);
                if (this.config.contains(s)) {
                    lst.addAll(this.getStrings(s));
                } else {
                    ret.add(s);
                }
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
