package fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ;

import java.util.ArrayList;

/**
 * A preference in arrival stations. The ride calculator has to prefer
 * sending the user to a Plus station on his ride.
 * 
 * @author Pierre Averous
 * @author Nicolas Topuz
 * @since 1.0
 */
public class PreferPlus implements ArrivalStationPreferenceVisitable {
	
	/**
	 * Constructor to get an PreferPlus instance.
	 */
	public PreferPlus() {
		
	}
	
	@Override
	public ArrayList<Station> getArrivalStations(Ride ride) {
		ArrayList<Station> allStations = ride.getAllStations();
		ArrayList<Station> arrivalStations = new ArrayList<Station>();
		for (int i = 0; i < allStations.size(); i++) {
			if (this.isValidPlusStationInRange(ride)) {
				if(allStations.get(i).isFreeSlot() && allStations.get(i) instanceof StationPlus) {
					arrivalStations.add(allStations.get(i));
				}
			}
			else {
				if(allStations.get(i).isFreeSlot()) {
					arrivalStations.add(allStations.get(i));
				}
			}
		}
		return arrivalStations;
	}
	
	public boolean isValidPlusStationInRange(Ride ride) {
		ArrayList<Station> arrivalStations = ride.getArrivalStations();
		double[] arrivalDistances = ride.distanceToArrival(arrivalStations);
		
		double minDistance = arrivalDistances[0];
		for (int i = 0; i < arrivalDistances.length; i++) {
			if(arrivalDistances[i]<minDistance) {minDistance = arrivalDistances[i];}
		}
		
		for (int i = 0; i < arrivalDistances.length; i++) {
			if(arrivalStations.get(i) instanceof StationPlus && arrivalDistances[i]<1.1*minDistance) {
				return true;
			}
		}
		return false;
	}
}
