package com.mycompany.lexico;

import java.util.*;

public class ParserMain {
    public static void main(String[] args) {
        // Definir la gramática
        Grammar grammar = new Grammar();
        
        grammar.addNonTerminal("<S>");
        grammar.addNonTerminal("<loop-constantes>");
        grammar.addNonTerminal("<literal>");
        grammar.addTerminal("arrá");
        grammar.addTerminal("IDENTIFICADOR");
        grammar.addTerminal("LITERAL_FLOTANTE");
        grammar.addTerminal("LITERAL_BOOLEANA");
        grammar.addTerminal("LITERAL_ENTERA");
        grammar.addTerminal("COMA");
        grammar.addTerminal("TERMINADOR");
        
        grammar.addProduction("<S>", Arrays.asList("arrá", "<loop-constantes>"));
        //grammar.addProduction("<loop-constantes>", Arrays.asList("IDENTIFICADOR", "<literal>", "COMA", "<loop-constantes>"));
        grammar.addProduction("<loop-constantes>", Arrays.asList("IDENTIFICADOR", "<literal>", "TERMINADOR"));
        grammar.addProduction("<literal>", Arrays.asList("LITERAL_FLOTANTE"));
        grammar.addProduction("<literal>", Arrays.asList("LITERAL_BOOLEANA"));
        grammar.addProduction("<literal>", Arrays.asList("LITERAL_ENTERA"));
        grammar.setStartSymbol("<S>");

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

        // Supongamos que ya tenemos los tokens del Scanner
        List<Token> tokens = Arrays.asList(
            new Token(TipoToken.ASIGNACION, "arrá", 1, 1),
            new Token(TipoToken.IDENTIFICADOR, "x1", 1, 5),
            new Token(TipoToken.LITERAL_FLOTANTE, "1.5", 1, 8),
            new Token(TipoToken.TERMINADOR, ";", 1, 11),
            new Token(TipoToken.EOF,"EOF",2,1)
        );
        
        List<Token> tokensTrue = Scanner.scan();

        // Crear el parser y ejecutar el análisis
        Parser2 parser = new Parser2(tokensTrue, grammar.getProductions(), parsingTable, grammar);
        parser.parse();
    }
}

