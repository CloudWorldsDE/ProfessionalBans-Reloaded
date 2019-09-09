package de.tutorialwork.commands;

import de.tutorialwork.listener.Chat;
import de.tutorialwork.main.Main;
import de.tutorialwork.utils.BanManager;
import de.tutorialwork.utils.LogManager;
import de.tutorialwork.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Chatlog extends Command {
    public Chatlog(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(args.length == 0){
                p.sendMessage(Main.Prefix+"/chatlog <Spieler>");
            } else {
                String UUID = UUIDFetcher.getUUID(args[0]);
                if(UUID != null){
                    if(BanManager.playerExists(UUID)){
                        if(!p.getUniqueId().toString().equals(UUID)){
                            if(Chat.hasMessages(UUID)){
                                String ID = Chat.createChatlog(UUID, p.getUniqueId().toString());
                                p.sendMessage(Main.Prefix+"Der Chatlog von §e§l"+BanManager.getNameByUUID(UUID)+" §7wurde erfolgreich erstellt");
                                if(Main.WebURL != null){
                                    p.sendMessage(Main.Prefix+"Link: §e§l"+Main.WebURL+"public/chatlog.php?id="+ID);
                                } else {
                                    p.sendMessage(Main.Prefix+"Der Link des Chatlogs kann nicht ausgegeben werden");
                                }
                                LogManager.createEntry(UUID, p.getUniqueId().toString(), "CREATE_CHATLOG", ID);
                            } else {
                                p.sendMessage(Main.Prefix+"§cDieser Spieler hat in der letzten Zeit keine Nachrichten verfasst");
                            }
                        } else {
                            p.sendMessage(Main.Prefix+"§cDu kannst kein Chatlog von dir selbst erstellen");
                        }
                    } else {
                        p.sendMessage(Main.Prefix+"§cDieser Spieler wurde nicht gefunden");
                    }
                } else {
                    p.sendMessage(Main.Prefix+"§cDieser Spieler wurde nicht gefunden");
                }
            }
        } else {

        }
    }
}
