package academia;

import java.time.LocalDate;

/**
 * Ciclo regular: ademas de los datos de Ciclo tiene turno, los meses que dura
 * y la cantidad de simulacros que incluye.
 *
 * Categoria por costo base: menos de S/ 600 BASICO, hasta S/ 1200 INTERMEDIO,
 * mas PREMIUM.
 * Descuento "Modalidad virtual": 8% para los ciclos regulares VIRTUALES.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class CicloRegular extends Ciclo {
    private String turno;
    private int meses_duracion;
    private int nro_simulacros;

    // Constructor vacio: los datos se ingresan con los setters
    public CicloRegular() {
    }

    public CicloRegular(String codigo, String nombre, LocalDate fec_inicio, LocalDate fec_fin,
            double costo_base, String modalidad, int vacantes, String turno,
            int meses_duracion, int nro_simulacros) {
        super(codigo, nombre, fec_inicio, fec_fin, costo_base, modalidad, vacantes);
        this.setTurno(turno);
        this.setMeses_duracion(meses_duracion);
        this.setNro_simulacros(nro_simulacros);
    }

    @Override
    public String getTipo() {
        return "CICLO REGULAR";
    }

    // Categoria por costo base: menos de 600 BASICO, hasta 1200 INTERMEDIO, mas PREMIUM
    @Override
    public String calcularCategoria() {
        if (this.costo_base < 600) {
            return "BASICO";
        } else if (this.costo_base <= 1200) {
            return "INTERMEDIO";
        } else {
            return "PREMIUM";
        }
    }

    // Descuento por modalidad virtual: 8%
    @Override
    public double calcularDescuento() {
        if (this.modalidad.equals("VIRTUAL")) {
            return 8;
        }
        return 0;
    }

    @Override
    public void verDatos() {
        System.out.println("CICLO REGULAR");
        this.verDatosGenerales();
        System.out.println("   Turno: " + this.turno + "  Duracion: " + this.meses_duracion
                + " meses  Simulacros incluidos: " + this.nro_simulacros);
    }

    // Linea del archivo: CICLO;codigo;nombre;inicio;fin;costo;modalidad;vacantes;matriculados;estado;turno;meses;simulacros
    @Override
    public String convertirALinea() {
        return "CICLO;" + this.datosComunesEnLinea() + ";" + this.turno + ";"
                + this.meses_duracion + ";" + this.nro_simulacros;
    }

    public String getTurno() {
        return turno;
    }

    // Solo MANANA, TARDE o NOCHE
    public void setTurno(String turno) {
        turno = turno.toUpperCase();
        if (turno.equals("MANANA") || turno.equals("TARDE") || turno.equals("NOCHE")) {
            this.turno = turno;
        } else {
            System.out.println("Error: el turno debe ser MANANA, TARDE o NOCHE");
        }
    }

    public int getMeses_duracion() {
        return meses_duracion;
    }

    // Un ciclo regular dura de 2 a 10 meses
    public void setMeses_duracion(int meses_duracion) {
        if (meses_duracion >= 2 && meses_duracion <= 10) {
            this.meses_duracion = meses_duracion;
        } else {
            System.out.println("Error: la duracion del ciclo regular debe estar entre 2 y 10 meses");
        }
    }

    public int getNro_simulacros() {
        return nro_simulacros;
    }

    // Al menos un simulacro por mes y como maximo cuatro por mes
    public void setNro_simulacros(int nro_simulacros) {
        if (this.meses_duracion == 0) {
            System.out.println("Error: primero debe ingresar la duracion en meses");
        } else if (nro_simulacros < this.meses_duracion) {
            System.out.println("Error: debe haber al menos un simulacro por mes, es decir "
                    + this.meses_duracion + " como minimo");
        } else if (nro_simulacros > this.meses_duracion * 4) {
            System.out.println("Error: no puede haber mas de cuatro simulacros por mes, es decir "
                    + (this.meses_duracion * 4) + " como maximo");
        } else {
            this.nro_simulacros = nro_simulacros;
        }
    }
}
