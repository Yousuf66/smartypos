package ksa.so.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ksa.so.repositories.CustomerOrderRepository;

@RestController
@RequestMapping("api/dashboard")
public class DashboardDataController {

	
	@Autowired
	CustomerOrderRepository orderRepo;


	@GetMapping("/sales/all")
	double getSalesAlltime() {
	  Double value = orderRepo.getSalesAlltime();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/sales/all/{id}")
	double getSalesAlltime(@PathVariable long id) {
	  Double value = orderRepo.getSalesAlltime(id);
	  return value == null ? 0 : value;
	}



	@GetMapping("/sales/month")
	double getSalesThisMonth() {
	  Double value = orderRepo.getSalesThisMonth();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/sales/month/{id}")
	double getSalesThisMonth(@PathVariable long id) {
	  Double value = orderRepo.getSalesThisMonth(id);
	  return value == null ? 0 : value;
	}



	@GetMapping("/sales/day")
	double getSalesToday() {
	  Double value = orderRepo.getSalesToday();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/sales/day/{id}")
	double getSalesToday(@PathVariable long id) {
	  Double value = orderRepo.getSalesToday(id);
	  return value == null ? 0 : value;
	}


	@GetMapping("/psold/day")
	double getProductsSoldToday() {
	  Double value = orderRepo.getSalesToday();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/psold/day/{id}")
	double getProductsSoldToday(@PathVariable long id) {
	  Double value = orderRepo.getSalesToday(id);
	  return value == null ? 0 : value;
	}
	
	



	@GetMapping("/sales/permonth")
	List<Object> getSalesPerMonth() {
	  return orderRepo.getSalesPerMonth();
	}
	
	@GetMapping("/sales/permonth/{id}")
	List<Object> getSalesPerMonth(@PathVariable long id) {
	  return orderRepo.getSalesPerMonth(id);
	}

	@GetMapping("/salesorders/permonth")
	List<Object> getSalesAndOrderPerMonth() {
	  return orderRepo.getSalesAndOrdersPerMonth();
	}

	@GetMapping("/salesorders/permonth/{id}")
	List<Object> getSalesAndOrderPerMonth(@PathVariable long id) {
	  return orderRepo.getSalesAndOrdersPerMonth(id);
	}


	@GetMapping("/sales/perarea")
	List<Object> getSalesPerArea() {
	  return orderRepo.getSalesPerArea();
	}

	@GetMapping("/sales/perarea/{id}")
	List<Object> getSalesPerArea(@PathVariable long id) {
	  return orderRepo.getSalesPerArea(id);
	}


	@GetMapping("/sales/perstore")
	List<Object> getSalesPerStore() {
	  return orderRepo.getSalesPerStore();
	}
	
	@GetMapping("/sales/perstore/{id}")
	List<Object> getSalesPerStore(@PathVariable long id) {
	  return orderRepo.getSalesPerStore(id);
	}


	@GetMapping("/orders/permonth")
	List<Object> getOrdersPerMonth() {
	  return orderRepo.getOrdersPerMonth();
	}
	
	@GetMapping("/orders/permonth/{id}")
	List<Object> getOrdersPerMonth(@PathVariable long id) {
	  return orderRepo.getOrdersPerMonth(id);
	}



	@GetMapping("/orders/all")
	double getItdOrders() {
	  Double value = orderRepo.getItdOrders();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/all/{id}")
	double getItdOrders(@PathVariable long id) {
	  Double value = orderRepo.getItdOrders(id);
	  return value == null ? 0 : value;
	}




	@GetMapping("/orders/quarter")
	double getQtdOrders() {
	  Double value = orderRepo.getQtdOrders();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/quarter/{id}")
	double getQtdOrders(@PathVariable long id) {
	  Double value = orderRepo.getQtdOrders(id);
	  return value == null ? 0 : value;
	}


	@GetMapping("/orders/month")
	double getMtdOrders() {
	  Double value = orderRepo.getMtdOrders();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/month/{id}")
	double getMtdOrders(@PathVariable long id) {
	  Double value = orderRepo.getMtdOrders(id);
	  return value == null ? 0 : value;
	}

	@GetMapping("/orders/day")
	double getTodaysOrders() {
	  Double value = orderRepo.getTodaysOrders();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/day/{id}")
	double getTodaysOrders(@PathVariable long id) {
	  Double value = orderRepo.getTodaysOrders(id);
	  return value == null ? 0 : value;
	}

	@GetMapping("/orders/avgdaily")
	double getAvgDailyOrders() {
	  Double value = orderRepo.getAvgDailyOrders();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/avgdaily/{id}")
	double getAvgDailyOrders(@PathVariable long id) {
	  Double value = orderRepo.getAvgDailyOrders(id);
	  return value == null ? 0 : value;
	}


	@GetMapping("/orders/avgmonthly")
	double getAvgMonthlyOrders() {
	  Double value = orderRepo.getAvgMonthlyOrders();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/avgmonthly/{id}")
	double getAvgMonthlyOrders(@PathVariable long id) {
	  Double value = orderRepo.getAvgMonthlyOrders(id);
	  return value == null ? 0 : value;
	}

	@GetMapping("/orders/avgquarterly")
	double getAvgQuarterlyOrders() {
	  Double value = orderRepo.getAvgQuarterlyOrders();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/avgquarterly/{id}")
	double getAvgQuarterlyOrders(@PathVariable long id) {
	  Double value = orderRepo.getAvgQuarterlyOrders(id);
	  return value == null ? 0 : value;
	}

	@GetMapping("/orders/avgyearly")
	double getAvgYearlyOrders() {
	  Double value = orderRepo.getAvgYearlyOrders();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/avgyearly/{id}")
	double getAvgYearlyOrders(@PathVariable long id) {
	  Double value = orderRepo.getAvgYearlyOrders(id);
	  return value == null ? 0 : value;
	}



	@GetMapping("/psold/all")
	double getProductsSoldAlltime() {
	  Double value = orderRepo.getProductsSoldAlltime();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/psold/all/{id}")
	double getProductsSoldAlltime(@PathVariable long id) {
	  Double value = orderRepo.getProductsSoldAlltime(id);
	  return value == null ? 0 : value;
	}


	@GetMapping("/psold/quarter")
	double getProductsSoldThisQuarter() {
	  Double value = orderRepo.getProductsSoldThisQuarter();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/psold/quarter/{id}")
	double getProductsSoldThisQuarter(@PathVariable long id) {
	  Double value = orderRepo.getProductsSoldThisQuarter(id);
	  return value == null ? 0 : value;
	}

	@GetMapping("/psold/month")
	double getProductsSoldThisMonth() {
	  Double value = orderRepo.getProductsSoldThisMonth();
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/psold/month/{id}")
	double getProductsSoldThisMonth(@PathVariable long id) {
	  Double value = orderRepo.getProductsSoldThisMonth(id);
	  return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/pending")
	double getNumberOfPendingOrders() {
		Double value = orderRepo.getNumberOfPendingOrders();
		return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/pending/{id}")
	double getNumberOfPendingOrders(@PathVariable long id) {
		Double value = orderRepo.getNumberOfPendingOrders(id);
		return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/lastday")
	double getLastDayOrders() {
		Double value = orderRepo.getLastDayOrders();
		return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/lastday/{id}")
	double getLastDayOrders(@PathVariable long id) {
		Double value = orderRepo.getLastDayOrders(id);
		return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/lastsevendays")
	double getLastSevenDaysOrders() {
		Double value = orderRepo.getLastSevenDaysOrders();
		return value == null ? 0 : value;
	}
	
	@GetMapping("/orders/lastsevendays/{id}")
	double getLastSevenDaysOrders(@PathVariable long id) {
		Double value = orderRepo.getLastSevenDaysOrders(id);
		return value == null ? 0 : value;
	}
	
	//Getting Shipping Rate.
	
		@GetMapping("/orders/shippingrate")
		double getShippingRate() {
			Double totalOrders = orderRepo.getTotalOrders();
			Double shippedOrders = orderRepo.getItdOrders();
			if (totalOrders == null || shippedOrders == null) {
				return 0;
			}
			if (totalOrders == 0) {
				return 0;
			}
			return shippedOrders / totalOrders;
		}
		
		@GetMapping("/orders/shippingrate/{id}")
		double getShippingRate(@PathVariable long id) {
			Double totalOrders = orderRepo.getTotalOrders(id);
			Double shippedOrders = orderRepo.getItdOrders(id);
			if (totalOrders == null || shippedOrders == null) {
				return 0;
			}
			if (totalOrders == 0) {
				return 0;
			}
			return shippedOrders / totalOrders;
		}
		
		//Cancellation Rate
		
		@GetMapping("/orders/cancellationrate")
		double getCancellationRate() {
			Double totalOrders = orderRepo.getTotalOrders();
			Double cancelledOrders = orderRepo.getCancelledOrders();
			if (totalOrders == null || cancelledOrders == null) {
				return 0;
			}
			if (totalOrders == 0) {
				return 0;
			}
			return cancelledOrders / totalOrders;
		}
		
		@GetMapping("/orders/cancellationrate/{id}")
		double getCancellationRate(@PathVariable long id) {
			Double totalOrders = orderRepo.getTotalOrders(id);
			Double cancelledOrders = orderRepo.getCancelledOrders(id);
			if (totalOrders == null || cancelledOrders == null) {
				return 0;
			}
			if (totalOrders == 0) {
				return 0;
			}
			return cancelledOrders / totalOrders;
		}
		
		
		@GetMapping("/branchrating/{id}")
		double getBranchRating(@PathVariable long id) {
			Double rating = orderRepo.getBranchRating(id);
			return rating == null ? 0 : rating;
		}
}
