/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lexico;

/**
 *
 * @author kpala
 * Clase para guardar la llave de la tabla de presedencia
 */
import java.util.Objects;

public class ScopeIdentifier {
    private String scope;
    private String identifier;

    public ScopeIdentifier(String scope, String identifier) {
        this.scope = scope;
        this.identifier = identifier;
    }

    public String getScope() {
        return scope;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScopeIdentifier that = (ScopeIdentifier) o;
        return scope.equals(that.scope) && identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scope, identifier);
    }

    @Override
    public String toString() {
        return "Scope: " + scope + ", Identifier: " + identifier;
    }
}
