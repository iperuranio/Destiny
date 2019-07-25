package Destiny;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class Destiny implements CommandExecutor 
{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(sender.hasPermission("*") || sender.isOp())
		{
			sender.sendMessage("Config reloddato.");
			Main.loadConfig();
		}
		else
		{
			sender.sendMessage(ChatColor.DARK_RED + "Permessi insufficienti.");
		}
		return true;
	}

}
