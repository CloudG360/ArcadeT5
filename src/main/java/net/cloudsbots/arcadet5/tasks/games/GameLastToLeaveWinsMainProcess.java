package net.cloudsbots.arcadet5.tasks.games;

import net.cloudsbots.archseriest.archt5.components.Logger;
import net.cloudsbots.archseriest.archt5.extensions.BotExtension;
import net.cloudsbots.archseriest.archt5.tasks.ArchTask;
import net.dv8tion.jda.core.entities.Role;

public class GameLastToLeaveWinsMainProcess extends ArchTask{

    @Override
    protected void run(String id) {
        ((Role) BotExtension.getBotExtended().getTaskManager().getThreadData(id).get("runtime_lobbyrole")).delete().complete();
        Logger.getLogger().logInfo("Hey the game worked.");
    }

    @Override
    public void cleanUp(String dataID) {

    }
}
