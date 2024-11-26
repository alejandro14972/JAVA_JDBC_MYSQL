package conexion2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conect {

	public static void main(String[] args) {
		
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			cn = DriverManager.getConnection("jdbc:mysql://localhost/test2", "root", "root");
			
			cn.setAutoCommit(false);
			insertar(cn);
			consultabd(cn, rs,st);
			
			actualizar(cn);
			consultabd(cn, rs,st);
			
			borrar(cn);
			consultabd(cn, rs,st);
			
			subiredad(cn);
			consultabd(cn, rs,st);
			
			//si dejo esta llamada aqui lo borra en java, pero no en el mysql
			//borrar(cn);
			//consultabd(cn, rs,st);
			
			cn.commit();
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void subiredad(Connection cn) throws SQLException {
		System.out.println("Subir edad: ");
		cn.setAutoCommit(false);
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM persona", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		 
		 while (rs.next()) {
			rs.updateInt(5, rs.getInt(5)+2);
			rs.updateRow();
		}
		
	}

	private static void borrar(Connection cn) throws SQLException {
		System.out.println("Borrando registro");
		String sql = "Delete from persona WHERE idpersona = 2";
		PreparedStatement pst = cn.prepareStatement(sql);
		pst.executeUpdate();
	}

	private static void actualizar(Connection cn) throws SQLException {
		System.out.println("Actualizar registro");
		String sql = "UPDATE persona SET edad = ? WHERE idpersona = ?;";

		PreparedStatement pst = cn.prepareStatement(sql);
		pst.setInt(1, 27);
		pst.setInt(2, 2);
		
		pst.executeUpdate();
		
	}

	private static void insertar(Connection cn) throws SQLException {
		System.out.println("Insertando registro");
		String sql = "insert into persona (idpersona, nombre, apellido, telefono, edad) values (?,?,?,?,?)";
		PreparedStatement pst = cn.prepareStatement(sql);
		pst.setInt(1, 2);
		pst.setString(2, "Alejandro");
		pst.setString(3, "González");
		pst.setString(4, "654356876");
		pst.setInt(5, 56);
		pst.executeUpdate();
	}

	private static void consultabd(Connection cn, ResultSet rs, Statement st) throws SQLException {
	    System.out.println("CONSULTA DE REGISTROS:");
	    System.out.println("===========================================================================");
	    System.out.printf("%-10s %-20s %-20s %-15s %-5s%n", "ID", "Nombre", "Apellido", "Teléfono", "Edad");
	    System.out.println("---------------------------------------------------------------------------");

	    st = cn.createStatement();
	    String sql = "SELECT idpersona, nombre, apellido, telefono, edad FROM persona";
	    rs = st.executeQuery(sql);

	    while (rs.next()) {
	        String id = rs.getString(1);
	        String nombre = rs.getString(2);
	        String apellido = rs.getString(3);
	        String telefono = rs.getString(4);
	        int edad = rs.getInt(5);
	        
	        // Formateo de los datos en columnas
	        System.out.printf("%-10s %-20s %-20s %-15s %-5d%n", id, nombre, apellido, telefono, edad);
	    }

	    System.out.println("===========================================================================");
	}


}
