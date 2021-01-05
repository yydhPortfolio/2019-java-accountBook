package views;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import domain.AccountVO;
import domain.UserVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.MainApp;
import util.JDBCUtil;
import util.Util;

public class AccountController extends MasterController {
	@FXML
	private TextField accountTitle;
	@FXML
	private TextArea accountContent;
	@FXML
	private TextArea txtCost;
	@FXML
	private TextArea txtUsed;
	@FXML
	private TextArea txtInfo;
	@FXML
	private ListView<AccountVO> AccountList;

	private LocalDate date;

	private ObservableList<AccountVO> list; // 옵저베이블 리스트

	@Override
	public void init() {
		txtUsed.setText("");
		txtInfo.setText("");
		txtCost.setText("");
	}

	@FXML
	public void initialize() {
		list = FXCollections.observableArrayList();
		AccountList.setItems(list);
	}

	public void setDate(LocalDate date) {
		this.date = date;
		UserVO user = MainApp.app.getLoginUser();

		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		list.clear(); // 기존에 있던 일정들을 모두 비워준다.
		String sql = "SELECT * FROM account_costs WHERE `date` = ? AND owner = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, Date.valueOf(date));
			pstmt.setString(2, user.getId());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AccountVO todo = new AccountVO();
				todo.setId(rs.getInt("id"));
				todo.setCosts(rs.getString("costs"));
				todo.setPlace(rs.getString("place"));
				todo.setContent(rs.getString("content"));
				todo.setDate(rs.getDate("date").toLocalDate()); // sql date를 LocalDate로 변환시
				todo.setOwner(rs.getString("owner"));
				list.add(todo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Util.showAlert("에러", "데이터베이스 접속중 오류 발생", AlertType.ERROR);
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}

	// 일정 신규 등록 매서드
	public void register() {
		String used = txtUsed.getText().trim();
		String cost = txtCost.getText().trim();
		String info = txtInfo.getText().trim();
		UserVO user = MainApp.app.getLoginUser();

		if (used.isEmpty() || cost.isEmpty()) {
			Util.showAlert("필수항목 비어있음", "지출 금액이나 사용처 비어있을 수 없습니다", AlertType.INFORMATION);
			return;
		}

		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO account_costs(`costs`, `place`, `content`, `date`, `owner`) "
				+ " VALUES(?, ?, ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cost);
			pstmt.setString(2, used);
			pstmt.setString(3, info);
			pstmt.setDate(4, Date.valueOf(date)); // LocalDate객체를 SQL 삽입시 valueOf를 사용
			pstmt.setString(5, user.getId());

			int result = pstmt.executeUpdate();
			if (result != 1) {
				Util.showAlert("에러", "데이터베이스에 정상적으로 입력되지 않았습니다.", AlertType.INFORMATION);
				return;
			}
			Util.showAlert("성공", "데이터베이스에 정상적으로 입력되었습니다.", AlertType.INFORMATION);

			MainApp.app.slideOut(getRoot());

		} catch (Exception e) {
			e.printStackTrace();
			Util.showAlert("에러", "데이터베이스 연결중 오류 발생", AlertType.ERROR);
		} finally {
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}

	// 일정 수정 매서드
	public void update() {
		String title = accountTitle.getText();
		String contents = accountContent.getText();
		String content = contents.substring(contents.lastIndexOf(":") + 1);
		UserVO user = MainApp.app.getLoginUser();

		if (title.isEmpty() || content.isEmpty()) {
			Util.showAlert("필수항목 비어있음", "필수 입력항목이 비어있습니다.", AlertType.INFORMATION);
			return;
		}
		int idx = AccountList.getSelectionModel().getSelectedIndex();
		if (idx < 0) {
			Util.showAlert("알림", "선택된 일정이 없습니다.", AlertType.INFORMATION);
			return;
		}

		AccountVO todo = list.get(idx);

		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE account_costs SET costs = ?, content = ? WHERE id =?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, todo.getId());
			int result = pstmt.executeUpdate();

			if (result != 1) {
				Util.showAlert("에러", "데이터베이스 처리중 오류 발생", AlertType.ERROR);
				return;
			}
			Util.showAlert("성공", "수정이 성공적으로 이루어졌습니다", AlertType.INFORMATION);
			MainApp.app.slideOut(getRoot());
		} catch (Exception e) {
			e.printStackTrace();
			Util.showAlert("에러", "데이터베이스 처리중 오류 발생", AlertType.ERROR);
		} finally {
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}

	// 일정창 닫기
	public void close() {
		MainApp.app.slideOut(getRoot());
	}

	// 일정 제목 클릭시 내용이 출력되도록 하는 매서드
	public void loadTodo() {
		int idx = AccountList.getSelectionModel().getSelectedIndex();
		if (idx < 0) {
			return;
		}

		AccountVO vo = list.get(idx);
		accountTitle.setText(vo.getCosts());
		accountContent.setText(vo.getPlace());
	}
}
