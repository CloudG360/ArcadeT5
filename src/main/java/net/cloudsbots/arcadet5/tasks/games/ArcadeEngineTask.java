package net.cloudsbots.arcadet5.tasks.games;

import net.cloudsbots.arcadet5.ArcadeT5Init;
import net.cloudsbots.arcadet5.games.Game;
import net.cloudsbots.arcadet5.games.ArcadeListener;
import net.cloudsbots.archseriest.archt5.Bot;
//import net.cloudsbots.archseriest.archt5.extensions.Validator;
import net.cloudsbots.archseriest.archt5.extensions.BotExtension;
import net.cloudsbots.archseriest.archt5.tasks.ArchTask;
import net.cloudsbots.archseriest.archt5.tasks.TaskManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Arcade Engine Launcher
 *
 * Begins games using the arcade Launcher. The arcade launcher uses
 * a lobby system with a joining interface in order to create a classic
 * minigame theme and feel. Most games should be designed to be at least
 * compatible with this engine so that they can be loaded up with other
 * launchers if more appropriate.
 *
 * This class essentially starts the lobby.
 *
 *
 *
 * -- Limits
 *
 * Once an Arcade game starts, all people who join the lobby become
 * spectators. While games can listen out for spectator joins and log
 * stuff themselves, the native systems aren't designed with this in mind.
 *
 * Spectators are spectators. If someone leaves the game, they can't rejoin
 * as a player.
 *
 * Due to limitations, the Arcade Engine has it's games last without a time
 * cap. Games must end and clean themselves up. Bad games don't clean themselves
 * up.
 *
 * If things break, the game better have a way to deal with it. This engine doesn't
 * have any way of cleaning up minus regular tasks cleaning up. It's recommended
 * your whole game is encased in a try catch and you clean up if it reaches the catch.
 *
 * While games can start with only one person, again, they're not designed to. It can
 * be anything from inefficient to broken. Games targetting the
 *
 */
public class ArcadeEngineTask extends ArchTask {

    @Override
    protected void run(String id) {

        Map<String, Object> threadData = BotExtension.getBotExtended().getTaskManager().getThreadData(id);

        //Data checks
        //requiredDataChecks(threadData);
        //defaultDataChecks(threadData);

        Game header = (Game) threadData.get("engine_gameheader");
        ArchTask gameclass = (ArchTask) threadData.get("engine_gameclass");
        Category category = Bot.getBot().getJDA().getCategoryById((String) ArcadeT5Init.getPlugin().getGameConfig().getConfig().get("category"));
        String serverid = "";
        int check = 100;
        while (check != 0){
            // Check ID is unique, else keep on regenerating - Can cause an infinite loop if you exceed 100 servers of the same type. The more servers, the more work.
            //TODO: Add a thread watcher which a task can request to close a thread if misbehaving. Task should be able to set a time which, if exceeded, the thread is interrupted.
            check = 0;
            serverid = header.getTypeid()+"-"+String.valueOf(BotExtension.getBotExtended().getTaskManager().getRunningTasks().size())+ new Random().nextInt(100);
            for(TextChannel channel:category.getTextChannels()){
                if(channel.getName().equals(serverid)){
                    check++;
                }
            }

        }

        TaskManager tm = BotExtension.getBotExtended().getTaskManager();

        tm.putThreadData(id, "arcade-serverid", serverid);

        // -- Lobby Process

        createLobby(id, serverid);

        tm.putThreadData(id, "gamestart", false);

        while(!((boolean) tm.getThreadData(id).get("gamestart"))) {
            lobbyLoop(id);
            startLoop(id);
        }

        gameclass.execute(threadData);



        //Clean up Lobby & Join area
        Bot.getBot().getJDA().getTextChannelById((String) threadData.get("runtime_lobbyid")).delete().complete();
        ((ArcadeListener) threadData.get("runtime_joineventlistener")).unregister();
        BotExtension.getBotExtended().getTaskManager().removeThreadData(id, "runtime_joineventlistener");
        Bot.getBot().getJDA().getTextChannelById((String) threadData.get("runtime_joinid")).delete().complete();

        //TODO: Add spectator joining method.


    }

