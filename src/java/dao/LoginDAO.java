
package dao;

import controller.util.DataConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAO {

	public static boolean validate(String login, String pwd) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
                        

			ps = con.prepareStatement("SELECT UTILISATEUR.LOGIN, UTILISATEUR.PWD FROM UTILISATEUR, ROLEU WHERE UTILISATEUR.LOGIN=?  AND UTILISATEUR.PWD=? AND UTILISATEUR.ID_UTILISATEUR=ROLEU.ID_UTILISATEUR AND ROLEU.ROLE='TopAdmin'");
			ps.setString(1, login);
			ps.setString(2, pwd);


			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				//result found, means valid inputs
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
		return false;
	}

public static boolean validateAdmin(String login, String pwd) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
                        

			ps = con.prepareStatement("SELECT UTILISATEUR.LOGIN, UTILISATEUR.PWD, UTILISATEUR.ACTIVATED FROM UTILISATEUR, ROLEU WHERE UTILISATEUR.LOGIN=?  AND UTILISATEUR.PWD=?  AND UTILISATEUR.ACTIVATED='True' AND UTILISATEUR.ID_UTILISATEUR=ROLEU.ID_UTILISATEUR AND ROLEU.ROLE='Admin'");
			ps.setString(1, login);
			ps.setString(2, pwd);


			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				//result found, means valid inputs
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
		return false;
	}



public static boolean validateUser(String login, String pwd) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
                        

			ps = con.prepareStatement("SELECT UTILISATEUR.LOGIN, UTILISATEUR.PWD, UTILISATEUR.ACTIVATED FROM UTILISATEUR, ROLEU WHERE UTILISATEUR.LOGIN=?  AND UTILISATEUR.PWD=? AND UTILISATEUR.ACTIVATED='True' AND UTILISATEUR.ID_UTILISATEUR=ROLEU.ID_UTILISATEUR AND ROLEU.ROLE='User'");
			ps.setString(1, login);
			ps.setString(2, pwd);


			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				//result found, means valid inputs
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
		return false;
	}


}
