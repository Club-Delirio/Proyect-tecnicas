package academia;

import java.time.LocalDate;

/**
 * Clase abstracta Ciclo. Representa lo que la academia ofrece: un ciclo
 * regular o un curso intensivo. Todos tienen codigo, nombre, fechas, costo
 * base, modalidad y un numero de vacantes.
 *
 * Obliga a las clases hijas (CicloRegular y CursoIntensivo) a implementar
 * verDatos(), calcularCategoria(), calcularDescuento(), getTipo() y
 * convertirALinea().
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public abstract class Ciclo {
    protected String codigo;
    protected String nombre;
    protected LocalDate fec_inicio;
    protected LocalDate fec_fin;
    protected double costo_base;
    protected String modalidad;          // PRESENCIAL o VIRTUAL
    protected int vacantes = -1;          // -1 = todavia no ingresado
    protected int matriculados = 0;
    protected String estado = "ABIERTO";  // ABIERTO o CERRADO

    // Constructor vacio: los datos se ingresan con los setters
    public Ciclo() {
    }

    public Ciclo(String codigo, String nombre, LocalDate fec_inicio, LocalDate fec_fin,
            double costo_base, String modalidad, int vacantes) {
        this.setCodigo(codigo);
        this.setNombre(nombre);
        this.setFec_inicio(fec_inicio);
        this.setFec_fin(fec_fin);
        this.setCosto_base(costo_base);
        this.setModalidad(modalidad);
        this.setVacantes(vacantes);
    }

    // ---------------- METODOS ABSTRACTOS ----------------
    public abstract void verDatos();

    public abstract String calcularCategoria();

    public abstract double calcularDescuento();   // porcentaje de descuento propio del tipo

    public abstract String getTipo();             // CICLO REGULAR o CURSO INTENSIVO

    public abstract String convertirALinea();

    // ---------------- METODOS COMUNES ----------------
    // Parte de la linea que es igual para el ciclo regular y el curso intensivo
    protected String datosComunesEnLinea() {
        return this.codigo + ";" + this.nombre + ";" + this.fec_inicio + ";" + this.fec_fin + ";"
                + this.costo_base + ";" + this.modalidad + ";" + this.vacantes + ";"
                + this.matriculados + ";" + this.estado;
    }

    public boolean hayVacantes() {
        return this.estado.equals("ABIERTO") && this.matriculados < this.vacantes;
    }

    public int vacantesDisponibles() {
        return this.vacantes - this.matriculados;
    }

    // Suma un matriculado y cierra el ciclo cuando se agotan las vacantes
    public void registrarMatriculado() {
        this.matriculados = this.matriculados + 1;
        if (this.matriculados >= this.vacantes) {
            this.estado = "CERRADO";
        }
    }

    // El ciclo esta vigente si la fecha de hoy cae dentro de su rango
    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(this.fec_inicio) && !hoy.isAfter(this.fec_fin);
    }

    // Datos que se muestran para cualquier ciclo; las hijas lo llaman dentro de verDatos()
    protected void verDatosGenerales() {
        System.out.println("   Codigo: " + this.codigo + "  Nombre: " + this.nombre
                + "  Modalidad: " + this.modalidad);
        System.out.println("   Del " + this.fec_inicio + " al " + this.fec_fin
                + "  Costo base: S/ " + String.format("%.2f", this.costo_base)
                + "  Categoria: " + this.calcularCategoria()
                + "  Vacantes: " + this.matriculados + "/" + this.vacantes
                + "  Estado: " + this.estado);
    }

    // ---------------- GETTERS Y SETTERS ----------------
    public String getCodigo() {
        return codigo;
    }

    // Formato CIC-000 o CUR-000 segun el tipo
    public void setCodigo(String codigo) {
        codigo = codigo.toUpperCase();
        if (codigo.length() == 7 && (codigo.startsWith("CIC-") || codigo.startsWith("CUR-"))
                && codigo.substring(4).matches("[0-9]+")) {
            this.codigo = codigo;
        } else {
            System.out.println("Error: el codigo debe tener el formato CIC-000 para un ciclo "
                    + "regular o CUR-000 para un curso intensivo");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre.trim().length() >= 3) {
            this.nombre = nombre.trim();
        } else {
            System.out.println("Error: el nombre debe tener al menos 3 caracteres");
        }
    }

    public LocalDate getFec_inicio() {
        return fec_inicio;
    }

    // No se puede programar un ciclo que empezo hace mas de un anio
    public void setFec_inicio(LocalDate fec_inicio) {
        if (fec_inicio.isBefore(LocalDate.now().minusYears(1))) {
            System.out.println("Error: la fecha de inicio no puede tener mas de un anio de antiguedad");
        } else {
            this.fec_inicio = fec_inicio;
        }
    }

    public LocalDate getFec_fin() {
        return fec_fin;
    }

    // La fecha de fin debe ser posterior a la de inicio; exige ingresar antes el inicio
    public void setFec_fin(LocalDate fec_fin) {
        if (this.fec_inicio == null) {
            System.out.println("Error: primero debe ingresar la fecha de inicio");
            return;
        }
        if (!fec_fin.isAfter(this.fec_inicio)) {
            System.out.println("Error: la fecha de fin debe ser posterior a la de inicio");
        } else {
            this.fec_fin = fec_fin;
        }
    }

    public double getCosto_base() {
        return costo_base;
    }

    // Costo permitido: de 150 a 5000 soles
    public void setCosto_base(double costo_base) {
        if (costo_base >= 150 && costo_base <= 5000) {
            this.costo_base = costo_base;
        } else {
            System.out.println("Error: el costo base debe estar entre S/ 150 y S/ 5000");
        }
    }

    public String getModalidad() {
        return modalidad;
    }

    // Solo PRESENCIAL o VIRTUAL
    public void setModalidad(String modalidad) {
        if (modalidad.equalsIgnoreCase("PRESENCIAL") || modalidad.equalsIgnoreCase("VIRTUAL")) {
            this.modalidad = modalidad.toUpperCase();
        } else {
            System.out.println("Error: la modalidad debe ser PRESENCIAL o VIRTUAL");
        }
    }

    public int getVacantes() {
        return vacantes;
    }

    // PRESENCIAL: de 10 a 45 vacantes; VIRTUAL: de 10 a 200
    public void setVacantes(int vacantes) {
        if (this.modalidad == null) {
            System.out.println("Error: primero debe ingresar si es PRESENCIAL o VIRTUAL");
        } else if (vacantes < 10) {
            System.out.println("Error: el ciclo debe tener al menos 10 vacantes");
        } else if (this.modalidad.equals("PRESENCIAL") && vacantes > 45) {
            System.out.println("Error: un ciclo PRESENCIAL no puede superar las 45 vacantes por aula");
        } else if (this.modalidad.equals("VIRTUAL") && vacantes > 200) {
            System.out.println("Error: un ciclo VIRTUAL no puede superar las 200 vacantes");
        } else {
            this.vacantes = vacantes;
        }
    }

    public int getMatriculados() {
        return matriculados;
    }

    public String getEstado() {
        return estado;
    }

    // Se usa al cargar del archivo un ciclo que ya estaba cerrado
    public void setEstado(String estado) {
        if (estado.equalsIgnoreCase("ABIERTO") || estado.equalsIgnoreCase("CERRADO")) {
            this.estado = estado.toUpperCase();
        } else {
            System.out.println("Error: el estado debe ser ABIERTO o CERRADO");
        }
    }

    public void setMatriculados(int matriculados) {
        if (matriculados >= 0) {
            this.matriculados = matriculados;
        }
    }
}
