import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class parseIntAgent {
    public static void main(String[] args) throws Exception {
        InputStream ins = new FileInputStream("C:\\Users\\romai\\Desktop\\TxtGoSecuri\\FicheAgent\\cberthier.json");
        Scanner obj = new Scanner(ins);
        while (obj.hasNextLine())
            System.out.println(obj.nextLine());
    }
}