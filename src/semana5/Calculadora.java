package semana5;

/**
 * Clase de apoyo para demostrar de forma aislada la SOBRECARGA DE MÉTODOS
 * y el MANEJO DE ERRORES aritméticos.
 *
 * El método calcular() está sobrecargado por cantidad de parámetros,
 * por tipo de dato y con parámetros variables (varargs).
 */
public class Calculadora {

    /** Suma de dos enteros. */
    public int calcular(int a, int b) {
        return a + b;
    }

    /** Suma de tres enteros: misma firma de nombre, distinta cantidad de parámetros. */
    public int calcular(int a, int b, int c) {
        return a + b + c;
    }

    /** Suma de dos decimales: misma cantidad de parámetros, distinto tipo de dato. */
    public double calcular(double a, double b) {
        return a + b;
    }

    /** Concatenación de dos textos: sobrecarga con tipo String. */
    public String calcular(String a, String b) {
        return a + " " + b;
    }

    /** Suma de una cantidad variable de enteros (varargs). */
    public int calcular(int... numeros) {
        int suma = 0;
        for (int n : numeros) {
            suma += n;
        }
        return suma;
    }

    /**
     * División entera.
     *
     * @throws ArithmeticException cuando el divisor es cero. Se relanza con un
     *         mensaje entendible para el usuario final.
     */
    public int dividir(int dividendo, int divisor) {
        try {
            return dividendo / divisor;
        } catch (ArithmeticException e) {
            throw new ArithmeticException("No es posible dividir entre cero.");
        }
    }

    /** División decimal: sobrecarga por tipo de dato. */
    public double dividir(double dividendo, double divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("No es posible dividir entre cero.");
        }
        return dividendo / divisor;
    }

    /**
     * Convierte un texto a número entero.
     *
     * @throws NumberFormatException si el texto no representa un número.
     */
    public static int convertirAEntero(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new NumberFormatException("El valor ingresado está vacío.");
        }
        return Integer.parseInt(texto.trim());
    }
}
