import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class listeAgents {

    public static String htmlPage;
    public static ArrayList<String> listeAgents;

    public static String readHtml() throws IOException {
        String htmlPage;
        String html = "";
		BufferedReader in = new BufferedReader(new FileReader("src/main/templates/index.html"));
		while (( htmlPage = in.readLine()) != null)
		{
   		    // System.out.println (htmlPage);
            html += htmlPage + "\n";
		}
		in.close();
        return html;
    }

    public static String replaceHtml() {
        String agent_link = "";
        
        for (String agent : listeAgents) {
            agent_link += "<li><a href=\"agent/" + agent + "\">" + agent + "</a></li>\n";
        }

        htmlPage = htmlPage.replace("{{% AGENT_LINK %}}", agent_link);

        // System.out.println(htmlPage);
        return htmlPage;
    }

    public static ArrayList<String> getListNom(){
        ArrayList<String> ficheAgent = parseGit.ListFileGit("https://github.com/Romain857/MsprFichiersTxt", "FicheAgent");
        ArrayList<String> listNoms = new ArrayList<>();

        for (String item : ficheAgent) {
            String fichierWithOutExt = item.replaceFirst("[.][^.]+$", "");
            // String FicheAgent = parseGit.parseGitAgent("https://github.com/Romain857/MsprFichiersTxt", "FicheAgent", item);
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
        // System.out.println(myList);
        return myList;
    }

    private static void DisplayListeNom() {
        ArrayList<String> listeNoms = getListNom();
        for (int i = 0; i < listeNoms.size(); i++) {
            System.out.println(i + 1 + " : " + listeNoms.get(i));
        }
    }

    public static void createHtmlFile() throws IOException {  
        // Recevoir le fichier 
        File f = new File("src/main/build/index.html");

        // Créer un nouveau fichier
        // Vérifier s'il n'existe pas
        if (!(f.createNewFile())) {
            System.out.println("File already exists");
            System.out.println("Deleting it...");
        
            if(f.delete())   // delete() will delete the selected file from system and return true if deletes successfully else it'll return false
            {
                System.out.println("File deleted successfully");
                f.createNewFile();
            }
            else
            {
                System.out.println("Failed to delete the file");
            }
        }

        System.out.println("File created");

        FileOutputStream fos = new FileOutputStream("src/main/build/index.html");
        fos.write(htmlPage.getBytes());
        fos.flush();
        fos.close();

        System.out.println("File edited");
        
    }

    public static void main(String[] args) throws Exception {
        listeAgents = getListNom();
        // DisplayAgent();
        htmlPage = readHtml();
        htmlPage = replaceHtml();
        createHtmlFile();
        System.out.println("DONE !");
    }
}