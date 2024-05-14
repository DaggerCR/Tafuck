package com.mycompany.lexico;

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
        Token[] tokens = Scanner.scan();
        for (Token token : tokens) 
        {
            System.out.println("Token: " +
                "tipo =" + token.getTipo() +
                ", lexema ='" + token.getLexema() + '\'' +
                ", linea =" + token.getLinea() +
                ", columna =" + token.getColumna());
        }
    }
    
}
