import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class listeAgents {
    public static void main(String[] args) throws Exception{

        System.out.println("*********** Liste de fichiers **********");
        File dir  = new File("C:\\Users\\romai\\Desktop\\TxtGoSecuri\\FicheAgent");
        File[] liste = dir.listFiles();
        List<String> listFichiers = new ArrayList<String>();
        for(File item : liste){
            if(item.isFile())
            {
                System.out.format("%s%n", item.getName());
                listFichiers.add(item.getName());
            }
        }
        for(int i = 0;i < listFichiers.size();i++){
            System.out.println( i + 1 + " : " + listFichiers.get(i));
        }
        System.out.println("*********** Accueil **********");
        String file = "C:\\Users\\romai\\Desktop\\TxtGoSecuri\\ListeAgents\\staff.json";
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            List<String> list = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                list.add(line);
            }
            for(int i = 0; i < list.size(); i++) {
                System.out.println( i + 1 + " : " + list.get(i));
            }
        }
        catch (IOException e) {
            System.out.println("Rien dans le txt");
            e.printStackTrace();
        }
    }
}