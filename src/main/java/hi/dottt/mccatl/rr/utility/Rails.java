package hi.dottt.mccatl.rr.utility;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

public class Rails {
    public int[] countRails(int x1, int x2, int y1, int y2, int z1, int z2) {
        int[] result = {0, 0, 0};

        boolean foundRail;
        boolean countRails = true;
        for (int z = z1; z < z2; z++) {
            foundRail = false;
            for (int y = y1; y < y2; y++) {
                for (int x = x1; x < x2; x++) {
                    World world = Bukkit.getWorld("world");
                    if (world.getBlockAt(x, y, z).getType() == Material.RAIL || world.getBlockAt(x, y, z).getType() == Material.POWERED_RAIL) {
                        result[0]++;
                        if (!foundRail && countRails) {
                            result[1]++;
                        }
                        foundRail = true;
                    }
                }
            }
            if (!foundRail) {
                countRails = false;
            }
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective checkpointObjective;
        try {
            checkpointObjective = scoreboard.registerNewObjective("checkpoints", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            checkpointObjective = scoreboard.getObjective("checkpoints");
        }

        result[2] = checkpointObjective.getScore("num").getScore();

        return result;
    }

    public void spawnTnt() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        World world = Bukkit.getWorld("world");

        Objective objective;
        try {
            objective = scoreboard.registerNewObjective("startrailpos", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            objective = scoreboard.getObjective("startrailpos");
        }

        Objective checkpointObjective;
        try {
            checkpointObjective = scoreboard.registerNewObjective("checkpoints", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            checkpointObjective = scoreboard.getObjective("checkpoints");
        }

        int x = objective.getScore(String.format("x%s", checkpointObjective.getScore("num").getScore())).getScore();
        int y = objective.getScore(String.format("y%s", checkpointObjective.getScore("num").getScore())).getScore();
        int z = objective.getScore(String.format("z%s", checkpointObjective.getScore("num").getScore())).getScore();

        if (x == 0 && y == 0 && z == 0) {
            Bukkit.getPluginManager().getPlugin("MCCATL-RR").getLogger().warning(String.format("TNT Minecart spawn location for checkpoint %s is X: 0, Y: 0, Z: 0, defaulting to checkpoint 0. Make sure you have set the TNT spawn location for this checkpoint.", checkpointObjective.getScore("num").getScore()));
            x = objective.getScore("x0").getScore();
            y = objective.getScore("y0").getScore();
            z = objective.getScore("z0").getScore();
        }
        Location location = new Location(world, x, y, z);

        Entity minecart = world.spawnEntity(location, EntityType.MINECART_TNT);

        minecart.setVelocity(new Vector(10, 10, 10));
    }

    public void resetCheckpoints() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective checkpointObjective;

        try {
            checkpointObjective = scoreboard.registerNewObjective("checkpoints", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            checkpointObjective = scoreboard.getObjective("checkpoints");
        }

        Score score = checkpointObjective.getScore("num");
        score.setScore(0);
    }
}
