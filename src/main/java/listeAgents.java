import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class listeAgents {
    private static Object ArrayList;

    public static void main(String[] args) throws Exception {

        System.out.println("*********** Liste de fichiers **********");
        ArrayList<String> ficheAgent = parseGit.ListFileGit("https://github.com/Romain857/MsprFichiersTxt", "FicheAgent");
        List<String> listNoms = new ArrayList<String>();
        for (String item : ficheAgent) {
            String fichierWithOutExt = item.replaceFirst("[.][^.]+$", "");
            String FicheAgent = parseGit.parseGitAgent("https://github.com/Romain857/MsprFichiersTxt", "FicheAgent", item);
            listNoms.add(fichierWithOutExt);
        }
        for (int i = 0; i < listNoms.size(); i++) {
            System.out.println(i + 1 + " : " + listNoms.get(i));
        }

        System.out.println("*********** Accueil **********");
        String listeAgents = parseGit.parseGitAgent("https://github.com/Romain857/MsprFichiersTxt", "ListeAgents", "staff.txt");
        List<String> myList = new ArrayList<String>(Arrays.asList(listeAgents.split("\n")));
        if (listNoms.equals(myList)) {
            System.out.println("Tous les agents ont inscris leur fiche agent");
        } else {
            System.out.println("Des agents n'ont pas inscris leur liste agents");
            List<String> listCompare = new ArrayList<String>(myList);
            listCompare.retainAll(listNoms);
            System.out.println("Seulement " + listCompare + " ont inscris leur fiche agent");
        }
    }
}