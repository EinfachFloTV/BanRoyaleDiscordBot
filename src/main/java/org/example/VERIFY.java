//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package main.java.org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class VERIFY extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("banroyale")) {
            if (event.getSubcommandName().equals("verify-embed")) {
                if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {

                    event.reply("Embed Erfolgreich in den Channel gesendet!").setEphemeral(true).queue();

                    EmbedBuilder eb = (new EmbedBuilder())
                            .setTitle("Hier kannst du am event Teilzunehmen!")
                            .setDescription("Sobald das Event gestartet ist geht ihr einfach in den <#"+Variable.chatChannelId+"> chat! Macht einfach </ban:"+Variable.banCommandId+"> und wählt ein Nutzer aus")
                            .addField("Mit dem Bot Verifizieren", "> Du kannst dich mit dem Bot Verifizieren, damit du sobald das Event vorbei ist automatisch wieder auf den Server joinst, sonst musst du nach einer Runde immer wieder diesem Server Rejoinen!", false)
                            .addField("Wie Verifiziere ich mich mit dem Bot?", "> Du musst unten auf den ```✅ Verifizieren``` Button drücken!", false);
                    Button b = Button.link("https://discord.com/oauth2/authorize?client_id=1110153198787891230&redirect_uri=https://restorecord.com/api/callback&response_type=code&scope=identify+guilds.join&state=1109882852738338836","✅ Verifizieren");
                    event.getChannel().sendMessageEmbeds(eb.build()).addActionRow(b).queue();

                } else {
                    event.reply("Du hast keine Rechte auf diesen Command!").setEphemeral(true).queue();
                }
            }
        }
    }
    /*public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getContentStripped().equals(Main.prefix + "setup verify")) {
            event.getMessage().delete().queue();
            if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)) return;
            EmbedBuilder eb = (new EmbedBuilder())
                    .setTitle("Hier kannst du am event Teilzunehmen!")
                    .setDescription("Sobald das Event gestartet ist geht ihr einfach in den <#1109882854680301670> chat! Macht einfach </ban:1224348809870704640> und wählt ein Nutzer aus")
                    .addField("Mit dem Bot Verifizieren", "> Du kannst dich mit dem Bot Verifizieren, damit du sobald das Event vorbei ist automatisch wieder auf den Server joinst, sonst musst du nach einer Runde immer wieder diesem Server Rejoinen!", false)
                    .addField("Wie Verifiziere ich mich mit dem Bot?", "> Du musst unten auf den ```✅ Verifizieren``` Button drücken!", false);
            Button b = Button.link("https://discord.com/oauth2/authorize?client_id=1110153198787891230&redirect_uri=https://restorecord.com/api/callback&response_type=code&scope=identify+guilds.join&state=1109882852738338836","✅ Verifizieren");
            event.getChannel().sendMessageEmbeds(eb.build()).addActionRow(b).queue();
        }

    }*/
}
