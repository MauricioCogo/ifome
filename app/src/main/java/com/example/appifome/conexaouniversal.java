package com.example.appifome;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class conexaouniversal {

    public static String postJSONObject(String myurl, JSONObject parameters) {
        HttpURLConnection conn = null;

        try {
            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(parameters.toString());
            writer.flush();
            writer.close();
            out.close();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("Response: " + response.toString());
                return response.toString();
            } else {
                System.err.println("Erro: Código de resposta HTTP " + responseCode);
            }

        } catch (IOException ex) {
            System.err.println("Erro ao enviar requisição: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
}
