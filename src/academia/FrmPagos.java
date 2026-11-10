package academia;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana de pagos y anulaciones.
 * RF-27 ver el cronograma de cuotas de una matricula y registrar el pago de cada una,
 * RF-28 anular una matricula y devolver la vacante al ciclo.
 *
 * Al anular, las cuotas desaparecen junto con la matricula porque son parte
 * de ella (composicion).
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class FrmPagos extends FormularioBase {
    private JComboBox<String> cboMatricula;
    private JButton btnVer, btnPagar, btnAnular;
    private JLabel lblResumen, lblSaldo;
    private JTable tblCuotas;

    public FrmPagos(VentanaPrincipal principal) {
        super("Pagos y anulaciones", 940, 520, principal);
        componentes();
        refrescar();
    }

    @Override
    protected void componentes() {
        titulo("Matrícula", 20, 10, 300);
        cboMatricula = combo(new String[]{}, 20, 45, 620);
        cboMatricula.addActionListener(this);
        btnVer = boton("Ver cronograma", 660, 43, 250);
        lblResumen = etiqueta("", 20, 80, 890);
        lblSaldo = titulo("", 20, 105, 890);
        titulo("Cuotas", 20, 140, 300);
        tblCuotas = tabla(new String[]{"Cuota", "Monto", "Vence", "Estado", "Fecha de pago"}, 20, 175, 890, 220);
        btnPagar = boton("Registrar pago de la cuota seleccionada", 20, 410, 330);
        btnAnular = boton("Anular matrícula", 660, 410, 250);
    }

    @Override
    public void refrescar() {
        String elegida = codigoDe(cboMatricula.getSelectedItem());
        cboMatricula.removeAllItems();
        for (Matricula m : principal.getMatriculas().getLista()) {
            cboMatricula.addItem(m.getCodigo() + " - " + m.getEstudiante().getNombreEnNomina() + " - "
                    + m.getCiclo().getNombre() + " - " + m.getForma_pago());
        }
        for (int i = 0; i < cboMatricula.getItemCount(); i++) {
            if (codigoDe(cboMatricula.getItemAt(i)).equals(elegida)) {
                cboMatricula.setSelectedIndex(i);
            }
        }
        mostrarCuotas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cboMatricula) || e.getSource().equals(btnVer)) {
            mostrarCuotas();
        } else if (e.getSource().equals(btnPagar)) {
            pagar();
        } else if (e.getSource().equals(btnAnular)) {
            anular();
        }
    }

    private Matricula elegida() {
        return principal.getMatriculas().buscarMatricula(codigoDe(cboMatricula.getSelectedItem()));
    }

    private void mostrarCuotas() {
        DefaultTableModel modelo = vaciar(tblCuotas);
        Matricula m = elegida();
        if (m == null) {
            lblResumen.setText("No hay matrículas registradas");
            lblSaldo.setText("");
            return;
        }
        for (Cuota c : m.getCuotas()) {
            modelo.addRow(new Object[]{c.getNumero(), soles(c.getMonto()), c.getFec_vencimiento(),
                c.getEstado(), c.isPagada() ? c.getFec_pago() : "—"});
        }
        lblResumen.setText(m.getEstudiante().getNombreCompleto() + "  |  " + m.getCiclo().getCodigo() + " "
                + m.getCiclo().getNombre() + "  |  Precio final " + soles(m.getPrecio_final())
                + "  |  Cuotas pagadas " + m.contarCuotasPagadas() + " de " + m.getCuotas().size());
        lblSaldo.setText("Pagado: " + soles(m.calcularMontoPagado()) + "      Saldo pendiente: "
                + soles(m.calcularSaldoPendiente()));
    }

    // RF-27: paga la cuota elegida en la tabla con la fecha de hoy
    private void pagar() {
        Matricula m = elegida();
        int fila = tblCuotas.getSelectedRow();
        if (m == null || fila < 0) {
            mostrarError("Seleccione una cuota en la tabla");
            return;
        }
        int numero = (Integer) tblCuotas.getValueAt(fila, 0);
        iniciarValidacion();
        boolean pagada = m.registrarPagoCuota(numero);
        String errores = terminarValidacion();
        if (!pagada) {
            mostrarError(errores);
            return;
        }
        principal.refrescarTodo();
        mostrarMensaje("Cuota " + numero + " de " + m.getCodigo() + " pagada.\nSaldo pendiente: "
                + soles(m.calcularSaldoPendiente()));
    }

    // RF-28: anula la matricula elegida; sus cuotas desaparecen con ella
    private void anular() {
        Matricula m = elegida();
        if (m == null) {
            mostrarError("No hay ninguna matrícula elegida");
            return;
        }
        if (!confirmar("¿Anular la matrícula " + m.getCodigo() + " de " + m.getEstudiante().getNombreCompleto()
                + "?\nLa vacante vuelve a " + m.getCiclo().getCodigo() + " y sus " + m.getCuotas().size()
                + " cuota(s) se eliminan.")) {
            return;
        }
        iniciarValidacion();
        boolean anulada = principal.getMatriculas().eliminarMatricula(m.getCodigo());
        String errores = terminarValidacion();
        if (!anulada) {
            mostrarError(errores);
            return;
        }
        principal.refrescarTodo();
        mostrarMensaje("Matrícula " + m.getCodigo() + " anulada.\n" + m.getCiclo().getCodigo() + " tiene ahora "
                + m.getCiclo().vacantesDisponibles() + " vacante(s) libre(s).");
    }
}
