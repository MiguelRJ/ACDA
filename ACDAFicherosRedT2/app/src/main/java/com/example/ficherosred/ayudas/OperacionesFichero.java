package com.example.ficherosred.ayudas;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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

        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
}
