package com.bengkel.booking.services;

import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.Motorcyle;
import com.bengkel.booking.models.Vehicle;

public class Validation {
	
	public static String validasiInput(String question, String errorMessage, String regex) {
	    Scanner input = new Scanner(System.in);
	    String result;
	    boolean isLooping = true;
	    do {
	      System.out.print(question);
	      result = input.nextLine();

	    //   validasi menggunakan matches
	      if (result.matches(regex)) {
	        isLooping = false;
	      }else {
	        System.out.println(errorMessage);
	      }

	    } while (isLooping);

	    return result;
	  }
	
	public static int validasiNumberWithRange(String question, String errorMessage, String regex, int max, int min) {
	    int result;
	    boolean isLooping = true;
	    do {
	      result = Integer.valueOf(validasiInput(question, errorMessage, regex));
	      if (result >= min && result <= max) {
	        isLooping = false;
	      }else {
	        System.out.println("Pilihan angka " + min + " s.d " + max);
	      }
	    } while (isLooping);

	    return result;
	  }
	public static String userValidation(){
		String userStatusMember = "";
		return userStatusMember;
	}
	public static Customer getCustomerbyLogin(String userId, String Password, List<Customer>listCustomer){ 
		Customer customer = listCustomer.stream()
				.filter(data -> (data.getCustomerId().equalsIgnoreCase(userId))&&(data.getPassword().equalsIgnoreCase(Password)))
				.findAny()
				.orElse(null);

		if (customer == null){
			System.out.println("Login Failed, customerId is not found");
			return null;
		}
		else{
			return customer;
		}
				
	}
	public static Vehicle getVehicleById(List<Vehicle>listVehicles,String vehicleId){
		Vehicle vehicle = listVehicles.stream()
				.filter(data -> data.getVehiclesId().equalsIgnoreCase(vehicleId))
				.findFirst()
				.orElse(null);
		
		if(vehicle == null){
			System.out.println("Vehicel id not found");
			return null;
		}else{
			return vehicle;
		}
	}
	public static ItemService getItemServicebyId(List<ItemService>AllListService,String itemServiceId){
		ItemService itemService = AllListService.stream()
					.filter(data -> data.getServiceId().equalsIgnoreCase(itemServiceId))
					.findFirst()
					.orElse(null);
					
		if(itemService == null){
			System.out.println("Vehicel id not found");
			return null;
		}else{
			return itemService;
		}
	}
}
