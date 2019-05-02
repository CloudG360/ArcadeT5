package net.cloudsbots.arcadet5.tasks;

import net.cloudsbots.archseriest.archt5.Bot;
import net.cloudsbots.archseriest.archt5.components.Logger;
import net.cloudsbots.archseriest.archt5dev.extensions.BotExtension;
import net.cloudsbots.archseriest.archt5dev.tasks.ArchTask;
import net.dv8tion.jda.core.EmbedBuilder;

import java.util.concurrent.TimeUnit;

public class TaskManagerBackgroundProcess extends ArchTask {

    @Override
    protected void run(String id) {
        while(true){
            try {
                TimeUnit.MINUTES.sleep(5);
                int length = 0;
                String text = "";
                for(String process: BotExtension.getBotExtended().getTaskManager().getRunningTasks()){
                    String task = process+"\n";
                    if(task.length() > 64){
                        Logger.getLogger().logWarn("Task/"+id, "Task '"+id+"' exceeds the 64 character limit.");
                        task = "TASK_ID_TOO_LONG - Blocked (Limited to 64 Characters)\n";
                    }

                    if(length + task.length() > 2048){
                        Bot.getBot().getJDA().getTextChannelById("546319924599980044").sendMessage(new EmbedBuilder().setTitle("Tasks").setDescription(text).build()).complete();
                        length = 0;
                        text = "";
                    }
                    text = text.concat(task);
                    length += task.length();
                }
                Bot.getBot().getJDA().getTextChannelById("546319924599980044").sendMessage(new EmbedBuilder().setTitle("Tasks").setDescription(text).build()).complete();
                length = 0;
                text = "";
            } catch (InterruptedException err) {

            }
        }
    }

    @Override
    public void cleanUp(String dataID) {

    }
}
