package hi.dottt.mccatl.rr.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

public class SetGoldMinePoints implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Console can't use the command");
            return false;
        }

        Player player = Bukkit.getPlayer(sender.getName());
        Location playerlocation = player.getLocation();

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Objective objective;
        try {
            objective = scoreboard.registerNewObjective("goldminepos", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            objective = scoreboard.getObjective("goldminepos");
        }

        if (args[0].equals("1")) {
            objective.getScore("x1").setScore(playerlocation.getBlockX());
            objective.getScore("y1").setScore(playerlocation.getBlockY());
            objective.getScore("z1").setScore(playerlocation.getBlockZ());
        } else if (args[0].equals("2")) {
            objective.getScore("x2").setScore(playerlocation.getBlockX());
            objective.getScore("y2").setScore(playerlocation.getBlockY());
            objective.getScore("z2").setScore(playerlocation.getBlockZ());
        } else {
            sender.sendMessage("No arguments or argument is not 1 or 2.");
            return false;
        }

        Component success_msg = Component.text(String.format("[✔] Successfully set point %s to %s %s %s.", args[0], playerlocation.getBlockX(), playerlocation.getBlockY(), playerlocation.getBlockZ())).color(TextColor.color(0x74ff59));
        sender.sendMessage(success_msg);

        return true;
    }
}
