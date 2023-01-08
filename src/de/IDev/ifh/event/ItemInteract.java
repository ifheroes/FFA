package de.IDev.ifh.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.IDev.ifh.FFA;
import de.IDev.ifh.customs.CustomItem;

public class ItemInteract implements Listener {

	double addHealth = 5;

	@EventHandler
	public void a(PlayerInteractEvent e) {
		if (!e.getAction().toString().contains("RIGHT"))
			return;
		if (e.getItem() == null)
			return;
		if (e.getItem().getType() != Material.GOLDEN_APPLE)
			return;
		e.setCancelled(true);

		Player p = e.getPlayer();
		int slot = p.getInventory().getHeldItemSlot();
		p.getInventory().setItem(slot, null);

		p.setHealth((e.getPlayer().getHealth() + addHealth) > 20 ? 20 : e.getPlayer().getHealth() + addHealth);
		p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 1));
		p.playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);

		Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {

			@Override
			public void run() {
				CustomItem apple = new CustomItem(Material.GOLDEN_APPLE, false, 0);
				apple.setName("§6Heilung");
				e.getPlayer().getInventory().setItem(slot, apple.getItem());

			}
		}, 20 * 45);
	}
}
