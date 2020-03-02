package Jotto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class JottoView{
	String now = "";
	//공통
	JFrame jf_jotto = new JFrame("Jotto 복권");
	Font auto_font = null;
	//공통 하단
	JPanel jp_south = new JPanel();
	JButton jbtn_stick = new JButton("수동");		//0
	JButton jbtn_auto = new JButton("자동");		//1
	JButton jbtn_episode = new JButton("회차검색");//2
	JButton jbtn_clean = new JButton("지우기");	//3
	//stick
	JPanel jp_stick = new JPanel();
	JPanel jp_stick_north = new JPanel();
	JPanel jp_stick_north_1 = new JPanel();
	JPanel jp_stick_north_2 = new JPanel();
	JPanel jp_stick_north_3 = new JPanel();
	JPanel jp_stick_north_4 = new JPanel();
	JPanel jp_stick_north_5 = new JPanel();
	JPanel jp_stick_south = new JPanel();
	JButton jbtn_stick_go = new JButton("과거뽑기");
	JTextField jtf_stick_num1 = new JTextField(2);
	JTextField jtf_stick_num2 = new JTextField(2);
	JTextField jtf_stick_num3 = new JTextField(2);
	JTextField jtf_stick_num4 = new JTextField(2);
	JTextField jtf_stick_num5 = new JTextField(2);
	JTextField jtf_stick_num6 = new JTextField(2);
	JTextField jtf_stick_num7 = new JTextField(2);
	JottoEvent jottoEvent = null;
	JLabel jl_stick_north_tf = new JLabel();
	JLabel jl_stick_north_money = new JLabel();
	JLabel jl_stick_north_b1 = new JLabel();
	JLabel jl_stick_north_b2 = new JLabel();
	JLabel jl_stick_north_b3 = new JLabel();
	JLabel jl_stick_north_b4 = new JLabel();
	JLabel jl_stick_north_b5 = new JLabel();
	JLabel jl_stick_north_b6 = new JLabel();
	JLabel jl_stick_north_b7 = new JLabel();
	JLabel jl_stick_north_episode = new JLabel();
	JLabel jl_stick_north_date = new JLabel();
	String[] stick_nums = new String[7];
	//auto
	JPanel jp_auto = new JPanel();
	JPanel jp_auto_north = new JPanel();
	JPanel jp_auto_south = new JPanel();
	String lineCount = "";
	String[] lineCounts = null;
	JComboBox<String> jcb_auto = null;
	JButton jbtn_auto_go = new JButton("자동뽑기");
	JLabel[] jl_auto_oneLine =null;
	String[] cols_auto = {"b1", "b2", "b3", "b4", "b5", "b6", "b7"}; 
	String[][] data_auto = new String[0][0]; 
	DefaultTableModel dtm_auto = new DefaultTableModel(data_auto, cols_auto);
	JTable jt_auto = new JTable(dtm_auto);
	//clean
	//episode
	JPanel jp_episode = new JPanel();
	JPanel jp_episode_north = new JPanel();
	String episodeCount = "";
	String[] episodeCounts = null;
	JComboBox<String> jcb_episode = null;
	JButton jbtn_episode_go = new JButton("회 검색");
	String[] cols_episode = {"회차", "날짜", "당첨자수", "상금", "1", "2", "3", "4", "5", "6", "보너스"};
	String[][] data_episode = new String[0][0];
	DefaultTableModel dtm_episode = new DefaultTableModel(data_episode, cols_episode);
	JTable jt_episode = new JTable(dtm_episode);
	JScrollPane jsp_episode = new JScrollPane(jt_episode);
	public JottoView() {
		initDisplay();
	}
	public void initDisplay() {		
		//프레임 사이즈, 존재여부
		jf_jotto.setResizable(false);
		jf_jotto.setSize(600,480);
		jf_jotto.setVisible(true);
		jf_jotto.setLocationRelativeTo(null);
		//버튼 추가 남쪽에
		jf_jotto.add("South", jp_south);
		//상시 쓰는 버튼레이아웃
		jp_south.setLayout(new GridLayout(1,4));
		jp_south.setPreferredSize(new Dimension(jf_jotto.getWidth(), jf_jotto.getHeight()/10));
		jp_south.add(jbtn_stick);
		jp_south.add(jbtn_auto);
		jp_south.add(jbtn_episode);
		jp_south.add(jbtn_clean);
		//인덱스 기본값 & 수정값
		jottoEvent = new JottoEvent(this);
		jbtn_stick.addActionListener(jottoEvent);
		jbtn_auto.addActionListener(jottoEvent);
		jbtn_episode.addActionListener(jottoEvent);
		jbtn_clean.addActionListener(jottoEvent);
		jbtn_auto_go.addActionListener(jottoEvent);
		jbtn_episode_go.addActionListener(jottoEvent);		
		jottoEvent.jottoStick = new JottoStick(this);
	}
	public void jottoStickView(){
		jp_stick.setVisible(true);
		jp_auto.setVisible(false);
		jp_episode.setVisible(false);
		jf_jotto.revalidate();
		jf_jotto.repaint();
		jf_jotto.add(jp_stick);
		jp_stick.setLayout(new BorderLayout());
		jp_stick.setBorder(BorderFactory.createEmptyBorder(60, 20, 20, 20));		
		jp_stick.add("Center", jp_stick_north);
		jp_stick.add("South", jp_stick_south);
		jp_stick_south.add(jtf_stick_num1);
		jp_stick_south.add(jtf_stick_num2);
		jp_stick_south.add(jtf_stick_num3);
		jp_stick_south.add(jtf_stick_num4);
		jp_stick_south.add(jtf_stick_num5);
		jp_stick_south.add(jtf_stick_num6);
		jp_stick_south.add(jtf_stick_num7);
		jp_stick_north.setLayout(new GridLayout(5,1));
		jp_stick_north.add(jp_stick_north_1);
		Font north_1 = new Font("@HY헤드라인M", Font.BOLD, 50);
		jp_stick_north_1.setFont(north_1);
		jp_stick_north.add(jp_stick_north_2);
		jp_stick_north.add(jp_stick_north_3);
		jp_stick_north.add(jp_stick_north_4);
		jp_stick_north.add(jp_stick_north_5);
		
		jp_stick_north_1.add(jl_stick_north_tf);
		jp_stick_north_2.add(jl_stick_north_episode);
		jp_stick_north_3.add(jl_stick_north_money);
		jp_stick_north_4.add(jl_stick_north_b1);
		jp_stick_north_4.add(jl_stick_north_b2);
		jp_stick_north_4.add(jl_stick_north_b3);
		jp_stick_north_4.add(jl_stick_north_b4);
		jp_stick_north_4.add(jl_stick_north_b5);
		jp_stick_north_4.add(jl_stick_north_b6);
		jp_stick_north_4.add(jl_stick_north_b7);
		jp_stick_north_5.add(jl_stick_north_date);
		
		jbtn_stick_go.addActionListener(jottoEvent);
		jp_stick_south.add(jbtn_stick_go);
	}
	public void jottoAutoView(){
		//중앙에 jp_stick 패널 추가, 컬러확인
		jp_stick.setVisible(false);
		jp_auto.setVisible(true);
		jp_episode.setVisible(false);
		jf_jotto.repaint();
		jt_auto.setShowVerticalLines(false);
		jt_auto.setShowHorizontalLines(false);
		jf_jotto.add(jp_auto);
		jp_auto.setLayout(new BorderLayout());
		jp_auto.add("Center", jp_auto_north);
		jp_auto.add("South", jp_auto_south);
		jp_auto_north.setBackground(new Color(30,144,255));
		jp_auto_south.setBackground(new Color(30,144,255));
		jt_auto.setBackground(new Color(30,144,255));
		auto_font = new Font("@HY헤드라인M", Font.BOLD, 30);
		//셀 가운데 정렬
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = jt_auto.getColumnModel();
		for(int i=0; i<cols_auto.length; i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);	
		}	
		jt_auto.setFont(auto_font);
		jt_auto.setForeground(Color.yellow);
		jp_auto_north.add(jt_auto);
		jt_auto.setRowHeight(40);
		lineCount = "1";
		lineCounts = new String[]{"1","2","3","4","5","6"};
		if(jcb_auto==null) {
		jcb_auto = new JComboBox<>(lineCounts);
		}
		jcb_auto.addItemListener(jottoEvent);
		jp_auto_south.add(jcb_auto);
		jp_auto_south.add(jbtn_auto_go);
		jf_jotto.revalidate();
	}
	public void jottoEpisodeView(){
		jp_stick.setVisible(false);
		jp_auto.setVisible(false);
		jp_episode.setVisible(true);
		jf_jotto.add("Center", jp_episode);
		jf_jotto.revalidate();
		jf_jotto.repaint();
		episodeCount = "전체";
		//"회차", "날짜", "당첨자수", "상금", "1", "2", "3", "4", "5", "6", "보너스"
		jt_episode.getColumn("회차").setPreferredWidth(5);
		jt_episode.getColumn("날짜").setPreferredWidth(55);
		jt_episode.getColumn("당첨자수").setPreferredWidth(30);
		jt_episode.getColumn("상금").setPreferredWidth(75);
		jt_episode.getColumn("1").setPreferredWidth(0);
		jt_episode.getColumn("2").setPreferredWidth(0);
		jt_episode.getColumn("3").setPreferredWidth(0);
		jt_episode.getColumn("4").setPreferredWidth(0);
		jt_episode.getColumn("5").setPreferredWidth(0);
		jt_episode.getColumn("6").setPreferredWidth(0);
		jt_episode.getColumn("보너스").setPreferredWidth(0);	
		//셀 가운데 정렬
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = jt_episode.getColumnModel();
		for(int i=0; i<cols_episode.length; i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);	
		}	
		jp_episode.setLayout(new BorderLayout());
		jp_episode.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		jp_episode.add("North", jp_episode_north);
		jp_episode.add("Center", jsp_episode);
		if(jcb_episode==null) {
			jcb_episode = new JComboBox<>(episodeCounts);	
		}
		jt_episode.setRowHeight(30);
		jcb_episode.addItemListener(jottoEvent);
		jp_episode_north.add(jcb_episode);
		jp_episode_north.add(jbtn_episode_go);
	}
	public void jottoStickViewClean(){
		jtf_stick_num1.setText("");
		jtf_stick_num2.setText("");
		jtf_stick_num3.setText("");
		jtf_stick_num4.setText("");
		jtf_stick_num5.setText("");
		jtf_stick_num6.setText("");
		jtf_stick_num7.setText("");
		jl_stick_north_tf.setText("");
		jl_stick_north_money.setText("");
		jl_stick_north_b1.setText("");
		jl_stick_north_b2.setText("");
		jl_stick_north_b3.setText("");
		jl_stick_north_b4.setText("");
		jl_stick_north_b5.setText("");
		jl_stick_north_b6.setText("");
		jl_stick_north_b7.setText("");
		jl_stick_north_episode.setText("");
		jl_stick_north_date.setText("");
		jf_jotto.revalidate();
		jf_jotto.repaint();
	}
	public void jottoAutoViewClean(){
		dtm_auto.setNumRows(0);
		jp_auto_south.remove(jcb_auto);
		jcb_auto = null;
		jcb_auto = new JComboBox<>(lineCounts);
		jp_auto_south.add(jcb_auto,0);
		jcb_auto.addItemListener(jottoEvent);
		jf_jotto.revalidate();
		jf_jotto.repaint();		
	}
	public void jottoEpisodeViewClean(){
		dtm_episode.setNumRows(0);
		jp_episode_north.remove(jcb_episode);
		jcb_episode = null;
		jcb_episode = new JComboBox<>(episodeCounts);
		jp_episode_north.add(jcb_episode, 0);
		jcb_episode.addItemListener(jottoEvent);
		jf_jotto.revalidate();
		jf_jotto.repaint();
	}
	public static void main(String[] args) {
		new JottoView();
	}
}
