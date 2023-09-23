package edu.sdse.csvprocessor;
import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.HashMap;

public class CityCSVProcessor {

	public void readAndProcess(File file) {
		//Try with resource statement (as of Java 8)
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			//Discard header row
			br.readLine();

			HashMap<String, List<CityRecord>> recordsByCity = new HashMap<>();
			List<CityRecord> allRecords = new ArrayList<>();
			String line;

			while ((line = br.readLine()) != null) {
				// Parse each line
				String[] rawValues = line.split(",");

				int id = convertToInt(rawValues[0]);
				int year = convertToInt(rawValues[1]);
				String city = convertToString(rawValues[2]);
				int population = convertToInt(rawValues[3]);

				//System.out.println("id: " + id + ", year: " + year + ", city: " + city + ", population: " + population);


				// We store the data as records and add the records in a list.
				CityRecord record = new CityRecord(id, year, city, population);
				//System.out.println(record);
				allRecords.add(record);

				if (!recordsByCity.containsKey(record.getCity())) {
					recordsByCity.put(record.getCity(), new ArrayList<CityRecord>());
				}
				recordsByCity.get(record.getCity()).add(record);
			}
			for (Entry<String, List<CityRecord>> entry : recordsByCity.entrySet()) {
				String city = entry.getKey();
				List<CityRecord> recordsOfCity = entry.getValue();

				//A) Total number of entries for that city
				int noEntries = recordsOfCity.size();

				int minYear = Integer.MAX_VALUE;
				int maxYear = Integer.MIN_VALUE;
				int populationSum = 0;

				for (CityRecord record : recordsOfCity){
					//B) The minimum year.
					int currYear = record.getYear();
					if (currYear < minYear){
						minYear = currYear;
					}
					//C) The maximum year
					if (currYear > maxYear) {
						maxYear = currYear;
					}
					// D) the average population over that period
					populationSum += record.getPopulation();
				}

				double averagePopulation = populationSum * 1.0 / noEntries;


				System.out.println("Average population of " + city + "(" + minYear + "-" + maxYear + ") : " + averagePopulation);

			}


		}
		catch(Exception e){
		System.err.println("An error occurred:");
		e.printStackTrace();
	}

}
	
	private String cleanRawValue(String rawValue) {
		return rawValue.trim();
	}
	
	private int convertToInt(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		return Integer.parseInt(rawValue);
	}
	
	private String convertToString(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		
		if (rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
			return rawValue.substring(1, rawValue.length() - 1);
		}
		
		return rawValue;
	}
	
	public static final void main(String[] args) {
		CityCSVProcessor reader = new CityCSVProcessor();
		
		File dataDirectory = new File("data/");
		File csvFile = new File(dataDirectory, "Cities.csv");
		
		reader.readAndProcess(csvFile);
	}
}
