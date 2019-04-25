package net.cloudsbots.arcadet5.games;

import net.cloudsbots.archseriest.archt5.Bot;
import net.cloudsbots.archseriest.archt5.extensions.BotExtension;
import net.cloudsbots.archseriest.archt5.tasks.TaskManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.regex.Pattern;

public class ArcadeListener extends ListenerAdapter {

    private String messageID;
    private String taskid;

    public ArcadeListener(String taskid, String messageID){
        this.messageID = messageID;
        this.taskid = taskid;
    }

    public ArcadeListener register(){
        Bot.getBot().getJDA().addEventListener(this);
        BotExtension.getBotExtended().getTaskManager().putThreadData(taskid, "runtime_joineventlistener", this);
        return this;
    }

    public void unregister(){
        Bot.getBot().getJDA().removeEventListener(this);
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(event.getMessageId().equals(messageID)) {
            try {
                event.getReaction().removeReaction(event.getUser()).queue();
            } catch (Exception err) {
                //Oh well, who cares.
            }
            TaskManager tm = BotExtension.getBotExtended().getTaskManager();
            Game header = (Game) tm.getThreadData(taskid).get("engine_gameheader");
            String serverid = (String) tm.getThreadData(taskid).get("arcade-serverid");
            Role lobbyrole = Bot.getBot().getJDA().getRoleById((String) tm.getThreadData(taskid).get("runtime_lobbyrole"));
            event.getGuild().getController().addRolesToMember(event.getMember(), lobbyrole).queue();
            event.getChannel().getMessageById(event.getMessageId()).complete().editMessage(new EmbedBuilder().setTitle("Join Game: "+serverid).setDescription(header.getDescription()).addField("Game", header.getDisplayname()+" ("+header.getTypeid()+")", true).addField("Version", header.getVersion(), true).addField("Players", lobbyrole.getGuild().getMembersWithRoles(lobbyrole).size()+"/"+tm.getThreadData(taskid).get("engine_maxplayers"),true).setColor(Color.MAGENTA).build()).queue();
            event.getChannel().getManager().setTopic("Join Lobby by reacting | ("+tm.getThreadData(taskid).get("engine_minplayers")+" min) | "+lobbyrole.getGuild().getMembersWithRoles(lobbyrole).size()+"/"+tm.getThreadData(taskid).get("engine_maxplayers")).complete();
            TextChannel lobby = event.getGuild().getTextChannelById((String)tm.getThreadData(taskid).get("runtime_lobbyid"));
            String[] split = lobby.getTopic().split(Pattern.quote("|"));
            lobby.getManager().setTopic(split[0]+"|"+split[1]+"| "+lobbyrole.getGuild().getMembersWithRoles(lobbyrole).size()+"/"+tm.getThreadData(taskid).get("engine_maxplayers")).complete();
        }

    }
}
