package com.mycompany.lexico;

import java.util.List;

public class Tafuck_Compiler {
    public static void main(String[] args) 
    {
        System.out.println("Hacer el compilador aquí");
        Tafuck_Compiler.ver_Scanner();
    }
    
    // llama a la biblioteca del Scanner para realizar el análisis léxico 
    // solo para consulta en consola
    private static void ver_Scanner()
    {
        // Scanner.scan() ejecuta el Scanner y 
        List<Token> tokens = Scanner.scan();
        
        System.out.print("\n-------Lista de tokens-------\n"); 
        
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            System.out.println("Tipo: " + token.getTipoString() + "  Lexema: "+ token.getLexema() + "   Fila: " + token.getLinea() + "   Columna: " + token.getColumna());
        }
        System.out.print("\n------- Fi de la lista de tokens-------");
    }
    
}
