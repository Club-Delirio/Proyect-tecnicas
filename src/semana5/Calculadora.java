package semana5;

/**
 * Clase de apoyo para demostrar de forma aislada la SOBRECARGA DE MÉTODOS
 * y el MANEJO DE ERRORES aritméticos.
 *
 * El método calcular() está sobrecargado por cantidad de parámetros,
 * por tipo de dato y con parámetros variables (varargs).
 */
public class Calculadora {

    public int calcular(int a, int b) {
        return a + b;
    }
    public int calcular(int a, int b, int c) {
        return a + b + c;
    }

    public double calcular(double a, double b) {
        return a + b;
    }
    public String calcular(String a, String b) {
        return a + " " + b;
    }
    public int calcular(int... numeros) {
        int suma = 0;
        for (int n : numeros) {
            suma += n;
        }
        return suma;
    }
    public int dividir(int dividendo, int divisor) {
        try {
            return dividendo / divisor;
        } catch (ArithmeticException e) {
            throw new ArithmeticException("No es posible dividir entre cero.");
        }
    }
    public double dividir(double dividendo, double divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("No es posible dividir entre cero.");
        }
        return dividendo / divisor;
    }

    public static int convertirAEntero(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new NumberFormatException("El valor ingresado está vacío.");
        }
        return Integer.parseInt(texto.trim());
    }
}
