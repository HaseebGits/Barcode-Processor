import java.util.Scanner;

public class Main {
    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter barcode(numeric digit only):");
        String barcode = scanner.nextLine();
        BarcodeProcessor processor = new BarcodeProcessor();
        String results= processor.process(barcode);
        System.out.println(results);
    }
}
