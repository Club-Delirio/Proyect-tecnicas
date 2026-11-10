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
 * Ventana de ciclos y cursos.
 * RF-01 registrar un ciclo regular o un curso intensivo,
 * RF-02 impedir codigos repetidos,
 * RF-03 clasificar en BASICO, INTERMEDIO o PREMIUM,
 * RF-35 mostrar la oferta agrupada por categoria.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturo
 * @author Rojas Valdivia, Jhann Frank
 */
public class FrmCiclos extends FormularioBase {
    private JComboBox<String> cboTipo, cboModalidad, cboEspecifico, cboFiltro;
    private JTextField txtCodigo, txtNombre, txtInicio, txtFin, txtCosto, txtVacantes, txtEsp2, txtEsp3;
    private JLabel lblEsp1, lblEsp2, lblEsp3;
    private JButton btnRegistrar, btnLimpiar;
    private JTable tblOferta;

    private static final String[] TURNOS = {"MANANA", "TARDE", "NOCHE"};
    private static final String[] AREAS = {"CIENCIAS", "LETRAS", "INGENIERIA"};

    public FrmCiclos(VentanaPrincipal principal) {
        super("Ciclos y cursos", 1130, 560, principal);
        componentes();
        refrescar();
    }

    @Override
    protected void componentes() {
        titulo("Nuevo ciclo o curso", 20, 10, 300);
        etiqueta("Tipo", 20, 45, 130);
        cboTipo = combo(new String[]{"CICLO REGULAR", "CURSO INTENSIVO"}, 160, 45, 200);
        cboTipo.addActionListener(this);
        etiqueta("Código", 20, 80, 130);
        txtCodigo = caja(160, 80, 200);
        etiqueta("Nombre", 20, 115, 130);
        txtNombre = caja(160, 115, 200);
        etiqueta("Inicio (aaaa-mm-dd)", 20, 150, 140);
        txtInicio = caja(160, 150, 200);
        etiqueta("Fin (aaaa-mm-dd)", 20, 185, 140);
        txtFin = caja(160, 185, 200);
        etiqueta("Costo base S/", 20, 220, 130);
        txtCosto = caja(160, 220, 200);
        etiqueta("Modalidad", 20, 255, 130);
        cboModalidad = combo(new String[]{"PRESENCIAL", "VIRTUAL"}, 160, 255, 200);
        etiqueta("Vacantes", 20, 290, 130);
        txtVacantes = caja(160, 290, 200);

        lblEsp1 = etiqueta("Turno", 20, 325, 140);
        cboEspecifico = combo(TURNOS, 160, 325, 200);
        lblEsp2 = etiqueta("Meses de duración", 20, 360, 140);
        txtEsp2 = caja(160, 360, 200);
        lblEsp3 = etiqueta("Simulacros", 20, 395, 140);
        txtEsp3 = caja(160, 395, 200);

        btnRegistrar = boton("Registrar", 20, 440, 160);
        btnLimpiar = boton("Limpiar", 200, 440, 160);

        titulo("Oferta académica por categoría", 390, 10, 300);
        etiqueta("Ver", 860, 10, 40);
        cboFiltro = combo(new String[]{"TODAS", "BASICO", "INTERMEDIO", "PREMIUM"}, 900, 10, 200);
        cboFiltro.addActionListener(this);
        tblOferta = tabla(new String[]{"Categoría", "Código", "Tipo", "Nombre", "Modalidad",
            "Costo", "Matriculados", "Estado"}, 390, 45, 710, 425);
    }

