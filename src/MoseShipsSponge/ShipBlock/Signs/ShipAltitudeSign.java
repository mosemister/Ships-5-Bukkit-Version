package MoseShipsSponge.ShipBlock.Signs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.core.helpers.Loader;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Ships.VesselTypes.StaticShipType;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.ShipCommands.ShipCommands;

public class ShipAltitudeSign implements ShipSign {

	@Override
	public void onCreation(ChangeSignEvent event) {
		SignData data = event.getText();
		data.setElement(0, Text.builder("[Altitude]").color(TextColors.YELLOW).build());
		Optional<LiveShip> opShip = Loader.safeLoadShip(event.getTargetTile().getLocatableBlock().getLocation(), true);
		if(opShip.isPresent()){
			LiveShip ship = opShip.get();
			int speed = ship.getEngineSpeed();
			data.setElement(1, Text.of(speed - 1));
			data.setElement(2, Text.of(speed));
		}else{
			data.setElement(2, Text.of(0));
		}
	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		SignData data = sign.getSignData();
		try{
			Optional<Text> opSpeedLine = data.get(2);
			if(opSpeedLine.isPresent()){
				int speed = Integer.parseInt(opSpeedLine.get().toPlain());
				StaticShipType staticShip = ship.getStatic();
				if (staticShip.getAltitudeSpeed() < speed) {
					data.setElement(2, Text.of(speed + 1));
				} else {
					data.setElement(2, Text.of(1));
				}
			}
		}catch(NumberFormatException e){
			apply(sign);
}
	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		SignData data = sign.getSignData();
		try {
			Optional<Text> opSpeedLine = data.get(2);
			if(opSpeedLine.isPresent()){
			int speed = Integer.parseInt(opSpeedLine.get().toPlain());
			if (ship.getCommands().contains(ShipCommands.LOCK_ALTITUDE)) {
				return;
			}
			Cause cause = Cause.builder().named("Player", player).named("Sign", sign).named("SignCommand", "Up").build();
			Optional<FailedMovement> causeMove = ship.move(0, speed, 0, cause);
			if (causeMove.isPresent()) {
				causeMove.get().process(player);
			}
			}
		} catch (NumberFormatException e) {
			apply(sign);
		}
	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		SignData data = sign.getSignData();
		try {
			Optional<Text> opSpeedLine = data.get(2);
			if(opSpeedLine.isPresent()){
			int speed = Integer.parseInt(opSpeedLine.get().toPlain());
			if (ship.getCommands().contains(ShipCommands.LOCK_ALTITUDE)) {
				return;
			}
			Cause cause = Cause.builder().named("Player", player).named("Sign", sign).named("SignCommand", "Up").build();
			Optional<FailedMovement> causeMove = ship.move(0, -speed, 0, cause);
			if (causeMove.isPresent()) {
				causeMove.get().process(player);
			}
			}
		} catch (NumberFormatException e) {
			apply(sign);
		}
	}

	@Override
	public void onRemove(Player player, Sign sign) {}

	@Override
	public List<String> getFirstLine() {
		return Arrays.asList("[Altitude]");
	}

	@Override
	public boolean isSign(Sign sign) {
		SignData data = sign.getSignData();
		Optional<Text> firstLine = data.get(0);
		if(firstLine.isPresent()){
			Text text = firstLine.get();
			if((text.getColor().equals(TextColors.YELLOW) && (text.toPlain().equals("Altitude")))){
				return true;
			}
		}
		return false;
	}

	@Override
	public void apply(Sign sign) {
		List<Text> lines = new ArrayList<>();
		lines.add(Text.builder("[Altitude]").color(TextColors.YELLOW).build());
		Optional<LiveShip> opShip = getAttachedShip(sign);
		if(opShip.isPresent()){
			LiveShip ship = opShip.get();
			int speed = ship.getEngineSpeed();
			lines.add(Text.of(speed - 1));
			lines.add(Text.of(speed));
		}else{
			lines.add(Text.of());
			lines.add(Text.of(0));
		}
		sign.offer(Keys.SIGN_LINES, lines);
	}

}
