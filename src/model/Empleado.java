/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author cristian
 * @author juanj
 */
public class Empleado extends Persona implements Serializable {

    private double salario;
    private int codigo;
    private static final long serialVersionUID = 1L;

    public Empleado(String nombre, int cedula, int edad, double salario, int codigo) {
        super(nombre, cedula, edad);
        this.salario = salario;
        this.codigo = codigo;
    }

    public Empleado() {
        super(null, 0, 0);
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

}
