package ie.mikereid.goeuro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Represents the bus route data file
 *
 * @author Michael Reid
 */
public class BusRouteDataFile {

    /* a bus route has 1 id and a minimum of 2 stop id's */
    private static final int MIN_LINE_TOKENS = 3;

    /* Number of bus routes contained in data file */
    private int numberOfRoutes;

    /* List of all bus routes in data file */
    private List<BusRoute> busRoutes;

    /**
     * C'tor
     *
     * @param dataFilePath path to data file
     */
    public BusRouteDataFile(String dataFilePath) {
        this.busRoutes = new LinkedList<>();
        this.busRoutes = parseDataFile(dataFilePath);
    }

    /**
     * Parse the data file and store all information.
     *
     * @param dataFilePath path to data file
     */
    private List<BusRoute> parseDataFile(String dataFilePath) {
        // no one likes magic... spaces
        final String SEPERATOR = " ";

        final List<BusRoute> parsedBusRoutes = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dataFilePath))) {

            // read first line to get number of routes contained in data file
            String line = br.readLine();
            this.numberOfRoutes = Integer.parseInt(line);

            // Parse the file!
            while ((line = br.readLine()) != null) {

                final String[] tokens = line.split(SEPERATOR);
                if (tokens == null || tokens.length < MIN_LINE_TOKENS) {
                    throw new IllegalArgumentException("Bus route must have 1 route id and at least 2 stop id's. file: " + dataFilePath);
                }

                // first int is route id
                final int id = Integer.parseInt(tokens[0]);

                // unique id's on route so a set makes better sense than a list,
                // however it assumes a check for duplicates has been done already
                final Set<Integer> idsOnRoute = new HashSet<>();
                // start at 1 as 0 is route id
                for (int i = 1; i < tokens.length; i++) {
                    idsOnRoute.add(Integer.parseInt(tokens[i]));
                }
                parsedBusRoutes.add(new BusRoute(id, idsOnRoute));

            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return parsedBusRoutes;
    }

    public int getNumberOfRoutes() {
        return this.numberOfRoutes;
    }

    public List<BusRoute> getBusRoutes() {
        return this.busRoutes;
    }

}
