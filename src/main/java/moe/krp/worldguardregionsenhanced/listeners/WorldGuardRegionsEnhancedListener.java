package moe.krp.worldguardregionsenhanced.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import moe.krp.worldguardregionsenhanced.WorldGuardRegionsEnhanced;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class WorldGuardRegionsEnhancedListener implements Listener {

    @EventHandler
    public void onItemUse(PlayerBucketEmptyEvent event) {
        if (event.getBucket() != Material.LAVA_BUCKET || event.getPlayer().hasPermission("worldguardregionsenhanced.bypass")) {
            return;
        }
        final Location eventLocation = BukkitAdapter.adapt(event.getBlockClicked().getLocation());
        final RegionContainer container = WorldGuard.getInstance().getPlatform()
                                                    .getRegionContainer();
        final RegionQuery query = container.createQuery();
        final boolean canPlaceLavaRes = query.testState(eventLocation, null, WorldGuardRegionsEnhanced.CAN_PLACE_LAVA);

        if (!canPlaceLavaRes) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE +
                    "You are not allowed to place lava here."
            );
        }
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        if (event.getItem().getType() != Material.LAVA_BUCKET) {
            return;
        }

        final Location eventLocation = BukkitAdapter.adapt(event.getBlock().getLocation());
        final RegionContainer container = WorldGuard.getInstance().getPlatform()
                                                    .getRegionContainer();
        final RegionQuery query = container.createQuery();
        final boolean canPlaceLavaRes = query.testState(eventLocation, null, WorldGuardRegionsEnhanced.CAN_PLACE_LAVA);

        if (!canPlaceLavaRes) {
            event.setCancelled(true);
        }
    }
}
