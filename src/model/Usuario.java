/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author juanj
 */
public class Usuario implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String user;
    private String password;
    
    public Usuario(String usuario, String password){
        super();
        this.user=usuario;
        this.password=password;
    }
    
    public Usuario(){
        super();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    

    @Override
    public String toString() {
        return "Usuario{" + "usuario=" + user + ", password=" + password + '}';
    }
    
    
}
