/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package academia;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Caso: Sistema de gestion de matricula y control de asistencia de la
 * Academia Preuniversitaria San Fernando.
 *
 * Se registran los ciclos que ofrece la academia (ciclos regulares y cursos
 * intensivos), los docentes, los estudiantes, las matriculas y la asistencia.
 * Al final se muestran las listas, el historial y las estadisticas, y se
 * guarda todo en archivos de texto.
 *
 * @author Cachi Tantalean, Anderson Alexis
 * @author Chuquimango Verastegui, Jhocep Jhanpier
 * @author Rodrigo Ortiz, Sumil Arturi
 * @author Rojas Valdivia, Jhann Frank
 */
public class AcademiaSanFernando {

    /**
     * El programa sigue el orden de trabajo de la academia: ciclos, docentes,
     * estudiantes, campana de descuento, matriculas, asistencia y reportes.
     * En cada seccion se pregunta "Desea ingresar otro s/n".
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Cada coleccion se maneja a traves de su controlador. El flujo no
        // toca nunca el ArrayList de forma directa: las reglas de negocio
        // (no duplicar un codigo, no pasarse del aforo) viven en una sola
        // clase y no repartidas por el main.
        PersonaController personas = new PersonaController();          // estudiantes y docentes (upcasting)
        CicloController ciclos = new CicloController();                // ciclos y cursos (upcasting)
        MatriculaController matriculas = new MatriculaController();
        AsistenciaController asistencias = new AsistenciaController();
        ArchivoUtil archivo = new ArchivoUtil("datos");
        String rpta;
        String nombre_campana = "SIN CAMPANA";
        double desc_campana = 0;
        boolean hay_datos = false;

        System.out.println("===== ACADEMIA PREUNIVERSITARIA SAN FERNANDO =====");
        System.out.println("      Sistema de matricula y control de asistencia");

        // ================== 0. RECUPERAR LOS DATOS GUARDADOS ==================
        System.out.println();
        System.out.println("----- RECUPERAR LOS DATOS GUARDADOS -----");
        System.out.println("Desea recuperar los datos guardados en los archivos s/n");
        rpta = sc.nextLine();
        if (rpta.equalsIgnoreCase("s")) {
            cargarCiclos(archivo, ciclos);
            cargarPersonas(archivo, personas);
            cargarMatriculas(archivo, personas, ciclos, matriculas);
            cargarAsistencias(archivo, personas, ciclos, asistencias);
            System.out.println("Se recuperaron " + personas.getCantidad() + " personas, " + ciclos.getCantidad()
                    + " ciclos, " + matriculas.getCantidad() + " matriculas y " + asistencias.getCantidad()
                    + " asistencias de los archivos");
            if (personas.getCantidad() > 0 || ciclos.getCantidad() > 0) {
                hay_datos = true;
            }
        }

        // ================== DATOS DE PRUEBA ==================
        if (hay_datos == false) {
            System.out.println("Desea cargar datos de prueba s/n");
            rpta = sc.nextLine();
        } else {
            rpta = "n";
        }
        if (rpta.equalsIgnoreCase("s")) {
            Docente raul = new Docente("DNI", "41258963", "Raul", "Mendoza", "Chavez", "976543210",
                    "DOC-001", "MATEMATICA", 35, 3);
            Docente carmen = new Docente("DNI", "43698521", "Carmen Rosa", "Vasquez", "Rojas", "965321478",
                    "DOC-002", "COMUNICACION", 28, 2.5);
            raul.registrarHoras(80);
            carmen.registrarHoras(64);
            personas.agregarPersona(raul);
            personas.agregarPersona(carmen);

            Estudiante lucia = new Estudiante("DNI", "72581496", "Lucia Fernanda", "Quispe", "Tanta",
                    "987654321", "EST-001", LocalDate.parse("2008-05-20"), "San Ramon",
                    "Medicina", "Maria Tanta Lopez");
            Estudiante diego = new Estudiante("DNI", "73695214", "Diego Alonso", "Huaman", "Paredes",
                    "912345678", "EST-002", LocalDate.parse("2006-11-08"), "Juan XXIII",
                    "Ingenieria de Sistemas", "Jose Huaman Diaz");
            Estudiante ana = new Estudiante("CE", "001245789", "Ana Sofia", "Cruz", "Marin",
                    "933221144", "EST-003", LocalDate.parse("2007-02-14"), "Santa Teresita",
                    "Derecho", "Rosa Marin Flores");
            Estudiante bruno = new Estudiante("DNI", "74125896", "Bruno Cesar", "Terrones", "Vigo",
                    "956871234", "EST-004", LocalDate.parse("2009-07-30"), "Dos de Mayo",
                    "Medicina", "Elena Vigo Alcantara");
            Estudiante karla = new Estudiante("DNI", "75236914", "Karla Yamile", "Chilon", "Bautista",
                    "941258963", "EST-005", LocalDate.parse("2008-01-12"), "Cristo Rey",
                    "Contabilidad", "Pedro Chilon Mego");
            Estudiante marco = new Estudiante("DNI", "76398521", "Marco Antonio", "Llanos", "Saldana",
                    "932147856", "EST-006", LocalDate.parse("2005-09-25"), "San Marcelino",
                    "Ingenieria Civil", "-");
            personas.agregarPersona(lucia);
            personas.agregarPersona(diego);
            personas.agregarPersona(ana);
            personas.agregarPersona(bruno);
            personas.agregarPersona(karla);
            personas.agregarPersona(marco);

            Ciclo anual = new CicloRegular("CIC-001", "Ciclo Anual 2026-II",
                    LocalDate.parse("2026-08-03"), LocalDate.parse("2026-12-18"),
                    1450, "PRESENCIAL", 30, "MANANA", 5, 10);
            Ciclo semestral = new CicloRegular("CIC-002", "Ciclo Semestral Virtual",
                    LocalDate.parse("2026-09-01"), LocalDate.parse("2026-12-20"),
                    850, "VIRTUAL", 120, "NOCHE", 4, 8);
            Ciclo repaso = new CursoIntensivo("CUR-001", "Repaso Intensivo Ciencias",
                    LocalDate.parse("2026-10-05"), LocalDate.parse("2026-11-28"),
                    620, "PRESENCIAL", 25, "CIENCIAS", 8, 5);
            Ciclo vacacional = new CursoIntensivo("CUR-002", "Vacacional Letras",
                    LocalDate.parse("2026-12-28"), LocalDate.parse("2027-01-22"),
                    380, "PRESENCIAL", 20, "LETRAS", 4, 4);
            ciclos.agregarCiclo(anual);
            ciclos.agregarCiclo(semestral);
            ciclos.agregarCiclo(repaso);
            ciclos.agregarCiclo(vacacional);

            // Seis matriculas de prueba: tres al contado y tres fraccionadas
            Estudiante[] quien = {lucia, bruno, karla, diego, ana, marco};
            Docente[] quien_tutor = {raul, raul, raul, carmen, carmen, raul};
            Ciclo[] en_que = {anual, anual, anual, semestral, semestral, repaso};
            String[] pago = {"CONTADO", "FRACCIONADO", "CONTADO", "FRACCIONADO", "CONTADO", "FRACCIONADO"};
            int[] cuotas = {0, 3, 0, 3, 0, 2};
            for (int i = 0; i < quien.length; i++) {
                Matricula m = new Matricula(quien[i], quien_tutor[i], en_que[i],
                        nombre_campana, desc_campana);
                m.setForma_pago(pago[i]);
                if (cuotas[i] > 0) {
                    m.setNro_cuotas(cuotas[i]);
                }
                m.calcularPrecioFinal();
                matriculas.agregarMatricula(m);
            }

            // Nueve registros de asistencia de tres estudiantes
            String[] estados = {"PRESENTE", "TARDANZA", "PRESENTE",
                                "AUSENTE", "AUSENTE", "PRESENTE",
                                "PRESENTE", "JUSTIFICADO", "AUSENTE"};
            Estudiante[] quienes = {lucia, lucia, lucia, diego, diego, diego, bruno, bruno, bruno};
            Ciclo[] donde = {anual, anual, anual, semestral, semestral, semestral, anual, anual, anual};
            for (int i = 0; i < estados.length; i++) {
                Asistencia a = new Asistencia(quienes[i], donde[i],
                        LocalDate.now().minusDays(9 - i));
                a.setEstado(estados[i]);
                asistencias.agregarAsistencia(a);
            }

            System.out.println("Datos de prueba cargados: " + personas.getCantidad() + " personas, "
                    + ciclos.getCantidad() + " ciclos, " + matriculas.getCantidad() + " matriculas y "
                    + asistencias.getCantidad() + " asistencias");
        }

        // ================== 1. REGISTRO DE CICLOS Y CURSOS ==================
        System.out.println();
        System.out.println("----- REGISTRO DE CICLOS Y CURSOS -----");
        System.out.println("Desea ingresar un ciclo o curso s/n");
        rpta = sc.nextLine();
        while (rpta.equalsIgnoreCase("s")) {
            String tipo = "";
            while (!tipo.equalsIgnoreCase("CICLO") && !tipo.equalsIgnoreCase("CURSO")) {
                System.out.println("Ingrese el tipo (CICLO para un ciclo regular, CURSO para un intensivo)");
                tipo = sc.nextLine();
            }
            // UPCASTING: el objeto es CicloRegular o CursoIntensivo pero lo guardamos en una variable Ciclo
            Ciclo c;
            if (tipo.equalsIgnoreCase("CICLO")) {
                c = new CicloRegular();
            } else {
                c = new CursoIntensivo();
            }
            // El controlador es quien sabe si el codigo ya esta usado
            boolean repetido = false;
            while (c.getCodigo() == null && repetido == false) {
                System.out.println("Ingrese el codigo (CIC-000 para ciclo regular, CUR-000 para curso)");
                String codigo = sc.nextLine();
                if (ciclos.buscarCiclo(codigo) != null) {
                    repetido = true;
                }
                if (repetido) {
                    System.out.println("Error: ya existe un ciclo con el codigo " + codigo.toUpperCase()
                            + ", no se puede registrar dos veces");
                } else {
                    c.setCodigo(codigo);
                }
            }
            if (repetido == false) {
                while (c.getNombre() == null) {
                    System.out.println("Ingrese el nombre del ciclo o curso");
                    c.setNombre(sc.nextLine());
                }
                while (c.getFec_inicio() == null) {
                    System.out.println("Ingrese la fecha de inicio (aaaa-mm-dd)");
                    c.setFec_inicio(leerFecha(sc));
                }
                while (c.getFec_fin() == null) {
                    System.out.println("Ingrese la fecha de fin (aaaa-mm-dd)");
                    c.setFec_fin(leerFecha(sc));
                }
                while (c.getCosto_base() == 0) {
                    System.out.println("Ingrese el costo base (S/)");
                    c.setCosto_base(leerDecimal(sc));
                }
                while (c.getModalidad() == null) {
                    System.out.println("Ingrese la modalidad (PRESENCIAL o VIRTUAL)");
                    c.setModalidad(sc.nextLine());
                }
                while (c.getVacantes() == -1) {
                    System.out.println("Ingrese el numero de vacantes");
                    c.setVacantes(leerEntero(sc));
                }
                // DOWNCASTING: para ingresar los datos que solo tiene cada clase hija
                if (c instanceof CicloRegular) {
                    CicloRegular cr = (CicloRegular) c;
                    while (cr.getTurno() == null) {
                        System.out.println("Ingrese el turno (MANANA, TARDE o NOCHE)");
                        cr.setTurno(sc.nextLine());
                    }
                    while (cr.getMeses_duracion() == 0) {
                        System.out.println("Ingrese la duracion en meses (2 a 10)");
                        cr.setMeses_duracion(leerEntero(sc));
                    }
                    while (cr.getNro_simulacros() == 0) {
                        System.out.println("Ingrese el numero de simulacros incluidos");
                        cr.setNro_simulacros(leerEntero(sc));
                    }
                } else {
                    CursoIntensivo ci = (CursoIntensivo) c;
                    while (ci.getArea() == null) {
                        System.out.println("Ingrese el area (CIENCIAS, LETRAS o INGENIERIA)");
                        ci.setArea(sc.nextLine());
                    }
                    while (ci.getSemanas_duracion() == 0) {
                        System.out.println("Ingrese la duracion en semanas (2 a 12)");
                        ci.setSemanas_duracion(leerEntero(sc));
                    }
                    while (ci.getHoras_diarias() == 0) {
                        System.out.println("Ingrese las horas diarias de clase (2 a 8)");
                        ci.setHoras_diarias(leerEntero(sc));
                    }
                }
                ciclos.agregarCiclo(c);
                System.out.println("Registrado. Categoria asignada automaticamente: " + c.calcularCategoria()
                        + "  Descuento propio: " + c.calcularDescuento() + "%");
            }
            System.out.println("Desea ingresar otro ciclo o curso s/n");
            rpta = sc.nextLine();
        }

        // Catalogo agrupado por categoria
        System.out.println();
        System.out.println("----- CATALOGO POR CATEGORIA -----");
        System.out.println("Ciclo regular: menos de S/ 600 BASICO, hasta S/ 1200 INTERMEDIO, mas PREMIUM");
        System.out.println("Curso intensivo: 6 horas diarias o mas PREMIUM; si no: menos de S/ 400 BASICO, hasta S/ 900 INTERMEDIO");
        String[] categorias = {"BASICO", "INTERMEDIO", "PREMIUM"};
        for (int i = 0; i < categorias.length; i++) {
            System.out.println("[" + categorias[i] + "]");
            for (Ciclo c : ciclos.getLista()) {
                if (c.calcularCategoria().equals(categorias[i])) {
                    System.out.println("   " + c.getTipo() + "  " + c.getCodigo() + "  " + c.getNombre()
                            + "  " + c.getModalidad() + "  S/ " + String.format("%.2f", c.getCosto_base())
                            + "  Vacantes " + c.getMatriculados() + "/" + c.getVacantes()
                            + "  " + c.getEstado());
                }
            }
        }

        // ================== 2. REGISTRO DE DOCENTES ==================
        System.out.println();
        System.out.println("----- REGISTRO DE DOCENTES -----");
        System.out.println("Desea ingresar un docente s/n");
        rpta = sc.nextLine();
        while (rpta.equalsIgnoreCase("s")) {
            Docente d = new Docente();
            while (d.getTipo_doc() == null) {
                System.out.println("Ingrese tipo de documento (DNI o CE)");
                d.setTipo_doc(sc.nextLine());
            }
            while (d.getNro_doc() == null) {
                System.out.println("Ingrese numero de documento");
                d.setNro_doc(sc.nextLine());
            }
            boolean existe = false;
            for (Persona p : personas.getLista()) {
                if (p.getNro_doc().equals(d.getNro_doc())) {
                    existe = true;
                }
            }
            if (existe) {
                System.out.println("Error: ya existe una persona con el documento " + d.getNro_doc());
            } else {
                while (d.getCodigo() == null) {
                    System.out.println("Ingrese el codigo del docente (DOC-000)");
                    d.setCodigo(sc.nextLine());
                }
                while (d.getNombres() == null) {
                    System.out.println("Ingrese sus nombres");
                    d.setNombres(sc.nextLine());
                }
                while (d.getApe_paterno() == null) {
                    System.out.println("Ingrese su apellido paterno");
                    d.setApe_paterno(sc.nextLine());
                }
                while (d.getApe_materno() == null) {
                    System.out.println("Ingrese su apellido materno");
                    d.setApe_materno(sc.nextLine());
                }
                while (d.getTelefono() == null) {
                    System.out.println("Ingrese su telefono (9 digitos)");
                    d.setTelefono(sc.nextLine());
                }
                while (d.getEspecialidad() == null) {
                    System.out.println("Ingrese su especialidad (MATEMATICA, FISICA, QUIMICA, BIOLOGIA, COMUNICACION o HISTORIA)");
                    d.setEspecialidad(sc.nextLine());
                }
                while (d.getTarifa_hora() == 0) {
                    System.out.println("Ingrese su tarifa por hora (S/ 15 a S/ 120)");
                    d.setTarifa_hora(leerDecimal(sc));
                }
                while (d.getBonificacion() == 0) {
                    System.out.println("Ingrese su bonificacion por tutoria en % (1 a 5)");
                    d.setBonificacion(leerDecimal(sc));
                }
                System.out.println("Ingrese las horas que dicto este mes");
                d.registrarHoras(leerEntero(sc));
                personas.agregarPersona(d); // upcasting: el Docente se guarda en la lista de Persona
                System.out.println("Docente registrado correctamente");
            }
            System.out.println("Desea ingresar otro docente s/n");
            rpta = sc.nextLine();
        }

        // ================== 3. REGISTRO DE ESTUDIANTES ==================
        System.out.println();
        System.out.println("----- REGISTRO DE ESTUDIANTES -----");
        System.out.println("Desea ingresar un estudiante s/n");
        rpta = sc.nextLine();
        while (rpta.equalsIgnoreCase("s")) {
            Estudiante e = new Estudiante();
            while (e.getTipo_doc() == null) {
                System.out.println("Ingrese tipo de documento (DNI o CE)");
                e.setTipo_doc(sc.nextLine());
            }
            while (e.getNro_doc() == null) {
                System.out.println("Ingrese numero de documento");
                e.setNro_doc(sc.nextLine());
            }
            // Si el estudiante ya existe no se vuelve a registrar, solo se actualiza su ultima visita
            Persona encontrada = null;
            for (Persona p : personas.getLista()) {
                if (p.getNro_doc().equals(e.getNro_doc())) {
                    encontrada = p;
                }
            }
            if (encontrada != null) {
                if (encontrada instanceof Estudiante) {
                    Estudiante existente = (Estudiante) encontrada; // downcasting
                    existente.registrarVisita();
                    System.out.println("Aviso: el estudiante ya esta registrado, no se vuelve a registrar. Se actualizo su ultima visita");
                    existente.verDatos();
                } else {
                    System.out.println("Error: ese documento ya pertenece a un docente");
                }
            } else {
                while (e.getCodigo() == null) {
                    System.out.println("Ingrese el codigo del estudiante (EST-000)");
                    e.setCodigo(sc.nextLine());
                }
                while (e.getNombres() == null) {
                    System.out.println("Ingrese sus nombres");
                    e.setNombres(sc.nextLine());
                }
                while (e.getApe_paterno() == null) {
                    System.out.println("Ingrese su apellido paterno");
                    e.setApe_paterno(sc.nextLine());
                }
                while (e.getApe_materno() == null) {
                    System.out.println("Ingrese su apellido materno");
                    e.setApe_materno(sc.nextLine());
                }
                while (e.getTelefono() == null) {
                    System.out.println("Ingrese su telefono (9 digitos)");
                    e.setTelefono(sc.nextLine());
                }
                while (e.getFec_nacimiento() == null) {
                    System.out.println("Ingrese su fecha de nacimiento (aaaa-mm-dd)");
                    e.setFec_nacimiento(leerFecha(sc));
                }
                while (e.getColegio() == null) {
                    System.out.println("Ingrese su colegio de procedencia");
                    e.setColegio(sc.nextLine());
                }
                while (e.getCarrera_objetivo() == null) {
                    System.out.println("Ingrese la carrera a la que postula");
                    e.setCarrera_objetivo(sc.nextLine());
                }
                while (e.getApoderado() == null) {
                    if (e.esMenorDeEdad()) {
                        System.out.println("El postulante es MENOR DE EDAD. Ingrese el nombre de su apoderado");
                    } else {
                        System.out.println("Ingrese el nombre de su apoderado (puede dejarlo vacio escribiendo un guion)");
                    }
                    e.setApoderado(sc.nextLine());
                }
                personas.agregarPersona(e); // upcasting
                System.out.println("Estudiante registrado correctamente");
            }
            System.out.println("Desea ingresar otro estudiante s/n");
            rpta = sc.nextLine();
        }

        // ================== 4. CAMPANA DE DESCUENTO ==================
        System.out.println();
        System.out.println("----- CAMPANA DE DESCUENTO -----");
        System.out.println("Hay alguna campana de descuento vigente s/n");
        rpta = sc.nextLine();
        if (rpta.equalsIgnoreCase("s")) {
            System.out.println("Ingrese el nombre de la campana (ej. PRONTO PAGO)");
            nombre_campana = sc.nextLine().toUpperCase();
            desc_campana = -1;
            while (desc_campana < 0 || desc_campana > 25) {
                System.out.println("Ingrese el descuento de campana en % (0 a 25)");
                desc_campana = leerDecimal(sc);
                if (desc_campana < 0 || desc_campana > 25) {
                    System.out.println("Error: el descuento debe estar entre 0 y 25%");
                }
            }
        }
        System.out.println("Campana vigente: " + nombre_campana + " (" + desc_campana + "%)");

        // ================== 5. MATRICULAS ==================
        System.out.println();
        System.out.println("----- REGISTRO DE MATRICULAS -----");
        System.out.println("Desea registrar una matricula s/n");
        rpta = sc.nextLine();
        while (rpta.equalsIgnoreCase("s")) {
            System.out.println("Ingrese el codigo del estudiante (EST-000)");
            Estudiante e = personas.buscarEstudiante(sc.nextLine());
            Docente tutor = null;
            if (e == null) {
                System.out.println("Error: no existe un estudiante con ese codigo. Primero debe registrarlo");
            } else {
                System.out.println("Estudiante: " + e.getNombreCompleto() + "  Edad: " + e.calcularEdad()
                        + (e.esMenorDeEdad() ? "  MENOR DE EDAD, apoderado: " + e.getApoderado() : ""));
                System.out.println("Ingrese el codigo del docente tutor (DOC-000)");
                tutor = personas.buscarDocente(sc.nextLine());
                if (tutor == null) {
                    System.out.println("Error: no existe un docente con ese codigo");
                }
            }
            Ciclo c = null;
            if (e != null && tutor != null) {
                System.out.println("Tutor: " + tutor.getNombreCompleto() + " (" + tutor.getEspecialidad() + ")");
                System.out.println("Ingrese el codigo del ciclo o curso");
                c = ciclos.buscarCiclo(sc.nextLine());
                if (c == null) {
                    System.out.println("Error: no existe un ciclo con ese codigo");
                } else {
                    c.verDatos(); // polimorfismo: verDatos() de CicloRegular o de CursoIntensivo
                    if (!c.hayVacantes()) {
                        System.out.println("SIN VACANTES: el ciclo " + c.getCodigo() + " ya esta "
                                + c.getEstado() + ", no se puede matricular a nadie mas");
                        c = null;
                    } else {
                        // El controlador es quien sabe si ya existe esa matricula
                        if (matriculas.existeMatricula(e, c)) {
                            System.out.println("Error: el estudiante ya esta matriculado en ese ciclo");
                            c = null;
                        }
                    }
                }
            }
            // Calcular el precio final y registrar la matricula
            if (c != null) {
                Matricula mat = new Matricula(e, tutor, c, nombre_campana, desc_campana);
                while (mat.getForma_pago() == null) {
                    System.out.println("Ingrese la forma de pago (CONTADO o FRACCIONADO)");
                    mat.setForma_pago(sc.nextLine());
                }
                if (mat.getForma_pago().equals("FRACCIONADO")) {
                    while (mat.getNro_cuotas() == 0) {
                        System.out.println("Ingrese el numero de cuotas (2, 3 o 4)");
                        mat.setNro_cuotas(leerEntero(sc));
                    }
                }
                mat.calcularPrecioFinal();
                mat.verDetallePrecio();
                System.out.println("Confirmar la matricula s/n");
                String confirma = sc.nextLine();
                if (confirma.equalsIgnoreCase("s")) {
                    // El controlador asigna el codigo, descuenta la vacante,
                    // guarda el ciclo en el historial del estudiante y acumula
                    // el monto gestionado al docente tutor.
                    if (matriculas.agregarMatricula(mat)) {
                        System.out.println("Matricula " + mat.getCodigo() + " registrada. El ciclo tiene ahora "
                                + c.vacantesDisponibles() + " vacante(s) y esta " + c.getEstado());
                    }
                } else {
                    System.out.println("Matricula cancelada, la vacante sigue disponible");
                }
            }
            System.out.println("Desea registrar otra matricula s/n");
            rpta = sc.nextLine();
        }

        // ================== 6. CONTROL DE ASISTENCIA ==================
        System.out.println();
        System.out.println("----- CONTROL DE ASISTENCIA -----");
        System.out.println("Desea registrar una asistencia s/n");
        rpta = sc.nextLine();
        while (rpta.equalsIgnoreCase("s")) {
            System.out.println("Ingrese el codigo del estudiante (EST-000)");
            Estudiante e = personas.buscarEstudiante(sc.nextLine());
            Ciclo c = null;
            if (e == null) {
                System.out.println("Error: no existe un estudiante con ese codigo");
            } else if (e.getMatriculas().isEmpty()) {
                System.out.println("Error: el estudiante " + e.getCodigo() + " no tiene ninguna matricula");
            } else {
                System.out.println("Sus ciclos matriculados son:");
                for (Ciclo m : e.getMatriculas()) {
                    System.out.println("   " + m.getCodigo() + "  " + m.getNombre());
                }
                System.out.println("Ingrese el codigo del ciclo");
                String cod = sc.nextLine();
                for (Ciclo m : e.getMatriculas()) {
                    if (m.getCodigo().equalsIgnoreCase(cod)) {
                        c = m;
                    }
                }
                if (c == null) {
                    System.out.println("Error: el estudiante no esta matriculado en ese ciclo");
                }
            }
            if (c != null) {
                System.out.println("Ingrese la fecha de la sesion (aaaa-mm-dd)");
                LocalDate fecha = leerFecha(sc);
                // El controlador es quien sabe si esa sesion ya se registro
                if (asistencias.existeRegistro(e, c, fecha)) {
                    System.out.println("Error: ya existe la asistencia de ese estudiante en ese ciclo y fecha");
                } else {
                    Asistencia a = new Asistencia(e, c, fecha);
                    if (a.getFecha() == null) {
                        System.out.println("No se registro la asistencia por fecha invalida");
                    } else {
                        while (a.getEstado() == null) {
                            System.out.println("Ingrese el estado (PRESENTE, TARDANZA, AUSENTE o JUSTIFICADO)");
                            a.setEstado(sc.nextLine());
                        }
                        System.out.println("Ingrese una observacion (puede dejarla vacia)");
                        a.setObservacion(sc.nextLine());
                        if (asistencias.agregarAsistencia(a)) {
                            System.out.println("Asistencia " + a.getCodigo() + " registrada");
                        }
                    }
                }
            }
            System.out.println("Desea registrar otra asistencia s/n");
            rpta = sc.nextLine();
        }

        // ================== 7. LISTAS Y REPORTES ==================
        System.out.println();
        System.out.println("===== LISTA DE CICLOS Y CURSOS =====");
        ciclos.listarCiclos(); // polimorfismo: verDatos() de CicloRegular o de CursoIntensivo

        System.out.println();
        System.out.println("===== LISTA DE ESTUDIANTES Y DOCENTES =====");
        personas.ordenarPorApellido();
        personas.listarPersonas(); // polimorfismo: verDatos() de Estudiante o de Docente

        System.out.println();
        System.out.println("===== HISTORIAL DE MATRICULAS =====");
        if (matriculas.getCantidad() == 0) {
            System.out.println("Aun no hay matriculas registradas");
        } else {
            for (Matricula mat : matriculas.getLista()) {
                System.out.println(mat.getCodigo() + "  " + mat.getFecha() + "  "
                        + mat.getEstudiante().getNombreEnNomina() + "  "
                        + mat.getCiclo().getNombre() + "  " + mat.getForma_pago()
                        + "  S/ " + String.format("%.2f", mat.getPrecio_final()));
            }

            // Estadisticas: se acumulan los montos y se cuentan las matriculas por tipo y categoria
            int regulares   = matriculas.contarPorTipo("CICLO REGULAR");
            int intensivos  = matriculas.contarPorTipo("CURSO INTENSIVO");
            int basico      = matriculas.contarPorCategoria("BASICO");
            int intermedio  = matriculas.contarPorCategoria("INTERMEDIO");
            int premium     = matriculas.contarPorCategoria("PREMIUM");
            int fraccionadas = matriculas.contarPorFormaPago("FRACCIONADO");
            double total      = matriculas.calcularIngresoTotal();
            double descuentos = matriculas.calcularTotalDescuentos();
            double recargos   = matriculas.calcularTotalRecargos();
            double campana = 0;
            for (Matricula mat : matriculas.getLista()) {
                campana = campana + mat.getMonto_campana();
            }
            System.out.println();
            System.out.println("===== ESTADISTICAS DE MATRICULA =====");
            System.out.println("Matriculas registradas : " + matriculas.getCantidad());
            System.out.println("Por tipo               : " + regulares + " ciclos regulares, " + intensivos + " cursos intensivos");
            System.out.println("Por categoria          : BASICO " + basico + " | INTERMEDIO " + intermedio + " | PREMIUM " + premium);
            System.out.println("Forma de pago          : " + (matriculas.getCantidad() - fraccionadas) + " al contado, " + fraccionadas + " fraccionadas");
            System.out.println("Matricula promedio     : S/ " + String.format("%.2f", total / matriculas.getCantidad()));

            System.out.println();
            System.out.println("===== REPORTE FINANCIERO =====");
            System.out.println("(-) Descuentos de campana : S/ " + String.format("%.2f", campana));
            System.out.println("(-) Descuentos del ciclo  : S/ " + String.format("%.2f", descuentos));
            System.out.println("(+) Recargos por cuotas   : S/ " + String.format("%.2f", recargos));
            System.out.println("INGRESO TOTAL             : S/ " + String.format("%.2f", total));
            for (Persona p : personas.getLista()) {
                if (p instanceof Docente) {
                    Docente d = (Docente) p; // downcasting
                    System.out.println("Pago de " + d.getNombreCompleto() + ": S/ "
                            + String.format("%.2f", d.calcularPagoTotal())
                            + "  (dictado S/ " + String.format("%.2f", d.calcularPagoHoras())
                            + " + bonificacion S/ " + String.format("%.2f", d.calcularBonificacion()) + ")");
                }
            }
            double planilla = personas.calcularPlanillaDocente();
            System.out.println("Planilla docente          : S/ " + String.format("%.2f", planilla));
            System.out.println("Ingreso neto              : S/ " + String.format("%.2f", total - planilla));
        }

        System.out.println();
        System.out.println("===== REPORTE DE ASISTENCIA =====");
        if (asistencias.getCantidad() == 0) {
            System.out.println("Aun no hay asistencias registradas");
        } else {
            int presentes = 0, tardanzas = 0, ausentes = 0, justificados = 0;
            for (Asistencia a : asistencias.getLista()) {
                if (a.getEstado().equals("PRESENTE")) {
                    presentes++;
                } else if (a.getEstado().equals("TARDANZA")) {
                    tardanzas++;
                } else if (a.getEstado().equals("AUSENTE")) {
                    ausentes++;
                } else {
                    justificados++;
                }
            }
            for (Persona p : personas.getLista()) {
                if (p instanceof Estudiante) {
                    Estudiante e = (Estudiante) p; // downcasting
                    int registros = 0, asistio = 0;
                    for (Asistencia a : asistencias.getLista()) {
                        if (a.getEstudiante().getCodigo().equals(e.getCodigo())) {
                            registros++;
                            if (a.cuentaComoAsistio()) {
                                asistio++;
                            }
                        }
                    }
                    if (registros > 0) {
                        double porcentaje = (asistio * 100.0) / registros;
                        System.out.println(e.getCodigo() + "  " + e.getNombreEnNomina()
                                + "  Asistencia: " + Math.round(porcentaje) + "%  (" + asistio + " de " + registros + ")"
                                + (porcentaje < 70 ? "   <-- RIESGO DE DESERCION" : ""));
                    }
                }
            }
            System.out.println("Presentes: " + presentes + " | Tardanzas: " + tardanzas
                    + " | Ausentes: " + ausentes + " | Justificados: " + justificados);
        }

        // ================== 8. GUARDAR LA INFORMACION EN ARCHIVOS ==================
        System.out.println();
        System.out.println("----- GUARDAR LA INFORMACION -----");
        System.out.println("Desea guardar la informacion en los archivos s/n");
        rpta = sc.nextLine();
        if (rpta.equalsIgnoreCase("s")) {
            guardarTodo(archivo, personas, ciclos, matriculas, asistencias);
            exportarReporteMatriculas(archivo, matriculas);
            System.out.println("Desea ver el contenido de un archivo s/n");
            rpta = sc.nextLine();
            if (rpta.equalsIgnoreCase("s")) {
                System.out.println("Ingrese el nombre del archivo (ciclos.txt, personas.txt, matriculas.txt o asistencias.txt)");
                archivo.mostrarArchivo(sc.nextLine());
            }
        }

        System.out.println();
        System.out.println("Fin del programa");
    }

    // ================== METODOS DE LECTURA SEGURA ==================

    // Lee un numero entero; si escriben letras lo vuelve a pedir
    public static int leerEntero(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero entero, ingrese de nuevo");
            }
        }
    }

    // Lee un numero decimal; si escriben letras lo vuelve a pedir
    public static double leerDecimal(Scanner sc) {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero, ingrese de nuevo");
            }
        }
    }

    // Lee una fecha en formato aaaa-mm-dd; si el formato es otro la vuelve a pedir
    public static LocalDate leerFecha(Scanner sc) {
        while (true) {
            try {
                return LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Error: la fecha debe tener el formato aaaa-mm-dd, ingrese de nuevo");
            }
        }
    }

    // ================== METODOS DE ARCHIVO ==================

    /**
     * Guarda las cuatro listas en cuatro archivos de texto. Cada objeto se
     * convierte en una linea con convertirALinea(): el metodo que se ejecuta
     * depende del objeto real, por polimorfismo.
     */
    public static void guardarTodo(ArchivoUtil archivo, PersonaController personas,
            CicloController ciclos, MatriculaController matriculas,
            AsistenciaController asistencias) {
        ArrayList<String> lineas = new ArrayList();
        for (Ciclo c : ciclos.getLista()) {
            lineas.add(c.convertirALinea());
        }
        archivo.guardarLineas("ciclos.txt", lineas);

        lineas = new ArrayList();
        for (Persona p : personas.getLista()) {
            lineas.add(p.convertirALinea());
        }
        archivo.guardarLineas("personas.txt", lineas);

        lineas = new ArrayList();
        for (Matricula m : matriculas.getLista()) {
            lineas.add(m.convertirALinea());
        }
        archivo.guardarLineas("matriculas.txt", lineas);

        lineas = new ArrayList();
        for (Asistencia a : asistencias.getLista()) {
            lineas.add(a.convertirALinea());
        }
        archivo.guardarLineas("asistencias.txt", lineas);
    }

