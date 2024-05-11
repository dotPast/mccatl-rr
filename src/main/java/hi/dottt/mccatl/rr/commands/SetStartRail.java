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

public class SetStartRail implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Console can't use the command");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("No arguments.");
            return false;
        }

        Player player = Bukkit.getPlayer(sender.getName());
        Location playerlocation = player.getLocation();

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Objective objective;
        try {
            objective = scoreboard.registerNewObjective("startrailpos", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            objective = scoreboard.getObjective("startrailpos");
        }

        objective.getScore(String.format("x%s", args[0])).setScore(playerlocation.getBlockX());
        objective.getScore(String.format("y%s", args[0])).setScore(playerlocation.getBlockY());
        objective.getScore(String.format("z%s", args[0])).setScore(playerlocation.getBlockZ());


        Component success_msg = Component.text(String.format("[✔] Successfully set starting rails point for checkpoint %s to %s %s %s.", args[0], playerlocation.getBlockX(), playerlocation.getBlockY(), playerlocation.getBlockZ())).color(TextColor.color(0x74ff59));
        sender.sendMessage(success_msg);

        return true;
    }
}
