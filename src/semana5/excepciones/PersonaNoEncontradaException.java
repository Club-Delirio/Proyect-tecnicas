package semana5.excepciones;

/**
 * Excepción propia que se lanza cuando se busca o se elimina una persona
 * cuyo número de documento no existe dentro de la colección.
 */
public class PersonaNoEncontradaException extends Exception {

    public PersonaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
