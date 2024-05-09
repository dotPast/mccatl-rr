package hi.dottt.mccatl.rr;

import hi.dottt.mccatl.rr.commands.*;
import hi.dottt.mccatl.rr.events.DynamiteStick;
import hi.dottt.mccatl.rr.events.MinecartEvents;
import hi.dottt.mccatl.rr.events.TriggerExplosion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RR extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("Registering command executors...");
        Bukkit.getPluginCommand("checkrails").setExecutor(new CheckRails());
        Bukkit.getPluginCommand("spawntnt").setExecutor(new SpawnTNT());
        Bukkit.getPluginCommand("setstartrail").setExecutor(new SetStartRail());
        Bukkit.getPluginCommand("setscanpoint").setExecutor(new SetScanPoint());
        Bukkit.getPluginCommand("setgoldminepoint").setExecutor(new SetGoldMinePoints());
        Bukkit.getPluginCommand("generatevein").setExecutor(new GenerateVein());
        Bukkit.getPluginCommand("resetveins").setExecutor(new ResetVeins());
        Bukkit.getPluginCommand("resetcheckpoints").setExecutor(new ResetCheckpoints());

        this.getLogger().info("Registering events...");
        getServer().getPluginManager().registerEvents(new TriggerExplosion(), this);
        getServer().getPluginManager().registerEvents(new MinecartEvents(), this);
        getServer().getPluginManager().registerEvents(new DynamiteStick(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
