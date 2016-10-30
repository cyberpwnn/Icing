package org.cyberpwn.icing;

import org.cyberpwn.icing.cakes.TeleportEnder;
import org.cyberpwn.icing.cosmetic.Cake;
import org.cyberpwn.icing.cosmetic.CakeType;
import org.phantomapi.construct.Controllable;
import org.phantomapi.construct.Controller;
import org.phantomapi.lang.GList;
import org.phantomapi.lang.GMap;

public class CakeController extends Controller
{
	private GMap<CakeType, GList<Cake>> cakes;
	
	public CakeController(Controllable parentController)
	{
		super(parentController);
		
		cakes = new GMap<CakeType, GList<Cake>>();
		
		register(new TeleportEnder(this));
	}
	
	@Override
	public void onStart()
	{
		for(Controllable i : getControllers())
		{
			if(i instanceof Cake)
			{
				Cake cake = (Cake) i;
				CakeType cakeType = cake.getType();
				
				if(!cakes.containsKey(cakeType))
				{
					cakes.put(cakeType, new GList<Cake>());
				}
				
				cakes.get(cakeType).add(cake);
			}
		}
	}
	
	@Override
	public void onStop()
	{
		
	}
}
