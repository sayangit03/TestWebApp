package com.spring.microservices.bean;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Shop {

	@Id
	int shopId;
	String shopName;
	String shopOwnerName;
	ShopAddress shopAddress;
	List<ShopProduct> shopProductList;
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopOwnerName() {
		return shopOwnerName;
	}
	public void setShopOwnerName(String shopOwnerName) {
		this.shopOwnerName = shopOwnerName;
	}
	public ShopAddress getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}
	public List<ShopProduct> getShopProductList() {
		return shopProductList;
	}
	public void setShopProductList(List<ShopProduct> shopProductList) {
		this.shopProductList = shopProductList;
	}
}
