package de.IDev.ifh.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.IDev.ifh.FFA;

public class SetSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if(!(s instanceof Player)) return true;
		if(!s.hasPermission("ffa.setspawn")) {
			s.sendMessage("§cDafür hast du keine Berechtigung");
			return true;
		}
		
		Player p = (Player) s;
		Location loc = p.getLocation();
		String world = loc.getWorld().getName();
		double x = loc.getBlockX()+0.5;
		double y = loc.getY();
		double z = loc.getBlockZ()+0.5;
		float yaw = loc.getYaw();
		loc.setPitch(0);
		FFA.worldData.set("spawn.world", world);
		FFA.worldData.set("spawn.x", x);
		FFA.worldData.set("spawn.y", y);
		FFA.worldData.set("spawn.z", z);
		FFA.worldData.set("spawn.yaw", yaw);
		FFA.worldData.set("spawn.pitch", 0);
		
		s.sendMessage("§eSpawn set");
		
		return true;
	}

}
