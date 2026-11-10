package academia;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Clase abstracta base de todas las ventanas internas del sistema.
 *
 * Cada ventana es un JInternalFrame que se abre dentro del escritorio
 * (JDesktopPane) de la VentanaPrincipal, como en la clase 12. Esta clase:
 * - HEREDA de JInternalFrame e IMPLEMENTA la interfaz ActionListener, asi
 *   cada ventana hija programa sus botones en actionPerformed();
 * - obliga a las hijas a implementar componentes() y refrescar();
 * - reune los metodos que todas repiten (crear etiquetas, cajas, botones y
 *   tablas con setBounds, leer numeros y fechas, mostrar mensajes).
 *
 * Las reglas de validacion NO se repiten en las ventanas: viven en los
 * metodos set de cada clase. La ventana llama a los set y recoge los
 * mensajes "Error: ..." que esos set imprimen, para mostrarlos al usuario.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturo
 * @author Rojas Valdivia, Jhann Frank
 */
public abstract class FormularioBase extends JInternalFrame implements ActionListener {
    protected VentanaPrincipal principal;
    private PrintStream consola;
    private ByteArrayOutputStream mensajes;

    public FormularioBase(String titulo, int ancho, int alto, VentanaPrincipal principal) {
        super(titulo, false, true, true, true); // no redimensionable; se puede cerrar, maximizar y minimizar
        this.principal = principal;
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(ancho, alto);
        setLayout(null); // posicionamiento con setBounds, como en la clase 12
    }

    // Crea y ubica los controles de la ventana
    protected abstract void componentes();

    // Vuelve a cargar las tablas y listas con los datos actuales
    public abstract void refrescar();

    // ================== CREACION DE CONTROLES ==================

    protected JLabel etiqueta(String texto, int x, int y, int ancho) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setBounds(x, y, ancho, 25); // X, Y, ANCHO, ALTO
        add(etiqueta);
        return etiqueta;
    }

    protected JLabel titulo(String texto, int x, int y, int ancho) {
        JLabel titulo = this.etiqueta(texto, x, y, ancho);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
        return titulo;
    }

    protected JTextField caja(int x, int y, int ancho) {
        JTextField caja = new JTextField();
        caja.setBounds(x, y, ancho, 25);
        add(caja);
        return caja;
    }

    protected JButton boton(String texto, int x, int y, int ancho) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, 30);
        boton.addActionListener(this); // el evento lo atiende actionPerformed() de la ventana
        add(boton);
        return boton;
    }

    protected JComboBox<String> combo(String[] opciones, int x, int y, int ancho) {
        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setBounds(x, y, ancho, 25);
        add(combo);
        return combo;
    }

    // Tabla de solo lectura dentro de un JScrollPane
    protected JTable tabla(String[] columnas, int x, int y, int ancho, int alto) {
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.getTableHeader().setReorderingAllowed(false);
        JScrollPane desplazamiento = new JScrollPane(tabla);
        desplazamiento.setBounds(x, y, ancho, alto);
        add(desplazamiento);
        return tabla;
    }

    protected JTextArea areaTexto(int x, int y, int ancho, int alto) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane desplazamiento = new JScrollPane(area);
        desplazamiento.setBounds(x, y, ancho, alto);
        add(desplazamiento);
        return area;
    }

    // Borra todas las filas de una tabla
    protected DefaultTableModel vaciar(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        return modelo;
    }

    // ================== VALIDACION CON LOS SET DEL DOMINIO ==================

    /**
     * Desvia System.out a un buffer para recoger los mensajes que imprimen
     * los metodos set mientras se llena un objeto.
     */
    protected void iniciarValidacion() {
        this.consola = System.out;
        this.mensajes = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.mensajes, true));
    }

    /**
     * Devuelve System.out a la consola, vuelve a escribir alli lo recogido
     * (asi sigue apareciendo en la ventana Output de NetBeans) y retorna solo
     * las lineas de error. Si no hubo errores retorna una cadena vacia.
     */
    protected String terminarValidacion() {
        System.out.flush();
        System.setOut(this.consola);
        String texto = this.mensajes.toString();
        System.out.print(texto);
        String errores = "";
        String[] lineas = texto.split("\\R");
        for (int i = 0; i < lineas.length; i++) {
            if (lineas[i].startsWith("Error") || lineas[i].startsWith("SIN VACANTES")) {
                errores = errores + lineas[i] + "\n";
            }
        }
        return errores;
    }

    // Igual que leerEntero(Scanner) de la consola: devuelve -1 si no es un numero
    protected int leerEntero(JTextField caja, String campo) {
        try {
            return Integer.parseInt(caja.getText().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: el campo " + campo + " debe ser un numero entero");
            return -1;
        }
    }

    protected double leerDecimal(JTextField caja, String campo) {
        try {
            return Double.parseDouble(caja.getText().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            System.out.println("Error: el campo " + campo + " debe ser un numero");
            return -1;
        }
    }

    // Devuelve null si la fecha no tiene el formato aaaa-mm-dd
    protected LocalDate leerFecha(JTextField caja, String campo) {
        try {
            return LocalDate.parse(caja.getText().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Error: el campo " + campo + " debe tener el formato aaaa-mm-dd");
            return null;
        }
    }

    // Extrae el codigo de un elemento de lista con forma "EST-001 - Nombre"
    protected String codigoDe(Object elemento) {
        if (elemento == null) {
            return "";
        }
        String texto = elemento.toString();
        int guion = texto.indexOf(" - ");
        return guion > 0 ? texto.substring(0, guion) : texto;
    }

    protected String soles(double monto) {
        return "S/ " + String.format("%.2f", monto);
    }

    // ================== MENSAJES AL USUARIO ==================

    protected void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Revise los datos", JOptionPane.ERROR_MESSAGE);
    }

    protected void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    protected boolean confirmar(String pregunta) {
        int respuesta = JOptionPane.showConfirmDialog(this, pregunta, getTitle(), JOptionPane.YES_NO_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }
}
