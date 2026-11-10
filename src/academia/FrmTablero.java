package academia;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 * Tablero del periodo.
 * RF-37 indicadores del periodo: matriculados, ingreso y ocupacion,
 * RF-21 cuantos estudiantes son nuevos y cuantos recurrentes,
 * RF-36 historial de matriculas con lo pagado y el saldo.
 *
 * Todos los numeros salen de los metodos de los controladores; la ventana
 * no calcula nada por su cuenta salvo la ocupacion, que suma los ciclos.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturo
 * @author Rojas Valdivia, Jhann Frank
 */
public class FrmTablero extends FormularioBase {
    private JLabel lblMatriculas, lblIngreso, lblRecaudado, lblPorCobrar, lblOcupacion, lblPlanilla,
            lblNeto, lblRiesgo, lblTipos, lblFinanzas;
    private JButton btnActualizar;
    private JTable tblHistorial;

    public FrmTablero(VentanaPrincipal principal) {
        super("Tablero del periodo", 1120, 600, principal);
        componentes();
        refrescar();
    }

    @Override
    protected void componentes() {
        titulo("Indicadores del periodo", 20, 10, 300);
        btnActualizar = boton("Actualizar", 960, 8, 130);
        lblMatriculas = tarjeta("Matrículas", 20, 50);
        lblIngreso = tarjeta("Ingreso total", 290, 50);
        lblRecaudado = tarjeta("Recaudado", 560, 50);
        lblPorCobrar = tarjeta("Por cobrar", 830, 50);
        lblOcupacion = tarjeta("Ocupación de vacantes", 20, 140);
        lblPlanilla = tarjeta("Planilla docente", 290, 140);
        lblNeto = tarjeta("Resultado neto", 560, 140);
        lblRiesgo = tarjeta("En riesgo de deserción", 830, 140);
        lblTipos = etiqueta("", 20, 222, 1070);
        lblFinanzas = etiqueta("", 20, 245, 1070);
        titulo("Historial de matrículas", 20, 275, 300);
        tblHistorial = tabla(new String[]{"Código", "Fecha", "Estudiante", "Ciclo", "Pago", "Precio final",
            "Pagado", "Saldo"}, 20, 305, 1070, 245);
    }

    // Recuadro con un titulo pequeno y un numero grande
    private JLabel tarjeta(String titulo, int x, int y) {
        JLabel tarjeta = new JLabel("", SwingConstants.CENTER);
        tarjeta.setBounds(x, y, 260, 75);
        tarjeta.setOpaque(true);
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setFont(tarjeta.getFont().deriveFont(Font.BOLD, 22f));
        tarjeta.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(31, 56, 100)), titulo));
        add(tarjeta);
        return tarjeta;
    }

    @Override
    public void refrescar() {
        MatriculaController matriculas = principal.getMatriculas();
        PersonaController personas = principal.getPersonas();
        AsistenciaController asistencias = principal.getAsistencias();

        int vacantes = 0, ocupadas = 0;
        for (Ciclo c : principal.getCiclos().getLista()) {
            vacantes = vacantes + c.getVacantes();
            ocupadas = ocupadas + c.getMatriculados();
        }
        int nuevos = 0, recurrentes = 0, riesgo = 0;
        for (Persona p : personas.getLista()) {
            if (p instanceof Estudiante) {
                Estudiante e = (Estudiante) p;
                if (e.calcularTipoEstudiante().equals("RECURRENTE")) {
                    recurrentes++;
                } else {
                    nuevos++;
                }
                int porcentaje = asistencias.calcularPorcentaje(e);
                if (porcentaje >= 0 && porcentaje < 70) {
                    riesgo++;
                }
            }
        }
        double ingreso = matriculas.calcularIngresoTotal();
        double planilla = personas.calcularPlanillaDocente();

        lblMatriculas.setText(String.valueOf(matriculas.getCantidad()));
        lblIngreso.setText(soles(ingreso));
        lblRecaudado.setText(soles(matriculas.calcularMontoRecaudado()));
        lblPorCobrar.setText(soles(matriculas.calcularSaldoPorCobrar()));
        lblOcupacion.setText(vacantes == 0 ? "0%" : String.format("%.1f%%", ocupadas * 100.0 / vacantes)
                + "  (" + ocupadas + "/" + vacantes + ")");
        lblPlanilla.setText(soles(planilla));
        lblNeto.setText(soles(ingreso - planilla));
        lblRiesgo.setText(riesgo + " estudiante(s)");
        lblTipos.setText("Estudiantes nuevos: " + nuevos + "     Recurrentes: " + recurrentes
                + "     |     Al contado: " + matriculas.contarPorFormaPago("CONTADO")
                + "     Fraccionadas: " + matriculas.contarPorFormaPago("FRACCIONADO")
                + "     |     Ciclos regulares: " + matriculas.contarPorTipo("CICLO REGULAR")
                + "     Cursos intensivos: " + matriculas.contarPorTipo("CURSO INTENSIVO"));
        lblFinanzas.setText("Descuentos otorgados: " + soles(matriculas.calcularTotalDescuentos())
                + "     Recargos por fraccionamiento: " + soles(matriculas.calcularTotalRecargos())
                + "     Campaña vigente: " + principal.getNombre_campana() + " (" + principal.getDesc_campana() + "%)");

        DefaultTableModel modelo = vaciar(tblHistorial);
        for (Matricula m : matriculas.getLista()) {
            modelo.addRow(new Object[]{m.getCodigo(), m.getFecha(), m.getEstudiante().getNombreEnNomina(),
                m.getCiclo().getNombre(), m.getForma_pago(), soles(m.getPrecio_final()),
                soles(m.calcularMontoPagado()), soles(m.calcularSaldoPendiente())});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnActualizar)) {
            refrescar();
        }
    }
}
