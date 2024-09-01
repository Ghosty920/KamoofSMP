package me.ghosty.kamoofsmp.managers;

import lombok.experimental.UtilityClass;
import me.ghosty.kamoofsmp.KamoofSMP;
import org.bukkit.entity.Player;
import xyz.haoshoku.nick.api.NickAPI;

import java.util.HashMap;

@UtilityClass
public final class DisguiseManager {
	
	public static void disguise(Player player, String name) {
		player.setDisplayName(player.getDisplayName().replace(player.getName(), name));
		
		NickAPI.nick(player, name);
		NickAPI.setSkin(player, name);
		NickAPI.setUniqueId(player, name);
		if (KamoofSMP.config().getBoolean("options.gameprofile"))
			NickAPI.setGameProfileName(player, name);
		NickAPI.refreshPlayer(player);
	}
	
	public static void undisguise(Player player) {
		player.setDisplayName(player.getDisplayName().replace(getDisguise(player), NickAPI.getOriginalName(player)));
		
		NickAPI.resetNick(player);
		NickAPI.resetSkin(player);
		NickAPI.resetUniqueId(player);
		if (KamoofSMP.config().getBoolean("options.gameprofile"))
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
