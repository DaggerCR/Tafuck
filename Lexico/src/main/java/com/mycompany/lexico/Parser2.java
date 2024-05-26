package com.mycompany.lexico;

import java.util.*;

public class Parser2 {
    private List<Token> tokens;
    private List<Producción> producciones;
    private ParsingTable parsingTable;
    private Stack<String> pila;
    private int currentIndex;
    private Grammar grammar;

    public Parser2(List<Token> tokens, List<Producción> producciones, ParsingTable parsingTable, Grammar grammar) {
        this.tokens = tokens;
        this.producciones = producciones;
        this.parsingTable = parsingTable;
        this.pila = new Stack<>();
        this.currentIndex = 0;
        this.grammar = grammar;
    }

    private Token dameToken() {
        if (currentIndex < tokens.size()) {
            return tokens.get(currentIndex++);
        }
        return new Token(TipoToken.EOF, "", -1, -1); // siempre hay un token EOF al final de la lista
    }

    private boolean esTerminal(String elemento) {
        return grammar.getTerminals().contains(elemento);
    }

    private void errorSintactico(String esperado, Token tokenActual) 
    {
        System.err.println("\n\n|------------------------------");
        System.err.println("| Error sintactico: se esperaba '" + esperado + "' pero se encontro '" + tokenActual.getTipoString() + "' ("+ tokenActual.getLexema() +") en la linea " + tokenActual.getLinea() + ", columna " + tokenActual.getColumna());
        System.err.println("|------------------------------\n\n");
    }

    // método principal que se encarga de hacer el análisis sintáctico
    public void parse() {
        pila.push("EOF");
        pila.push("<S>");
        Token tokenActual = dameToken();
        
        while (!pila.isEmpty()) 
        {
            System.out.print("---------------");
            // si encuentra un ; se da por terminado la revisión de ese conjunto de tokens y empiza a bucar reglas gramaticales nuevas
            if(tokenActual.getTipoString().equals("TERMINADOR"))
            {
                pila.clear();
                pila.push("EOF");
                pila.push("<S>");
                tokenActual = dameToken();
            }
            String elementoActual = pila.pop();
            System.out.print("\nElemento en gramatica en pila: " + elementoActual + " --- Token actual: "+ tokenActual.getTipoString() + " -> " + tokenActual.getLexema());
            if (esTerminal(elementoActual)) 
            {
                System.out.print("\nEl elemento en pila es una terminal");
                if (elementoActual.equals(tokenActual.getTipoString()) || elementoActual.equals(tokenActual.getLexema())) 
                {
                    System.out.println("\nElemento actual == Tipo Token: "+ elementoActual.equals(tokenActual.getTipoString()) + " ---- Elemento Actual == Lexema: " + elementoActual.equals(tokenActual.getLexema()));
                    tokenActual = dameToken();
                } else {
                    System.out.println("\nSi es un terminal pero no hubo match");
                    errorSintactico(elementoActual, tokenActual);
                    //return;
                }
            } else {
                
                System.out.println("\nEs un NO terminal, se hace recorre la gramática");
                List<Producción> posiblesProducciones = grammar.getProductionsForNonTerminal(elementoActual);
                boolean matchFound = false;

                for (Producción producción : posiblesProducciones) {
                    List<String> secuencia = producción.getSecuencia();
                    if (!secuencia.isEmpty()) {
                        String primerElemento = secuencia.get(0);

                        if ((esTerminal(primerElemento) && (primerElemento.equals(tokenActual.getTipoString()) || primerElemento.equals(tokenActual.getLexema())))
                            || !esTerminal(primerElemento)) 
                        {
                            matchFound = true;
                            for (int i = secuencia.size() - 1; i >= 0; i--) {
                                pila.push(secuencia.get(i));
                            }
                            break;
                        }
                    }
                }

                if (!matchFound) 
                {
                    if(tokenActual.getTipoString().equals("EOF"))
                        continue;
                    else
                    {
                        System.out.println("\nEs un no terminal, no hubo match");
                        errorSintactico(elementoActual, tokenActual);
                        return;
                    }
                    
                }
            }
        }

        if (tokenActual.getTipoString().equals("EOF")) {
            System.out.println("\n|-------------------------------------------|");
            System.out.println("| Analisis sintactico completado con exito. |");
            System.out.println("|-------------------------------------------|");
        } else {
            System.out.println("Último token: " + tokenActual.getTipoString());
            errorSintactico("EOF", tokenActual);
        }
    }
}