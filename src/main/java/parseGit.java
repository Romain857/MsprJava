import org.json.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class parseGit {
    // Cherche tout le contenu d'un agent sur le git
    public static String parseGitAgent(String urlGit, String nomDossier, String nomFichier) {
        String response = "";
        String url = parseUrl(urlGit, nomDossier);
        if (!Objects.equals(nomFichier, "")) {
            String urlDown = parseUrl(url, nomFichier);
            if (urlDown.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
                response = parseUrl(urlDown, "");
            } else {
                response = urlDown;
            }
        } else {
            response = "Veuillez entrez un nom d'agent.";
        }
        return response;
    }

    //
        public static ArrayList<String> ListFileGit(String urlGit, String nomFolder) {

        String urlFolder = parseUrl(urlGit, nomFolder);
        return parseFolder(urlFolder);
    }

    public static String parseUrl(String url, String name) {

        InputStream is = openStream(url);

        boolean isHere = false;
        String response = "";
        try {
            assert is != null;
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String text = readAll(rd);

            if (text.contains("{")) {
                JSONArray jsonArray = new JSONArray(text);
                int i = 0;
                while (i < jsonArray.length() && !isHere) {
                    JSONObject jsonIndex = jsonArray.getJSONObject(i);
                    if (jsonIndex.getString("name").contains(name)) {
                        if (!jsonIndex.isNull("download_url")) {
                            response = jsonIndex.getString("download_url");
                        } else {
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

    public static ArrayList<String> parseFolder(String url) {
        ArrayList<String> listFolder = new ArrayList<>();

        InputStream is = openStream(url);
        try {
            assert is != null;
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String text = readAll(rd);

            if (text.contains("{")) {
                JSONArray jsonArray = new JSONArray(text);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonIndex = jsonArray.getJSONObject(i);
                    listFolder.add(jsonIndex.getString("name"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listFolder;
    }

    public static InputStream openStream(String url) {
        String apiGit;
        if (!url.contains("api.github") && !url.contains("raw")) {
            apiGit = "https://api.github.com/repos/" + url.split("com/")[1] + "/contents";
        } else {
            apiGit = url;
        }

        String response = "";
        InputStream is = null;
        boolean isHere = false;

        try {
            URL urlApi = new URL(apiGit);
            is = urlApi.openStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
