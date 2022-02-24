import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class parseIntEquipement {

    public static ArrayList<String> listeEquipementAgent = new ArrayList<String>();
    public static ArrayList<ArrayList<String>> listeEquipement = new ArrayList<ArrayList<String>>();
    public static String agentName;
    public static String agentPseudo;
    public static ArrayList<String> listeAgent = new ArrayList<String>();
    public static String htmlPage;

    private static void FicheAgent(String dossier, String agent) {
        // System.out.println("*********** Fiche Agent **********");
        String FicheAgent = parseGit.parseGitAgent("https://github.com/DreamTeamOberj/MsprFichiersTxt", dossier, agent);
        List<String> listeFicheAgent = new ArrayList<String>(Arrays.asList(FicheAgent.split("\n")));
        // for (String item : listeFicheAgent) {
        //     System.out.println("###" + item);
        // }

        agentName = listeFicheAgent.get(0) + " " + listeFicheAgent.get(1);
        agentPseudo = (listeFicheAgent.get(1).charAt(0) + listeFicheAgent.get(0)).toLowerCase();

        for (int i = 5; i < listeFicheAgent.size(); i++) {
            listeEquipementAgent.add(listeFicheAgent.get(i).toString());
        }

        // for (String s : listeFicheAgent) {
        //     if (FicheAgent.contains(s)) {
        //         System.out.println(s + " : True");
        //     }
        // }

    }

    public static String readHtml() throws IOException {
        String html = "";
		BufferedReader in = new BufferedReader(new FileReader("src/main/templates/agent.html"));
		while (( htmlPage = in.readLine()) != null)
		{
   		    // System.out.println (htmlPage);
            html += htmlPage + "\n";
		}
		in.close();
        return html;
    }

    public static String replaceHtml() {
        String equip_list = "";

        htmlPage = htmlPage.replace("{{% AGENT_NAME %}}", agentName);
        
        for (ArrayList<String> equipement : listeEquipement) {
            equip_list += "<li>" + equipement.get(1) +"\n";

            if (Arrays.toString(listeEquipementAgent.toArray()).contains(equipement.get(0))) {
                equip_list += "<input type=\"checkbox\" name=\"checkbox\" id=\"checkbox\" onclick=\"return false;\" checked>\n";
            } else {
                equip_list += "<input type=\"checkbox\" name=\"checkbox\" id=\"checkbox\" onclick=\"return false;\">\n";
            }
 
            equip_list += "</li>\n";
        }

        htmlPage = htmlPage.replace("{{% AGENT_MATERIEL %}}", equip_list);

        // System.out.println(htmlPage);
        return htmlPage;
    }

    public static void createHtmlFile(String pseudo) throws IOException {  
        // Recevoir le fichier 

        String jecomprendpas = "build/agents/"+pseudo+".html";
        // System.out.println(jecomprendpas);
        // System.out.println(agentPseudo);

        File f = new File(jecomprendpas);

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

        FileOutputStream fos = new FileOutputStream("build/agents/" + pseudo + ".html");
        fos.write(htmlPage.getBytes());
        fos.flush();
        fos.close();

        System.out.println("File edited");
    }

    public static void parseFichiersAgent() {
        listeAgent = parseGit.ListFileGit("https://github.com/DreamTeamOberj/MsprFichiersTxt", "FicheAgent");
    }

    private static void FicheEquipement(String FicheEquipement, String nomFichier) {
        // System.out.println("*********** Fiche Equipement **********");
        String FicheEquipements = parseGit.parseGitAgent("https://github.com/DreamTeamOberj/MsprFichiersTxt", FicheEquipement, nomFichier);
        List<String> myList = new ArrayList<String>(Arrays.asList(FicheEquipements.split("\n")));
        // for (String item : myList) {
        //     System.out.println(item);
        // }

        for (String line : myList) {
            ArrayList<String> equip = new ArrayList<String>();
            
            for (int i = 0; i < line.length(); i++) {
                if (Character.isUpperCase(line.charAt(i))) {
                    equip.add(line.substring(0, i-1));
                    equip.add(line.substring(i, line.length()));
                }
            }
            // System.out.println(equip);
            listeEquipement.add(equip);
        }

        // System.out.println(listeEquipement);
    }

    public static void main(String[] args) throws Exception {
        FicheEquipement("FicheEquipement", "liste.txt");
        parseFichiersAgent();
        String fiche;
        String pseudo;

        System.out.println(listeAgent.size());
        

        for (int i = 0; i < listeAgent.size(); i++) {
            htmlPage = readHtml();
            fiche = listeAgent.get(i);
            FicheAgent("FicheAgent", fiche);
            pseudo = fiche.split(".txt")[0];
            htmlPage = replaceHtml();
            createHtmlFile(pseudo);
        }
        
        System.out.println("DONE !");
    }
}