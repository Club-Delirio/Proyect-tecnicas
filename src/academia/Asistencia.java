package academia;

import java.time.LocalDate;

/**
 * Asistencia de un estudiante a una sesion de un ciclo. Relaciona al
 * estudiante con el ciclo en una fecha determinada y guarda el estado de esa
 * sesion.
 *
 * Estados: PRESENTE, TARDANZA, AUSENTE o JUSTIFICADO. Los tres primeros
 * menos AUSENTE cuentan como asistencia efectiva.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class Asistencia {
    private String codigo;
    private Estudiante estudiante;
    private Ciclo ciclo;
    private LocalDate fecha;
    private String estado;
    private String observacion;

    public Asistencia(Estudiante estudiante, Ciclo ciclo, LocalDate fecha) {
        this.estudiante = estudiante;
        this.ciclo = ciclo;
        this.setFecha(fecha);
        this.observacion = "";
    }

    // Cuenta como asistencia efectiva todo lo que no sea AUSENTE
    public boolean cuentaComoAsistio() {
        return this.estado.equals("PRESENTE") || this.estado.equals("TARDANZA")
                || this.estado.equals("JUSTIFICADO");
    }

    public void verDatos() {
        System.out.println("ASISTENCIA " + this.codigo + "  " + this.fecha
                + "  " + this.estudiante.getCodigo() + " " + this.estudiante.getNombreEnNomina()
                + "  Ciclo: " + this.ciclo.getCodigo()
                + "  Estado: " + this.estado
                + (this.observacion.isEmpty() ? "" : "  Obs: " + this.observacion));
    }

    // Linea del archivo: codigo;cod_estudiante;cod_ciclo;fecha;estado;observacion
    public String convertirALinea() {
        return this.codigo + ";" + this.estudiante.getCodigo() + ";" + this.ciclo.getCodigo() + ";"
                + this.fecha + ";" + this.estado + ";" + this.observacion;
    }

    public String getCodigo() {
        return codigo;
    }

    // Codigo correlativo de la asistencia (ASI-1, ASI-2, ...)
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    // No se puede registrar la asistencia de una sesion que todavia no ocurrio
    public void setFecha(LocalDate fecha) {
        if (fecha.isAfter(LocalDate.now())) {
            System.out.println("Error: no se puede registrar la asistencia de una fecha futura");
        } else {
            this.fecha = fecha;
        }
    }

    public String getEstado() {
        return estado;
    }

    // Solo PRESENTE, TARDANZA, AUSENTE o JUSTIFICADO
    public void setEstado(String estado) {
        estado = estado.toUpperCase();
        if (estado.equals("PRESENTE") || estado.equals("TARDANZA")
                || estado.equals("AUSENTE") || estado.equals("JUSTIFICADO")) {
            this.estado = estado;
        } else {
            System.out.println("Error: el estado debe ser PRESENTE, TARDANZA, AUSENTE o JUSTIFICADO");
        }
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion.trim();
    }
}
