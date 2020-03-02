package Jotto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class JottoEvent implements ActionListener, ItemListener {
	JottoView jottoView = null;
	JottoStick jottoStick = null;
	JottoAuto jottoAuto = null;
	JottoEpisode jottoEpisode = null;
	public JottoEvent(JottoView jottoView) {	
		this.jottoView = jottoView;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == jottoView.jbtn_stick) {
			if(jottoView.now=="Stick") {
				return;
			}else {
				jottoStick = new JottoStick(jottoView);				
			}
		}else if(obj==jottoView.jbtn_stick_go) {
			if("".equals(jottoView.jtf_stick_num1.getText())
		 	 ||"".equals(jottoView.jtf_stick_num2.getText())
		 	 ||"".equals(jottoView.jtf_stick_num3.getText())
			 ||"".equals(jottoView.jtf_stick_num4.getText())
			 ||"".equals(jottoView.jtf_stick_num5.getText())
			 ||"".equals(jottoView.jtf_stick_num6.getText())
			 ||"".equals(jottoView.jtf_stick_num7.getText())) {
				return;
			} else {
				jottoView.stick_nums[0] = jottoView.jtf_stick_num1.getText();
				jottoView.stick_nums[1] = jottoView.jtf_stick_num2.getText();
				jottoView.stick_nums[2] = jottoView.jtf_stick_num3.getText();
				jottoView.stick_nums[3] = jottoView.jtf_stick_num4.getText();
				jottoView.stick_nums[4] = jottoView.jtf_stick_num5.getText();
				jottoView.stick_nums[5] = jottoView.jtf_stick_num6.getText();
				jottoView.stick_nums[6] = jottoView.jtf_stick_num7.getText();
				jottoStick.getStickSearch(jottoView.stick_nums);
				
			}			
		} else if(obj == jottoView.jbtn_auto) {
			if(jottoView.now=="Auto") {
				return;
			}else {
				jottoAuto = new JottoAuto(jottoView);
			}
		} else if(obj==jottoView.jbtn_auto_go) {
			jottoView.dtm_auto.setNumRows(0);
			jottoAuto.madeLine(jottoView.lineCount);	
		} else if(obj == jottoView.jbtn_episode) {
			if(jottoView.now=="Episode") {
				return;
			}else {
				jottoEpisode = new JottoEpisode(jottoView);
			}
		} else if(obj==jottoView.jbtn_episode_go) {
			jottoView.dtm_episode.setNumRows(0);
			jottoEpisode.getEpisodeSearch(jottoView.episodeCount);
		} else if(obj == jottoView.jbtn_clean) {
			if(jottoView.now.equals("Stick")) {
				jottoView.jottoStickViewClean();
			} else if(jottoView.now.equals("Auto")) {
				jottoView.jottoAutoViewClean();

			} else if(jottoView.now.equals("Episode")) {
				jottoView.jottoEpisodeViewClean();
			}
		}
	}
	@Override
	public void itemStateChanged(ItemEvent ie) {
		Object obj = ie.getSource();
		if(obj == jottoView.jcb_auto) {
			if(ie.getStateChange()==ItemEvent.SELECTED) {
				jottoView.lineCount = jottoView.jcb_auto.getSelectedItem().toString();	
				System.out.println(jottoView.lineCount);
			}
		}
		if(obj == jottoView.jcb_episode) {
			if(ie.getStateChange()==ItemEvent.SELECTED) {
				jottoView.episodeCount = jottoView.jcb_episode.getSelectedItem().toString();
				System.out.println(jottoView.episodeCount);
		
			}
		}
	}	
	
}
