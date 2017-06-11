package MoseShipsSponge.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import com.flowpowered.math.vector.Vector3i;

public class LocationUtils {

	public static boolean blocksEqual(Location<? extends Extent> loc1, Location<? extends Extent> loc2) {
		if ((loc1.getBlockX() == loc2.getBlockX()) && (loc1.getBlockY() == loc2.getBlockY()) && (loc1.getBlockZ() == loc2.getBlockZ())) {
			return true;
		}
		return false;
	}

	public static boolean blockWorldContains(Collection<Location<World>> list, Location<? extends Extent> loc) {
		return list.stream().anyMatch(l -> blocksEqual(l, loc));
	}

	public static boolean blockChunkContains(Collection<Location<Chunk>> list, Location<? extends Extent> loc) {
		return list.stream().anyMatch(l -> blocksEqual(l, loc));
	}
	
	public static Vector3i getReletive(Direction direction, int speed) {
		switch (direction) {
			case DOWN:
				return new Vector3i(0, -speed, 0);
			case EAST:
				return new Vector3i(speed, 0, 0);
			case NONE:
				return new Vector3i(0, 0, 0);
			case NORTH:
				return new Vector3i(0, 0, -speed);
			case NORTHEAST:
				break;
			case NORTHWEST:
				break;
			case SOUTH:
				return new Vector3i(0, 0, speed);
			case SOUTHEAST:
				break;
			case SOUTHWEST:
				break;
			case UP:
				return new Vector3i(0, speed, 0);
			case WEST:
				return new Vector3i(-speed, 0, 0);
			default:
				return new Vector3i(0, 0, 0);
		}
		return new Vector3i(0, 0, 0);
	}
	
	public static List<Sign> getAttachedSigns(Location<? extends Extent> loc){
		List<Sign> list = new ArrayList<>();
		Direction[] directions = {Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH};
		for(Direction direction : directions){
			Location<? extends Extent> loc2 = loc.copy().getRelative(direction);
			Optional<TileEntity> opTile = loc2.getTileEntity();
			if(opTile.isPresent()){
				TileEntity entity = opTile.get();
				if(entity instanceof Sign){
					list.add((Sign)entity);
				}
			}
		}
		return list;
	}

}
