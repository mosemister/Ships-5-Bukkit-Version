package MoseShipsBukkit.Ships.Movement.MovementAlgorithm.Types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import MoseShipsBukkit.Ships.Movement.MovementAlgorithm.MovementAlgorithm;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterType;

public class Ships5Movement implements MovementAlgorithm {

	@Override
	public void move(LoadableShip type, List<MovingBlock> blocksUn) {
		List<MovingBlock> blocks = MovingBlock.setPriorityOrder(blocksUn);
		int waterLevel = 63;
		if (type instanceof WaterType) {
			WaterType type2 = (WaterType) type;
			waterLevel = type2.getWaterLevel();
		}
		final int waterLevelFinal = waterLevel;
		for (MovingBlock block : blocks) {
			if (block.getOrigin().getBlockY() > waterLevelFinal) {
				block.clearOriginalBlock();
			} else {
				block.replaceOriginalBlock(Material.STATIONARY_WATER, (byte) 0);
			}
		}
		List<Block> newStructure = new ArrayList<Block>();
		Block lic = null;
		// place all blocks
		for (int A = (blocks.size() - 1); A >= 0; A--) {
			MovingBlock block = blocks.get(A);
			newStructure.add(block.getMovingTo().getBlock());
			block.move();
			if (type.getLocation().getBlock().equals(block.getOrigin().getBlock())){
				lic = block.getMovingTo().getBlock();
				System.out.println("Licence block updated: " + lic);
			}
		}
		/*
		 * blocks.stream().forEach(block -> {
		 * newStructure.add(block.getMovingTo());
		 * block.move(BlockChangeFlag.NONE); MovementType mType =
		 * block.getMovementType(); Optional<Direction> opConnected =
		 * block.getMovingTo().get(Keys.DIRECTION); switch (mType) { case
		 * ROTATE_LEFT: if (opConnected.isPresent()) {
		 * BlockRotate.getRotation(opConnected.get(), RotateType.LEFT); } break;
		 * case ROTATE_RIGHT: if (opConnected.isPresent()) {
		 * BlockRotate.getRotation(opConnected.get(), RotateType.LEFT); } break;
		 * default: break;
		 * 
		 * } });
		 */
		Location loc = blocks.get(0).getMovingTo().clone().subtract(blocks.get(0).getOrigin());
		MovingBlock tBlock = new MovingBlock(type.getTeleportToLocation().getBlock(), loc.getBlockX(), loc.getBlockY(),
				loc.getBlockZ());
		type.setBasicStructure(newStructure, lic, tBlock.getMovingTo());
	}

	@Override
	public String getName() {
		return "Ships 5";
	}

}
