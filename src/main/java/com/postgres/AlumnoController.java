package com.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AlumnoController {
    public void controlAlumnos(Connection connection, Scanner teclado) {
        int action;
        do {
            System.out.println("\t\t1.-Fullname de todos los alumnos\n\t\t2.-Nuevo alumno\n\t\t0.-Salir");
            action = Integer.parseInt(teclado.nextLine());
            switch (action) {
                case 1:
                    try (ResultSet rs = connection.createStatement().executeQuery("SELECT a.fullname FROM alumno a;")) {
                        while (rs.next()) {
                            System.out.println(rs.getString(1));
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try (PreparedStatement statement = connection
                            .prepareStatement("INSERT INTO alumno VALUES (?, ?, ?, ROW(?, ?, ?), ?)")) {
                        statement.setInt(1, Integer.parseInt(teclado.nextLine()));
                        statement.setString(2, teclado.nextLine());
                        statement.setString(3, teclado.nextLine());
                        statement.setInt(4, Integer.parseInt(teclado.nextLine()));
                        statement.setString(5, teclado.nextLine());
                        statement.setString(6, teclado.nextLine());
                        statement.setInt(7, Integer.parseInt(teclado.nextLine()));
                        statement.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try (PreparedStatement prepareStatement = connection.prepareStatement("SELECT searchperson(?);")) {
                        prepareStatement.setInt(1, Integer.parseInt(teclado.nextLine()));
                        ResultSet rs = prepareStatement.executeQuery();
                        while (rs.next()) {
                            System.out.println(rs.getObject(1));
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    break;
            }
        } while (action != App.EXIT);
    }
}
