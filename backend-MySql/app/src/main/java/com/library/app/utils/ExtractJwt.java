package com.library.app.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJwt {

    public static String payloadJwtExtraction(String token, String extraction) {
        token.replace("Bearer", "");

        String[] chunks = token.split("\\."); // token contains header, payload and signature which is seperated by "."

        Base64.Decoder decoder = Base64.getUrlDecoder();  // This object is going to be used for decoding the token

        String payload = new String(decoder.decode(chunks[1]));

        String[] entries = payload.split(",");

        Map<String, String> map = new HashMap<String, String>();

        for (String entry : entries) {
            String[] keyValue = entry.split(":");
            if (keyValue[0].equals(extraction)) {  // In payload sub is in format like : "sub" : "data"

                int remove = 1;

                if (keyValue[1].endsWith("}")) {
                    remove = 2;
                }

                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                map.put(keyValue[0], keyValue[1]);
            }
        }

        if (map.containsKey(extraction)) {
            return map.get(extraction);
        }

        return null;
    }
}

