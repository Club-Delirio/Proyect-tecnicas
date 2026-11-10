package academia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Ventana principal de la interfaz grafica (RF-43).
 *
 * Sigue el esquema de la clase 12: un JFrame con una barra de menu
 * (JMenuBar, JMenu, JMenuItem) y un JDesktopPane llamado escritorio donde se
 * abren las ventanas internas. Cada opcion del menu crea el formulario, lo
 * agrega al escritorio y lo muestra con show().
 *
 * Esta ventana crea los cuatro controladores y los comparte con todos los
 * formularios: cada formulario recibe la ventana principal ya creada.
 *
 * Al abrirse recupera los archivos sin que el usuario lo pida (RF-40).
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturo
 * @author Rojas Valdivia, Jhann Frank
 */
public class VentanaPrincipal extends JFrame implements ActionListener {
    private PersonaController personas = new PersonaController();
    private CicloController ciclos = new CicloController();
    private MatriculaController matriculas = new MatriculaController();
    private AsistenciaController asistencias = new AsistenciaController();
    private ArchivoUtil archivo = new ArchivoUtil("datos");
    private String nombre_campana = "SIN CAMPANA";
    private double desc_campana = 0;

    private JDesktopPane escritorio;
    private JLabel estado;
    private JMenuItem itmGuardar, itmRespaldo, itmExportar, itmSalir;
    private JMenuItem itmCiclos, itmEstudiantes, itmDocentes;
    private JMenuItem itmMatricula, itmPagos, itmAsistencia;
    private JMenuItem itmTablero;

