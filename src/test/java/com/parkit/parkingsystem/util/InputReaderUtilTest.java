package com.parkit.parkingsystem.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
public class InputReaderUtilTest {
	
	
	@Test
	@DisplayName("Read the default input")
	public void testReadSelection(){
		InputReaderUtil inputReaderUtil = new InputReaderUtil(null);
		assertEquals(-1,inputReaderUtil.readSelection());		
	}

	

	@Test
	@DisplayName("Returns correctly an error when the VehicleRegistrationNumber input is null")
	public void testReadVehicleRegistrationNumberNull() throws Exception {
		InputReaderUtil inputReaderUtil = new InputReaderUtil(null);
		    assertThrows(Exception.class,
		            ()->{
		            	inputReaderUtil.readVehicleRegistrationNumber();
		            });
	}
	

	/*
	@Mock
	private static Scanner scan = new Scanner(System.in);
	
	@Test
	@DisplayName("Returns correctly an error if an incorrect input is given")
	public void testReadIncorrectSelection1(){
		doReturn("abc").when(scan).nextLine();
		when(scan.nextLine()).thenReturn("abc");
		InputReaderUtil inputReaderUtil = new InputReaderUtil(scan);
		when(scan.hasNextLine()).thenReturn(false);
		assertEquals(-1,inputReaderUtil.readSelection());
	}
	
	@Test
	@DisplayName("Returns correctly an error when the VehicleRegistrationNumber input is null")
	public void testReadIncorrectSelection2(){
		when(scan.nextLine()).thenReturn(null);
		InputReaderUtil inputReaderUtil = new InputReaderUtil(scan);
		when(scan.hasNextLine()).thenReturn(false);
		assertThrows(Exception.class,
		            ()->{
		            	inputReaderUtil.readVehicleRegistrationNumber();
		            });;
	}
	
	@Test
	@DisplayName("Read the default input")
	public void testReadSelection(){
		InputReaderUtil inputReaderUtil = new InputReaderUtil(null);
		assertEquals(-1,inputReaderUtil.readSelection());		
	}*/

	
}
