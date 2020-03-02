package Jotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConnectionMgr;

public class JottoStick {
	JottoView jottoView = null;
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	JottoStick(JottoView jView) {
		this.jottoView = jView;
		jottoView.now="Stick";
		jView.jottoStickView();
	}
	public void getStickSearch(String[] sticknums) {
		System.out.println("getStickSearch 도착");
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT EPISODE                                                                                    ");
	    sb.append("       ,TO_CHAR(EPISODE_DATE, 'YYYY-MM-DD') AS EPISODE_DATE,PRIZE_MONEY                            ");
	    sb.append("       , BALL1, BALL2, BALL3, BALL4, BALL5, BALL6, BALL7                                           ");
	    sb.append("   FROM jotto_Episode                                                                              ");
	    sb.append("  WHERE (ball1 = ? OR ball2 = ? OR ball3 = ? OR ball4 = ? OR ball5 = ? OR ball6 = ? OR ball7 = ?)  ");
	    sb.append("    AND (ball1 = ? OR ball2 = ? OR ball3 = ? OR ball4 = ? OR ball5 = ? OR ball6 = ? OR ball7 = ?)  ");
	    sb.append("    AND (ball1 = ? OR ball2 = ? OR ball3 = ? OR ball4 = ? OR ball5 = ? OR ball6 = ? OR ball7 = ?)  ");
	    sb.append("    AND (ball1 = ? OR ball2 = ? OR ball3 = ? OR ball4 = ? OR ball5 = ? OR ball6 = ? OR ball7 = ?)  ");
	    sb.append("    AND (ball1 = ? OR ball2 = ? OR ball3 = ? OR ball4 = ? OR ball5 = ? OR ball6 = ? OR ball7 = ?)  ");
	    sb.append("    AND (ball1 = ? OR ball2 = ? OR ball3 = ? OR ball4 = ? OR ball5 = ? OR ball6 = ? OR ball7 = ?)  ");
	    sb.append("    AND (ball1 = ? OR ball2 = ? OR ball3 = ? OR ball4 = ? OR ball5 = ? OR ball6 = ? OR ball7 = ?)  ");
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			int k=1;
			for(int i=0; i<7; i++) {	
				for(int j=0; j<7; j++) {
					pstmt.setString(k++, sticknums[i]);
				}
			}
			rs = pstmt.executeQuery();
			String result = null;
			jottoView.jl_stick_north_tf.setText("");
			jottoView.jl_stick_north_money.setText("");
			jottoView.jl_stick_north_b1.setText("");
			jottoView.jl_stick_north_b2.setText("");
			jottoView.jl_stick_north_b3.setText("");
			jottoView.jl_stick_north_b4.setText("");
			jottoView.jl_stick_north_b5.setText("");
			jottoView.jl_stick_north_b6.setText("");
			jottoView.jl_stick_north_b7.setText("");
			jottoView.jl_stick_north_episode.setText("");
			jottoView.jl_stick_north_date.setText("");
			jottoView.jf_jotto.revalidate();
			while(rs.next()) {
				jottoView.jl_stick_north_tf.setText("당첨!!!! 축하드립니다!!!");
				jottoView.jl_stick_north_episode.setText("만약에 " + Integer.toString(rs.getInt("EPISODE")) + " 회때라면");
				jottoView.jl_stick_north_money.setText(rs.getString("PRIZE_MONEY") + "원을 가질 수 있었는데...!");
				jottoView.jl_stick_north_date.setText(rs.getString("EPISODE_DATE"));
				result = jottoView.jl_stick_north_date.getText();
				//rs.getString("EPISODE_DATE")
				jottoView.jl_stick_north_b1.setText(Integer.toString(rs.getInt("BALL1")));
				jottoView.jl_stick_north_b2.setText(Integer.toString(rs.getInt("BALL2")));
				jottoView.jl_stick_north_b3.setText(Integer.toString(rs.getInt("BALL3")));
				jottoView.jl_stick_north_b4.setText(Integer.toString(rs.getInt("BALL4")));
				jottoView.jl_stick_north_b5.setText(Integer.toString(rs.getInt("BALL5")));
				jottoView.jl_stick_north_b6.setText(Integer.toString(rs.getInt("BALL6")));
				jottoView.jl_stick_north_b7.setText(Integer.toString(rs.getInt("BALL7")));
				System.out.println("zz");
				jottoView.jf_jotto.revalidate();
			}
			if(result==null) {
				jottoView.jl_stick_north_tf.setText("아쉽게도 당첨하지 못하였습니다....!");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}
	}	
}