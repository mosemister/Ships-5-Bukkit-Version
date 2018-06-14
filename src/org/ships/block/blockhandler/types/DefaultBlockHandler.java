package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Attachable;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class DefaultBlockHandler implements BlockHandler<BlockState> {

	Block block;
	
	@Override
	public Block getBlock() {
		return this.block;
	}

	@Override
	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public void remove(Material material) {
		this.block.setType(material);
	}

	@Override
	public void apply() {
	}
	
	@Override
	public BlockPriority getPriority() {
		if(getBlock().getBlockData() instanceof Attachable) {
			return BlockPriority.ATTACHABLE;
		}
		return BlockPriority.DEFAULT;
	}

}
