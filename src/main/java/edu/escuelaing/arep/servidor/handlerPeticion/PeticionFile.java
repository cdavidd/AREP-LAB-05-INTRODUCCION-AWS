package edu.escuelaing.arep.servidor.handlerPeticion;


import java.net.Socket;

import edu.escuelaing.arep.servidor.exception.HandlerException;

public interface PeticionFile {
    
    void handlerImg (String path, Socket clientSocket) throws HandlerException;
    void handlerFile (String path, Socket clientSocket) throws HandlerException;

}