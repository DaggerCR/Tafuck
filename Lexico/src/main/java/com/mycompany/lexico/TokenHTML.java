package com.mycompany.lexico;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenHTML {
    private String lexema;
    private int fila;
    private int columna;
    private String tipo;

    public String getTipo() {
        return tipo;
    }
    
    public TokenHTML(String lexema, int fila, int columna, String tipo) {
        this.lexema = lexema;
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
    }

    public String toHTMLTableRow() {
        return "<tr><td>" + lexema + "</td><td>" + fila + "</td><td>" + columna + "</td><td>" + tipo + "</td></tr>";
    }
}