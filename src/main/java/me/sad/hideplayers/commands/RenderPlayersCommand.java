package me.sad.hideplayers.commands;

import com.google.common.collect.Lists;
import me.sad.hideplayers.HidePlayers;
import me.sad.hideplayers.utils.ConfigUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    @Override public List<String> getCommandAliases() { return Collections.singletonList("hp"); }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "toggle", "help", "list", "add", "remove");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("remove")) {
                return getListOfStringsMatchingLastWord(args, HidePlayers.players);
            } else if (args[0].equals("add")) {
                //TODO: Find better way of doing this.
                NetHandlerPlayClient connection = Minecraft.getMinecraft().getNetHandler();
                List<NetworkPlayerInfo> playerInfo = new ArrayList(connection.getPlayerInfoMap());
                List<String> playerList = Lists.<String>newArrayList();
                for (int i = 0; i < playerInfo.size(); ++i) {
                    if (i < playerInfo.size()) {
                        playerList.add(playerInfo.get(i).getGameProfile().getName());
                    } }
                return getListOfStringsMatchingLastWord(args, playerList);
            }
        }
        return null;
    }

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
