import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class parseIntEquipement {
    public static void main(String[] args) throws Exception {
        System.out.println("*********** Fiche Equipement **********");
        String FicheEquipements = parseGit.parseGitAgent("https://github.com/Romain857/MsprFichiersTxt", "FicheEquipement", "liste.txt");
        List<String> myList = new ArrayList<String>(Arrays.asList(FicheEquipements.split("\n")));
        for (String item : myList){
            System.out.println(item);
        }
        System.out.println("*********** Fiche Agent **********");
        String FicheAgent = parseGit.parseGitAgent("https://github.com/Romain857/MsprFichiersTxt", "FicheAgent", "cberthier.txt");
        List<String> listeFicheAgent = new ArrayList<String>(Arrays.asList(FicheAgent.split("\n")));
        for (String item : listeFicheAgent){
            System.out.println(item);
        }

        List<String> listeEquipementAgent = new ArrayList<>();
        for (int i = 5; i < listeFicheAgent.size(); i++) {
            listeEquipementAgent.add(listeFicheAgent.get(i));
        }
        for (int i = 0; i < listeEquipementAgent.size(); i++){
            if (FicheAgent.contains(listeEquipementAgent.get(i))){
                System.out.println(listeEquipementAgent.get(i) + " : True");
            }
        }
    }
}