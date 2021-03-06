package org.cyberpwn.icing.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.cyberpwn.icing.ability.BasicAbility;
import org.cyberpwn.icing.skill.Skill;
import org.cyberpwn.icing.xp.XP;
import org.phantomapi.clust.Comment;
import org.phantomapi.clust.Keyed;
import org.phantomapi.util.C;
import org.phantomapi.util.F;
import org.phantomapi.world.MaterialBlock;

public class LightArmorAcrobatics extends BasicAbility
{
	@Comment("The Max ")
	@Keyed("initial-range")
	public double maxMitigation = 0.86;
	
	public LightArmorAcrobatics(Skill parent)
	{
		super(parent, "acrobatics");
		
		maxLevel = 14;
		level = 6;
		levelStep = 1;
		upgradeCost = 1;
		unlockCost = 1;
	}
	
	@EventHandler
	public void on(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			if(e.getCause().equals(DamageCause.FALL))
			{
				Player p = (Player) e.getEntity();
				
				if(!XP.isReady(p))
				{
					return;
				}
				
				if(isUnlocked(p) && isEnabled(p))
				{
					double splitDamage = e.getDamage() / 4;
					
					int v = 0;
					
					for(ItemStack i : p.getInventory().getArmorContents())
					{
						if(i.getType().toString().contains("CHAINMAIL") || i.getType().toString().contains("LEATHER"))
						{
							v++;
						}
					}
					
					double fd = splitDamage * v;
					
					e.setDamage(e.getDamage() - (fd * getMitigation((int) getLevel(p))));
				}
			}
			
			if(e.getCause().equals(DamageCause.FIRE))
			{
				Player p = (Player) e.getEntity();
				
				if(isUnlocked(p) && isEnabled(p))
				{
					double splitDamage = e.getDamage() / 4;
					
					int v = 0;
					
					for(ItemStack i : p.getInventory().getArmorContents())
					{
						if(i.getType().toString().contains("CHAINMAIL") || i.getType().toString().contains("LEATHER"))
						{
							v++;
						}
					}
					
					double fd = splitDamage * v;
					
					e.setDamage(e.getDamage() - (fd * (getMitigation((int) getLevel(p)) / 2)));
				}
			}
		}
	}
	
	public double getMitigation(int level)
	{
		return ((double) level / (double) getMaxLevel()) * maxMitigation;
	}
	
	@Override
	public MaterialBlock getMaterialBlock()
	{
		return new MaterialBlock(Material.FEATHER);
	}
	
	@Override
	public String getDescription()
	{
		return "Reduces Fall Damage & Other forms of damage.";
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
			return C.LIGHT_PURPLE + F.pc(getMitigation(1)) + " " + getAbilityGraph(20, (double) getLevel(p) / (double) getMaxLevel(), "") + C.LIGHT_PURPLE + " " + F.pc(getMitigation(getMaxLevel())) + " Reduction";
		}
		
		return C.LIGHT_PURPLE + F.pc(getMitigation(1)) + " " + getAbilityGraph(20, (double) getLevel(p) / (double) getMaxLevel(), F.pc(getMitigation((int) getLevel(p)))) + C.LIGHT_PURPLE + " " + F.pc(getMitigation(getMaxLevel())) + " Reduction";
	}
	
	@Override
	public String getGraphInitial()
	{
		return F.pc(getMitigation(1));
	}
	
	@Override
	public String getGraphMax()
	{
		return F.pc(getMitigation(getMaxLevel()));
	}
	
	@Override
	public String getGraphCurrent(int level)
	{
		return F.pc(getMitigation(level));
	}
}
