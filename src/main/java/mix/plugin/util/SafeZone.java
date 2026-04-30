package mix.plugin.util;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.function.Consumer;

public class SafeZone {

    private static final Random random = new Random();

    /**
     * チャンク単位で安全地形を探すRTP
     */
    public static void find(JavaPlugin plugin, World world, int range, int attempts, Consumer<Location> callback) {

        new org.bukkit.scheduler.BukkitRunnable() {

            int count = 0;

            @Override
            public void run() {

                if (count++ >= attempts) {
                    callback.accept(null);
                    cancel();
                    return;
                }


                int x = random.nextInt(range * 2) - range;
                int z = random.nextInt(range * 2) - range;

                int chunkX = x >> 4;
                int chunkZ = z >> 4;

                // ② チャンク取得（軽いロード）
                Chunk chunk = world.getChunkAt(chunkX, chunkZ);

                // ③ チャンク内探索（16x16）
                Location loc = scanChunk(world, chunk);

                if (loc != null) {
                    callback.accept(loc);
                    cancel();
                }

            }

        }.runTaskTimer(plugin, 0L, 2L); // 2tickごと（軽量）
    }


    private static Location scanChunk(World world, Chunk chunk) {

        int baseX = chunk.getX() << 4;
        int baseZ = chunk.getZ() << 4;

        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {

                int x = baseX + dx;
                int z = baseZ + dz;

                int y = world.getHighestBlockYAt(x, z);

                Location loc = new Location(world, x + 0.5, y + 1, z + 0.5);

                if (isSafe(loc)) {
                    return loc;
                }
            }
        }

        return null;
    }


    private static boolean isSafe(Location loc) {

        Block feet = loc.getBlock();
        Block head = loc.clone().add(0, 1, 0).getBlock();
        Block ground = loc.clone().subtract(0, 1, 0).getBlock();

        if (!feet.getType().isAir()) return false;
        if (!head.getType().isAir()) return false;
        if (!ground.getType().isSolid()) return false;

        Material m = ground.getType();

        return m != Material.LAVA
                && m != Material.WATER
                && m != Material.CACTUS
                && m != Material.MAGMA_BLOCK
                && m != Material.FIRE;
    }
}