package hi.dottt.mccatl.rr.events;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

public class MinecartEvents implements Listener {
    @EventHandler
    public void onMinecartMove(VehicleMoveEvent event) {
        Minecart entity = (Minecart) event.getVehicle();
        Block railBlock = entity.getLocation().getBlock();

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Objective objective;
        try {
            objective = scoreboard.registerNewObjective("minecarttimer", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            objective = scoreboard.getObjective("minecarttimer");
        }

        if (entity.getType() == EntityType.MINECART_TNT) {
            int timer = objective.getScore("timer").getScore();
            timer--;
            Bukkit.broadcast(Component.text(railBlock.getType().toString()));
            if (railBlock.getType() == Material.POWERED_RAIL) {
                objective.getScore("timer").setScore(150);
                entity.setVelocity(entity.getVelocity().add(new Vector(1, 1, 1))); // Prevent the minecart from slowing down on powered rails
            } else {
                objective.getScore("timer").setScore(timer);
            }

            if (timer == 0) {
                Bukkit.getPluginManager().callEvent(new ExplosionPrimeEvent(entity, 0, false));
            }
        }
    }

    @EventHandler
    public void onMinecartSpawn(VehicleCreateEvent event) {
        Entity entity = event.getVehicle();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        ((Minecart) event.getVehicle()).setSlowWhenEmpty(false);
        ((Minecart) event.getVehicle()).setDerailedVelocityMod(new Vector(0, 0, 0));

        if (entity.getType() == EntityType.MINECART_TNT) {
            Objective objective;
            try {
                objective = scoreboard.registerNewObjective("minecarttimer", Criteria.DUMMY, Component.empty());
            } catch (IllegalArgumentException exception) {
                objective = scoreboard.getObjective("minecarttimer");
            }

            objective.getScore("timer").setScore(150);
        }
    }
}
