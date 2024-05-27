
package com.mycompany.lexico;

/**
 *
 * @author kpala
 * Campo de la tabla de simbolos con los metadatos necesarios para el análisis
 */
public class SymbolInfo {
    String dataType;
    String scope;
    String additionalInfo;
    int line;

    @Override
    public String toString() {
        return "SymbolInfo{" + "dataType=" + dataType + ", scope=" + scope + ", additionalInfo=" + additionalInfo + ", line=" + line + '}';
    }

    public SymbolInfo() {
    }

    public SymbolInfo(String dataType, String scope, String additionalInfo, int line) {
        this.dataType = dataType;
        this.scope = scope;
        this.additionalInfo = additionalInfo;
        this.line = line;
    }

    public String getDataType() {
        return dataType;
    }

    public String getScope() {
        return scope;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public int getLine() {
        return line;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setLine(int line) {
        this.line = line;
    }
    
    
}
