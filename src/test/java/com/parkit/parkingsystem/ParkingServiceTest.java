package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeEach
    private void setUpPerTest() {
        try {
        	parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }
    }

    @Test
    @Tag("ParkingServiceTest")
    @DisplayName("Parking spot is correctly updated when the vehicle leaves the parking ")
    public void processExitingVehicleTest() throws Exception{
    	when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
        when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
        Ticket ticket = new Ticket();
        ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
        parkingService.processExitingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }
    
    
    @Test
    @Tag("ParkingServiceTest")
    @DisplayName("A ticket is correctly saved when a vehicle enters the parking ")
    public void processIncomingVehicleTest() throws Exception{
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
        when(ticketDAO.saveTicket(any(Ticket.class))).thenReturn(true);
        parkingService.processIncomingVehicle();
        verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
    }
    
    @Test
    @Tag("ParkingServiceTest")
    @DisplayName("When the user inputs 1, the vehicle type is a car")
    public void getVehichleTypeCarTest() {
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	assertEquals(ParkingType.CAR,parkingService.getVehichleType());
    }
    
    @Test
    @Tag("ParkingServiceTest")
    @DisplayName("When the user inputs 2, the vehicle type is a bike")
    public void getVehichleTypeBikeTest() {
    	when(inputReaderUtil.readSelection()).thenReturn(2);
    	assertEquals(ParkingType.BIKE,parkingService.getVehichleType());
    }
    
    @Test
    @Tag("ParkingServiceTest")
    @DisplayName("When the user inputs an incorrect number, there is an error")
    public void getVehichleTypeIncorrectTest() {
    	when(inputReaderUtil.readSelection()).thenReturn(-1);
    	assertThrows(IllegalArgumentException.class,() -> parkingService.getVehichleType());
    }
    
    @Test
    @Tag("ParkingServiceTest")
    @DisplayName("A car receives correctly a parking spot")
    public void getNextParkingNumberAvailableForCarTest() throws Exception {
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
    	ParkingSpot spot = new ParkingSpot(1,ParkingType.CAR, true);
    	assertEquals(spot,parkingService.getNextParkingNumberIfAvailable());
    }
    
    @Test
    @Tag("ParkingServiceTest")
    @DisplayName("A bike receives correctly a parking spot")
    public void getNextParkingNumberAvailableForBikeTest() throws Exception {
    	when(inputReaderUtil.readSelection()).thenReturn(2);
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE)).thenReturn(1);
    	ParkingSpot spot = new ParkingSpot(1,ParkingType.BIKE, true);
    	assertEquals(spot,parkingService.getNextParkingNumberIfAvailable());
    }
    
    @Test
    @Tag("ParkingServiceTest")
    @DisplayName("There is an error when a vehicle tries to park and there are no spots left")
    public void getNextParkingNumberNonAvailableTest() {
    	when(inputReaderUtil.readSelection()).thenReturn(2);
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE)).thenReturn(0);
    	assertThrows(Exception.class,() -> parkingService.getNextParkingNumberIfAvailable());
    }



    
        

}

