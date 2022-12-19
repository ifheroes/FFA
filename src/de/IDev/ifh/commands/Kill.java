package de.IDev.ifh.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Kill implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if(!(s instanceof Player)) return true;
		if(!s.hasPermission("ffa.kill")) {
			s.sendMessage("§cDafür hast du keine Berechtigung");
			return true;
		}
		
		if(args.length == 0) {
			Player p = (Player) s;
			p.setHealth(0);
			return true;
		}
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("all")) {
				Player p = (Player) s;
				for(Entity e : p.getWorld().getEntities()) {
					if(e instanceof Player) continue;
					if(e.getType() == EntityType.ARMOR_STAND) continue;
					e.remove();
				}
				s.sendMessage("§cAlle Entitys wurden getötet");
				return true;
			}
			Player p = Bukkit.getPlayer(args[0]);
			if(p == null) {
				s.sendMessage("§cDer Spieler "+args[0]+" wurde nicht gefunden");
				return true;
			}
			p.setHealth(0);
			s.sendMessage("§eDu hast "+args[0]+ " getötet");
			return true;
		}
		return true;
	}

}