    protected void lobbyLoop(String id){
        TaskManager tm = BotExtension.getBotExtended().getTaskManager();
        Role role = Bot.getBot().getJDA().getRoleById((String)tm.getThreadData(id).get("runtime_lobbyrole"));
        TextChannel lobby = Bot.getBot().getJDA().getTextChannelById((String)tm.getThreadData(id).get("runtime_lobbyid"));
        lobby.getManager().setTopic(":clock3: Starting soon | Not Enough Players ("+tm.getThreadData(id).get("engine_minplayers")+" min) | "+role.getGuild().getMembersWithRoles(role).size()+"/"+tm.getThreadData(id).get("engine_maxplayers")).complete();


        while(role.getGuild().getMembersWithRoles(role).size() < (int) tm.getThreadData(id).get("engine_minplayers")){
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException err){ }
        }

    }

    protected void startLoop(String id){
        TaskManager tm = BotExtension.getBotExtended().getTaskManager();
        tm.putThreadData(id, "runtime_startTimer", 15);
        TextChannel lobby = Bot.getBot().getJDA().getTextChannelById((String)tm.getThreadData(id).get("runtime_lobbyid"));

        Role lobbyrole = Bot.getBot().getJDA().getRoleById((String)tm.getThreadData(id).get("runtime_lobbyrole"));

        tm.putThreadData(id, "gamestart", true);

        while((int)tm.getThreadData(id).get("runtime_startTimer") > 0){
            lobby.getManager().setTopic(":clock3: Starting game | Time till start: "+String.valueOf((int)tm.getThreadData(id).get("runtime_startTimer"))+"s | "+lobbyrole.getGuild().getMembersWithRoles(lobbyrole).size()+"/"+tm.getThreadData(id).get("engine_maxplayers")).complete();

            if(lobbyrole.getGuild().getMembersWithRoles(lobbyrole).size() < (int) tm.getThreadData(id).get("engine_minplayers")){
                tm.putThreadData(id, "runtime_startTimer", 0);
                tm.putThreadData(id, "gamestart", false);
            }

            tm.putThreadData(id, "runtime_startTimer", (int)tm.getThreadData(id).get("runtime_startTimer") - 1);
        }
    }

    protected final void createLobby(String id, String serverid){

        // runtime_joinmsgid
        // runtime_lobbyid
        // runtime_joinid
        // runtime_lobbyrole

        TaskManager tm = BotExtension.getBotExtended().getTaskManager();

        Game header = (Game) tm.getThreadData(id).get("engine_gameheader");
        ArchTask gameclass = (ArchTask) tm.getThreadData(id).get("engine_gameclass");
        Category category = Bot.getBot().getJDA().getCategoryById((String) ArcadeT5Init.getPlugin().getGameConfig().getConfig().get("category"));

        Role role = category.getGuild().getController().createRole().setName("lobby-"+serverid).setMentionable(false).setHoisted(false).setPermissions(new ArrayList<Permission>()).complete();
        tm.getThreadData(id).put("runtime_lobbyrole", role.getId());

        TextChannel lobby = (TextChannel) category.createTextChannel("lobby-"+serverid).complete();
        BotExtension.getBotExtended().getTaskManager().putThreadData(id, "runtime_lobbyid", lobby.getId());
        lobby.getManager().setTopic(":clock3: Starting soon | Not Enough Players ("+tm.getThreadData(id).get("engine_minplayers")+" min) | "+role.getGuild().getMembersWithRoles(role).size()+"/"+tm.getThreadData(id).get("engine_maxplayers")).complete();
        lobby.sendMessage(new EmbedBuilder().setTitle("Game: "+header.getDisplayname()).setDescription(header.getDescription()).addField("Version", header.getVersion(), false).setColor(Color.MAGENTA).build()).complete().pin().queue();
        lobby.createPermissionOverride(category.getGuild().getPublicRole()).setDeny(Permission.VIEW_CHANNEL, Permission.MESSAGE_READ).queue();
        lobby.createPermissionOverride(role).setAllow(Permission.MESSAGE_READ, Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE, Permission.MESSAGE_HISTORY).queue();

        TextChannel join = (TextChannel) category.createTextChannel("join-"+serverid).complete();
        BotExtension.getBotExtended().getTaskManager().putThreadData(id, "runtime_joinid", join.getId());
        join.getManager().setTopic("Join Lobby by reacting | ("+tm.getThreadData(id).get("engine_minplayers")+" min) | 0/"+tm.getThreadData(id).get("engine_maxplayers")).complete();
        Message msg = join.sendMessage(new EmbedBuilder().setTitle("Join Game: "+serverid).setDescription(header.getDescription()).addField("Game", header.getDisplayname()+" ("+header.getTypeid()+")", true).addField("Version", header.getVersion(), true).addField("Players", "0/"+tm.getThreadData(id).get("engine_maxplayers"),true).setColor(Color.MAGENTA).build()).complete();
        BotExtension.getBotExtended().getTaskManager().putThreadData(id, "runtime_joinmsgid", msg.getId());
        msg.addReaction("tada").complete();
        msg.getChannel().sendMessage("**React with :tada: to join the lobby!**").queue();
        join.createPermissionOverride(category.getGuild().getPublicRole()).setAllow(Permission.MESSAGE_READ, Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY).setDeny(Permission.MESSAGE_WRITE).queue();
        join.createPermissionOverride(role).setDeny(Permission.MESSAGE_READ, Permission.VIEW_CHANNEL).queue();


        new ArcadeListener(id, msg.getId()).register();
    }

    /*protected final void requiredDataChecks(Map<String, Object> threadData){
        Validator.verifyType(threadData.get("engine_gameheader"), Game.class, "Data point 'gameheader' must be of type 'Game' for the Arcade Engine to function."); //TODO: Update ArchT5 to support adding additional info.
        Validator.verifyType(threadData.get("engine_gameclass"), ArchTask.class, "Data point 'gameclass' must be of type 'ArchTask' for the Arcade Engine to function.");
    }

    protected final void defaultDataChecks(Map<String, Object> threadData){

        // -- Regular Engine Data Checks

        if(!Validator.verifyTypeBool(threadData.get("engine_minplayers"), Integer.class)){
            threadData.put("engine_minplayers", 4);
        }
        if(!Validator.verifyTypeBool(threadData.get("engine_maxplayers"), Integer.class)){
            threadData.put("engine_maxplayers", 16);
        }

        // -- Arcade Specific Checks
        if(!Validator.verifyTypeBool(threadData.get("arcade_hdevc"), Boolean.class)){
            threadData.put("arcade_hdevc", false);
        }
        if(!Validator.verifyTypeBool(threadData.get("arcade_specenabled"), Boolean.class)){
            threadData.put("arcade_specenabled", false);
        }
    }*/

    @Override
    public void cleanUp(String dataID) {
        //TODO: Delete Lobby role and channel, game should have already copied any useful data and created the game channel.
    }
}
