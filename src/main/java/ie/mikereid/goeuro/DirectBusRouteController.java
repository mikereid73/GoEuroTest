package ie.mikereid.goeuro;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles the API requests for this test
 *
 * @author Michael Reid
 */

@RestController
public class DirectBusRouteController {

    /*  depart station id JSON object key */
    private static final String DEPART_SID = "dep_sid";
    /* arrive station id JSON object key */
    private static final String ARRIVE_SID = "arr_sid";
    /* direct bus route JSON object key */
    private static final String DIRECT_BUS_ROUTE = "direct_bus_route";
    /* path to bus data file supplied as cmd arg */
    private static String BUS_DATA_PATH;

    public static void setBusDataPath(String path) {
        BUS_DATA_PATH = path;
    }

    /**
     * This version represents the path in the updated test, and uses
     * RequestParam instead of PathVariable
     *
     * @param depSID depart station id
     * @param arrSID arrive station id
     * @return JSON of result
     */
    @RequestMapping(value = "/api/direct", method = RequestMethod.GET)
    public String checkDirectRoute(
            @RequestParam(value = "dep_sid", required = true) final int depSID,
            @RequestParam(value = "arr_sid", required = true) final int arrSID
    ) {

        boolean directBusRoute = checkDirectBusRoute(depSID, arrSID);

        JSONObject resultJSON = new JSONObject();
        resultJSON.put(DEPART_SID, depSID);
        resultJSON.put(ARRIVE_SID, arrSID);
        resultJSON.put(DIRECT_BUS_ROUTE, directBusRoute);
        return resultJSON.toString();
    }

    /**
     * This version represents the path in the original test, and uses
     * PathVariable instead of RequestParam
     *
     * @param depSID depart station id
     * @param arrSID arrive station id
     * @return JSON of result
     */
    @RequestMapping(value = "/rest/provider/goeurobus/direct/{dep_sid}/{arr_sid}", method = RequestMethod.GET)
    public String checkDirectRouteAlt(
            @PathVariable(value = "dep_sid") final int depSID,
            @PathVariable(value = "arr_sid") final int arrSID
    ) {

        // Check if both id's are on same route
        boolean directBusRoute = checkDirectBusRoute(depSID, arrSID);

        final JSONObject resultJSON = new JSONObject();
        resultJSON.put(DEPART_SID, depSID);
        resultJSON.put(ARRIVE_SID, arrSID);
        resultJSON.put(DIRECT_BUS_ROUTE, directBusRoute);
        return resultJSON.toString();
    }

    /**
     * Checks if the supplied departure and arrival station id's are on a single route
     *
     * @param depSID depart station id
     * @param arrSID arrive station id
     * @return true/false if both id's are on same bus route
     */
    private boolean checkDirectBusRoute(int depSID, int arrSID) {
        List<BusRoute> busRoutes = getBusProviderRoutes();
        for (BusRoute current : busRoutes) {
            if (current.containsIds(depSID, arrSID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Load in the bus data file and parse the routes from it
     *
     * @return List of all bus routes
     */
    private List<BusRoute> getBusProviderRoutes() {
        final BusRouteDataFile busRouteDataFile = new BusRouteDataFile(BUS_DATA_PATH);
        return busRouteDataFile.getBusRoutes();
    }
}
