import java.util.Arrays;
public class NearestCityOptimiser {

    private CityList route;  // The linked list of cities forming the route
    private boolean[] visited;  // Array to track visited cities
    private double[][] distanceMatrix;  // Pre-computed distance between cities

    // Constructor initialises the optimiser with city coordinates
    public NearestCityOptimiser(int[][] coordinates) {
        this.distanceMatrix = calculateDistanceMatrix(coordinates); // Create distance matrix
        this.visited = new boolean[coordinates.length]; // To track if a city is visited
        this.route = new CityList();  // Initialise the route as an empty CityList
    }

    // Method to calculate distance between each pair of cities
    private double[][] calculateDistanceMatrix(int[][] coordinates) {
        int n = coordinates.length;
        double[][] distances = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distances[i][j] = Math.sqrt(Math.pow(coordinates[i][0] - coordinates[j][0], 2)
                                          + Math.pow(coordinates[i][1] - coordinates[j][1], 2));
            }
        }
        return distances;
    }

    // Method to find the nearest unvisited city from the current city
    private int findNearestCity(int currentCity) {
        double shortestDistance = Double.MAX_VALUE;
        int nearestCity = -1;
        
        for (int i = 0; i < distanceMatrix.length; i++) {
            if (!visited[i] && i != currentCity) {
                double distance = distanceMatrix[currentCity][i];
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestCity = i;
                }
            }
        }
        return nearestCity;
    }

    // Main method to build the route using the nearest city heuristic
    public void buildRoute(int startCity) {
        // Reset visited array and initialise route with the starting city
        Arrays.fill(visited, false);
        visited[startCity] = true;
        route.add(startCity);  // Add the starting city to the route
        
        int currentCity = startCity;  // Start at the specified city
        
        // Continue finding the nearest unvisited city and adding it to the route
        for (int i = 1; i < visited.length; i++) {
            int nearestCity = findNearestCity(currentCity);
            if (nearestCity != -1) {
                visited[nearestCity] = true;  // Mark the nearest city as visited
                route.add(nearestCity);  // Add the nearest city to the route
                currentCity = nearestCity;  // Move to the newly added city
            }
        }
    }

    // Print the current route
    public void printRoute() {
        for (int i = 0; i < route.size(); i++) {
            System.out.print(route.get(i) + " -> ");
        }
        System.out.println("Start");
    }

    // Calculate the total distance of the current route
    public double calculateTotalDistance() {
        double totalDistance = 0;

        // Sum the distances between consecutive cities in the route
        for (int i = 0; i < route.size() - 1; i++) {
            totalDistance += distanceMatrix[route.get(i)][route.get(i + 1)];
        }

        // Add the distance to return to the start city
        if (route.size() > 1) {
            totalDistance += distanceMatrix[route.get(route.size() - 1)][route.get(0)];
        }

        return totalDistance;
    }
    
    public double findShortestRoute() {
    	buildRoute(0);
    	return calculateTotalDistance();
    }

    
}
