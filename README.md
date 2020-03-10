# AREP-LAB-05-INTRODUCCION-AWS

mvn exec:java -Dexec.mainClass="edu.escuelaing.arep.servidor.HttpServer"
mvn exec:java -Dexec.mainClass="edu.escuelaing.arep.cliente.ClientServer" -Dexec.args="http://localhost:35000/index.html"
