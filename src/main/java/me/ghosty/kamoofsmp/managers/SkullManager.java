package me.ghosty.kamoofsmp.managers;

import lombok.experimental.UtilityClass;
import me.ghosty.kamoofsmp.KamoofSMP;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

@UtilityClass
public final class SkullManager {
	
	private static final Random random = new Random();
	
	public static ItemStack getSkull(String player) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(player));
		meta.setItemName(KamoofSMP.getInstance().getConfig().getString("head-name").replace("%player%", player));
		meta.setLore(KamoofSMP.getInstance().getConfig().getStringList("head-lore"));
		// faire que l'item ne soit pas stackable
		meta.getPersistentDataContainer().set(new NamespacedKey("kamoofsmp", "id"), PersistentDataType.LONG, random.nextLong());
		item.setItemMeta(meta);
		return item;
	}
	
	public static OfflinePlayer getHolder(ItemStack item) {
		if(item == null || !item.hasItemMeta())
			return null;
		if(!(item.getItemMeta() instanceof SkullMeta meta))
			return null;
		return meta.getOwningPlayer();
	}
	
}
