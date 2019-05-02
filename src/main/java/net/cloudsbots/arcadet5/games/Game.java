package net.cloudsbots.arcadet5.games;

import net.cloudsbots.archseriest.archt5dev.extensions.UtilitiyFuctions;
import net.cloudsbots.archseriest.archt5dev.tasks.ArchTask;
import net.cloudsbots.archseriest.archt5dev.tasks.PreTaskScheduler;

import java.util.Map;

public abstract class Game {

    private PreTaskScheduler trigger;

    public Game(String displayName, Map<String, Object> args){
        setTrigger(args);
    }


    private void setTrigger(Map<String, Object> args){
        Map<String, Object> fdat = UtilitiyFuctions.mergeMaps(getTriggerData(), args);
        fdat.put("engine_gameheader", this);
        this.trigger = new PreTaskScheduler(getTriggerTask(), fdat);
    }

    public abstract Map<String, Object> getTriggerData();
    public abstract ArchTask getTriggerTask();
    public final PreTaskScheduler getTrigger(){ return trigger; }
    public abstract String getDisplayname();
    public abstract String getTypeid();
    public String getDescription() { return "No description provided."; }
    public String getVersion() { return "Undocumented"; }
}
