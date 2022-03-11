package net.tarcadia.tribina.erod.login;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.util.*;

public interface InterfaceLogIn {

    boolean enabled();
    void enable();
    void disable();

    long timeLogin(@NotNull Player player);
    @NotNull String getWelcome(@NotNull Player player);

    boolean hasPlayer(@NotNull Player player);
    boolean regPlayer(@NotNull Player player, @NotNull String password);
    boolean loginPlayer(@NotNull Player player, @NotNull String password);
    void logoutPlayer(@NotNull Player player);
}
