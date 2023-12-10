package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.repositories.CustomerRepository;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class MenuService {
	private static List<Customer> listAllCustomers = CustomerRepository.getAllCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static List<Customer>userTempLogin = new ArrayList<>();
	private static List<BookingOrder>allBookingOrders = new ArrayList<>();
	private static String REGEX_PATTERN_CUSTOMER_ID = "(?i)^Cust-\\d{3}$";
	private static String REGEX_PATTERN = "^[a-zA-Z0-9_]*$";
	private static String REGEX_SERVICE = "(?i)^(Svcm|Svcc)-[0-9]{3}$";
	private static String REGEX_PATTREN_YESNO = "^(?i)(ya|tidak)$";
	private static String REGEX_PATTREN_SALDO = "^0(Saldo Coin|Cash)$";
	private static Scanner input = new Scanner(System.in);

	public static void run() {

		boolean isLooping = true;
		do {
			login();
			if (!userTempLogin.isEmpty()){
				mainMenu();
			}
			;
		} while (isLooping);
		
	}
	
	public static void login() {
		boolean isLooping = true;
		System.out.println("----------Booking System Bengkel----------");
		String [] listLoginMenu = {"Login","Exit"};
		PrintService.printMenu(listLoginMenu, "Menu Login");
		int menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", listLoginMenu.length-1, 0);
		switch (menuChoice) {
			case 1:
				BengkelService.loginProcess(listAllCustomers, userTempLogin,REGEX_PATTERN_CUSTOMER_ID,REGEX_PATTERN);
				break;
			case 0:
				isLooping = false;
			default:
				break;
		}
		if(!isLooping){
			System.out.println("Menutup Aplikasi");
			System.exit(0);
		}
	}
	
	public static void mainMenu() {
		String[] listMenu = {"Informasi Customer", "Booking Bengkel", "Top Up Bengkel Coin", "Informasi Booking", "Logout"};
		int menuChoice = 0;
		boolean isLooping = true;
		
		do {
			System.out.println();
			PrintService.printMenu(listMenu, "Booking Bengkel Menu");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
			System.out.println(menuChoice);
			
			switch (menuChoice) {
			case 1:
				//panggil fitur Informasi Customer
				BengkelService.customerInfo(userTempLogin);
				break;
			case 2:
				//panggil fitur Booking Bengkel
				BengkelService.bookingReservation(userTempLogin, listAllItemService, allBookingOrders,REGEX_PATTERN,REGEX_PATTREN_YESNO,REGEX_PATTREN_SALDO,REGEX_SERVICE);
				break;
			case 3:
				//panggil fitur Top Up Saldo Coin
				if (userTempLogin.get(0)instanceof MemberCustomer){
					BengkelService.topUp(userTempLogin);
				}
				else{
					System.out.println("Untuk Melakukan TopUp anda harus menjadi member");
				}
				break;
			case 4:
				//panggil fitur Informasi Booking Order
				PrintService.printhistory(allBookingOrders,userTempLogin);
				break;
			case 0:
				System.out.println("Logout");
				BengkelService.logoutProcess(userTempLogin);
				isLooping = false;
				break;
			default:
				break;
			}
		} while (isLooping);
		
	}
	
	//Silahkan tambahkan kodingan untuk keperluan Menu Aplikasi
}
