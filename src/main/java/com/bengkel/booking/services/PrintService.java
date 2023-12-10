package com.bengkel.booking.services;

import java.security.Provider.Service;
import java.util.List;
import java.util.stream.IntStream;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.Vehicle;

public class PrintService {
	
	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printVechicle(List<Vehicle> listVehicle) {
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s | %-5s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi", "Tahun", "Tipe Kendaraan");
	    System.out.format(line);
	    int number = 1;
	    String vehicleType = "";

	    for (Vehicle vehicle : listVehicle) {
	    	if (vehicle instanceof Car) {
				vehicleType = "Mobil";
			}else {
				vehicleType = "Motor";
			}
	    	System.out.format(formatTable, number, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getBrand(), vehicle.getTransmisionType(), vehicle.getYearRelease(), vehicleType);
	    	number++;
	    }
	    System.out.printf(line);
	}
	
	public static void printMenuMotorcycle(List<ItemService>listItemServices){
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi");
	    System.out.format(line);
		IntStream.range(0,listItemServices.size())
		.filter(index -> listItemServices.get(index).getVehicleType().equalsIgnoreCase("Motorcyle"))
		.forEach(index -> {
			ItemService item = listItemServices.get(index);
			System.out.printf(formatTable,
					index + 1,
					item.getServiceId(),
					item.getServiceName(),
					item.getVehicleType(),
					item.getPrice()
			);
		});
	}
	public static void printMenuCar(List<ItemService>listItemServices){
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi");
	    System.out.format(line);
		IntStream.range(0,listItemServices.size())
		.filter(index -> listItemServices.get(index).getVehicleType().equalsIgnoreCase("Car"))
		.forEach(index -> {
			ItemService item = listItemServices.get(index);
			System.out.printf(formatTable,
					index + 1,
					item.getServiceId(),
					item.getServiceName(),
					item.getVehicleType(),
					item.getPrice()
			);
		});
	}
	public static void printhistory(List<BookingOrder>allBookingOrders,List<Customer>userTempLogin){
		String formatTable = "| %-2s | %-20s | %-15s | %-20s | %-20s | %-20s | %-20s |%n";
		String line = "+----+----------------------+-----------------+----------------------+----------------------+----------------------+----------------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Booking Id", "Nama Customer", "Payment Method", "Total Service","Total Payment","List Service");
	    System.out.format(line);
		IntStream.range(0,allBookingOrders.size())
		.filter(index->allBookingOrders.get(index).getCustomer().getCustomerId().equalsIgnoreCase(userTempLogin.get(0).getCustomerId()))
		.forEach(index -> {
			BookingOrder data = allBookingOrders.get(index);
			System.out.printf(formatTable,
					index + 1,
					data.getBookingId(),
					data.getCustomer().getName(),
					data.getPaymentMethod(),
					data.getTotalServicePrice(),
					data.getTotalPayment(),
					printServices(data.getServices())
			);
		});
	}
	public static String printServices(List<ItemService>customesListService){
        String result = "";
        // Bisa disesuaikan kembali
        for (ItemService service : customesListService) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }
	//Silahkan Tambahkan function print sesuai dengan kebutuhan.
	
}
