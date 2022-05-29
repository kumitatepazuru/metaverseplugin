package tech.inudev.metaverseplugin;

import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tech.inudev.metaverseplugin.config.ConfigHandler;
import tech.inudev.metaverseplugin.listener.test;
import tech.inudev.metaverseplugin.scheduler.DatabasePingRunnable;
import tech.inudev.metaverseplugin.utils.DatabaseUtil;

/**
 * メタバースプラグイン（仮）
 *
 * @author kumitatepazuru, tererun, toru-toruto
 */
public final class Metaverseplugin extends JavaPlugin {

    @Getter
    private static Metaverseplugin instance;

    @Getter
    private ConfigHandler configHandler;
    private DatabasePingRunnable databasePingRunnable;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.configHandler = new ConfigHandler(instance);
        this.databasePingRunnable = new DatabasePingRunnable();

        DatabaseUtil.connect();

        registerSchedulers();
        registerListeners();

        getLogger().info("Metaverseplugin enabled!");
    }

    private void registerSchedulers() {
        this.databasePingRunnable.runTaskTimer(this, 0, 20 * 60 * 60);
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new test(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        DatabaseUtil.disconnect();

        getLogger().info("Metaverseplugin disabled!");
    }

}
