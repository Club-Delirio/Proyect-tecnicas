package academia;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana de matricula y cotizacion.
 * RF-22 configurar la campana de descuento vigente,
 * RF-23 cotizar el precio con su desglose sin registrar,
 * RF-24 registrar la matricula al contado o en 2, 3 o 4 cuotas,
 * RF-25 impedir matricular sin vacantes o dos veces en el mismo ciclo,
 * RF-27 mostrar el cronograma de cuotas que crea la matricula.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturo
 * @author Rojas Valdivia, Jhann Frank
 */
public class FrmMatricula extends FormularioBase {
    private JTextField txtCampana, txtDescuento;
    private JComboBox<String> cboEstudiante, cboTutor, cboCiclo, cboPago, cboCuotas;
    private JButton btnCampana, btnCotizar, btnMatricular;
    private JLabel lblCampana, lblResultado;
    private JTextArea txaDesglose;
    private JTable tblCronograma;

    public FrmMatricula(VentanaPrincipal principal) {
        super("Matrícula y cotización", 1080, 600, principal);
        componentes();
        refrescar();
    }

    @Override
    protected void componentes() {
        titulo("Campaña de descuento vigente", 20, 10, 300);
        etiqueta("Nombre", 20, 45, 80);
        txtCampana = caja(100, 45, 160);
        etiqueta("% (0 a 25)", 270, 45, 80);
        txtDescuento = caja(345, 45, 60);
        btnCampana = boton("Aplicar", 415, 43, 100);
        lblCampana = etiqueta("", 530, 45, 520);

        titulo("Datos de la matrícula", 20, 90, 300);
        etiqueta("Estudiante", 20, 125, 100);
        cboEstudiante = combo(new String[]{}, 120, 125, 330);
        etiqueta("Tutor", 20, 160, 100);
        cboTutor = combo(new String[]{}, 120, 160, 330);
        etiqueta("Ciclo o curso", 20, 195, 100);
        cboCiclo = combo(new String[]{}, 120, 195, 330);
        etiqueta("Forma de pago", 20, 230, 100);
        cboPago = combo(new String[]{"CONTADO", "FRACCIONADO"}, 120, 230, 160);
        cboPago.addActionListener(this);
        etiqueta("Cuotas", 300, 230, 60);
        cboCuotas = combo(new String[]{"2", "3", "4"}, 360, 230, 90);
        cboCuotas.setEnabled(false);
        btnCotizar = boton("Cotizar (no registra)", 20, 275, 210);
        btnMatricular = boton("Matricular", 240, 275, 210);
        lblResultado = titulo("", 20, 320, 440);

        titulo("Desglose del precio", 480, 90, 300);
        txaDesglose = areaTexto(480, 125, 570, 200);
        titulo("Cronograma de pagos (cuotas que crea la matrícula)", 20, 350, 500);
        tblCronograma = tabla(new String[]{"Cuota", "Monto", "Vence", "Estado"}, 20, 385, 1030, 160);
    }

    @Override
    public void refrescar() {
        String estudiante = codigoDe(cboEstudiante.getSelectedItem());
        String tutor = codigoDe(cboTutor.getSelectedItem());
        String ciclo = codigoDe(cboCiclo.getSelectedItem());
        cboEstudiante.removeAllItems();
        cboTutor.removeAllItems();
        cboCiclo.removeAllItems();
        for (Persona p : principal.getPersonas().getLista()) {
            if (p instanceof Estudiante) {
                cboEstudiante.addItem(((Estudiante) p).getCodigo() + " - " + p.getNombreEnNomina());
            } else if (p instanceof Docente) {
                Docente d = (Docente) p;
                cboTutor.addItem(d.getCodigo() + " - " + d.getNombreEnNomina() + " (" + d.getEspecialidad() + ")");
            }
        }
        for (Ciclo c : principal.getCiclos().getLista()) {
            cboCiclo.addItem(c.getCodigo() + " - " + c.getNombre() + "  [" + c.getMatriculados() + "/"
                    + c.getVacantes() + " " + c.getEstado() + "]");
        }
        reseleccionar(cboEstudiante, estudiante);
        reseleccionar(cboTutor, tutor);
        reseleccionar(cboCiclo, ciclo);
        lblCampana.setText("Vigente: " + principal.getNombre_campana() + " (" + principal.getDesc_campana() + "%)");
    }