    /**
     * Exporta el historial de matriculas a un archivo CSV con fila de
     * encabezados, para que la administracion lo abra en una hoja de calculo.
     */
    public static void exportarReporteMatriculas(ArchivoUtil archivo, MatriculaController matriculas) {
        ArrayList<String> lineas = new ArrayList();
        lineas.add("CODIGO;FECHA;ESTUDIANTE;TUTOR;CICLO;TIPO;CATEGORIA;FORMA_PAGO;PRECIO_FINAL");
        for (Matricula m : matriculas.getLista()) {
            lineas.add(m.getCodigo() + ";" + m.getFecha() + ";"
                    + m.getEstudiante().getNombreCompleto() + ";"
                    + m.getTutor().getNombreCompleto() + ";"
                    + m.getCiclo().getNombre() + ";" + m.getCiclo().getTipo() + ";"
                    + m.getCiclo().calcularCategoria() + ";" + m.getForma_pago() + ";"
                    + String.format("%.2f", m.getPrecio_final()));
        }
        archivo.guardarLineas("reporte_matriculas.csv", lineas);
    }

    /**
     * Lee ciclos.txt y vuelve a crear los objetos CicloRegular o
     * CursoIntensivo segun el primer campo de la linea.
     */
    public static void cargarCiclos(ArchivoUtil archivo, CicloController ciclos) {
        ArrayList<String> lineas = archivo.leerLineas("ciclos.txt");
        int cargados = 0;
        for (int i = 0; i < lineas.size(); i++) {
            String[] campo = lineas.get(i).split(";");
            if (campo.length < 13) {
                System.out.println("Aviso: la linea " + (i + 1) + " de ciclos.txt esta incompleta y se omite");
                continue;
            }
            try {
                Ciclo c;
                if (campo[0].equals("CICLO")) {
                    c = new CicloRegular(campo[1], campo[2], LocalDate.parse(campo[3]),
                            LocalDate.parse(campo[4]), Double.parseDouble(campo[5]), campo[6],
                            Integer.parseInt(campo[7]), campo[10], Integer.parseInt(campo[11]),
                            Integer.parseInt(campo[12]));
                } else {
                    c = new CursoIntensivo(campo[1], campo[2], LocalDate.parse(campo[3]),
                            LocalDate.parse(campo[4]), Double.parseDouble(campo[5]), campo[6],
                            Integer.parseInt(campo[7]), campo[10], Integer.parseInt(campo[11]),
                            Integer.parseInt(campo[12]));
                }
                c.setMatriculados(Integer.parseInt(campo[8]));
                c.setEstado(campo[9]);
                if (c.getCodigo() != null) {
                    ciclos.agregarCiclo(c);
                    cargados = cargados + 1;
                }
            } catch (Exception e) {
                System.out.println("Aviso: no se pudo leer la linea " + (i + 1) + " de ciclos.txt");
            }
        }
        System.out.println("Ciclos recuperados: " + cargados);
    }

