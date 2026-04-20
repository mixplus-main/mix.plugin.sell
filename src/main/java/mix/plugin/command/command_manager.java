package mix.plugin.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

import mix.plugin.command.run.sell.sell_run;

public final class command_manager {

    private final JavaPlugin plugin;
    public command_manager(JavaPlugin plugin) {
        this.plugin = plugin;
    }



    public void register() {

        BasicCommand cmd = (sender, args) -> {
            sell_run.run(sender.getSender(), args);
        };

        plugin.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                commands -> {
                    commands.registrar().register("sell", cmd);
                }
        );

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
    }
}