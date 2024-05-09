package hi.dottt.mccatl.rr.commands;

import hi.dottt.mccatl.rr.utility.VeinGenerator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
        VeinGenerator gen = new VeinGenerator();

        Component success_msg = Component.text("[✔] Successfully reset all veins").color(TextColor.color(0x74ff59));

        gen.reset_veins();
        sender.sendMessage(success_msg);

        return true;
    }
}
