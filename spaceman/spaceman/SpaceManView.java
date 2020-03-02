package spaceman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpaceManView extends JFrame{
	
	static int spaceManNowX = 0;
	static int spaceManNowY = 0;
	static int gamePlaying = 0;	//0 은 게임전 //1은 게임중 // 2는 게임후
	

	static int sunNowX = 0;
	static int sunNowY = 0;
	
	int j;
	int k;

	JLabelStarImage jl_star = null;
	JLabelWallImage jl_wall = null;
	JLabelSpaceManImage jl_spaceMan = new JLabelSpaceManImage();
	JLabelSunImage jl_sun = new JLabelSunImage();
	
	ArrayList<JLabelWallImage> wallList = new ArrayList<>();
	ArrayList<JLabelStarImage> starList = new ArrayList<>();
	
	
	public SpaceManView() {
		initDisplay();
		viewInitDisplay();
		
	}
	public void initDisplay() {
		getContentPane().setBackground(Color.black);
		setSize(606, 630);
		setLayout(null);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public void viewInitDisplay() {
		JPanel jp_center = new JPanel();
		jp_center.setBounds(0,150,600,300);
		jp_center.setBackground(Color.black);
		jp_center.setOpaque(false);
	
		JPanel jp_south = new JPanel();
		jp_south.setLayout(new GridLayout(2,1));
		jp_south.setBounds(0,300,600,50);
		jp_south.setBackground(Color.black);
		jp_south.setOpaque(false); 
		
		JLabel jl_title = new JLabel("SpaceMan");
		jl_title.setHorizontalAlignment(JLabel.CENTER);
		jl_title.setForeground(Color.yellow);
		jl_title.setFont(new Font("나눔고딕", Font.BOLD, 115));
		jp_center.add(jl_title);
		
		JLabel jl_english = new JLabel("Press Any Key To continue");
		jl_english.setHorizontalAlignment(JLabel.CENTER);
		jl_english.setForeground(Color.white);
		jl_english.setFont(new Font("나눔고딕", Font.BOLD, 20));
		jp_south.add(jl_english);
		
		JLabel jl_kor = new JLabel("아무키나 누르세요.");
		jl_kor.setHorizontalAlignment(JLabel.CENTER);
		jl_kor.setForeground(Color.white);
		jl_kor.setFont(new Font("나눔고딕", Font.BOLD, 12));
		jp_south.add(jl_kor);
		
		JPanelBack jp_b = new JPanelBack();
		jp_b.setBounds(0,0,600,600);

		add(jp_south);
		add(jp_center);
		add(jp_b);
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int crash = 0;
				if(gamePlaying==0) {
					remove(jp_b);
					remove(jp_center);
					remove(jp_south);
					mainViewInitDisplay();
					revalidate();
					repaint();
					gamePlaying = 1;
				} else if(gamePlaying==1) {
					if(e.getKeyCode()==37) {
						for(int i = 0; i<wallList.size();i++) {
							for(int j=0; j<38; j++) {
								for(int k=0; k<38; k++) {
									int wallLX = (int)wallList.get(i).getX();
									int wallLY = (int)wallList.get(i).getY();
									int wallRX = j+(int)wallList.get(i).getX();
									int wallRY = j+(int)wallList.get(i).getY();
									int spaceManLX = jl_spaceMan.getX();
									int spaceManLY = jl_spaceMan.getY();
									int spaceManRX = k+jl_spaceMan.getX();
									int spaceManRY = k+jl_spaceMan.getY();
									 
									if(spaceManLX-40==wallLX) {
										if(wallRY==spaceManRY) {
											crash = 1;
											return;
										} else {
											crash = 0;
										}										
									}
								}
							}
						}
						if(crash==1) {
							return;
						} else if(crash==0) {
							if(spaceManNowX-40==0) {
								return;
							} else {
								jl_spaceMan.setBounds(spaceManNowX-=2, spaceManNowY, 40, 40);	
							}
							
						}
						for(int i = 0; i<starList.size();i++) {
							for(int j=0; j<38; j++) {
								for(int k=0; k<38; k++) {
									int starLX = (int)starList.get(i).getX();
									int starLY = (int)starList.get(i).getY();
									int starRX = j+(int)starList.get(i).getX();
									int starRY = j+(int)starList.get(i).getY();
									int spaceManLX = jl_spaceMan.getX();
									int spaceManLY = jl_spaceMan.getY();
									int spaceManRX = k+jl_spaceMan.getX();
									int spaceManRY = k+jl_spaceMan.getY();
									if(spaceManLX-30==starLX) {
										if(starRY==spaceManRY) {
											remove(starList.get(i));
											starList.remove(i);
											revalidate();
											repaint();
											return;
										}	
									}
								}
							}
						}
					} else if(e.getKeyCode()==38) {
						for(int i = 0; i<wallList.size();i++) {
							for(int j=0; j<38; j++) {
								for(int k=0; k<38; k++) {
									int wallLX = (int)wallList.get(i).getX();
									int wallLY = (int)wallList.get(i).getY();
									int wallRX = j+(int)wallList.get(i).getX();
									int wallRY = j+(int)wallList.get(i).getY();
									int spaceManLX = jl_spaceMan.getX();
									int spaceManLY = jl_spaceMan.getY();
									int spaceManRX = k+jl_spaceMan.getX();
									int spaceManRY = k+jl_spaceMan.getY();
									if(spaceManLY-40==wallLY) {
										if(wallRX==spaceManRX) {
											crash = 1;
											return;
										} else {
											crash = 0;
										}										
									}
								}
							}
						}
						if(crash==1) {
							return;
						} else if(crash==0) {
							if(spaceManNowY-40==0) {
								return;
							} else {
								jl_spaceMan.setBounds(spaceManNowX, spaceManNowY-=2, 40, 40);
							}
						}
						for(int i = 0; i<starList.size();i++) {
							for(int j=0; j<38; j++) {
								for(int k=0; k<38; k++) {
									int starLX = (int)starList.get(i).getX();
									int starLY = (int)starList.get(i).getY();
									int starRX = j+(int)starList.get(i).getX();
									int starRY = j+(int)starList.get(i).getY();
									int spaceManLX = jl_spaceMan.getX();
									int spaceManLY = jl_spaceMan.getY();
									int spaceManRX = k+jl_spaceMan.getX();
									int spaceManRY = k+jl_spaceMan.getY();
									if(spaceManLY-30==starLY) {
										if(starRX==spaceManRX) {
											remove(starList.get(i));
											starList.remove(i);
											revalidate();
											repaint();
											return;
										}	
									}
								}
							}
						}
					} else if(e.getKeyCode()==39) {
						for(int i = 0; i<wallList.size();i++) {
							for(int j=0; j<38; j++) {
								for(int k=0; k<38; k++) {
									int wallLX = (int)wallList.get(i).getX();
									int wallLY = (int)wallList.get(i).getY();
									int wallRX = j+(int)wallList.get(i).getX();
									int wallRY = j+(int)wallList.get(i).getY();
									int spaceManLX = jl_spaceMan.getX();
									int spaceManLY = jl_spaceMan.getY();
									int spaceManRX = k+jl_spaceMan.getX();
									int spaceManRY = k+jl_spaceMan.getY();
									 
									if(spaceManLX+40==wallLX) {
										if(wallRY==spaceManRY) {
											crash = 1;
											return;
										} else {
											crash = 0;
										}										
									}
								}
							}
						}
						if(crash==1) {
							return;
						} else if(crash==0) {
							if(spaceManNowX+40==560) {
								return;
							} else {
								jl_spaceMan.setBounds(spaceManNowX+=2, spaceManNowY, 40, 40);
							}
						}
						for(int i = 0; i<starList.size();i++) {
							for(int j=0; j<38; j++) {
								for(int k=0; k<38; k++) {
									int starLX = (int)starList.get(i).getX();
									int starLY = (int)starList.get(i).getY();
									int starRX = j+(int)starList.get(i).getX();
									int starRY = j+(int)starList.get(i).getY();
									int spaceManLX = jl_spaceMan.getX();
									int spaceManLY = jl_spaceMan.getY();
									int spaceManRX = k+jl_spaceMan.getX();
									int spaceManRY = k+jl_spaceMan.getY();
									if(spaceManLX+30==starLX) {
										if(starRY==spaceManRY) {
											remove(starList.get(i));
											starList.remove(i);
											revalidate();
											repaint();
											return;
										}	
									}
								}
							}
						}
					} else if(e.getKeyCode()==40) {
						for(int i = 0; i<wallList.size();i++) {
							for(int j=0; j<38; j++) {
								for(int k=0; k<38; k++) {
									int wallLX = (int)wallList.get(i).getX();
									int wallLY = (int)wallList.get(i).getY();
									int wallRX = j+(int)wallList.get(i).getX();
									int wallRY = j+(int)wallList.get(i).getY();
									int spaceManLX = jl_spaceMan.getX();
									int spaceManLY = jl_spaceMan.getY();
									int spaceManRX = k+jl_spaceMan.getX();
									int spaceManRY = k+jl_spaceMan.getY();
									if(spaceManLY+40==wallLY) {
										if(wallRX==spaceManRX) {
											crash = 1;
											return;
										} else {
											crash = 0;
										}										
									}
								}
							}
						}
						if(crash==1) {
							return;
						} else if(crash==0) {
							if(spaceManNowY+40==560) {
								return;
							} else {
								jl_spaceMan.setBounds(spaceManNowX, spaceManNowY+=2, 40, 40);
							}
						}
						for(int i = 0; i<starList.size();i++) {
							for(int j=0; j<38; j++) {
								for(int k=0; k<38; k++) {
									int starLX = (int)starList.get(i).getX();
									int starLY = (int)starList.get(i).getY();
									int starRX = j+(int)starList.get(i).getX();
									int starRY = j+(int)starList.get(i).getY();
									int spaceManLX = jl_spaceMan.getX();
									int spaceManLY = jl_spaceMan.getY();
									int spaceManRX = k+jl_spaceMan.getX();
									int spaceManRY = k+jl_spaceMan.getY();
									if(spaceManLY+30==starLY) {
										if(starRX==spaceManRX) {
											remove(starList.get(i));
											starList.remove(i);
											revalidate();
											repaint();
											return;
										}	
									}
								}
							}
						}
					}
				}
			}

		});
	}
	public void mainViewInitDisplay() {
		JLabelWallImage[] jl_outwall = new JLabelWallImage[56];
		for(int i = 0; i<56; i++) {
			jl_outwall[i] = new JLabelWallImage();
		}
		int outX = 0;
		int outY = 0;
		for(int i = 0; i<56;i++) {
			if(i<15) {
				jl_outwall[i].setBounds(outX, 0, 40, 40);
				outX+=40;
				add(jl_outwall[i]);
			} else if(i>=15 && i<41) {
				if(i==15) {
					outX = 0;
					outY = 40;
				}
				jl_outwall[i].setBounds(outX, outY, 40, 40);
				add(jl_outwall[i]);
				++i;
				outX = 560;
				jl_outwall[i].setBounds(outX, outY, 40, 40);
				add(jl_outwall[i]);
				outX= 0;
				outY+=40;
			}else if(i>40 && i<56) {
				jl_outwall[i].setBounds(outX, 560, 40, 40);
				outX+=40;
				add(jl_outwall[i]);
			}
		}	
		Random random = new Random();
		startMainInBuild(0);
		//startMainInBuild(random.nextInt(2)); 		
		
		
	}
	
	
	private void startMainInBuild(int mapChoice) {
		j = 0;
		k = 0;
		//빌드 메소드
		sunBuild(jl_sun, 1, 6, 6);
		spaceManBuild(jl_spaceMan, 1, 6, 12);
		
		if(mapChoice==0) {
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 11);
			j=0;
			k++;
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 3);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 2);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			j=0;
			k++;
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 3);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 3);
			starBuild(jl_star, 3);
			j=0;
			k++;
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 9);
			j=0;
			k++;
			starBuild(jl_star, 3);
			wallBuild(jl_wall, 3);
			j++;
			wallBuild(jl_wall, 3);
			starBuild(jl_star, 3);
			j=0;
			k++;
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 2);
			j=9;
			wallBuild(jl_wall, 2);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			j=0;
			k++;
			starBuild(jl_star, 3);
			wallBuild(jl_wall, 1);
			j=9;
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 3);
			j=0;
			k++;
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 11);
			starBuild(jl_star, 1);
			j=0;
			k++;
			starBuild(jl_star, 6);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 3);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 2);
			j=0;
			k++;
			wallBuild(jl_wall, 4);
			starBuild(jl_star, 6);
			wallBuild(jl_wall, 2);
			starBuild(jl_star, 1);
			j=0;
			k++;
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 2);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 2);
			j=0;
			k++;
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 2);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 2);
			j=0;
			k++;
			starBuild(jl_star, 5);
			wallBuild(jl_wall, 1);
			j=7;
			wallBuild(jl_wall, 1);
			wallBuild(jl_wall, 1);
			starBuild(jl_star, 3);
			wallBuild(jl_wall, 1);			
		} else if(mapChoice==1) {
			
		} else if(mapChoice==2) {
			
		}
		
		revalidate();
		
		Timer sun_t = new Timer();
		TimerTask sun_t_t= new TimerTask() {
			int i = 0;
			@Override
			public void run() {
				int x = sunNowX;
				int y = sunNowY;
				if(mapChoice==0&&gamePlaying==1) {
					crashSun();
					starClean();
					revalidate();
					repaint();
					if(i==0) {
						jl_sun.setBounds(sunNowX, sunNowY-=2, 40, 40);
						if(sunNowY<=40) {i=1;}
					} else if(i==1) {
						jl_sun.setBounds(sunNowX+=2, sunNowY, 40, 40);
						if(sunNowX>=520) {i=2;}
					} else if(i==2) {
						jl_sun.setBounds(sunNowX-=2, sunNowY, 40, 40);
						if(sunNowX<=480) {i=3;}
					} else if(i==3) {
						jl_sun.setBounds(sunNowX, sunNowY+=2, 40, 40);
						if(sunNowY>=280) {i=4;}
					} else if(i==4) {
						jl_sun.setBounds(sunNowX+=2, sunNowY, 40, 40);
						if(sunNowX>=520) {i=5;}
					} else if(i==5) {
						jl_sun.setBounds(sunNowX, sunNowY+=2, 40, 40);
						if(sunNowY>=480) {i=6;}
					} else if(i==6) {
						jl_sun.setBounds(sunNowX-=2, sunNowY, 40, 40);
						if(sunNowX<=480) {i=7;}
					} else if(i==7) {
						jl_sun.setBounds(sunNowX, sunNowY+=2, 40, 40);
						if(sunNowY>=520) {i=8;}
					} else if(i==8) {
						jl_sun.setBounds(sunNowX-=2, sunNowY, 40, 40);
						if(sunNowX<=400) {i=9;}
					} else if(i==9) {
						jl_sun.setBounds(sunNowX, sunNowY-=2, 40, 40);
						if(sunNowY<=400) {i=10;}
					} else if(i==10) {
						jl_sun.setBounds(sunNowX-=2, sunNowY, 40, 40);
						if(sunNowX<=200) {i=11;}
					} else if(i==11) {
						jl_sun.setBounds(sunNowX, sunNowY-=2, 40, 40);
						if(sunNowY<=360) {i=12;}
					} else if(i==12) {
						jl_sun.setBounds(sunNowX-=2, sunNowY, 40, 40);
						if(sunNowX<=40) {i=13;}
					} else if(i==13) {
						jl_sun.setBounds(sunNowX, sunNowY-=2, 40, 40);
						if(sunNowY<=280) {i=14;}
					} else if(i==14) {
						jl_sun.setBounds(sunNowX+=2, sunNowY, 40, 40);
						if(sunNowX>=80) {i=15;}
					} else if(i==15) {
						jl_sun.setBounds(sunNowX, sunNowY-=2, 40, 40);
						if(sunNowY<=200) {i=16;}
					} else if(i==16) {
						jl_sun.setBounds(sunNowX+=2, sunNowY, 40, 40);
						if(sunNowX>=120) {i=17;}
					} else if(i==17) {
						jl_sun.setBounds(sunNowX, sunNowY-=2, 40, 40);
						if(sunNowY<=40) {i=18;}
					} else if(i==18) {
						jl_sun.setBounds(sunNowX+=2, sunNowY, 40, 40);
						if(sunNowX<=220) {i=0;}
					}
				}
				revalidate();
				repaint();
			}
			
		};
		sun_t.schedule(sun_t_t,0, 50);
	}
	
	public void theEndInitDisplay(){
		JPanel jp_center = new JPanel();
		jp_center.setLayout(new GridLayout(2,1));
		jp_center.setBounds(0,180,600,300);
		jp_center.setBackground(Color.black);
		jp_center.setOpaque(false);
	
		JPanel jp_south = new JPanel();
		jp_south.setLayout(new GridLayout(2,1));
		jp_south.setBounds(0,330,600,50);
		jp_south.setBackground(Color.black);
		jp_south.setOpaque(false); 
		
		JLabel jl_title = new JLabel("태양과 충돌");
		jl_title.setHorizontalAlignment(JLabel.CENTER);
		jl_title.setForeground(Color.yellow);
		jl_title.setFont(new Font("나눔고딕", Font.BOLD, 105));
		jp_center.add(jl_title);
		
		
		JLabel jl_english = new JLabel("The End");
		jl_english.setHorizontalAlignment(JLabel.CENTER);
		jl_english.setForeground(Color.white);
		jl_english.setFont(new Font("나눔고딕", Font.BOLD, 20));
		jp_south.add(jl_english);
		
		JLabel jl_kor = new JLabel("게임 끝");
		jl_kor.setHorizontalAlignment(JLabel.CENTER);
		jl_kor.setForeground(Color.white);
		jl_kor.setFont(new Font("나눔고딕", Font.BOLD, 12));
		jp_south.add(jl_kor);
		
		JPanelBack jp_b = new JPanelBack();
		jp_b.setBounds(0,0,600,600);

		add(jp_south);
		add(jp_center);
		add(jp_b);
	}
	
	public void victoryInitDisplay(){
		JPanel jp_center = new JPanel();
		jp_center.setLayout(new GridLayout(2,1));
		jp_center.setBounds(0,180,600,300);
		jp_center.setBackground(Color.black);
		jp_center.setOpaque(false);
	
		JPanel jp_south = new JPanel();
		jp_south.setLayout(new GridLayout(2,1));
		jp_south.setBounds(0,330,600,50);
		jp_south.setBackground(Color.black);
		jp_south.setOpaque(false); 
		
		JLabel jl_title = new JLabel("별모두파괴");
		jl_title.setHorizontalAlignment(JLabel.CENTER);
		jl_title.setForeground(Color.yellow);
		jl_title.setFont(new Font("나눔고딕", Font.BOLD, 105));
		jp_center.add(jl_title);
		
		
		JLabel jl_english = new JLabel("Victory");
		jl_english.setHorizontalAlignment(JLabel.CENTER);
		jl_english.setForeground(Color.white);
		jl_english.setFont(new Font("나눔고딕", Font.BOLD, 20));
		jp_south.add(jl_english);
		
		JLabel jl_kor = new JLabel("승리");
		jl_kor.setHorizontalAlignment(JLabel.CENTER);
		jl_kor.setForeground(Color.white);
		jl_kor.setFont(new Font("나눔고딕", Font.BOLD, 12));
		jp_south.add(jl_kor);
		
		JPanelBack jp_b = new JPanelBack();
		jp_b.setBounds(0,0,600,600);

		add(jp_south);
		add(jp_center);
		add(jp_b);
	}
	
	public void wallBuild(JLabelWallImage jl_wall, int count){
		for(int i = 0; i<count; i++) {
			jl_wall = new JLabelWallImage();
			jl_wall.setBounds(j*40+40, k*40+40, 40, 40);
			wallList.add(jl_wall);
			j++;
			add(jl_wall);
		}
	}
	public void starBuild(JLabelStarImage jl_star, int count){
		for(int i = 0; i<count; i++) {
			jl_star = new JLabelStarImage(); 
			jl_star.setBounds(j*40+40, k*40+40, 40, 40);
			starList.add(jl_star);
			int now = starList.size()-1;
			j++;
			add(starList.get(now));
		}
	}
	public void spaceManBuild(JLabelSpaceManImage jl_spaceMan, int count, int j, int k){
		for(int i = 0; i<count; i++) {
			spaceManNowX = j*40+40;
			spaceManNowY = k*40+40;
			jl_spaceMan.setBounds(j++*40+40, k*40+40, 40, 40);
			add(jl_spaceMan);
		}
	}
	public void sunBuild(JLabelSunImage jl_sun, int count, int j, int k){
		for(int i = 0; i<count; i++) {
			sunNowX = j*40+40;
			sunNowY = k*40+40;
			jl_sun.setBounds(j++*40+40, k*40+40, 40, 40);
			add(jl_sun);
		}
	}
	class JPanelBack extends JPanel{
		public void paint(Graphics g) {
			Image img = null;
			try {
				img = ImageIO.read(new File("E:\\workspace.java\\dev_java\\src\\spaceman\\backimage.png"));	
			} catch (Exception e) {
				e.printStackTrace();
			}
			g.drawImage(img, 0, 0, 600, 600, null);
		}
	}

	class JLabelWallImage extends JLabel{
		public void paint(Graphics g) {
			Image img = null;
			try {
				img = ImageIO.read(new File("E:\\workspace.java\\dev_java\\src\\spaceman\\wallimage.png"));	
			} catch (Exception e) {
				e.printStackTrace();
			}
			g.drawImage(img, 0, 0, 40, 40, null);
		}
	}	
	
	class JLabelSunImage extends JLabel{
		public void paint(Graphics g) {
			Image img = null;
			try {
				img = ImageIO.read(new File("E:\\workspace.java\\dev_java\\src\\spaceman\\sunimage.png"));	
			} catch (Exception e) {
				e.printStackTrace();
			}
			g.drawImage(img, 0, 0, 40, 40, null);
		}
	}	
	
	class JLabelSpaceManImage extends JLabel{
		public void paint(Graphics g) {
			Image img = null;
			try {
				img = ImageIO.read(new File("E:\\workspace.java\\dev_java\\src\\spaceman\\spacemanimage2.png"));	
			} catch (Exception e) {
				e.printStackTrace();
			}
			g.drawImage(img, 0, 0, 40, 40, null);
		}
	}
	
	class JLabelStarImage extends JLabel{
		public void paint(Graphics g) {
			Image img = null;
			try {
				img = ImageIO.read(new File("E:\\workspace.java\\dev_java\\src\\spaceman\\starimage.png"));	
			} catch (Exception e) {
				e.printStackTrace();
			}
			g.drawImage(img, 0, 0, 40, 40, null);
		}
	}
	
	private void starClean() {
		if(starList.size()==0) {
		revalidate();
		repaint();
		gamePlaying = 2;
		getContentPane().removeAll();
		victoryInitDisplay();
		}
	}
	
	
	private void crashSun() {
		for(int i = 0; i<38;i++) {
			for(int j=0; j<38; j++) {
				for(int k=0; k<38; k++) {
					int sunLX = jl_sun.getX();
					int sunLY = jl_sun.getY();
					int sunRX = j+jl_sun.getX();
					int sunRY = j+jl_sun.getY();
					int spaceManLX = jl_spaceMan.getX();
					int spaceManLY = jl_spaceMan.getY();
					int spaceManRX = k+jl_spaceMan.getX();
					int spaceManRY = k+jl_spaceMan.getY();
					if(spaceManLY+30==sunLY) {
						if(sunRX==spaceManRX) {
							gamePlaying = 2;
							getContentPane().removeAll();
							theEndInitDisplay();
							return;
						}
					} else if(spaceManLY-30==sunLY) {
						if(sunRX==spaceManRX) {
							gamePlaying = 2;
							getContentPane().removeAll();
							theEndInitDisplay();
							return;
						}
					} else if(spaceManLX+30==sunLX) {
						if(sunRY==spaceManRY) {
							gamePlaying = 2;
							getContentPane().removeAll();
							theEndInitDisplay();
							return;
						}
					} else if(spaceManLX-30==sunLX) {
						if(sunRY==spaceManRY){
							gamePlaying = 2;
							getContentPane().removeAll();
							theEndInitDisplay();
							return;
						}
					}
				}	
			}
		}
	}
	
	public static void main(String[] args) {		
		SpaceManView sv = new SpaceManView();
	}
}
