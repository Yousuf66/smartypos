package ksa.so.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ksa.so.beans.BranchInventoryPaging;
import ksa.so.beans.InvoiceDto;
import ksa.so.beans.InvoiceItemPojo;
import ksa.so.beans.InvoicePaging;
import ksa.so.beans.InvoicePojo;
import ksa.so.beans.Paging;
import ksa.so.domain.Branch;
import ksa.so.domain.Invoice;
import ksa.so.domain.InvoiceItem;
import ksa.so.domain.Item;
import ksa.so.repositories.BranchRepository;
import ksa.so.repositories.CustomerOrderRepository;
import ksa.so.repositories.InvoiceItemRepository;
import ksa.so.repositories.InvoiceRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.OrderItemRepository;

@RestController
public class InvoiceController {
	
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	CustomerOrderRepository customerOrderRepository;
	@Autowired
	OrderItemRepository orderItemRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired 
	InvoiceItemRepository invoiceItemRepository;
	
	
	@PostMapping("/api/invoice")
	public List<InvoiceItem> handleInvoice(@RequestBody InvoiceDto invoiceDto) {
		List<InvoiceItem> invoiceItemList = new ArrayList<>();
			for(int i=0;i<invoiceDto.getProductsList().size();i++) {
				Item item = new Item();
				InvoiceItem invoiceItem = new InvoiceItem();
				item = itemRepository.findById(invoiceDto.getProductsList().get(i).getId()).get();
				item.setQuantity(item.getQuantity() - invoiceDto.getProductsList().get(i).getQuantity());
				Item savedItem = itemRepository.save(item);
				invoiceItem.setItem(savedItem);
				invoiceItem.setQuantity(savedItem.getQuantity());
				invoiceItem.setBarcode(item.getBarcode());
				invoiceItem.setCost(item.getCost());
				invoiceItem.setDiscountAmount(item.getDiscountAmount());
				invoiceItem.setNetSalePrice(item.getNetSalePrice());
				invoiceItem.setPrice(item.getPrice());
				invoiceItem.setDiscountPercentage(item.getDiscountPercentage());
				invoiceItem.setTitle(item.getItemLanguageList().get(0).getTitle());
				invoiceItem.setItemsId(savedItem.getId());
				invoiceItemRepository.save(invoiceItem);
				
				invoiceItemList.add(invoiceItem);
			}
			Invoice invoice = new Invoice();
			invoice = invoiceDto.getInvoice();
//			 LocalDateTime currentTime = LocalDateTime.now();
			Date date =  new Date();
//			invoice.setBillDate( date);

			Branch branch = branchRepository.getOne(invoiceDto.getStoreId());
			invoice.setBranch(branch);
			invoice.setListOfInvoiceItems(invoiceItemList);
			
			invoiceRepository.save(invoice);
			
		
		return invoiceItemList;
	}
	
	
	
	
//	@PostMapping("/api/branchinventory")
//	public BranchInventoryPaging getBranchInventory(@RequestBody Paging param) {
//		Pageable listing = new PageRequest(param.getPage(), param.getSize(), null);
////		Pageable listing = PageRequest.of(param.getPage(),  param.getSize(), null);
//		
//		BranchInventoryPaging branchInventoryPaging = new BranchInventoryPaging();
//		if(param.getTitle()==("")||param.getTitle() ==null) {
//			branchInventoryPaging.setBranchItems(branchRepository.getBranchInventory(param.getId(),listing));
//			branchInventoryPaging.setTotalRecords(branchRepository.getTotalBranchInventoryRecords(param.getId()));
//		}
//		else {
//			branchInventoryPaging.setBranchItems(branchRepository.findBranchInventoryByTitle(param.getId(),param.getTitle().toLowerCase(), listing));
//			branchInventoryPaging.setTotalRecords(branchRepository.getBranchInventoryTotalRecordsByTitle(param.getId(),param.getTitle().toLowerCase()));
//		} 
//		return branchInventoryPaging;
////		return branchRepository.getBranchInventory(param.getId());
//		
//		
//	}
//	
	@PostMapping("/api/invoicelisting")
	public InvoicePaging getInvoiceListing(@RequestBody Paging param)
	{
		InvoicePaging invoicePaging = new InvoicePaging();
		List<Invoice> invoiceList = new ArrayList<>();
//		List<InvoicePojo> invoicePojo = new ArrayList<>();
		Pageable listing = new PageRequest(param.getPage(), param.getSize(), null);
		if(param.getDate()==null) {
			
			invoiceList =  invoiceRepository.findByBranchId(param.getId(),listing);
			invoicePaging.setInvoiceList(invoiceList);
			invoicePaging.setTotalRecords(invoiceRepository.findByBranchId(param.getId()));
			
		}else {
			invoiceList = invoiceRepository.findByBranchIdAndBillDate(param.getId(),param.getDate(),listing);
			invoicePaging.setInvoiceList(invoiceList);
			invoicePaging.setTotalRecords(invoiceRepository.findByBranchIdAndBillDate(param.getId(),param.getDate()));
			
		}
		return invoicePaging;

	}
	
	
	
	
}
