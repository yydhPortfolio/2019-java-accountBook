package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtil {
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("드라이버 파일을 찾을 수 없습니다.");
			return null;
		}
		// 데이터베이스명, 유저명, 비밀번호 변경
		String connectionString = "jdbc:mysql://gondr.asuscomm.com/yy_10117" + "?useUnicode=true"
				+ "&characterEncoding=utf8" + "&useSSL=false" + "&serverTimezone=Asia/Seoul";
		String userId = "yy_10117";
		String password = "@12345678";

		Connection con = null;

		try {
			con = DriverManager.getConnection(connectionString, userId, password);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB 연결중 오류 발생");
		}

		return con;
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
		}
	}

	public static void close(PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (Exception e) {
		}
	}

	public static void close(Connection co) {
		try {
			if (co != null)
				co.close();
		} catch (Exception e) {
		}
	}
}