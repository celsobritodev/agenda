package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {

	/** The Constant DRIVER. */
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	/** The Constant URL. */
	public static final String URL = "jdbc:mysql://localhost:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	
	/** The Constant USER_LOGIN. */
	public static final String USER_LOGIN = "root";
	
	/** The Constant USER_PASSWD. */
	public static final String USER_PASSWD = "admin";

	/** The con. */
	Connection con;

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public static Connection getConnection() {

		Connection con = null;
		try {
			Class.forName(DRIVER); // força o carregamento do driver
			con = DriverManager.getConnection(URL, USER_LOGIN, USER_PASSWD);
			System.out.println("Conectado com sucesso!");
		} catch (SQLException e) {

			System.out.println("Não pode conectar:" + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Driver não encontrado!");

		}
		return con;
	}

	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome,fone,email) values (?,?,?)";
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.executeUpdate();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> listarContatos() {
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "SELECT * FROM CONTATOS ORDER BY NOME";
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				contatos.add(new JavaBeans(idcon, nome, fone, email));

			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
	
	
	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	public void selecionarContato(JavaBeans contato) {
		String read2 = "SELECT * FROM CONTATOS WHERE IDCON = ?";
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
				
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	public void alterarContato(JavaBeans contato) {
		String update = "update contatos set nome=?,fone=?,email=? where idcon=?";
        try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}	
	}
	
	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	public void deletarContato(JavaBeans contato) {
		String delete = "DELETE FROM CONTATOS WHERE IDCON=?";
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(delete);
            pst.setString(1,contato.getIdcon());
            pst.executeUpdate();
            con.close();
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
	}

}
