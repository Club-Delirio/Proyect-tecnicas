package academia;

/**
 * Clase abstracta Persona. Tiene los datos que comparten el estudiante y el
 * docente. Obliga a las clases hijas a implementar verDatos() y
 * convertirALinea().
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public abstract class Persona {
    protected String tipo_doc;
    protected String nro_doc;
    protected String nombres;
    protected String ape_paterno;
    protected String ape_materno;
    protected String telefono;

    // Constructor vacio: los datos se ingresan despues con los setters
    public Persona() {
    }

    public Persona(String tipo_doc, String nro_doc, String nombres, String ape_paterno,
            String ape_materno, String telefono) {
        this.setTipo_doc(tipo_doc);
        this.setNro_doc(nro_doc);
        this.setNombres(nombres);
        this.setApe_paterno(ape_paterno);
        this.setApe_materno(ape_materno);
        this.setTelefono(telefono);
    }

    // Metodo abstracto: cada clase hija muestra sus datos a su manera
    public abstract void verDatos();

    // Metodo abstracto: cada clase hija arma su propia linea para el archivo
    public abstract String convertirALinea();

    // Parte de la linea que es igual para el estudiante y para el docente
    protected String datosComunesEnLinea() {
        return this.tipo_doc + ";" + this.nro_doc + ";" + this.nombres + ";"
                + this.ape_paterno + ";" + this.ape_materno + ";" + this.telefono;
    }

    public String getNombreCompleto() {
        return this.nombres + " " + this.ape_paterno + " " + this.ape_materno;
    }

    // Nombre en formato de nomina: apellidos antes del nombre
    public String getNombreEnNomina() {
        return this.ape_paterno + " " + this.ape_materno + ", " + this.nombres;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    // Solo acepta DNI o CE (se guarda en mayusculas)
    public void setTipo_doc(String tipo_doc) {
        if (tipo_doc.equalsIgnoreCase("DNI") || tipo_doc.equalsIgnoreCase("CE")) {
            this.tipo_doc = tipo_doc.toUpperCase();
        } else {
            System.out.println("Error: tipo de documento invalido, solo se acepta DNI o CE");
        }
    }

    public String getNro_doc() {
        return nro_doc;
    }

    // DNI = 8 digitos, CE = 9 digitos; exige ingresar antes el tipo de documento
    public void setNro_doc(String nro_doc) {
        if (this.tipo_doc == null) {
            System.out.println("Error: primero debe ingresar el tipo de documento");
            return;
        }
        if (!nro_doc.matches("[0-9]+")) {
            System.out.println("Error: el numero de documento solo debe tener digitos");
        } else if (this.tipo_doc.equals("DNI") && nro_doc.length() != 8) {
            System.out.println("Error: el DNI debe tener 8 digitos");
        } else if (this.tipo_doc.equals("CE") && nro_doc.length() != 9) {
            System.out.println("Error: el CE debe tener 9 digitos");
        } else {
            this.nro_doc = nro_doc;
        }
    }

    public String getNombres() {
        return nombres;
    }

    // Solo letras y espacios, minimo 2 caracteres
    public void setNombres(String nombres) {
        if (nombres.trim().length() >= 2 && nombres.matches("[a-zA-ZaeiouAEIOUnN ]+")) {
            this.nombres = nombres.trim();
        } else {
            System.out.println("Error: los nombres solo deben tener letras (minimo 2)");
        }
    }

    public String getApe_paterno() {
        return ape_paterno;
    }

    public void setApe_paterno(String ape_paterno) {
        if (ape_paterno.trim().length() >= 2 && ape_paterno.matches("[a-zA-ZaeiouAEIOUnN ]+")) {
            this.ape_paterno = ape_paterno.trim();
        } else {
            System.out.println("Error: el apellido paterno solo debe tener letras");
        }
    }

    public String getApe_materno() {
        return ape_materno;
    }

    public void setApe_materno(String ape_materno) {
        if (ape_materno.trim().length() >= 2 && ape_materno.matches("[a-zA-ZaeiouAEIOUnN ]+")) {
            this.ape_materno = ape_materno.trim();
        } else {
            System.out.println("Error: el apellido materno solo debe tener letras");
        }
    }

    public String getTelefono() {
        return telefono;
    }

    // Celular peruano: 9 digitos y empieza con 9
    public void setTelefono(String telefono) {
        if (telefono.length() == 9 && telefono.startsWith("9") && telefono.matches("[0-9]+")) {
            this.telefono = telefono;
        } else {
            System.out.println("Error: el telefono debe tener 9 digitos y empezar con 9");
        }
    }
}
