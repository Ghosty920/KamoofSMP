package me.ghosty.kamoofsmp.features;

import lombok.Getter;
import lombok.SneakyThrows;
import me.ghosty.kamoofsmp.KamoofSMP;
import me.ghosty.kamoofsmp.managers.DisguiseManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.haoshoku.nick.api.NickAPI;

import java.io.File;

@Getter
public final class DisguiseRestaurer implements Listener {
	
	private final File file;
	private YamlConfiguration yaml;
	
	@SneakyThrows
	public DisguiseRestaurer(String path) {
		file = new File(path);
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (Throwable exc) {
				throw new RuntimeException("Couldn't create the configuration file at path \"" + path + "\".", exc);
			}
		}
		yaml = YamlConfiguration.loadConfiguration(file);
	}
	
	@SneakyThrows
	public void save() {
		/*file.getParentFile().mkdirs();
		file.createNewFile();*/
		yaml.save(file);
		yaml = YamlConfiguration.loadConfiguration(file);
	}
	
	public static void set(String name, String disguise) {
		DisguiseRestaurer instance = KamoofSMP.getInstance().restaurer;
		instance.yaml.set(name, disguise);
		instance.save();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		System.out.println(event.getPlayer().getName());
		if (!KamoofSMP.config().getBoolean("options.restaure.enabled"))
			return;
		
		Player player = event.getPlayer();
		String name = player.getName();
		if (!yaml.contains(name))
			return;
		
		String disguiseName = yaml.getString(name);
		set(name, null);
		DisguiseManager.disguise(player, disguiseName);
		
		labelJoinMessage:
		{
			String message = event.getJoinMessage();
			if (message == null)
				break labelJoinMessage;
			event.setJoinMessage(message.replace(name, disguiseName));
		}
		
		if (KamoofSMP.config().getBoolean("options.restaure.messages.disguised"))
			KamoofSMP.sendMessage(player, "messages.disguised", disguiseName);
		if (KamoofSMP.config().getBoolean("options.restaure.messages.undisguise-info"))
			KamoofSMP.sendMessage(player, "messages.undisguise-info", disguiseName);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		System.out.println(event.getPlayer().getName());
		if (!KamoofSMP.config().getBoolean("options.restaure.enabled"))
			return;
		
		Player player = event.getPlayer();
		if (!DisguiseManager.isDisguised(player))
			return;
		
		set(NickAPI.getOriginalName(player), DisguiseManager.getDisguise(player));
	}
	
	public void onEnable() {
		if (!KamoofSMP.config().getBoolean("options.restaure.enabled"))
			return;
		
		Bukkit.getOnlinePlayers().forEach(player -> {
			String name = player.getName();
			if (yaml.contains(name)) {
				String disguiseName = yaml.getString(name);
				yaml.set(name, null);
				DisguiseManager.disguise(player, disguiseName);
			}
		});
		save();
	}
	
	public void onDisable() {
		if (!KamoofSMP.config().getBoolean("options.restaure.enabled"))
			return;
		
		Bukkit.getOnlinePlayers().forEach(player -> {
			if (DisguiseManager.isDisguised(player))
				yaml.set(NickAPI.getOriginalName(player), DisguiseManager.getDisguise(player));
		});
		save();
	}
}