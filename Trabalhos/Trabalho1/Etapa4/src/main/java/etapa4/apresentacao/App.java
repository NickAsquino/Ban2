package etapa4.apresentacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import etapa4.gerenciamento.gravadora;

public class App 
{
    private static gravadora gravadora;
    public static void main( String[] args ) throws SQLException
    {
        Connection conexao = null;

        try {
            //conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BAN2-Trabalho-nulo", "postgres", "nick");
            conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BAN2-Trabalho", "postgres", "nick");
            if (conexao != null) {
                System.out.println("Conectado ao banco de dados!");
                gravadora = new gravadora(conexao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new TelaPrincipal(gravadora).setVisible(true));
    }
}