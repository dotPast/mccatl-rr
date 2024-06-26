package hi.dottt.mccatl.rr.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;

public class TriggerExplosion implements Listener {
    @EventHandler
    public void onCollision(VehicleBlockCollisionEvent event) {
        if (event.getVehicle().getType() == EntityType.MINECART_TNT) {
            Bukkit.getPluginManager().callEvent(new ExplosionPrimeEvent(event.getVehicle(), 0, false));
        }
    }

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent event) {
        event.setCancelled(true);

        Entity entity = event.getEntity();
        World world = entity.getWorld();
        Location explosionLocation = entity.getLocation();

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        if (entity.getType() == EntityType.MINECART_TNT || entity.getType() == EntityType.MINECART) {
            if (entity.getType() == EntityType.MINECART_TNT) {
                Objective objective;
                try {
                    objective = scoreboard.registerNewObjective("startrailpos", Criteria.DUMMY, Component.empty());
                } catch (IllegalArgumentException exception) {
                    objective = scoreboard.getObjective("startrailpos");
                }

                int startRailX = objective.getScore("x").getScore();
                int startRailY = objective.getScore("y").getScore();
                int startRailZ = objective.getScore("z").getScore();
                Location startRail = new Location(entity.getWorld(), startRailX, startRailY, startRailZ);

                DecimalFormat distanceFormatter = new DecimalFormat("#.#");

                Bukkit.broadcast(
                        Component.text().content("[").color(TextColor.color(0x242424))
                                .append(Component.text("GAME").color(TextColor.color(0x26dbff)))
                                .append(Component.text().content("] ").color(TextColor.color(0x242424)))
                                .append(Component.text("TNT Minecart crashed ").color(TextColor.color(0x26dbff)))
                                .append(Component.text(
                                        distanceFormatter.format(startRail.distance(explosionLocation))
                                ).color(TextColor.color(0x26dbff)))
                                .append(Component.text(" blocks away from spawn.").color(TextColor.color(0x26dbff))).build()
                );
            }

            for (Player player : explosionLocation.getNearbyEntitiesByType(Player.class, 5)) {
                player.damage(6, DamageSource.builder(DamageType.EXPLOSION).build());
            }

            Objective checkpointObjective;
            try {
                checkpointObjective = scoreboard.registerNewObjective("checkpoints", Criteria.DUMMY, Component.empty());
            } catch (IllegalArgumentException exception) {
                checkpointObjective = scoreboard.getObjective("checkpoints");
            }

            boolean reachedCheckpoint = false;

            int x1 = explosionLocation.getBlockX() - 3;
            int y1 = explosionLocation.getBlockY() - 3;
            int z1 = explosionLocation.getBlockZ() - 3;
            int x2 = explosionLocation.getBlockX() + 3;
            int y2 = explosionLocation.getBlockY() + 3;
            int z2 = explosionLocation.getBlockZ() + 3;

            for (int x = x1; x < x2; x++) {
                for (int y = y1; y < y2; y++) {
                    for (int z = z1; z < z2; z++) {
                        if (world.getBlockAt(x, y, z).getType() == Material.CRACKED_STONE_BRICKS) {
                            world.getBlockAt(x, y, z).setType(Material.COBBLESTONE);
                        }
                        if (world.getBlockAt(x, y, z).getType() == Material.STONE_BRICKS) {
                            world.getBlockAt(x, y, z).setType(Material.CRACKED_STONE_BRICKS);
                        }
                        if (world.getBlockAt(x, y, z).getType() == Material.WHITE_CONCRETE) {
                            world.getBlockAt(x, y, z).breakNaturally();
                        }
                        if (world.getBlockAt(x, y, z).getType() == Material.OXIDIZED_COPPER && entity.getType() == EntityType.MINECART_TNT) {
                            world.getBlockAt(x, y, z).setType(Material.AIR);
                            reachedCheckpoint = true;
                        }
                        if (world.getBlockAt(x, y, z).getType() == Material.RAIL && entity.getType() == EntityType.MINECART) {
                            world.getBlockAt(x, y, z).breakNaturally();
                        }
                        if (world.getBlockAt(x, y, z).getType() == Material.POWERED_RAIL && entity.getType() == EntityType.MINECART) {
                            world.getBlockAt(x, y, z).breakNaturally();
                        }
                    }
                }
            }

            if (reachedCheckpoint) {
                Score score = checkpointObjective.getScore("num");
                score.setScore(score.getScore() + 1);

                Component checkpointMsg = Component.text().content("[").color(TextColor.color(0x242424))
                        .append(Component.text("GAME").color(TextColor.color(0x26dbff)))
                        .append(Component.text().content("] ").color(TextColor.color(0x242424)))
                        .append(Component.text(String.format("Reached checkpoint %s.", score.getScore())).color(TextColor.color(0x26dbff))).build();

                Bukkit.broadcast(checkpointMsg);
            }

            world.createExplosion(explosionLocation, 0);
            entity.remove();
        }
    }
}
