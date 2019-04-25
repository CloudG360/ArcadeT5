package net.cloudsbots.archseriest.archt5.tasks;

import net.cloudsbots.archseriest.archt5.extensions.UtilitiyFuctions;

import java.util.Map;

public class PreTaskScheduler {

    private ArchTask task;
    private Map<String, Object> args;

    public PreTaskScheduler(ArchTask task, Map<String, Object> args){
        this.task = task;
        this.args = args;
    }

    public void runtask(Map<String, Object> runtimeargs){ task.execute(UtilitiyFuctions.mergeMaps(args, runtimeargs)); }
    public void runtask(){ task.execute(args); }

}
