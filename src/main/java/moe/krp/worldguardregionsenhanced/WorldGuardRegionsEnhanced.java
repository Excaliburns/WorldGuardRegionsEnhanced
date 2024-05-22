package moe.krp.worldguardregionsenhanced;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import moe.krp.worldguardregionsenhanced.listeners.WorldGuardRegionsEnhancedListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class WorldGuardRegionsEnhanced extends JavaPlugin {
    final static Logger logger = Bukkit.getLogger();
    public static StateFlag CAN_PLACE_LAVA;

    @Override
    public void onLoad() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            StateFlag flag = new StateFlag("lava-placeable", true);
            registry.register(flag);
            CAN_PLACE_LAVA = flag;
            logger.info("lava-placeable custom flag registered");
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("lava-placeable");
            if (existing instanceof StateFlag) {
                CAN_PLACE_LAVA = (StateFlag) existing;
            } else {
                logger.warning("Failed to register flag 'lava-placeable' - a flag with the same name already exists!");
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }
    }

    @Override
    public void onEnable() {
        logger.info("WorldGuardRegionsEnhanced has been enabled.");
        getServer().getPluginManager().registerEvents(new WorldGuardRegionsEnhancedListener(), this);
    }

    @Override
    public void onDisable() {
        logger.info("WorldGuardRegionsEnhanced has been disabled.");
    }
}
