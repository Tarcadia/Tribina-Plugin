package net.tarcadia.tribina.plugin.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Test;

import java.io.File;

class ConfigurationTest {

    private void sleep(long millis) {
        var t = System.currentTimeMillis();
        while (System.currentTimeMillis() < t + millis) {
            try {
                Thread.sleep(t + millis - System.currentTimeMillis());
            } catch (InterruptedException e) {}
        }
    }

    @Test
    void readQueryWrite() {
        YamlConfiguration ymlConfig = YamlConfiguration.loadConfiguration(new File("src/test/resources/ConfigurationTest/A.yml"));
        Configuration configA = new Configuration("src/test/resources/ConfigurationTest/B.yml", ymlConfig, 500);
        Configuration configB = new Configuration("src/test/resources/ConfigurationTest/B.yml", ymlConfig, 500);
        ymlConfig.set("a.b.r", 7);
        System.out.println(configA.getString("a.b.q"));
        System.out.println(configA.getString("a.b.w"));
        System.out.println(configA.getString("a.b.e"));
        configB.set("a.b.e", 5);
        sleep(1000);
        ymlConfig.set("a.b.r", 7);
        System.out.println(configA.getString("a.b.q"));
        System.out.println(configA.getString("a.b.w"));
        System.out.println(configA.getString("a.b.e"));
        sleep(2000);
        System.out.println(configA.getString("a.b.r"));
        System.out.println(configA.getString("a.c"));
        System.out.println(configA.getString("d"));
    }
}