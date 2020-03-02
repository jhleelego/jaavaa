package HooonTalk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.util.DBConnectionMgr;


public class HooonTalkPlay extends JFrame implements ActionListener, WindowListener{

	HooonTalkView htView = null;
	HooonTalkEvent htEvent = null;
	
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	JPanel jp_create_center = new JPanel();
	JLabel jl_create_roomName = new JLabel("           방이름 :");
	JTextField jtf_create_roomName = new JTextField(15);
	JLabel jl_create_roomAdmin = new JLabel("           관리자 :");
	JLabel jl_create_roomAdminShow = new JLabel();
	JPanel jp_create_south = new JPanel();
	JButton jbtn_create_back = new JButton("뒤로가기");
	JButton jbtn_create_go = new JButton("방 생성하기");

	JPanel jp_play_east = new JPanel();
	JPanel jp_play_center = new JPanel();
	JLabel jl_play_roomName = new JLabel("           방이름 :");
	JTextField jtf_play_roomName = new JTextField(15);
	JLabel jl_play_roomAdmin = new JLabel("           관리자 :");
	JLabel jl_play_roomAdminShow = new JLabel();
	JPanel jp_play_east_center = new JPanel();
	JTextArea jta_play_user = new JTextArea();
	JPanel jp_play_east_south = new JPanel();
	JTextArea jta_play_userOutput = new JTextArea();
	JScrollPane jsp_play = new JScrollPane(jta_play_userOutput);
	JTextField jtf_play_userInput = new JTextField();
	
	String roomNameTest = null;
	
	String nowRoomName = "";

	
	public HooonTalkPlay(HooonTalkView htView, HooonTalkEvent htEvent) {
		this.htView = htView;
		this.htEvent = htEvent;
	}
	
	public HooonTalkPlay(HooonTalkView htView, HooonTalkEvent htEvent, String nowRoomName) {
		this.htView = htView;
		this.htEvent = htEvent;
		this.nowRoomName = nowRoomName;
	}
	public void goButton(String result) {
		if("enter".equals(result)) {
			userRoomIn();
		}else if("create".equals(result)) {
			createRoomForm();
		} else if("delete".equals(result)) {
			deleteRoom();
		}
	}	

	public void playRoomForm(){
		add("Center", jp_play_center);
		jp_play_center.setLayout(new BorderLayout());
		jp_play_center.add("Center", jsp_play);
		jp_play_center.add("South", jtf_play_userInput);
		jta_play_userOutput.setEditable(false);
		
		jp_play_east.setLayout(new BorderLayout());
		add("East", jp_play_east);
		jp_play_east.add(jta_play_user);
		
		jta_play_user.setBackground(Color.white);
		jta_play_user.setEditable(false);
		addWindowListener(this);
		
		jbtn_create_go.addActionListener(this);
		jbtn_create_back.addActionListener(this);
		jtf_play_userInput.addActionListener(this);
		
		setSize(400,500);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = getSize();
		// (모니터화면 가로 - 프레임화면 가로) / 2, (모니터화면 세로 - 프레임화면 세로) / 2
		setLocation((screenSize.width - frameSize.width) /2, (screenSize.height - frameSize.height) /2);
		setResizable(false);
		setVisible(true);
	}
	
	public void createRoomForm(){
		add("Center", jp_create_center);
		jp_create_center.setLayout(new GridLayout(2,1));
		jp_create_center.setBorder(BorderFactory.createEmptyBorder(10, 90, 10, 90));
		jp_create_center.add(jl_create_roomName);
		jp_create_center.add(jtf_create_roomName);
		jp_create_center.add(jl_create_roomAdmin);
		jp_create_center.add(jl_create_roomAdminShow);
		jl_create_roomAdminShow.setText(htView.privacy[0]);
		
		add("South", jp_create_south);
		jp_create_south.add(jbtn_create_back);
		jp_create_south.add(jbtn_create_go);
		jbtn_create_back.addActionListener(this);
		jbtn_create_go.addActionListener(this);
		
		setSize(400,150);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = getSize();
		// (모니터화면 가로 - 프레임화면 가로) / 2, (모니터화면 세로 - 프레임화면 세로) / 2
		setLocation((screenSize.width - frameSize.width) /2, (screenSize.height - frameSize.height) /2);
		setVisible(true);
		}

