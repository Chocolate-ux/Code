import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Number of repetitions for each test suite
        int n = 5;  // Adjust this as needed

        List<String> fileNames = new ArrayList<>();
        fileNames.add("cities10.txt");
        //fileNames.add("cities20.txt");
        //fileNames.add("cities30.txt");
        //fileNames.add("cities40.txt");
        //fileNames.add("cities50.txt");

        // Load the test suites from the files
        List<int[][]> testSuites = loadTestSuitesFromFiles(fileNames);

        // CSV file path
        String filePath = "execution_times_and_distances.csv";

        // Run the test suites and save results to a CSV file
        try {
            FileWriter csvWriter = new FileWriter(filePath);
            csvWriter.append("TestSuite,Run,ExecutionTime(ms),ShortestDistance\n");
            for (int suiteIndex = 0; suiteIndex < testSuites.size(); suiteIndex++) {
                int[][] cities = testSuites.get(suiteIndex);
                // Repeat n times for each test suite
                for (int i = 0; i < n; i++) {
                    // Create a new route optimiser instance
                	// REPLACE WITH YOUR OPTIMISER HERE
                	DeliveryRouteOptimiser optimiser = new DeliveryRouteOptimiser(cities);
                	//NearestCityOptimiser optimiser = new NearestCityOptimiser(cities);

                    // Measure execution time
                    long startTime = System.nanoTime();
                    double shortestDistance = optimiser.findShortestRoute();  // Get the shortest distance
                    long endTime = System.nanoTime();

                    // Calculate execution time in milliseconds
                    long executionTime = (endTime - startTime) / 1_000_000;

                    // Write the results to the CSV file
                    csvWriter.append("Suite-" + (suiteIndex + 1));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(i + 1));  // Run number
                    csvWriter.append(",");
                    csvWriter.append(Long.toString(executionTime));  // Execution time in milliseconds
                    csvWriter.append(",");
                    csvWriter.append(Double.toString(shortestDistance));  // Shortest distance
                    csvWriter.append("\n");
                }
            }
            csvWriter.flush();
            csvWriter.close();
            System.out.println("Execution times and distances saved to " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads the test suites from a list of filenames into an ArrayList of 
     * coordinated
     * @param fileNames an ArrayList of filenames/paths
     * @return testSuites an ArrayList of city coordinates
     */
    public static List<int[][]> loadTestSuitesFromFiles(List<String> fileNames) {
        List<int[][]> testSuites = new ArrayList<>();

        // Loop through each file in the provided file list
        for (String fileName : fileNames) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                List<int[]> cityList = new ArrayList<>();
                String line;

                // Read each line from the file
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    cityList.add(new int[] { x, y });  // Add city coordinates to the list
                }
                reader.close();

                // Convert List<int[]> to int[][]
                int[][] citiesArray = new int[cityList.size()][2];
                for (int i = 0; i < cityList.size(); i++) {
                    citiesArray[i] = cityList.get(i);
                }

                testSuites.add(citiesArray);  // Add to the list of test suites

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return testSuites;  // Return all loaded test suites
    }

}
