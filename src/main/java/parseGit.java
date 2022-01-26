import org.json.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class parseGit {

    public static String parseGitRepo(String urlGit, String nomFichier) {

        String apiGit;
        if (!urlGit.contains("api.github") && !urlGit.contains("raw")) {
            apiGit = "https://api.github.com/repos/" + urlGit.split("com/")[1] + "/contents";
        } else {
            apiGit = urlGit;
        }

        InputStream is = null;
        try {
            is = new URL(apiGit).openStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jobj = null;
        JSONObject urlObj = null;
        String jsonText = null;
        try {
            assert is != null;
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            jsonText = readAll(rd);
            if (jsonText.contains("{")) {
                JSONArray jsonArray = new JSONArray(jsonText);
                jobj = new JSONObject();
                urlObj = new JSONObject();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonIndex = jsonArray.getJSONObject(i);
                    String name = jsonIndex.getString("name");

                    jobj.put(name, jsonArray.getJSONObject(i));
                    if (!jsonIndex.isNull("download_url")) {
                        String nameTxt = jsonIndex.getString("name");
                        String url = jsonIndex.getString("download_url");
                        urlObj.put(nameTxt, url);
                    }
                }

                if (urlObj.isEmpty() && !Objects.equals(nomFichier, "")) {
                    parseGitRepo(jobj.getJSONObject(nomFichier).getString("url"), nomFichier);
                } else {
                    for (int i = 0; i < urlObj.names().length(); i++) {
                        String key = urlObj.names().getString(i);
                        parseGitRepo((String) urlObj.get(key), "");
                    }
                }
            } else {
                return jsonText;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(parseGitRepo("https://github.com/Romain857/MsprFichiersTxt", "FicheAgent"));
    }
}
