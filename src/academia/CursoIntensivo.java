package academia;

import java.time.LocalDate;

/**
 * Curso intensivo: ademas de los datos de Ciclo tiene el area a la que se
 * orienta, las semanas que dura y las horas diarias de clase.
 *
 * Categoria: si tiene 6 o mas horas diarias siempre es PREMIUM; si no, por
 * costo base: menos de S/ 400 BASICO, hasta S/ 900 INTERMEDIO, mas PREMIUM.
 * Descuento "Vacacional": 6% para los cursos de 4 semanas o menos.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class CursoIntensivo extends Ciclo {
    private String area;
    private int semanas_duracion;
    private int horas_diarias;

    // Constructor vacio: los datos se ingresan con los setters
    public CursoIntensivo() {
    }

    public CursoIntensivo(String codigo, String nombre, LocalDate fec_inicio, LocalDate fec_fin,
            double costo_base, String modalidad, int vacantes, String area,
            int semanas_duracion, int horas_diarias) {
        super(codigo, nombre, fec_inicio, fec_fin, costo_base, modalidad, vacantes);
        this.setArea(area);
        this.setSemanas_duracion(semanas_duracion);
        this.setHoras_diarias(horas_diarias);
    }

    @Override
    public String getTipo() {
        return "CURSO INTENSIVO";
    }

    // 6 horas diarias o mas siempre es PREMIUM; si no, por costo
    @Override
    public String calcularCategoria() {
        if (this.horas_diarias >= 6) {
            return "PREMIUM";
        }
        if (this.costo_base < 400) {
            return "BASICO";
        } else if (this.costo_base <= 900) {
            return "INTERMEDIO";
        } else {
            return "PREMIUM";
        }
    }

    // Descuento vacacional para cursos cortos: 6%
    @Override
    public double calcularDescuento() {
        if (this.semanas_duracion <= 4) {
            return 6;
        }
        return 0;
    }

    @Override
    public void verDatos() {
        System.out.println("CURSO INTENSIVO");
        this.verDatosGenerales();
        System.out.println("   Area: " + this.area + "  Duracion: " + this.semanas_duracion
                + " semanas  Horas diarias: " + this.horas_diarias);
    }

    // Linea del archivo: CURSO;codigo;nombre;inicio;fin;costo;modalidad;vacantes;matriculados;estado;area;semanas;horas
    @Override
    public String convertirALinea() {
        return "CURSO;" + this.datosComunesEnLinea() + ";" + this.area + ";"
                + this.semanas_duracion + ";" + this.horas_diarias;
    }

    public String getArea() {
        return area;
    }

    // Solo CIENCIAS, LETRAS o INGENIERIA
    public void setArea(String area) {
        area = area.toUpperCase();
        if (area.equals("CIENCIAS") || area.equals("LETRAS") || area.equals("INGENIERIA")) {
            this.area = area;
        } else {
            System.out.println("Error: el area debe ser CIENCIAS, LETRAS o INGENIERIA");
        }
    }

    public int getSemanas_duracion() {
        return semanas_duracion;
    }

    // Un curso intensivo dura de 2 a 12 semanas
    public void setSemanas_duracion(int semanas_duracion) {
        if (semanas_duracion >= 2 && semanas_duracion <= 12) {
            this.semanas_duracion = semanas_duracion;
        } else {
            System.out.println("Error: la duracion del curso intensivo debe estar entre 2 y 12 semanas");
        }
    }

    public int getHoras_diarias() {
        return horas_diarias;
    }

    // De 2 a 8 horas diarias
    public void setHoras_diarias(int horas_diarias) {
        if (horas_diarias >= 2 && horas_diarias <= 8) {
            this.horas_diarias = horas_diarias;
        } else {
            System.out.println("Error: las horas diarias deben estar entre 2 y 8");
        }
    }
}
