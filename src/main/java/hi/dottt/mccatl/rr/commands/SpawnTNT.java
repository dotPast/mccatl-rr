package hi.dottt.mccatl.rr.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class SpawnTNT implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Console can't use the command");
            return false;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Objective objective;
        try {
            objective = scoreboard.registerNewObjective("startrailpos", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            objective = scoreboard.getObjective("startrailpos");
        }

        int x = objective.getScore("x").getScore();
        int y = objective.getScore("y").getScore();
        int z = objective.getScore("z").getScore();
        Location location = new Location(player.getWorld(), x, y, z);

        Entity minecart = player.getWorld().spawnEntity(location, EntityType.MINECART_TNT);

        minecart.setVelocity(new Vector(10, 10, 10));

        return true;
    }
}
