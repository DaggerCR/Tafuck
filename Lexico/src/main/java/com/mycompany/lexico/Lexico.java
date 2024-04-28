
package com.mycompany.lexico;

/**
 *
 * @author daniel
 */
public class Lexico {

    public static void main(String[] args) {
         for (int i = 0; i <= 100; i++) {
            System.out.print("Procesando: " + i + "%\r");
            try {
                // Simula algún proceso que lleva tiempo
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("""
                           Proceso completado!
                           Pero el Scanner esta en el scanner.java
                           Ejecuta ese!""");
    }
}

