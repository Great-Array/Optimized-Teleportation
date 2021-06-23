package me.great_array.optimized_teleportation.Util.Update;

import me.great_array.optimized_teleportation.Teleport;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class CheckUpdate {

    private static final int pluginVersion = Teleport.configVersion;

    public static boolean checkForUpdate() throws IOException {

        URL url = new URL("https://pastebin.com/raw/9vQgHncZ");

        Scanner scanner = new Scanner(url.openStream());
        StringBuffer stringBuffer = new StringBuffer();

        while (scanner.hasNext()) {
            stringBuffer.append(scanner.next());
        }

        String results = stringBuffer.toString();
        results = results.replaceAll("<[^>]*>", "");

        int updateVersion = Integer.parseInt(results);

        return updateVersion > pluginVersion;

    }

}