    @Override
    public void refrescar() {
        DefaultTableModel modelo = vaciar(tblOferta);
        String[] categorias = {"BASICO", "INTERMEDIO", "PREMIUM"};
        String filtro = cboFiltro.getSelectedItem().toString();
        // RF-35: se recorre por categoria, asi la tabla queda agrupada
        for (int i = 0; i < categorias.length; i++) {
            if (!filtro.equals("TODAS") && !filtro.equals(categorias[i])) {
                continue;
            }
            for (Ciclo c : principal.getCiclos().getLista()) {
                if (c.calcularCategoria().equals(categorias[i])) {
                    modelo.addRow(new Object[]{categorias[i], c.getCodigo(), c.getTipo(), c.getNombre(),
                        c.getModalidad(), soles(c.getCosto_base()),
                        c.getMatriculados() + " / " + c.getVacantes(), c.getEstado()});
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnRegistrar)) {
            registrar();
        } else if (e.getSource().equals(btnLimpiar)) {
            limpiar();
        } else if (e.getSource().equals(cboTipo)) {
            cambiarTipo();
        } else if (e.getSource().equals(cboFiltro)) {
            refrescar();
        }
    }

    // Los tres ultimos campos cambian segun el tipo: es la parte propia de cada clase hija
    private void cambiarTipo() {
        cboEspecifico.removeAllItems();
        String[] opciones = cboTipo.getSelectedIndex() == 0 ? TURNOS : AREAS;
        for (int i = 0; i < opciones.length; i++) {
            cboEspecifico.addItem(opciones[i]);
        }
        if (cboTipo.getSelectedIndex() == 0) {
            lblEsp1.setText("Turno");
            lblEsp2.setText("Meses de duración");
            lblEsp3.setText("Simulacros");
        } else {
            lblEsp1.setText("Área");
            lblEsp2.setText("Semanas de duración");
            lblEsp3.setText("Horas diarias");
        }
    }

    private void registrar() {
        // Upcasting: la variable es de tipo Ciclo y el objeto es de una clase hija
        Ciclo c;
        if (cboTipo.getSelectedIndex() == 0) {
            c = new CicloRegular();
        } else {
            c = new CursoIntensivo();
        }
        String codigo = txtCodigo.getText().trim().toUpperCase();
        if (principal.getCiclos().buscarCiclo(codigo) != null) { // RF-02
            mostrarError("Error: el codigo " + codigo + " ya esta registrado");
            return;
        }
        iniciarValidacion();
        c.setCodigo(codigo);
        c.setNombre(txtNombre.getText());
        LocalDate inicio = leerFecha(txtInicio, "Inicio");
        if (inicio != null) {
            c.setFec_inicio(inicio);
        }
        LocalDate fin = leerFecha(txtFin, "Fin");
        if (fin != null) {
            c.setFec_fin(fin);
        }
        c.setCosto_base(leerDecimal(txtCosto, "Costo base"));
        c.setModalidad(cboModalidad.getSelectedItem().toString());
        c.setVacantes(leerEntero(txtVacantes, "Vacantes"));
        // Downcasting para llenar los datos propios del tipo
        if (c instanceof CicloRegular) {
            CicloRegular cr = (CicloRegular) c;
            cr.setTurno(cboEspecifico.getSelectedItem().toString());
            cr.setMeses_duracion(leerEntero(txtEsp2, "Meses de duracion"));
            cr.setNro_simulacros(leerEntero(txtEsp3, "Simulacros"));
        } else {
            CursoIntensivo ci = (CursoIntensivo) c;
            ci.setArea(cboEspecifico.getSelectedItem().toString());
            ci.setSemanas_duracion(leerEntero(txtEsp2, "Semanas de duracion"));
            ci.setHoras_diarias(leerEntero(txtEsp3, "Horas diarias"));
        }
        String errores = terminarValidacion();
        if (errores.length() > 0) {
            mostrarError(errores);
            return;
        }
        iniciarValidacion();
        boolean agregado = principal.getCiclos().agregarCiclo(c);
        errores = terminarValidacion();
        if (!agregado) {
            mostrarError(errores);
            return;
        }
        mostrarMensaje(c.getTipo() + " " + c.getCodigo() + " registrado.\nCategoría: "
                + c.calcularCategoria() + "   Descuento propio: " + c.calcularDescuento() + "%");
        limpiar();
        principal.refrescarTodo();
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtInicio.setText("");
        txtFin.setText("");
        txtCosto.setText("");
        txtVacantes.setText("");
        txtEsp2.setText("");
        txtEsp3.setText("");
        txtCodigo.requestFocus();
    }
}
