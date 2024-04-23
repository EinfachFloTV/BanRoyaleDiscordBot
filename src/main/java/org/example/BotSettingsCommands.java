package main.java.org.example;



import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotSettingsCommands extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(!event.getMember().getId().equals(Main.ownerID)) return;
        if(!event.getName().equals("bot")) return;

        if(event.getSubcommandName().equals("restart")) {
            event.reply("Bot wird neugestartet...").setEphemeral(true).queue();
            Main.client.shutdown();
            System.out.println("Bot ist jetzt Offline");
            try {
                Main.startBot();
                System.out.println("Bot ist jetzt wieder Online");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else if (event.getSubcommandName().equals("stop")) {

            event.reply("Bot wird heruntergefahren...").setEphemeral(true).queue();
            Main.client.shutdown();
            System.out.println("Bot ist jetzt Offline");


        }
    }

}
