package academia;

/**
 * Interfaz que comparten todas las clases cuyos objetos se guardan en los
 * archivos de texto: Persona (y por herencia Estudiante y Docente), Ciclo (y
 * por herencia CicloRegular y CursoIntensivo), Matricula y Asistencia.
 *
 * Esas clases no tienen una superclase en comun, pero todas saben mostrarse y
 * convertirse en una linea de texto. La interfaz declara ese contrato una sola
 * vez, y ArchivoUtil puede guardar cualquier lista de objetos Registrable sin
 * saber de que clase son (polimorfismo por interfaz).
 *
 * Estudiante extiende Persona y, a traves de ella, tambien es Registrable:
 * es un caso de herencia multiple de tipo.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public interface Registrable {

    // Muestra los datos del objeto en consola
    void verDatos();

    // Devuelve el objeto como una linea de texto separada por punto y coma
    String convertirALinea();
}
