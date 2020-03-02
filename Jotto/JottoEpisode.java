package Jotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.util.DBConnectionMgr;

public class JottoEpisode {
	
	JottoView jottoView = null;
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null; 
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		
	JottoEpisode(JottoView jView) {
		this.jottoView = jView;
		jottoView.now="Episode";
		getEpisodeList();
		jView.jottoEpisodeView();
		
	}
	
	public void getEpisodeList() {
		String[] episodes = null;
		StringBuilder sb = new StringBuilder("");
		sb.append("	SELECT '전체' episode FROM dual" );
		sb.append("	 UNION ALL                     ");
		sb.append("	SELECT to_char(episode)        ");
		sb.append("	  FROM (                       ");
		sb.append("	SELECT episode                 ");
		sb.append("	  FROM jotto_episode           ");
		sb.append("	 ORDER BY episode desc)        ");
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			Vector<String> v = new Vector<>();
			while(rs.next()) {
				String episode = rs.getString("episode");
				v.add(episode);
			}
			episodes = new String[v.size()];
			v.copyInto(episodes);
			jottoView.episodeCounts = new String[episodes.length];
			jottoView.episodeCounts = episodes;			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getEpisodeSearch(String episodeCountChoice) {
		String episodeCount = episodeCountChoice;		
		StringBuilder sb = new StringBuilder("");
		if(episodeCountChoice.equals("전체")){
			sb.append("SELECT ");
			sb.append(" EPISODE,");
			sb.append(" TO_CHAR(EPISODE_DATE, 'yyyy-mm-dd') AS EPISODE_DATE,");
			sb.append(" WINNER_COUNTS,");
			sb.append(" PRIZE_MONEY,");
			sb.append(" BALL1, BALL2, BALL3, BALL4, BALL5, BALL6, BALL7 "); 
			sb.append(" FROM JOTTO_EPISODE ");
		} else {
		sb.append("SELECT ");
		sb.append(" EPISODE,");
		sb.append(" TO_CHAR(EPISODE_DATE, 'yyyy-mm-dd') AS EPISODE_DATE,");
		sb.append(" WINNER_COUNTS,");
		sb.append(" PRIZE_MONEY,");
		sb.append(" BALL1, BALL2, BALL3, BALL4, BALL5, BALL6, BALL7 "); 
		sb.append(" FROM JOTTO_EPISODE ");
		sb.append(" WHERE EPISODE  = ?");	
		}
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			if(episodeCountChoice.equals("전체")){		
			} else {
				pstmt.setString(1, episodeCount);
			}
			rs = pstmt.executeQuery();
			Vector<EpisodeVO> v = new Vector<>();
			EpisodeVO[] epiVOS = null;
			EpisodeVO epiVO = null;
			while(rs.next()) {
				epiVO = new EpisodeVO();
				epiVO.setEpisode(rs.getInt("EPISODE"));
				epiVO.setEpisode_date(rs.getString("EPISODE_DATE"));
				epiVO.setWinner_counts(rs.getInt("WINNER_COUNTS"));
				epiVO.setPrize_money(rs.getString("PRIZE_MONEY"));
				epiVO.setBall1(rs.getInt("BALL1"));
				epiVO.setBall2(rs.getInt("BALL2"));
				epiVO.setBall3(rs.getInt("BALL3"));
				epiVO.setBall4(rs.getInt("BALL4"));
				epiVO.setBall5(rs.getInt("BALL5"));
				epiVO.setBall6(rs.getInt("BALL6"));
				epiVO.setBall7(rs.getInt("BALL7"));
				v.add(epiVO);
			}
			epiVOS = new EpisodeVO[v.size()];
			v.copyInto(epiVOS);
			for(int i = 0; i<v.size(); i++) {
				int j = 0;
				Vector<Object> row = new Vector<>();
				row.add(j++, epiVOS[i].getEpisode());
				row.add(j++, epiVOS[i].getEpisode_date());
				row.add(j++, epiVOS[i].getWinner_counts());
				row.add(j++, epiVOS[i].getPrize_money());
				row.add(j++, epiVOS[i].getBall1());
				row.add(j++, epiVOS[i].getBall2());
				row.add(j++, epiVOS[i].getBall3());
				row.add(j++, epiVOS[i].getBall4());
				row.add(j++, epiVOS[i].getBall5());
				row.add(j++, epiVOS[i].getBall6());
				row.add(j++, epiVOS[i].getBall7());
				jottoView.dtm_episode.addRow(row);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
