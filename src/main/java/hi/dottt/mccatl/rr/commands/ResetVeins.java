package hi.dottt.mccatl.rr.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ResetVeins implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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
                for (int cubeZ = z2; cubeZ < y2; cubeZ++) {
                    Block block = Bukkit.getWorld("world").getBlockAt(cubeX, cubeY, cubeZ);

                    Bukkit.broadcast(Component.text(block.getType().toString()));

                    if (block.getType() == Material.GOLD_ORE) {
                        block.setType(Material.STONE);
                    }
                }
            }
        }

        return true;
    }
}
