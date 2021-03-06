package edu.escuelaing.arep.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import edu.escuelaing.arep.servidor.exception.HandlerException;
import edu.escuelaing.arep.servidor.handlerPeticion.PeticionFile;
import edu.escuelaing.arep.servidor.handlerPeticion.impl.HandlerImpl;

public class HandlerClient {

    private PrintWriter out = null;
    private BufferedReader in = null;
    private PeticionFile pF = new HandlerImpl();
    private Socket clientSocket = null;

    /**
     * Constructor que recibe un socket para atender el cliente
     * 
     * @param clientSocket socket del cliente
     */
    public HandlerClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Metodo que resuelve la peticion del socket cliente
     */
    public void request() {
        String inputLine, archivo;
        archivo = "index.html";
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                // System.out.println("Recibi: " + inputLine);
                if (!in.ready()) {
                    String[] pathType = getType(archivo);
                    // System.out.println("Archivo: " + archivo);
                    if (pathType[1].equals("html") || pathType[1].equals("js")) {

                        pF.handlerFile(pathType[0], clientSocket);
                    } else if (pathType[1].equals("img")) {

                        pF.handlerImg(pathType[0], clientSocket);
                    }

                    break;
                }
                if (inputLine.contains("GET")) {
                    // System.out.println(inputLine.indexOf("/") + " " + inputLine.indexOf(" ",
                    // inputLine.indexOf(" ") + 1));
                    archivo = inputLine.substring(inputLine.indexOf("/") + 1,
                            inputLine.indexOf(" ", inputLine.indexOf(" ") + 1));
                    if (archivo.length() == 0) {
                        // System.out.println("Archivo: " + archivo);
                        archivo = "index.html";
                    }
                    // System.out.println("Archivo: " + archivo);
                }
            }
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("request error: " + e);
        } catch (HandlerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("request error: " + e);
        }

    }

    /**
     * Analiza el archivo para devolver el path y que tipo es
     * 
     * @param file archivo
     * @return
     */
    private String[] getType(String file) {
        String path = "src/main/resources/";
        String[] res = new String[2];

        if (file.endsWith("html")) {
            path += "web/" + file;
            res[1] = "html";

        } else if (file.endsWith(".js")) {
            path += "js/" + file;
            res[1] = "js";
        } else {
            path += "img/" + file;
            res[1] = "img";
        }
        res[0] = path;
        return res;
    }
}