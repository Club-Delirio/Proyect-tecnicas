package academia;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana de estudiantes.
 * RF-15 registrar un estudiante,
 * RF-16 reconocer al que ya esta registrado (por su documento) en lugar de duplicarlo,
 * RF-17 ver el historial de ciclos del estudiante,
 * RF-21 clasificarlo como NUEVO o RECURRENTE.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturo
 * @author Rojas Valdivia, Jhann Frank
 */
public class FrmEstudiantes extends FormularioBase {
    private JComboBox<String> cboTipoDoc;
    private JTextField txtDocumento, txtCodigo, txtNombres, txtPaterno, txtMaterno, txtTelefono,
            txtNacimiento, txtColegio, txtCarrera, txtApoderado;
    private JButton btnBuscar, btnRegistrar, btnLimpiar, btnHistorial;
    private JTable tblEstudiantes;
    private JTextArea txaHistorial;

    public FrmEstudiantes(VentanaPrincipal principal) {
        super("Estudiantes", 1180, 600, principal);
        componentes();
        refrescar();
    }

    @Override
    protected void componentes() {
        titulo("Ficha del estudiante", 20, 10, 300);
        etiqueta("Tipo de documento", 20, 45, 140);
        cboTipoDoc = combo(new String[]{"DNI", "CE"}, 170, 45, 80);
        etiqueta("N° de documento", 20, 80, 140);
        txtDocumento = caja(170, 80, 120);
        btnBuscar = boton("Buscar", 300, 78, 80);
        etiqueta("Código (EST-000)", 20, 115, 140);
        txtCodigo = caja(170, 115, 210);
        etiqueta("Nombres", 20, 150, 140);
        txtNombres = caja(170, 150, 210);
        etiqueta("Apellido paterno", 20, 185, 140);
        txtPaterno = caja(170, 185, 210);
        etiqueta("Apellido materno", 20, 220, 140);
        txtMaterno = caja(170, 220, 210);
        etiqueta("Teléfono", 20, 255, 140);
        txtTelefono = caja(170, 255, 210);
        etiqueta("Nacimiento (aaaa-mm-dd)", 20, 290, 150);
        txtNacimiento = caja(170, 290, 210);
        etiqueta("Colegio", 20, 325, 140);
        txtColegio = caja(170, 325, 210);
        etiqueta("Carrera a la que postula", 20, 360, 150);
        txtCarrera = caja(170, 360, 210);
        etiqueta("Apoderado", 20, 395, 140);
        txtApoderado = caja(170, 395, 210);
        btnRegistrar = boton("Registrar", 20, 440, 170);
        btnLimpiar = boton("Limpiar", 210, 440, 170);

        titulo("Estudiantes registrados", 410, 10, 300);
        tblEstudiantes = tabla(new String[]{"Código", "Documento", "Estudiante", "Edad", "Carrera",
            "Ciclos", "Tipo", "Última visita"}, 410, 45, 740, 330);
        btnHistorial = boton("Ver historial del seleccionado", 410, 385, 260);
        txaHistorial = areaTexto(410, 420, 740, 110);
    }

    @Override
    public void refrescar() {
        DefaultTableModel modelo = vaciar(tblEstudiantes);
        for (Persona p : principal.getPersonas().getLista()) {
            if (p instanceof Estudiante) {
                Estudiante e = (Estudiante) p; // downcasting
                modelo.addRow(new Object[]{e.getCodigo(), e.getTipo_doc() + " " + e.getNro_doc(),
                    e.getNombreEnNomina(), e.calcularEdad(), e.getCarrera_objetivo(),
                    e.getMatriculas().size(), e.calcularTipoEstudiante(), e.getFec_ultima_visita()});
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnBuscar)) {
            buscar();
        } else if (e.getSource().equals(btnRegistrar)) {
            registrar();
        } else if (e.getSource().equals(btnLimpiar)) {
            limpiar();
        } else if (e.getSource().equals(btnHistorial)) {
            verHistorial();
        }
    }

