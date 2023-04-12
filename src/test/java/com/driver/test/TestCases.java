package com.driver.test;

import com.driver.EaseMyTrip;
import com.driver.controllers.AirportController;
import com.driver.model.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest(classes = EaseMyTrip.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCases {
    AirportController airportController = new AirportController();
    @Test
    public void testAddAirport(){
        String result = airportController.addAirport(new Airport("IGI2",2,City.DELHI));
        Assertions.assertEquals("SUCCESS",result);
    }

    @Test
    public void testGetLargestAirportName(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        String airportName = airportController.getLargestAirportName();
        Assertions.assertEquals("KIA",airportName);
    }

    @Test
    public void testGetLargestAirportNameInCaseOfSameNumberOfTerminals(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addAirport(new Airport("BIA",4,City.BANGLORE));
        String airportName = airportController.getLargestAirportName();
        Assertions.assertEquals("BIA",airportName);
    }
    @Test
    public void testGetShortestDurationOfPossibleBetweenTwoCities(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));

        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));

        double duration = airportController.getShortestDurationOfPossibleBetweenTwoCities(City.CHANDIGARH,City.BANGLORE);
        Assertions.assertEquals(2.5,duration);

    }
    @Test
    public void testGetShortestDurationOfPossibleBetweenTwoCitiesWhenFightDontExist(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        double duration = airportController.getShortestDurationOfPossibleBetweenTwoCities(City.CHENNAI,City.BANGLORE);
        Assertions.assertEquals(-1,duration);

    }
    @Test
    public void testGetNumberOfPeopleOn(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addPassenger(new Passenger(1,"e1","p1",12));
        airportController.addPassenger(new Passenger(2,"e2","p2",15));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        airportController.bookATicket(1,1);
        airportController.bookATicket(2,2);
        int numberOfPeopleOn = airportController.getNumberOfPeopleOn(new Date(2023,3,27),"KIA");
        Assertions.assertEquals(2,numberOfPeopleOn);
    }
    @Test
    public void testGetNumberOfPeopleOnWithNoFlight(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        int numberOfPeopleOn = airportController.getNumberOfPeopleOn(new Date(2023,3,27),"A");
        Assertions.assertEquals(0,numberOfPeopleOn);
    }
    @Test
    public void testCalculateFlightFare(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addPassenger(new Passenger(1,"e1","p1",12));
        airportController.addPassenger(new Passenger(2,"e2","p2",15));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        airportController.bookATicket(1,1);
        airportController.bookATicket(2,2);
        int flightFare = airportController.calculateFlightFare(1);
        Assertions.assertEquals(3050,flightFare);
    }
    @Test
    public void testBookAFlight(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addPassenger(new Passenger(1,"e1","p1",12));
        airportController.addPassenger(new Passenger(2,"e2","p2",15));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        airportController.bookATicket(1,1);
        airportController.bookATicket(2,2);
        String result = airportController.bookATicket(1,2);
        Assertions.assertEquals("SUCCESS", result);
    }
    @Test
    public void testBookAFlightWhenPassengerAlreadyThere(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addPassenger(new Passenger(1,"e1","p1",12));
        airportController.addPassenger(new Passenger(2,"e2","p2",15));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        airportController.bookATicket(1,1);
        airportController.bookATicket(2,2);
        String result = airportController.bookATicket(1,1);
        Assertions.assertEquals("FAILURE", result);
    }

    @Test
    public void testCancelATicket(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addPassenger(new Passenger(1,"e1","p1",12));
        airportController.addPassenger(new Passenger(2,"e2","p2",15));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        airportController.bookATicket(1,1);
        airportController.bookATicket(2,2);
        String result = airportController.cancelATicket(1,1);
        Assertions.assertEquals("SUCCESS", result);

    }
    @Test
    public void testCancelATicketWhenTicketDoesntExist(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addPassenger(new Passenger(1,"e1","p1",12));
        airportController.addPassenger(new Passenger(2,"e2","p2",15));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        airportController.bookATicket(1,1);
        airportController.bookATicket(2,2);
        String result = airportController.cancelATicket(2,1);
        Assertions.assertEquals("FAILURE", result);
    }
    @Test
    public void testAddFlight(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        String result = airportController.addFlight(new Flight(3,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        Assertions.assertEquals("SUCCESS", result);

    }
    @Test
    public void testGetAirportNameFromFlightId(){airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));

        String airportName = airportController.getAirportNameFromFlightId(1);
        Assertions.assertEquals("CA", airportName);
    }
    @Test
    public void testGetAirportNameFromFlightIdWhenAirportDoesntExist(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        String airportName = airportController.getAirportNameFromFlightId(3);
        Assertions.assertEquals(null, airportName);
    }
    @Test
    public void testCalculateRevenueOfAFlight(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addPassenger(new Passenger(1,"e1","p1",12));
        airportController.addPassenger(new Passenger(2,"e2","p2",15));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        airportController.bookATicket(1,1);
        airportController.bookATicket(2,2);
        int revenue = airportController.calculateRevenueOfAFlight(1);
        Assertions.assertEquals(3000, revenue);
    }
    @Test
    public void testAddPassenger(){
        String result = airportController.addPassenger(new Passenger(3,"e3","p3",15));
        Assertions.assertEquals("SUCCESS", result);

    }
    @Test
    public void testCountOfBookingsDoneByPassengerAllCombined(){
        airportController.addAirport(new Airport("IGI",2,City.DELHI));
        airportController.addAirport(new Airport("CA",3,City.CHANDIGARH));
        airportController.addAirport(new Airport("KIA",4,City.BANGLORE));
        airportController.addPassenger(new Passenger(1,"e1","p1",12));
        airportController.addPassenger(new Passenger(2,"e2","p2",15));
        airportController.addFlight(new Flight(1,City.CHANDIGARH,City.BANGLORE,100,new Date(2023,3,27),2.5));
        airportController.addFlight(new Flight(2,City.DELHI,City.BANGLORE,100,new Date(2023,3,27),2.0));
        airportController.bookATicket(1,1);
        airportController.bookATicket(2,2);
        airportController.bookATicket(2,1);
        int count = airportController.countOfBookingsDoneByPassengerAllCombined(1);
        Assertions.assertEquals(2, count);
    }
}


