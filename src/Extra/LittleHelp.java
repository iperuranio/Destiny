package Extra;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Destiny.Main;
import Enums.Enums.ExecutionType;
import Messages.Errori;

public class LittleHelp
{
	public static void consoleMessage(String s)
	{
		Bukkit.getConsoleSender().sendMessage(s);
	}
	
	public static void command(String s)
	{
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
	}
	
	public static HashMap<Double, Double> calculateProbability()
	{
		HashMap<Double, Double> final_probs = new HashMap<>();
		List<Double> PROBS = Objects.PROBABILITY;
		List<Double> WET_PROBS = Objects.WET_PROBABILITY;
		double summ = 0;
		double wet_summ = 0;		
		
		for(int i = 0; i < PROBS.size(); i++)
		{
			summ += PROBS.get(i);
		}
		
		for(int i = 0; i < WET_PROBS.size(); i++)
		{
			wet_summ += WET_PROBS.get(i);
		}
		
		for(int i = 0; i < PROBS.size(); i++)
		{
			PROBS.set(i, PROBS.get(i) / summ);
		}
		
		for(int i = 0; i < WET_PROBS.size(); i++)
		{
			WET_PROBS.set(i, WET_PROBS.get(i) / wet_summ);
		}
		
		double random = Math.random();
		
		summ = 0;
		wet_summ = 0;
		
		for(int i = 0; i < PROBS.size(); i++)
		{
			summ += PROBS.get(i);
			if(summ > random)
			{
				summ = i;
				break;
			}
		}
		
		for(int i = 0; i < WET_PROBS.size(); i++)
		{
			wet_summ += WET_PROBS.get(i);
			if(wet_summ > random)
			{
				wet_summ = i;
				break;
			}
		}
		
		final_probs.put(summ, wet_summ);
		
		return final_probs;
	}
	
	public static void sendMessage(String message, String broadcast, Player p)
	{
		if(!message.equalsIgnoreCase("NULL"))
		{
			p.sendMessage(Main.LUCKYPREFIX+message);
		}
		
		if(!broadcast.equalsIgnoreCase("NULL"))
		{
			for(Player pl : Bukkit.getOnlinePlayers())
			{
				pl.sendMessage(Main.LUCKYPREFIX+broadcast);
			}
		}
	}
	
	public static void executeCase(ExecutionType et, Player p, String PREFIX, Location l)
	{
		String message = Main.getDestiny().getConfig().getString(PREFIX+".message").replace('&', '§').replaceAll("%player%", p.getName());
		String broadcast = Main.getDestiny().getConfig().getString(PREFIX+".broadcast").replace('&', '§').replaceAll("%player%", p.getName());
		sendMessage(message, broadcast, p);
		
		if(et.equals(ExecutionType.COMMAND))
		{
			String command = Main.getDestiny().getConfig().getString(PREFIX+".command").toLowerCase().replaceAll("%player%", p.getName());
			LittleHelp.command(command);
		}
		else if(et.equals(ExecutionType.ITEM))
		{
			ItemStack daGivv = Objects.ITEMS.get(Main.getDestiny().getConfig().getString(PREFIX+".item").toLowerCase());
			
			if(p.getInventory().firstEmpty() == -1)
			{
				p.sendMessage(Main.LUCKYPREFIX+"§cInventario pieno, item droppato per terra.");
				p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0F, 1.0F);
				p.getWorld().dropItem(p.getLocation(), daGivv);
			}
			else
			{
				p.getInventory().addItem(daGivv);
			}
		}
		else if(et.equals(ExecutionType.MONEY))
		{
			Vault.addMoney(p, Main.getDestiny().getConfig().getDouble(PREFIX+".amount"));
		}
		else
		{
			LittleHelp.consoleMessage(Errori.getParseWrongType(l));
		}
	}
}
