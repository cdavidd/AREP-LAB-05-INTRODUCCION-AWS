package edu.escuelaing.arep.servidor.handlerPeticion;

import edu.escuelaing.arep.servidor.exception.HandlerException;

public interface Peticion {
    
    void handlerImg () throws HandlerException;
    void handlerFile () throws HandlerException;

}