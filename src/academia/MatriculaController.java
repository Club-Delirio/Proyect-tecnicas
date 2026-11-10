package academia;

import java.util.ArrayList;

/**
 * Controlador de las matriculas. Concentra el alta, la busqueda, el listado y
 * los totales del reporte financiero.
 *
 * El alta es el punto donde se aplican las dos reglas del RF-25: no se puede
 * matricular en un ciclo sin vacantes ni repetir la matricula del mismo
 * estudiante en el mismo ciclo.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturo
 * @author Rojas Valdivia, Jhann Frank
 */
public class MatriculaController {

    ArrayList<Matricula> lista = new ArrayList();

    /**
     * Agrega la matricula y aplica sus efectos: descuenta la vacante del
     * ciclo, la guarda en el historial del estudiante y acumula el monto al
     * docente tutor.
     */
    public boolean agregarMatricula(Matricula nueva_matricula) {
        if (nueva_matricula == null) {
            System.out.println("Error: no hay ninguna matricula que agregar");
            return false;
        }
        Estudiante e = nueva_matricula.getEstudiante();
        Docente d = nueva_matricula.getTutor();
        Ciclo c = nueva_matricula.getCiclo();
        if (e == null || d == null || c == null) {
            System.out.println("Error: la matricula esta incompleta");
            return false;
        }
        if (this.existeMatricula(e, c)) {
            System.out.println("Error: el estudiante " + e.getCodigo()
                    + " ya esta matriculado en " + c.getCodigo());
            return false;
        }
        if (!c.hayVacantes()) {
            System.out.println("SIN VACANTES: el ciclo " + c.getCodigo() + " esta "
                    + c.getEstado() + " y no admite mas matriculados");
            return false;
        }
        nueva_matricula.setCodigo(this.generarCodigo());
        this.lista.add(nueva_matricula);
        c.registrarMatriculado();
        e.agregarMatricula(c);
        d.registrarMatricula(nueva_matricula.getPrecio_final());
        return true;
    }

    /**
     * Agrega una matricula recuperada de matriculas.txt. A diferencia de
     * agregarMatricula(), no vuelve a descontar la vacante ni a asignar el
     * codigo, porque el contador de matriculados del ciclo y el codigo ya
     * vienen guardados en el archivo. Si se descontara otra vez, las vacantes
     * saldrian a la mitad.
     */
    public boolean agregarDesdeArchivo(Matricula matricula_guardada) {
        if (matricula_guardada == null) {
            return false;
        }
        this.lista.add(matricula_guardada);
        return true;
    }

    public void listarMatriculas() {
        if (this.lista.isEmpty()) {
            System.out.println("No hay matriculas registradas");
            return;
        }
        for (int i = 0; i < this.lista.size(); i++) {
            this.lista.get(i).verDatos();
        }
    }

    /**
     * Sobrecarga: lista solo las matriculas con la forma de pago indicada,
     * que puede ser CONTADO o FRACCIONADO.
     */
    public void listarMatriculas(String forma_pago) {
        int encontrados = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Matricula m = this.lista.get(i);
            if (m.getForma_pago() != null && m.getForma_pago().equalsIgnoreCase(forma_pago)) {
                m.verDatos();
                encontrados = encontrados + 1;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay matriculas con forma de pago " + forma_pago);
        }
    }

    /**
     * Sobrecarga: lista las matriculas de un ciclo.
     */
    public void listarMatriculas(Ciclo ciclo) {
        int encontrados = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Matricula m = this.lista.get(i);
            if (m.getCiclo() == ciclo) {
                m.verDatos();
                encontrados = encontrados + 1;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay matriculas en ese ciclo");
        }
    }

    public boolean existeMatricula(Estudiante estudiante, Ciclo ciclo) {
        for (int i = 0; i < this.lista.size(); i++) {
            Matricula m = this.lista.get(i);
            if (m.getEstudiante() == estudiante && m.getCiclo() == ciclo) {
                return true;
            }
        }
        return false;
    }

    public Matricula buscarMatricula(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (int i = 0; i < this.lista.size(); i++) {
            Matricula m = this.lista.get(i);
            if (m.getCodigo() != null && m.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return m;
            }
        }
        return null;
    }

