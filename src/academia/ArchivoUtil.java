package academia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Manejo de archivos de la academia. Es la unica clase del proyecto que
 * importa java.io: aqui se escribe y se lee todo lo que el sistema guarda en
 * disco, de modo que las demas clases no saben como se guardan los datos.
 *
 * Los archivos son de texto y cada linea es un objeto, con los campos
 * separados por punto y coma. Se usa BufferedWriter para escribir y
 * BufferedReader para leer, y el cierre del flujo va siempre en el bloque
 * finally.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturo
 * @author Rojas Valdivia, Jhann Frank
 */
public class ArchivoUtil {
    private String carpeta;

    // Por defecto los archivos se guardan en la carpeta datos del proyecto
    public ArchivoUtil() {
        this.carpeta = "datos";
    }

    public ArchivoUtil(String carpeta) {
        this.setCarpeta(carpeta);
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        if (carpeta.trim().length() > 0) {
            this.carpeta = carpeta.trim();
        } else {
            System.out.println("Error: el nombre de la carpeta no puede estar vacio");
        }
    }

    // Arma la ruta con File.separator para que funcione en Windows y en Linux
    public String rutaDe(String nombre_archivo) {
        return this.carpeta + File.separator + nombre_archivo;
    }

    // Crea la carpeta datos si todavia no existe; mkdirs crea tambien las intermedias
    public boolean crearCarpeta() {
        File directorio = new File(this.carpeta);
        if (directorio.exists()) {
            return true;
        }
        boolean creada = directorio.mkdirs();
        if (creada) {
            System.out.println("Carpeta creada: " + directorio.getAbsolutePath());
        } else {
            System.out.println("Error: no se pudo crear la carpeta " + this.carpeta);
        }
        return creada;
    }

    public boolean existeArchivo(String nombre_archivo) {
        File archivo = new File(this.rutaDe(nombre_archivo));
        return archivo.exists() && archivo.isFile();
    }

    // Tamanio en bytes; sirve para comprobar que el archivo no quedo vacio
    public long tamanioArchivo(String nombre_archivo) {
        File archivo = new File(this.rutaDe(nombre_archivo));
        if (!archivo.exists()) {
            return 0;
        }
        return archivo.length();
    }

    /**
     * Escribe la lista completa en el archivo. El segundo parametro de
     * FileWriter va en false, por eso borra lo que habia antes y deja solo lo
     * nuevo. El flujo se declara fuera del try para que el finally pueda
     * cerrarlo aunque la escritura falle a medias.
     */
    public boolean guardarLineas(String nombre_archivo, ArrayList<String> lineas) {
        if (lineas == null) {
            System.out.println("Error: no hay lineas que guardar");
            return false;
        }
        this.crearCarpeta();
        BufferedWriter escritor = null;
        boolean resultado = false;
        try {
            escritor = new BufferedWriter(new FileWriter(this.rutaDe(nombre_archivo), false));
            for (int i = 0; i < lineas.size(); i++) {
                escritor.write(lineas.get(i));
                escritor.newLine();
            }
            resultado = true;
            System.out.println("Se guardaron " + lineas.size() + " registro(s) en "
                    + this.rutaDe(nombre_archivo));
        } catch (IOException e) {
            System.out.println("Error de escritura: " + e.getMessage());
        } finally {
            try {
                if (escritor != null) {
                    escritor.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo: " + e.getMessage());
            }
            System.out.println("[Operacion de escritura finalizada]");
        }
        return resultado;
    }

    // Agrega una sola linea al final del archivo: FileWriter en modo true
    public boolean agregarLinea(String nombre_archivo, String linea) {
        if (linea == null || linea.trim().length() == 0) {
            System.out.println("Error: la linea no puede estar vacia");
            return false;
        }
        this.crearCarpeta();
        BufferedWriter escritor = null;
        boolean resultado = false;
        try {
            escritor = new BufferedWriter(new FileWriter(this.rutaDe(nombre_archivo), true));
            escritor.write(linea);
            escritor.newLine();
            resultado = true;
        } catch (IOException e) {
            System.out.println("Error de escritura: " + e.getMessage());
        } finally {
            try {
                if (escritor != null) {
                    escritor.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo: " + e.getMessage());
            }
        }
        return resultado;
    }

    /**
     * Devuelve las lineas del archivo. Si el archivo todavia no existe avisa y
     * devuelve la lista vacia, para que la primera ejecucion del programa no
     * termine con una excepcion. readLine() devuelve null al llegar al final.
     */
    public ArrayList<String> leerLineas(String nombre_archivo) {
        ArrayList<String> lineas = new ArrayList();
        if (!this.existeArchivo(nombre_archivo)) {
            System.out.println("Aviso: el archivo " + this.rutaDe(nombre_archivo)
                    + " todavia no existe");
            return lineas;
        }
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new FileReader(this.rutaDe(nombre_archivo)));
            String linea = lector.readLine();
            while (linea != null) {
                if (linea.trim().length() > 0) {
                    lineas.add(linea);
                }
                linea = lector.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
        } finally {
            try {
                if (lector != null) {
                    lector.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo: " + e.getMessage());
            }
            System.out.println("[Operacion de lectura finalizada]");
        }
        return lineas;
    }

    public int contarLineas(String nombre_archivo) {
        return this.leerLineas(nombre_archivo).size();
    }

    // Muestra el contenido del archivo numerado, para la evidencia de la practica
    public void mostrarArchivo(String nombre_archivo) {
        ArrayList<String> lineas = this.leerLineas(nombre_archivo);
        if (lineas.isEmpty()) {
            System.out.println("El archivo no tiene contenido");
            return;
        }
        System.out.println("--- Contenido de " + this.rutaDe(nombre_archivo)
                + " (" + this.tamanioArchivo(nombre_archivo) + " bytes) ---");
        for (int i = 0; i < lineas.size(); i++) {
            System.out.println((i + 1) + ": " + lineas.get(i));
        }
        System.out.println("--- Fin del archivo ---");
    }

    public boolean eliminarArchivo(String nombre_archivo) {
        File archivo = new File(this.rutaDe(nombre_archivo));
        if (!archivo.exists()) {
            System.out.println("Error: el archivo " + nombre_archivo + " no existe");
            return false;
        }
        boolean borrado = archivo.delete();
        if (borrado) {
            System.out.println("Archivo eliminado: " + nombre_archivo);
        } else {
            System.out.println("Error: no se pudo eliminar " + nombre_archivo);
        }
        return borrado;
    }
}
