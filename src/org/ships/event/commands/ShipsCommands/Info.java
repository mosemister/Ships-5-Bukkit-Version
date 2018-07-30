package org.ships.event.commands.ShipsCommands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ships.configuration.Config;
import org.ships.event.commands.CommandLauncher;
import org.ships.event.commands.gui.ShipsGUICommand;
import org.ships.plugin.Ships;
import org.ships.ship.type.VesselType;
import org.ships.ship.type.hooks.Fuel;
import org.ships.ship.type.hooks.RequiredMaterial;

public class Info extends CommandLauncher {
	public Info() {
		super("Info", "", "Get info about the ships plugin", null, true, true, InfoGUI.class);
		new InfoGUI(this);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		player.sendMessage(Info.getFormat("Version", Ships.getPlugin().getDescription().getVersion()));
		player.sendMessage(Info.getFormat("Ships track limit", config.getInt("Structure.StructureLimits.trackLimit")));
		player.sendMessage(Info.getFormat("Submarine air check limit", config.getInt("Structure.StructureLimits.airCheckGap")));
		player.sendMessage(ChatColor.GOLD + "---[Protected Vessels]---");
		player.sendMessage(Info.getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.BlockBreak")));
		player.sendMessage(Info.getFormat("Inventory Protect", "" + config.getBoolean("World.ProtectedVessels.InventoryOpen")));
		player.sendMessage(Info.getFormat("Fire Protect", "" + config.getBoolean("World.ProtectedVessels.FireProtect2")));
		player.sendMessage(Info.getFormat("Creeper Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.Creeper")));
		player.sendMessage(Info.getFormat("TNT Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.TNT")));
		player.sendMessage(Info.getFormat("EnderDragon Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderDragon")));
		player.sendMessage(Info.getFormat("Wither Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.Wither")));
		player.sendMessage(Info.getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderMan")));
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		sender.sendMessage(Info.getFormat("Version", Ships.getPlugin().getDescription().getVersion()));
		sender.sendMessage(Info.getFormat("Ships track limit", config.getInt("Structure.StructureLimits.trackLimit")));
		sender.sendMessage(Info.getFormat("Submarine air check limit", config.getInt("Structure.StructureLimits.airCheckGap")));
		sender.sendMessage(ChatColor.GOLD + "---[Protected Vessels]---");
		sender.sendMessage(Info.getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.BlockBreak")));
		sender.sendMessage(Info.getFormat("Inventory Protect", "" + config.getBoolean("World.ProtectedVessels.InventoryOpen")));
		sender.sendMessage(Info.getFormat("Fire Protect", "" + config.getBoolean("World.ProtectedVessels.FireProtect2")));
		sender.sendMessage(Info.getFormat("Creeper Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.Creeper")));
		sender.sendMessage(Info.getFormat("TNT Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.TNT")));
		sender.sendMessage(Info.getFormat("EnderDragon Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderDragon")));
		sender.sendMessage(Info.getFormat("Wither Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.Wither")));
		sender.sendMessage(Info.getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderMan")));
	}

	public static String getFormat(String message, Object result) {
		String ret = ChatColor.GOLD + "[" + message + "] " + ChatColor.AQUA + result;
		return ret;
	}

	public static class InfoGUI extends ShipsGUICommand {
		public InfoGUI(CommandLauncher command) {
			super(command);
		}

		@Override
		public void onScreenClick(HumanEntity player, ItemStack item, Inventory inv, int slot, ClickType type) {
			if (item != null) {
				if (item.equals(ShipsGUICommand.FORWARD_BUTTON)) {
					this.onInterfaceBoot(player, this.getPage(inv) + 1);
				} else if (item.equals(ShipsGUICommand.BACK_BUTTON)) {
					this.onInterfaceBoot(player, this.getPage(inv) - 1);
				}
			}
		}

		List<ItemStack> getVesselTypeInfo(VesselType type) {
			ArrayList<String> fuels;
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			ItemStack name = new ItemStack(Material.CAULDRON, 1);
			ItemMeta nameMeta = name.getItemMeta();
			nameMeta.setDisplayName(type.getName());
			name.setItemMeta(nameMeta);
			items.add(name);
			ItemStack speed = new ItemStack(Material.CAULDRON, 1);
			ItemMeta speedMeta = speed.getItemMeta();
			speedMeta.setDisplayName("Speeds");
			speedMeta.setLore(Arrays.asList("Engine: " + type.getDefaultSpeed(), "Boost:" + type.getDefaultBoostSpeed()));
			speed.setItemMeta(speedMeta);
			items.add(speed);
			ItemStack blockLimits = new ItemStack(Material.CAULDRON, 1);
			ItemMeta blockLimitsMeta = blockLimits.getItemMeta();
			blockLimitsMeta.setDisplayName("Block Limits");
			blockLimitsMeta.setLore(Arrays.asList("Min: " + type.getMinBlocks(), "Max:" + type.getMaxBlocks()));
			blockLimits.setItemMeta(blockLimitsMeta);
			items.add(blockLimits);
			if (type instanceof Fuel) {
				Fuel type2 = (Fuel)type;
				ItemStack fuelLimits = new ItemStack(Material.CAULDRON, 1);
				ItemMeta fuelMeta = fuelLimits.getItemMeta();
				fuelMeta.setDisplayName("Block Limits");
				fuels = new ArrayList<>();
				for (Material material : type2.getFuelTypes()) {
					fuels.add(material.name().toLowerCase());
				}
				fuelMeta.setLore(fuels);
				fuelLimits.setItemMeta(fuelMeta);
				items.add(fuelLimits);
			}
			if (type instanceof RequiredMaterial) {
				RequiredMaterial type2 = (RequiredMaterial) type;
				ItemStack matLimits = new ItemStack(Material.CAULDRON, 1);
				ItemMeta matMeta = matLimits.getItemMeta();
				matMeta.setDisplayName("Required Blocks");
				fuels = new ArrayList<>();
				Set<Material> materials = type2.getRequiredMaterials();
				if (materials != null) {
					for (Material material : materials) {
						fuels.add(material.name());
					}
					matMeta.setLore(fuels);
					matLimits.setItemMeta(matMeta);
					items.add(matLimits);
				}
				ItemStack perLimits = new ItemStack(Material.CAULDRON, 1);
				ItemMeta perMeta = perLimits.getItemMeta();
				perMeta.setDisplayName("Percent");
				perMeta.setLore(Arrays.asList("Percent:" + type2.getRequiredPercent()));
				perLimits.setItemMeta(perMeta);
				items.add(perLimits);
			}
			return items;
		}

		public List<ItemStack> getPage1Info(HumanEntity entity) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			ItemStack versionIS = new ItemStack(Material.ARMOR_STAND, 1);
			ItemMeta versionIM = versionIS.getItemMeta();
			versionIM.setDisplayName("Version");
			versionIM.setLore(Arrays.asList(Config.getConfig().getLatestVersionString()));
			versionIS.setItemMeta(versionIM);
			items.add(versionIS);
			ItemStack limitIS = new ItemStack(Material.ANVIL, 1);
			ItemMeta limitIM = limitIS.getItemMeta();
			limitIM.setDisplayName("Limits");
			limitIM.setLore(Arrays.asList("Track: " + config.getInt("Structure.StructureLimits.trackLimit"), "SubHeight: " + config.getInt("Structure.StructureLimits.airCheckGap")));
			limitIS.setItemMeta(limitIM);
			items.add(limitIS);
			return items;
		}

		public void onInterfaceBoot(HumanEntity player, int page) {
			block6: {
				if (page == 1) {
					Inventory inv = ShipsGUICommand.createPageGUI(this.getPage1Info(player), this.getInventoryName(), page, false);
					player.openInventory(inv);
				} else {
					try {
						VesselType type = VesselType.values().get(page - 2);
						List<ItemStack> items = this.getVesselTypeInfo(type);
						Inventory inv = ShipsGUICommand.createPageGUI(items, this.getInventoryName(), page, false);
						player.openInventory(inv);
					} catch (IndexOutOfBoundsException e) {
						Inventory inv = ShipsGUICommand.createPageGUI(new ArrayList<ItemStack>(), this.getInventoryName(), page, false);
						player.openInventory(inv);
						if (!(player instanceof Player))
							break block6;
					}
				}
			}
		}

		@Override
		public void onInterfaceBoot(HumanEntity player) {
			Inventory inv = ShipsGUICommand.createPageGUI(this.getPage1Info(player), this.getInventoryName(), 1, true);
			player.openInventory(inv);
		}

		@Override
		public String getInventoryName() {
			return "Info";
		}
	}

}
