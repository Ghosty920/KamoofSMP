package me.ghosty.kamoofsmp.features;

import me.ghosty.kamoofsmp.managers.SkullManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class CommandGiveHead implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("§cThis command is only usable by players.");
			return true;
		}
		
		if (args.length == 0) {
			player.sendMessage(String.format("§cUsage: /%s <playerName>", label.toLowerCase()));
			return true;
		}
		
		ItemStack item = SkullManager.getSkull(args[0]);
		if(player.getInventory().addItem(item).size() > 0)
			player.sendMessage("§cYour inventory is full");
		else
			player.sendMessage("§aGave yourself the head of §e"+args[0]);
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 1)
			return List.of();
		
		ArrayList<String> values = new ArrayList<>();
		for (OfflinePlayer player : Bukkit.getOfflinePlayers())
			values.add(player.getName());
		
		if (args.length > 0)
			values.removeIf(player -> !player.toLowerCase().contains(args[0].toLowerCase()));
		
		return values;
	}
	
}
