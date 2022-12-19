package de.I_Dev.FFA;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_13_R2.PacketPlayInClientCommand;
import net.minecraft.server.v1_13_R2.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		super.onEnable();
		Bukkit.getPluginManager().registerEvents(this, this);
		for (World w : Bukkit.getWorlds()) {
			w.setGameRuleValue("keepInventory", "true");
		}
		getCommand("setspawn").setExecutor(this);
		getCommand("stats").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
		if (c.getName().equalsIgnoreCase("setspawn")) {
			if (s.hasPermission("ifheroes.setspawn")) {
				File spawns = new File("spawns.yml", null);
				spawns.set("spawn",
						((Player) s).getLocation().getWorld().getName() + "," + ((Player) s).getLocation().getX() + ","
								+ ((Player) s).getLocation().getY() + "," + ((Player) s).getLocation().getZ() + ","
								+ ((Player) s).getLocation().getYaw() + ",0");
				sendactionabr("§8» §7Du hast den Spawn gesetzt", (Player) s);
			} else {
				sendactionabr("§8» §cDazu hast du keine Rechte", (Player) s);
			}
		}
		if (c.getName().equalsIgnoreCase("stats")) {
			File stats = new File("stats.yml", null);
			s.sendMessage("§8»--------------------«");
			int kills = stats.getobject(((Player) s).getUniqueId().toString() + ".kills") == null ? 0
					: (int) stats.getobject(((Player) s).getUniqueId().toString() + ".kills");
			int deaths = stats.getobject(((Player) s).getUniqueId().toString() + ".deaths") == null ? 0
					: (int) stats.getobject(((Player) s).getUniqueId().toString() + ".deaths");
			s.sendMessage("§8» §eKills§7: " + kills);
			s.sendMessage("§8» §eDeath§7: " + deaths);
			
			double kd = 0;
			kd = (double) ( kills == 0?1:kills) / (double) (deaths == 0 ? 1 : deaths);
			kd *= 100.0D;
	        kd = Math.round(kd);
	        kd /= 100.0D;
			s.sendMessage("§8» §eKD§7: " + kd);
			
			s.sendMessage("§8»--------------------«");
		}
		return true;
	}

	public void sendactionabr(String message, Player p) {
		IChatBaseComponent chat = ChatSerializer.a("{\"text\":\"" + message + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(chat, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	@EventHandler
	public void a(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void a(PlayerDeathEvent e) {
		
		new Particle(EnumParticle.SMOKE_NORMAL, e.getEntity().getLocation().add(0.5, 1, 0.5), true, (float) 0.01, (float) 1, (float) 0.01, (float) 1, 1000).sendAll();
		
		File stats = new File("stats.yml", null);
		if (stats.getobject(e.getEntity().getUniqueId().toString() + ".deaths") != null) {
			stats.set(e.getEntity().getUniqueId().toString() + ".deaths",
					(int) stats.getobject(e.getEntity().getUniqueId().toString() + ".deaths") + 1);
		} else {
			stats.set(e.getEntity().getUniqueId().toString() + ".deaths", 1);
		}
		if (stats.getobject(e.getEntity().getKiller().getUniqueId().toString() + ".kills") != null) {
			stats.set(e.getEntity().getKiller().getUniqueId().toString() + ".kills",
					(int) stats.getobject(e.getEntity().getKiller().getUniqueId().toString() + ".kills") + 1);
		} else {
			stats.set(e.getEntity().getKiller().getUniqueId().toString() + ".kills", 1);
		}

		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {

			@Override
			public void run() {
				((CraftPlayer) e.getEntity()).getHandle().playerConnection
						.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
			}
		}, 2L);
		
		
		
		sendactionabr("§8» §e"+e.getEntity().getKiller().getName()+" §7hatte noch §8[§7"+Math.round(e.getEntity().getKiller().getHealth())+"/"+e.getEntity().getKiller().getMaxHealth()+"§8] §7Herzen.", e.getEntity());
	}

	@EventHandler
	public void a(PlayerJoinEvent e) {
		File spawns = new File("spawns.yml", null);
		if (spawns.getobject("spawn") != null) {
			String[] location = ((String) spawns.getobject("spawn")).split(",");
			Location loc = new Location(Bukkit.getWorld(location[0]), Double.parseDouble(location[1]),
					Double.parseDouble(location[2]), Double.parseDouble(location[3]), Float.parseFloat(location[4]),
					Float.parseFloat(location[5]));
			e.getPlayer().teleport(loc);
		}
		setinv(e.getPlayer());
		e.setJoinMessage("");
		for (Player p : Bukkit.getOnlinePlayers()) {
			sendactionabr("§8» §7Der Spieler §a" + e.getPlayer().getName() + " §7hat FFA betreten", p);
		}
	}

	@EventHandler
	public void a(PlayerQuitEvent e) {
		e.setQuitMessage("");
		for (Player p : Bukkit.getOnlinePlayers()) {
			sendactionabr("§8» §7Der Spieler §a" + e.getPlayer().getName() + " §7hat FFA verlassen", p);
		}
	}

	@EventHandler
	public void a(PlayerInteractEvent e) {
		if (e.getAction() == Action.PHYSICAL) {
			e.setCancelled(true);
		}
		if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() == Material.GOLDEN_APPLE) {
			e.setCancelled(true);
			final int slot = e.getPlayer().getInventory().getHeldItemSlot();
			e.getPlayer().getInventory().setItem(slot, null);
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 15, 4));
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 20, 2));
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 3));
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BURP, 1, 1);
			
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {

				@Override
				public void run() {
					e.getPlayer().getInventory().setItem(slot,
							new Item(Material.GOLDEN_APPLE, "§8» §7Goldener Apfel").toItem());

				}
			}, 20 * 45);
		}
	}

	@EventHandler
	public void a(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void a(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL) {
			e.setDamage(e.getDamage() / 2);
		}
	}

	@EventHandler
	public void a(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void a(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}
	
	public void setinv(Player p) {
		p.getInventory().setHelmet(new Item(Material.DIAMOND_HELMET, "§8» §7Diamant Helm")
				.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).toItem());
		p.getInventory().setChestplate(new Item(Material.DIAMOND_CHESTPLATE, "§8» §7Diamant Brustplatte")
				.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).toItem());
		p.getInventory().setLeggings(new Item(Material.DIAMOND_LEGGINGS, "§8» §7Diamant Hose")
				.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).toItem());
		p.getInventory().setBoots(new Item(Material.DIAMOND_BOOTS, "§8» §7Diamant Schuhe")
				.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).toItem());
		p.getInventory().setItem(0, new Item(Material.DIAMOND_SWORD, "§8» §7Diamant Schwert")
				.addEnchantment(Enchantment.DAMAGE_ALL, 5).setUnbreakable(true).toItem());
		p.getInventory().setItem(1, new Item(Material.BOW, "§8» §7Bogen").addEnchantment(Enchantment.ARROW_DAMAGE, 5)
				.addEnchantment(Enchantment.ARROW_INFINITE, 1).setUnbreakable(true).toItem());
		p.getInventory().setItem(2, new Item(Material.GOLDEN_APPLE, "§8» §7Goldener Apfel").toItem());
		p.getInventory().setItem(3, new Item(Material.ARROW, "§8» §7Pfeil").toItem());
	}
}