    public VentanaPrincipal() {
        componentes();
        setTitle("Academia Preuniversitaria San Fernando — Matrícula y control de asistencia");
        setSize(1240, 780);
        setLocationRelativeTo(null); // centrar
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Adaptador: solo se programa el evento que interesa, cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir();
            }
        });
        setVisible(true);
        cargarAlIniciar();
    }

    private void componentes() {
        JMenuBar barra = new JMenuBar();

        JMenu mnuArchivo = new JMenu("Archivo");
        itmGuardar = opcion(mnuArchivo, "Guardar datos");
        itmRespaldo = opcion(mnuArchivo, "Crear copia de respaldo");
        itmExportar = opcion(mnuArchivo, "Exportar matrículas a CSV");
        mnuArchivo.addSeparator();
        itmSalir = opcion(mnuArchivo, "Salir");

        JMenu mnuDatos = new JMenu("Datos básicos");
        itmCiclos = opcion(mnuDatos, "Ciclos y cursos");
        itmEstudiantes = opcion(mnuDatos, "Estudiantes");
        itmDocentes = opcion(mnuDatos, "Docentes");

        JMenu mnuProcesos = new JMenu("Procesos");
        itmMatricula = opcion(mnuProcesos, "Matrícula y cotización");
        itmPagos = opcion(mnuProcesos, "Pagos y anulaciones");
        itmAsistencia = opcion(mnuProcesos, "Control de asistencia");

        JMenu mnuReportes = new JMenu("Reportes");
        itmTablero = opcion(mnuReportes, "Tablero del periodo");

        barra.add(mnuArchivo);
        barra.add(mnuDatos);
        barra.add(mnuProcesos);
        barra.add(mnuReportes);
        setJMenuBar(barra);

        escritorio = new JDesktopPane();
        escritorio.setBackground(new Color(44, 62, 80));
        estado = new JLabel(" ");
        estado.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        setLayout(new BorderLayout()); // escritorio al centro y barra de estado abajo
        add(escritorio, BorderLayout.CENTER);
        add(estado, BorderLayout.SOUTH);
    }

    // Crea una opcion de menu y le asigna esta ventana como oyente
    private JMenuItem opcion(JMenu menu, String texto) {
        JMenuItem item = new JMenuItem(texto);
        item.addActionListener(this);
        menu.add(item);
        return item;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(itmCiclos)) {
            abrir(new FrmCiclos(this));
        } else if (e.getSource().equals(itmEstudiantes)) {
            abrir(new FrmEstudiantes(this));
        } else if (e.getSource().equals(itmDocentes)) {
            abrir(new FrmDocentes(this));
        } else if (e.getSource().equals(itmMatricula)) {
            abrir(new FrmMatricula(this));
        } else if (e.getSource().equals(itmPagos)) {
            abrir(new FrmPagos(this));
        } else if (e.getSource().equals(itmAsistencia)) {
            abrir(new FrmAsistencia(this));
        } else if (e.getSource().equals(itmTablero)) {
            abrir(new FrmTablero(this));
        } else if (e.getSource().equals(itmGuardar)) {
            guardar();
            JOptionPane.showMessageDialog(this, "Datos guardados en la carpeta " + archivo.getCarpeta());
        } else if (e.getSource().equals(itmRespaldo)) {
            crearRespaldo();
        } else if (e.getSource().equals(itmExportar)) {
            AcademiaSanFernando.exportarReporteMatriculas(archivo, matriculas);
            JOptionPane.showMessageDialog(this, "Historial exportado a " + archivo.rutaDe("reporte_matriculas.csv"));
        } else if (e.getSource().equals(itmSalir)) {
            salir();
        }
    }

    /**
     * Abre un formulario en el escritorio, como en la clase 12:
     * se crea la instancia, se agrega al escritorio y se muestra.
     * Si ya habia uno abierto del mismo tipo, solo se trae al frente.
     */
    public void abrir(FormularioBase formulario) {
        JInternalFrame[] abiertas = escritorio.getAllFrames();
        for (int i = 0; i < abiertas.length; i++) {
            if (abiertas[i].getClass().equals(formulario.getClass())) {
                ((FormularioBase) abiertas[i]).refrescar();
                abiertas[i].toFront();
                seleccionar(abiertas[i]);
                return;
            }
        }
        escritorio.add(formulario);
        formulario.setLocation(20 + 30 * abiertas.length, 20 + 30 * abiertas.length);
        formulario.show();
        seleccionar(formulario);
    }

    private void seleccionar(JInternalFrame ventana) {
        try {
            ventana.setSelected(true);
        } catch (PropertyVetoException e) {
            System.out.println("Aviso: no se pudo seleccionar la ventana " + ventana.getTitle());
        }
    }

    // Despues de un cambio, todas las ventanas abiertas vuelven a leer los datos
    public void refrescarTodo() {
        JInternalFrame[] abiertas = escritorio.getAllFrames();
        for (int i = 0; i < abiertas.length; i++) {
            ((FormularioBase) abiertas[i]).refrescar();
        }
        actualizarEstado();
    }

    public void actualizarEstado() {
        estado.setText("Campaña: " + nombre_campana + " (" + desc_campana + "%)    |    Estudiantes: "
                + personas.contarEstudiantes() + "    Docentes: " + personas.contarDocentes()
                + "    Ciclos: " + ciclos.getCantidad() + "    Matrículas: " + matriculas.getCantidad()
                + "    Asistencias: " + asistencias.getCantidad() + "    |    Datos en: " + archivo.getCarpeta());
    }

    // ================== ARCHIVOS ==================

    // RF-40: al abrir el programa se recuperan los archivos sin que el usuario lo pida
    private void cargarAlIniciar() {
        if (archivo.existeArchivo("ciclos.txt") || archivo.existeArchivo("personas.txt")) {
            AcademiaSanFernando.cargarCiclos(archivo, ciclos);
            AcademiaSanFernando.cargarPersonas(archivo, personas);
            AcademiaSanFernando.cargarMatriculas(archivo, personas, ciclos, matriculas);
            AcademiaSanFernando.cargarCuotas(archivo, matriculas);
            AcademiaSanFernando.cargarAsistencias(archivo, personas, ciclos, asistencias);
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "Todavía no hay datos guardados.\n¿Desea cargar los datos de prueba?",
                    "Primer inicio", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                AcademiaSanFernando.cargarDatosDePrueba(personas, ciclos, matriculas, asistencias);
            }
        }
        actualizarEstado();
    }

    public void guardar() {
        AcademiaSanFernando.guardarTodo(archivo, personas, ciclos, matriculas, asistencias);
    }

    // RF-41: guarda primero y luego copia los archivos a una carpeta con fecha y hora
    private void crearRespaldo() {
        guardar();
        String carpeta = archivo.crearRespaldo();
        if (carpeta == null) {
            JOptionPane.showMessageDialog(this, "No había archivos que respaldar", "Respaldo",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Copia de respaldo creada en:\n" + carpeta);
        }
    }

    private void salir() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea guardar los cambios antes de salir?",
                "Salir", JOptionPane.YES_NO_CANCEL_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            guardar();
            System.exit(0);
        } else if (respuesta == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    // ================== CAMPANA DE DESCUENTO (RF-22) ==================

    // Misma regla que la consola: el descuento de campana va de 0 a 25%
    public boolean setCampana(String nombre, double descuento) {
        if (nombre == null || nombre.trim().length() < 3) {
            System.out.println("Error: el nombre de la campana debe tener al menos 3 caracteres");
            return false;
        }
        if (descuento < 0 || descuento > 25) {
            System.out.println("Error: el descuento debe estar entre 0 y 25%");
            return false;
        }
        this.nombre_campana = nombre.trim().toUpperCase();
        this.desc_campana = descuento;
        actualizarEstado();
        return true;
    }

    public String getNombre_campana() {
        return nombre_campana;
    }

    public double getDesc_campana() {
        return desc_campana;
    }

    public PersonaController getPersonas() {
        return personas;
    }

    public CicloController getCiclos() {
        return ciclos;
    }

    public MatriculaController getMatriculas() {
        return matriculas;
    }

    public AsistenciaController getAsistencias() {
        return asistencias;
    }

    public ArchivoUtil getArchivo() {
        return archivo;
    }

    public static void main(String[] args) {
        // Apariencia Nimbus, la misma que usa NetBeans en los JFrame Form
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Aviso: se usa la apariencia por defecto");
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal();
            }
        });
    }
}
