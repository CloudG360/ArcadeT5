package net.cloudsbots.archseriest.archt5.extensions;

import net.cloudsbots.archseriest.archt5.Bot;
import net.cloudsbots.archseriest.archt5.tasks.TaskManager;

/**
 * WARNING: Development preview system.
 *
 * This system is not implemented into the core API yet, hence why it's referred to as a
 * development preview system. This means that it's official but only available in select
 * packages.
 *
 * Extensions are an in-testing way of implementing extra APIs for plugins which you can load
 * up. Currently are not implemented and have to be hardcoded to extend core components.
 * Extensions, unlike other development systems, don't have a planned release. They just state
 * when the features they contain are aimed to be released.
 *
 * Version Goal: 1.0.0 (No Build ID determined)
 * Push Target Class: net.cloudsbots.archseriest.archt5.Bot (Main Branch on release)
 */
public class BotExtension {

    private Bot botpointer;
    private static BotExtension botExtend;
    private static boolean botset = false;

    private TaskManager taskManager;

    public BotExtension(){
        botpointer = Bot.getBot();
        taskManager = new TaskManager();
    }

    public static void setBotExtension(BotExtension botExtension){
        if(!botset){
            botset = true;
            botExtend = botExtension;
        }
    }

    public static BotExtension getBotExtended(){ return botExtend; }

    public Bot getBot(){ return botpointer; }
    public TaskManager getTaskManager() { return taskManager; }
}
