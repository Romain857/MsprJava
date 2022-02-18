import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class listeAgents {

    public static ArrayList<String> getListNom(){
        ArrayList<String> ficheAgent = parseGit.ListFileGit("https://github.com/Romain857/MsprFichiersTxt", "FicheAgent");
        ArrayList<String> listNoms = new ArrayList<>();

        for (String item : ficheAgent) {
            String fichierWithOutExt = item.replaceFirst("[.][^.]+$", "");
            //String FicheAgent = parseGit.parseGitAgent("https://github.com/Romain857/MsprFichiersTxt", "FicheAgent", item);
            listNoms.add(fichierWithOutExt);
        }
        return listNoms;
    }

    private static ArrayList<String> DisplayAgent() {
        System.out.println("*********** Accueil **********");
        String listeAgents = parseGit.parseGitAgent("https://github.com/Romain857/MsprFichiersTxt", "ListeAgents", "staff.txt");
        ArrayList<String> myList = new ArrayList<>(Arrays.asList(listeAgents.split("\n")));
        ArrayList<String> listNoms = getListNom();
        if (listNoms.equals(myList)) {
            System.out.println("Tous les agents ont inscris leur fiche agent");
        } else {
            System.out.println("Des agents n'ont pas inscris leur liste agents");
            List<String> listCompare = new ArrayList<>(myList);
            listCompare.retainAll(listNoms);
            System.out.println("Seulement " + listCompare + " ont inscris leur fiche agent");
        }
        System.out.println(myList);
        return myList;
    }

    private static ArrayList<String> DisplayListeNom() {
        ArrayList<String> listeNoms = getListNom();
        for (int i = 0; i < listeNoms.size(); i++) {
            System.out.println(i + 1 + " : " + listeNoms.get(i));
        }
        return listeNoms;
    }

    public static void main(String[] args) throws Exception {
        DisplayListeNom();
        //DisplayAgent();
    }
}