package conexion2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Instituto {

	public static void main(String[] args) {
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			cn = DriverManager.getConnection("jdbc:mysql://localhost/test1", "root", "root");
			cn.setAutoCommit(false);
			consultabdAlumnos(cn, rs, st);
			//consultabdProfesor(cn, rs, st);
			// insertarDatosAlumnos(cn);
			// insertarDatosAlumnosTeclado(cn);
			//actualizar(cn);
			//borrarRegistro(cn);
			cn.commit();
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void borrarRegistro(Connection cn) throws SQLException {
		System.out.println("Borrando registro");
		Scanner sc = new Scanner(System.in);
		String sql = "Delete from alumno WHERE cod_alumno = ?";
		
		PreparedStatement pst = cn.prepareStatement(sql);
		System.out.println("Identificador:");
		int id = sc.nextInt();
		pst.setInt(1, id);
		pst.executeUpdate();
	}

	private static void actualizar(Connection cn) throws SQLException {
		System.out.println("Actualizar registro");
		String sql = "UPDATE alumno SET nombre_alumno= ?, apellidos_alumno= ?, fecha_nacimiento = ?, grupo = ? WHERE cod_alumno = ?;";

		Scanner sc = new Scanner(System.in);

		PreparedStatement pst = cn.prepareStatement(sql);
		System.out.println("Identificador:");
		int id = sc.nextInt();
		sc.nextLine();
		pst.setInt(5, id);

		System.out.println("Nombre:");
		String nombre = sc.nextLine();
		pst.setString(1, nombre);

		System.out.println("Apellidos:");
		String apellido = sc.nextLine();
		pst.setString(2, apellido);

		System.out.println("Fecha (yyyy-MM-dd):");
		String fecha = sc.nextLine();
		pst.setString(3, fecha);

		System.out.println("Grupo:");
		String grupo = sc.nextLine();
		pst.setString(4, grupo);

		pst.executeUpdate();

	}

	private static void insertarDatosAlumnosTeclado(Connection cn) throws SQLException {

		Scanner sc = new Scanner(System.in);

		System.out.println("Insertando registro");
		String sql = "insert into alumno (cod_alumno, nombre_alumno, apellidos_alumno, fecha_nacimiento, grupo) values (?,?,?,?,?)";
		PreparedStatement pst = cn.prepareStatement(sql);
		System.out.println("Identificador:");
		int id = sc.nextInt();
		sc.nextLine();
		pst.setInt(1, id);

		System.out.println("Nombre:");
		String nombre = sc.nextLine();
		pst.setString(2, nombre);

		System.out.println("Apellidos:");
		String apellido = sc.nextLine();
		pst.setString(3, apellido);

		System.out.println("Fecha (yyyy-MM-dd):");
		String fecha = sc.nextLine();
		pst.setString(4, fecha);

		System.out.println("Grupo:");
		String grupo = sc.nextLine();
		pst.setString(5, grupo);

		pst.executeUpdate();

	}

	private static void insertarDatosAlumnos(Connection cn) throws SQLException {
		System.out.println("Insertando registro");
		String sql = "insert into alumno (cod_alumno, nombre_alumno, apellidos_alumno, fecha_nacimiento, grupo) values (?,?,?,?,?)";
		PreparedStatement pst = cn.prepareStatement(sql);
		pst.setInt(1, 37);
		pst.setString(2, "Luis");
		pst.setString(3, "Diaz");
		pst.setDate(4, new java.sql.Date(new Date(2001, 8, 11).getTime()));
		pst.setString(5, "B");

		pst.executeUpdate();
	}

	private static void consultabdProfesor(Connection cn, ResultSet rs, Statement st) throws SQLException {

		System.out.println("CONSULTA DE REGISTROS PROFESORES:");
		System.out.println("===========================================================================");
		System.out.printf("%-10s %-20s %-20s%n", "cod", "Nombre", "Ciudad");
		System.out.println("---------------------------------------------------------------------------");

		st = cn.createStatement();
		String sql = "SELECT * FROM profesor";
		rs = st.executeQuery(sql);

		while (rs.next()) {
			String id = rs.getString(1);
			String nombre = rs.getString(2);
			String ciudad = rs.getString(3);

			System.out.printf("%-10s %-20s %-20s%n", id, nombre, ciudad);
		}
		System.out.println("===========================================================================");

	}

	private static void consultabdAlumnos(Connection cn, ResultSet rs, Statement st) throws SQLException {
		System.out.println("CONSULTA DE REGISTROS ALUMNOS:");
		System.out.println("===========================================================================");
		System.out.printf("%-10s %-20s %-20s %-15s %-5s%n", "cod", "Nombre", "Apellido", "Fecha nacimiento", "Grupo");
		System.out.println("---------------------------------------------------------------------------");

		st = cn.createStatement();
		String sql = "SELECT cod_alumno, nombre_alumno, apellidos_alumno, fecha_nacimiento, grupo FROM alumno";
		rs = st.executeQuery(sql);

		while (rs.next()) {
			String id = rs.getString(1);
			String nombre = rs.getString(2);
			String apellido = rs.getString(3);
			Date fecha = rs.getDate(4);
			String grupo = rs.getString(5);

			System.out.printf("%-10s %-20s %-20s %-15s %-5s%n", id, nombre, apellido, fecha, grupo);

		}

		System.out.println("===========================================================================");
	}

}
