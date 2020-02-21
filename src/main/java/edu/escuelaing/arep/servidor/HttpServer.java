package edu.escuelaing.arep.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private static ServerSocket serverSocket = null;
    private static PrintWriter out = null;
    private static Socket clientSocket = null;
    private static BufferedReader in = null;
    private boolean running;

    public HttpServer(){
        int port = getPort();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }
    }

    public void iniciarServidor (){
        running = true;
        ExecutorService pool = Executors.newFixedThreadPool(10);

        while(running){
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            

            

        }

    }

    /**
     * Inicia el servidor para empezar a escuchar
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args){
        new HttpServer().iniciarServidor();
    }


    /**
     * Puerto a utilizar
     * @return 
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000; // returns default port if heroku-port isn't set(i.e. on localhost)
    }

}