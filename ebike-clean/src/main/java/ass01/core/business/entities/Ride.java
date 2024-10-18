package ass01.core.business.entities;

import ass01.core.business.ports.RideSimulation;
import ass01.core.business.ports.RideSimulator;

import java.util.Date;
import java.util.Optional;

public class Ride {

	private Date startedDate;
	private Optional<Date> endDate;
	private User user;
	private EBike ebike;
	private boolean ongoing;
	private String id;
	private RideSimulation simulation = null;

	public Ride(String id, User user, EBike ebike, Date startedDate, Optional<Date> endDate, boolean ongoing) {
		this.id = id;
		this.startedDate = startedDate;
		this.endDate = endDate;
		this.user = user;
		this.ebike = ebike;
		this.ongoing = ongoing;
	}
	public Ride(String id, User user, EBike ebike) {
		this(id, user, ebike, new Date(), Optional.empty(), false);
	}
	
	public String getId() {
		return id;
	}

	public void start(RideSimulator simulator) {
		ongoing = true;
		this.simulation = simulator.createSimulation(this);
		simulation.startSimulation();
	}
	
	public void end() {
		endDate = Optional.of(new Date());
		ongoing = false;
		simulation.stopSimulation();
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public boolean isOngoing() {
		return this.ongoing;
	}
	
	public Optional<Date> getEndDate() {
		return endDate;
	}

	public User getUser() {
		return user;
	}

	public EBike getEBike() {
		return ebike;
	}
	
	public String toString() {
		return "{ id: " + this.id + ", user: " + user.getId() + ", bike: " + ebike.getId() + " }";
	}
}
