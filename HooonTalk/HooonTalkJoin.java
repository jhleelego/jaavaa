package HooonTalk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import com.util.DBConnectionMgr;

public class HooonTalkJoin{
	//sql컨넥션
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//메인사용하기
	HooonTalkView htView = null;	
	HooonTalkEvent htEvent = null;
	//개인정보수집
	String sId = "";
	String sPw = "";
	String sName = "";
	String sAge = "";
	String sEmail = "";
	String[] privacy = null;
	//이름 숫자체크, 공백체크
	char temp;
	//ID중복체크
	int returnValue = 0;
	
	boolean checkResult = false;
	
	public HooonTalkJoin(HooonTalkView htView, HooonTalkEvent htEvent) {
		this.htView = htView;
		this.htEvent = htEvent;
		joinCheck();
	}
	public void joinCheck() {
		privacy = new String[5];
		privacy[0] = htView.jtf_join_studentId.getText();
		privacy[1] = htView.jtf_join_studentPw.getText();
		privacy[2] = htView.jtf_join_studentName.getText();
		privacy[3] = htView.jtf_join_studentAge.getText();
		privacy[4] = htView.jtf_join_studentEmail.getText();
		blankCheck();
	}
	public void blankCheck() {
		if(privacy[0].length()<6||privacy[0].length()>12) {
			JOptionPane.showMessageDialog(null, "아이디는 6자이상 12자 이하여야 합니다.");
			return;
		} else if(privacy[1].length()<8||privacy[1].length()>16) {
			JOptionPane.showMessageDialog(null, "비밀번호는 6자이상 12자 이하여야 합나다.");
			return;
		}		
		for(int i=2; i<privacy.length; i++) {
			System.out.println(privacy[i]);
			privacy[i]=privacy[i].replaceAll(" ", "");
			if(privacy[i].equals("")) {
				if(i==2) {
					JOptionPane.showMessageDialog(null,"이름에 공백으로 두실 수 없습니다.");
					return;
				} else if(i==3) {
					JOptionPane.showMessageDialog(null,"나이에 공백으로 두실 수 없습니다.");
					return;
				} else if(i==4) {
					JOptionPane.showMessageDialog(null,"이메일에 공백으로 두실 수 없습니다.");
					return;
				}					
			}
		}
		for(int i=0; i<privacy[2].length();i++) {
			temp = privacy[2].charAt(i);
			if(!('ㄱ' <= temp && temp<='힇')) {
				JOptionPane.showMessageDialog(null, "이름에는 한글만 입력하여 주세요.");
				return;
			}
		}
		for(int i=0; i<privacy[3].length();i++) {
			temp = privacy[3].charAt(i);
			if(!('0' <= temp && temp <= '9')) {
				JOptionPane.showMessageDialog(null, "나이는 숫자만 입력하여주세요!");
				return;
			}
		}
		idCheck();
	}
	public void idCheck() {
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT Count(u_id) as cnt FROM htuser WHERE u_id =? ");
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, privacy[0]);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				returnValue = rs.getInt("cnt");					
			}
			if(returnValue==1) {
				JOptionPane.showMessageDialog(null, "중복 아이디입니다. 다른 아이디를 입력해주세요.");
				return;
			}		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SQL문에러 - HooonTalkJoin의 idCheck메소드]");
			
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}		
		insertStudent();
	}
	public void insertStudent()  {
		StringBuilder sb = new StringBuilder("");
		sb.append( "INSERT INTO htuser(u_id, u_pw, u_name, u_age, u_email) VALUES( ?, ?, ?, ?, ?)" );
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			int i = 0;
			pstmt.setString(++i, privacy[i-1]);
			pstmt.setString(++i, privacy[i-1]);
			pstmt.setString(++i, privacy[i-1]);
			pstmt.setInt(++i, Integer.parseInt(privacy[i-1]));
			pstmt.setString(++i, privacy[i-1]);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SQL문에러 - HooonTalkJoin의 idCheck메소드]");
		} finally {
			dbMgr.freeConnection(con, pstmt);
			JOptionPane.showMessageDialog(null, privacy[0] + "님 환영합니다.");
			htView.jtf_join_studentId.setText("");
			htView.jtf_join_studentPw.setText("");
			htView.jtf_join_studentName.setText("");
			htView.jtf_join_studentAge.setText("");
			htView.jtf_join_studentEmail.setText("");
			htView.jp_join_joinForm.setVisible(false);
			htView.jp_login_loginForm.setVisible(true);
			checkResult = true;
		}
	}
}
