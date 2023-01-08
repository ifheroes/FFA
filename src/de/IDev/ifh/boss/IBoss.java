package de.IDev.ifh.boss;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface IBoss {

	public static final String BOSS = "BOSS";
	public List<Attacks> attacks = new ArrayList<>();

	public void spawnBoss(Location loc);
	public void moveTo(Location loc, double speed);
	public void attack(Entity en, double damage);
	
	public void setType(BossType type);
	public void setHealth(double health);
	public void	setArmor(double armor);
	public void setMaxHealth(double health);
	public double getMaxHealth();
	public double getArmor();
	public double getHealth();
	public boolean isAlive();
	
	public BossType getType();
	default List<Attacks> getAttacks(){
		return attacks;
	}
	default void addAttacks(List<Attacks> attacks) {
		attacks.addAll(attacks);
	}
	default void removeAttack(Attacks attack) {
		attacks.remove(attack);
	}
}
