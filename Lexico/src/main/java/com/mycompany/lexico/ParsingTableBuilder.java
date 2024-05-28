package com.mycompany.lexico;


import java.util.*;

public class ParsingTableBuilder {
    private Grammar grammar;
    private PredictCalculator predictCalculator;
    private ParsingTable parsingTable;

    public ParsingTableBuilder(Grammar grammar, PredictCalculator predictCalculator) {
        this.grammar = grammar;
        this.predictCalculator = predictCalculator;
        this.parsingTable = new ParsingTable();
        this.parsingTable.initialize(grammar.getNonTerminals(), grammar.getTerminals());
        buildParsingTable();
    }

    private void buildParsingTable() {
        for (String family : grammar.productionFamilies.keySet()) {
            List<Producción> productions = grammar.getProductionsForFamily(family);
            for (Producción production : productions) {
                Set<String> predictSet = predictCalculator.getPredict(production);
                for (String terminal : predictSet) {
                    parsingTable.addEntry(production.getNoTerminal(), terminal, production);
                }
            }
        }
    }

    public ParsingTable getParsingTable() {
        return parsingTable;
    }
}
