package semana5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import semana5.excepciones.PersonaNoEncontradaException;


public class PersonaController {

    private final ArrayList<Persona> lista;
    private final HashMap<String, Persona> indicePorDocumento;

    public PersonaController() {
        this.lista = new ArrayList<>();
        this.indicePorDocumento = new HashMap<>();
    }
    public boolean agregarPersona(Persona persona) {
        if (persona == null || persona.getNroDocumento() == null) {
            System.out.println("No se puede agregar: la persona no tiene documento registrado.");
            return false;
        }
        String clave = persona.getNroDocumento();
        if (indicePorDocumento.containsKey(clave)) {
            System.out.println("El documento " + clave + " ya está registrado.");
            return false;
        }
        lista.add(persona);
        indicePorDocumento.put(clave, persona);
        return true;
    }

   
    public int agregarPersona(Persona... personas) {
        int agregadas = 0;
        for (Persona p : personas) {
            if (agregarPersona(p)) {
                agregadas++;
            }
        }
        return agregadas;
    }

    public Persona buscarPorDocumento(String nroDocumento)
            throws PersonaNoEncontradaException {
        if (nroDocumento == null || nroDocumento.trim().isEmpty()) {
            throw new PersonaNoEncontradaException(
                    "Debe indicar un número de documento para buscar.");
        }
        Persona encontrada = indicePorDocumento.get(nroDocumento.trim());
        if (encontrada == null) {
            throw new PersonaNoEncontradaException(
                    "No se encontró ninguna persona con el documento " + nroDocumento + ".");
        }
        return encontrada;
    }
    public Persona obtenerPorIndice(int indice) {
        try {
            return lista.get(indice);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("El registro solicitado no existe. "
                    + "La lista tiene " + lista.size() + " elemento(s).");
            return null;
        }
    }
    public void eliminarPorDocumento(String nroDocumento)
            throws PersonaNoEncontradaException {
        Persona persona = buscarPorDocumento(nroDocumento);
        lista.remove(persona);
        indicePorDocumento.remove(persona.getNroDocumento());
    }
    public void listarPersonas() {
        if (lista.isEmpty()) {
            System.out.println("No hay registros para mostrar.");
            return;
        }
        System.out.println("Lista de personas registradas (" + lista.size() + "):");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + lista.get(i));
        }
    }
    public void listarPersonas(String tipoDocumento) {
        List<Persona> filtradas = new ArrayList<>();
        for (Persona p : lista) {
            if (p.getTipoDocumento() != null
                    && p.getTipoDocumento().equalsIgnoreCase(tipoDocumento)) {
                filtradas.add(p);
            }
        }
        if (filtradas.isEmpty()) {
            System.out.println("No hay registros con tipo de documento " + tipoDocumento + ".");
            return;
        }
        System.out.println("Personas con " + tipoDocumento.toUpperCase()
                + " (" + filtradas.size() + "):");
        for (Persona p : filtradas) {
            System.out.println("  - " + p);
        }
    }
    public void listarPersonas(int edadMinima, int edadMaxima) {
        List<Persona> filtradas = new ArrayList<>();
        for (Persona p : lista) {
            int edad = p.calcularEdad();
            if (edad >= edadMinima && edad <= edadMaxima) {
                filtradas.add(p);
            }
        }
        if (filtradas.isEmpty()) {
            System.out.println("No hay personas entre " + edadMinima
                    + " y " + edadMaxima + " años.");
            return;
        }
        System.out.println("Personas entre " + edadMinima + " y " + edadMaxima
                + " años (" + filtradas.size() + "):");
        for (Persona p : filtradas) {
            System.out.println("  - " + p);
        }
    }
    public void ordenarPorApellido() {
        lista.sort(Comparator
                .comparing((Persona p) -> p.getPaterno() == null ? "" : p.getPaterno().toUpperCase())
                .thenComparing(p -> p.getNombre() == null ? "" : p.getNombre().toUpperCase()));
    }
    public Map<String, Integer> contarPorTipoDocumento() {
        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (Persona p : lista) {
            String tipo = p.getTipoDocumento() == null ? "SIN TIPO" : p.getTipoDocumento();
            conteo.put(tipo, conteo.getOrDefault(tipo, 0) + 1);
        }
        return conteo;
    }

    public int getCantidad() {
        return lista.size();
    }

    public boolean estaVacia() {
        return lista.isEmpty();
    }
    public List<Persona> getLista() {
        return new ArrayList<>(lista);
    }
}
