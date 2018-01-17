package org.ships.event.commands.ShipsCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.ships.event.commands.CommandLauncher;

import MoseShipsBukkit.StillShip.Vessel.Vessel;

public class VesselCommand extends CommandLauncher {

	public VesselCommand() {
		super("Vessel", "", "Lists all; vessels you own", null, true, false);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		player.sendMessage(ChatColor.AQUA + "---[Your Vessels]---");
		player.sendMessage(ChatColor.AQUA + "[Vessel name] | [Vessel type]");
		for (Vessel vessel : Vessel.getVessels(player)) {
			player.sendMessage(ChatColor.AQUA + vessel.getName() + " | " + vessel.getVesselType().getName());
		}

	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		// TODO Auto-generated method stub

	}

}