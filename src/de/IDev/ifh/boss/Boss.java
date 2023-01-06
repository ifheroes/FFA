package de.IDev.ifh.boss;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class Boss implements IBoss{

	private LivingEntity entity;
	private BossType bosstype;
	private double health;
	private double maxHealth;
	private double armor;
	
	public Boss(BossType type) {
		this.bosstype = type;
	}
	
	@Override
	public void spawnBoss(Location loc) {
		entity = (LivingEntity) loc.getWorld().spawnEntity(loc, BossType.getEntityType(bosstype));
		entity.setAI(false);
		entity.setRemoveWhenFarAway(false);
		entity.setSilent(false);
		entity.addScoreboardTag(BOSS);
		entity.setMaximumNoDamageTicks(0);
	}

	@Override
	public void moveTo(Location loc) {
		// TODO Pathfinder...	
	}

	@Override
	public void attack(Entity en, double damage) {
		en.setLastDamageCause(new EntityDamageEvent(en, DamageCause.CUSTOM, damage));
	}

	@Override
	public void setType(BossType type) {
		this.bosstype = type;
	}

	@Override
	public void setHealth(double health) {
		this.health = health;
	}

	@Override
	public void setArmor(double armor) {
		this.armor = armor;
	}

	@Override
	public BossType getType() {
		return bosstype;
	}

	@Override
	public void setMaxHealth(double health) {
		this.maxHealth = health;	
	}

	@Override
	public double getMaxHealth() {
		return this.maxHealth;
	}

	@Override
	public double getArmor() {
		return this.armor;
	}

	@Override
	public double getHealth() {
		return this.health;
	}

	@Override
	public boolean isAlive() {
		if(entity == null) return false;
		return !entity.isDead();
	}
}
