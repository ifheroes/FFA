package de.IDev.ifh.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.IDev.ifh.commands.Spawn;

public class Move implements Listener {

	@EventHandler
	public void a(PlayerMoveEvent e) {
		Spawn.cancelSpawn(e.getPlayer());
	}
}
