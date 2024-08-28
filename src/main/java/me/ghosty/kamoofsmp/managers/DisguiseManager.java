package me.ghosty.kamoofsmp.managers;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import xyz.haoshoku.nick.api.NickAPI;

@UtilityClass
public final class DisguiseManager {
	
	public static void disguise(Player player, String name) {
		NickAPI.nick(player, name);
		NickAPI.setSkin(player, name);
		NickAPI.setUniqueId(player, name);
		NickAPI.setGameProfileName(player, name);
		NickAPI.refreshPlayer(player);
	}
	
	public static void undisguise(Player player) {
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
