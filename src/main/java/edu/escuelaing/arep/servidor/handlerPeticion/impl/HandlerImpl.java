package edu.escuelaing.arep.servidor.handlerPeticion.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;

import edu.escuelaing.arep.servidor.exception.HandlerException;
import edu.escuelaing.arep.servidor.handlerPeticion.PeticionFile;

public class HandlerImpl implements PeticionFile {

    /**
     * controlador de imagen
     * 
     * @param path         direccion
     * @param clientSocket socket del cliente
     */
    @Override
    public void handlerImg(String path, Socket clientSocket) throws HandlerException {
        PrintWriter out = null;
        OutputStream clientOutput = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            clientOutput = clientSocket.getOutputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        File archivo = new File(path);
        if (archivo.exists()) {
            try {
                BufferedImage image = ImageIO.read(new File(path));
                ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
                DataOutputStream writeimg = new DataOutputStream(clientOutput);
                ImageIO.write(image, "PNG", ArrBytes);
                writeimg.writeBytes("HTTP/1.1 200 OK \r\n" + "Content-Type: image/png \r\n" + "\r\n");
                writeimg.write(ArrBytes.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            out.println("HTTP/1.1 404 Not Found \r\nContent-Type: text/html \r\n\r\n <!DOCTYPE html> <html>"
                    + "<head><title>404</title></head>" + "<body> <h1>404 Not Found " + archivo.getName()
                    + "</h1></body></html>");
        }

    }

    /**
     * Controlador de archivo
     * 
     * @param path         patch
     * @param clientSocket socket del cliente
     */
    @Override
    public void handlerFile(String path, Socket clientSocket) throws HandlerException {
        PrintWriter out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        File archivo = new File(path);
        String temp, outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n";
        if (archivo.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                while ((temp = br.readLine()) != null) {
                    outputLine += temp;
                }
                out.println(outputLine);
                // clientSocket.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            out.println("HTTP/1.1 404 Not Found \r\nContent-Type: text/html \r\n\r\n <!DOCTYPE html> <html>"
                    + "<head><title>404</title></head>" + "<body> <h1>404 Not Found " + archivo.getName()
                    + "</h1></body></html>");
        }

    }
}