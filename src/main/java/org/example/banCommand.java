
package main.java.org.example;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class banCommand extends ListenerAdapter {
    private boolean banRoyaleEnabled = false;

    public banCommand() {
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("ban")) {
            Member target = event.getOption("user").getAsMember();

            try {
                if (this.banRoyaleEnabled) {
                    if (event.getUser().getMutualGuilds().contains(event.getGuild()) && target.getUser().getMutualGuilds().contains(event.getGuild())) {
                        if (!target.getUser().isBot() && !target.hasPermission(Permission.ADMINISTRATOR)) {
                            if (!target.getId().equals(event.getMember().getId())) {
                                target.ban(0, TimeUnit.HOURS).reason("BanRoyaleBattle").queue();
                                event.reply("Du hast " + target.getUser().getAsMention() + " erfolgreich gebannt!").setEphemeral(true).queue();
                                EmbedBuilder e2 = (new EmbedBuilder()).setTitle("Nutzer wurde Gebannt").addField("Gebannter Nutzer:", target.getAsMention(), false).addField("Wurde gebannt von:", event.getMember().getAsMention(), false);
                                event.getGuild().getTextChannelById(Variable.logChannelId).sendMessageEmbeds(e2.build(), new MessageEmbed[0]).queue();
                            } else {
                                event.reply(" **BRUH!** Du kannst dich doch nicht selbst bannen!").setEphemeral(true).queue();
                            }
                        } else {
                            event.reply("**BRUH!** Du kannst doch keinen Administrator oder Bot bannen!").setEphemeral(true).queue();
                        }
                    } else {
                        event.reply("**BRUH!** Das Mitglied wurde bereits vom Bannhammer gebannt!").setEphemeral(true).queue();
                    }
                } else {
                    event.reply("Ban-Royale Modus wurde noch nicht Aktiviert!").setEphemeral(true).queue();
                }
            } catch (Exception var4) {
                event.reply(" **BRUH!** Das Mitglied wurde bereits vom Bannhammer gebannt!").setEphemeral(true).queue();
            }
        }

        final MessageChannel channel = event.getChannel();
        if (event.getName().equals("banroyale")) {

            if (event.getSubcommandName().equals("start")) {
                if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if(banRoyaleEnabled == false) {

                        event.reply("Das Event startet nun!").setEphemeral(true).queue();

                        channel.sendMessage("✅ | Der Countdown zum Start des Events beginnt nun!").queue();
                        channel.sendMessage("**3**").queue();
                        final Timer t1 = new Timer();
                        t1.schedule(new TimerTask() {
                            public void run() {
                                channel.sendMessage("**2**").queue();
                                t1.cancel();
                            }
                        }, 1000L);
                        final Timer t2 = new Timer();
                        t2.schedule(new TimerTask() {
                            public void run() {
                                channel.sendMessage("**1**").queue();
                                t2.cancel();
                            }
                        }, 2000L);
                        final Timer t3 = new Timer();
                        t3.schedule(new TimerTask() {
                            public void run() {
                                channel.sendMessage("** BAN-ROYALE Modus aktiviert! Nutzt </ban:"+Variable.banCommandId+"> um eure Gegner zu bannen, bevor sie es tun!**").complete();
                                t3.cancel();
                                banCommand.this.banRoyaleEnabled = true;
                                event.getGuild().getPublicRole().getManager().givePermissions(new Permission[]{Permission.USE_APPLICATION_COMMANDS}).queue();
                            }
                        }, 3000L);
                    } else {
                        event.reply("Ban Royale-Modus ist bereits schon gestartet!").setEphemeral(true).queue();
                    }
                } else {
                    event.reply("Du hast keine Berechtigung, den Ban Royale-Modus zu aktivieren!").setEphemeral(true).queue();
                }
            }
            if (event.getSubcommandName().equals("end")) {
                if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if(banRoyaleEnabled == true) {
                        this.banRoyaleEnabled = false;
                        channel.sendMessage("Ban Royale-Modus wurde deaktiviert!").queue();
                        event.getGuild().getPublicRole().getManager().revokePermissions(new Permission[]{Permission.USE_APPLICATION_COMMANDS}).queue();
                        Guild guild = event.getGuild();
                        guild.retrieveBanList().queue((banList) -> {
                            banList.forEach((ban) -> {
                                guild.unban(ban.getUser()).queue();
                            });
                            event.reply("✅ | Du hast alle Nutzer erfolgreich für die nächste Runde entbannt!").setEphemeral(true).queue();
                        });
                    } else {
                        event.reply("Ban Royale-Modus ist schon deaktiviert!").setEphemeral(true).queue();
                    }
                } else {
                    event.reply("Du hast keine Berechtigung, den Ban Royale-Modus zu deaktivieren!").setEphemeral(true).queue();
                }
            }
        }
    }



   /* public void onMessageReceived(final MessageReceivedEvent event) {
        String[] command = event.getMessage().getContentRaw().split(" ");
        final MessageChannel channel = event.getChannel();
        if (command[0].equalsIgnoreCase("!start")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                channel.sendMessage("✅ | Der Countdown zum Start des Events beginnt nun!").queue();
                channel.sendMessage("**3**").queue();
                final Timer t1 = new Timer();
                t1.schedule(new TimerTask() {
                    public void run() {
                        channel.sendMessage("**2**").queue();
                        t1.cancel();
                    }
                }, 1000L);
                final Timer t2 = new Timer();
                t2.schedule(new TimerTask() {
                    public void run() {
                        channel.sendMessage("**1**").queue();
                        t2.cancel();
                    }
                }, 2000L);
                final Timer t3 = new Timer();
                t3.schedule(new TimerTask() {
                    public void run() {
                        Message m = (Message)channel.sendMessage("** BAN-ROYALE Modus aktiviert! Nutzt `/ban` um eure Gegner zu bannen, bevor sie es tun!**").complete();
                        t3.cancel();
                        banCommand.this.banRoyaleEnabled = true;
                        event.getGuild().getPublicRole().getManager().givePermissions(new Permission[]{Permission.USE_APPLICATION_COMMANDS}).queue();
                    }
                }, 3000L);
            } else {
                channel.sendMessage("Du hast keine Berechtigung, den Ban Royale-Modus zu aktivieren!").queue();
            }
        }

        if (command[0].equalsIgnoreCase("!stop")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                this.banRoyaleEnabled = false;
                channel.sendMessage("Ban Royale-Modus deaktiviert!").queue();
                event.getGuild().getPublicRole().getManager().revokePermissions(new Permission[]{Permission.USE_APPLICATION_COMMANDS}).queue();
                Guild guild = event.getGuild();
                guild.retrieveBanList().queue((banList) -> {
                    banList.forEach((ban) -> {
                        guild.unban(ban.getUser()).queue();
                    });
                    event.getChannel().sendMessage("✅ | Du hast alle Nutzer erfolgreich für die nächste Runde entbannt!").queue();
                });
            } else {
                channel.sendMessage("Du hast keine Berechtigung, den Ban Royale-Modus zu deaktivieren!").queue();
            }
        }

    }*/
}
