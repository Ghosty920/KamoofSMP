package me.ghosty.kamoofsmp.features;

import me.ghosty.kamoofsmp.KamoofSMP;
import me.ghosty.kamoofsmp.managers.DisguiseManager;
import me.ghosty.kamoofsmp.managers.SkullManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.haoshoku.nick.api.NickAPI;

public final class EventsListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		OfflinePlayer target = SkullManager.getOwner(event.getItem());
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
		if (target instanceof Player targetP)
			name = NickAPI.getOriginalName(targetP);
		if (name == null)
			name = SkullManager.getName(event.getItem());
		
		DisguiseManager.disguise(player, name);
		KamoofSMP.sendMessage(player, "messages.disguised", name);
		KamoofSMP.sendMessage(player, "messages.undisguise-info", name);
		
		event.getItem().setAmount(event.getItem().getAmount() - 1);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (DisguiseManager.isDisguised(player)) {
			String disguise = DisguiseManager.getDisguise(player);
			DisguiseManager.undisguise(player);
			KamoofSMP.sendMessage(player, "messages.lostdisguise", disguise);
			
			// compabilité déco combat
			DisguiseRestaurer.set(player.getName(), null);
		}
		
		event.getDrops().add(SkullManager.getSkull(player.getName()));
	}
	
}
