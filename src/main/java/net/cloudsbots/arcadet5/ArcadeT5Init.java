package net.cloudsbots.arcadet5;

import net.cloudsbots.arcadet5.games.devtest.GameHeaderLastToLeaveWins;
import net.cloudsbots.arcadet5.tasks.TaskManagerBackgroundProcess;
import net.cloudsbots.archseriest.archt5.Bot;
import net.cloudsbots.archseriest.archt5.config.ConfigurationFile;
import net.cloudsbots.archseriest.archt5.exceptions.InvalidConfigException;
import net.cloudsbots.archseriest.archt5.extensions.BotExtension;
//import net.cloudsbots.archseriest.archt5.extensions.Validator;
import net.cloudsbots.archseriest.archt5.plugin.DisableReason;
import net.cloudsbots.archseriest.archt5.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * WARNING: Development preview systems IN USE
 *
 * This plugin uses official systems which have not been merged or developed
 * for the core API yet but are aimed for release eventually, just being used
 * in smaller projects.
 *
 */
public class ArcadeT5Init extends Plugin {

    private static ArcadeT5Init plugin;
    public static ArcadeT5Init getPlugin() { return plugin; }

    private ConfigurationFile gameConfig;
    public ConfigurationFile getGameConfig(){ return gameConfig; }

    @Override
    protected void onEnable() {
        plugin = this;
        BotExtension be = new BotExtension();
        BotExtension.setBotExtension(be);
        new TaskManagerBackgroundProcess().execute(new HashMap<>());
        try {
            gameConfig = new ConfigurationFile(new File("arcade.cfgp"), "category");
        } catch (IOException err){
            Bot.getBot().getLogger().logRuntimeError("ArcadeT5/EngineInit", "An IO Exception was thrown whilst loading the config for the games.", err.getMessage());
            this.disablePlugin(DisableReason.FAILURE);
        } catch (InvalidConfigException err){
            Bot.getBot().getLogger().logRuntimeError("ArcadeT5/EngineInit", "An InvalidConfigException was thrown whilst loading the config for the games.", err.getMessage());
            this.disablePlugin(DisableReason.FAILURE);
        }
        //Validator.verifyType(gameConfig.getConfig().get("category"), String.class, "");
        new GameHeaderLastToLeaveWins("Test Game", new HashMap<>()).getTrigger().runtask();
    }
}
