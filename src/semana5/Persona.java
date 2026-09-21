/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana5;

import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author jhose
 */
public class Persona {
    private String tipo_doc;
    private String nro_documento;
    private String nombre;
    private String ape_paterno;
    private String ape_materno;
    private LocalDate fecha_nacimiento;

    public Persona() {

    }

    public Persona(String tipo_doc) {
        this.setTipo_doc(tipo_doc);
    }

    public Persona(String tipo_doc, String nro_documento) {
        this.setTipo_doc(tipo_doc);
        this.setNro_documento(nro_documento);
    }

    public Persona(String tipo_doc, String nro_documento, String nombre,
            String ape_paterno, String ape_materno, LocalDate fecha_nacimiento) {
        this.setTipo_doc(tipo_doc);
        this.setNro_documento(nro_documento);
        this.nombre = nombre;
        this.ape_paterno = ape_paterno;
        this.ape_materno = ape_materno;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        if (tipo_doc == null) {
            System.out.println("Error: el tipo de documento no puede estar vacio");
            return;
        }
        if (tipo_doc.equals("DNI") || tipo_doc.equals("CE")) {
            this.tipo_doc = tipo_doc;
        } else {
            System.out.println("Error: tipo de documento invalido. Use DNI o CE");
        }
    }

    public String getNro_documento() {
        return nro_documento;
    }

    public void setNro_documento(String nro_documento) {
        if (this.tipo_doc == null) {
            System.out.println("Primero debe de ingresar el tipo de documento");
            return;
        }
        if (nro_documento == null || nro_documento.trim().isEmpty()) {
            System.out.println("Error: el numero de documento no puede estar vacio");
            return;
        }
        if (this.tipo_doc.equals("DNI") && nro_documento.length() == 8) {
            this.nro_documento = nro_documento;
        } else if (this.tipo_doc.equals("CE") && nro_documento.length() == 10) {
            this.nro_documento = nro_documento;
        } else {
            System.out.println("Error: Para " + this.tipo_doc + " el numero debe tener "
                    + (this.tipo_doc.equals("DNI") ? "8" : "10") + " digitos.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe_paterno() {
        return ape_paterno;
    }

    public void setApe_paterno(String ape_paterno) {
        this.ape_paterno = ape_paterno;
    }

    public String getApe_materno() {
        return ape_materno;
    }

    public void setApe_materno(String ape_materno) {
        this.ape_materno = ape_materno;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        if (fecha_nacimiento == null) {
            System.out.println("Error: la fecha de nacimiento no puede estar vacia");
            return;
        }
        if (fecha_nacimiento.isAfter(LocalDate.now())) {
            System.out.println("Error: la fecha de nacimiento no puede ser futura");
            return;
        }
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public void VerDatos() {
        System.out.println(" Persona TIPO DOC: " + this.tipo_doc
                + " nro documento : " + this.nro_documento + " NOMBRE: " + this.nombre
                + " apellido : " + this.ape_paterno + " apellido materno: " + this.ape_materno
                + " FECHA DE NACIMIENTO: " + this.fecha_nacimiento);
    }

    public void VerDatos(String titulo) {
        System.out.println("---------- " + titulo + " ----------");
        this.VerDatos();
    }

    public String NombreCompleto() {
        return this.nombre + " " + this.ape_paterno + " " + this.ape_materno;
    }

    public String NombreCompleto(boolean apellidos_primero) {
        if (apellidos_primero) {
            return this.ape_paterno + " " + this.ape_materno + ", " + this.nombre;
        }
        return this.NombreCompleto();
    }

    public int CalcularEdad() {
        return this.CalcularEdad(LocalDate.now());
    }

    public int CalcularEdad(LocalDate fecha_referencia) {
        if (this.fecha_nacimiento == null || fecha_referencia == null) {
            return 0;
        }
        return Period.between(this.fecha_nacimiento, fecha_referencia).getYears();
    }
}
