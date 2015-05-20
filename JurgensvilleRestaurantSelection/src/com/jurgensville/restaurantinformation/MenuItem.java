/**
 * 
 */
package com.jurgensville.restaurantinformation;

import java.util.Set;

/**
 * @author akritim
 *
 */
public class MenuItem {
	private final String itemName;
	private final float price;
	private final Set<String> itemComponentsSet;

	public MenuItem(String itemName, float price, Set<String> itemComponentsSet) {
		this.itemName = itemName;
		this.price = price;
		this.itemComponentsSet = itemComponentsSet;
	}

	public String getItemName() {
		return itemName;
	}

	public float getPrice() {
		return price;
	}

	public Set<String> getItemComponentsSet() {
		return itemComponentsSet;
	}

}
