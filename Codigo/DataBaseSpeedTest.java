package com.mycompany.testvelocidad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import redis.clients.jedis.Jedis;

import com.datastax.oss.driver.api.core.CqlSession;

public class DatabaseSpeedTest {
    public static void main(String[] args) {
        System.out.println("-----------------------------");
        testMariaDB();
        testMongoDB();
        testRedis();
        testCassandra();
        System.out.println("-----------------------------");
    }

    private static void testMariaDB() {
        String url = "jdbc:mariadb://localhost:3306/mydatabase";
        String user = "admin";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            long startTime = System.currentTimeMillis();

            // Realizar operaciones en la base de datos
            Statement statement = connection.createStatement();
            // Ejemplo: Ejecutar una consulta simple
            statement.executeQuery("SELECT * FROM mytable");

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Velocidad de MariaDB: " + elapsedTime + " ms");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void testMongoDB() {
        String uri = "mongodb://admin:password@localhost:27017/mydatabase";
        String databaseName = "mydatabase";

        try (MongoClient mongoClient = new MongoClient(new MongoClientURI(uri))) {
            long startTime = System.currentTimeMillis();
           
            // Realizar operaciones en la base de datos
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            // Ejemplo: Contar el número de documentos en una colección
            long count = database.getCollection("collection").countDocuments();

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Velocidad de MongoDB: " + elapsedTime + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testRedis() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("password");

        long startTime = System.currentTimeMillis();

        // Realizar operaciones en la base de datos
        // Ejemplo: Realizar una operación de escritura
        jedis.set("clave", "valor");

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Velocidad de Redis: " + elapsedTime + " ms");

        jedis.close();
    }

    private static void testCassandra() {
        try (CqlSession session = CqlSession.builder().build()) {
            long startTime = System.currentTimeMillis();

            // Realizar operaciones en la base de datos
            // Ejemplo: Ejecutar una consulta simple
            session.execute("SELECT * FROM mydatabase.tabla");

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Velocidad de Cassandra: " + elapsedTime + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}