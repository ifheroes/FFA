package de.IDev.ifh.event;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import de.IDev.ifh.FFA;
import de.IDev.ifh.customs.Attributes;
import de.IDev.ifh.customs.CustomItem;
import de.IDev.ifh.utils.CombatLog;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Damage implements Listener {

	static HashMap<String, String> hitCooldown = new HashMap<>();

	@EventHandler
	public void a(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player))
			return;
		Player one = (Player) e.getDamager();
		Entity two = e.getEntity();
		
		/*
		 * CombatLog and hitCoolDown
		 */
		CombatLog.addPlayer(one, 20*5);
		
		
		if (two instanceof Player) {
			CombatLog.addPlayer((Player) two, 20 * 3);
			
			if(hitCooldown.containsKey(one.getName()) && hitCooldown.get(one.getName()).equals(two.getName())) {
				e.setCancelled(true);
				return;
			}
			hitCooldown.put(one.getName(), ((Player) two).getName());
		}
		
		
		/*
		 * Weapons
		 */
		ItemStack i = one.getInventory().getItemInMainHand();
		CustomItem weapon = new CustomItem(i);
		/*
		 * Damage
		 */
		double damage = weapon.getAttributeValue(Attributes.DAMAGE);
		e.setDamage(e.getDamage() * (1 + (damage / 100)));
		
		/*
		 * AttackSpeed
		 */
		double attackspeed = weapon.getAttributeValue(Attributes.ATTACK_SPEED);
		long ticksForNextAttack = (long) (10 - (10*(attackspeed/100)));
		Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {
			@Override
			public void run() {
				hitCooldown.remove(one.getName());
			}
		}, ticksForNextAttack);
		
		/*
		 * CritChance and CritDamage
		 */
		double critchance = weapon.getAttributeValue(Attributes.CRIT_CHANCE);
		Random r = new Random();
		double randomValue = 0 + (100 - 0) * r.nextDouble();
		if(randomValue <= critchance) {
			double critdamage = weapon.getAttributeValue(Attributes.DAMAGE)/100;
			e.setDamage(e.getDamage() * (1.5+critdamage));
			one.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§oCRIT"));
			Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {
				@Override
				public void run() {
					one.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
				}
			}, 10L);
		}
		
		/*
		 * ARMOR
		 */
		
		if(!(two instanceof Player)) {
			return;
		}
		
		Player target = (Player) two;
		HashMap<Attributes, Double> armorStats = new HashMap<>();
		for(ItemStack s : target.getInventory().getArmorContents()) {
			if(s == null) continue;
			CustomItem piece = new CustomItem(s);
			for(Entry<Attributes, Double> entry : piece.getAttributes().entrySet()) {
				double value = armorStats.get(entry.getKey()) == null ? 0 : armorStats.get(entry.getKey());
				value += entry.getValue();
				armorStats.put(entry.getKey(), value);
			}
		}
		/*
		 * Protection
		 */
		double protection = armorStats.get(Attributes.PROTECTION) == null ? 0 : armorStats.get(Attributes.PROTECTION);
		System.out.println(protection + " "+ e.getDamage());
		e.setDamage(e.getDamage() - (e.getDamage() * (protection / 100)));
		System.out.println(e.getDamage());
	}

	@EventHandler
	public void a(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL) {
			e.setCancelled(true);
		}
	}
}
