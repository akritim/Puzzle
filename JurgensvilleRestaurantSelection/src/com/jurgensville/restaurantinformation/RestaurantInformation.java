/**
 * 
 */
package com.jurgensville.restaurantinformation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author akritim
 *
 */
public class RestaurantInformation {
	private final int restaurant_id;
	private Set<MenuItem> menu = new HashSet<MenuItem>();

	/**
	 * @param restaurant_id
	 * @param menu
	 * @param hasValueMeals
	 */
	public RestaurantInformation(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public Set<MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(Set<MenuItem> menu) {
		this.menu = menu;
	}

	public int getRestaurant_id() {
		return restaurant_id;
	}

	public void addItemToMenu(MenuItem menuItem) {
		menu.add(menuItem);
	}

	public boolean hasAllRequestedFoodItems(Set<String> requestedFoodItems) {
		return hasRequestedFoodItems(requestedFoodItems, menu);
	}

	public boolean hasRequestedFoodItems(Set<String> requestedFoodItems,
			Set<MenuItem> menuItems) {
		for (String requestedFoodItem : requestedFoodItems) {
			if (!hasRequestedFoodItem(requestedFoodItem, menuItems)) {
				return false;
			}
		}
		return true;
	}

	public boolean hasRequestedFoodItem(String requestedFoodItem,
			Set<MenuItem> menuItems) {
		for (MenuItem menuItem : menuItems) {
			if (menuItem.getItemComponentsSet().contains(requestedFoodItem)) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<ArrayList<MenuItem>> menuItemCombinations(
			ArrayList<MenuItem> menuItems) {
		ArrayList<ArrayList<MenuItem>> subsetCollection = new ArrayList<ArrayList<MenuItem>>();
		if (menuItems.size() == 0) {
			subsetCollection.add(new ArrayList<MenuItem>());
		} else {
			ArrayList<MenuItem> reducedSet = new ArrayList<MenuItem>();
			reducedSet.addAll(menuItems);
			MenuItem firstMenuItem = reducedSet.remove(0);
			ArrayList<ArrayList<MenuItem>> subsets = menuItemCombinations(reducedSet);
			subsetCollection.addAll(subsets);
			subsets = menuItemCombinations(reducedSet);
			for (ArrayList<MenuItem> subset : subsets) {
				subset.add(0, firstMenuItem);
			}
			subsetCollection.addAll(subsets);
		}
		return subsetCollection;
	}

	public ArrayList<Float> getMinPriceForRequestedFoodItems(
			Set<String> requestedFoodItems) {
		ArrayList<Float> minPriceSelectionDetails = new ArrayList<Float>();
		Set<MenuItem> menuItems = new HashSet<MenuItem>();
		for (String requestedFoodItem : requestedFoodItems) {
			for (MenuItem menuItem : menu) {
				if (menuItem.getItemComponentsSet().contains(requestedFoodItem)) {
					menuItems.add(menuItem);
				}
			}
		}
		ArrayList<ArrayList<MenuItem>> selectedMenuItemsCombinations = menuItemCombinations(new ArrayList<MenuItem>(
				menuItems));
		minPriceSelectionDetails = getMinPrice(selectedMenuItemsCombinations,
				requestedFoodItems);
		return minPriceSelectionDetails;
	}

	private ArrayList<Float> getMinPrice(
			ArrayList<ArrayList<MenuItem>> selectedMenuItemsCombinations,
			Set<String> requestedFoodItems) {
		ArrayList<Float> minPriceCombinationDetails = new ArrayList<Float>();
		float minPrice = 0;
		int totalFoodItemsCount = 0;
		for (ArrayList<MenuItem> menuItemCombination : selectedMenuItemsCombinations) {
			float currentMenuItemCombinationPrice = 0;
			int foodItemCount = 0;
			if (hasRequestedFoodItems(requestedFoodItems,
					new HashSet<MenuItem>(menuItemCombination))) {
				for (MenuItem menuItem : menuItemCombination) {
					currentMenuItemCombinationPrice += menuItem.getPrice();
					foodItemCount += menuItem.getItemComponentsSet().size();
				}
				if (minPrice == 0 || currentMenuItemCombinationPrice < minPrice) {
					minPrice = currentMenuItemCombinationPrice;
					totalFoodItemsCount = foodItemCount;
				}
			}
		}
		minPriceCombinationDetails.add(minPrice);
		minPriceCombinationDetails.add((float) totalFoodItemsCount);
		return minPriceCombinationDetails;
	}
}