    /**
     * RF-16: si el documento ya existe no se vuelve a registrar; solo se
     * actualiza la ultima visita. Devuelve true si lo encontro.
     */
    private boolean buscar() {
        String documento = txtDocumento.getText().trim();
        Persona encontrada = principal.getPersonas().buscarPorDocumento(documento);
        if (encontrada == null) {
            mostrarMensaje("El documento " + documento + " no está registrado.\nComplete la ficha para registrarlo.");
            return false;
        }
        if (encontrada instanceof Estudiante) {
            Estudiante existente = (Estudiante) encontrada; // downcasting
            existente.registrarVisita();
            principal.refrescarTodo();
            seleccionarFila(existente.getCodigo());
            mostrarMensaje("El estudiante ya está registrado: " + existente.getNombreCompleto()
                    + " (" + existente.getCodigo() + ").\nNo se vuelve a registrar; se actualizó su última visita.");
        } else {
            mostrarError("Error: el documento " + documento + " pertenece a un docente");
        }
        return true;
    }

    private void registrar() {
        if (principal.getPersonas().buscarPorDocumento(txtDocumento.getText().trim()) != null) {
            buscar(); // RF-16: no se duplica
            return;
        }
        Estudiante e = new Estudiante();
        iniciarValidacion();
        e.setTipo_doc(cboTipoDoc.getSelectedItem().toString());
        e.setNro_doc(txtDocumento.getText().trim());
        String codigo = txtCodigo.getText().trim().toUpperCase();
        if (principal.getPersonas().buscarEstudiante(codigo) != null) {
            System.out.println("Error: el codigo " + codigo + " ya pertenece a otro estudiante");
        } else {
            e.setCodigo(codigo);
        }
        e.setNombres(txtNombres.getText());
        e.setApe_paterno(txtPaterno.getText());
        e.setApe_materno(txtMaterno.getText());
        e.setTelefono(txtTelefono.getText().trim());
        LocalDate nacimiento = leerFecha(txtNacimiento, "Nacimiento");
        if (nacimiento != null) {
            e.setFec_nacimiento(nacimiento);
        }
        e.setColegio(txtColegio.getText());
        e.setCarrera_objetivo(txtCarrera.getText());
        if (e.getFec_nacimiento() != null) { // la regla del apoderado depende de la edad
            String apoderado = txtApoderado.getText().trim();
            e.setApoderado(apoderado.length() == 0 ? "-" : apoderado);
        }
        String errores = terminarValidacion();
        if (errores.length() > 0) {
            mostrarError(errores);
            return;
        }
        iniciarValidacion();
        boolean agregado = principal.getPersonas().agregarPersona(e); // upcasting a Persona
        errores = terminarValidacion();
        if (!agregado) {
            mostrarError(errores);
            return;
        }
        mostrarMensaje("Estudiante " + e.getCodigo() + " registrado: " + e.getNombreCompleto()
                + "\nEdad: " + e.calcularEdad() + " años   Tipo: " + e.calcularTipoEstudiante());
        limpiar();
        principal.refrescarTodo();
    }

    // RF-17: historial de ciclos del estudiante seleccionado en la tabla
    private void verHistorial() {
        int fila = tblEstudiantes.getSelectedRow();
        if (fila < 0) {
            mostrarError("Seleccione un estudiante en la tabla");
            return;
        }
        Estudiante e = principal.getPersonas().buscarEstudiante(tblEstudiantes.getValueAt(fila, 0).toString());
        String texto = e.getNombreCompleto() + " (" + e.getCodigo() + ") — " + e.calcularTipoEstudiante()
                + " — " + e.getMatriculas().size() + " ciclo(s)\n";
        for (int i = 0; i < e.getMatriculas().size(); i++) {
            Ciclo c = e.getMatriculas().get(i);
            texto = texto + "  " + (i + 1) + ". " + c.getCodigo() + "  " + c.getNombre() + "  ("
                    + c.getTipo() + ", " + c.getModalidad() + ")\n";
        }
        txaHistorial.setText(texto);
    }

    private void seleccionarFila(String codigo) {
        for (int i = 0; i < tblEstudiantes.getRowCount(); i++) {
            if (tblEstudiantes.getValueAt(i, 0).equals(codigo)) {
                tblEstudiantes.setRowSelectionInterval(i, i);
            }
        }
    }

    private void limpiar() {
        txtDocumento.setText("");
        txtCodigo.setText("");
        txtNombres.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");
        txtTelefono.setText("");
        txtNacimiento.setText("");
        txtColegio.setText("");
        txtCarrera.setText("");
        txtApoderado.setText("");
        txtDocumento.requestFocus();
    }
}
