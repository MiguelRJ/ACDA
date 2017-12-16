package com.example.ficherosfinal;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.StrictMode;


/**
 * Created by mrj on 22/11/17.
 */

public class OperacionesFichero {
    public OperacionesFichero() {

    }

    public Boolean escribirEnFichero(String fcontent, String fpath){
        try {
            File file = new File(fpath);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.newLine();
            bw.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String leerFicheroCompleto(String fpath){
        BufferedReader br = null;
        String response = null;

        try {
            StringBuffer output = new StringBuffer();
            Log.d("fpath",fpath);

            br = new BufferedReader(new FileReader(fpath));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line +"\n");
            }
            response = output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public int numeroLineasFichero(String fpath){
        BufferedReader br = null;
        int numLineas =0;

        try {
            br = new BufferedReader(new FileReader(fpath));
            while (br.readLine() != null) {
                numLineas++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return numLineas;
    }

    public int numeroLineasUrl(String url){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL direccionUrl = null;
        try {
            direccionUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        int numLineas =0;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(direccionUrl.openStream()));
            while (br.readLine() != null) {
                numLineas++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return numLineas;
    }

    public String[] leerURL(String url) {

        URL direccionUrl = null;
        try {
            direccionUrl = new URL(url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        BufferedReader in = null;
        String inputLine;
        int numUrls = numeroLineasUrl(url);
        String [] enlaces = new String[numUrls];
        int i = 0;
        /*https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html*/

        try {
            in = new BufferedReader(new InputStreamReader(direccionUrl.openStream()));
            while ((inputLine = in.readLine()) != null) {
                enlaces[i++] = inputLine;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return enlaces;
    }
}
