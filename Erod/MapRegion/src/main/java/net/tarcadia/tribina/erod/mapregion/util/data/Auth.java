package net.tarcadia.tribina.erod.mapregion.util.data;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class Auth {

    private final File file;
    private final Configuration config;

    public Auth(@NotNull File file) {
        this.file = file;
        this.config = Configuration.getConfiguration(file);
    }

    @NotNull
    private Collection<String> getStrings(@NotNull String key) {
        if (this.config.isString(key)) {
            var s = this.config.getString(key);
            if (s != null) return Set.of(s);
            else return Set.of();
        } else if (this.config.isList(key)) {
            return this.config.getStringList(key);
        } else if (this.config.isConfigurationSection(key)) {
            var s = this.config.getConfigurationSection(key);
            var ret = new HashSet<String>();
            if (s != null) {
                for (var subKey : s.getKeys(true)) {
                    ret.addAll(this.getStrings(key + "." + subKey));
                }
            }
            return ret;
        } else {
            return new HashSet<>();
        }
    }

    @NotNull
    public Set<String> getPlaySet(@NotNull String authTag) {
        var ret = new HashSet<String>();
        var vis = new HashSet<String>();
        var lst = new LinkedList<>(this.getStrings(authTag));
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
    public Set<String> getPlaySet(@NotNull Collection<String> authTags) {
        Set<String> ret = new HashSet<>();
        for (var key : authTags) {
            ret.addAll(this.getPlaySet(key));
        }
        return ret;
    }

}
