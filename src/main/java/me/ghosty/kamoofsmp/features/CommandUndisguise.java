package me.ghosty.kamoofsmp.features;

import me.ghosty.kamoofsmp.KamoofSMP;
import me.ghosty.kamoofsmp.managers.DisguiseManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public final class CommandUndisguise implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("§cThis command is only usable by players.");
			return true;
		}
		
		if (DisguiseManager.isDisguised(player)) {
			DisguiseManager.undisguise(player);
			KamoofSMP.sendMessage(player, "messages.undisguised");
		} else {
			KamoofSMP.sendMessage(player, "messages.nodisguise");
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return List.of();
	}
	
}
