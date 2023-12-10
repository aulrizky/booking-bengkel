package com.bengkel.booking.services;

import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;

import com.bengkel.booking.models.BookingOrder;
// import com.bengkel.booking.models.C;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;
import com.bengkel.booking.models.Car;

public class BengkelService {
	
	//Silahkan tambahkan fitur-fitur utama aplikasi disini
	
	//Login
	public static void loginProcess(List<Customer>listCustomers,List<Customer>customerTemp,String REGEX_Pattern_CUSTOMER_ID,String REGEX_PATTERN){
		String customerId = Validation.validasiInput("CustomerId : ", "Maaf itu bukan customer id bengkel", REGEX_Pattern_CUSTOMER_ID);
		String costumerPassword = Validation.validasiInput("Password : ", "Maaf Password itu bukan",REGEX_PATTERN);
		Customer customer =  Validation.getCustomerbyLogin(customerId,costumerPassword,listCustomers);

		if (customer != null){
			customerTemp.add(customer);
			if(customer instanceof MemberCustomer){
				System.out.println("Selamat datang Mr"+customer.getName());
			}
			else{
				System.out.println("Selamat datang Mr"+customer.getCustomerId());
			}
		}else{
			System.out.println("Login Failed");
		}
		
	}
	//Info Customer
	public static void customerInfo(List<Customer>customerTemp){
		customerTemp.stream()
		// .filter(data ->)
		.forEach(data -> {
			if(data instanceof MemberCustomer){
				System.out.printf("%-15s  %s%n","Customer Id :",data.getCustomerId());
				System.out.printf("%-15s  %s%n","Nama :",data.getName());
				System.out.printf("%-15s  %s%n","Customer status :","Member");
				System.out.printf("%-15s  %s%n","Alamat :",data.getAddress());
				System.out.printf("%-15s  %,.2f %n", "Saldo :",((MemberCustomer)data).getSaldoCoin());
				PrintService.printVechicle(data.getVehicles());
				}
			else{
				System.out.printf("%-15s  %-10s","Customer Id",data.getCustomerId());
				System.out.printf("%-15s  %-10s","Nama",data.getName());
				System.out.printf("%-15s  %-10s","Customer status","Non-member");
				System.out.printf("%-15s  %-10s","Alamat :",data.getAddress());
				PrintService.printVechicle(data.getVehicles());
			}										
		});
	}
	//Booking atau Reservation
	public static void bookingReservation(List<Customer>customerTemp,List<ItemService> listAllItemService,List<BookingOrder>historyBookingOrders,String REGEX_PATTERN,String REGEX_PATTREN_YESNO,String REGEX_PATTREN_SALDO,String REGEX_SERVICE){
		
		List<ItemService>customerOrderList = new ArrayList<>();
		int maxCounter = customerTemp.get(0).getMaxNumberOfService();
		int counter = 0;
		Boolean bookingOrderCondition = true;
		String vehicleId = Validation.validasiInput("Silahkan masukan id kendaraan anda: ", "bukan format service id, periksa kembali ", REGEX_PATTERN);
		
		Vehicle vehicle = Validation.getVehicleById(customerTemp.get(0).getVehicles(), vehicleId);
		
		if(vehicle instanceof Car){
			System.out.println(vehicle.getBrand());
			PrintService.printMenuCar(listAllItemService);
		}else{
			System.out.println(vehicle.getVehiclesId());
			PrintService.printMenuMotorcycle(listAllItemService);
		}

		if(vehicle != null){
			System.out.println("ada");
            while(bookingOrderCondition){
            String itemServiceId = Validation.validasiInput("Silahkan masukan id service untuk kendaraan anda :", "bukan format service ", REGEX_SERVICE);
            ItemService itemService = Validation.getItemServicebyId(listAllItemService, itemServiceId);
        
			if(itemService != null){
                if(!customerOrderList.contains(itemService)){
                customerOrderList.add(itemService);
				counter++;
                }else{
                System.out.println("service sudah ada ");
                }
            }else{
                System.out.println("servis tidak ditemukan");
            }
			

			if (counter != maxCounter){
				String pilihan = Validation.validasiInput("apakah ada lagi yang mau dipilih ya/tidak : ","jawaban hanya ya dan tidak",REGEX_PATTREN_YESNO);

            	if (pilihan.equalsIgnoreCase("tidak")){
            		bookingOrderCondition = false;
            	}
			
			}else{
				bookingOrderCondition = false;
			}
            //melakukan cek ke dalam service list 
            
        }
		String payment = paymentMethod(customerTemp,REGEX_PATTREN_SALDO);

        int number = historyBookingOrders.size() + 1;
		String cusId = customerTemp.get(0).getCustomerId();
		String [] split = cusId.split("-");  
		String bookingId = "Book-Cust-00"+number+"-"+split[1];
        BookingOrder customerOrder = new BookingOrder(
			bookingId,
			customerTemp.get(0),
			customerOrderList,
			payment
			);

        historyBookingOrders.add(customerOrder);
		if (payment.equalsIgnoreCase("Saldo Coin")&&customerTemp.get(0) instanceof MemberCustomer){
			double reduce = ((MemberCustomer)customerTemp.get(0)).getSaldoCoin() - customerOrder.getTotalPayment();
			((MemberCustomer)customerTemp.get(0)).setSaldoCoin(reduce);
			System.out.println("Pembayaran Melalui Salco Coin");
			System.out.println("Reservation object created: " + bookingId+", Booking Service is success");
		}else if(customerTemp.get(0) instanceof Customer){
			System.out.println("Pembayaran lewat Cash segera ke Kasir, Tunjukan Code Booking ");
			System.out.println("Reservation object created: " + bookingId+", Booking Service is success");
		}

		}

	}
	
	public static String paymentMethod(List<Customer>customerTemp,String REGEX_PATTREN_SALDO){
		String paymentMethod = Validation.validasiInput("Masukan metode Pembayaran ", "hanya terdapat Saldo Coin dan Cash",REGEX_PATTREN_SALDO);
		if(customerTemp.get(0) instanceof MemberCustomer){
			return paymentMethod;
		}else{
			return paymentMethod = "Cash";
		}
	}
	//Top Up Saldo Coin Untuk Member Customer
	public static void topUp(List<Customer>customerTemp){
		int money = Validation.validasiNumberWithRange("Masukan nominal topup ","Nilai tidak boleh negatif","^[1-9][0-9]*$",1000000,5000);
		MemberCustomer member = (MemberCustomer)customerTemp.get(0);
		System.out.println(member);
		member.setSaldoCoin((member.getSaldoCoin()) + money);

	}
	//Logout
	public static void logoutProcess(List<Customer>customerTemp){
		customerTemp.clear();
		if(customerTemp.isEmpty()){
			System.out.println("Logout Success");
		}else{
			System.out.println("Logout Failed");
		}
	}
	
}
