package hi.dottt.mccatl.rr.commands;

import hi.dottt.mccatl.rr.utility.Rails;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

public class CheckRails implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Rails utility = new Rails();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Objective objective;
        try {
            objective = scoreboard.registerNewObjective("scanpointpos", Criteria.DUMMY, Component.empty());
        } catch (IllegalArgumentException exception) {
            objective = scoreboard.getObjective("scanpointpos");
        }

        int x1 = objective.getScore("x").getScore();
        int y1 = objective.getScore("y").getScore();
        int z1 = objective.getScore("z").getScore();

        int x2 = x1 + 23;
        int y2 = y1 + 20;
        int z2 = z1 + 512;

        Component lagMsg = Component.text().content("[").color(TextColor.color(0x242424))
                .append(Component.text("GAME").color(TextColor.color(0x26dbff)))
                .append(Component.text().content("] ").color(TextColor.color(0x242424)))
                .append(Component.text("Counting rails, server may lag...").color(TextColor.color(0x26dbff)))
                .build();

        Bukkit.broadcast(lagMsg);

        int[] rails = utility.countRails(x1, x2, y1, y2, z1, z2);

        Component results = Component.text().content("[").color(TextColor.color(0x242424))
                .append(Component.text("GAME").color(TextColor.color(0x26dbff)))
                .append(Component.text().content("] ").color(TextColor.color(0x242424)))
                .append(Component.text().content("Total rails placed: ").color(TextColor.color(0x26dbff)))
                .append(Component.text().content(String.format("%s. ", rails[0])).color(TextColor.color(0x26dbff)))
                .append(Component.text().content("Length of the track: ").color(TextColor.color(0x26dbff)))
                .append(Component.text().content(String.format("%s. ", rails[1])).color(TextColor.color(0x26dbff)))
                .append(Component.text().content("Checkpoints reached: ").color(TextColor.color(0x26dbff)))
                .append(Component.text().content(String.format("%s.", rails[2])).color(TextColor.color(0x26dbff)))
                .build();

        Bukkit.broadcast(results);
        return true;
    }
}
