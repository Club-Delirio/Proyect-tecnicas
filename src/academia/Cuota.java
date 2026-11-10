package academia;

import java.time.LocalDate;

/**
 * Cuota del cronograma de pago de una matricula (RF-27).
 *
 * Es una PARTE de la matricula en una relacion de COMPOSICION: la cuota no se
 * crea fuera, la crea la propia Matricula dentro de su metodo
 * generarCronograma(), y si la matricula se anula sus cuotas desaparecen con
 * ella. Por eso el constructor no es publico: solo las clases del paquete
 * academia pueden usarlo, y la unica que lo hace es Matricula.
 *
 * Una matricula al CONTADO tiene una sola cuota; una FRACCIONADA tiene 2, 3 o 4.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class Cuota {
    private int numero;
    private double monto;
    private LocalDate fec_vencimiento;
    private boolean pagada;
    private LocalDate fec_pago;

    // Solo la Matricula crea sus cuotas: composicion
    Cuota(int numero, double monto, LocalDate fec_vencimiento) {
        this.numero = numero;
        this.monto = monto;
        this.fec_vencimiento = fec_vencimiento;
        this.pagada = false;
        this.fec_pago = null;
    }

    // Registra el pago de la cuota en la fecha indicada
    public boolean registrarPago(LocalDate fecha_pago) {
        if (this.pagada) {
            System.out.println("Error: la cuota " + this.numero + " ya fue pagada el " + this.fec_pago);
            return false;
        }
        if (fecha_pago == null || fecha_pago.isAfter(LocalDate.now())) {
            System.out.println("Error: la fecha de pago no puede ser futura");
            return false;
        }
        this.pagada = true;
        this.fec_pago = fecha_pago;
        return true;
    }

    // PAGADA, VENCIDA si ya paso su fecha y no se pago, o PENDIENTE
    public String getEstado() {
        if (this.pagada) {
            return "PAGADA";
        } else if (this.fec_vencimiento.isBefore(LocalDate.now())) {
            return "VENCIDA";
        }
        return "PENDIENTE";
    }

    public void verDatos() {
        System.out.println("   Cuota " + this.numero + "  S/ " + String.format("%.2f", this.monto)
                + "  vence " + this.fec_vencimiento + "  " + this.getEstado()
                + (this.pagada ? "  (pagada el " + this.fec_pago + ")" : ""));
    }

    public int getNumero() {
        return numero;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFec_vencimiento() {
        return fec_vencimiento;
    }

    public boolean isPagada() {
        return pagada;
    }

    public LocalDate getFec_pago() {
        return fec_pago;
    }
}
