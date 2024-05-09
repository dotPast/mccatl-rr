package hi.dottt.mccatl.rr.commands;

import hi.dottt.mccatl.rr.utility.VeinGenerator;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GenerateVein implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        VeinGenerator gen = new VeinGenerator();
        int amount = 1;

        if (!args[0].isEmpty()) {
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException err) {
                sender.sendMessage("Invalid number defaulting to 1 vein.");
            }
        }
        Location generatedAt;
        for (int i = 0; i < amount; i++) {
            generatedAt = gen.generate_vein();
            sender.sendMessage(String.format("Vein generated at %s %s %s", generatedAt.getBlockX(), generatedAt.getBlockY(), generatedAt.getBlockZ()));
        }

        return true;
    }
}
