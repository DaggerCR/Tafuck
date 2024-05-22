package com.mycompany.lexico;


import java.util.List;

public class Producción {
    private String noTerminal;
    private List<String> secuencia;

    public Producción(String noTerminal, List<String> secuencia) {
        this.noTerminal = noTerminal;
        this.secuencia = secuencia;
    }

    public String getNoTerminal() {
        return noTerminal;
    }

    public List<String> getSecuencia() {
        return secuencia;
    }
    
    
}


