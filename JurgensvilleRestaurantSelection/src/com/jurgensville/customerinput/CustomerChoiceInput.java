/**
 * 
 */
package com.jurgensville.customerinput;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jurgensville.csvreaderandparser.CsvFileReaderAndParser;
import com.jurgensville.minimumpricefinder.PriceAnalyser;
import com.jurgensville.restaurantinformation.RestaurantInformation;

/**
 * @author akritim
 *
 */
public class CustomerChoiceInput {

	/**
	 * @param args
	 *            arg[0] - csv file path arg[1...args.length] - requested food
	 *            items
	 */
	public static void main(String[] args) {
		PriceAnalyser priceAnalyser = new PriceAnalyser();
		String csvFilePath = args[0];
		Set<String> requestedFoodItems = new HashSet<String>();
		for (String requestedFoodItem : (String[]) Arrays.copyOfRange(args, 1,
				args.length)) {
			requestedFoodItems.add(requestedFoodItem);
		}
		if (requestedFoodItems.size() == 0) {
			System.out.println("Nil");
		} else {
			Map<Integer, RestaurantInformation> restaurantsmap = CsvFileReaderAndParser
					.parseContent(csvFilePath);
			System.out.println(priceAnalyser.findMinimumPriceOption(
					restaurantsmap, requestedFoodItems));
		}
	}

}
