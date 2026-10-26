package academia;

import java.util.ArrayList;

/**
 * Controlador de la oferta academica. Guarda en una sola lista los ciclos
 * regulares y los cursos intensivos mediante upcasting, y concentra las
 * operaciones de alta, busqueda, listado, eliminacion, ordenamiento y conteo.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class CicloController {

    ArrayList<Ciclo> lista = new ArrayList();

    /**
     * Agrega un ciclo comprobando que tenga codigo y que ese codigo no este
     * registrado ya. Es la regla del RF-02.
     */
    public boolean agregarCiclo(Ciclo nuevo_ciclo) {
        if (nuevo_ciclo == null) {
            System.out.println("Error: no hay ningun ciclo que agregar");
            return false;
        }
        if (nuevo_ciclo.getCodigo() == null) {
            System.out.println("Error: el ciclo no tiene codigo");
            return false;
        }
        if (this.buscarCiclo(nuevo_ciclo.getCodigo()) != null) {
            System.out.println("Error: el codigo " + nuevo_ciclo.getCodigo()
                    + " ya esta registrado");
            return false;
        }
        this.lista.add(nuevo_ciclo);
        return true;
    }

    /**
     * Lista toda la oferta. Cada objeto responde con su propia version de
     * verDatos(): eso es polimorfismo.
     */
    public void listarCiclos() {
        if (this.lista.isEmpty()) {
            System.out.println("No hay ciclos ni cursos registrados");
            return;
        }
        for (int i = 0; i < this.lista.size(); i++) {
            this.lista.get(i).verDatos();
        }
    }

    /**
     * Sobrecarga: lista solo los ciclos de la categoria indicada, que puede
     * ser BASICO, INTERMEDIO o PREMIUM.
     */
    public void listarCiclos(String categoria) {
        int encontrados = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Ciclo c = this.lista.get(i);
            if (c.calcularCategoria().equalsIgnoreCase(categoria)) {
                c.verDatos();
                encontrados = encontrados + 1;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay ciclos de categoria " + categoria);
        }
    }

    /**
     * Sobrecarga: lista los ciclos cuyo costo base esta en el rango indicado.
     */
    public void listarCiclos(double costo_minimo, double costo_maximo) {
        int encontrados = 0;
        System.out.println("Ciclos entre S/ " + costo_minimo + " y S/ " + costo_maximo + ":");
        for (int i = 0; i < this.lista.size(); i++) {
            Ciclo c = this.lista.get(i);
            if (c.getCosto_base() >= costo_minimo && c.getCosto_base() <= costo_maximo) {
                c.verDatos();
                encontrados = encontrados + 1;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay ciclos en ese rango de costo");
        }
    }

    public Ciclo buscarCiclo(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (int i = 0; i < this.lista.size(); i++) {
            Ciclo c = this.lista.get(i);
            if (c.getCodigo() != null && c.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return c;
            }
        }
        return null;
    }

    public Ciclo obtenerCiclo(int posicion) {
        try {
            return this.lista.get(posicion);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: la posicion " + posicion + " no existe. La lista tiene "
                    + this.lista.size() + " ciclo(s)");
            return null;
        }
    }

    public void eliminarCiclo(String codigo) {
        Ciclo c = this.buscarCiclo(codigo);
        if (c == null) {
            System.out.println("Error: no se encontro el ciclo " + codigo);
            return;
        }
        if (c.getMatriculados() > 0) {
            System.out.println("Error: el ciclo " + codigo + " tiene " + c.getMatriculados()
                    + " matriculado(s) y no se puede eliminar");
            return;
        }
        this.lista.remove(c);
        System.out.println("Ciclo eliminado. Quedan " + this.lista.size() + " ciclo(s)");
    }

    /**
     * Ordena la oferta por costo base, de menor a mayor, con el metodo de la
     * burbuja.
     */
    public void ordenarPorCosto() {
        for (int i = 0; i < this.lista.size() - 1; i++) {
            for (int j = i + 1; j < this.lista.size(); j++) {
                if (this.lista.get(i).getCosto_base() > this.lista.get(j).getCosto_base()) {
                    Ciclo temporal = this.lista.get(i);
                    this.lista.set(i, this.lista.get(j));
                    this.lista.set(j, temporal);
                }
            }
        }
    }

    public int contarPorCategoria(String categoria) {
        int contador = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            if (this.lista.get(i).calcularCategoria().equalsIgnoreCase(categoria)) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    public int contarPorTipo(String tipo) {
        int contador = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            if (this.lista.get(i).getTipo().equalsIgnoreCase(tipo)) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    /**
     * Devuelve cuantas vacantes quedan libres en toda la oferta.
     */
    public int contarVacantesLibres() {
        int total = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            total = total + this.lista.get(i).vacantesDisponibles();
        }
        return total;
    }

    public int getCantidad() {
        return this.lista.size();
    }

    public ArrayList<Ciclo> getLista() {
        return this.lista;
    }
}
