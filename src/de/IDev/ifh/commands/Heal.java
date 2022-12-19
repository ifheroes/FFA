package de.IDev.ifh.commands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if(!(s instanceof Player)) return true;
		if(!s.hasPermission("ffa.heal")) {
			s.sendMessage("§cDafür hast du keine Berechtigung");
			return true;
		}
		
		if(args.length >= 1) {
			Player p = Bukkit.getPlayer(args[0]);
			if(p == null) {
				s.sendMessage("§cDer Spieler "+args[0]+" wurde nicht gefunden");
				return true;
			}
			
			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			p.setFoodLevel(40);
			
			s.sendMessage("§aDu hast "+ p.getName()+" geheilt");
			p.sendMessage("§a"+((Player) s).getName() + " hat dich geheilt");
			return true;
		}
		
		Player p = (Player) s;
		p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		p.setFoodLevel(40);
	
		p.sendMessage("§aDu hast dich geheilt");
		return true;
	}

}
