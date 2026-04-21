package mix.plugin.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.Inventory;

import mix.plugin.command.run.sell.sell_run;


public final class command_manager {

    private final JavaPlugin plugin;
    public command_manager(JavaPlugin plugin) {
        this.plugin = plugin;
    }



    public void register() {
        //sell
        BasicCommand sell = (sender, args) -> {
            sell_run.run(sender.getSender(), args);
        };

        plugin.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                commands -> {
                    commands.registrar().register("sell", sell);
                }
        );

        //reloadCMD
        BasicCommand reloadCmd = (sender, args) -> {
            plugin.reloadConfig();
            sender.getSender().sendMessage("config reload完了");
        };
        plugin.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                commands -> {
                    commands.registrar().register("reload", reloadCmd);
                }
        );

        //ec command
        BasicCommand ecCMD = (source, args) -> {
            var sender = source.getSender();

            if (!(sender instanceof Player player)) {
                source.getSender().sendMessage("プレイヤーのみ実行可能");
                return;
            }

            player.openInventory(player.getEnderChest());
        };
        plugin.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                commands -> {
                    commands.registrar().register("ec", ecCMD);
                }
        );
    }
}