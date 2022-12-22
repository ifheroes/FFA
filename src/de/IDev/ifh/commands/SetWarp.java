package de.IDev.ifh.commands;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.IDev.ifh.FFA;

public class SetWarp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if(!(s instanceof Player)) return true;
		if(!s.hasPermission("ffa.kill")) {
			s.sendMessage("§cDafür hast du keine Berechtigung");
			return true;
		}
		if(args.length < 1) {
			s.sendMessage("§e/setwarp [name] (Hold Item in Hand)");
			return true;
		}
		Player p = (Player) s;
		Location loc = p.getLocation();
		String world = loc.getWorld().getName();
		String name = args[0];
		double x = loc.getBlockX()+0.5;
		double y = loc.getY();
		double z = loc.getBlockZ()+0.5;
		float yaw = loc.getYaw();
		loc.setPitch(0);

		HashMap<String, Location> warps = FFA.worldData.getobject("warps") != null ? (HashMap<String, Location>) FFA.worldData.getobject("warps") : new HashMap<>();
		warps.put(name, loc);
		
		FFA.worldData.set("warps", warps);
		
		
		s.sendMessage("§eWarp "+name+ " set");
		
		return true;
	}

}
