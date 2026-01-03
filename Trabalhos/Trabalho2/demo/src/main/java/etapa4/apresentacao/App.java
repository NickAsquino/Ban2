package etapa4.apresentacao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import javax.swing.SwingUtilities;

import etapa4.gerenciamento.gravadora;

public class App 
{
    private static gravadora gravadora;
    // Mongo client and database for future migration
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    public static void main( String[] args )
    {
        String mongoUri = "mongodb://localhost:27017";
        try {
            mongoClient = MongoClients.create(mongoUri);
            mongoDatabase = mongoClient.getDatabase("BAN2-Trabalho2");
            System.out.println("Conectado ao MongoDB, banco: " + mongoDatabase.getName());
            gravadora = new gravadora(mongoDatabase);
        } catch (Exception e) {
            System.err.println("Falha ao conectar ao MongoDB: " + e.getMessage());
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new TelaPrincipal(gravadora).setVisible(true));
    }
}