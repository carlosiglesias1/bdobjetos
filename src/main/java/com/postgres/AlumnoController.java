package com.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.postgresql.util.PGobject;

public class AlumnoController {
    private void fullName(Connection connection) {
        try (ResultSet rs = connection.createStatement().executeQuery("SELECT a.fullname FROM alumno a;")) {
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertData(Connection connection, Scanner teclado) {
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
    }

    private void searchperson(Connection connection, Scanner teclado) {
        try (PreparedStatement prepareStatement = connection.prepareStatement("SELECT searchperson(?);")) {
            prepareStatement.setInt(1, Integer.parseInt(teclado.nextLine()));
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                System.out.println(((PGobject) rs.getObject(1)).getValue());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verAlumnos(Connection connection) {
        try (ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM alumno")) {
            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteAlumno(Connection connection, Scanner teclado) {
        try (PreparedStatement prepareStatement = connection.prepareStatement("SELECT delete_person(?);")) {
            prepareStatement.setInt(1, Integer.parseInt(teclado.nextLine()));
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getObject(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void controlAlumnos(Connection connection, Scanner teclado) {
        int action;
        do {
            System.out.println(
                    "\t\t1.-Fullname de todos los alumnos\n\t\t2.-Nuevo alumno\n\t\t3.-Buscar persona por el id"
                            + "\n\t\t4.-Borrar alumno por el id\n\t\t0.-Salir");
            action = Integer.parseInt(teclado.nextLine());
            switch (action) {
                case 1:
                    fullName(connection);
                    break;
                case 2:
                    insertData(connection, teclado);
                    break;
                case 3:
                    deleteAlumno(connection, teclado);
                    break;
                case 4:
                    searchperson(connection, teclado);
                    break;
                default:
                    verAlumnos(connection);
                    break;
            }
        } while (action != App.EXIT);
    }
}
