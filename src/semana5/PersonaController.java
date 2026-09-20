package semana5;

import java.util.ArrayList;

/**
 *
 * @author jhose
 */
public class PersonaController {
    ArrayList<Persona> lista = new ArrayList();

    public boolean AgregarPersona(Persona nuevapersona) {
        if (nuevapersona.getNro_documento() == null) {
            System.out.println("Error: la persona no tiene numero de documento");
            return false;
        }
        if (this.buscarPersona(nuevapersona.getNro_documento()) != null) {
            System.out.println("Error: el documento " + nuevapersona.getNro_documento()
                    + " ya esta registrado");
            return false;
        }
        lista.add(nuevapersona);
        return true;
    }

    public void listarPersonas() {
        if (lista.isEmpty()) {
            System.out.println("No hay personas registradas");
            return;
        }
        System.out.println("La lista contiene las siguientes personas es: ");
        for (int i = 0; i < lista.size(); i++) {
            Persona p = lista.get(i);
            p.VerDatos();
        }
    }

    public void listarPersonas(String tipo_doc) {
        int encontrados = 0;
        System.out.println("Personas con tipo de documento " + tipo_doc + ":");
        for (int i = 0; i < lista.size(); i++) {
            Persona p = lista.get(i);
            if (p.getTipo_doc() != null && p.getTipo_doc().equals(tipo_doc)) {
                p.VerDatos();
                encontrados++;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay personas con ese tipo de documento");
        }
    }

    public void listarPersonas(int edad_minima, int edad_maxima) {
        int encontrados = 0;
        System.out.println("Personas entre " + edad_minima + " y " + edad_maxima + " años:");
        for (int i = 0; i < lista.size(); i++) {
            Persona p = lista.get(i);
            int edad = p.CalcularEdad();
            if (edad >= edad_minima && edad <= edad_maxima) {
                p.VerDatos();
                encontrados++;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay personas en ese rango de edad");
        }
    }

    public Persona buscarPersona(String nro_documento) {
        for (int i = 0; i < lista.size(); i++) {
            Persona p = lista.get(i);
            if (p.getNro_documento() != null && p.getNro_documento().equals(nro_documento)) {
                return p;
            }
        }
        return null;
    }

    public Persona obtenerPersona(int posicion) {
        try {
            return lista.get(posicion);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: la posicion " + posicion + " no existe. La lista tiene "
                    + lista.size() + " persona(s)");
            return null;
        }
    }

    public void eliminarPersona(String nro_documento) {
        Persona p = this.buscarPersona(nro_documento);
        if (p == null) {
            System.out.println("Error: no se encontro el documento " + nro_documento);
            return;
        }
        lista.remove(p);
        System.out.println("Persona eliminada. Quedan " + lista.size() + " persona(s)");
    }

    public void ordenarPorApellido() {
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                String a = lista.get(i).getApe_paterno();
                String b = lista.get(j).getApe_paterno();
                if (a != null && b != null && a.compareToIgnoreCase(b) > 0) {
                    Persona temporal = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, temporal);
                }
            }
        }
        System.out.println("Lista ordenada por apellido paterno");
    }

    public int contarPorTipo(String tipo_doc) {
        int contador = 0;
        for (int i = 0; i < lista.size(); i++) {
            Persona p = lista.get(i);
            if (p.getTipo_doc() != null && p.getTipo_doc().equals(tipo_doc)) {
                contador++;
            }
        }
        return contador;
    }

    public int getCantidad() {
        return lista.size();
    }
}
