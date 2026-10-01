package ies.laguna.di.ud1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Concordancia {
    static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce el nombre del fichero: ");
        String nombreFichero = sc.nextLine();

        FileWriter crear = new FileWriter(nombreFichero, true);  // Si el fichero no existe lo creo, y
        crear.close();                                           // true es para si existe, que no borre lo que tenía


        int opcion = 0;
        BufferedReader lector;
        String linea;

        while (opcion != 4) {
            System.out.println("MENÚ PRINCIPAL");
            System.out.println("1. Añadir usuario");
            System.out.println("2. Mostrar usuarios introducidos");
            System.out.println("3. Generar fichero de concordancias");
            System.out.println("4. Salir");

            System.out.println("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();     //para limpiar el salto de línea

            switch (opcion) {

                case 1:
                    System.out.println("Opcion 1: Añadir usuario");

                    System.out.println("Introduce el código de usuario: ");
                    String codigo = sc.nextLine();

                    System.out.println("Introduce las aficiones:(Separadas por un espacio) ");
                    String aficionesLinea = sc.nextLine();

                    if (aficionesLinea.isEmpty()) {
                        System.out.println("Error, tienes que tener al menos una afición");
                        break;
                    }

                    lector = new BufferedReader(new FileReader(nombreFichero));

                    linea = lector.readLine();
                    boolean usuarioEncontrado = false;

                    while (linea != null) {
                        String[] datos = linea.split(" ");

                        if (codigo.equals(datos[0])) {
                            usuarioEncontrado = true;
                            break;
                        }
                    }

                    lector.close();

                    if (usuarioEncontrado) {
                        System.out.println("ERROR: El usuario ya existe");
                    } else {
                        FileWriter persona = new FileWriter(nombreFichero, true);

                        persona.append(codigo + " " + aficionesLinea + "\n");
                        persona.close();

                        System.out.println("Usuario añadido correctamente");
                    }

                    break;

                case 2:
                    System.out.println("Opcion 2: Mostrar usuarios");

                    lector = new BufferedReader(new FileReader(nombreFichero));

                    linea = lector.readLine();

                    while (linea != null) {
                        System.out.println(linea);

                        linea = lector.readLine();
                    }

                    lector.close();

                    break;

                case 3:
                    System.out.println("Opción 3: Generar fichero de concordancias");

                    System.out.println("Introduce el número mínimo de concordancias:");
                    int minimo = sc.nextInt();
                    sc.nextLine();

                    if (minimo < 1) {
                        System.out.println("El número debe ser mayor o igual que 1.");
                        break;
                    }

                    lector = new BufferedReader(new FileReader(nombreFichero));

                    int contador = 0;
                    linea = lector.readLine();

                    while (linea != null) {
                        contador++;
                        linea = lector.readLine();
                    }

                    lector.close();

                    String[] usuarios = new String[contador];

                    lector = new BufferedReader(new FileReader(nombreFichero));

                    linea = lector.readLine();
                    int posicion = 0;

                    while (linea != null) {
                        usuarios[posicion] = linea;
                        posicion++;
                        linea = lector.readLine();
                    }

                    lector.close();

                    for (int i = 0; i < usuarios.length; i++) {
                        for (int j = i + 1; j < usuarios.length; j++) {

                            String[] usuario1 = usuarios[i].split(" ");
                            String[] usuario2 = usuarios[j].split(" ");

                            int comunes = 0;
                            String aficionesComunes = "";

                            for (int x = 1; x < usuario1.length; x++) {
                                for (int y = 1; y < usuario2.length; y++) {

                                    if (usuario1[x].equals(usuario2[y])) {

                                        if (comunes == 0) {
                                            aficionesComunes = usuario1[x];
                                        } else {
                                            aficionesComunes = aficionesComunes + " " + usuario1[x];
                                        }

                                        comunes++;
                                    }
                                }
                            }

                            if (comunes >= minimo) {
                                System.out.println(usuario1[0] + " " + usuario2[0] + " " + aficionesComunes);
                            }
                        }
                    }

                    break;

                case 4:
                    System.out.println("Salir");
                    break;

                default:
                    System.out.println("Opcion inválida");
            }
        }

        sc.close();
    }
}