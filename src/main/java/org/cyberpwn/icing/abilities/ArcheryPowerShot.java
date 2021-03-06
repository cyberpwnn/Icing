package org.cyberpwn.icing.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.cyberpwn.icing.ability.BasicAbility;
import org.cyberpwn.icing.skill.Skill;
import org.cyberpwn.icing.xp.XP;
import org.phantomapi.sync.TaskLater;
import org.phantomapi.util.C;
import org.phantomapi.util.F;
import org.phantomapi.world.MaterialBlock;

public class ArcheryPowerShot extends BasicAbility
{
	public ArcheryPowerShot(Skill parent)
	{
		super(parent, "power shot");
		
		maxLevel = 12;
		level = 5;
		levelStep = 1;
		upgradeCost = 3;
		unlockCost = 4;
	}
	
	@EventHandler
	public void on(ProjectileLaunchEvent e)
	{
		if(e.getEntity() instanceof Arrow)
		{
			Arrow a = (Arrow) e.getEntity();
			
			if(a.getShooter() instanceof Player)
			{
				Player p = (Player) a.getShooter();
				
				if(!XP.isReady(p))
				{
					return;
				}
				
				if(isUnlocked(p) && a.isCritical() && isEnabled(p))
				{
					int level = (int) getLevel(p);
					double boost = getBoost(level);
					
					new TaskLater()
					{
						@Override
						public void run()
						{
							a.setVelocity(p.getLocation().getDirection().clone().multiply(((boost + 1) * 2) + 1.3));
						}
					};
				}
			}
		}
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
		return "Shoots faster arrows";
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
		return F.pc(getBoost(1));
	}
	
	@Override
	public String getGraphMax()
	{
		return "100%";
	}
	
	@Override
	public String getGraphCurrent(int level)
	{
		return F.pc(getBoost(level));
	}
}
