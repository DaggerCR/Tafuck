
package com.mycompany.lexico;
import java.util.Hashtable;

/**
 *
 * @author kpala
 * Tabla de simbolos para el compilador de Tafac
 */
public class SymbolTable {
    Hashtable<String, SymbolInfo> globalSymbolTable = new Hashtable<>();
    
    public void addSymbol(String symbolId, SymbolInfo symbolInformation) {
        globalSymbolTable.put(symbolId, symbolInformation);
    }
    
    @Override
    public String toString(){
        globalSymbolTable.forEach((k, v) -> System.out.println(k + " -> " + v));
        return "Tabla completa";
    }

    public Hashtable<String, SymbolInfo> getGlobalSymbolTable() {
        return globalSymbolTable;
    }

    public void setGlobalSymbolTable(Hashtable<String, SymbolInfo> globalSymbolTable) {
        this.globalSymbolTable = globalSymbolTable;
    }
    
    
}
