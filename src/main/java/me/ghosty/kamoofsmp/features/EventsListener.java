package me.ghosty.kamoofsmp.features;

import me.ghosty.kamoofsmp.KamoofSMP;
import me.ghosty.kamoofsmp.managers.DisguiseManager;
import me.ghosty.kamoofsmp.managers.SkullManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public final class EventsListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		OfflinePlayer target = SkullManager.getHolder(event.getItem());
		if (target == null)
			return;
		
		switch (event.getAction()) {
			case LEFT_CLICK_BLOCK, LEFT_CLICK_AIR, PHYSICAL -> {
				return;
			}
			case RIGHT_CLICK_BLOCK -> {
				event.setCancelled(true);
				return;
			}
		}
		
		String name = target.getName();
		if (name == null)
			name = SkullManager.getName(event.getItem());
		
		DisguiseManager.disguise(player, name);
		KamoofSMP.sendMessage(player, "messages.disguised", name);
		KamoofSMP.sendMessage(player, "messages.undisguise-info", name);
		// au cas où l'item soit quand même stacké, on n'en retire qu'un
		event.getItem().setAmount(event.getItem().getAmount() - 1);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (DisguiseManager.isDisguised(player)) {
			String disguise = DisguiseManager.getDisguise(player);
			DisguiseManager.undisguise(player);
			KamoofSMP.sendMessage(player, "messages.lostdisguise", disguise);
		}
		
		event.getDrops().add(SkullManager.getSkull(player.getName()));
	}
	
}
