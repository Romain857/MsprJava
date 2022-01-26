import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class parseIntEquipement {
    public static void main(String[] args) throws Exception {
        InputStream is = new FileInputStream("C:\\Users\\romai\\Desktop\\TxtGoSecuri\\FicheEquipement\\liste.json");
        Scanner obj = new Scanner(is);
        while (obj.hasNextLine())
            System.out.println(obj.nextLine());
    }
}