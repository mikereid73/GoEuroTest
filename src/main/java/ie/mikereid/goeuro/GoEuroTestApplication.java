package ie.mikereid.goeuro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point into service
 * @author Michael Reid
 * 
 */

@SpringBootApplication
public class GoEuroTestApplication {

	/**
	 * mr. main
	 * @param args contains path to bus data file supplied
	 */
	public static void main(String[] args) {		
        if(args.length == 0) {
            System.err.println("Please proivide a bus route data file...");
            System.exit(1);
        }
        if(args.length > 1) {
            System.err.println("Please only proivide a single bus route data file...");
            System.exit(1);
        }
    
		DirectBusRouteController.setBusDataPath(args[0]);
		SpringApplication.run(GoEuroTestApplication.class, args);
	}
}
