package views;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import domain.UserVO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.MainApp;
import util.Util;

public class MainController extends MasterController {
	@FXML
	private Button btnPrev;
	@FXML
	private Button btnNext;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblDay;
	@FXML
	private Label loginInfo;
	@FXML
	private GridPane gridCalendar;
	@FXML
	private Button jan;
	@FXML
	private Button fev;
	@FXML
	private Button mar;
	@FXML
	private Button apr;
	@FXML
	private Button may;
	@FXML
	private Button jun;
	@FXML
	private Button jul;
	@FXML
	private Button aug;
	@FXML
	private Button sep;
	@FXML
	private Button oct;
	@FXML
	private Button nov;
	@FXML
	private Button dec;

	private UserVO user;

	private List<DayController> dayList;

	private Map<String, String> dayOfWeek = new HashMap<>();

	private YearMonth currentYM; // 현재 년도와 월을 저장하는 변수

//	private boolean monthChange = true;

	int nowMonth = 0;

	public UserVO getUser() {
		return user;
	}

	public void setLoginInfo(UserVO vo) {
		this.user = vo;
		loginInfo.setText(vo.getName() + "[" + vo.getId() + "]");
	}

	public void logout() {
		user = null;
		MainApp.app.loadPage("login");
	}

	public void prevYear() {
		loadMonthData(currentYM.minusYears(1));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void nextYear() {
		loadMonthData(currentYM.plusYears(1));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	// 클릭시 해당월 색칠
	public void setMonthColor() {
		int janNum = 1;
		int febNum = 2;
		int marNum = 3;
		int aprNum = 4;
		int mayNum = 5;
		int junNum = 6;
		int julNum = 7;
		int augNum = 8;
		int sepNum = 9;
		int octNum = 10;
		int novNum = 11;
		int decNum = 12;

		if (nowMonth == janNum) {
			jan.getStyleClass().add("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == febNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().add("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == marNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().add("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == aprNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().add("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == mayNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().add("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == junNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().add("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == julNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().add("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == augNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().add("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == sepNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().add("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == octNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().add("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == novNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().add("activeMonth");
			dec.getStyleClass().remove("activeMonth");
		} else if (nowMonth == decNum) {
			jan.getStyleClass().remove("activeMonth");
			fev.getStyleClass().remove("activeMonth");
			mar.getStyleClass().remove("activeMonth");
			apr.getStyleClass().remove("activeMonth");
			may.getStyleClass().remove("activeMonth");
			jun.getStyleClass().remove("activeMonth");
			jul.getStyleClass().remove("activeMonth");
			aug.getStyleClass().remove("activeMonth");
			sep.getStyleClass().remove("activeMonth");
			oct.getStyleClass().remove("activeMonth");
			nov.getStyleClass().remove("activeMonth");
			dec.getStyleClass().add("activeMonth");
		}

	}

	public void moveMonthJan() {
		String months = jan.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthFev() {
		String months = fev.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthMar() {
		String months = mar.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthApr() {
		String months = apr.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthMay() {
		String months = may.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthJun() {
		String months = jun.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthJul() {
		String months = jul.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthAug() {
		String months = aug.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthSep() {
		String months = sep.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthOct() {
		String months = oct.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthNov() {
		String months = nov.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	public void moveMonthDec() {
		String months = dec.getText();
		String monthh[] = months.split("월");
		int month = Integer.parseInt(monthh[0]);
		loadMonthData(YearMonth.of(currentYM.getYear(), month));
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);

		for (DayController day : dayList) {
			if (day.getDate().equals(firstDay)) {
				day.setFocus();
				break;
			}
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@FXML
	private void initialize() {
		dayList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/views/DayLayout.fxml"));
				try {
					AnchorPane ap = loader.load();
					gridCalendar.add(ap, j, i);
					DayController dc = loader.getController();
					dc.setRoot(ap);
					dayList.add(dc);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.printf("j : %d, i : %d 번째 그리는 중 오류 발생\n", j, i);
					Util.showAlert("에러", "달력 초기화중 오류 발생", AlertType.ERROR);
				}
			}
		}

		String[] engDay = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		String[] korDay = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };

		for (int i = 0; i < engDay.length; i++) {
			dayOfWeek.put(engDay[i], korDay[i]);
		}

		loadMonthData(YearMonth.now());
		setToday(LocalDate.now());
	}

	public void setToday(LocalDate date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY.MMM", Locale.ENGLISH);
		lblDate.setText(date.format(dtf));
		lblDay.setText(dayOfWeek.get(date.getDayOfWeek().toString()));
	}

	public void loadMonthData(YearMonth ym) {
		nowMonth = ym.getMonthValue();
		// 해당 년월의 1일 날짜를 만들어서 가져온다.
		LocalDate calendarDate = LocalDate.of(ym.getYear(), ym.getMonthValue(), 1);
		while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
			// 일요일이 아닐때까지 하루씩 빼나아간다.
			calendarDate = calendarDate.minusDays(1); // 하루씩 감소
		}
		// 여기까지 오면 해당 주간의 첫째날로 설정되게 된다. 여기서부터 캘린더를 그린다.

		for (DayController day : dayList) {
			day.setDayLabel(calendarDate); // 현재 날짜 셋팅하고
			calendarDate = calendarDate.plusDays(1); // 하루 더해준다.
//			day.setCountLabel(0); // 처음에는 일정을 전부 없는 것으로 한다.
		}

		currentYM = ym;
	}

	public void setClickData(LocalDate date) {
//		if (monthChange) {
//			return;
//		}
		setToday(date);
		// 오늘날짜 셋팅후에 모든 날짜에서 active를 제거한다.
		for (DayController dc : dayList) {
			dc.outFocus();
		}
	}
}
