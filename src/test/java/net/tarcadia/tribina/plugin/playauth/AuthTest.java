package net.tarcadia.tribina.plugin.playauth;

import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AuthTest {

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
        File fileA = new File("src/test/resources/playauth/AuthTest/A.yml");
        Auth auth = new Auth(fileA);
        Configuration config = Configuration.getConfiguration(fileA);

        System.out.println(auth.getAuth("play"));
        System.out.println(auth.getAuth("play.place.dirt"));
        System.out.println(auth.getAuth("play.place.grass"));
        System.out.println(auth.getAuth("play.place.flower"));
        System.out.println(auth.getAuth("play.break.dirt"));
        System.out.println(auth.getAuth("play.break.grass"));
        System.out.println(auth.getAuth("play.break.flower"));
        config.set("play.break.flower", "play_break_flower");
        System.out.println(auth.getAuth("play.break.flower"));
        sleep(5000);
        System.out.println(auth.getAuth("play.break.flower"));
        config.set("play.break.flower", null);
        System.out.println(auth.getAuth("play.place"));
        System.out.println(auth.getAuth("play.break"));
        System.out.println(auth.getAuth("play.place*"));
        sleep(5000);
        System.out.println(auth.getAuth("play.place"));
        System.out.println(auth.getAuth("play.break"));
        System.out.println(auth.getAuth("play.place*"));
    }
}