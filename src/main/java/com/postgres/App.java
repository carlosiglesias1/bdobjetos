package com.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.init_db.InitDB;

/**
 * Hello world!
 *
 */
public class App {
    public static final int EXIT = 0;

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int action;
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql:lasttrypostgres", "postgres", "carlos");
            System.out.println("Conexión establecida");
            do {
                System.out.println("\t1.- Crea la base de datos\n\t2.-Gestión de los alumnos\n\t0.-Sale del programa");
                action = Integer.parseInt(teclado.nextLine());
                switch (action) {
                    case 1:
                        InitDB.createDatabase(conn);
                        break;
                    case 2:
                        new AlumnoController().controlAlumnos(conn, teclado);
                        break;
                    default:
                        System.out.println("Chao chao");
                        action = EXIT;
                }
            } while (action != EXIT);
            conn.close();
            teclado.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
