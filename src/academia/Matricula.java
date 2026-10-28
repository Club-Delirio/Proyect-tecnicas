package academia;

import java.time.LocalDate;

/**
 * Matricula de un estudiante. Relaciona al estudiante, al docente que sera su
 * tutor y al ciclo, y calcula el precio final:
 *
 *   costo base
 *   - descuento de campana (el que configura la administracion)
 *   - descuento propio del tipo de ciclo (virtual o vacacional)
 *   + recargo por fraccionamiento (2 cuotas 4%, 3 cuotas 7%, 4 cuotas 10%)
 *   = PRECIO FINAL
 *
 * Tambien se usa para cotizar: se crea la matricula, se calcula y no se
 * registra.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class Matricula {
    private String codigo;
    private LocalDate fecha;
    private Estudiante estudiante;
    private Docente tutor;
    private Ciclo ciclo;
    private String forma_pago;
    private int nro_cuotas;
    private String nombre_campana;
    private double desc_campana;         // porcentaje
    private double monto_campana;
    private double monto_descuento;
    private double subtotal;
    private double recargo;
    private double precio_final;
    private double cuota_mensual;

    public Matricula(Estudiante estudiante, Docente tutor, Ciclo ciclo, String nombre_campana,
            double desc_campana) {
        this.estudiante = estudiante;
        this.tutor = tutor;
        this.ciclo = ciclo;
        this.nombre_campana = nombre_campana;
        this.desc_campana = desc_campana;
        this.fecha = LocalDate.now();
    }

    // Recargo por fraccionamiento: contado 0%, 2 cuotas 4%, 3 cuotas 7%, 4 cuotas 10%
    public double calcularPorcentajeRecargo() {
        if (this.forma_pago.equals("CONTADO")) {
            return 0;
        } else if (this.nro_cuotas == 2) {
            return 4;
        } else if (this.nro_cuotas == 3) {
            return 7;
        } else {
            return 10;
        }
    }

    // ciclo.calcularDescuento() se ejecuta segun el objeto real (CicloRegular o CursoIntensivo): polimorfismo
    public void calcularPrecioFinal() {
        double base = this.ciclo.getCosto_base();
        this.monto_campana = redondear(base * this.desc_campana / 100);
        this.monto_descuento = redondear(base * this.ciclo.calcularDescuento() / 100);
        this.subtotal = redondear(base - this.monto_campana - this.monto_descuento);
        this.recargo = redondear(this.subtotal * this.calcularPorcentajeRecargo() / 100);
        this.precio_final = redondear(this.subtotal + this.recargo);
        if (this.forma_pago.equals("FRACCIONADO")) {
            this.cuota_mensual = redondear(this.precio_final / this.nro_cuotas);
        }
    }

    private double redondear(double numero) {
        return Math.round(numero * 100) / 100.0;
    }

    // Muestra el desglose del precio para que el apoderado vea de donde sale cada sol
    public void verDetallePrecio() {
        System.out.println("   ---- DETALLE DEL PRECIO ----");
        System.out.println("   " + this.ciclo.getTipo() + ": " + this.ciclo.getNombre()
                + " (" + this.ciclo.calcularCategoria() + ", " + this.ciclo.getModalidad() + ")");
        System.out.println("   Costo base             : S/ " + String.format("%.2f", this.ciclo.getCosto_base()));
        System.out.println("   (-) Desc. campana      : S/ " + String.format("%.2f", this.monto_campana)
                + "  [" + this.nombre_campana + " " + this.desc_campana + "%]");
        System.out.println("   (-) Desc. del ciclo    : S/ " + String.format("%.2f", this.monto_descuento)
                + "  [" + this.ciclo.calcularDescuento() + "%]");
        System.out.println("   Subtotal               : S/ " + String.format("%.2f", this.subtotal));
        System.out.println("   (+) Recargo fraccion.  : S/ " + String.format("%.2f", this.recargo)
                + "  [" + this.calcularPorcentajeRecargo() + "%]");
        System.out.println("   PRECIO FINAL           : S/ " + String.format("%.2f", this.precio_final));
        if (this.forma_pago.equals("FRACCIONADO")) {
            System.out.println("   Cuota mensual          : S/ " + String.format("%.2f", this.cuota_mensual)
                    + " x " + this.nro_cuotas + " cuotas");
        }
    }

    public void verDatos() {
        System.out.println("MATRICULA " + this.codigo + "  Fecha: " + this.fecha
                + "  Forma de pago: " + this.forma_pago);
        System.out.println("   Estudiante: " + this.estudiante.getNombreEnNomina()
                + " (" + this.estudiante.getCodigo() + ")");
        System.out.println("   Tutor     : " + this.tutor.getNombreEnNomina()
                + " (" + this.tutor.getCodigo() + ", " + this.tutor.getEspecialidad() + ")");
        this.verDetallePrecio();
    }

    // Linea del archivo: codigo;fecha;cod_estudiante;cod_tutor;cod_ciclo;forma_pago;cuotas;campana;desc;precio_final
    public String convertirALinea() {
        return this.codigo + ";" + this.fecha + ";" + this.estudiante.getCodigo() + ";"
                + this.tutor.getCodigo() + ";" + this.ciclo.getCodigo() + ";"
                + this.forma_pago + ";" + this.nro_cuotas + ";" + this.nombre_campana + ";"
                + this.desc_campana + ";" + this.precio_final;
    }

    public String getCodigo() {
        return codigo;
    }

    // Codigo correlativo de la matricula (MAT-1, MAT-2, ...)
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    // Se usa al recuperar una matricula del archivo, para conservar su fecha original
    public void setFecha(LocalDate fecha) {
        if (fecha.isAfter(LocalDate.now())) {
            System.out.println("Error: la fecha de la matricula no puede ser futura");
        } else {
            this.fecha = fecha;
        }
    }

    public String getForma_pago() {
        return forma_pago;
    }

    // Solo CONTADO o FRACCIONADO
    public void setForma_pago(String forma_pago) {
        if (forma_pago.equalsIgnoreCase("CONTADO") || forma_pago.equalsIgnoreCase("FRACCIONADO")) {
            this.forma_pago = forma_pago.toUpperCase();
        } else {
            System.out.println("Error: la forma de pago debe ser CONTADO o FRACCIONADO");
        }
    }

    public int getNro_cuotas() {
        return nro_cuotas;
    }

    // Solo se fracciona en 2, 3 o 4 cuotas
    public void setNro_cuotas(int nro_cuotas) {
        if (nro_cuotas == 2 || nro_cuotas == 3 || nro_cuotas == 4) {
            this.nro_cuotas = nro_cuotas;
        } else {
            System.out.println("Error: solo se fracciona en 2, 3 o 4 cuotas");
        }
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Docente getTutor() {
        return tutor;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public double getMonto_campana() {
        return monto_campana;
    }

    public double getMonto_descuento() {
        return monto_descuento;
    }

    public double getRecargo() {
        return recargo;
    }

    public double getPrecio_final() {
        return precio_final;
    }

    public double getCuota_mensual() {
        return cuota_mensual;
    }
}
