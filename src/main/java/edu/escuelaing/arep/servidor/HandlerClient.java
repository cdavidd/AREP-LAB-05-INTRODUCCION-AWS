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
    

    public void request(Socket clientSocket){
        System.out.println("entraaaaaaaaaaaaaaaaaaaaaaa  clientSocket");
        String inputLine, archivo;
        archivo = "/";
        try {
            System.out.println("entraaaaaaaaaaaaaaaaaaaaaaa  -");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("entraaaaaaaaaaaaaaaaaaaaaaa  out");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("entraaaaaaaaaaaaaaaaaaaaaaa  in");
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibi: " + inputLine);
                if (!in.ready()) {
                    break;
                }
                if (inputLine.contains("GET")) {
                    System.out
                            .println(inputLine.indexOf("/") + " " + inputLine.indexOf(" ", inputLine.indexOf(" ") + 1));
                    archivo = inputLine.substring(inputLine.indexOf("/") + 1,
                            inputLine.indexOf(" ", inputLine.indexOf(" ") + 1));
                    if (archivo.length() == 0) {
                        // System.out.println("Archivo: " + archivo);
                        archivo = "index.html";
                    }
                    // System.out.println("Archivo: " + archivo);
                    break;
                }
            }
            String[] pathType = getType(archivo);
            // System.out.println("Archivo: " + archivo);
            if (pathType[1].equals("html")  || pathType[1].equals("js")) {
                System.out.println("entraaaaaaaaaaaaaaaaaaaaaaa  html js");
                pF.handlerFile(pathType[0], clientSocket);
            } else if (pathType[1].equals("img")) {
                System.out.println("entraaaaaaaaaaaaaaaaaaaaaaa  img");
                pF.handlerImg(pathType[0], clientSocket);
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("request error: "+e);
        } catch (HandlerException e) {
			// TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("request error: "+e);
        }
        
    }

    /**
     * Analiza el archivo para devolver el path y que tipo es
     * @param file
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