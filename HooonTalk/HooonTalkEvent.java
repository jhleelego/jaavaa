package HooonTalk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class HooonTalkEvent implements ActionListener {
	HooonTalkView htView = null;
	HooonTalkLogin htLogin = null;
	HooonTalkJoin htJoin = null;
	HooonTalkPlay htPlay = null;

	
	ArrayList<String> openRoomList = new ArrayList<>();
	//공백체크
	public HooonTalkEvent(HooonTalkView htView) {
		this.htView = htView;
	}
	
	public boolean openFine() {
		boolean open = true;
		if(openRoomList.size()==0) {
			open = true;
			return open;
		} else {
			for(int i=0; i<openRoomList.size(); i++) {
				if(openRoomList.get(i).equals(htView.nowMouseClickResult_name)) {
					open = false;
					return open;
				} else {
					open = true;
				}
			}
		return open;
		}
	}	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == htView.jbtn_login_join) {
			htView.changeJoinForm();
		} else if(obj == htView.jbtn_login_login || obj == htView.jtf_pw || obj == htView.jtf_id) {
			htLogin = new HooonTalkLogin(htView);
			htPlay = new HooonTalkPlay(htView, this);
			htLogin = null;
		} else if(obj == htView.jbtn_join_back) {
			htView.removeAll();
			htView = new HooonTalkView();
			htJoin = null;
		} else if(obj == htView.jbtn_join_joingo
			   || obj == htView.jtf_join_studentAge
			   || obj == htView.jtf_join_studentEmail
			   || obj == htView.jtf_join_studentId
			   || obj == htView.jtf_join_studentPw
			   || obj == htView.jtf_join_studentName) {
			htJoin = new HooonTalkJoin(htView, this);
			if(htJoin.checkResult==true) {
				System.out.println(htJoin.checkResult);
				htView.removeAll();
				htView = new HooonTalkView();
				htJoin = null;
			} else {
				return;
			}
		} else if(obj == htView.jbtn_play_enterRoom) {
			htPlay = null;
			boolean open = openFine();
			for(int i = 0; i<openRoomList.size();i++) {
				System.out.println(openRoomList.get(i));
			}
			if(open==true) {
				openRoomList.add(htView.nowMouseClickResult_name);
				htPlay = new HooonTalkPlay(htView, this, htView.nowMouseClickResult_name);
				htPlay.goButton("enter");
			} else if(open==false) {
				return;
			}
		} else if(obj == htView.jbtn_play_createRoom) {
			htPlay = null;
			htPlay = new HooonTalkPlay(htView, this);
			htPlay.goButton("create");	
		} else if(obj == htView.jbtn_play_deleteRoom) {
			htPlay = null;
			htPlay = new HooonTalkPlay(htView, this);
			htPlay.goButton("delete");
		} else if(obj == htView.jbtn_play_logout) {
			htView.createLoginForm();
			htView.removeAll();
			htView = new HooonTalkView();
			htPlay = null;
		}
	}	
}
