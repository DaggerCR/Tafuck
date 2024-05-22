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

        buildTable();
    }

    private void buildTable() {
        Map<Producción, Set<String>> predictSets = predictCalculator.getPredictSets();
        for (Map.Entry<Producción, Set<String>> entry : predictSets.entrySet()) {
            Producción production = entry.getKey();
            Set<String> predictSet = entry.getValue();
            for (String terminal : predictSet) {
                parsingTable.addEntry(production.getNoTerminal(), terminal, grammar.getProductions().indexOf(production));
            }
        }
    }

    public ParsingTable getParsingTable() {
        return parsingTable;
    }
}
