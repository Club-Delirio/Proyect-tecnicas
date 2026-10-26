package academia;

/**
 * Docente de la academia. Hereda de Persona y agrega su codigo, su
 * especialidad, su tarifa por hora y lo que lleva acumulado: las horas que
 * dicto y las matriculas de los estudiantes que tiene a su cargo como tutor.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class Docente extends Persona {
    private String codigo;
    private String especialidad;
    private double tarifa_hora;
    private double bonificacion;      // porcentaje por estudiante que tutorea, por ejemplo 2 = 2%
    private int horas_dictadas;
    private int cant_matriculas;
    private double monto_gestionado;

    // Constructor vacio: los datos se ingresan con los setters
    public Docente() {
    }

    public Docente(String tipo_doc, String nro_doc, String nombres, String ape_paterno,
            String ape_materno, String telefono, String codigo, String especialidad,
            double tarifa_hora, double bonificacion) {
        super(tipo_doc, nro_doc, nombres, ape_paterno, ape_materno, telefono);
        this.setCodigo(codigo);
        this.setEspecialidad(especialidad);
        this.setTarifa_hora(tarifa_hora);
        this.setBonificacion(bonificacion);
    }

    public String getCodigo() {
        return codigo;
    }

    // Formato DOC-000 (DOC, guion y 3 digitos)
    public void setCodigo(String codigo) {
        codigo = codigo.toUpperCase();
        if (codigo.length() == 7 && codigo.startsWith("DOC-") && codigo.substring(4).matches("[0-9]+")) {
            this.codigo = codigo;
        } else {
            System.out.println("Error: el codigo debe tener el formato DOC-000 (ejemplo DOC-001)");
        }
    }

    public String getEspecialidad() {
        return especialidad;
    }

    // Solo las areas que dicta la academia
    public void setEspecialidad(String especialidad) {
        especialidad = especialidad.toUpperCase();
        if (especialidad.equals("MATEMATICA") || especialidad.equals("FISICA")
                || especialidad.equals("QUIMICA") || especialidad.equals("BIOLOGIA")
                || especialidad.equals("COMUNICACION") || especialidad.equals("HISTORIA")) {
            this.especialidad = especialidad;
        } else {
            System.out.println("Error: la especialidad debe ser MATEMATICA, FISICA, QUIMICA, "
                    + "BIOLOGIA, COMUNICACION o HISTORIA");
        }
    }

    public double getTarifa_hora() {
        return tarifa_hora;
    }

    // Tarifa por hora permitida: de 15 a 120 soles
    public void setTarifa_hora(double tarifa_hora) {
        if (tarifa_hora >= 15 && tarifa_hora <= 120) {
            this.tarifa_hora = tarifa_hora;
        } else {
            System.out.println("Error: la tarifa por hora debe estar entre S/ 15 y S/ 120");
        }
    }

    public double getBonificacion() {
        return bonificacion;
    }

    // Porcentaje de bonificacion permitido: de 1 a 5
    public void setBonificacion(double bonificacion) {
        if (bonificacion >= 1 && bonificacion <= 5) {
            this.bonificacion = bonificacion;
        } else {
            System.out.println("Error: la bonificacion debe estar entre 1 y 5 por ciento");
        }
    }

    public int getHoras_dictadas() {
        return horas_dictadas;
    }

    public int getCant_matriculas() {
        return cant_matriculas;
    }

    public double getMonto_gestionado() {
        return monto_gestionado;
    }

    // Suma las horas que el docente dicto en el periodo
    public void registrarHoras(int horas) {
        if (horas <= 0 || horas > 200) {
            System.out.println("Error: las horas dictadas deben estar entre 1 y 200 al mes");
            return;
        }
        this.horas_dictadas = this.horas_dictadas + horas;
    }

    // Suma una matricula y su monto al acumulado del docente tutor
    public void registrarMatricula(double precio_final) {
        this.cant_matriculas = this.cant_matriculas + 1;
        this.monto_gestionado = this.monto_gestionado + precio_final;
    }

    // Pago por dictado = tarifa por hora x horas dictadas
    public double calcularPagoHoras() {
        return Math.round(this.tarifa_hora * this.horas_dictadas * 100) / 100.0;
    }

    // Bonificacion = monto gestionado x porcentaje de bonificacion
    public double calcularBonificacion() {
        return Math.round(this.monto_gestionado * this.bonificacion) / 100.0;
    }

    public double calcularPagoTotal() {
        return this.calcularPagoHoras() + this.calcularBonificacion();
    }

    @Override
    public void verDatos() {
        System.out.println("DOCENTE  Codigo: " + this.codigo + "  Documento: " + this.tipo_doc
                + " " + this.nro_doc + "  Nombre: " + this.getNombreEnNomina());
        System.out.println("   Especialidad: " + this.especialidad
                + "  Tarifa: S/ " + String.format("%.2f", this.tarifa_hora) + " por hora"
                + "  Bonificacion: " + this.bonificacion + "%"
                + "  Telefono: " + this.telefono);
        System.out.println("   Horas dictadas: " + this.horas_dictadas
                + "  Pago por dictado: S/ " + String.format("%.2f", this.calcularPagoHoras())
                + "  Tutorias: " + this.cant_matriculas
                + "  Bonificacion ganada: S/ " + String.format("%.2f", this.calcularBonificacion())
                + "  TOTAL: S/ " + String.format("%.2f", this.calcularPagoTotal()));
    }

    // Linea del archivo: DOCENTE;tipo_doc;nro_doc;nombres;ape_pat;ape_mat;telefono;codigo;especialidad;tarifa;bonificacion;horas
    @Override
    public String convertirALinea() {
        return "DOCENTE;" + this.datosComunesEnLinea() + ";" + this.codigo + ";"
                + this.especialidad + ";" + this.tarifa_hora + ";" + this.bonificacion + ";"
                + this.horas_dictadas;
    }
}
