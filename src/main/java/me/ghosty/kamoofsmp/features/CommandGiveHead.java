package me.ghosty.kamoofsmp.features;

import me.ghosty.kamoofsmp.managers.SkullManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class CommandGiveHead implements CommandExecutor, TabCompleter {
	
	private static Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9_]{1,16}$");
	
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
		
		if(!usernamePattern.matcher(args[0]).matches()) {
			player.sendMessage(String.format("§cInvalid username '%s'!", args[0]));
			return true;
		}
		
		ItemStack item = SkullManager.getSkull(args[0]);
		if (player.getInventory().addItem(item).size() > 0)
			player.sendMessage("§cYour inventory is full");
		else
			player.sendMessage("§aGave yourself the head of §e" + args[0]);
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 1)
			return List.of();
		
		ArrayList<String> values = new ArrayList<>();
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if(player == null || player.getName() == null)
				continue;
			values.add(player.getName());
		}
		
		if (args.length == 1) {
			String toCheck = args[0].toLowerCase().trim();
			values.removeIf(player -> !player.toLowerCase().trim().contains(toCheck));
		}
		
		return values;
	}
	
}
