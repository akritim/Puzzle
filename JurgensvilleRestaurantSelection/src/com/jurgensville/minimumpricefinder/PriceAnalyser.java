/**
 * 
 */
package com.jurgensville.minimumpricefinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.jurgensville.restaurantinformation.RestaurantInformation;

/**
 * @author akritim
 *
 */
public class PriceAnalyser {

	public String findMinimumPriceOption(
			Map<Integer, RestaurantInformation> restaurants,
			Set<String> requestedFoodItems) {
		Iterator<RestaurantInformation> iterator = restaurants.values()
				.iterator();
		ArrayList<ArrayList<Float>> priceOptions = new ArrayList<ArrayList<Float>>();
		ArrayList<Float> priceOptionDetails;
		RestaurantInformation restaurant;
		while (iterator.hasNext()) {
			restaurant = iterator.next();
			if (restaurant.hasAllRequestedFoodItems(requestedFoodItems)) {
				priceOptionDetails = new ArrayList<Float>();
				priceOptionDetails.add((float) restaurant.getRestaurant_id());
				priceOptionDetails.addAll(restaurant
						.getMinPriceForRequestedFoodItems(requestedFoodItems));
				priceOptions.add(priceOptionDetails);
			}

		}
		if (priceOptions.size() == 0) {
			return "Nil";
		} else {
			float minPrice = 0;
			int totalItemCount = 0;
			int restaurant_id = 0;
			float currentMinPrice = 0;
			for (ArrayList<Float> priceOption : priceOptions) {
				currentMinPrice = priceOption.get(1);
				if (minPrice == 0 || currentMinPrice < minPrice) {
					minPrice = currentMinPrice;
					totalItemCount = priceOption.get(2).intValue();
					restaurant_id = priceOption.get(0).intValue();
				} else if (minPrice == currentMinPrice) {
					if (priceOption.get(2).intValue() > totalItemCount) {
						minPrice = currentMinPrice;
						totalItemCount = priceOption.get(2).intValue();
						restaurant_id = priceOption.get(0).intValue();
					}
				}
			}
			return restaurant_id + " " + minPrice;
		}
	}
}
