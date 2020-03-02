package HooonTalk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.util.DBConnectionMgr;


public class HooonTalkView extends JFrame {
	//메인폼
	HooonTalkEvent htEvent 				= null;
	RoomVO<StudentVO> room 				= null;
	ArrayList<RoomVO> al_Room= null;
	
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String[] privacy = null;
	
	//로그인 플레이폼 공통
	String cols[]					= {"톡방명", "관리자", "현재참여자수"};
	String data[][] 				= new String[0][3];
	DefaultTableModel dtm_room 	= new DefaultTableModel(data,cols);
	JTable jt_room 				= new JTable(dtm_room);
	JScrollPane jsp_north_room 	= new JScrollPane(jt_room);
	
	int[] rows = null;
	
	//로그인폼
	JPanel jp_login_loginForm 			= new JPanel();
	JPanel jp_login_north_room 			= new JPanel();
	JPanel jp_login_north_logo 			= new JPanel();
	JLabel jl_login_logo 				= new JLabel("HOOONTALK");
	JPanel jp_login_center 				= new JPanel();
	JLabel jl_id 						= new JLabel("                 아 이 디 :");
	JLabel jl_pw 						= new JLabel("                 비밀번호 :");
	JTextField jtf_id 					= new JTextField(20);
	JTextField jtf_pw 					= new JTextField(20);
	JPanel jp_login_south 				= new JPanel();
	JButton jbtn_login_join 			= new JButton("회원가입");
	JButton jbtn_login_login 			= new JButton("  로그인  ");
	JPanel jp_login_south_info			= new JPanel();
	JLabel jl_login_info 				= new JLabel("관리자 이메일 : jhleelego@naver.com");
	//조인폼
	JPanel jp_join_joinForm 			= new JPanel();
	JPanel jp_join_north 				= new JPanel();
	JLabel jl_join_logo 				= new JLabel("HOOONTALK");
	JPanel jp_join_center 				= new JPanel(); 
	JLabel jl_join_studentId 			= new JLabel("아이디 (6 ~ 12자이하) ");
	JTextField jtf_join_studentId 		= new JTextField(10);
	JLabel jl_join_studentPw 			= new JLabel("패스워드 (8 ~ 16자이하) ");
	JTextField jtf_join_studentPw 		= new JTextField(10);
	JLabel jl_join_studentName 			= new JLabel("이 름 ");
	JTextField jtf_join_studentName 	= new JTextField(10);
	JLabel jl_join_studentAge 			= new JLabel("나 이  ");
	JTextField jtf_join_studentAge 		= new JTextField(10);
	JLabel jl_join_studentEmail 		= new JLabel("이메일  ");
	JTextField jtf_join_studentEmail 	= new JTextField(10);
	JPanel jp_join_south 				= new JPanel();
	JButton jbtn_join_back				= new JButton("뒤로가기");
	JButton jbtn_join_joingo 			= new JButton("가입하기");
	JLabel jl_join_info 				= new JLabel("관리자 이메일 : jhleelego@naver.com");
	//플레이폼
	JPanel jp_play_playForm 			= new JPanel();
	JPanel jp_play_north_room			= new JPanel();
	JPanel jp_play_north_logo 			= new JPanel();
	JLabel jl_play_logo 				= new JLabel("HOOONTALK");
	JPanel jp_play_center 				= new JPanel(); 
	JLabel jl_play_studentId 			= new JLabel();
	JPanel jp_play_south 				= new JPanel();
	JButton jbtn_play_enterRoom			= new JButton("방 참가");
	JButton jbtn_play_createRoom		= new JButton("방 생성");
	JButton jbtn_play_deleteRoom			= new JButton("방 삭제");
	JButton jbtn_play_logout			= new JButton("로그아웃");
	JLabel jl_play_info 				= new JLabel("관리자 이메일 : jhleelego@naver.com");
	
	//마우스오버관련
	String nowMouseClickResult_name = "";
	String nowMouseClickResult_admin = "";
	
	
	public HooonTalkView() {
		selectRoom();
		createLoginForm();
	}

