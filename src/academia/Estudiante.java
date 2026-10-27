package academia;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 * Estudiante de la academia. Hereda de Persona y guarda su colegio de
 * procedencia, la carrera a la que postula, los datos de su apoderado y los
 * ciclos en los que se matriculo.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class Estudiante extends Persona {
    private String codigo;
    private LocalDate fec_nacimiento;
    private String colegio;
    private String carrera_objetivo;
    private String apoderado;
    private LocalDate fec_ultima_visita;
    private ArrayList<Ciclo> matriculas = new ArrayList(); // un estudiante lleva varios ciclos

    // Al crear el estudiante su ultima visita es la fecha de hoy
    public Estudiante() {
        this.fec_ultima_visita = LocalDate.now();
    }

    public Estudiante(String tipo_doc, String nro_doc, String nombres, String ape_paterno,
            String ape_materno, String telefono, String codigo, LocalDate fec_nacimiento,
            String colegio, String carrera_objetivo, String apoderado) {
        super(tipo_doc, nro_doc, nombres, ape_paterno, ape_materno, telefono);
        this.setCodigo(codigo);
        this.setFec_nacimiento(fec_nacimiento);
        this.setColegio(colegio);
        this.setCarrera_objetivo(carrera_objetivo);
        this.setApoderado(apoderado);
        this.fec_ultima_visita = LocalDate.now();
    }

    public String getCodigo() {
        return codigo;
    }

    // Formato EST-000 (EST, guion y 3 digitos)
    public void setCodigo(String codigo) {
        codigo = codigo.toUpperCase();
        if (codigo.length() == 7 && codigo.startsWith("EST-") && codigo.substring(4).matches("[0-9]+")) {
            this.codigo = codigo;
        } else {
            System.out.println("Error: el codigo debe tener el formato EST-000 (ejemplo EST-001)");
        }
    }

    public LocalDate getFec_nacimiento() {
        return fec_nacimiento;
    }

    // El postulante debe tener entre 14 y 30 anios y no puede nacer en el futuro
    public void setFec_nacimiento(LocalDate fec_nacimiento) {
        if (fec_nacimiento.isAfter(LocalDate.now())) {
            System.out.println("Error: la fecha de nacimiento no puede ser futura");
            return;
        }
        int anios = Period.between(fec_nacimiento, LocalDate.now()).getYears();
        if (anios < 14 || anios > 30) {
            System.out.println("Error: la edad del postulante debe estar entre 14 y 30 anios, usted ingreso " + anios);
        } else {
            this.fec_nacimiento = fec_nacimiento;
        }
    }

    public int calcularEdad() {
        if (this.fec_nacimiento == null) {
            return 0;
        }
        return Period.between(this.fec_nacimiento, LocalDate.now()).getYears();
    }

    public boolean esMenorDeEdad() {
        return this.calcularEdad() < 18;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        if (colegio.trim().length() >= 3) {
            this.colegio = colegio.trim();
        } else {
            System.out.println("Error: el colegio de procedencia debe tener al menos 3 caracteres");
        }
    }

    public String getCarrera_objetivo() {
        return carrera_objetivo;
    }

    public void setCarrera_objetivo(String carrera_objetivo) {
        if (carrera_objetivo.trim().length() >= 3) {
            this.carrera_objetivo = carrera_objetivo.trim();
        } else {
            System.out.println("Error: la carrera objetivo debe tener al menos 3 caracteres");
        }
    }

    public String getApoderado() {
        return apoderado;
    }

    // Si el postulante es menor de edad el apoderado es obligatorio
    public void setApoderado(String apoderado) {
        if (this.esMenorDeEdad() && apoderado.trim().length() < 5) {
            System.out.println("Error: el postulante es menor de edad, el apoderado es obligatorio");
            return;
        }
        this.apoderado = apoderado.trim();
    }

    public LocalDate getFec_ultima_visita() {
        return fec_ultima_visita;
    }

    // Actualiza la fecha de ultima visita cuando el estudiante vuelve a la academia
    public void registrarVisita() {
        this.fec_ultima_visita = LocalDate.now();
    }

    public ArrayList<Ciclo> getMatriculas() {
        return matriculas;
    }

    // Guarda el ciclo en el historial del estudiante (relacion 1 a muchos)
    public void agregarMatricula(Ciclo ciclo) {
        this.matriculas.add(ciclo);
        this.registrarVisita();
    }

    @Override
    public void verDatos() {
        System.out.println("ESTUDIANTE  Codigo: " + this.codigo + "  Documento: " + this.tipo_doc
                + " " + this.nro_doc + "  Nombre: " + this.getNombreEnNomina());
        System.out.println("   Edad: " + this.calcularEdad() + " anios"
                + (this.esMenorDeEdad() ? " (MENOR DE EDAD)" : "")
                + "  Telefono: " + this.telefono
                + "  Colegio: " + this.colegio
                + "  Postula a: " + this.carrera_objetivo);
        System.out.println("   Apoderado: " + (this.apoderado == null || this.apoderado.isEmpty()
                ? "no registra" : this.apoderado)
                + "  Ultima visita: " + this.fec_ultima_visita);
        if (this.matriculas.size() > 0) {
            System.out.println("   Sus matriculas son: ");
            for (Ciclo c : this.matriculas) {
                System.out.println("   - " + c.getTipo() + " " + c.getCodigo() + " " + c.getNombre()
                        + "  (" + c.calcularCategoria() + ")");
            }
        } else {
            System.out.println("   No tiene matriculas");
        }
    }

    // Linea del archivo: ESTUDIANTE;tipo_doc;nro_doc;nombres;ape_pat;ape_mat;telefono;codigo;fec_nac;colegio;carrera;apoderado
    @Override
    public String convertirALinea() {
        return "ESTUDIANTE;" + this.datosComunesEnLinea() + ";" + this.codigo + ";"
                + this.fec_nacimiento + ";" + this.colegio + ";" + this.carrera_objetivo + ";"
                + this.apoderado;
    }
}
