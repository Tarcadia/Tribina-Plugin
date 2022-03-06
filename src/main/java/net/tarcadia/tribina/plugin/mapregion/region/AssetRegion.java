package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.region.base.BaseDisjointSubRegion;
import net.tarcadia.tribina.plugin.mapregion.region.base.BaseRegion;
import net.tarcadia.tribina.plugin.mapregion.region.base.DisjointRegion;
import net.tarcadia.tribina.plugin.mapregion.region.base.ParentRegion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class AssetRegion extends BaseDisjointSubRegion {

    public static String KEY_ASSET_AUTHS = "asset-info.auths";
    public static String KEY_ASSET_OWNER = "asset-info.owner";
    public static String AUTH_REGION_ASSET_OWNER = "region.asset.owner";

    public AssetRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends DisjointRegion> peers,
            @NotNull ParentRegion parent
    ) {
        super(regionId, fileRoot, peers, parent);
    }

    @Override
    @NotNull
    public List<String> getAuth() {
        if (!this.isNull()) {
            return this.config().getStringList(BaseRegion.KEY_AUTH);
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    @NotNull
    public List<String> getAuth(@NotNull Player player) {
        List<String> pAuth = new LinkedList<>();
        if (!this.isNull()) {
            if (this.config().getString(KEY_ASSET_OWNER, "").equals(player.getName())) {
                pAuth.add(AUTH_REGION_ASSET_OWNER);
            }
            pAuth.addAll(this.config().getStringList(KEY_ASSET_AUTHS + "." + player.getName()));
            pAuth.addAll(this.config().getStringList(BaseRegion.KEY_AUTH));
        }
        return pAuth;
    }

    // TODO: More implements that make asset a region that is a region

}
