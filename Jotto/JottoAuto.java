package Jotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import com.util.DBConnectionMgr;

public class JottoAuto {
	JottoView jottoView = null;
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public JottoAuto(JottoView jView) {
		this.jottoView = jView;
		jottoView.now="Auto";
		jView.jottoAutoView();
	}
	public void madeLine(String lineCount_s) {
			//리털타입을 1차 배열로 했으므로 1차 배열 선언하기
			//오라클 서버에게 보낼 select문 작성하기
			//String은 원본이 바뀌지 않음.
			StringBuilder sb = new StringBuilder("");
			//자바코드는 이클립스에서 디버깅하고 select문 토드에서 디버깅하기
			if(lineCount_s.equals("1")) {
				jottoView.jp_auto_north.setBorder(BorderFactory.createEmptyBorder(160, 10, 10, 10));
			} else if(lineCount_s.equals("2")) {
				jottoView.jp_auto_north.setBorder(BorderFactory.createEmptyBorder(140, 10, 10, 10));
			} else if(lineCount_s.equals("3")) {
				jottoView.jp_auto_north.setBorder(BorderFactory.createEmptyBorder(120, 10, 10, 10));
			} else if(lineCount_s.equals("4")) {
				jottoView.jp_auto_north.setBorder(BorderFactory.createEmptyBorder(100, 10, 10, 10));
			} else if(lineCount_s.equals("5")) {
				jottoView.jp_auto_north.setBorder(BorderFactory.createEmptyBorder(80, 10, 10, 10));
			} else if(lineCount_s.equals("6")) {
				jottoView.jp_auto_north.setBorder(BorderFactory.createEmptyBorder(60, 10, 10, 10));
			}	
		    sb.append("  SELECT MIN(DECODE(MOD(ROWNUM - 1, 7) + 1, 1, num)) no1, ");
		    sb.append("         MIN(DECODE(MOD(ROWNUM - 1, 7) + 1, 2, num)) no2, ");
		    sb.append("         MIN(DECODE(MOD(ROWNUM - 1, 7) + 1, 3, num)) no3, ");
		    sb.append("         MIN(DECODE(MOD(ROWNUM - 1, 7) + 1, 4, num)) no4, ");
		    sb.append("         MIN(DECODE(MOD(ROWNUM - 1, 7) + 1, 5, num)) no5, ");
		    sb.append("         MIN(DECODE(MOD(ROWNUM - 1, 7) + 1, 6, num)) no6, ");
		    sb.append("         MIN(DECODE(MOD(ROWNUM - 1, 7) + 1, 7, num)) no7  ");
		    sb.append("    FROM (                                                ");
			sb.append("  SELECT grp, num                                         ");
			sb.append("    FROM (                                                ");
			sb.append("  SELECT grp, num, MOD(ROWNUM - 1, 45) + 1 rnum           ");
			sb.append("    FROM (                                                ");
			sb.append("	 SELECT CEIL(LEVEL / 45) grp, MOD(LEVEL - 1, 45) + 1 num ");
			sb.append("	   FROM DUAL                                             ");
			sb.append(" CONNECT BY LEVEL <= 45 * ?                               ");
			sb.append("	  ORDER BY 1, DBMS_RANDOM.VALUE                          ");
			sb.append("		    )                                                ");
			sb.append("	     )                                                   ");
			sb.append("	  WHERE rnum <= 7                                        ");
			sb.append("	  ORDER BY grp, num                                      ");
			sb.append("   )                                                      ");
			sb.append("   GROUP BY grp                                           ");	
			try {
				con = dbMgr.getConnection();
				pstmt = con.prepareStatement(sb.toString());
				pstmt.setString(1, lineCount_s);
				rs = pstmt.executeQuery();
				Vector<BallVO> v = new Vector<>();				
				BallVO[] bVOS = null;
				BallVO bVO = null;
				while(rs.next()) {
					bVO = new BallVO();
					bVO.setBall1Num(rs.getString("NO1"));
					bVO.setBall2Num(rs.getString("NO2"));
					bVO.setBall3Num(rs.getString("NO3"));
					bVO.setBall4Num(rs.getString("NO4"));
					bVO.setBall5Num(rs.getString("NO5"));
					bVO.setBall6Num(rs.getString("NO6"));
					bVO.setBall7Num(rs.getString("NO7"));
					v.add(bVO);			
				}
				bVOS = new BallVO[v.size()];
				v.copyInto(bVOS);
				for(int x=0; x<v.size();x++) {
					//그래서 for문 안에서 벡터를 하나 더 생성했어요
					//addRow라는 메소드가 있는데 이 파라미터에 Vector를 넣으면 한개로우씩
					//추가 해준다고 합니다.
					Vector<Object> row = new Vector<>();
					row.add(0, bVOS[x].getBall1Num());
					row.add(1, bVOS[x].getBall2Num());
					row.add(2, bVOS[x].getBall3Num());
					row.add(3, bVOS[x].getBall4Num());
					row.add(4, bVOS[x].getBall5Num());
					row.add(5, bVOS[x].getBall6Num());
					row.add(6, bVOS[x].getBall7Num());
					jottoView.dtm_auto.addRow(row);
				}
			} catch (Exception e) {
				//stack영역에 관리되는 에러메시지 정보를 라인번호와 이력까지 출력해줌.
				e.printStackTrace();
			}
	}
}
