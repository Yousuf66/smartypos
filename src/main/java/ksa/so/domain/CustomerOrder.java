package ksa.so.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.CancellationReasonLanguageRepository;
import ksa.so.repositories.FlashSaleItemRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;
import ksa.so.repositories.MetaStatusLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;
import ksa.so.repositories.OrderItemRepository;
import ksa.so.repositories.OrderItemUpdatedRepository;
import ksa.so.repositories.OrderStatusLogRepository;
import ksa.so.repositories.WalletRepository;

@Entity

public class CustomerOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String orderNumber;
	private Double actualAmount = (double) 0;
	private Double totalAmount = (double) 0;
	private Double discountedAmount = (double) 0;
	private Double walletAmountUsed = (double) 0;

	@JsonProperty
	@Column(nullable = true)
	private long estimatedTime;

	@Column(columnDefinition = "text")
	private String review = "-";

	private String instructionAudio = "-";

	@Column(columnDefinition = "text")
	private String instructionComment = "-";

	@Column(columnDefinition = "text")
	private String feedback = "-";

	private Long lon = (long) 0;

	private Long lat = (long) 0;

	@Column(nullable = true)
	private boolean isReviewed = false;

	@Column(nullable = true)
	private boolean customerArrived = false;

	@Column(nullable = true)
	private boolean isTakeaway = false;

	private Timestamp pickupTime;

	@ManyToOne
	@JoinColumn(name = "FkRating", nullable = false)
	private MetaRating rating;

	@ManyToOne
	@JoinColumn(name = "FkStatus", nullable = false)
	private MetaStatus status;

	@ManyToOne
	@JoinColumn(name = "FkSubOperator", nullable = true)
	private User subOperator = null;

	@ManyToOne
	@JoinColumn(name = "FkUserAddress", nullable = true)
	private UserAddress address = null;

	@ManyToOne
	@JoinColumn(name = "FkBranch", nullable = false)
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "FkUser", nullable = false)
	private UserApp user;

	@ManyToOne
	@JoinColumn(name = "FkSlot")
	private MetaTime metaTime;

	@ManyToOne
	@JoinColumn(name = "FkCurrency", nullable = false)
	private MetaCurrency currency;

	@ManyToOne
	@JoinColumn(name = "FkCancelResponse")
	private MetaCancellationReason cancellationReason;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItemList;

	@OneToMany(mappedBy = "order")
	private List<OrderItemUpdated> orderItemUpdatedList;

	@OneToMany(mappedBy = "order")
	private List<OrderCombo> orderComboList;

	@OneToMany(mappedBy = "order")
	private List<CustomerOrderHistory> orderHistoryList;

	private Timestamp tsClient;

	private Timestamp tsServer;

	private Timestamp tsUpd;

	public UserAddress getAddress() {
		return address;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public Double getWalletAmountUsed() {
		return walletAmountUsed;
	}

	public void setWalletAmountUsed(Double walletAmountUsed) {
		this.walletAmountUsed = walletAmountUsed;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public void setAddress(UserAddress address) {
		this.address = address;
	}

	public List<CustomerOrderHistory> getOrderHistoryList() {
		return orderHistoryList;
	}

	public void setOrderHistoryList(List<CustomerOrderHistory> orderHistoryList) {
		this.orderHistoryList = orderHistoryList;
	}

	public boolean isCustomerArrived() {
		return customerArrived;
	}

	public void setTakeaway(boolean isTakeaway) {
		this.isTakeaway = isTakeaway;
	}

	public MetaTime getMetaTime() {
		return metaTime;
	}

	public void setMetaTime(MetaTime metaTime) {
		this.metaTime = metaTime;
	}

	public MetaDay getDay() {
		return day;
	}

	public void setDay(MetaDay day) {
		this.day = day;
	}

	@ManyToOne
	@JoinColumn(name = "FkDay")
	private MetaDay day;

	@ManyToOne
	@JoinColumn(name = "FkDiscountCoupon")
	private DiscountCoupon discountCoupon;

	public DiscountCoupon getDiscountCoupon() {
		return discountCoupon;
	}

	public void setDiscountCoupon(DiscountCoupon discountCoupon) {
		this.discountCoupon = discountCoupon;
	}

	@ManyToOne
	@JoinColumn(name = "FkOperator", nullable = false)
	private User operator;

	@ManyToOne
	@JoinColumn(name = "CancelledBy", nullable = true)
	private User cancelledBy;

	public User getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(User cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	public void setLon(Long lon) {
		this.lon = lon;
	}

	public Long getLat() {
		return lat;
	}

	public void setLat(Long lat) {
		this.lat = lat;
	}

	public MetaRating getRating() {
		return rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public void setRating(MetaRating rating) {
		this.rating = rating;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public UserApp getUser() {
		return user;
	}

	public void setUser(UserApp user) {
		this.user = user;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public MetaCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(MetaCurrency currency) {
		this.currency = currency;
	}

	public MetaCancellationReason getCancellationReason() {
		return cancellationReason;
	}

	public void setCancellationReason(MetaCancellationReason cancellationReason) {
		this.cancellationReason = cancellationReason;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemUpdatedList(List<OrderItemUpdated> orderItemUpdatedList) {
		this.orderItemUpdatedList = orderItemUpdatedList;
	}

	public List<OrderItemUpdated> getOrderItemUpdatedList() {
		return orderItemUpdatedList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public Timestamp getTsClient() {
		return tsClient;
	}

	public Timestamp getTsServer() {
		return tsServer;
	}

	public void setTsServer(Timestamp tsServer) {
		this.tsServer = tsServer;
	}

	public Timestamp getTsUpd() {
		return tsUpd;
	}

	public void setTsUpd(Timestamp tsUpd) {
		this.tsUpd = tsUpd;
	}

	public List<OrderCombo> getOrderComboList() {
		return orderComboList;
	}

	public void setOrderComboList(List<OrderCombo> orderComboList) {
		this.orderComboList = orderComboList;
	}

	public long getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(long estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public boolean isReviewed() {
		return isReviewed;
	}

	public void setReviewed(boolean isReviewed) {
		this.isReviewed = isReviewed;
	}

	public boolean hasCustomerArrived() {
		return customerArrived;
	}

	public void setCustomerArrived(boolean customerArrived) {
		this.customerArrived = customerArrived;
	}

	public boolean getIsTakeaway() {
		return isTakeaway;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getInstructionComment() {
		return instructionComment;
	}

	public void setInstructionComment(String instructionComment) {
		this.instructionComment = instructionComment;
	}

	public String getInstructionAudio() {
		return instructionAudio;
	}

	public void setInstructionAudio(String instructionAudio) {
		this.instructionAudio = instructionAudio;
	}

	public User getSubOperator() {
		return subOperator;
	}

	public UserAddress getUserAddress() {
		return address;
	}

	public void setUserAddress(UserAddress address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setTsClient(Timestamp tsClient) {
		this.tsClient = tsClient;
	}

	public Timestamp getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Timestamp pickupTime) {
		this.pickupTime = pickupTime;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getReview() {
		return review;
	}

	public void setSubOperator(User subOperator) {
		this.subOperator = subOperator;
	}

	public Boolean getIsReviewed() {
		return isReviewed;
	}

	public void setIsReviewed(Boolean isReviewed) {
		this.isReviewed = isReviewed;
	}

	public Long getLon() {
		return lon;
	}

	public void setIsTakeaway(boolean isTakeaway) {
		this.isTakeaway = isTakeaway;
	}

	public JSONObject getInfo(MetaLanguage language, MetaStatusRepository statusRepository,
			MetaStatusLanguageRepository statusLanguageRepository,
			// ComboLanguageRepository comboLanguageRepository,
			// OrderItemSubOptRepository orderItemSubOptRepository,
			// SubOptLanguageRepository subOptLanguageRepository,
			// ItemOptSubOptRepository itemOptSubOptRepository,
			ItemLanguageRepository itemLanguageRepository, BranchLanguageRepository branchLanguageRepository,
			MetaDayLanguageRepository dayLanguageRepository, boolean getItemInfoList, boolean getOrderStatusLog,
			OrderStatusLogRepository orderStatusLogRepository, OrderItemRepository orderItemRepository,
			boolean getUpdatedItemInfoList, OrderItemUpdatedRepository orderItemUpdatedRepository,
			ItemRepository itemRepository, LibraryCategoryLanguageRepository libraryCategoryLanguageRepository,
			CancellationReasonLanguageRepository cancellationReasonLanguageRepository,
			WalletRepository walletRepository, Boolean isFlashSale, FlashSale flashSale,
			FlashSaleItemRepository flashSaleItemRepository) {

		JSONObject json = new JSONObject();

		json.put("id", this.getId());
		json.put("orderNumber", this.getOrderNumber());
		json.put("review", this.getReview());
		json.put("isReviewed", this.getIsReviewed());
		json.put("ratingInfo", this.getRating().getInfo());
		json.put("rateMe", true);
		json.put("walletAmount", this.getWalletAmountUsed());
		json.put("iAmHere", this.hasCustomerArrived());
		json.put("timePlaced", this.getTsClient());
		json.put("pickupType", this.getIsTakeaway() ? "Takeaway" : "Delivery");
		json.put("statusInfo", this.getStatus().getInfo(language, statusLanguageRepository));
		json.put("currencyInfo", this.getCurrency().getInfo());
		json.put("instructionComment", this.getInstructionComment());
		json.put("instructionAudio", this.getInstructionAudio());
		json.put("totalPrice", this.getTotalAmount());
		json.put("actualPrice", this.getActualAmount());
		json.put("discountedPrice", this.getDiscountedAmount());
		json.put("timeSlots", this.getMetaTime().getInfo());
		json.put("day", this.getDay().getCode());
		if (this.getUserAddress() != null) {
			json.put("userAddress", this.getUserAddress().getInfo());
		} else {
			json.put("userAddress", "-");
		}

		Wallet wallet = walletRepository.findByUser(this.getUser());
		json.put("walletAmountUser", wallet.getWalletAmount());
		if (this.getSubOperator() != null)
			json.put("subOperatorInfo", this.getSubOperator().getInfo());
		json.put("userInfo", this.getUser().getInfo());
		json.put("branchInfo", this.getBranch().getBasicInfo(language, branchLanguageRepository, itemRepository,
				libraryCategoryLanguageRepository, false));
		json.put("feedback", this.getFeedback());
		if (this.getCancellationReason() != null)
			json.put("cancelMessage",
					this.getCancellationReason().getInfo(language, cancellationReasonLanguageRepository));

		if (getOrderStatusLog) {
			// order status log
			List<OrderStatusLog> orderLogList = orderStatusLogRepository.findByOrderOrderByTsClientAsc(this);
			JSONArray jsonOrderLog = new JSONArray();
			for (OrderStatusLog orderLog : orderLogList) {
				jsonOrderLog.put(orderLog.getInfo(language, statusLanguageRepository));
			}

			json.put("orderStatusLog", jsonOrderLog);
		}

		// json.put("totalPrice", orderItemRepository.getOrderTotalPrice(this.getId()));

		Timestamp currentTime = statusRepository.getCurrentTime();

		long remainingTime = TimeUnit.MILLISECONDS.toMinutes(
				(this.getTsServer().getTime() + (this.getEstimatedTime() * 60 * 1000)) - currentTime.getTime());
		if (remainingTime < 0) {
			remainingTime = 0;
		}
		json.put("remainingTime", remainingTime);

		long elapsedTime = TimeUnit.MILLISECONDS.toMinutes(currentTime.getTime() - this.getTsServer().getTime());
		if (elapsedTime < 0) {
			elapsedTime = 0;
		}
		json.put("elapsedTime", elapsedTime);

		double price = 0.0;
		double updatedPrice = 0;

		if (getItemInfoList) {
			JSONArray itemInfoList = new JSONArray();
			for (OrderItem orderItem : this.getOrderItemList()) {
				price = price + orderItem.getPrice() * orderItem.getQuantity();

				JSONObject itemInfo = orderItem.getItem().getInfo(language, itemLanguageRepository,
						branchLanguageRepository, dayLanguageRepository, libraryCategoryLanguageRepository,
						itemRepository, isFlashSale, flashSale, flashSaleItemRepository, false);

				itemInfo.put("price", orderItem.getPrice());
				itemInfo.put("quntity", orderItem.getQuantity());

				if (getUpdatedItemInfoList) {
					OrderItemUpdated orderItemUpdated = orderItemUpdatedRepository.findByOrderAndItem(this,
							orderItem.getItem());
					updatedPrice = updatedPrice + orderItemUpdated.getPrice() * orderItemUpdated.getQuantity();
					itemInfo.put("updatedQuantity", orderItemUpdated.getQuantity());
					itemInfo.put("isAvailable", orderItemUpdated.getIsAvailable());
				}
				itemInfoList.put(itemInfo);
			}
			json.put("itemInfoList", itemInfoList);
		}

		json.put("price", price);
		json.put("updatedPrice", updatedPrice);

		return json;
	}

	public CustomerOrder() {
	}

	public CustomerOrder(long estimatedTime, Timestamp tsClient, Timestamp tsServer, Branch branch, MetaRating rating,
			MetaStatus status, UserApp user, long lat, long lon, String orderNumber, Boolean isTakeaway, User operator,
			Timestamp pickupTime, String instructionAudio, String instructionComment, UserAddress address,
			double actualAmount, double totalAmount, double discountedAmount, DiscountCoupon discountCoupon,
			MetaDay day, MetaTime metaTime, double walletAmount) {
		this.setBranch(branch);
		this.setStatus(status);
		this.setRating(rating);
		this.setUser(user);
		this.setOperator(operator);
		this.setDay(day);
		this.setMetaTime(metaTime);
		this.setCurrency(branch.getCurrency());
		this.setLat(lat);
		this.setLon(lon);
		this.setOrderNumber(orderNumber);
		this.setReview("-");
		this.setFeedback("-");
		this.setTsClient(tsClient);
		this.setTsServer(tsServer);
		this.setTsUpd(tsServer);
		this.setEstimatedTime(estimatedTime);
		this.setReviewed(false);
		this.setCustomerArrived(false);
		this.setIsTakeaway(isTakeaway);
		this.setPickupTime(pickupTime);
		this.setInstructionAudio(instructionAudio);
		this.setInstructionComment(instructionComment);
		this.setUserAddress(address);
		this.setActualAmount(actualAmount);
		this.setTotalAmount(totalAmount);
		this.setDiscountedAmount(discountedAmount);
		this.setDiscountCoupon(discountCoupon);
		this.setWalletAmountUsed(walletAmount);
	}
}
