/**
 * Created by karthikeyan Yegappan on 13/02/17.
 *
 * Class Name: SalesMessageProcessor
 *      This class contains the main program to run the application.
 *      Requires a test input file containing sales message details.
 *      And a sale object is initiated and called to process each
 *      request. A report is made optional and is included in
 *      this assessment to report the product logs to system console.
 *
 */

// include the package
import SalesMessageProcessor.Sale;

// Required inbuilt packages
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class SalesMessageProcessor {

    public static void main(String[] args) {
        // Initiate the sale object
        Sale sale = new Sale();

        // Read inputs from test file
        try {
            String line;
            BufferedReader inputFile = new BufferedReader(new FileReader("testInput/input.txt"));
            while((line = inputFile.readLine()) != null) {
                // process message for each sale notification
                sale.processNotification(line);

                // Call the report
                // @note: report only generates after every 10th iteration and stops on 50th iteration and pauses for
                // 2 seconds.
                sale.log.report();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}