	public void createRoom() {
		String room_name = jtf_create_roomName.getText();
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT room_name FROM room WHERE room_name =? ");
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, room_name);
			rs = pstmt.executeQuery();
			String result = "";
			if(rs.next()) {
				result = rs.getString("room_name");
			}
			if(result.equals(room_name)) {
				JOptionPane.showMessageDialog(null, "방 제목 중복입니다. 다른 제목을 입력해 주세요.");
			} else if(!(result.equals(room_name))) {
				//방추가
				sb = null;
				sb = new StringBuilder("");
				sb.append(" INSERT INTO room(room_name, room_admin, room_playcount) VALUES(?, ?, ?) ");
				con = dbMgr.getConnection();
				pstmt = con.prepareStatement(sb.toString());
				int i = 0;
				pstmt.setString(++i, room_name);
				pstmt.setString(++i, htView.privacy[0]);
				pstmt.setInt(++i, 0);
				pstmt.executeUpdate();
				//방정보추가
				sb = null;
				sb = new StringBuilder("");
				sb.append(" CREATE TABLE " + room_name + "(play_user_id VARCHAR2(12))");
				con = dbMgr.getConnection();
				pstmt = con.prepareStatement(sb.toString());
				pstmt.executeUpdate();
				//채팅창추가
				sb = null;
				sb = new StringBuilder("");
				sb.append(" CREATE TABLE " + room_name + "_chat(chat_no NUMBER(3), play_user_id VARCHAR2(12), chat_list VARCHAR2(100))");
				con = dbMgr.getConnection();
				pstmt = con.prepareStatement(sb.toString());
				pstmt.executeUpdate();
				//각채팅창시퀀스
				sb = null;
				sb = new StringBuilder("");
				sb.append(" CREATE SEQUENCE seq_" + room_name + " INCREMENT BY 1 START WITH 1 MINVALUE 1 MAXVALUE 99999 NOCYCLE NOCACHE ORDER ");
				con = dbMgr.getConnection();
				pstmt = con.prepareStatement(sb.toString());
				pstmt.executeUpdate();
				htView.dtm_room.setRowCount(0);
				htView.selectRoom();
				dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}
	}	
	
	public void deleteRoom() {
		if(htView.nowMouseClickResult_name.equals("")||htView.nowMouseClickResult_admin.equals("")) {
			JOptionPane.showMessageDialog(null, "방을 선택하지 않았습니다.");
			return;
		} else if (!(htView.nowMouseClickResult_admin.equals(htView.privacy[0]))){
			JOptionPane.showMessageDialog(null, "선택하신 방의 관리자가 아니여서 삭제하실 수 없습니다.");
		} else {
			StringBuilder sb = new StringBuilder("");
			sb.append(" DELETE FROM room WHERE room_name = ? AND room_admin = ? ");
			try {
				//채팅방제거
				con = dbMgr.getConnection();
				pstmt = con.prepareStatement(sb.toString());
				int i = 0;
				pstmt.setString(++i, htView.nowMouseClickResult_name);
				pstmt.setString(++i, htView.nowMouseClickResult_admin);
				pstmt.executeUpdate();
				//방정보제거
				sb = null;
				sb = new StringBuilder("");
				sb.append(" DROP TABLE " + htView.nowMouseClickResult_name);
				con = dbMgr.getConnection();
				pstmt = con.prepareStatement(sb.toString());
				pstmt.executeUpdate();
				//채팅방제거
				sb = null;
				sb = new StringBuilder("");
				sb.append(" DROP TABLE " + htView.nowMouseClickResult_name+"_chat");
				con = dbMgr.getConnection();
				pstmt = con.prepareStatement(sb.toString());
				pstmt.executeUpdate();
				//채팅창시퀀스제거
				sb = null;
				sb = new StringBuilder("");
				sb.append(" DROP SEQUENCE seq_"+htView.nowMouseClickResult_name);
				con = dbMgr.getConnection();
				pstmt = con.prepareStatement(sb.toString());
				pstmt.executeUpdate();				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dbMgr.freeConnection(con, pstmt);
			}
			htView.dtm_room.setRowCount(0);
			htView.selectRoom();
		}
	}
	
	private void userRoomIn() {
		jta_play_user.setText("현재접속자\n");
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT play_user_id FROM " + nowRoomName + " WHERE play_user_id = ? ");
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, htView.privacy[0]);
			rs = pstmt.executeQuery();
			String roomInUserResult = "";
			while (rs.next()) {
				roomInUserResult = rs.getString("play_user_id"); 
			}
			if(roomInUserResult.equals(htView.privacy[0])) {
				System.out.println("들어갈 수 없습니다.");
				return;
			} else {		
			sb = null;
			sb = new StringBuilder("");
			sb.append("SELECT rownum FROM " + nowRoomName);
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			String rowNum = "";
			while(rs.next()) {
				rowNum = rs.getString("rownum");
			}		
			sb = new StringBuilder("");
			sb.append("INSERT INTO " + nowRoomName + "(play_user_id) VALUES(?)");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, htView.privacy[0]);	
			pstmt.executeUpdate();
			userRefresh();
			chatRefresh();
			playRoomForm();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
			userRefresh();
			htView.selectRoom();
		}
	}
	
	private void userRoomOut() {
		StringBuilder sb = new StringBuilder("");
		sb.append("DELETE FROM " + nowRoomName + " WHERE play_user_id = ?");
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, htView.privacy[0]);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbMgr.freeConnection(con, pstmt);
			//userRefresh();
			userRefresh();
			htView.selectRoom();
		}
	}
	
	public void userRefresh() {
		jta_play_user.setText("현재접속자\n");
		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT play_user_id FROM " + nowRoomName);
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				jta_play_user.append(rs.getString("play_user_id")+"\n");
			}
			sb = null;
			sb = new StringBuilder("");
			sb.append("SELECT rownum FROM " + nowRoomName);
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			String rowNum = "";
			while(rs.next()) {
				rowNum = rs.getString("rownum");
			}

			sb = null;
			sb = new StringBuilder("");
			sb.append(" UPDATE room SET room_playcount = ? WHERE room_name = ?");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, rowNum);
			pstmt.setString(2, nowRoomName);
			pstmt.executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}
	}
	
	public void chatInput() {
		if("".equals(jtf_play_userInput.getText())) {
			return;
		}
		String chat = jtf_play_userInput.getText();
		StringBuilder sb = new StringBuilder("");
		sb.append(" INSERT INTO " + nowRoomName + "_chat (chat_no, play_user_id, chat_list)");
		sb.append(" VALUES(seq_" + nowRoomName + ".nextval, ?, ?)  		   		  ");
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, htView.privacy[0]);
			pstmt.setString(2, chat);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jtf_play_userInput.setText("");
			dbMgr.freeConnection(con, pstmt);
			jsp_play.getVerticalScrollBar().setValue(jsp_play.getVerticalScrollBar().getMaximum());
		}
	}
	public void chatRefresh() {
		jta_play_userOutput.setText("");
		StringBuilder sb = new StringBuilder("");
		try {
			sb.append("delete FROM " + nowRoomName + "_chat WHERE chat_list is null");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.executeUpdate();
			sb = null;
			sb = new StringBuilder("");
			sb.append(" SELECT chat_no, play_user_id, chat_list FROM " + nowRoomName + "_chat ORDER BY chat_no asc ");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			String nowUserOutput = "";
			String colUserOutput = "";

			while(rs.next()) {
				if(rs.isFirst()) {
					colUserOutput = rs.getString("play_user_id");
					nowUserOutput = colUserOutput;
					jta_play_userOutput.append(nowUserOutput + " : \n");
					jta_play_userOutput.append(rs.getString("chat_list") + " \n");
				} else {
					colUserOutput = rs.getString("play_user_id");
					if(!(colUserOutput.equals(nowUserOutput))) {
						nowUserOutput = colUserOutput;
						jta_play_userOutput.append(nowUserOutput+" : \n");
						jta_play_userOutput.append(rs.getString("chat_list") + " \n");
					} else {
						jta_play_userOutput.append(rs.getString("chat_list") + " \n");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jtf_play_userInput.setText("");
			dbMgr.freeConnection(con, pstmt, rs);
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == jbtn_create_go || obj==jtf_create_roomName) {
			roomNameTest = jtf_create_roomName.getText().replaceAll(" ", "");				
			if(roomNameTest.equals("")) {
				JOptionPane.showMessageDialog(null,"공백으로 방을 만들 수 없습니다.");
					return;		
			} else {createRoom();}	
		} else if(obj == jbtn_create_back) {
			dispose();
		} else if(obj ==jtf_play_userInput){
			chatInput();
			chatRefresh();
		}		
	}

	@Override
	public void windowActivated(java.awt.event.WindowEvent e) {}
	@Override
	public void windowClosed(java.awt.event.WindowEvent e) {
		htEvent.openRoomList.remove(nowRoomName);
		userRoomOut();
	}
	@Override
	public void windowClosing(java.awt.event.WindowEvent e) {
		htEvent.openRoomList.remove(nowRoomName);
		userRoomOut();
	}
	@Override
	public void windowDeactivated(java.awt.event.WindowEvent e) {}
	@Override
	public void windowDeiconified(java.awt.event.WindowEvent e) {}
	@Override
	public void windowIconified(java.awt.event.WindowEvent e) {}
	@Override
	public void windowOpened(java.awt.event.WindowEvent e) {}
	
}
