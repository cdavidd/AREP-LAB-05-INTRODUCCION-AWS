package edu.escuelaing.arep.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private ServerSocket serverSocket = null;
    // private Socket clientSocket = null;
    private boolean running;
    private ExecutorService pool;
    private int productores = 10;

    /**
     * Inicia el servidor para empezar a escuchar
     * 
     * @param args argumentos
     */
    public static void main(String[] args) {
        new HttpServer().iniciarServidor();
    }

    /**
     * Constructor
     */
    public HttpServer() {
        int port = getPort();
        pool = Executors.newFixedThreadPool(productores);
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }
    }

    /**
     * Metodo para empezar a escuchar a los clientes
     */
    public void iniciarServidor() {
        running = true;

        while (running) {
            Socket clientSocket = null;
            try {
                // System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
                HandlerClient handlerClient = new HandlerClient(clientSocket);
                Runnable peticion = () -> {
                    handlerClient.request();
                };
                pool.execute(peticion);
                // peticion.start();
            } catch (IOException e) {
                System.err.println("Accept failed. " + e);
                System.exit(1);
            }
        }
    }

    /**
     * Puerto a utilizar
     * 
     * @return
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000; // returns default port if heroku-port isn't set(i.e. on localhost)
    }

}