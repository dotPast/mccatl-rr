package hi.dottt.mccatl.rr.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class WeakBlocks implements Listener {
    @EventHandler
    public void breakWeakBlocks(BlockPlaceEvent event) {
        Block block = event.getBlock();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (block.getType() == Material.GRANITE) {
                    block.setType(Material.AIR);
                }
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("MCCATL-RR"), 100);
    }
}
