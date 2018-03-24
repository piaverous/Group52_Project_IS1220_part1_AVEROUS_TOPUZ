package fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ;

import java.util.ArrayList;

public class NoPreference implements ArrivalStationPreferenceVisitable {

	public NoPreference() {
		
	}
	
	public ArrayList<Station> getArrivalStations(Ride ride) {
		ArrayList<Station> allStations = ride.getAllStations();
		ArrayList<Station> arrivalStations = new ArrayList<Station>();
		for (int i = 0; i < allStations.size(); i++) {
			if(allStations.get(i).isFreeSlot()) {
				arrivalStations.add(allStations.get(i));
			}
		}
		return arrivalStations;
	}
}
