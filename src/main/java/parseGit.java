import org.json.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class parseGit {
    // Cherche tout le contenu d'un fichier dans un dossier du git
    public static String parseGitAgent(String urlGit, String nomDossier, String nomFichier) {
        String response = "";
        String url = parseUrl(urlGit, nomDossier);
        // Vérifie si le nom de fichier est non vide
        if (!Objects.equals(nomFichier, "")) {
            // Parse l'url du contenu du fichier
            String urlDownload = parseUrl(url, nomFichier);
            // Vérifie si l'url de téléchargement est valide
            if (urlDownload.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
                // Si elle ne l'est pas on reparse l'url
                response = parseUrl(urlDownload, "");
            } else {
                // Sinon le résultat est le contenu de l'url
                response = urlDownload;
            }
        } else {
            response = "Veuillez entrez un nom de fichier valide.";
        }
        return response;
    }

    // Etabli une liste des fichiers selon un nom de dossier fourni
    public static ArrayList<String> ListFileGit(String urlGit, String nomFolder) {

        String urlFolder = parseUrl(urlGit, nomFolder);
        return parseFolder(urlFolder);
    }

    // Parse un url donné avec le nom du dossier
    public static String parseUrl(String url, String nomDossier) {
        // Ouvre une connexion
        InputStream is = openStream(url);

        boolean isHere = false;
        String response = "";
        try {
            assert is != null;
            // Lecture de tous les éléments de notre connexion avec l'url
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String text = readAll(rd);
            // Si la chaîne est un JSON
            if (text.contains("{")) {
                // Création d'un tableau JSON
                JSONArray jsonArray = new JSONArray(text);
                int i = 0;
                // Tant que i n'atteint pas la taille de notre JSON et tant qu'on ne trouve pas notre élément "download_url"
                while (i < jsonArray.length() && !isHere) {
                    JSONObject jsonIndex = jsonArray.getJSONObject(i);
                    // Si le JSON contient le dossier
                    if (jsonIndex.getString("name").contains(nomDossier)) {
                        // Si le contenu de "download_url" n'est pas null
                        if (!jsonIndex.isNull("download_url")) {
                            // On affecte la valeur de "download_url"
                            response = jsonIndex.getString("download_url");
                        } else {
                            // On affecte la valeur de "url"
                            response = jsonIndex.getString("url");
                        }
                        isHere = true;
                    }
                    i++;
                }
                if (!isHere) {
                    response = "Le nom d'agent n'est pas présent dans le répertoire.";
                }
            } else {
                response = text;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    // Recherche tous les éléments d'un dossier
    public static ArrayList<String> parseFolder(String url) {
        ArrayList<String> listFolder = new ArrayList<>();
        // Ouverture d'une connexion selon l'url fourni
        InputStream is = openStream(url);
        try {
            assert is != null;
            // Lecture de tous les éléments de notre connexion avec l'url
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String text = readAll(rd);
            // Si la chaîne est un JSON
            if (text.contains("{")) {
                // Création d'un tableau JSON
                JSONArray jsonArray = new JSONArray(text);
                for (int i = 0; i < jsonArray.length(); i++) {
                    // On ajoute à la liste tous les éléments avec la clé "name"
                    JSONObject jsonIndex = jsonArray.getJSONObject(i);
                    listFolder.add(jsonIndex.getString("name"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listFolder;
    }

    // Ouvre une connexion avec une URL
    public static InputStream openStream(String url) {
        String apiGit;
        // Vérifie si l'url n'est pas l'adresse de l'api de base ou celle d'une adresse download
        if (!url.contains("api.github") && !url.contains("raw")) {
            apiGit = "https://api.github.com/repos/" + url.split("com/")[1] + "/contents";
        } else {
            apiGit = url;
        }

        InputStream is = null;

        try {
            // Ouverture de la connexion
            URL urlApi = new URL(apiGit);
            is = urlApi.openStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    // Lecture de tous les élements d'un reader donné
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
