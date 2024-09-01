package me.ghosty.kamoofsmp;

import lombok.Getter;
import me.ghosty.kamoofsmp.features.*;
import me.ghosty.kamoofsmp.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KamoofSMP extends JavaPlugin {
	
	@Getter
	private static KamoofSMP instance;
	
	public DisguiseRestaurer restaurer;
	
	public static FileConfiguration config() {
		return instance.getConfig();
	}
	
	public static void sendMessage(Player player, String path, String playerName) {
		String message = config().getString(path);
		if (message == null)
			return;
		if (message.isBlank())
			return;
		if (playerName != null)
			message = message.replace("%player%", playerName);
		player.spigot().sendMessage(MessageManager.toBaseComponent(message));
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		instance = this;
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		saveDefaultConfig();
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(restaurer = new DisguiseRestaurer(getDataFolder() + "\\restaurer.yml"), this);
		restaurer.onEnable();
		
		pm.registerEvents(new EventsListener(), this);
		
		setupCommand("givehead", new CommandGiveHead());
		setupCommand("undisguise", new CommandUndisguise());
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		restaurer.onDisable();
	}
	
	private void setupCommand(String name, Object command) {
		getCommand(name).setExecutor((CommandExecutor) command);
		getCommand(name).setTabCompleter((TabCompleter) command);
	}
	
}
