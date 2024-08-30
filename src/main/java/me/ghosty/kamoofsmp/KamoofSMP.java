package me.ghosty.kamoofsmp;

import lombok.Getter;
import me.ghosty.kamoofsmp.features.*;
import me.ghosty.kamoofsmp.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KamoofSMP extends JavaPlugin {
	
	@Getter
	private static KamoofSMP instance;
	
	/*private static boolean isSpigot = false, isAdventure = false, hasBungeeChat = false;
	
	static {
		try {
			Class.forName("org.bukkit.entity.Player.Spigot");
			isSpigot = true;
		} catch (Throwable ignored) {
		}
		try {
			Class.forName("net.kyori.adventure.Adventure");
			isAdventure = true;
		} catch (Throwable ignored) {
		}
		try {
			Class.forName("net.md_5.bungee.chat.ComponentSerializer");
			hasBungeeChat = true;
		} catch (Throwable ignored) {
		}
	}*/
	
	/*public static BaseComponent[] configComponent(String path) {
		return MessageManager.toBaseComponent(instance.getConfig().getString(path));
	}*/
	
	public static void sendMessage(Player player, String path, String playerName) {
		String message = instance.getConfig().getString(path);
		if (message == null)
			return;
		if (playerName != null)
			message = message.replace("%player%", playerName);
		player.spigot().sendMessage(MessageManager.toBaseComponent(message));
//		ComponentSerializer.parse(jsonString);
//		player.spigot().sendMessage(ComponentSerializer.parse(MessageManager.toJSON(MessageManager.deserialize(message))));
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
		pm.registerEvents(new EventsListener(), this);
		
		setupCommand("givehead", new CommandGiveHead());
		setupCommand("undisguise", new CommandUndisguise());
	}
	
	private void setupCommand(String name, Object command) {
		getCommand(name).setExecutor((CommandExecutor) command);
		getCommand(name).setTabCompleter((TabCompleter) command);
	}
	
}
