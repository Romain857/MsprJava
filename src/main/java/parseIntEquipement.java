import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class parseIntEquipement {
    public static void main(String[] args) throws Exception {
        System.out.println("*********** Fiche Equipement **********");
        String file = "C:\\Users\\romai\\Desktop\\TxtGoSecuri\\FicheEquipements\\liste.json";
        List<String> list = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + 1 + " : " + list.get(i));
            }
        } catch (IOException e) {
            System.out.println("Rien dans le txt");
            e.printStackTrace();
        }

        System.out.println("*********** Fiche Agent **********");
        String fileS = "C:\\Users\\romai\\Desktop\\TxtGoSecuri\\FichesAgents\\cberthier.json";
        List<String> listS = new ArrayList<String>();
        List<String> tabList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileS))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                listS.add(line);
                String[] tab = line.split(" ");
                for (int j = 0; j < tab.length; j++) {
                    System.out.println("Premier mot : " + tab[0]);
                }
                tabList = Arrays.asList(tab);
            }
            for (int i = 5; i < listS.size(); i++) {
                System.out.println(i + 1 + " : " + listS.get(i));
            }
        } catch (IOException e) {
            System.out.println("Rien dans le txt");
            e.printStackTrace();
        }
        if (listS.equals(list)) {
            System.out.println("Same");
        } else {
            System.out.println("Des agents n'ont pas inscris leur liste agents");
            List<String> listCompare = new ArrayList<String>(list);
            for (int i = 0; i < list.size(); i++) {
                listCompare.retainAll(tabList);
            }
        }
    }
}