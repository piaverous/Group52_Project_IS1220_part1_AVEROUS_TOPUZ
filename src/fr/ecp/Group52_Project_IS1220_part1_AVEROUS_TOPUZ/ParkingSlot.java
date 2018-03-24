package fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ;

import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.Exceptions.EmptySlotException;
import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.Exceptions.OccupiedSlotException;

/**
 * ParkingSlots is the class for all parking slots in the
 * myVelib system. Every parking slot has a unique ID within
 * the station it is built in. Its state defines if the 
 * parking slot is available or not. The state variable
 * can take three values :
 * <ul>
 * <li> "free" 
 * <li> "taken"
 * <li> "outOfOrder"
 * </ul> 
 * 
 * @author Pierre Averous
 * @author Nicolas Topuz
 * @since 1.0
 * 
 */

public class ParkingSlot implements VisitableItems{
	/**
	 * A double to store the unique ID of the parking slot within a Station.
	 */
	private double parkingSlotID;
	
	/**
	 * A ParkingSlotState to store the state the parking slot is in.
	 */
	private ParkingSlotState state;
	
	/**
	 * A Station object to indicate in which Station the parking slot is located.
	 */
	private Station station;
	
	/**
	 * A Bike object to store the bike that is available at the parking slot.
	 */
	private Bike bike;
	
	/**
	 * A constructor for creating a ParkingSlot instance with a unique ID within a station
	 * @param station	the station in which the parking slot is built.
	 */
	public ParkingSlot(Station station) {
		this.parkingSlotID = station.getParkingCounter();
		station.countUp();
		this.station = station;
		this.state = ParkingSlotState.free;
	}
	
	/**
	 * A constructor for creating a ParkingSlot instance with a unique ID within a station
	 * @param station	the station in which the parking slot is built.
	 * @param bike		the bike stationed at the parking slot
	 */
	public ParkingSlot(Station station, Bike bike) {
		this.parkingSlotID = station.getParkingCounter();
		station.countUp();
		this.station = station;
		this.state = ParkingSlotState.free;
		this.bike = bike;
	}
	
	
	//Autres M�thodes
	
	/**
	 * This method tells if a parking slot is available or not. 
	 * It returns <code>false</code> if it is "taken" or "out of order", <code>true</code> if it is "free".
	 * 
	 * @return a boolean, tells if the spot is free (<code>true</code>) or not (<code>false</code>)
	 */
	public boolean isFree() {
		if (this.state == ParkingSlotState.free) { return true; }
		else { return false; }
	}
	
	/**
	 * This method tells if there is a bike on the parking slot or not.
	 * It returns <code>false</code> if there is no bike, or <code>true</code> if there is one.
	 * 
	 * @return a boolean, tells if there's a bike (<code>true</code>) or not (<code>false</code>)
	 */
	public boolean isBike() {
		if (this.state == ParkingSlotState.taken) { return true; }
		else { return false; }
	}
	
	/**
	 * This method allows a User to drop a bike in a parking slot.
	 * It is called upon by the User class.
	 * 
	 * @param	u	User who drops bike
	 * @see		User 
	  */
	public void acceptBike(User u) throws OccupiedSlotException {
		if (!this.isFree()) {throw new OccupiedSlotException();}
		else {
			this.bike = u.getBike();
			u.setBike(null);
			this.state = ParkingSlotState.taken;
			this.station.setAvailableBikeNumber(this.station.getAvailableBikeNumber()+1);
			this.station.setFreeSlotNumber(this.station.getFreeSlotNumber()-1);
			if(this.station.getFreeSlotNumber() == 0) {
				this.station.notifyAllArrivalObservers();
			}
		}	
	}
	
	/**
	 * This method allows a User to take a bike in a parking slot.
	 * It is called upon by the User class.
	 * 
	 * @param	u	User who takes bike
	 * @see		User 
	  */
	public void giveBike(User u) throws EmptySlotException {
		if (!this.isBike()) {throw new EmptySlotException();}
		else {
			u.setBike(this.bike);
			this.bike = null;
			this.state = ParkingSlotState.free;
			this.station.setAvailableBikeNumber(this.station.getAvailableBikeNumber()-1);
			this.station.setFreeSlotNumber(this.station.getFreeSlotNumber()+1);
			if(this.station.getAvailableBikeNumber() == 0) {
				this.station.notifyAllDepartureObservers();
			}
		}
	}
	
	
	//Mise en place des getters
	/**
	 * Getter method for the state of the parkingSlot
	 * 
	 * @return 	the state of the parking slot from ParkingSlotState enum
	 * @see ParkingSlotState
	 */
	public ParkingSlotState getState() {
		return this.state;
	}
	
	/**
	 * Getter method for the parking slot's unique ID
	 * 
	 * @return The parking slot's ID.
	 */
	public double getParkingSlotID() {
		return this.parkingSlotID;
	}
	
	/**
	 * Getter method for the station the parking slot belongs to.
	 * 
	 * @return The parking slot's station.
	 */
	public Station getStation() {
		return this.station;
	}
	
	/**
	 * Getter method for the bike that is on the parking slot.
	 * 
	 * @return The bike that is on the slot.
	 */
	public Bike getBike() throws EmptySlotException {
		if(this.bike == null) {throw new EmptySlotException();}
		else { return this.bike;}
	}
	
	
	//Mise en place des setters
	/**
	 * Setter method for the state of the parkingSlot
	 * 
	 * @param 	the new state of the parking slot from ParkingSlotState enum
	 * @see ParkingSlotState
	 */
	public void setState(ParkingSlotState state) {
		this.state = state;
	}
	
	/**
	 * Setter method for the bike that is on the parking slot.
	 * 
	 * @param The bike that is to be set on the slot.
	 */
	public void setBike(Bike bike) {
		this.bike = bike;
	}
	
	
	@Override
	public String toString() {
		String str = ("This is parking slot number " + this.parkingSlotID + ", situated within station number " + this.station.getStationID() +".");
		str += "\n";
		str += ("This parking slot is located at : "+this.station.getLocation().toString()+".");
		return str; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParkingSlot) {
			ParkingSlot that = (ParkingSlot) obj;
			return (this.parkingSlotID==that.getParkingSlotID() && this.station==that.getStation());
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return (int) (41*(41+this.station.hashCode())+this.parkingSlotID);
	}

	@Override
	public String accept(StatisticCompiler v) {
		return v.visit(this);
	}

	
}
