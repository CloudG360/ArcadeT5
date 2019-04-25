package net.cloudsbots.arcadet5.games.devtest;

import net.cloudsbots.arcadet5.games.Game;
import net.cloudsbots.arcadet5.tasks.games.GameLastToLeaveWinsMainProcess;
import net.cloudsbots.arcadet5.tasks.games.ArcadeEngineTask;
import net.cloudsbots.archseriest.archt5.tasks.ArchTask;

import java.util.HashMap;
import java.util.Map;

public class GameHeaderLastToLeaveWins extends Game {

    public GameHeaderLastToLeaveWins(String display, Map<String, Object> args){
        super(display, args);
    }

    @Override
    public ArchTask getTriggerTask() {
        return new ArcadeEngineTask();
    }

    @Override
    public Map<String, Object> getTriggerData() {
        Map<String, Object> map = new HashMap<>();
        map.put("engine_gameclass", new GameLastToLeaveWinsMainProcess());
        map.put("engine_maxplayers", 24);
        map.put("engine_minplayers", 2);
        map.put("cake_commands", false);
        map.put("arcade_hdevc", true); //Developer Commands to start and stop the game. Limited compared to Cake's commands
        return map;
    }

    @Override
    public String getTypeid() {
        return "lasttoleave";
    }

    @Override
    public String getDisplayname() {
        return "Last2Leave";
    }
}
