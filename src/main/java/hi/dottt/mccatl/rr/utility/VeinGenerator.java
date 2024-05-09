package hi.dottt.mccatl.rr.utility;


import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Random;

/**
 * Utility class for generating and resetting gold ore veins
 */
public class VeinGenerator {
    /**
     * Attempt to generate a vein at specified coordinates.
     *
     * @return Location where the vein was spawned
     */
    public Location generate_vein() {
        // Get a random valid vein block
        Location loc = get_random_block();

        // Create a 3x3 cube
        int x1 = loc.blockX() - 1;
        int x2 = loc.blockX() + 1;
        int y1 = loc.blockY() - 1;
        int y2 = loc.blockY() + 1;
        int z1 = loc.blockZ() - 1;
        int z2 = loc.blockZ() + 1;

        for (int cubeX = x1; cubeX < x2; cubeX++) {
            for (int cubeY = y1; cubeY < y2; cubeY++) {
                for (int cubeZ = z1; cubeZ < z2; cubeZ++) {
                    Block pickedBlock = Bukkit.getWorld("world").getBlockAt(cubeX, cubeY, cubeZ);

                    if (pickedBlock.getType() == Material.ANDESITE || pickedBlock.getType() == Material.STONE) {
                        pickedBlock.setType(Material.GOLD_ORE);
                    }
                }
            }
        }

        return loc;
    }

    public Location get_random_block() {
        Location blockPos = new Location(Bukkit.getWorld("world"), 0, 0, 0);

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Random rng;

        Objective objective;
        try {
            objective = scoreboard.registerNewObjective("goldminepos", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            objective = scoreboard.getObjective("goldminepos");
        }

        int x1 = objective.getScore("x1").getScore();
        int y1 = objective.getScore("y1").getScore();
        int z1 = objective.getScore("z1").getScore();

        int x2 = objective.getScore("x2").getScore();
        int y2 = objective.getScore("y2").getScore();
        int z2 = objective.getScore("z2").getScore();

        while (blockPos.getBlock().getType() != Material.STONE) {
            rng = new Random();
            blockPos = new Location(Bukkit.getWorld("world"), rng.nextInt(x2-x1) + x1, rng.nextInt(y2-y1) + y1, rng.nextInt(z2-z1) + z1);
        }

        return blockPos;
    }

    public void reset_veins() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Objective objective;
        try {
            objective = scoreboard.registerNewObjective("goldminepos", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            objective = scoreboard.getObjective("goldminepos");
        }

        int x1 = objective.getScore("x1").getScore();
        int y1 = objective.getScore("y1").getScore();
        int z1 = objective.getScore("z1").getScore();

        int x2 = objective.getScore("x2").getScore();
        int y2 = objective.getScore("y2").getScore();
        int z2 = objective.getScore("z2").getScore();

        for (int cubeX = x1; cubeX < x2; cubeX++) {
            for (int cubeY = y1; cubeY < y2; cubeY++) {
                for (int cubeZ = z1; cubeZ < z2; cubeZ++) {
                    Block block = Bukkit.getWorld("world").getBlockAt(cubeX, cubeY, cubeZ);

                    if (block.getType() == Material.GOLD_ORE) {
                        block.setType(Material.STONE);
                    }
                }
            }
        }
    }
}
