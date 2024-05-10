package hi.dottt.mccatl.rr.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class DeathMsg implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Entity player = event.getPlayer();
        Random rng = new Random();

        Component[] explosion_death_msgs = {
                Component.text(String.format("☠ - %s didn't read the source code.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s forgot to check their health.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s missed that explosions deal damage while reading the source code.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s was ran over by a minecart with TNT.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s slipped and fell on TNT.", player.getName())).color(TextColor.color(0x666666)),
        };

        Component[] lava_death_msgs = {
                Component.text(String.format("☠ - %s was an Imposter.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s drank a Fire Resistance potion that lasted 3 seconds.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s joined the Magma Cube army.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s evaporated.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s slipped.", player.getName())).color(TextColor.color(0x666666)),
        };

        Component[] extra_death_msgs = {
                Component.text(String.format("☠ - %s forgot about physics.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s didn't star the repository with source code.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s is now a pancake.", player.getName())).color(TextColor.color(0x666666)),
                Component.text(String.format("☠ - %s slipped.", player.getName())).color(TextColor.color(0x666666)),
        };

        if (player.getLastDamageCause().getDamageSource().getDamageType() == DamageType.EXPLOSION) {
            event.deathMessage(explosion_death_msgs[rng.nextInt(explosion_death_msgs.length)]);
        } else if (player.getLastDamageCause().getDamageSource().getDamageType() == DamageType.LAVA) {
            event.deathMessage(lava_death_msgs[rng.nextInt(lava_death_msgs.length)]);
        } else {
            event.deathMessage(extra_death_msgs[rng.nextInt(extra_death_msgs.length)]);
        }
    }
}
