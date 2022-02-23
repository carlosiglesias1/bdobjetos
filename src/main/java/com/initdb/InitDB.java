package com.initdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDB {
    private InitDB() {
        super();
    }

    public static void createDatabase(Connection connection) {
        try (Statement st = connection.createStatement()) {
            st.addBatch("CREATE TYPE address_type AS (id INTEGER, city TEXT, country TEXT);");
            st.addBatch(
                    "CREATE TYPE person_type AS (id INTEGER, firstname TEXT, lastname TEXT, address address_type);");
            st.addBatch("CREATE TABLE person OF person_type");
            st.addBatch("CREATE TABLE alumno (nExpediente INTEGER) inherits (person);");
            st.addBatch("CREATE TABLE profesor (nSegSoc INTEGER) inherits (person);");
            st.addBatch(
                    "CREATE OR REPLACE FUNCTION fullname (p_row person) RETURNS TEXT AS $$ SELECT concat_ws(' ', p_row.firstname, p_row.lastname);$$ LANGUAGE SQL;");
            st.addBatch(
                    "CREATE OR REPLACE FUNCTION max_id (p_row person) RETURNS INTEGER LANGUAGE SQL AS $$ SELECT max(p_row.id) $$;");
            st.addBatch(
                    "CREATE OR REPLACE FUNCTION public.searchperson(search_id INTEGER) RETURNS person_type LANGUAGE sql AS $function$ SELECT * FROM person WHERE person.id = search_id $function$");
            st.addBatch(
                    "CREATE OR REPLACE FUNCTION public.delete_person(person_id integer) RETURNS person_type LANGUAGE sql AS $function$ DELETE FROM person WHERE person.id = person_id RETURNING * $function$");
            st.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void dropDatabase(Connection connection) {
        try (Statement st = connection.createStatement()) {
            st.addBatch("DROP TABLE IF EXISTS person OF person_type");
            st.addBatch("DROP TABLE IF EXISTS alumno (nExpediente INTEGER) inherits (person);");
            st.addBatch("DROP TABLE IF EXISTS profesor (nSegSoc INTEGER) inherits (person);");
            st.addBatch("DROP TYPE IF EXISTS address_type AS (id INTEGER, city TEXT, country TEXT);");
            st.addBatch(
                    "DROP TYPE IF EXISTS person_type AS (id INTEGER, firstname TEXT, lastname TEXT, address address_type);");
            st.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
