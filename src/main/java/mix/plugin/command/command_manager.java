package mix.plugin.command;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import mix.plugin.command.run.sell.sell_run;
import mix.plugin.command.run.rtp.Overworld;
import mix.plugin.command.run.rtp.Nether;
import mix.plugin.command.run.rtp.End;



public final class command_manager {

    private final JavaPlugin plugin;
    public command_manager(JavaPlugin plugin) {
        this.plugin = plugin;
    }



    public void register() {


        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {

            // sell
            commands.registrar().register("sell", (source, args) -> {
                sell_run.run(source.getSender(), args);
            });

            // reload
            commands.registrar().register("reload", (source, args) -> {
                plugin.reloadConfig();
                source.getSender().sendMessage("config reload完了");
            });

            // ec
            commands.registrar().register("ec", (source, args) -> {
                var sender = source.getSender();
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("プレイヤーのみ実行可能");
                    return;
                }
                player.openInventory(player.getEnderChest());
            });

            // rtp

            LiteralArgumentBuilder<CommandSourceStack> rtp = Commands.literal("rtp")
                    .then(Commands.literal("overworld")
                            .executes(ctx -> new Overworld(plugin).run(ctx))

                    .then(Commands.literal("nether")
                            .executes(Nether::run))

                    .then(Commands.literal("end")
                            .executes(End::run)));

            commands.registrar().register(rtp.build());
        }
    );}
}