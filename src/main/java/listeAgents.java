import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class listeAgents {

    public static String htmlPage;
    public static String[] tabAgents;
    public static String listeAgents;


    public static void readHtml() throws IOException {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
			BufferedReader in = new BufferedReader(new FileReader("src/main/templates/index.html"));
			while (( htmlPage = in.readLine()) != null)
			{
   			  System.out.println (htmlPage);
			}
			in.close();
    }

    public static void replaceHtml() {
        System.out.println(htmlPage);
        tabAgents = listeAgents.split("\n");
        String agent_link = "";
        
        for (String agent : tabAgents) {
            agent_link += "<a href=\"agent/" + agent + "\">" + agent + "</a>\n";
        }

        htmlPage = htmlPage.replace("{{% AGENT_LINK %}}", agent_link);

        System.out.println(htmlPage);
    }

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
        listeAgents = parseGit.parseGitAgent("https://github.com/Romain857/MsprFichiersTxt", "ListeAgents", "staff.txt");
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
        //DisplayListeNom();
        DisplayAgent();
        readHtml();
        replaceHtml();
    }
}