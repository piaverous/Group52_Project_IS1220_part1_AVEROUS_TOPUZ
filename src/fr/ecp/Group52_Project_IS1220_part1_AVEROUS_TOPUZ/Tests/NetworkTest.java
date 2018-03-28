package fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.Tests;

import static org.junit.Assert.*;

import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.Bike;
import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.BikeFactory;
import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.BikesType;
import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.CardTypes;
import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.GPScoordinates;
import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.Network;
import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.Station;
import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.User;
import fr.ecp.Group52_Project_IS1220_part1_AVEROUS_TOPUZ.Exceptions.*;

import org.junit.Test;

public class NetworkTest {

	@Test
	public void testNetwork() {
		Network n1 = new Network();
		assertTrue("Basic Constructor", n1 instanceof Network && n1.getStationList().isEmpty() && n1.getBikeList().isEmpty() && n1.getUserList().isEmpty());
	}
	
	@Test
	public void testNetworkIntIntDouble() {
		try{
			Network n1 = new Network(0,0,0);
			assertTrue("Constructeur basique", n1 instanceof Network && n1.getStationList().size()==0 && n1.getBikeList().size()==0);
			Network n2 = new Network(1,10,0.7f);
			assertTrue("Constructeur avec une station, partiellement rempli", n2 instanceof Network && n2.getStationList().size()==1 && n2.getStationList().get(0).getParkingSlots().size()==10 && n2.getStationList().get(0).getParkingSlots().get(6).isBike() && !n2.getStationList().get(0).getParkingSlots().get(8).isBike());
			Network n3 = new Network(1,2,1f);
			assertTrue("Constructeur avec une station, entièrement rempli", n3 instanceof Network && n3.getStationList().size()==1 && n3.getStationList().get(0).getParkingSlots().size()==2 && n3.getStationList().get(0).getParkingSlots().get(1).isBike());
			Network n4 = new Network(1,2,0f);
			assertTrue("Constructeur avec une station, 0 bike", n4 instanceof Network && n4.getStationList().size()==1 && n4.getStationList().get(0).getParkingSlots().size()==2 && !n4.getStationList().get(0).isAvailableBicycle());
			Network n5 = new Network(5,2,0.5f);
			assertTrue("Constructeur avec cinq station, moitié bike", n5 instanceof Network && n5.getStationList().size()==5 && n5.getStationList().get(4).getParkingSlots().size()==2 && n5.getStationList().get(4).getParkingSlots().get(0).isBike() && !n5.getStationList().get(4).getParkingSlots().get(0).isBike());
		} catch(InvalidProportionsException e) {
			e.printStackTrace();
		}		
	}
	
	@Test (expected = InvalidProportionsException.class)
	public void testNetworkException_over() throws InvalidProportionsException {
		Network n1 = new Network (1,1,1.5f);
	}
	
	@Test (expected = InvalidProportionsException.class)
	public void testNetworkException_under() throws InvalidProportionsException {
		Network n1 = new Network (1,1,-0.1f);
	}

	@Test
	public void testCreateStationIntGPScoordinates() {
		try {
			Network n1 = new Network();
			n1.createStation(2, new GPScoordinates(40,50));
			assertTrue("Create Station", n1.getStationList().size()==1 && n1.getStationList().get(0).getParkingSlots().size()==2);
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateStationIntGPScoordinatesIntFloat() {
		try {
			Network n1 = new Network();
			n1.createStation(2, new GPScoordinates(40,50),1,1f);
			assertTrue("Create Station", n1.getStationList().size()==1 && n1.getStationList().get(0).getParkingSlots().size()==2 && n1.getStationList().get(0).getParkingSlots().get(0).isBike() && n1.getBikeList().size()==1);
		} catch (OutOfBoundsException | MoreBikesThanSlotsException | InvalidProportionsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateUserString() {
		Network n1 = new Network();
		n1.createUser("Pierre");
		assertTrue("Create User", n1.getUserList().size()==1 && n1.getUserList().get(0).getName().equals("Pierre"));
	}

	@Test
	public void testCreateUserStringCardTypes() {
		Network n1 = new Network();
		n1.createUser("Pierre",CardTypes.Vmax);
		assertTrue("Create User", n1.getUserList().size()==1 && n1.getUserList().get(0).getName().equals("Pierre") && n1.getUserList().get(0).getCard().equals(CardTypes.Vmax));
	}

	@Test
	public void testCreateBike() {
		Network n1 = new Network();
		n1.createBike(BikesType.Electrical);
		assertTrue("Create Bike", n1.getBikeList().size()==1 && n1.getBikeList().get(0).getType().equals(BikesType.Electrical));
	}

	@Test
	public void testAddBike() {
		Network n1= new Network();
		Bike b1 = BikeFactory.create(BikesType.Electrical);
		n1.addBike(b1);
		assertTrue("Add Bike", n1.getBikeList().get(0).equals(b1));
	}

	@Test
	public void testAddUser() {
		Network n1= new Network();
		User u1 = new User("Pierre");
		n1.addUser(u1);
		assertTrue("Add User", n1.getUserList().get(0).equals(u1));	
	}

	@Test
	public void testAddStation() {
		try {
			Network n1= new Network();
			Station s1 = new Station(n1,2,new GPScoordinates(40, 50));
			n1.addStation(s1);
			assertTrue("Add User", n1.getStationList().get(0).equals(s1));	
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
		}
			
	}

}
