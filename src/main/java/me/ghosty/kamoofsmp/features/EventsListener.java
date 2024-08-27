package me.ghosty.kamoofsmp.features;

import me.ghosty.kamoofsmp.KamoofSMP;
import me.ghosty.kamoofsmp.managers.DisguiseManager;
import me.ghosty.kamoofsmp.managers.SkullManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;

public final class EventsListener implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR)
			return;
		
		Player player = event.getPlayer();
		OfflinePlayer target = SkullManager.getHolder(event.getItem());
		if (target == null)
			return;
		
		DisguiseManager.disguise(player, target.getName());
		KamoofSMP.sendMessage(player, "messages.disguised", Map.of("player", target.getName()));
		KamoofSMP.sendMessage(player, "messages.undisguise-info");
		// au cas où l'item soit quand même stacké, on n'en retire qu'un
		event.getItem().setAmount(event.getItem().getAmount() - 1);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if(DisguiseManager.isDisguised(player)) {
			String disguise = DisguiseManager.getDisguise(player);
			DisguiseManager.undisguise(player);
			KamoofSMP.sendMessage(player, "messages.lostdisguide", Map.of("player", disguise));
		}
		
		if(!player.hasPermission("kamoofsmp.bypass"))
			event.getDrops().add(SkullManager.getSkull(player.getName()));
	}
	
}
