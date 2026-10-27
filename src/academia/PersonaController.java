package academia;

import java.util.ArrayList;

/**
 * Controlador de la coleccion de personas. Guarda en una sola lista a los
 * estudiantes y a los docentes mediante upcasting, y concentra las operaciones
 * de alta, busqueda, listado, eliminacion, ordenamiento y conteo.
 *
 * El flujo del programa no toca nunca el ArrayList de forma directa: siempre
 * pasa por estos metodos, de modo que las reglas (no duplicar un documento, no
 * repetir un codigo) se apliquen en un solo lugar.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class PersonaController {

    ArrayList<Persona> lista = new ArrayList();

    /**
     * Agrega una persona comprobando que tenga documento y que ese documento
     * no este registrado ya.
     */
    public boolean agregarPersona(Persona nueva_persona) {
        if (nueva_persona == null) {
            System.out.println("Error: no hay ninguna persona que agregar");
            return false;
        }
        if (nueva_persona.getNro_doc() == null) {
            System.out.println("Error: la persona no tiene numero de documento");
            return false;
        }
        if (this.buscarPorDocumento(nueva_persona.getNro_doc()) != null) {
            System.out.println("Error: el documento " + nueva_persona.getNro_doc()
                    + " ya esta registrado");
            return false;
        }
        this.lista.add(nueva_persona);
        return true;
    }

    /**
     * Lista a todas las personas. Cada objeto responde con su propia version
     * de verDatos(): eso es polimorfismo.
     */
    public void listarPersonas() {
        if (this.lista.isEmpty()) {
            System.out.println("No hay personas registradas");
            return;
        }
        for (int i = 0; i < this.lista.size(); i++) {
            this.lista.get(i).verDatos();
        }
    }

    /**
     * Sobrecarga: lista solo las personas del tipo indicado, ESTUDIANTE o
     * DOCENTE. Comprueba el tipo real con instanceof.
     */
    public void listarPersonas(String tipo) {
        int encontrados = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Persona p = this.lista.get(i);
            if (tipo.equalsIgnoreCase("ESTUDIANTE") && p instanceof Estudiante) {
                p.verDatos();
                encontrados = encontrados + 1;
            } else if (tipo.equalsIgnoreCase("DOCENTE") && p instanceof Docente) {
                p.verDatos();
                encontrados = encontrados + 1;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay personas de tipo " + tipo);
        }
    }

    /**
     * Sobrecarga: lista los estudiantes cuya edad esta en el rango indicado.
     */
    public void listarPersonas(int edad_minima, int edad_maxima) {
        int encontrados = 0;
        System.out.println("Estudiantes entre " + edad_minima + " y " + edad_maxima + " anios:");
        for (int i = 0; i < this.lista.size(); i++) {
            Persona p = this.lista.get(i);
            if (p instanceof Estudiante) {
                Estudiante e = (Estudiante) p;   // downcasting
                int edad = e.calcularEdad();
                if (edad >= edad_minima && edad <= edad_maxima) {
                    e.verDatos();
                    encontrados = encontrados + 1;
                }
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay estudiantes en ese rango de edad");
        }
    }

    public Persona buscarPorDocumento(String nro_doc) {
        for (int i = 0; i < this.lista.size(); i++) {
            Persona p = this.lista.get(i);
            if (p.getNro_doc() != null && p.getNro_doc().equals(nro_doc)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Busca un estudiante por su codigo. Comprueba el tipo real antes de
     * hacer el downcasting, de modo que no se lance ClassCastException.
     */
    public Estudiante buscarEstudiante(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (int i = 0; i < this.lista.size(); i++) {
            Persona p = this.lista.get(i);
            if (p instanceof Estudiante) {
                Estudiante e = (Estudiante) p;
                if (e.getCodigo() != null && e.getCodigo().equalsIgnoreCase(codigo.trim())) {
                    return e;
                }
            }
        }
        return null;
    }

    public Docente buscarDocente(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (int i = 0; i < this.lista.size(); i++) {
            Persona p = this.lista.get(i);
            if (p instanceof Docente) {
                Docente d = (Docente) p;
                if (d.getCodigo() != null && d.getCodigo().equalsIgnoreCase(codigo.trim())) {
                    return d;
                }
            }
        }
        return null;
    }

    /**
     * Comprueba si el codigo ya esta usado, sea de estudiante o de docente.
     */
    public boolean existeCodigo(String codigo) {
        return this.buscarEstudiante(codigo) != null || this.buscarDocente(codigo) != null;
    }

    public Persona obtenerPersona(int posicion) {
        try {
            return this.lista.get(posicion);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: la posicion " + posicion + " no existe. La lista tiene "
                    + this.lista.size() + " persona(s)");
            return null;
        }
    }

    public void eliminarPersona(String nro_doc) {
        Persona p = this.buscarPorDocumento(nro_doc);
        if (p == null) {
            System.out.println("Error: no se encontro el documento " + nro_doc);
            return;
        }
        this.lista.remove(p);
        System.out.println("Persona eliminada. Quedan " + this.lista.size() + " persona(s)");
    }

    /**
     * Ordena la lista por apellido paterno con el metodo de la burbuja.
     */
    public void ordenarPorApellido() {
        for (int i = 0; i < this.lista.size() - 1; i++) {
            for (int j = i + 1; j < this.lista.size(); j++) {
                String a = this.lista.get(i).getApe_paterno();
                String b = this.lista.get(j).getApe_paterno();
                if (a != null && b != null && a.compareToIgnoreCase(b) > 0) {
                    Persona temporal = this.lista.get(i);
                    this.lista.set(i, this.lista.get(j));
                    this.lista.set(j, temporal);
                }
            }
        }
    }

    public int contarEstudiantes() {
        int contador = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            if (this.lista.get(i) instanceof Estudiante) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    public int contarDocentes() {
        int contador = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            if (this.lista.get(i) instanceof Docente) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    /**
     * Suma lo que hay que pagar a todos los docentes del periodo.
     */
    public double calcularPlanillaDocente() {
        double total = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Persona p = this.lista.get(i);
            if (p instanceof Docente) {
                total = total + ((Docente) p).calcularPagoTotal();
            }
        }
        return total;
    }

    public int getCantidad() {
        return this.lista.size();
    }

    public ArrayList<Persona> getLista() {
        return this.lista;
    }
}