    /**
     * Lee personas.txt y vuelve a crear los objetos Estudiante o Docente
     * segun el primer campo de la linea (upcasting a la lista de Persona).
     */
    public static void cargarPersonas(ArchivoUtil archivo, PersonaController personas) {
        ArrayList<String> lineas = archivo.leerLineas("personas.txt");
        int cargados = 0;
        for (int i = 0; i < lineas.size(); i++) {
            String[] campo = lineas.get(i).split(";");
            if (campo.length < 11) {
                System.out.println("Aviso: la linea " + (i + 1) + " de personas.txt esta incompleta y se omite");
                continue;
            }
            try {
                Persona p;
                if (campo[0].equals("ESTUDIANTE")) {
                    p = new Estudiante(campo[1], campo[2], campo[3], campo[4], campo[5], campo[6],
                            campo[7], LocalDate.parse(campo[8]), campo[9], campo[10], campo[11]);
                } else {
                    Docente d = new Docente(campo[1], campo[2], campo[3], campo[4], campo[5], campo[6],
                            campo[7], campo[8], Double.parseDouble(campo[9]), Double.parseDouble(campo[10]));
                    d.registrarHoras(Integer.parseInt(campo[11]));
                    p = d;
                }
                if (p.getNro_doc() != null) {
                    personas.agregarPersona(p);
                    cargados = cargados + 1;
                }
            } catch (Exception e) {
                System.out.println("Aviso: no se pudo leer la linea " + (i + 1) + " de personas.txt");
            }
        }
        System.out.println("Personas recuperadas: " + cargados);
    }

