package de.IDev.ifh.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.IDev.ifh.event.Chat;
import net.md_5.bungee.api.chat.TextComponent;

public class DeleteMessage implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
		if (!s.hasPermission("FFA.deletemessage")) {
			s.sendMessage("§cDafür hast du keine Berechtigung");
			return true;
		}
		int number = Integer.parseInt(args[0]);
		Chat.messages.remove(number);
		for (Player p : Bukkit.getOnlinePlayers()) {
			for (int i = 0; i <= 100; i++) {
				p.sendMessage(" ");
			}
			for (int i : Chat.messages.keySet()) {

				TextComponent message = (TextComponent) Chat.messages.get(i);

				if (!p.hasPermission("FFA.deletemessage")) {
					TextComponent base = message.duplicate();
					base.setClickEvent(null);
					p.spigot().sendMessage(base);
					continue;
				}
				p.spigot().sendMessage(message);
			}
		}
		return true;
	}
	
}
