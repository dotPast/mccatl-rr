package hi.dottt.mccatl.rr;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

public class Utility {
    public int[] countRails(int x1, int x2, int y1, int y2, int z1, int z2) {
        int[] result = {0, 0};

        boolean foundRail;
        boolean countRails = true;
        for (int z = z1; z < z2; z++) {
            foundRail = false;
            for (int y = y1; y < y2; y++) {
                for (int x = x1; x < x2; x++) {
                    World world = Bukkit.getWorld("world");
                    if (world.getBlockAt(x, y, z).getBlockData().getMaterial() == Material.RAIL || world.getBlockAt(x, y, z).getBlockData().getMaterial() == Material.POWERED_RAIL) {
                        result[0]++;
                        if (!foundRail && countRails) {
                            result[1]++;
                        }
                        foundRail = true;
                    }
                }
                if (!foundRail) {
                    countRails = false;
                }
            }
        }

        return result;
    }
}
