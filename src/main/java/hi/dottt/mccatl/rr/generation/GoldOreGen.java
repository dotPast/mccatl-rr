package hi.dottt.mccatl.rr.generation;


import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Utility class for generating gold ore veins
 */
public class GoldOreGen {
    /**
     * Attempt to generate a vein at specified coordinates.
     */
    public void generate_vein(Location loc) {
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
    }

    public Location get_random_block() {
        Location blockPos = new Location(Bukkit.getWorld("world"), 0, 0, 0);

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Objective objective;
        try {
            objective = scoreboard.registerNewObjective("goldminepos", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            objective = scoreboard.getObjective("goldminepos");
        }

        while (blockPos.getBlock().getType() != Material.STONE || blockPos.getBlock().getType() != Material.ANDESITE) {
            blockPos = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        }

        return blockPos;
    }
}
