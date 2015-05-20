/**
 * 
 */
package com.jurgensville.csvreaderandparser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jurgensville.restaurantinformation.MenuItem;
import com.jurgensville.restaurantinformation.RestaurantInformation;
import au.com.bytecode.opencsv.CSVReader;

/**
 * @author akritim
 *
 */
public class CsvFileReaderAndParser {

	public static Map<Integer, RestaurantInformation> parseContent(
			String csvFilename) {
		CSVReader csvReader = null;
		List<String[]> allRows = new ArrayList<String[]>();
		Map<Integer, RestaurantInformation> restaurants = new HashMap<Integer, RestaurantInformation>();
		try {
			csvReader = new CSVReader(new FileReader(csvFilename));
			allRows = csvReader.readAll();

			Integer restaurantId;

			for (String[] row : allRows) {
				restaurantId = Integer.parseInt(row[0]);

				RestaurantInformation restaraunt;
				if (!restaurants.containsKey(restaurantId)) {
					restaurants.put(restaurantId, new RestaurantInformation(
							restaurantId));
				}
				restaraunt = restaurants.get(restaurantId);

				float menuItemPrice = Float.parseFloat(row[1]);

				Set<String> menuItemComponents = new HashSet<String>();
				String menuItemName = "";

				for (int i = 2; i < row.length; i++) {
					if (!row[i].trim().isEmpty()) {
						menuItemComponents.add(row[i].trim());
						menuItemName = menuItemName.concat(row[i].trim());
					}
				}
				MenuItem menuItem = new MenuItem(menuItemName, menuItemPrice,
						menuItemComponents);
				restaraunt.addItemToMenu(menuItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return restaurants;
	}
}