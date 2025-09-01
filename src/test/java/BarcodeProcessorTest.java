import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BarcodeProcessorTest {

    BarcodeProcessor processor = new BarcodeProcessor();
    @Test
    public void testBarcodeTooShort(){
        String output = processor.process("0111");
        assertEquals("Alert: Invalid barcode",output);
    }
    @Test
    public void testValidItemCodeNoModules(){
        String output = processor.process("0112345");
        assertEquals("INFO: No module information provided with item barcode" ,output);
    }
    @Test
    public void testInvalidRefrigerationValue() {
        String output = processor.process("0112345133");
        assertEquals("ALERT: Invalid barcode", output);
    }
    @Test
    public void testVolumeExpiryNotRefrigeratedExpired() {
        // Volume = 250.00 ml (14 25000 1), expired date 01-02-23 (15 010203)
        String barcode = "01123451425000115010223";
        String output = processor.process(barcode);
        assertEquals("ALERT: The item 250.0 ml is expired", output);
    }

    @Test
    public void testItemNotExpired() {
        // Expiry date far in future
        String barcode = "011234515311203"; // 31 Dec 2003 (not expired)
        String output = processor.process(barcode);
        assertEquals("ALERT: The item is expired", output);
    }
    @Test
    public void testValidVolumeUnit() {
        // Unit code invalid: '4' is not in [1,2,3]
        String barcode = "011234514250001";
        String output = processor.process(barcode);
        assertEquals("INFO: The item is not expired", output);
    }
    @Test
    public void testInvalidVolumeUnit() {
        // Unit code invalid: '4' is not in [1,2,3]
        String barcode = "011234514250004";
        String output = processor.process(barcode);
        assertEquals("ALERT: Invalid barcode", output);
    }
}