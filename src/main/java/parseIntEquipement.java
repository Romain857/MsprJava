import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class parseIntEquipement {

    private static void FicheAgent(String dossier, String agent) {
        System.out.println("*********** Fiche Agent **********");
        String FicheAgent = parseGit.parseGitAgent("https://github.com/Romain857/MsprFichiersTxt", dossier, agent);
        List<String> listeFicheAgent = new ArrayList<String>(Arrays.asList(FicheAgent.split("\n")));
        for (String item : listeFicheAgent) {
            System.out.println(item);
        }

        List<String> listeEquipementAgent = new ArrayList<>();
        for (int i = 5; i < listeFicheAgent.size(); i++) {
            listeEquipementAgent.add(listeFicheAgent.get(i));
        }

        for (String s : listeEquipementAgent) {
            if (FicheAgent.contains(s)) {
                System.out.println(s + " : True");
            }
        }
    }

    private static void FicheEquipement(String FicheEquipement, String nomFichier) {
        System.out.println("*********** Fiche Equipement **********");
        String FicheEquipements = parseGit.parseGitAgent("https://github.com/Romain857/MsprFichiersTxt", FicheEquipement, nomFichier);
        List<String> myList = new ArrayList<String>(Arrays.asList(FicheEquipements.split("\n")));
        for (String item : myList) {
            System.out.println(item);
        }
    }

    public static void main(String[] args) throws Exception {
        //FicheEquipement("FicheEquipement", "liste.txt");
        FicheAgent("FicheAgent", "cberthier.txt");
    }
}