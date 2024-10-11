package ass01.core.domain;

import ass01.core.database.DataStorage;

public class RideSimulation extends Thread {
	
	private final Ride ride;
	private final DataStorage storage;
	private volatile boolean stopped;
	
	public RideSimulation(Ride ride, DataStorage storage) {
		this.ride = ride;
		this.storage = storage;
		stopped = false;
	}
	
	public void run() {
		var b = ride.getEBike();
		var user = ride.getUser();
		b.updateSpeed(1);
		
		var lastTimeDecreasedCredit = System.currentTimeMillis();
		user.decreaseCredit(1);

		var lastTimeChangedDir = System.currentTimeMillis();
		
		while (!stopped) {
			
			/* update pos */
			
			var l = b.getLocation();
			var d = b.getDirection();
			var s = b.getSpeed();
			b.updateLocation(l.sum(d.mul(s)));
			l = b.getLocation();
			if (l.x() > 200 || l.x() < -200) {
				b.updateDirection(new V2d(-d.x(), d.y()));
				if (l.x() > 200) {
					b.updateLocation(new P2d(200, l.y()));
				} else {
					b.updateLocation(new P2d(-200, l.y()));
				}
			};
			if (l.y() > 200 || l.y() < -200) {
				b.updateDirection(new V2d(d.x(), -d.y()));
				if (l.y() > 200) {
					b.updateLocation(new P2d(l.x(), 200));
				} else {
					b.updateLocation(new P2d(l.x(), -200));
				}
			};
			
			/* change dir randomly */
			
			var elapsedTimeSinceLastChangeDir = System.currentTimeMillis() - lastTimeChangedDir;
			if (elapsedTimeSinceLastChangeDir > 500) {
				double angle = Math.random()*60 - 30;
				b.updateDirection(d.rotate(angle));
				elapsedTimeSinceLastChangeDir = System.currentTimeMillis();
			}
			
			
			/* update credit */
			
			var elapsedTimeSinceLastDecredit = System.currentTimeMillis() - lastTimeDecreasedCredit;
			if (elapsedTimeSinceLastDecredit > 1000) {
				user.decreaseCredit(1);
				lastTimeDecreasedCredit = System.currentTimeMillis();
			}

			// update database
			storage.update(b.getId(), b);
			storage.update(user.getId(), user);

			try {
				Thread.sleep(20);
			} catch (Exception ex) {}
			
		}
	}

	public void stopSimulation() {
		stopped = true;
		interrupt();
	}
}
