package mix.plugin.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Cooldown {

    private static final Map<UUID, Long> cooldowns = new HashMap<>();

    private Cooldown() {}

    public static boolean isOnCooldown(UUID uuid) {
        return cooldowns.containsKey(uuid) &&
                System.currentTimeMillis() < cooldowns.get(uuid);
    }

    public static long getRemaining(UUID uuid) {
        return Math.max(0, (cooldowns.get(uuid) - System.currentTimeMillis()) / 1000);
    }

    public static void set(UUID uuid, long seconds, boolean view, JavaPlugin plugin, Player player) {

        cooldowns.put(uuid, System.currentTimeMillis() + (seconds * 1000));

        if (!view) return;

        // 表示ループ
        new BukkitRunnable() {
            @Override
            public void run() {

                if (!isOnCooldown(uuid)) {
                    cancel();
                    return;
                }

                long remain = getRemaining(uuid);

                player.sendActionBar(
                        Component.text("あと " + remain + " 秒")
                                .color(NamedTextColor.GREEN)
                );
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
}