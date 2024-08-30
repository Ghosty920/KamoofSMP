package me.ghosty.kamoofsmp.managers;

import lombok.experimental.UtilityClass;
import me.ghosty.kamoofsmp.KamoofSMP;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.regex.Pattern;

@UtilityClass
public final class SkullManager {
	
	private static final NamespacedKey keyTimestamp = new NamespacedKey("kamoofsmp", "timestamp");
	private static final NamespacedKey keyPlayer = new NamespacedKey("kamoofsmp", "player");
	
	public static ItemStack getSkull(String player) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(player));
		meta.setItemName(KamoofSMP.getInstance().getConfig().getString("head-name").replace("%player%", player));
		meta.setLore(KamoofSMP.getInstance().getConfig().getStringList("head-lore"));
		
		boolean stackable = KamoofSMP.getInstance().getConfig().getBoolean("options.stackable");
		meta.getPersistentDataContainer().set(keyTimestamp, PersistentDataType.LONG, stackable ? -1L : System.currentTimeMillis());
		meta.getPersistentDataContainer().set(keyPlayer, PersistentDataType.STRING, player);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static OfflinePlayer getOwner(ItemStack item) {
		if (item == null || !item.hasItemMeta())
			return null;
		if (!(item.getItemMeta() instanceof SkullMeta meta))
			return null;
		if (!meta.getPersistentDataContainer().has(keyTimestamp))
			return null;
		return meta.getOwningPlayer();
	}
	
	public static String getName(ItemStack item) {
		if (item == null || !item.hasItemMeta())
			return null;
		if (!(item.getItemMeta() instanceof SkullMeta meta))
			return null;
		return meta.getPersistentDataContainer().get(keyPlayer, PersistentDataType.STRING);
	}
}