    // Despues de recargar una lista, vuelve a dejar elegido lo que estaba elegido
    private void reseleccionar(JComboBox<String> combo, String codigo) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (codigoDe(combo.getItemAt(i)).equals(codigo)) {
                combo.setSelectedIndex(i);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cboPago)) {
            cboCuotas.setEnabled(cboPago.getSelectedIndex() == 1);
        } else if (e.getSource().equals(btnCampana)) {
            aplicarCampana();
        } else if (e.getSource().equals(btnCotizar)) {
            Matricula m = armarMatricula();
            if (m != null) {
                mostrarDesglose(m);
                lblResultado.setText("COTIZACIÓN: no se registró ninguna matrícula");
            }
        } else if (e.getSource().equals(btnMatricular)) {
            matricular();
        }
    }

    private void aplicarCampana() {
        iniciarValidacion();
        boolean aplicada = principal.setCampana(txtCampana.getText(), leerDecimal(txtDescuento, "%"));
        String errores = terminarValidacion();
        if (!aplicada) {
            mostrarError(errores);
            return;
        }
        refrescar();
        mostrarMensaje("Campaña vigente: " + principal.getNombre_campana() + " (" + principal.getDesc_campana()
                + "%).\nSe aplica a las cotizaciones y matrículas desde ahora.");
    }

    // Crea la matricula con lo elegido y calcula su precio; todavia no la registra
    private Matricula armarMatricula() {
        Estudiante est = principal.getPersonas().buscarEstudiante(codigoDe(cboEstudiante.getSelectedItem()));
        Docente tutor = principal.getPersonas().buscarDocente(codigoDe(cboTutor.getSelectedItem()));
        Ciclo ciclo = principal.getCiclos().buscarCiclo(codigoDe(cboCiclo.getSelectedItem()));
        if (est == null || tutor == null || ciclo == null) {
            mostrarError("Error: elija un estudiante, un tutor y un ciclo");
            return null;
        }
        Matricula m = new Matricula(est, tutor, ciclo, principal.getNombre_campana(), principal.getDesc_campana());
        m.setForma_pago(cboPago.getSelectedItem().toString());
        if (m.getForma_pago().equals("FRACCIONADO")) {
            m.setNro_cuotas(Integer.parseInt(cboCuotas.getSelectedItem().toString()));
        }
        m.calcularPrecioFinal(); // aqui la matricula crea sus cuotas (composicion)
        return m;
    }

    private void matricular() {
        Matricula m = armarMatricula();
        if (m == null) {
            return;
        }
        Ciclo c = m.getCiclo();
        // RF-25: las mismas comprobaciones que hace la consola antes de registrar
        if (!c.hayVacantes()) {
            mostrarError("SIN VACANTES: el ciclo " + c.getCodigo() + " ya está " + c.getEstado()
                    + " (" + c.getMatriculados() + "/" + c.getVacantes() + ")");
            return;
        }
        if (principal.getMatriculas().existeMatricula(m.getEstudiante(), c)) {
            mostrarError("Error: " + m.getEstudiante().getNombreCompleto() + " ya está matriculado en " + c.getCodigo());
            return;
        }
        mostrarDesglose(m);
        if (!confirmar("¿Confirmar la matrícula por " + soles(m.getPrecio_final()) + "?")) {
            lblResultado.setText("Matrícula cancelada; la vacante sigue disponible");
            return;
        }
        iniciarValidacion();
        boolean registrada = principal.getMatriculas().agregarMatricula(m);
        String errores = terminarValidacion();
        if (!registrada) {
            mostrarError(errores);
            return;
        }
        lblResultado.setText("Matrícula " + m.getCodigo() + " registrada");
        principal.refrescarTodo();
        mostrarDesglose(m);
        mostrarMensaje("Matrícula " + m.getCodigo() + " registrada.\n" + c.getCodigo() + " tiene ahora "
                + c.vacantesDisponibles() + " vacante(s).\nEstudiante: " + m.getEstudiante().calcularTipoEstudiante());
    }

    // Muestra el desglose (RF-23) y el cronograma de cuotas (RF-27)
    private void mostrarDesglose(Matricula m) {
        Ciclo c = m.getCiclo();
        String texto = c.getTipo() + ": " + c.getNombre() + " (" + c.calcularCategoria() + ", " + c.getModalidad() + ")\n"
                + "Estudiante : " + m.getEstudiante().getNombreCompleto() + "\n"
                + "Tutor      : " + m.getTutor().getNombreCompleto() + "\n\n"
                + String.format("Costo base             : %10s%n", soles(c.getCosto_base()))
                + String.format("(-) Desc. campaña      : %10s  [%s %.1f%%]%n", soles(m.getMonto_campana()),
                        principal.getNombre_campana(), principal.getDesc_campana())
                + String.format("(-) Desc. del ciclo    : %10s  [%.1f%%]%n", soles(m.getMonto_descuento()), c.calcularDescuento())
                + String.format("Subtotal               : %10s%n", soles(m.getSubtotal()))
                + String.format("(+) Recargo fraccion.  : %10s  [%.1f%%]%n", soles(m.getRecargo()), m.calcularPorcentajeRecargo())
                + String.format("PRECIO FINAL           : %10s%n", soles(m.getPrecio_final()));
        if (m.getForma_pago().equals("FRACCIONADO")) {
            texto = texto + "En " + m.getNro_cuotas() + " cuotas de " + soles(m.getCuota_mensual()) + "\n";
        }
        txaDesglose.setText(texto);
        DefaultTableModel modelo = vaciar(tblCronograma);
        for (Cuota cuota : m.getCuotas()) {
            modelo.addRow(new Object[]{cuota.getNumero(), soles(cuota.getMonto()), cuota.getFec_vencimiento(),
                cuota.getEstado()});
        }
    }
}
