package semana5.excepciones;

/**
 * Excepción propia que se lanza cuando el tipo o el número de documento
 * no cumplen con las reglas de validación definidas en Persona.
 *
 * Es una excepción verificada (extiende Exception), por lo que el compilador
 * obliga a capturarla o declararla con throws.
 */
public class DocumentoInvalidoException extends Exception {

    public DocumentoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public DocumentoInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
