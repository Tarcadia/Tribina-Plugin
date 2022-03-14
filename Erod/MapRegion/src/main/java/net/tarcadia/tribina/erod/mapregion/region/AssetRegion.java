package net.tarcadia.tribina.erod.mapregion.region;

import net.tarcadia.tribina.erod.mapregion.region.base.BaseDisjointSubRegion;
import net.tarcadia.tribina.erod.mapregion.region.base.BaseRegion;
import net.tarcadia.tribina.erod.mapregion.region.base.DisjointRegion;
import net.tarcadia.tribina.erod.mapregion.region.base.ParentRegion;
import net.tarcadia.tribina.erod.mapregion.util.data.Configuration;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class AssetRegion extends BaseDisjointSubRegion {

    public static final String KEY_ASSET_OWNER = "asset-info.owner";
    public static final String KEY_ASSET_AUTH_OWNER = "asset-info.auth-owner";
    public static final String KEY_ASSET_AUTHS = "asset-info.auths";

    public static void initAssetRegion(@NotNull String id, @NotNull File fileConfig, @NotNull File fileBitmap, @NotNull Location loc) {
        BaseRegion.initBaseRegion(id, fileConfig, fileBitmap, loc);
        var config = Configuration.getConfiguration(fileConfig);
        config.set(KEY_ASSET_OWNER, null);
        config.set(KEY_ASSET_AUTH_OWNER, List.of());
        config.set(KEY_ASSET_AUTHS, null);
        // TODO: More init, when further implements finished.
    }

    public static AssetRegion create(
            @NotNull String id,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull TownRegion parent,
            @NotNull Location loc
    ) {
        AssetRegion.initBaseRegion(id, fileConfig, fileBitmap, loc);
        return new AssetRegion(id, fileConfig, fileBitmap, parent.assets(), parent);
    }

    public AssetRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends DisjointRegion> peers,
            @NotNull ParentRegion parent
    ) {
        super(regionId, fileRoot, peers, parent);
    }

    public AssetRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull List<? extends DisjointRegion> peers,
            @NotNull ParentRegion parent
    ) {
        super(regionId, fileConfig, fileBitmap, peers, parent);
    }

    @Override
    @NotNull
    public List<String> getAuthTags() {
        if (!this.isNull()) {
            return Objects.requireNonNull(this.config()).getStringList(BaseRegion.KEY_AUTH);
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    @NotNull
    public List<String> getAuthTags(@NotNull Player player) {
        List<String> pAuth = new LinkedList<>();
        if (!this.isNull()) {
            pAuth.addAll(Objects.requireNonNull(this.config()).getStringList(BaseRegion.KEY_AUTH));
            pAuth.addAll(Objects.requireNonNull(this.config()).getStringList(KEY_ASSET_AUTHS + "." + player.getName()));
            if (Objects.requireNonNull(this.config()).getString(KEY_ASSET_OWNER, "").equals(player.getName())) {
                pAuth.addAll(Objects.requireNonNull(this.config()).getStringList(KEY_ASSET_AUTH_OWNER));
            }
            pAuth.addAll(Objects.requireNonNull(this.config()).getStringList(KEY_ASSET_AUTHS + "." + player.getName()));
            pAuth.addAll(Objects.requireNonNull(this.config()).getStringList(BaseRegion.KEY_AUTH));
        }
        return pAuth;
    }

    // TODO: More implements that make asset a region that is a region

}
