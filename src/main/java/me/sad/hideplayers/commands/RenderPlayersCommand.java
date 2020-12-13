package me.sad.hideplayers.commands;

import me.sad.hideplayers.HidePlayers;
import me.sad.hideplayers.utils.ConfigUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import java.io.IOException;

public class RenderPlayersCommand extends CommandBase {

    private void toggleRenderer(ICommandSender sender) {
        HidePlayers.toggled = !HidePlayers.toggled;
        if (HidePlayers.toggled) sender.addChatMessage(new ChatComponentText(HidePlayers.prefix + "Toggled rendering players \u00a7aON\u00a7r!"));
        else sender.addChatMessage(new ChatComponentText(HidePlayers.prefix + "Toggled rendering players \u00a7cOFF\u00a7r!"));
        try {
            ConfigUtils.writeConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public int getRequiredPermissionLevel() { return 0; }
    @Override public String getCommandName() { return "hideplayers"; }
    @Override public String getCommandUsage(ICommandSender sender) { return "/hideplayers (toggle/help/list/add/remove) [player]"; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) toggleRenderer(sender);
        else {
            switch (args[0].toLowerCase()) {
                case "toggle":
                    toggleRenderer(sender);
                    break;
                case "help":
                    sender.addChatMessage(new ChatComponentText(HidePlayers.prefix + getCommandUsage(sender)));
                    break;
                case "list":
                    sender.addChatMessage(new ChatComponentText(HidePlayers.prefix + HidePlayers.players.toString().substring(1,HidePlayers.players.toString().length()-1)));
                    break;
                case "add":
                    if (args.length == 1) {
                        sender.addChatMessage(new ChatComponentText(HidePlayers.prefix + getCommandUsage(sender)));
                    } else {
                        if (HidePlayers.players.contains(args[1].toLowerCase())) {
                            sender.addChatMessage(new ChatComponentText(HidePlayers.prefix + "\u00a7b" + args[1] + "\u00a7f is already in the list!"));
                        } else {
                            HidePlayers.players.add(args[1].toLowerCase());
                            sender.addChatMessage(new ChatComponentText(HidePlayers.prefix + "\u00a7b" + args[1] + "\u00a7f has been added to the list!"));
                            try {
                                ConfigUtils.writeConfig();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case "remove":
                    if (args.length == 1) {
                        sender.addChatMessage(new ChatComponentText(HidePlayers.prefix + getCommandUsage(sender)));
                    } else {
                        if (!HidePlayers.players.contains(args[1].toLowerCase())) {
                            sender.addChatMessage(new ChatComponentText(HidePlayers.prefix + "\u00a7b" + args[1] + "\u00a7f is not in the list!"));
                        } else {
                            HidePlayers.players.remove(args[1].toLowerCase());
                            sender.addChatMessage(new ChatComponentText(HidePlayers.prefix + "\u00a7b" + args[1] + "\u00a7f has been removed from the list!"));
                            try {
                                ConfigUtils.writeConfig();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
        }
    }
}
