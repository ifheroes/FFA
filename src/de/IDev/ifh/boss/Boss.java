package de.IDev.ifh.boss;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import net.minecraft.world.entity.EntityInsentient;

public class Boss implements IBoss{

	private LivingEntity entity;
	private BossType bosstype;
	private double health;
	private double maxHealth;
	private double armor;
	private double visRange;
	
	public Boss(BossType type) {
		this.bosstype = type;
	}
	
	public Boss(Entity en) {
		bosstype = BossType.getBossType(en.getType());
		entity = (LivingEntity) en;
	
		//Init all stats
		
	}
	
	@Override
	public void spawnBoss(Location loc) {
		entity = (LivingEntity) loc.getWorld().spawnEntity(loc, BossType.getEntityType(bosstype));
		entity.setAI(true);
		entity.setRemoveWhenFarAway(false);
		entity.setSilent(true);
		entity.addScoreboardTag(BOSS);
		entity.setMaximumNoDamageTicks(0);
	}

	@Override
	public void moveTo(Location loc, double speed) {
		((EntityInsentient) ((CraftEntity) entity).getHandle()).E().a(loc.getX(), loc.getY(),
				loc.getZ(), speed);
	}

	@Override
	public void attack(Entity en, double damage) {
		if(en instanceof LivingEntity) {
			((LivingEntity) en).damage(damage, entity);
			entity.swingMainHand();
		}
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

	public double getVisRange() {
		return visRange;
	}

	public void setVisRange(double visRange) {
		this.visRange = visRange;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
}
