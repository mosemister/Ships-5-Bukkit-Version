package MoseShipsSponge.Event.Create;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;

import MoseShipsSponge.Event.ShipsEvent;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;

public class ShipsCreateEvent extends ShipsEvent implements Cancellable {

	boolean g_cancelled;

	public ShipsCreateEvent(ShipsData ship, Cause cause) {
		super(ship, cause);
	}

	@Override
	public boolean isCancelled() {
		return g_cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		g_cancelled = arg0;
	}

}