    /**
     * Lee matriculas.txt y vuelve a armar cada matricula buscando en las
     * listas al estudiante, al tutor y al ciclo que la linea referencia.
     * Tambien reconstruye el historial del estudiante y el acumulado del
     * docente. El contador de matriculados del ciclo ya viene del archivo, de
     * modo que aqui no se vuelve a sumar.
     */
    public static void cargarMatriculas(ArchivoUtil archivo, PersonaController personas,
            CicloController ciclos, MatriculaController matriculas) {
        ArrayList<String> lineas = archivo.leerLineas("matriculas.txt");
        int cargadas = 0;
        for (int i = 0; i < lineas.size(); i++) {
            String[] campo = lineas.get(i).split(";");
            if (campo.length < 10) {
                System.out.println("Aviso: la linea " + (i + 1) + " de matriculas.txt esta incompleta y se omite");
                continue;
            }
            try {
                Estudiante e = personas.buscarEstudiante(campo[2]);
                Docente d = personas.buscarDocente(campo[3]);
                Ciclo c = ciclos.buscarCiclo(campo[4]);
                if (e == null || d == null || c == null) {
                    System.out.println("Aviso: la matricula " + campo[0]
                            + " referencia datos que ya no existen y se omite");
                    continue;
                }
                Matricula m = new Matricula(e, d, c, campo[7], Double.parseDouble(campo[8]));
                m.setFecha(LocalDate.parse(campo[1]));
                m.setForma_pago(campo[5]);
                if (campo[5].equals("FRACCIONADO")) {
                    m.setNro_cuotas(Integer.parseInt(campo[6]));
                }
                m.calcularPrecioFinal();
                m.setCodigo(campo[0]);
                e.agregarMatricula(c);
                d.registrarMatricula(m.getPrecio_final());
                // Se agrega sin volver a descontar la vacante: el contador de
                // matriculados ya viene guardado en ciclos.txt.
                matriculas.agregarDesdeArchivo(m);
                cargadas = cargadas + 1;
            } catch (Exception e) {
                System.out.println("Aviso: no se pudo leer la linea " + (i + 1) + " de matriculas.txt");
            }
        }
        System.out.println("Matriculas recuperadas: " + cargadas);
    }

