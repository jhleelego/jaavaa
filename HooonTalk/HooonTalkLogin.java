package HooonTalk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.util.DBConnectionMgr;

public class HooonTalkLogin {
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//메인사용하기
	HooonTalkView htView = null;	
	
	//개인정보수집
	String sId = "";
	String sPw = "";
	
	//이름 숫자체크, 공백체크
	char temp;
	
	//ID중복체크
	int returnValue = 0;
	
	public HooonTalkLogin(HooonTalkView htView) {
		this.htView = htView;
		loginCheck();
	}
	
	public void loginCheck() {
		htView.privacy = new String[5];
		htView.privacy[0] = htView.jtf_id.getText();
		htView.privacy[1] = htView.jtf_pw.getText();  
		
		loginIdPwCheck();
	}
	
	public void loginIdPwCheck() {
			String mem_id = "";
			String mem_name = "";
			String status = "";
			StringBuilder sql1 = new StringBuilder("");
			StringBuilder sql2 = new StringBuilder("");
			
			try {
				sql1.append(" SELECT NVL((SELECT 1" );
				sql1.append("   FROM htuser"       );
				sql1.append("  WHERE u_id=?)"     );
				sql1.append("        ,-1)status "   );
				sql1.append("   FROM dual"          );
				
				con = dbMgr.getConnection();
				pstmt = con.prepareStatement(sql1.toString());
				pstmt.setString(1, htView.privacy[0]);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					status = rs.getString("status");
					if(status.equals("1")) {
						sql2.append("SELECT u_id FROM htuser WHERE u_id = ? AND u_pw = ?");
						pstmt = con.prepareStatement(sql2.toString());
						pstmt.setString(1, htView.privacy[0]);
						pstmt.setString(2, htView.privacy[1]);
						rs = pstmt.executeQuery();
						if(rs.next()) {
							JOptionPane.showMessageDialog(null,  "로그인 성공!!!", "Message", JOptionPane.ERROR_MESSAGE);
							htView.changePlayForm();
							htView.jl_play_studentId.setText(htView.privacy[0] + "님 환영합니다.");							
						}else { 
							JOptionPane.showMessageDialog(null,  "비밀번호를 잘못 입력하셨습니다.", "Message", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
					}else {
						JOptionPane.showMessageDialog(null,  "아이디가 없거나 잘못 입력하셨습니다.", "Message", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}	
					
			} catch (SQLException e) {
				System.out.println(e.toString());
			} finally {
		}
	}

}
