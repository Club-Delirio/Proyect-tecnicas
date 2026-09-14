package semana5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import semana5.excepciones.PersonaNoEncontradaException;

/**
 * Controlador que gestiona las personas en memoria usando COLECCIONES.
 *
 *  - ArrayList: mantiene el orden de registro y permite recorrer y listar.
 *  - HashMap:   índice por número de documento para buscar en tiempo constante
 *               y para impedir documentos duplicados.
 *
 * Ambas estructuras se mantienen sincronizadas: todo lo que entra o sale de la
 * lista también entra o sale del mapa.
 */
public class PersonaController {

    private final ArrayList<Persona> lista;
    private final HashMap<String, Persona> indicePorDocumento;

    public PersonaController() {
        this.lista = new ArrayList<>();
        this.indicePorDocumento = new HashMap<>();
    }

    // ------------------------------------------------------------------
    // ALTA
    // ------------------------------------------------------------------

    /**
     * Agrega una persona a la colección.
     *
     * @return true si se agregó; false si el documento ya existía o el objeto
     *         llegó nulo o incompleto.
     */
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

    /** Sobrecarga: agrega varias personas de una sola llamada (varargs). */
    public int agregarPersona(Persona... personas) {
        int agregadas = 0;
        for (Persona p : personas) {
            if (agregarPersona(p)) {
                agregadas++;
            }
        }
        return agregadas;
    }

    // ------------------------------------------------------------------
    // CONSULTA
    // ------------------------------------------------------------------

    /**
     * Busca una persona por su número de documento.
     *
     * @throws PersonaNoEncontradaException si el documento no está registrado.
     */
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

    /**
     * Devuelve la persona ubicada en una posición de la lista.
     * Controla el índice para no propagar IndexOutOfBoundsException.
     */
    public Persona obtenerPorIndice(int indice) {
        try {
            return lista.get(indice);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("El registro solicitado no existe. "
                    + "La lista tiene " + lista.size() + " elemento(s).");
            return null;
        }
    }

    // ------------------------------------------------------------------
    // BAJA
    // ------------------------------------------------------------------

    /**
     * Elimina una persona por su número de documento.
     *
     * @throws PersonaNoEncontradaException si el documento no está registrado.
     */
    public void eliminarPorDocumento(String nroDocumento)
            throws PersonaNoEncontradaException {
        Persona persona = buscarPorDocumento(nroDocumento);
        lista.remove(persona);
        indicePorDocumento.remove(persona.getNroDocumento());
    }

    // ------------------------------------------------------------------
    // LISTADOS (métodos sobrecargados)
    // ------------------------------------------------------------------

    /** Lista todas las personas registradas. */
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

    /** Sobrecarga: lista solo las personas de un tipo de documento. */
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

    /** Sobrecarga: lista las personas dentro de un rango de edad. */
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

    // ------------------------------------------------------------------
    // OPERACIONES SOBRE LA COLECCIÓN
    // ------------------------------------------------------------------

    /** Ordena la lista alfabéticamente por apellido paterno y luego por nombre. */
    public void ordenarPorApellido() {
        lista.sort(Comparator
                .comparing((Persona p) -> p.getPaterno() == null ? "" : p.getPaterno().toUpperCase())
                .thenComparing(p -> p.getNombre() == null ? "" : p.getNombre().toUpperCase()));
    }

    /** Cuenta cuántas personas hay por cada tipo de documento. */
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

    /** Devuelve una copia de la lista para que nadie modifique la colección interna. */
    public List<Persona> getLista() {
        return new ArrayList<>(lista);
    }
}
