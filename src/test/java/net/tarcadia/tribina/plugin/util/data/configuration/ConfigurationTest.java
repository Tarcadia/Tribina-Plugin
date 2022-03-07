package net.tarcadia.tribina.plugin.util.data.configuration;

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
    void doTest() {
        File fileA = new File("src/test/resources/util/data/configuration/ConfigurationTest/A.yml");
        File fileB = new File("src/test/resources/util/data/configuration/ConfigurationTest/B.yml");
        YamlConfiguration ymlConfig = YamlConfiguration.loadConfiguration(fileA);
        Configuration configA = Configuration.getConfiguration(fileB);
        Configuration configB = Configuration.getConfiguration(fileB);
        configA.setDefaults(ymlConfig);
        configB.setDefaults(ymlConfig);
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