datos segment
    num1 db ?
datos ends

pila segment stack 'stack'
    dw 256 dup (?) ; Definición de la pila
pila ends

codigo segment
    assume cs:codigo, ds:datos, ss:pila

inicio:
    mov ax, ds    ; ponemos al ES a apuntar al inicio del PSP
    mov es, ax

    mov ax, datos  ; El DS que apuntaba al PSP se debe poner a apuntar al segmento de datos para 
    mov ds, ax     ; poder usar las variables.

    mov ax, pila
    mov ss, ax

    mov al, byte ptr [es:82h]; entrada
    sub al, 30h              ; Convertir de ASCII
    mov num1, al             ; Asignar a num1

    cmp num1, 0              ; Comparar con 0
    jz imprimir_cero         ; Si es 0, imprimir 0 (false)
    mov dl, '1'              ; Si no es 0, imprimir 1 (true)
    jmp imprimir_caracter

imprimir_cero:
    mov dl, '0'              ;imprime el 0

imprimir_caracter:
    mov ah, 02h    ; Función para imprimir un caracter en la salida estándar
    int 21h       

    mov ax, 4C00h  
    int 21h        

codigo ends
end inicio

