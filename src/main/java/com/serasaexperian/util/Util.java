package com.serasaexperian.util;

import java.io.BufferedReader;
import java.io.IOException;

public class Util {
    public static String converterJsonEmString(BufferedReader buffereReader) throws IOException {
        String resposta;
        StringBuilder jsonString = new StringBuilder();
        while ((resposta = buffereReader.readLine()) != null) {
            jsonString.append(resposta);
        }
        return jsonString.toString();
    }
}
