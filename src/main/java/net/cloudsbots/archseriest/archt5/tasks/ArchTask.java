package net.cloudsbots.archseriest.archt5.tasks;

import net.cloudsbots.archseriest.archt5.extensions.BotExtension;

import java.util.Map;

/**
 * <h2>WARNING: Development preview system.</h2>
 *
 * This system is not implemented into the core API yet, hence why it's referred to as a
 * development preview system. This means that it's official but only available in select
 * packages.
 *
 * An ArchTask is a replacement structure for Behaviors & Commands. Behaviors can still be
 * based off of Behavior object but the Behavior object will be tied to ArchTasks and TaskManager.
 *
 * Version Goal: 1.0.0 (No Build ID determined)
 * [!] BREAKING_CHANGES
 */
public abstract class ArchTask {

    public final void execute(Map<String, Object> vars){
        BotExtension.getBotExtended().getTaskManager().processTask(this, vars);
    }

    protected abstract void run(String id);

    public abstract void cleanUp(String dataID);

    @Override
    public String toString() {
        return getClass().getTypeName();
    }
}
