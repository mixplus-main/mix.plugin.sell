package mix.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import mix.plugin.command.command_manager;
import mix.plugin.command.run.sell.SellCloseListener;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveConfig();

        // コマンド登録などを呼ぶだけ
        new command_manager(this).register();

        getServer().getPluginManager().
                registerEvents(new SellCloseListener(this), this);
    }
}