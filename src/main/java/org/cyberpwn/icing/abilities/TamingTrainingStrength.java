package org.cyberpwn.icing.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTameEvent;
import org.cyberpwn.icing.ability.BasicAbility;
import org.cyberpwn.icing.skill.Skill;
import org.cyberpwn.icing.xp.XP;
import org.phantomapi.util.C;
import org.phantomapi.util.F;
import org.phantomapi.world.MaterialBlock;

public class TamingTrainingStrength extends BasicAbility
{
	public TamingTrainingStrength(Skill parent)
	{
		super(parent, "training strength");
		
		maxLevel = 7;
		level = 2;
		levelStep = 1;
		upgradeCost = 1;
		unlockCost = 2;
	}
	
	@EventHandler
	public void on(EntityTameEvent e)
	{
		if(e.getOwner() instanceof Player)
		{
			Player p = (Player) e.getOwner();
			
			if(!XP.isReady(p))
			{
				return;
			}
			
			if(isUnlocked(p) && isUnlocked(p))
			{
				e.getEntity().setMaxHealth(e.getEntity().getMaxHealth() + (getLevel(p) * 2));
			}
		}
	}
	
	public String getV(int level)
	{
		return "+" + level + " \u2665";
	}
	
	public double getBoost(int level)
	{
		return (double) level / (double) maxLevel;
	}
	
	@Override
	public MaterialBlock getMaterialBlock()
	{
		return new MaterialBlock(Material.ARROW);
	}
	
	@Override
	public String getDescription()
	{
		return "Tamed animals have increased health.";
	}
	
	@Override
	public void onStart()
	{
		
	}
	
	@Override
	public void onStop()
	{
		
	}
	
	@Override
	public String getStatGraph(Player p)
	{
		if(getLevel() == getMaxLevel())
		{
			return C.LIGHT_PURPLE + F.pc(getBoost(1)) + getAbilityGraph(20, (double) getLevel(p) / (double) getMaxLevel(), "") + C.LIGHT_PURPLE + " " + F.pc(getBoost(getMaxLevel()));
		}
		
		return C.LIGHT_PURPLE + F.pc(getBoost(1)) + getAbilityGraph(20, (double) getLevel(p) / (double) getMaxLevel(), F.pc(getBoost((int) getLevel(p)))) + C.LIGHT_PURPLE + " " + F.pc(getBoost(getMaxLevel()));
	}
	
	@Override
	public String getGraphInitial()
	{
		return getV(1);
	}
	
	@Override
	public String getGraphMax()
	{
		return getV(getMaxLevel());
	}
	
	@Override
	public String getGraphCurrent(int level)
	{
		return getV(level);
	}
}
