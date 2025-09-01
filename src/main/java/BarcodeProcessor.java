import java.text.SimpleDateFormat;
import java.util.Date;

public class BarcodeProcessor {
    public String process(String barcode) {
        if (barcode == null || !barcode.startsWith("01") || barcode.length() < 7) {
            return "Alert: Invalid barcode";
        }

        String itemCode = barcode.substring(2, 7); // extract item code
        String rest = barcode.substring(7); // extract rest of the barcode

        ItemModule item = new ItemModule(itemCode);
        Refrigeration refModule = null;// these are optional because they may and may not be present in barcode inputs
        Volume volModule = null;
        ExpiryDate expModule = null;

        int i = 0;
        while (i <= rest.length() - 2) {//I use -2 to make sure at least 2 characters are available, so the code does not crash Make sure at least 2 characters are left to read a prefix safely
            String prefix = rest.substring(i, i + 2);//Get the module prefix like "13", "14", or "15"
            i += 2; //Move the prefix to read module data next

            switch (prefix) {
                case "13":
                    if (i + 1 > rest.length()) {// check we have one more character in barcode
                        return "ALERT: Invalid barcode";
                    }
                    String flag = rest.substring(i, i + 1);// extracting charcter like 131
                    refModule = new Refrigeration(flag);
                    if (!refModule.isValid())
                        return "ALERT: Invalid barcode";
                    i += 1;
                    break;

                case "14":
                    if (i + 6 > rest.length()) { // checking at least six charcters after itemcode
                        return "ALERT: Invalid barcode";
                    }
                    String vol = rest.substring(i, i + 5); // take 5 charcters from i
                    String unit = rest.substring(i + 5, i + 6);// take 6th charcter after reading 5 of volume code module
                    volModule = new Volume(vol, unit);
                    if (!volModule.isValid()){
                        return "ALERT: Invalid barcode";
                    }
                    i += 6; // after all validation of these 6 characters move
                    break;

                case "15":
                    if (i + 6 > rest.length()){
                        return "ALERT: Invalid barcode";
                    }
                    String date = rest.substring(i, i + 6);
                    expModule = new ExpiryDate(date);
                    if (!expModule.isValid()) {
                        return "ALERT: Invalid barcode";
                    }
                    i += 6;
                    break;

                default:
                    return "ALERT: Invalid barcode";
            }
        }



        if (refModule == null && volModule == null && expModule == null) {  // this check is for checking if all module is null in input
            return "INFO: No module information provided with item barcode";
        }

        // Expirydate logic
        if (expModule != null) {  // check for expirymodule is not null
            Date today = new Date();
            Date expiry = expModule.getExpiryDate();

            long diff = (expiry.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);  // difference of number days till today to check expiry date it uses utc 1970 time format in milliseconds
            String type = refModule != null && refModule.needsRefrigeration() ? "refrigerated item" : "item"; // here we see if it refrigerated item or not
            String volInfo = volModule != null ? " " + volModule.getVolume() + " " + volModule.getUnit() : ""; //ternery operation


            if (diff < 0) {  //If a valid barcode with expired date is given, then system should generate the required alert accordingly
                return "ALERT: The " + type + volInfo + " is expired";
            } else if (diff <= 7) {  //If a refrigerated item with valid expiry date is given, then system should behave as expected
                return "WARNING: The " + type + volInfo + " will be expired in " + diff + " days";
            } else {// If a refrigerated item with volume 100 ml will be expired in next 5 days, system should behave accordingly
                return "INFO: The " + type + volInfo + " is not expired";
            }

        }

        return "INFO: The item is not expired";
    }
}
