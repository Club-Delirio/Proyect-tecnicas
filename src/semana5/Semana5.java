package semana5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;
import semana5.excepciones.DocumentoInvalidoException;
import semana5.excepciones.PersonaNoEncontradaException;

/**
 * Clase principal de la práctica de la semana 5.
 *
 * Integra los tres temas evaluados:
 *   1. Sobrecarga de métodos  -> Persona y Calculadora
 *   2. Manejo de errores      -> try / catch / finally y excepciones propias
 *   3. Colecciones            -> PersonaController (ArrayList + HashMap)
 *
 * Ejecución:
 *   java semana5.Semana5          -> menú interactivo
 *   java semana5.Semana5 demo     -> ejecuta la demostración automática
 */
public class Semana5 {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final PersonaController controlador = new PersonaController();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
            ejecutarDemostracion();
            return;
        }
        menuPrincipal();
    }

    // ==================================================================
    // MENÚ INTERACTIVO
    // ==================================================================

    private static void menuPrincipal() {
        int opcion = -1;
        do {
            System.out.println();
            System.out.println("=========================================");
            System.out.println("  SEMANA 5 - REGISTRO DE PERSONAS");
            System.out.println("=========================================");
            System.out.println(" 1. Registrar persona");
            System.out.println(" 2. Listar todas las personas");
            System.out.println(" 3. Listar por tipo de documento");
            System.out.println(" 4. Buscar por número de documento");
            System.out.println(" 5. Eliminar por número de documento");
            System.out.println(" 6. Ordenar por apellido paterno");
            System.out.println(" 7. Resumen por tipo de documento");
            System.out.println(" 8. Probar sobrecarga (Calculadora)");
            System.out.println(" 0. Salir");
            System.out.print("Elija una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
                switch (opcion) {
                    case 1: registrarPersona();   break;
                    case 2: controlador.listarPersonas(); break;
                    case 3: listarPorTipo();      break;
                    case 4: buscarPersona();      break;
                    case 5: eliminarPersona();    break;
                    case 6:
                        controlador.ordenarPorApellido();
                        System.out.println("Lista ordenada por apellido paterno.");
                        controlador.listarPersonas();
                        break;
                    case 7: mostrarResumen();     break;
                    case 8: probarCalculadora();  break;
                    case 0: System.out.println("Programa finalizado."); break;
                    default:
                        System.out.println("Opción no válida. Elija un número del 0 al 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese solo números en la opción del menú.");
            } finally {
                // El bloque finally se ejecuta siempre, haya error o no.
                System.out.println("[Operación finalizada]");
            }
        } while (opcion != 0);

        sc.close();
    }

    // ==================================================================
    // OPCIONES DEL MENÚ
    // ==================================================================

    private static void registrarPersona() {
        try {
            System.out.print("Tipo de documento (DNI / CE / RUC / PASAPORTE): ");
            String tipo = sc.nextLine().trim();

            System.out.print("Número de documento ("
                    + Persona.longitudEsperada(tipo) + " caracteres): ");
            String numero = sc.nextLine().trim();

            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Apellido paterno: ");
            String paterno = sc.nextLine().trim();

            System.out.print("Apellido materno: ");
            String materno = sc.nextLine().trim();

            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
            LocalDate fecha = LocalDate.parse(sc.nextLine().trim(), FORMATO);

            Persona persona = new Persona(tipo, numero, nombre, paterno, materno, fecha);

            if (controlador.agregarPersona(persona)) {
                System.out.println("Persona registrada correctamente.");
                persona.verDatos("REGISTRO NUEVO");
            }

        } catch (DocumentoInvalidoException e) {
            System.out.println("Error de documento: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Fecha inválida. Use el formato dd/MM/yyyy, por ejemplo 15/03/2004.");
        }
    }

    private static void listarPorTipo() {
        System.out.print("Tipo de documento a listar: ");
        controlador.listarPersonas(sc.nextLine().trim());
    }

    private static void buscarPersona() {
        System.out.print("Número de documento a buscar: ");
        String numero = sc.nextLine().trim();
        try {
            Persona encontrada = controlador.buscarPorDocumento(numero);
            encontrada.verDatos("RESULTADO DE BÚSQUEDA");
        } catch (PersonaNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void eliminarPersona() {
        System.out.print("Número de documento a eliminar: ");
        String numero = sc.nextLine().trim();
        try {
            controlador.eliminarPorDocumento(numero);
            System.out.println("Registro eliminado. Quedan "
                    + controlador.getCantidad() + " persona(s).");
        } catch (PersonaNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void mostrarResumen() {
        if (controlador.estaVacia()) {
            System.out.println("No hay registros para resumir.");
            return;
        }
        System.out.println("Resumen por tipo de documento:");
        for (Map.Entry<String, Integer> entrada : controlador.contarPorTipoDocumento().entrySet()) {
            System.out.println("  " + entrada.getKey() + ": " + entrada.getValue());
        }
        System.out.println("Total de objetos Persona creados: " + Persona.getTotalCreadas());
    }

    private static void probarCalculadora() {
        Calculadora calc = new Calculadora();
        System.out.println("calcular(5, 3)            = " + calc.calcular(5, 3));
        System.out.println("calcular(5, 3, 2)         = " + calc.calcular(5, 3, 2));
        System.out.println("calcular(5.5, 3.2)        = " + calc.calcular(5.5, 3.2));
        System.out.println("calcular(\"Hola\", \"UPN\")   = " + calc.calcular("Hola", "UPN"));
        System.out.println("calcular(1,2,3,4,5)       = " + calc.calcular(1, 2, 3, 4, 5));

        System.out.print("Ingrese un divisor para 10: ");
        try {
            int divisor = Calculadora.convertirAEntero(sc.nextLine());
            System.out.println("10 / " + divisor + " = " + calc.dividir(10, divisor));
        } catch (NumberFormatException e) {
            System.out.println("Ingrese solo números.");
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }

    // ==================================================================
    // DEMOSTRACIÓN AUTOMÁTICA (para capturas de evidencia)
    // ==================================================================

    private static void ejecutarDemostracion() {
        Calculadora calc = new Calculadora();

        System.out.println("=== 1. SOBRECARGA DE MÉTODOS ===");
        System.out.println("calcular(5, 3)        -> " + calc.calcular(5, 3));
        System.out.println("calcular(5, 3, 2)     -> " + calc.calcular(5, 3, 2));
        System.out.println("calcular(5.5, 3.2)    -> " + calc.calcular(5.5, 3.2));
        System.out.println("calcular(\"Hola\",\"UPN\")-> " + calc.calcular("Hola", "UPN"));
        System.out.println("calcular(1,2,3,4,5)   -> " + calc.calcular(1, 2, 3, 4, 5));

        System.out.println();
        System.out.println("=== 2. MANEJO DE ERRORES ===");

        try {
            System.out.println("10 / 0 -> " + calc.dividir(10, 0));
        } catch (ArithmeticException e) {
            System.out.println("Capturado ArithmeticException: " + e.getMessage());
        }

        try {
            Calculadora.convertirAEntero("abc");
        } catch (NumberFormatException e) {
            System.out.println("Capturado NumberFormatException: entrada no numérica \"abc\".");
        }

        try {
            new Persona("DNI", "123");
        } catch (DocumentoInvalidoException e) {
            System.out.println("Capturado DocumentoInvalidoException: " + e.getMessage());
        }

        try {
            new Persona("LICENCIA", "12345678");
        } catch (DocumentoInvalidoException e) {
            System.out.println("Capturado DocumentoInvalidoException: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 3. COLECCIONES ===");
        try {
            controlador.agregarPersona(
                new Persona("DNI", "70123456", "Jeanpher", "Vásquez", "Chávez",
                            LocalDate.of(2004, 3, 15)),
                new Persona("DNI", "70987654", "María", "Sánchez", "Rojas",
                            LocalDate.of(2003, 11, 2)),
                new Persona("CE",  "001234567", "Luis", "Alvarado", "Díaz",
                            LocalDate.of(2001, 7, 30)),
                new Persona("RUC", "10701234561", "Ana", "Bustamante", "León",
                            LocalDate.of(1999, 1, 20))
            );
            System.out.println("Personas registradas: " + controlador.getCantidad());

            // Documento duplicado
            controlador.agregarPersona(
                new Persona("DNI", "70123456", "Otro", "Registro", "Duplicado",
                            LocalDate.of(2000, 5, 5)));

        } catch (DocumentoInvalidoException e) {
            System.out.println("Error al cargar datos de prueba: " + e.getMessage());
        }

        controlador.listarPersonas();

        System.out.println();
        controlador.listarPersonas("DNI");

        System.out.println();
        controlador.listarPersonas(20, 25);

        System.out.println();
        System.out.println("Índice fuera de rango:");
        controlador.obtenerPorIndice(7);

        System.out.println();
        try {
            System.out.println("Buscando el documento 99999999...");
            controlador.buscarPorDocumento("99999999");
        } catch (PersonaNoEncontradaException e) {
            System.out.println("Capturado: " + e.getMessage());
        }

        System.out.println();
        try {
            controlador.eliminarPorDocumento("70987654");
            System.out.println("Eliminado 70987654. Quedan " + controlador.getCantidad() + ".");
        } catch (PersonaNoEncontradaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        controlador.ordenarPorApellido();
        System.out.println("Lista ordenada por apellido paterno:");
        controlador.listarPersonas();

        System.out.println();
        System.out.println("Resumen por tipo de documento:");
        for (Map.Entry<String, Integer> e : controlador.contarPorTipoDocumento().entrySet()) {
            System.out.println("  " + e.getKey() + ": " + e.getValue());
        }
        System.out.println("Total de objetos Persona creados: " + Persona.getTotalCreadas());
    }
}
