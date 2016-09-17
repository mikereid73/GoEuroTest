package ie.mikereid.goeuro;

import java.util.Set;
import java.util.StringJoiner;

/**
 * Represents a single bus route and the station id's it contains
 *
 * @author Michael Reid
 */
public class BusRoute {

    /* bus route unique id */
    private int id;

    /* set of all id's on bus route */
    private Set<Integer> idsOnRoute;

    /**
     * C'tor
     *
     * @param id         bus route id
     * @param idsOnRoute bus route station id's
     */
    public BusRoute(int id, Set<Integer> idsOnRoute) {
        this.id = id;
        this.idsOnRoute = idsOnRoute;
    }

    /**
     * Check that we are not going from a station to the same station.
     * Check if both id's are on the route
     *
     * @param depSID depart station id
     * @param arrSID arrive station id
     * @return true/false if this route contains both id's
     */
    public boolean containsIds(int depSID, int arrSID) {
        return depSID != arrSID &&
                this.idsOnRoute.contains(depSID) && this.idsOnRoute.contains(arrSID);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", " [", "]");
        for (Integer i : this.idsOnRoute) {
            sj.add(Integer.toString(i));
        }

        return "id: " + this.id + sj.toString();
    }
}
