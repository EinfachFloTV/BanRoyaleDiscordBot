

package main.java.org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.List;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
    public static String prefix = "!";
    public static JDA client;
    public static String ownerID = "871714118946660352";

    public Main() {
    }

    public static void main(String[] args) throws InterruptedException {

        startBot();
        new Thread(() -> {

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            String command = "";

            try {
                while ((command = input.readLine()) != null) {
                    if (!command.startsWith("!")) return;
                    switch (command.substring(1)) {
                        case "bot-stop":
                            System.out.println("Bot ist jetzt Offline");
                            Main.client.shutdown();
                            break;
                        case "bot-start":
                            System.out.println("Bot wird gestartet");
                            try {
                                Main.startBot();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            break;
                        case "bot-restart":
                            Main.client.shutdown();
                            System.out.println("Bot ist jetzt Offline");
                            try {
                                Main.startBot();
                                System.out.println("Bot ist jetzt wieder Online");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }).start();
    }

    public static void startBot() throws InterruptedException {
        String token = Variable.token;
        JDABuilder jda = JDABuilder.createDefault(token).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE).setActivity(Activity.playing("mit dem Bannhammer!")).setChunkingFilter(ChunkingFilter.ALL).setMemberCachePolicy(MemberCachePolicy.ALL).enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_INVITES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_BANS);
        jda.enableCache(CacheFlag.ONLINE_STATUS);
        EnumSet<CacheFlag> enumSet = EnumSet.of(CacheFlag.ONLINE_STATUS, CacheFlag.CLIENT_STATUS, CacheFlag.EMOJI, CacheFlag.VOICE_STATE, CacheFlag.STICKER);
        jda.enableCache(enumSet);
        jda.addEventListeners(new banCommand(), new VERIFY(), new BotSettingsCommands());
        System.out.println("[Bot] Der Bot ist nun online");
        System.out.println("[Bot] Der Prefix lautet: " + prefix);


        client = jda.build().awaitReady();

        OptionData optionDataUser = new OptionData(OptionType.USER, "user", "Wähle einen Benutzer!", true);
        client.awaitReady().updateCommands().addCommands(
                Commands.slash("ban", "Banne einen Nutzer!").addOptions(optionDataUser),


                Commands.slash("banroyale", "Einstellungen zum Event")
                        .addSubcommands(new SubcommandData("verify-embed", "Hier mit kannst du das Verify/Erklärungs Embed in den Channel Posten!"))
                                .addSubcommands(new SubcommandData("start", "Hier mit kannst du das Event Starten!"))
                                .addSubcommands(new SubcommandData("end", "Hier mit kannst du das Event beenden und alle Nutzer werden entbannt!")),

                Commands.slash("bot", "Bot Einstellungen!")
                        .addSubcommands(new SubcommandData("restart", "Hier mit kannst du den Bot Restarten!"))
                        .addSubcommands(new SubcommandData("stop", "Hier mit kannst du den Bot Stoppen!"))
        ).queue();
    }
}
