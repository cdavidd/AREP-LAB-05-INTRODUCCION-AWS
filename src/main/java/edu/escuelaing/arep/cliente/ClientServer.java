package edu.escuelaing.arep.cliente;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientServer implements Runnable {

    private URL url;
    private static int threads;
    static AtomicInteger succesA = new AtomicInteger(0);
    static AtomicInteger failA = new AtomicInteger(0);

    /**
     * Constructor que recibe una url
     * 
     * @param urll url
     */
    public ClientServer(String urll) {
        try {
            this.url = new URL(urll);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e);
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        long start = System.nanoTime();
        ClientServer cs = new ClientServer(args[0]);
        threads = Integer.parseInt(args[1]);
        Thread[] peticiones = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            peticiones[i] = new Thread(cs);
            peticiones[i].start();
        }
        for (int i = 0; i < threads; i++) {
            try {
                peticiones[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("InterruptedException: " + e);
            }
        }
        long elapsedTime = System.nanoTime() - start;
        long convert = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        System.out.println("succes: " + succesA + " " + " fail : " + failA);
        System.out.println("Tiempo MILLISECONDS: " + convert);

    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                // System.out.println(inputLine);
            }
            succesA.addAndGet(1);
        } catch (final IOException x) {
            System.err.println(x);
            failA.addAndGet(1);
        }
    }
}