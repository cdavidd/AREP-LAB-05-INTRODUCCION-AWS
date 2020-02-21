package edu.escuelaing.arep;

import java.io.*; 
import java.net.*; 
public class URLReader { 
  public static void main(final String[] args) throws Exception { 
      final URL url = new URL(args[0]); 
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(url.openStream()))) { 
            String inputLine = null; 
            while ((inputLine = reader.readLine()) != null) { 
                  System.out.println(inputLine); 
             } 
       } catch (final IOException x) { 
               System.err.println(x); 
       } 
    } 
}