    public Matricula obtenerMatricula(int posicion) {
        try {
            return this.lista.get(posicion);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: la posicion " + posicion + " no existe. La lista tiene "
                    + this.lista.size() + " matricula(s)");
            return null;
        }
    }

    /**
     * Genera el siguiente codigo MAT-n tomando el numero mas alto que ya
     * existe. No se usa el tamanio de la lista porque, despues de anular una
     * matricula, el tamanio baja y se repetiria un codigo que ya esta en uso.
     */
    public String generarCodigo() {
        int mayor = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            String codigo = this.lista.get(i).getCodigo();
            try {
                int numero = Integer.parseInt(codigo.substring(4));
                if (numero > mayor) {
                    mayor = numero;
                }
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                // un codigo con otro formato no cuenta para el correlativo
            }
        }
        return "MAT-" + (mayor + 1);
    }

    /**
     * Anula una matricula (RF-28). Solo se permite si todavia no se pago
     * ninguna cuota. Al anularla:
     * - la vacante vuelve al ciclo (y el ciclo se reabre si estaba lleno),
     * - el ciclo sale del historial del estudiante,
     * - el tutor deja de contar esa matricula,
     * - y sus cuotas desaparecen con ella, porque son parte de la matricula
     *   (composicion).
     */
    public boolean eliminarMatricula(String codigo) {
        Matricula m = this.buscarMatricula(codigo);
        if (m == null) {
            System.out.println("Error: no se encontro la matricula " + codigo);
            return false;
        }
        if (m.contarCuotasPagadas() > 0) {
            System.out.println("Error: la matricula " + m.getCodigo() + " ya tiene "
                    + m.contarCuotasPagadas() + " cuota(s) pagada(s); no se puede anular");
            return false;
        }
        m.getCiclo().liberarVacante();
        m.getEstudiante().quitarMatricula(m.getCiclo());
        m.getTutor().quitarMatricula(m.getPrecio_final());
        this.lista.remove(m);
        System.out.println("Matricula " + m.getCodigo() + " anulada. El ciclo " + m.getCiclo().getCodigo()
                + " tiene ahora " + m.getCiclo().vacantesDisponibles() + " vacante(s)");
        return true;
    }

    // Suma de lo efectivamente cobrado en cuotas pagadas (RF-27, RF-37)
    public double calcularMontoRecaudado() {
        double total = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            total = total + this.lista.get(i).calcularMontoPagado();
        }
        return Math.round(total * 100) / 100.0;
    }

    // Suma de lo que falta cobrar (RF-27, RF-37)
    public double calcularSaldoPorCobrar() {
        double total = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            total = total + this.lista.get(i).calcularSaldoPendiente();
        }
        return Math.round(total * 100) / 100.0;
    }

    /**
     * Ordena las matriculas por precio final, de mayor a menor, con el metodo
     * de la burbuja.
     */
    public void ordenarPorPrecio() {
        for (int i = 0; i < this.lista.size() - 1; i++) {
            for (int j = i + 1; j < this.lista.size(); j++) {
                if (this.lista.get(i).getPrecio_final() < this.lista.get(j).getPrecio_final()) {
                    Matricula temporal = this.lista.get(i);
                    this.lista.set(i, this.lista.get(j));
                    this.lista.set(j, temporal);
                }
            }
        }
    }

    public double calcularIngresoTotal() {
        double total = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            total = total + this.lista.get(i).getPrecio_final();
        }
        return total;
    }

    public double calcularTotalDescuentos() {
        double total = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            total = total + this.lista.get(i).getMonto_descuento();
        }
        return total;
    }

    public double calcularTotalRecargos() {
        double total = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            total = total + this.lista.get(i).getRecargo();
        }
        return total;
    }

    public int contarPorFormaPago(String forma_pago) {
        int contador = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Matricula m = this.lista.get(i);
            if (m.getForma_pago() != null && m.getForma_pago().equalsIgnoreCase(forma_pago)) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    public int contarPorTipo(String tipo) {
        int contador = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            if (this.lista.get(i).getCiclo().getTipo().equalsIgnoreCase(tipo)) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    public int contarPorCategoria(String categoria) {
        int contador = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            if (this.lista.get(i).getCiclo().calcularCategoria().equalsIgnoreCase(categoria)) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    public int getCantidad() {
        return this.lista.size();
    }

    public ArrayList<Matricula> getLista() {
        return this.lista;
    }
}
