package hi.dottt.mccatl.rr.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DynamiteStick implements Listener {
    @EventHandler
    public void onPlaced(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.RED_CANDLE) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    block.setType(Material.AIR);
                    Entity tempEntity = block.getWorld().spawnEntity(block.getLocation(), EntityType.MINECART);
                    Bukkit.getPluginManager().callEvent(new ExplosionPrimeEvent(tempEntity, 0, false));
                }
            }.runTaskLater(Bukkit.getPluginManager().getPlugin("MCCATL-RR"), 30);
        }
    }
}
