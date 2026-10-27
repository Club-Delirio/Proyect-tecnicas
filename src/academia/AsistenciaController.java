package academia;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controlador del registro de asistencia. Concentra el alta, la busqueda, el
 * listado y el calculo del porcentaje de asistencia de cada estudiante.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class AsistenciaController {

    ArrayList<Asistencia> lista = new ArrayList();

    /**
     * Agrega un registro de asistencia comprobando que no exista ya uno del
     * mismo estudiante, en el mismo ciclo y en la misma fecha.
     */
    public boolean agregarAsistencia(Asistencia nueva_asistencia) {
        if (nueva_asistencia == null) {
            System.out.println("Error: no hay ninguna asistencia que agregar");
            return false;
        }
        if (nueva_asistencia.getEstudiante() == null || nueva_asistencia.getCiclo() == null
                || nueva_asistencia.getFecha() == null) {
            System.out.println("Error: la asistencia esta incompleta");
            return false;
        }
        if (this.existeRegistro(nueva_asistencia.getEstudiante(), nueva_asistencia.getCiclo(),
                nueva_asistencia.getFecha())) {
            System.out.println("Error: ya se registro la asistencia de "
                    + nueva_asistencia.getEstudiante().getCodigo() + " en "
                    + nueva_asistencia.getCiclo().getCodigo() + " el "
                    + nueva_asistencia.getFecha());
            return false;
        }
        nueva_asistencia.setCodigo("ASI-" + (this.lista.size() + 1));
        this.lista.add(nueva_asistencia);
        return true;
    }

    /**
     * Agrega un registro recuperado de asistencias.txt. No asigna codigo
     * nuevo, porque el del archivo es el que debe conservarse.
     */
    public boolean agregarDesdeArchivo(Asistencia asistencia_guardada) {
        if (asistencia_guardada == null) {
            return false;
        }
        this.lista.add(asistencia_guardada);
        return true;
    }

    public void listarAsistencias() {
        if (this.lista.isEmpty()) {
            System.out.println("No hay asistencias registradas");
            return;
        }
        for (int i = 0; i < this.lista.size(); i++) {
            this.lista.get(i).verDatos();
        }
    }

    /**
     * Sobrecarga: lista solo los registros con el estado indicado, que puede
     * ser PRESENTE, TARDANZA, AUSENTE o JUSTIFICADO.
     */
    public void listarAsistencias(String estado) {
        int encontrados = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Asistencia a = this.lista.get(i);
            if (a.getEstado() != null && a.getEstado().equalsIgnoreCase(estado)) {
                a.verDatos();
                encontrados = encontrados + 1;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay asistencias con estado " + estado);
        }
    }

    /**
     * Sobrecarga: lista los registros de un estudiante en un ciclo.
     */
    public void listarAsistencias(Estudiante estudiante, Ciclo ciclo) {
        int encontrados = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Asistencia a = this.lista.get(i);
            if (a.getEstudiante() == estudiante && a.getCiclo() == ciclo) {
                a.verDatos();
                encontrados = encontrados + 1;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay asistencias de ese estudiante en ese ciclo");
        }
    }

    public boolean existeRegistro(Estudiante estudiante, Ciclo ciclo, LocalDate fecha) {
        for (int i = 0; i < this.lista.size(); i++) {
            Asistencia a = this.lista.get(i);
            if (a.getEstudiante() == estudiante && a.getCiclo() == ciclo
                    && a.getFecha().equals(fecha)) {
                return true;
            }
        }
        return false;
    }

    public Asistencia buscarAsistencia(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (int i = 0; i < this.lista.size(); i++) {
            Asistencia a = this.lista.get(i);
            if (a.getCodigo() != null && a.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return a;
            }
        }
        return null;
    }

    public Asistencia obtenerAsistencia(int posicion) {
        try {
            return this.lista.get(posicion);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: la posicion " + posicion + " no existe. La lista tiene "
                    + this.lista.size() + " asistencia(s)");
            return null;
        }
    }

    /**
     * Cuenta cuantas sesiones se registraron para un estudiante en un ciclo.
     */
    public int contarSesiones(Estudiante estudiante, Ciclo ciclo) {
        int contador = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Asistencia a = this.lista.get(i);
            if (a.getEstudiante() == estudiante && a.getCiclo() == ciclo) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    /**
     * Cuenta en cuantas de esas sesiones el estudiante si asistio. La tardanza
     * y la falta justificada cuentan como asistencia.
     */
    public int contarAsistio(Estudiante estudiante, Ciclo ciclo) {
        int contador = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Asistencia a = this.lista.get(i);
            if (a.getEstudiante() == estudiante && a.getCiclo() == ciclo
                    && a.cuentaComoAsistio()) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    /**
     * Porcentaje de asistencia del estudiante en el ciclo, de 0 a 100.
     * Devuelve -1 si no hay sesiones registradas.
     */
    public int calcularPorcentaje(Estudiante estudiante, Ciclo ciclo) {
        int sesiones = this.contarSesiones(estudiante, ciclo);
        if (sesiones == 0) {
            return -1;
        }
        return this.contarAsistio(estudiante, ciclo) * 100 / sesiones;
    }

    public int contarPorEstado(String estado) {
        int contador = 0;
        for (int i = 0; i < this.lista.size(); i++) {
            Asistencia a = this.lista.get(i);
            if (a.getEstado() != null && a.getEstado().equalsIgnoreCase(estado)) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    public int getCantidad() {
        return this.lista.size();
    }

    public ArrayList<Asistencia> getLista() {
        return this.lista;
    }
}
