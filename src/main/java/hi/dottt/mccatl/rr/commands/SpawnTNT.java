package hi.dottt.mccatl.rr.commands;

import hi.dottt.mccatl.rr.utility.Rails;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SpawnTNT implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Rails rails = new Rails();

        rails.spawnTnt();
        Component success_msg = Component.text("[✔] Successfully spawned a TNT Minecart.").color(TextColor.color(0x74ff59));
        sender.sendMessage(success_msg);

        return true;
    }
}
