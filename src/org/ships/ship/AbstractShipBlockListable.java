package org.ships.ship;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ships.block.MovingBlock;
import org.ships.block.configuration.MovementInstruction;
import org.ships.block.structure.ShipsStructure;
import org.ships.configuration.BlockList;
import org.ships.ship.type.VesselType;

public abstract class AbstractShipBlockListable extends AbstractShip implements ShipBlockListable{

	protected File blockListLoc;
	protected YamlConfiguration blockListYaml;
	protected BlockList blockList;
	
	public AbstractShipBlockListable(VesselType type, Location loc, Location teleportLocation, OfflinePlayer player,
			ShipsStructure structure, File file) {
		this(type, loc, teleportLocation, player, structure, file, new File(file.getParentFile(), "BlockList.yml"));
	}
	
	public AbstractShipBlockListable(VesselType type, Location loc, Location teleportLocation, OfflinePlayer player,
			ShipsStructure structure, File file, File blockList) {
		super(type, loc, teleportLocation, player, structure, file);
		this.setBlockListFile(blockList);
	}
	
	@Override
	protected boolean isBlocked(MovingBlock block) {
		Location loc = block.getMovingTo();
		Block block2 = loc.getBlock();
		if (!isPartOfVessel(loc)) {
			if (this.blockList.getCurrentWith(block2.getType()).getInstruction().equals(MovementInstruction.RAM)) {
				return false;
			} else if (isMoveInBlock(loc)) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
	
	@Override
	public YamlConfiguration getBlockListConfiguration() {
		return this.blockListYaml;
	}
	
	@Override
	public File getBlockListFile() {
		return this.blockListLoc;
	}

	@Override
	public void setBlockListFile(File file) {
		this.blockListLoc = file;
		this.blockListYaml = YamlConfiguration.loadConfiguration(file);
	}

	@Override
	public BlockList getBlockList() {
		return this.blockList;
	}

	@Override
	public void setBlockList(BlockList blockList) {
		this.blockList = blockList;
	}

}