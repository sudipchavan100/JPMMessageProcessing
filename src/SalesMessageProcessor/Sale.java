package SalesMessageProcessor;

import SalesMessageProcessor.AdjustPrice;
import SalesMessageProcessor.MessageParser;
import SalesMessageProcessor.Product;
import SalesMessageProcessor.SaleLog;

/**
 * Created by karthikeyan Yegappan on 13/02/17.
 *
 * Class Name: Sale
 *      Sale class processes each product sales and appends them in a sales log.
 *      Uses a message parser to parse the incoming messages and collects the
 *      product information and prepares a product object for sale processing.
 *      Ignores processing of any invalid messages and processes until it reaches the
 *      log report limit.
 */
public class Sale {

    // public field log to store messages and product details
    public SaleLog log;

    // Messageparser helps to parse the incoming messages and obtain product sale information.
    private MessageParser messageParser;

    // Adjustment of product price is handled within this object e.g. add 20p, subtract 20p, etc.
    private AdjustPrice adjustPrice;

    // This holds the product details
    private Product product;


    // Constructor
    public Sale() {
        log = new SaleLog();
    }

    // Process notices and appends product information to the relevant product in the
    // salelog.
    // @return [Boolean] false on empty string and invalid message and true on the rest.
    public boolean processNotification(String saleNotice) {
        // Proces the given message
        this.messageParser = new MessageParser(saleNotice);

        // Get the product type e.g 'apple'
        String productType = messageParser.getProductType();

        // Check if product type is empty return false and do nothing.
        if (productType.isEmpty()) {
            return false;
        }

        //Returns an existing product else returns a new Product object
        this.product = log.getProduct(productType);

        // Prepare the product details for adjustment
        this.adjustPrice = new AdjustPrice(product);

        // Set the product details from the parsed message
        this.product.setProductQuantity(messageParser.getProductQuantity());
        this.product.setTotalQuantity(messageParser.getProductQuantity());
        this.product.setProductPrice(messageParser.getProductPrice());
        this.product.setAdjustmentOperator(messageParser.getOperatorType());

        // Set the total value of the product.
        setProductTotalPrice();

        // Set the sale log reports
        log.setNormalReports(saleNotice);

        // Update the product with the new details
        log.updateProduct(product);

        return true;
    }

    // Set or Append Total product price based on any adjustment if given.
    // Also appends the log for adjustments made.
    private void setProductTotalPrice() {
        double adjustedPrice;
        double productValue;

        if (!product.getAdjustmentOperator().isEmpty()) {
            adjustedPrice = adjustPrice.getAdjustedPrice();
            log.setAdjustmentReports(adjustPrice.adjustmentReport());
            product.setTotalPrice(adjustedPrice);
        } else {
            productValue = product.calculatePrice(product.getProductQuantity(), product.getProductPrice());
            product.appendTotalPrice(productValue);
        }
    }

}