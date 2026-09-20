/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package semana5;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author jhose
 */
public class Semana5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        PersonaController controla = new PersonaController();
        String opcion = "";

        while (!opcion.equals("0")) {
            System.out.println("");
            System.out.println("=========================================");
            System.out.println("  SEMANA 5 - REGISTRO DE PERSONAS");
            System.out.println("=========================================");
            System.out.println(" 1. Registrar persona");
            System.out.println(" 2. Listar todas las personas");
            System.out.println(" 3. Listar por tipo de documento");
            System.out.println(" 4. Listar por rango de edad");
            System.out.println(" 5. Buscar por numero de documento");
            System.out.println(" 6. Eliminar por numero de documento");
            System.out.println(" 7. Ordenar por apellido paterno");
            System.out.println(" 8. Contar por tipo de documento");
            System.out.println(" 0. Salir");
            System.out.print("Elija una opcion: ");
            opcion = sc.nextLine();

            try {
                if (opcion.equals("1")) {
                    registrarPersona(sc, controla);
                } else if (opcion.equals("2")) {
                    controla.listarPersonas();
                } else if (opcion.equals("3")) {
                    System.out.print("Ingrese el tipo de documento: ");
                    controla.listarPersonas(sc.nextLine());
                } else if (opcion.equals("4")) {
                    System.out.print("Ingrese la edad minima: ");
                    int minima = Integer.parseInt(sc.nextLine());
                    System.out.print("Ingrese la edad maxima: ");
                    int maxima = Integer.parseInt(sc.nextLine());
                    controla.listarPersonas(minima, maxima);
                } else if (opcion.equals("5")) {
                    System.out.print("Ingrese el numero de documento: ");
                    Persona encontrada = controla.buscarPersona(sc.nextLine());
                    if (encontrada == null) {
                        System.out.println("No se encontro esa persona");
                    } else {
                        encontrada.VerDatos("RESULTADO DE BUSQUEDA");
                        System.out.println("Nombre completo: " + encontrada.NombreCompleto(true));
                        System.out.println("Edad: " + encontrada.CalcularEdad() + " años");
                    }
                } else if (opcion.equals("6")) {
                    System.out.print("Ingrese el numero de documento: ");
                    controla.eliminarPersona(sc.nextLine());
                } else if (opcion.equals("7")) {
                    controla.ordenarPorApellido();
                    controla.listarPersonas();
                } else if (opcion.equals("8")) {
                    System.out.println("Total de personas: " + controla.getCantidad());
                    System.out.println("Con DNI: " + controla.contarPorTipo("DNI"));
                    System.out.println("Con CE: " + controla.contarPorTipo("CE"));
                } else if (opcion.equals("0")) {
                    System.out.println("Programa finalizado");
                } else {
                    System.out.println("Opcion invalida. Elija un numero del 0 al 8");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar solo numeros");
            } catch (DateTimeParseException e) {
                System.out.println("Error: la fecha debe tener el formato aaaa-mm-dd");
            } finally {
                System.out.println("[Operacion finalizada]");
            }
        }
        sc.close();
    }

    public static void registrarPersona(Scanner sc, PersonaController controla) {
        Persona p = new Persona();

        System.out.println("Ingrese tipo de documento");
        String tipo = sc.nextLine();
        p.setTipo_doc(tipo);

        System.out.println("Ingrese numero de documento");
        String nro = sc.nextLine();
        p.setNro_documento(nro);

        System.out.println("Ingrese su nombre: ");
        String nom = sc.nextLine();
        p.setNombre(nom);

        System.out.println("Ingrese su apellido paterno");
        String ape_pa = sc.nextLine();
        p.setApe_paterno(ape_pa);

        System.out.println("Ingrese su apellido materno");
        String ape_ma = sc.nextLine();
        p.setApe_materno(ape_ma);

        System.out.println("Ingrese su fecha de nacimiento (aaaa-mm-dd)");
        String fecha_nac = sc.nextLine();
        p.setFecha_nacimiento(LocalDate.parse(fecha_nac));

        if (p.getNro_documento() == null || p.getFecha_nacimiento() == null) {
            System.out.println("No se registro la persona por datos invalidos");
            return;
        }
        if (controla.AgregarPersona(p)) {
            p.VerDatos("REGISTRO NUEVO");
        }
    }
}
