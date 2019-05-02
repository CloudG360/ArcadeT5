package net.cloudsbots.arcadet5.tasks.games;

import net.cloudsbots.archseriest.archt5.Bot;
import net.cloudsbots.archseriest.archt5.components.Logger;
import net.cloudsbots.archseriest.archt5dev.extensions.BotExtension;
import net.cloudsbots.archseriest.archt5dev.tasks.ArchTask;
import net.dv8tion.jda.core.entities.Role;

public class GameLastToLeaveWinsMainProcess extends ArchTask{

    @Override
    protected void run(String id) {
        Bot.getBot().getJDA().getRoleById((String) BotExtension.getBotExtended().getTaskManager().getThreadData(id).get("runtime_lobbyrole")).delete().complete();
        Logger.getLogger().logInfo("Last2Leave","Hey the game worked.");

        // 1 minute "Grace Period" where the rules below are mentioned and don't apply.


        // Create Listener for:
        // - State Changes (User Status must not be Idle or DnD)
        // - Any messages outside of the channel (Results in lose)
        // - Any messages in channel only saying "Im leaving/I'm leaving"
    }

    @Override
    public void cleanUp(String dataID) {

    }
}
