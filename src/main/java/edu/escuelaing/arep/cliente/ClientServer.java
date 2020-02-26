package edu.escuelaing.arep.cliente;

import java.io.*;
import java.net.*;

public class ClientServer implements Runnable {

    private URL url;
    private static int threads = 40;
    private static int succes = 0;
    private static int fail = 0;

    public ClientServer(String urll) {
        try {
            this.url = new URL(urll);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("MalformedURLException: " + e);
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        ClientServer cs = new ClientServer(args[0]);
        Thread[] peticiones = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            // System.out.println(i);
            peticiones[i] = new Thread(cs);
            peticiones[i].start();
        }
        for (int i = 0; i < threads; i++) {
            // System.out.println(i);
            try {
                peticiones[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("InterruptedException: " + e);
            }
        }
        System.out.println("succes: " + succes + " " + " fail : " + fail);

    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine = null;
            // System.out.println("succes: " + succes + " " + " fail : " + fail);
            while ((inputLine = reader.readLine()) != null) {
                // System.out.println(inputLine);
                // succes++;
            }
            succes++;
            System.out.println("successsssssss: " + succes + " " + " fail : " + fail);
        } catch (final IOException x) {
            System.err.println(x);
            fail++;
        }
    }
}