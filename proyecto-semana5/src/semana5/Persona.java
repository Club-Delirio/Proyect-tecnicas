package semana5;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import semana5.excepciones.DocumentoInvalidoException;

/**
 * Modelo de una persona.
 *
 * Demuestra los temas de la semana 3 y 4 del sílabo:
 *  - Sobrecarga de constructores (4 firmas distintas).
 *  - Sobrecarga de métodos: verDatos, getNombreCompleto y calcularEdad.
 *  - Modificador estático: constantes y método utilitario validarDocumento.
 *  - Manejo de errores: lanza DocumentoInvalidoException ante datos incorrectos.
 *
 * @author Equipo - Técnicas de Programación Orientada a Objetos (SIST1202A)
 */
public class Persona {

    // Tipos de documento aceptados (constantes estáticas)
    public static final String DNI = "DNI";
    public static final String CARNET_EXTRANJERIA = "CE";
    public static final String PASAPORTE = "PASAPORTE";
    public static final String RUC = "RUC";

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Contador estático: cuántos objetos Persona se han creado
    private static int totalCreadas = 0;

    private String tipoDocumento;
    private String nroDocumento;
    private String nombre;
    private String paterno;
    private String materno;
    private LocalDate fechaNacimiento;

    // ------------------------------------------------------------------
    // SOBRECARGA DE CONSTRUCTORES
    // ------------------------------------------------------------------

    /** Constructor vacío. Deja la persona sin datos, lista para usar los setters. */
    public Persona() {
        totalCreadas++;
    }

    /** Constructor con un solo parámetro: solo el tipo de documento. */
    public Persona(String tipoDocumento) throws DocumentoInvalidoException {
        this();
        setTipoDocumento(tipoDocumento);
    }

    /** Constructor con dos parámetros: tipo y número de documento. */
    public Persona(String tipoDocumento, String nroDocumento)
            throws DocumentoInvalidoException {
        this(tipoDocumento);
        setNroDocumento(nroDocumento);
    }

    /** Constructor completo. */
    public Persona(String tipoDocumento, String nroDocumento, String nombre,
                   String paterno, String materno, LocalDate fechaNacimiento)
            throws DocumentoInvalidoException {
        this(tipoDocumento, nroDocumento);
        setNombre(nombre);
        setPaterno(paterno);
        setMaterno(materno);
        setFechaNacimiento(fechaNacimiento);
    }

    // ------------------------------------------------------------------
    // VALIDACIONES (método estático)
    // ------------------------------------------------------------------

    /** Devuelve la cantidad de dígitos o caracteres que exige cada tipo de documento. */
    public static int longitudEsperada(String tipoDocumento) {
        if (tipoDocumento == null) {
            return 0;
        }
        switch (tipoDocumento.toUpperCase()) {
            case DNI:                return 8;
            case CARNET_EXTRANJERIA: return 9;
            case RUC:                return 11;
            case PASAPORTE:          return 12;
            default:                 return 0;
        }
    }

    /**
     * Valida el número de documento según su tipo.
     *
     * @throws DocumentoInvalidoException si el número es nulo, vacío, tiene una
     *         longitud incorrecta o contiene caracteres no numéricos cuando no debe.
     */
    public static void validarDocumento(String tipoDocumento, String nroDocumento)
            throws DocumentoInvalidoException {

        int esperado = longitudEsperada(tipoDocumento);
        if (esperado == 0) {
            throw new DocumentoInvalidoException(
                    "Tipo de documento no reconocido: " + tipoDocumento
                    + ". Use DNI, CE, RUC o PASAPORTE.");
        }
        if (nroDocumento == null || nroDocumento.trim().isEmpty()) {
            throw new DocumentoInvalidoException(
                    "El número de documento no puede estar vacío.");
        }
        String numero = nroDocumento.trim();
        if (numero.length() != esperado) {
            throw new DocumentoInvalidoException(
                    "Para " + tipoDocumento + " el número debe tener "
                    + esperado + " caracteres. Se recibieron " + numero.length() + ".");
        }
        // El pasaporte admite letras; los demás documentos solo dígitos.
        if (!PASAPORTE.equalsIgnoreCase(tipoDocumento) && !numero.matches("\\d+")) {
            throw new DocumentoInvalidoException(
                    "El número de " + tipoDocumento + " solo debe contener dígitos.");
        }
    }

    // ------------------------------------------------------------------
    // SOBRECARGA DE MÉTODOS
    // ------------------------------------------------------------------

    /** Muestra los datos de la persona con el encabezado por defecto. */
    public void verDatos() {
        verDatos("PERSONA");
    }

    /** Muestra los datos de la persona con un encabezado personalizado. */
    public void verDatos(String titulo) {
        System.out.println("---------- " + titulo + " ----------");
        System.out.println("Documento : " + tipoDocumento + " " + nroDocumento);
        System.out.println("Nombre    : " + getNombreCompleto());
        System.out.println("Nacimiento: "
                + (fechaNacimiento == null ? "(sin registrar)"
                                           : fechaNacimiento.format(FORMATO_FECHA)));
        System.out.println("Edad      : " + calcularEdad() + " años");
    }

    /** Nombre completo en el formato "Nombre Paterno Materno". */
    public String getNombreCompleto() {
        return getNombreCompleto(false);
    }

    /**
     * Nombre completo.
     *
     * @param apellidosPrimero true devuelve "Paterno Materno, Nombre";
     *                         false devuelve "Nombre Paterno Materno".
     */
    public String getNombreCompleto(boolean apellidosPrimero) {
        String n = nombre  == null ? "" : nombre.trim();
        String p = paterno == null ? "" : paterno.trim();
        String m = materno == null ? "" : materno.trim();

        String completo = apellidosPrimero
                ? (p + " " + m + ", " + n)
                : (n + " " + p + " " + m);

        completo = completo.replaceAll("\\s+", " ").trim();
        return completo.isEmpty() || completo.equals(",") ? "(sin nombre)" : completo;
    }

    /** Edad calculada a la fecha actual. */
    public int calcularEdad() {
        return calcularEdad(LocalDate.now());
    }

    /** Edad calculada a una fecha de referencia (por ejemplo, al cierre del ciclo). */
    public int calcularEdad(LocalDate fechaReferencia) {
        if (fechaNacimiento == null || fechaReferencia == null
                || fechaNacimiento.isAfter(fechaReferencia)) {
            return 0;
        }
        return Period.between(fechaNacimiento, fechaReferencia).getYears();
    }

    // ------------------------------------------------------------------
    // GETTERS Y SETTERS
    // ------------------------------------------------------------------

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) throws DocumentoInvalidoException {
        if (longitudEsperada(tipoDocumento) == 0) {
            throw new DocumentoInvalidoException(
                    "Tipo de documento no reconocido: " + tipoDocumento
                    + ". Use DNI, CE, RUC o PASAPORTE.");
        }
        this.tipoDocumento = tipoDocumento.toUpperCase();
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) throws DocumentoInvalidoException {
        if (this.tipoDocumento == null) {
            throw new DocumentoInvalidoException(
                    "Primero debe ingresar el tipo de documento.");
        }
        validarDocumento(this.tipoDocumento, nroDocumento);
        this.nroDocumento = nroDocumento.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public static int getTotalCreadas() {
        return totalCreadas;
    }

    @Override
    public String toString() {
        return String.format("%-9s %-12s %-35s %3d años",
                tipoDocumento, nroDocumento, getNombreCompleto(), calcularEdad());
    }
}
