package de.IDev.ifh.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import de.IDev.ifh.FFA;
import de.IDev.ifh.customs.CustomItem;
import de.IDev.ifh.utils.StatsData;

public class Join implements Listener {

	@EventHandler
	public void a(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.teleport(getSpawnLoc() != null ? getSpawnLoc() : p.getLocation());
		p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);
		e.setJoinMessage("§7› "+p.getName()+" hat das Spiel betreten");
		
		p.setMaximumNoDamageTicks(0);
		
		Object kills = FFA.playerData.getobject(p.getUniqueId() + ".kills");
		Object deaths = FFA.playerData.getobject(p.getUniqueId() + ".deaths");
		new StatsData(p, (int) (deaths != null ? deaths : 0), (int) (kills != null ? kills : 0));
		
//		if(p.hasPlayedBefore()) return;
		p.getInventory().clear();
		CustomItem sword = new CustomItem(Material.STONE_SWORD, true, 25);
		sword.setName("§6Schwert");
		CustomItem helmet = new CustomItem(Material.IRON_HELMET, true, 25);
		helmet.setName("§6Helm");
		CustomItem chestplate = new CustomItem(Material.IRON_CHESTPLATE, true, 25);
		chestplate.setName("§6Brustpanzer");
		CustomItem leggings = new CustomItem(Material.IRON_LEGGINGS, true, 25);
		leggings.setName("§6Hose");
		CustomItem boots = new CustomItem(Material.IRON_BOOTS, true, 25);
		boots.setName("§6Schuhe");
		//Upgradle later
		CustomItem pickaxe = new CustomItem(Material.STONE_PICKAXE, false, 0);
		pickaxe.setName("§6Spitzhacke");
		CustomItem apple = new CustomItem(Material.GOLDEN_APPLE, false, 0);
		apple.setName("§6Heilung");
		
		p.getInventory().addItem(sword.getItem());
		p.getInventory().addItem(pickaxe.getItem());
		p.getInventory().addItem(apple.getItem());
		p.getInventory().setArmorContents(new ItemStack[] {boots.getItem(), leggings.getItem(), chestplate.getItem(), helmet.getItem()});
	}
	
	
	public Location getSpawnLoc() {
		String world = (String) FFA.worldData.getobject("spawn.world");
		double x = (double) FFA.worldData.getobject("spawn.x");
		double y = (double) FFA.worldData.getobject("spawn.y");
		double z = (double) FFA.worldData.getobject("spawn.z");
		double yaw = Double.parseDouble(FFA.worldData.getobject("spawn.yaw")+"");
		int pitch = (int) FFA.worldData.getobject("spawn.pitch");
		return new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, pitch);
	}
}
