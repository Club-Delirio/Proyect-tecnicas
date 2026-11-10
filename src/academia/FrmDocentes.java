package academia;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana de docentes.
 * RF-09 registrar un docente con su especialidad, tarifa y bonificacion, y
 * acumular las horas que dicta y las matriculas que tutela.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturo
 * @author Rojas Valdivia, Jhann Frank
 */
public class FrmDocentes extends FormularioBase {
    private JComboBox<String> cboTipoDoc, cboEspecialidad;
    private JTextField txtDocumento, txtCodigo, txtNombres, txtPaterno, txtMaterno, txtTelefono,
            txtTarifa, txtBonificacion, txtHoras;
    private JButton btnRegistrar, btnLimpiar;
    private JTable tblDocentes;
    private JLabel lblPlanilla;

    public FrmDocentes(VentanaPrincipal principal) {
        super("Docentes", 1180, 540, principal);
        componentes();
        refrescar();
    }

    @Override
    protected void componentes() {
        titulo("Ficha del docente", 20, 10, 300);
        etiqueta("Tipo de documento", 20, 45, 140);
        cboTipoDoc = combo(new String[]{"DNI", "CE"}, 170, 45, 80);
        etiqueta("N° de documento", 20, 80, 140);
        txtDocumento = caja(170, 80, 200);
        etiqueta("Código (DOC-000)", 20, 115, 140);
        txtCodigo = caja(170, 115, 200);
        etiqueta("Nombres", 20, 150, 140);
        txtNombres = caja(170, 150, 200);
        etiqueta("Apellido paterno", 20, 185, 140);
        txtPaterno = caja(170, 185, 200);
        etiqueta("Apellido materno", 20, 220, 140);
        txtMaterno = caja(170, 220, 200);
        etiqueta("Teléfono", 20, 255, 140);
        txtTelefono = caja(170, 255, 200);
        etiqueta("Especialidad", 20, 290, 140);
        cboEspecialidad = combo(new String[]{"MATEMATICA", "FISICA", "QUIMICA", "BIOLOGIA",
            "COMUNICACION", "HISTORIA"}, 170, 290, 200);
        etiqueta("Tarifa por hora S/", 20, 325, 140);
        txtTarifa = caja(170, 325, 200);
        etiqueta("Bonificación %", 20, 360, 140);
        txtBonificacion = caja(170, 360, 200);
        etiqueta("Horas dictadas", 20, 395, 140);
        txtHoras = caja(170, 395, 200);
        btnRegistrar = boton("Registrar", 20, 440, 165);
        btnLimpiar = boton("Limpiar", 205, 440, 165);

        titulo("Plana docente", 400, 10, 300);
        tblDocentes = tabla(new String[]{"Código", "Docente", "Especialidad", "Tarifa", "Horas",
            "Tutorías", "Monto gestionado", "Pago total"}, 400, 45, 750, 390);
        lblPlanilla = titulo("", 400, 445, 750);
    }

    @Override
    public void refrescar() {
        DefaultTableModel modelo = vaciar(tblDocentes);
        for (Persona p : principal.getPersonas().getLista()) {
            if (p instanceof Docente) {
                Docente d = (Docente) p; // downcasting
                modelo.addRow(new Object[]{d.getCodigo(), d.getNombreEnNomina(), d.getEspecialidad(),
                    soles(d.getTarifa_hora()), d.getHoras_dictadas(), d.getCant_matriculas(),
                    soles(d.getMonto_gestionado()), soles(d.calcularPagoTotal())});
            }
        }
        lblPlanilla.setText("Planilla docente del periodo: "
                + soles(principal.getPersonas().calcularPlanillaDocente()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnRegistrar)) {
            registrar();
        } else if (e.getSource().equals(btnLimpiar)) {
            limpiar();
        }
    }

    private void registrar() {
        String documento = txtDocumento.getText().trim();
        Persona existente = principal.getPersonas().buscarPorDocumento(documento);
        if (existente != null) {
            mostrarError("Error: el documento " + documento + " ya está registrado ("
                    + existente.getNombreCompleto() + ")");
            return;
        }
        Docente d = new Docente();
        iniciarValidacion();
        d.setTipo_doc(cboTipoDoc.getSelectedItem().toString());
        d.setNro_doc(documento);
        d.setCodigo(txtCodigo.getText().trim().toUpperCase());
        d.setNombres(txtNombres.getText());
        d.setApe_paterno(txtPaterno.getText());
        d.setApe_materno(txtMaterno.getText());
        d.setTelefono(txtTelefono.getText().trim());
        d.setEspecialidad(cboEspecialidad.getSelectedItem().toString());
        d.setTarifa_hora(leerDecimal(txtTarifa, "Tarifa por hora"));
        d.setBonificacion(leerDecimal(txtBonificacion, "Bonificacion"));
        d.registrarHoras(leerEntero(txtHoras, "Horas dictadas"));
        String errores = terminarValidacion();
        if (errores.length() > 0) {
            mostrarError(errores);
            return;
        }
        iniciarValidacion();
        boolean agregado = principal.getPersonas().agregarPersona(d); // upcasting a Persona
        errores = terminarValidacion();
        if (!agregado) {
            mostrarError(errores);
            return;
        }
        mostrarMensaje("Docente " + d.getCodigo() + " registrado: " + d.getNombreCompleto()
                + "\nPago por horas: " + soles(d.calcularPagoHoras()));
        limpiar();
        principal.refrescarTodo();
    }

    private void limpiar() {
        txtDocumento.setText("");
        txtCodigo.setText("");
        txtNombres.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");
        txtTelefono.setText("");
        txtTarifa.setText("");
        txtBonificacion.setText("");
        txtHoras.setText("");
        txtDocumento.requestFocus();
    }
}
