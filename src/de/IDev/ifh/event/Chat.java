package de.IDev.ifh.event;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.IDev.ifh.FFA;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class Chat implements Listener {

	public static Map<Integer, BaseComponent> messages = new HashMap<>();

	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		int newest = getLatestNumber() + 1;

		Player player = (Player) e.getPlayer();
		User user = FFA.luckPermsApi.getUserManager().getUser(player.getUniqueId());
		String prefix = user.getCachedData().getMetaData().getPrefix();
		prefix = prefix != null ? prefix : "";
		prefix = prefix.replaceAll("&", "§");

		String message = e.getMessage();
		if (player.hasPermission("ffa.chatcolors")) {
			message = message.replaceAll("&", "§");
		}

		TextComponent textMessage = new TextComponent(prefix + player.getDisplayName() + "§7: " + message);

		TextComponent advancedMessage = textMessage.duplicate();
		advancedMessage.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/deletemessage " + newest));

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission("FFA.deletemessage")) {
				p.spigot().sendMessage(advancedMessage);
				continue;
			}
			p.spigot().sendMessage(textMessage);
		}

		messages.put(newest, advancedMessage);

	}

	public int getLatestNumber() {
		if (messages.keySet() == null)
			return 0;
		return messages.keySet().size();
	}
}
