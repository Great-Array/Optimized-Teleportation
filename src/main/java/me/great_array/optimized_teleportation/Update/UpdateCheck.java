package me.great_array.optimized_teleportation.Update;

import me.great_array.optimized_teleportation.Teleport;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class UpdateCheck {

    public static boolean checkForUpdate() throws IOException {
        URL url = new URL("https://pastebin.com/raw/9vQgHncZ");
        Scanner sc = new Scanner(url.openStream());
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
            sb.append(sc.next());
        }
        String result = sb.toString();
        result = result.replaceAll("<[^>]*>", "");

        int version = Teleport.plugin.getConfig().getInt("ConfigVersion");
        int update = Integer.parseInt(result);

        if (update > version) {
            return true;
        } else {
            return false;
        }

    }

}
