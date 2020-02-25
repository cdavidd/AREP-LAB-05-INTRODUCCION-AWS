package edu.escuelaing.arep.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private boolean running;
    private HandlerClient handlerClient= null;
    private ExecutorService pool;

    /**
     * Inicia el servidor para empezar a escuchar
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args){
        new HttpServer().iniciarServidor();
    }

    public HttpServer(){
        int port = getPort();
        pool = Executors.newFixedThreadPool(10);
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }
    }

    public void iniciarServidor (){
        running = true;
        while(running){
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();   
            } catch (IOException e) {
                System.err.println("Accept failed. "+e);
                System.exit(1);
            }

            handlerClient = new HandlerClient(clientSocket);
            Thread peticion = new Thread( () -> {
                
                handlerClient.request();                                                                                
            });
            pool.execute(peticion);
        }    
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