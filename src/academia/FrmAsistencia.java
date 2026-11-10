package academia;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana de control de asistencia.
 * RF-31 registrar la asistencia de un estudiante a una sesion de su ciclo,
 * RF-36 reporte de asistencia con alerta de riesgo de desercion (menos de 70%).
 *
 * Solo se ofrecen los estudiantes matriculados en el ciclo elegido.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturo
 * @author Rojas Valdivia, Jhann Frank
 */
public class FrmAsistencia extends FormularioBase {
    private JComboBox<String> cboCiclo, cboEstudiante, cboEstado;
    private JTextField txtFecha, txtObservacion;
    private JButton btnRegistrar;
    private JTable tblReporte;
    private JLabel lblTotales;

    public FrmAsistencia(VentanaPrincipal principal) {
        super("Control de asistencia", 1080, 560, principal);
        componentes();
        refrescar();
    }

    @Override
    protected void componentes() {
        titulo("Registrar asistencia", 20, 10, 300);
        etiqueta("Ciclo o curso", 20, 45, 110);
        cboCiclo = combo(new String[]{}, 130, 45, 270);
        cboCiclo.addActionListener(this);
        etiqueta("Estudiante", 20, 80, 110);
        cboEstudiante = combo(new String[]{}, 130, 80, 270);
        etiqueta("Fecha (aaaa-mm-dd)", 20, 115, 120);
        txtFecha = caja(130, 115, 270);
        etiqueta("Estado", 20, 150, 110);
        cboEstado = combo(new String[]{"PRESENTE", "TARDANZA", "AUSENTE", "JUSTIFICADO"}, 130, 150, 270);
        etiqueta("Observación", 20, 185, 110);
        txtObservacion = caja(130, 185, 270);
        btnRegistrar = boton("Registrar asistencia", 130, 230, 270);

        titulo("Reporte de asistencia por matrícula", 430, 10, 400);
        tblReporte = tabla(new String[]{"Código", "Estudiante", "Ciclo", "Asistió", "Sesiones", "%", "Alerta"},
                430, 45, 620, 410);
        lblTotales = etiqueta("", 430, 465, 620);
        txtFecha.setText(LocalDate.now().toString());
    }

    @Override
    public void refrescar() {
        String ciclo = codigoDe(cboCiclo.getSelectedItem());
        cboCiclo.removeAllItems();
        for (Ciclo c : principal.getCiclos().getLista()) {
            cboCiclo.addItem(c.getCodigo() + " - " + c.getNombre());
        }
        for (int i = 0; i < cboCiclo.getItemCount(); i++) {
            if (codigoDe(cboCiclo.getItemAt(i)).equals(ciclo)) {
                cboCiclo.setSelectedIndex(i);
            }
        }
        cargarEstudiantesDelCiclo();

        // Reporte: una fila por matricula; usa calcularPorcentaje(Estudiante, Ciclo)
        DefaultTableModel modelo = vaciar(tblReporte);
        AsistenciaController asistencias = principal.getAsistencias();
        for (Matricula m : principal.getMatriculas().getLista()) {
            int sesiones = asistencias.contarSesiones(m.getEstudiante(), m.getCiclo());
            if (sesiones > 0) {
                int porcentaje = asistencias.calcularPorcentaje(m.getEstudiante(), m.getCiclo());
                modelo.addRow(new Object[]{m.getEstudiante().getCodigo(), m.getEstudiante().getNombreEnNomina(),
                    m.getCiclo().getCodigo(), asistencias.contarAsistio(m.getEstudiante(), m.getCiclo()),
                    sesiones, porcentaje + "%", porcentaje < 70 ? "RIESGO DE DESERCIÓN" : ""});
            }
        }
        lblTotales.setText("Presentes: " + asistencias.contarPorEstado("PRESENTE")
                + "   Tardanzas: " + asistencias.contarPorEstado("TARDANZA")
                + "   Ausentes: " + asistencias.contarPorEstado("AUSENTE")
                + "   Justificados: " + asistencias.contarPorEstado("JUSTIFICADO"));
    }

    // Solo los estudiantes matriculados en el ciclo elegido
    private void cargarEstudiantesDelCiclo() {
        cboEstudiante.removeAllItems();
        Ciclo c = principal.getCiclos().buscarCiclo(codigoDe(cboCiclo.getSelectedItem()));
        if (c == null) {
            return;
        }
        for (Matricula m : principal.getMatriculas().getLista()) {
            if (m.getCiclo() == c) {
                cboEstudiante.addItem(m.getEstudiante().getCodigo() + " - " + m.getEstudiante().getNombreEnNomina());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cboCiclo)) {
            cargarEstudiantesDelCiclo();
        } else if (e.getSource().equals(btnRegistrar)) {
            registrar();
        }
    }

    private void registrar() {
        Ciclo c = principal.getCiclos().buscarCiclo(codigoDe(cboCiclo.getSelectedItem()));
        Estudiante est = principal.getPersonas().buscarEstudiante(codigoDe(cboEstudiante.getSelectedItem()));
        if (c == null || est == null) {
            mostrarError("Error: elija un ciclo y un estudiante matriculado en él");
            return;
        }
        iniciarValidacion();
        LocalDate fecha = leerFecha(txtFecha, "Fecha");
        Asistencia a = null;
        if (fecha != null) {
            a = new Asistencia(est, c, fecha);
            a.setEstado(cboEstado.getSelectedItem().toString());
            a.setObservacion(txtObservacion.getText());
        }
        String errores = terminarValidacion();
        if (errores.length() > 0) {
            mostrarError(errores);
            return;
        }
        iniciarValidacion();
        boolean registrada = principal.getAsistencias().agregarAsistencia(a);
        errores = terminarValidacion();
        if (!registrada) {
            mostrarError(errores);
            return;
        }
        txtObservacion.setText("");
        principal.refrescarTodo();
        int porcentaje = principal.getAsistencias().calcularPorcentaje(est, c);
        mostrarMensaje("Asistencia " + a.getCodigo() + " registrada: " + est.getNombreEnNomina() + " — "
                + a.getEstado() + "\nAsistencia en " + c.getCodigo() + ": " + porcentaje + "%"
                + (porcentaje < 70 ? "  (RIESGO DE DESERCIÓN)" : ""));
    }
}
