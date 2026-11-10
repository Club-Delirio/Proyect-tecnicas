package academia;

import java.time.LocalDate;
import java.util.ArrayList;

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
 * Relaciones:
 * - ASOCIACION con Estudiante, Docente y Ciclo: la matricula guarda una
 *   referencia a cada uno, que ya existian antes (clase de asociacion).
 * - COMPOSICION con Cuota: la matricula crea sus propias cuotas al calcular el
 *   precio (generarCronograma) y las cuotas no existen fuera de ella (RF-27).
 * - Implementa la interfaz Registrable.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class Matricula implements Registrable {
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
    private ArrayList<Cuota> cuotas = new ArrayList(); // composicion: nacen y mueren con la matricula

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
        this.generarCronograma();
    }

    /**
     * COMPOSICION (RF-27): la matricula crea sus cuotas con new dentro de si
     * misma. Al contado hay una sola cuota que vence el dia de la matricula;
     * fraccionada hay una por mes. La ultima cuota absorbe el redondeo para
     * que la suma de las cuotas sea exactamente el precio final.
     */
    private void generarCronograma() {
        this.cuotas.clear();
        if (this.forma_pago.equals("CONTADO")) {
            this.cuotas.add(new Cuota(1, this.precio_final, this.fecha));
        } else {
            double acumulado = 0;
            for (int i = 1; i <= this.nro_cuotas; i++) {
                double monto = this.cuota_mensual;
                if (i == this.nro_cuotas) {
                    monto = redondear(this.precio_final - acumulado);
                }
                this.cuotas.add(new Cuota(i, monto, this.fecha.plusMonths(i - 1)));
                acumulado = redondear(acumulado + monto);
            }
        }
    }

    // Busca una cuota por su numero; devuelve null si no existe
    public Cuota buscarCuota(int numero) {
        for (int i = 0; i < this.cuotas.size(); i++) {
            if (this.cuotas.get(i).getNumero() == numero) {
                return this.cuotas.get(i);
            }
        }
        return null;
    }

    // Registra el pago de una cuota con la fecha de hoy (sobrecarga)
    public boolean registrarPagoCuota(int numero) {
        return this.registrarPagoCuota(numero, LocalDate.now());
    }

    // Registra el pago de una cuota en una fecha dada; se usa tambien al recuperar del archivo
    public boolean registrarPagoCuota(int numero, LocalDate fecha_pago) {
        Cuota c = this.buscarCuota(numero);
        if (c == null) {
            System.out.println("Error: la matricula " + this.codigo + " no tiene la cuota " + numero);
            return false;
        }
        return c.registrarPago(fecha_pago);
    }

    public int contarCuotasPagadas() {
        int contador = 0;
        for (int i = 0; i < this.cuotas.size(); i++) {
            if (this.cuotas.get(i).isPagada()) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    public double calcularMontoPagado() {
        double total = 0;
        for (int i = 0; i < this.cuotas.size(); i++) {
            if (this.cuotas.get(i).isPagada()) {
                total = total + this.cuotas.get(i).getMonto();
            }
        }
        return redondear(total);
    }

    public double calcularSaldoPendiente() {
        return redondear(this.precio_final - this.calcularMontoPagado());
    }

    // Muestra el cronograma de cuotas con el estado de cada una
    public void verCronograma() {
        System.out.println("   ---- CRONOGRAMA DE PAGOS de " + this.codigo + " ----");
        for (int i = 0; i < this.cuotas.size(); i++) {
            this.cuotas.get(i).verDatos();
        }
        System.out.println("   Pagado: S/ " + String.format("%.2f", this.calcularMontoPagado())
                + "   Saldo: S/ " + String.format("%.2f", this.calcularSaldoPendiente()));
    }

    // Lineas para cuotas.txt: cod_matricula;numero;monto;fec_vencimiento;estado;fec_pago
    public ArrayList<String> convertirCuotasALineas() {
        ArrayList<String> lineas = new ArrayList();
        for (int i = 0; i < this.cuotas.size(); i++) {
            Cuota c = this.cuotas.get(i);
            lineas.add(this.codigo + ";" + c.getNumero() + ";" + c.getMonto() + ";"
                    + c.getFec_vencimiento() + ";" + (c.isPagada() ? "PAGADA" : "PENDIENTE") + ";"
                    + (c.isPagada() ? c.getFec_pago() : "-"));
        }
        return lineas;
    }

    public ArrayList<Cuota> getCuotas() {
        return cuotas;
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

    @Override
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
    @Override
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

    public double getSubtotal() {
        return subtotal;
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
