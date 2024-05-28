package com.mycompany.lexico;

import java.util.*;

public class ParserMain {
    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        grammar.setStartSymbol("<S>");
        
        grammar.addNonTerminal("<S>");
        grammar.addNonTerminal("<loop-constantes>");
        grammar.addNonTerminal("<literal>");         
        grammar.addTerminal("octará");
        grammar.addTerminal("arrá");
        grammar.addTerminal("IDENTIFICADOR");
        grammar.addTerminal("LITERAL_FLOTANTE");
        grammar.addTerminal("LITERAL_BOOLEANA");
        grammar.addTerminal("LITERAL_ENTERO");
        grammar.addTerminal("COMA");
        grammar.addTerminal("TERMINADOR");
        grammar.addTerminal("TIPO_DATO");
        
        grammar.addNonTerminal("<loop-definicion>");
        grammar.addTerminal("aíca");
        grammar.addTerminal("putú");
        
        grammar.addTerminal("tilhtilh");
        grammar.addTerminal("nicó");
        grammar.addNonTerminal("<seccion-variables>");
        
        grammar.addTerminal("nhequéquequé");

        grammar.addTerminal(":= ");
        grammar.addTerminal("+= ");
        grammar.addTerminal("/= ");
        grammar.addTerminal("*= ");
        grammar.addTerminal("-= ");
        grammar.addTerminal("ASIGNACION");
        
        grammar.addTerminal("% ");
        grammar.addTerminal("+ ");
        grammar.addTerminal("/ ");
        grammar.addTerminal("* ");
        grammar.addTerminal("- ");
        grammar.addTerminal("OPERADOR_ENTERO");
        
        grammar.addTerminal("EOF");
        
        // Agregar producciones por familias
        grammar.addProduction("programa", "<S>", Arrays.asList("nhequéquequé", "IDENTIFICADOR", "TERMINADOR"));
        
        grammar.addProduction("constantes", "<S>", Arrays.asList("octará", "arrá", "<loop-constantes>"));
        grammar.addProduction("constantes", "<loop-constantes>", Arrays.asList("IDENTIFICADOR", "<literal>", "TERMINADOR"));
        grammar.addProduction("constantes", "<literal>", Arrays.asList("LITERAL_FLOTANTE"));
        grammar.addProduction("constantes", "<literal>", Arrays.asList("LITERAL_BOOLEANA"));
        grammar.addProduction("constantes", "<literal>", Arrays.asList("LITERAL_ENTERO"));

        grammar.addProduction("tipos", "<S>", Arrays.asList("aíca", "<loop-definicion>"));
        grammar.addProduction("tipos", "<loop-definicion>", Arrays.asList("putú", "IDENTIFICADOR", "TIPO_DATO", "TERMINADOR"));

        grammar.addProduction("variables", "<S>", Arrays.asList("tilhtilh", "<seccion-variables>"));
        grammar.addProduction("variables", "<seccion-variables>", Arrays.asList("TIPO_DATO", "IDENTIFICADOR", "nicó", "<literal>", "TERMINADOR"));

        grammar.addProduction("asignaciones", "<S>", Arrays.asList("IDENTIFICADOR", "ASIGNACION", "<literal>"));

        grammar.addProduction("operadores", "<S>", Arrays.asList("IDENTIFICADOR", "OPERADOR_ENTERO", "IDENTIFICADOR", "TERMINADOR"));
        
        grammar.addProduction("eof", "<S>", Arrays.asList("EOF"));

        //----------------------------------------------------------
        // Calcular First y Follow
        FirstFollow firstFollowCalculator = new FirstFollow(grammar);
        firstFollowCalculator.calculateFirst();
        firstFollowCalculator.calculateFollow();

        // Calcular Predict
        PredictCalculator predictCalculator = new PredictCalculator(grammar, firstFollowCalculator);
        predictCalculator.calculatePredict();

        // Construir la tabla de parsing
        ParsingTableBuilder parsingTableBuilder = new ParsingTableBuilder(grammar, predictCalculator);
        ParsingTable parsingTable = parsingTableBuilder.getParsingTable();

        // Obtiene los tokens del Scanner
        List<Token> tokensTrue = Scanner.scan();

        // Crear el parser y ejecutar el análisis sintáctico
        Parser2 parser = new Parser2(tokensTrue, parsingTable, grammar);
        parser.parse();
    }
}

