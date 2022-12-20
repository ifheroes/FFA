package de.IDev.ifh.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.IDev.ifh.FFA;

public class Explosion implements Listener{

	@EventHandler
	public void a(EntityExplodeEvent e) {
		
		List<Block> blocks = e.blockList();
		List<BlockData> data = new ArrayList<>();
		
		
		for(Block bl : blocks) {
			data.add(bl.getBlockData());
			bl.getDrops().clear();
		}
		
		new BukkitRunnable() {
			@Override
			public void run() {
				Block b = blocks.get(0);
				b.getWorld().getBlockAt(b.getLocation()).setBlockData(data.get(0));
				blocks.remove(0);
				data.remove(0);
				if(blocks.isEmpty()) this.cancel();
			}
		}.runTaskTimer(JavaPlugin.getPlugin(FFA.class), 0L, 2L);
		
	}	
}
