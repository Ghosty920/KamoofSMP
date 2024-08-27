package me.ghosty.kamoofsmp;

import lombok.Getter;
import me.ghosty.kamoofsmp.features.*;
import me.ghosty.kamoofsmp.managers.MessageManager;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public final class KamoofSMP extends JavaPlugin {
	
	@Getter
	private static KamoofSMP instance;
	
	private static boolean isSpigot = false, isAdventure = false;
	
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
	}
	
	/*public static BaseComponent[] configComponent(String path) {
		return MessageManager.toBaseComponent(instance.getConfig().getString(path));
	}*/
	
	public static void sendMessage(Player player, String path, Map<String, Object> args) {
		String message = instance.getConfig().getString(path);
		
		List<String> keys = args.keySet().stream().toList();
		List<Object> values = args.values().stream().toList();
		for (int i = 0; i < args.size(); i++)
			message = message.replace("%"+keys.get(i)+"%", values.get(i).toString());
		
		player.sendRawMessage(MessageManager.toJSON(MessageManager.deserialize(message)));
	}
	
	public static void sendMessage(Player player, String path) {
		String message = instance.getConfig().getString(path);
		player.sendRawMessage(MessageManager.toJSON(MessageManager.deserialize(message)));
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		instance = this;
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
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
