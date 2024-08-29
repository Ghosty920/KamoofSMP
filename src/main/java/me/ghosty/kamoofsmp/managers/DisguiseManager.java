package me.ghosty.kamoofsmp.managers;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import xyz.haoshoku.nick.api.NickAPI;

import java.util.HashMap;

@UtilityClass
public final class DisguiseManager {
	
	private static final HashMap<Player, String> displayNames = new HashMap<>();
	
	public static void disguise(Player player, String name) {
		displayNames.put(player, player.getDisplayName());
		player.setDisplayName(player.getDisplayName().replace(player.getName(), name));
		
		NickAPI.nick(player, name);
		NickAPI.setSkin(player, name);
		NickAPI.setUniqueId(player, name);
		NickAPI.setGameProfileName(player, name);
		NickAPI.refreshPlayer(player);
	}
	
	public static void undisguise(Player player) {
		player.setDisplayName(displayNames.get(player));
		displayNames.remove(player);
		
		NickAPI.resetNick(player);
		NickAPI.resetSkin(player);
		NickAPI.resetUniqueId(player);
		NickAPI.resetGameProfileName(player);
		NickAPI.refreshPlayer(player);
	}
	
	public static boolean isDisguised(Player player) {
		return NickAPI.isNicked(player);
	}
	
	public static String getDisguise(Player player) {
		return NickAPI.getName(player);
	}
	
}
