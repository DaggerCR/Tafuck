package com.mycompany.lexico;

import java.util.*;

public class ParserMain {
    public static void main(String[] args) {
        // Definir la gramática
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

        //nombre del programa
        grammar.addProduction("<S>", Arrays.asList("nhequéquequé","IDENTIFICADOR","TERMINADOR"));
        // Sección de constantes
        grammar.addProduction("<S>", Arrays.asList("octará","arrá","<loop-constantes>"));
        //grammar.addProduction("<loop-constantes>", Arrays.asList("IDENTIFICADOR", "<literal>", "COMA", "<loop-constantes>"));
        grammar.addProduction("<loop-constantes>", Arrays.asList("IDENTIFICADOR", "<literal>", "TERMINADOR"));
        grammar.addProduction("<literal>", Arrays.asList("LITERAL_FLOTANTE"));
        grammar.addProduction("<literal>", Arrays.asList("LITERAL_BOOLEANA"));
        grammar.addProduction("<literal>", Arrays.asList("LITERAL_ENTERO"));
        
        //sección de tipos
        grammar.addProduction("<S>", Arrays.asList("aíca","<loop-definicion>"));
        grammar.addProduction("<loop-definicion>", Arrays.asList("putú","IDENTIFICADOR","TIPO_DATO","TERMINADOR"));
        

        //sección de variables
        grammar.addProduction("<S>", Arrays.asList("tilhtilh","<seccion-variables>"));
        grammar.addProduction("<seccion-variables>", Arrays.asList("TIPO_DATO","IDENTIFICADOR","nicó","<literal>","TERMINADOR"));

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

        //obtiene los tokens del Scanner
        List<Token> tokensTrue = Scanner.scan();

        // Crear el parser y ejecutar el análisis
        Parser2 parser = new Parser2(tokensTrue, grammar.getProductions(), parsingTable, grammar);
        parser.parse();

    }
}