	public void selectRoom() {
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT room_name, room_admin, room_playcount FROM room ");
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			al_Room = new ArrayList<>();
			RoomVO<StudentVO> rVO = null;
			while(rs.next()) {
				rVO = new RoomVO<>();
				rVO.setRoomName(rs.getString("ROOM_NAME"));
				rVO.setRoomAdmin(rs.getString("ROOM_ADMIN"));
				rVO.setRoomPlayCount(rs.getInt("ROOM_PLAYCOUNT"));
				al_Room.add(rVO);
			}
			dtm_room.setRowCount(0);

			//방수
			rows = new int[al_Room.size()];
			for(int i = 0; i<al_Room.size(); i++) {
				rows[i] = i+1;
			}
			
			for(int i=0; i<al_Room.size();i++) {
				int j = 0;
				Vector<Object> row = new Vector<>();
				row.add(j++, al_Room.get(i).getRoomName());
				row.add(j++, al_Room.get(i).getRoomAdmin());
				row.add(j++, Integer.toString(al_Room.get(i).getRoomPlayCount()));
				dtm_room.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}
		jt_room.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}			
			@Override
			public void mousePressed(MouseEvent e) {}			
			@Override
			public void mouseExited(MouseEvent e) {}			
			@Override
			public void mouseEntered(MouseEvent e) {}			
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = jt_room.getSelectedRow();
		    	int col = jt_room.getSelectedColumn();
		    	nowMouseClickResult_name = (String)jt_room.getModel().getValueAt(row, 0);
		    	nowMouseClickResult_admin = (String)jt_room.getModel().getValueAt(row, 1);	
		    	System.out.println("nowMouseClickResult_name : " +nowMouseClickResult_name);
			}
		});
		revalidate();
		repaint();
	}
	
	public void createLoginForm() {
		jp_play_playForm.removeAll();
		jp_join_joinForm.removeAll();
		add(jp_login_loginForm);
		jp_join_joinForm.setVisible(false);
		jp_play_playForm.setVisible(false);
		jp_login_loginForm.setVisible(true);
		jt_room.setRowHeight(25);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = jt_room.getColumnModel();
		for(int i=0; i<cols.length; i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);	
		}	
		jp_login_loginForm.setLayout(new BorderLayout());
		jp_login_loginForm.add("North", jp_login_north_room);
		
		jp_login_north_room.add(jp_login_north_logo);
		jp_login_north_room.add(jsp_north_room);
		jp_login_north_logo.add(jl_login_logo);
		jl_login_logo.setFont(new Font("@HY헤드라인M", Font.BOLD, 25));
		jp_login_north_room.setBorder(BorderFactory.createEmptyBorder(20,10,70,10));
		jsp_north_room.setPreferredSize(new Dimension(400, 300));

		jp_login_loginForm.add("Center", jp_login_center);
		jp_login_center.setBorder(BorderFactory.createEmptyBorder(0, 90, 0, 90));
		jp_login_center.setLayout(new GridLayout(2,1));
		jp_login_center.add(jl_id);
		jp_login_center.add(jtf_id);
		jp_login_center.add(jl_pw);
		jp_login_center.add(jtf_pw);

		privacy = null;
		jtf_id.setText("");
		jtf_pw.setText("");
		

		jp_login_loginForm.add("South", jp_login_south);
		jp_login_south.setBorder(BorderFactory.createEmptyBorder(0, 40, 60, 40));
		jp_login_south.add(jbtn_login_join);
		jp_login_south.add(jbtn_login_login);
		jp_login_south.add(jl_login_info);
		htEvent = new HooonTalkEvent(this);

		addWindowListener(htEvent.htPlay);
		
		jbtn_login_join.addActionListener(htEvent);
		jbtn_login_login.addActionListener(htEvent);
		jbtn_join_back.addActionListener(htEvent);
		jbtn_join_joingo.addActionListener(htEvent);
		jtf_id.addActionListener(htEvent);
		jtf_pw.addActionListener(htEvent);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(480, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);

	}
	public void changeJoinForm() {
		jp_play_playForm.removeAll();
		jp_login_loginForm.removeAll();
		add(jp_join_joinForm);
		jp_login_loginForm.setVisible(false);
		jp_play_playForm.setVisible(false);
		jp_join_joinForm.setVisible(true);

		jp_join_joinForm.setLayout(new BorderLayout());
		jp_join_joinForm.add("North", jp_join_north);

		jp_join_north.add(jl_join_logo);
		jl_join_logo.setFont(new Font("@HY헤드라인M", Font.BOLD, 25));
		jp_join_north.setBorder(BorderFactory.createEmptyBorder(25,10,60,10));
		
		jp_join_joinForm.add("Center", jp_join_center);
		jp_join_center.setBorder(BorderFactory.createEmptyBorder(20, 90, 20, 90));
		jp_join_center.setLayout(new GridLayout(10,1));
		jp_join_center.add(jl_join_studentId);
		jp_join_center.add(jtf_join_studentId);
		jp_join_center.add(jl_join_studentPw);
		jp_join_center.add(jtf_join_studentPw);
		jp_join_center.add(jl_join_studentName);
		jp_join_center.add(jtf_join_studentName);
		jp_join_center.add(jl_join_studentAge);
		jp_join_center.add(jtf_join_studentAge);
		jp_join_center.add(jl_join_studentEmail);
		jp_join_center.add(jtf_join_studentEmail);
		
		jtf_join_studentId.setText("");
		jtf_join_studentPw.setText("");
		jtf_join_studentName.setText("");
		jtf_join_studentAge.setText("");
		jtf_join_studentEmail.setText("");
		
		jp_join_joinForm.add("South", jp_join_south);
		jp_join_south.setBorder(BorderFactory.createEmptyBorder(10, 40, 60, 40));	
		jp_join_south.add(jbtn_join_back);
		jp_join_south.add(jbtn_join_joingo);
		jp_join_south.add(jl_join_info);
		
		jbtn_join_back.addActionListener(htEvent);

		revalidate();
		repaint();
	}
	
	public void changePlayForm() {
		selectRoom();
		jp_join_joinForm.removeAll();
		jp_login_loginForm.removeAll();
		add(jp_play_playForm);
		jp_login_loginForm.setVisible(false);
		jp_join_joinForm.setVisible(false);
		jp_play_playForm.setVisible(true);
		jt_room.setRowHeight(25);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = jt_room.getColumnModel();
		for(int i=0; i<cols.length; i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);	
		}	
		jp_play_playForm.setLayout(new BorderLayout());
		jp_play_playForm.add("North", jp_play_north_room);
		
		jp_play_north_room.add(jp_play_north_logo);
		jp_play_north_room.add(jsp_north_room);
		jp_play_north_logo.add(jl_play_logo);
		jl_play_logo.setFont(new Font("@HY헤드라인M", Font.BOLD, 25));
		jp_play_north_room.setBorder(BorderFactory.createEmptyBorder(20,10,70,10));
		jsp_north_room.setPreferredSize(new Dimension(400, 300));

		jp_play_playForm.add("Center", jp_play_center);
		jp_play_center.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
		jp_play_center.setLayout(new GridLayout(1,1));
		jp_play_center.add(jbtn_play_enterRoom);
		jp_play_center.add(jbtn_play_createRoom);
		jp_play_center.add(jbtn_play_deleteRoom);
		jp_play_center.add(jbtn_play_logout);
           
		jp_play_playForm.add("South", jp_play_south);
		jp_play_south.setBorder(BorderFactory.createEmptyBorder(15, 40, 60, 40));
		jl_play_studentId.setFont(new Font("@HY헤드라인M", Font.BOLD, 25));
		jp_play_south.add(jl_play_studentId);
		jp_play_south.add(jl_play_info);

		htEvent = new HooonTalkEvent(this);
		
		jbtn_play_enterRoom.addActionListener(htEvent);
		jbtn_play_createRoom.addActionListener(htEvent);
		jbtn_play_deleteRoom.addActionListener(htEvent);
		jbtn_play_logout.addActionListener(htEvent);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(480, 600);
		setVisible(true);

		revalidate();
		repaint();
	}
	
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		new HooonTalkView();
	}
}