    /**
     * Lee asistencias.txt y vuelve a armar cada registro buscando al
     * estudiante y al ciclo que la linea referencia.
     */
    public static void cargarAsistencias(ArchivoUtil archivo, PersonaController personas,
            CicloController ciclos, AsistenciaController asistencias) {
        ArrayList<String> lineas = archivo.leerLineas("asistencias.txt");
        int cargadas = 0;
        for (int i = 0; i < lineas.size(); i++) {
            String[] campo = lineas.get(i).split(";");
            if (campo.length < 5) {
                System.out.println("Aviso: la linea " + (i + 1) + " de asistencias.txt esta incompleta y se omite");
                continue;
            }
            try {
                Estudiante e = personas.buscarEstudiante(campo[1]);
                Ciclo c = ciclos.buscarCiclo(campo[2]);
                if (e == null || c == null) {
                    System.out.println("Aviso: la asistencia " + campo[0]
                            + " referencia datos que ya no existen y se omite");
                    continue;
                }
                Asistencia a = new Asistencia(e, c, LocalDate.parse(campo[3]));
                a.setEstado(campo[4]);
                if (campo.length >= 6) {
                    a.setObservacion(campo[5]);
                }
                a.setCodigo(campo[0]);
                if (a.getEstado() != null) {
                    asistencias.agregarDesdeArchivo(a);
                    cargadas = cargadas + 1;
                }
            } catch (Exception e) {
                System.out.println("Aviso: no se pudo leer la linea " + (i + 1) + " de asistencias.txt");
            }
        }
        System.out.println("Asistencias recuperadas: " + cargadas);
    }
}
