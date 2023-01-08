package de.IDev.ifh.boss.bosses;

import org.bukkit.Location;

import de.IDev.ifh.boss.Boss;
import de.IDev.ifh.boss.BossType;

public class BlazeBoss extends Boss{

	public BlazeBoss(BossType type) {
		super(type);
	}

	@Override
	public void moveTo(Location loc, double speed) {
		getEntity().setVelocity(loc.subtract(getEntity().getLocation()).toVector().normalize().multiply(speed));
	}
}
