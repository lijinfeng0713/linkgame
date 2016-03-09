package test;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


class LianLianKan implements ActionListener 
{ 
	private AudioClip audioClip;	
	//�����
	JFrame mainFrame;	  
	//�������
	Container thisContainer; 

	//����� 
	JPanel centerPanel,southPanel,northPanel,westPanel,eastPanel;
	
	//��Ϸ��ť����
	JButton diamondsButton[][] = new JButton[6][5];
	
	//��ʼ���˳������У����¿�ʼ��ť
	JButton exitButton,resetButton,newlyButton;
    //	JButton startButton;
	//������ǩ
	JLabel fractionLable=new JLabel("0");
	//ʱ���ǩ
	JLabel time=new JLabel("");
	
	//�ֱ��¼���α�ѡ�еİ�ť
    JButton fristButton,secondButton;
    
    //������Ϸ��ťλ�� 
    int grid[][] = new int[8][7]; 
    
    //�ж��Ƿ��а�ť������
    static boolean pressInformation=false;
    
    //��ѡ�е�������Ϸ��ť��λ������(x0,y0),(x,y)
    int x0=0,y0=0,x=0,y=0;
    //������ѡ�а�ť����Ӧ������
	int fristMsg=0,secondMsg=0;
	int i,j,k,n;
//	int s=0;
	
	final Timer t=new Timer();
	//Color b=Color.orange;
	Color b=new Color(38,38,38,255);
	//Color g= Color.cyan;
	Color g=new Color(83,83,83,255);
	Color m=Color.pink;
//	Color c=Color.blue;
	boolean flag;
	
	LianLianKan()
	{
		try {
			audioClip = Applet.newAudioClip(new URL("file:������.wav"));
			audioClip.loop();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��ʼ��
	public void init()
	  { //��������壬��ʾ"��ӭ���������������"
		mainFrame=new JFrame("��ӭ���������������"); 	
		thisContainer = mainFrame.getContentPane(); 
		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );//�رմ��ڣ���������
		mainFrame.setResizable(false); //���ô��ڴ�СΪ���ɸı�
//		thisContainer.setBackground(c);
		/**
		 * ��thisContainer����ΪCenter��South��North��������
		 * North������ӷ���
		 * Center���������Ϸ��ť
		 * South��������˳������С���һ�ֵȰ�ť
		 */
		thisContainer.setLayout(new BorderLayout()); 
		centerPanel=new JPanel();
		centerPanel.setBackground(b);
		southPanel=new JPanel();
		southPanel.setBackground(g);
		northPanel=new JPanel(new GridLayout(2,0) ); 
		northPanel.setBackground(m);
		westPanel=new JPanel();
		westPanel.setBackground(m);
		eastPanel=new JPanel();
		eastPanel.setBackground(m);
		thisContainer.add(centerPanel,"Center"); 
		thisContainer.add(southPanel,"South"); 
		thisContainer.add(northPanel,"North"); 
		
		JPanel panel1=new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));//���������
		JPanel panel2=new JPanel(new GridLayout(1,4) );

		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(g);
		JMenu choosingMenu = new JMenu("ģʽѡ��");
		choosingMenu.setForeground(Color.white);  //�����������ɫ
		JMenu settingMenu = new JMenu("����");
		settingMenu .setForeground(Color.white);
		JMenu helpMenu = new JMenu("����");
		helpMenu.setForeground(Color.white);
		JMenuItem aboutMenu = new JMenu("����");
		aboutMenu.setForeground(Color.white);
        
        menubar.add(choosingMenu);  //һ���˵�
        JMenuItem pattern1=new JMenuItem("����ģʽ"); //�����˵�
        JMenuItem pattern2=new JMenuItem("����ģʽ");
        choosingMenu.add(pattern1); 
        choosingMenu.add(pattern2);
        
        menubar.add(settingMenu);
        JMenuItem on=new JMenuItem("���ֿ�");
        JMenuItem off=new JMenuItem("���ֹ�");
        settingMenu.add(on);
        settingMenu.add(off);
        menubar.add(helpMenu);
        menubar.add(aboutMenu);
        
        choosingMenu.setFont(new Font("����",Font.BOLD,14));//���ò˵�����ʹ�С
        settingMenu.setFont(new Font("����",Font.BOLD,14)); 
        helpMenu.setFont(new Font("����",Font.BOLD,14)); 
        aboutMenu.setFont(new Font("����",Font.BOLD,14)); 
        
	    northPanel.add(panel1);
	    panel1.setBackground(g);
	    panel1.add(menubar);
	    northPanel.add(panel2,"South");
	    panel2.add(BorderLayout.WEST,new JLabel("      ʣ��ʱ��:"));
	    panel2.add(BorderLayout.EAST,time);
	    time.setFont(new Font("����",Font.BOLD,20));
	    panel2.add(BorderLayout.CENTER,new JLabel("���ĵ÷�:"));
	    panel2.add(BorderLayout.EAST,fractionLable);
	    panel2.add(fractionLable,"Center");
	    fractionLable.setFont(new Font("����",Font.BOLD,20));
	    panel2.setBackground(m);
        
        on.addActionListener(new ActionListener(){  //��on���뵽�����¼���

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			
				   audioClip.loop(); //���"���ֿ�"�˵�����Ƶѭ������
				}
        	});
        off.addActionListener(new ActionListener(){//��off���뵽�����¼���

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				audioClip.stop(); 
				}
        	});
        helpMenu.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null,"�����ģʽѡ�񡱽���ģʽѡ��\n"
						+ "��������á��ɽ��д򿪡��ر��������л�\n"
						+ "����������ʱ����������С���������\n"
						+ "������˳���������Ϸ���������һ�֡�������Ϸ"
						," ����", fristMsg);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			});
        aboutMenu.addMouseListener(new MouseListener() {			
        	
        	@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

				JOptionPane.showMessageDialog(null,"    ��ӭ������������������������1.0��"
						+ "�ɼ������ѧ�뼼��ѧԺ\n���zy1302��ѧ����翪����"
						+ "����׷���Լ������ڲ�����\n"
						+ "     Copyright@ 2014 Li Jinfeng,All Rights Resarved","����", fristMsg);        
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub   
				
			}
        });
        
   
		//��CenterPanel��������Ϊ���񲼾�
		centerPanel.setLayout(new GridLayout(6,5)); 
		for(int cols = 0;cols < 6;cols++)
		  { 
			for(int rows = 0;rows < 5;rows++ )
			  { 
				if(grid[cols+1][rows+1]!=0)
				  {//��ָ����ť���ͼƬ
					diamondsButton[cols][rows]=createImgBtn("./grid/"+grid[cols+1][rows+1]+".jpg", String.valueOf(grid[cols+1][rows+1]));
				  }
				else
				  {//��ָ����ťΪ��ʱ�����ַ�������ƬΪ������ƬΪ��
				    diamondsButton[cols][rows]=createImgBtn("",String.valueOf(grid[cols+1][rows+1]));
				  }
         
				diamondsButton[cols][rows].addActionListener(this); 
				centerPanel.add(diamondsButton[cols][rows]); 
			  } 
		   }
//		startButton=new JButton("��ʼ");
//		startButton.addActionListener(this);
		exitButton=new JButton("�˳�"); 
		exitButton.addActionListener(this); 
		resetButton=new JButton("����"); 
		resetButton.addActionListener(this); 
		newlyButton=new JButton("��һ��"); 
		newlyButton.addActionListener(this);
//		southPanel.add(startButton);
		southPanel.add(exitButton); 
		southPanel.add(resetButton); 
		southPanel.add(newlyButton); 
		/**
		*Ϊ�ñ�ǩ����һ���ı��ַ���
		*���ַ���Ϊ��ǩ�ַ�������ʾ���ı��ַ�����Ϊ�з��ŵ�ʮ��������Ϊ�������ַ���
		*/
		fractionLable.setText(String.valueOf(Integer.parseInt(fractionLable.getText()))); 
		mainFrame.setBounds(180,10,700,700);//����������λ�úʹ�С 
	//	mainFrame.setVisible(false);
		mainFrame.setVisible(true); 
//		centerPanel.setVisible(false);
//		timerDemo();
	  } 
	
	//��������ͼƬ�İ�ť
	public JButton createImgBtn(String ing,String txt)
	  {//���ݸ������Ƶ���Դ����һ�� ImageIcon��
		ImageIcon image = new ImageIcon(getClass().getResource(ing));
		JButton button = new JButton(txt,image);
		//�����ı��������֣������ͼ��Ĵ�ֱλ��Ϊ�׼�ˮƽλ��Ϊ����
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setHorizontalTextPosition(JButton.CENTER);
		return button;
	  }
	
	/**
	 *������Ϸ�е��������
	 *��������������ͬ
	 */
	public void randomBuild() 
	  { 
		int randoms,cols,rows; 
		for(int twins=1;twins<=15;twins++) 
		  { 
			randoms=(int)(Math.random()*25+1);//�������һ��1~25������ 
			for(int alike=1;alike<=2;alike++) 
			  { //����������������������ͬһ������
				cols=(int)(Math.random()*6+1); 
				rows=(int)(Math.random()*5+1); 
				while(grid[cols][rows]!=0)//��������ظ�������
				  { 
					cols=(int)(Math.random()*6+1); 
					rows=(int)(Math.random()*5+1); 
				  } 
				this.grid[cols][rows]=randoms; 
			   } 
		   } 
	  } 
	
	//����÷�
	public void fraction()
	{ 
		fractionLable.setText(String.valueOf(Integer.parseInt(fractionLable.getText())+100)); 
	} 
	
	//����
	public void reload()
	  { 
		int save[] = new int[30]; 
		int n=0,cols,rows; 
		int grid[][]= new int[8][7];
//		time.setVisible(false);
		for(int i=0;i<=6;i++) 
		   { 
			for(int j=0;j<=5;j++) 
			  { 
				if(this.grid[i][j]!=0) 
				  { //��δ��ȥ��ͼƬ�����ַ���save������
					save[n]=this.grid[i][j]; 
					n++; 
				  } 
			  } 
		   } 
		n=n-1; 
		this.grid=grid; //��grid����ȫ����ʼ��Ϊ��
		while(n>=0)
		  { //��ʣ�µ�����ͼƬ�������������������grid��
			cols=(int)(Math.random()*6+1); 
			rows=(int)(Math.random()*5+1); 
			while(grid[cols][rows]!=0)//��������ظ�������
			  { 
				cols=(int)(Math.random()*6+1); 
				rows=(int)(Math.random()*5+1); 
			  } 
			this.grid[cols][rows]=save[n]; 
			n--; 
		  } 
		mainFrame.setVisible(false); 
		mainFrame.setResizable(false);
		//����ť�����Ϣ��Ϊ��ʼ 
		pressInformation=false;
		init(); 
		for(int i = 0;i < 6;i++)
		  { 
	        for(int j = 0;j < 5;j++ )
	          { 
		        if(grid[i+1][j+1]==0)
		          {//����ȥ�İ�ť����Ϊ���ɼ� 
			         diamondsButton[i][j].setVisible(false); 
			      } 
		      }
		  } 
	  }

	//ѡ�а�ť��Ϣ�Ĵ洢�����
	public void estimateEven(int placeX,int placeY,JButton bz) 
	  {
		if(pressInformation==false)
		  {//�����һ����ťδ�����У��򽫴����İ�ť���긳��(x,y) 
	         x=placeX; 
	         y=placeY; 
	         fristMsg=grid[x][y];//����ť�ϵ����ָ���fristMsg
          	 fristButton=bz;//��(placeX��placey)λ���ϵİ�ťbz����fristButton
	         pressInformation=true;//����ť�����Ϣ����Ϊtrue 
		  }
		else
		  { /**
		    *�����һ����ť������ ,�򽫵�һ����ť���긳��(x0,y0)
		    *��fristButton��ť����secondButton��ť
		    *�������İ�ť���긳�����꣨x��y��
		    *�����������ͬ�İ�ť�ϵ��������ʱ�����remove()������ȥ
		    */
			x0=x; 
			y0=y; 
			secondMsg=fristMsg;//�����еĵ�һ����ť�ϵ����ָ����ڶ�����ť
			secondButton=fristButton;//����һ����ť�����ڶ�����ť
			x=placeX; 
			y=placeY; 
			fristMsg=grid[x][y];//���������İ�ť�ϵ����ָ���fristMsg
			fristButton=bz;//���������İ�ť����fristButton 
			if(fristMsg==secondMsg && secondButton!=fristButton)
			  { //�����������ͬ�İ�ť�ϵ��������ʱ����ȥ
				xiao(); 
			  } 
		  } 
	  } 
	
	//��ȥ
	public void xiao() 
	  { 
		if((x0==x &&(y0==y+1||y0==y-1)) || ((x0==x+1||x0==x-1)&&(y0==y)))
		  { //���������ť���ڣ�����ȥ 
		     remove(); 
	      } 
		else
		  { //���������ť������
			//�ж����һ��ťͬ�е����
			for (j=0;j<7;j++ ) 
			  { //�жϵ�һ����ťͬ���ĸ���ťΪ��
				if (grid[x0][j]==0)
				  { //���ͬ���пհ�ť
					if (y>j) 
					  { //����ڶ�����ť��y������ڿհ�ť��j����˵���հ�ť�ڵڶ���ť��� 
						for (i=y-1;i>=j;i--)
						  { //�жϵڶ���ť���ֱ��λ��(x,j)��û�а�ť 
							//���ж���հ�ťͬ�С���ڶ���ťͬ�е�λ�õ��ڶ���ť�����Ϊֹ��û�а�ť
							if (grid[x][i]!=0) 
							  {//����а�ť����k��ʼ��Ϊ�㣬��������ѭ�� 
								k=0; 
								break; 
							  }	 
							else
							  { //���û�а�ť
								k=1;//K=1˵��ͨ���˵�һ����֤  
							  } 
						   } 
						if (k==1)
						  { //k==1˵��������Ϊx,�������j��(y-1)��λ�ö�û�а�ť
							//��˵����հ�ťͬ�С���ڶ���ťͬ�е�λ�õ��ڶ���ť�����Ϊֹû�а�ť
							linePassOne(); 
						  } 
					   } 
					if (y<j)
					  { //����ڶ�����ť��y����С�ڿհ�ť��j����˵���հ�ť�ڵڶ���ť�ұ� 
						for (i=y+1;i<=j;i++)
						  { //�жϵڶ���ť�Ҳ�ֱ��λ��(x,j)��û�а�ť 
							if (grid[x][i]!=0)
							  { //����а�ť����k��ʼ��Ϊ�㣬��������ѭ��
								k=0; 
								break; 
							  } 
							else 
							  {//���û�а�ť
								k=1;
							  } 
						  } 
						if (k==1)
						  { //ͨ����һ����֤�����ڶ���ť�Ҳ�ֱ��λ��(x,j)û�а�ť
							linePassOne(); 
						  } 
					  } 
					if (y==j ) 
					  { //�ڶ�����ť��հ�ťͬ�У����ڶ���ť���һ��ťͬ��
						linePassOne(); 
					  } 
				  } 
				if (k==2) 
				  {//ͨ���ڶ���֤
					if (x0==x) 
					  { //������ť��ͬһ��
						remove(); 
					  } 
					if (x0<x) 
					  { //�ڶ�����ť�������ڵ�һ��ť�����е�����
						for (n=x0;n<=x-1;n++)
						  { //�жϿհ�ť�²�ֱ��λ��(x-1,j)��û�а�ť
							if (grid[n][j]!=0)
							  { //����а�ť����k��ʼ��Ϊ�㣬������ѭ��
								k=0; 
								break; 
							  } 
							if(grid[n][j]==0&&n==x-1) 
							  { //���ֱ��λ��(x-1,j)û�а�ť
								remove(); 
							  } 
						  } 
					  } 
					if (x0>x) 
					  { //�ڶ�����ť�������ڵ�һ��ť�����е�����
						for (n=x0;n>=x+1;n--)
						  { //�жϿհ�ť�ϲ�ֱ��λ��(x+1,j)��û�а�ť
							if (grid[n][j]!=0) 
							  { //����а�ť����k��ʼ��Ϊ�㣬������ѭ��
								k=0; 
								break; 
							  } 
							if(grid[n][j]==0&&n==x+1) 
							  { //���ֱ��λ��(x+1,j)û�а�ť
								remove(); 
							  } 
						   } 
					   } 
				   } 
			  } 
			//�ж����һ��ťͬ�����
			for (i=0;i<8;i++)
			  { //�жϵ�һ����ťͬ���ĸ���ťΪ��
				if (grid[i][y0]==0)
				  { //ͬ���пհ�ť
					if (x>i)
					  { //����ڶ�����ť��x������ڿհ�ť��i����˵���հ�ť�ڵڶ���ť�ϱ�
						for(j=x-1;j>=i;j--) 
						  {//�жϵڶ���ť�ϲ�ֱ��λ��(i,y)��û�а�ť 
							if (grid[j][y]!=0)
							  { //����а�ť����k��ʼ��Ϊ�㣬������ѭ��
								k=0; 
								break; 
							  } 
							else
							  { //���û�а�ť
								 k=1; //˵��ͨ����һ����֤
							  } 
						   } 
						if (k==1) 
						  { //�ڶ���ť�ϲ�ֱ��λ��(i,y)û�а�ť
							rowPassOne(); 
						  } 
					} 
					if (x<i)
					  { //�հ�ť�ڵڶ���ť�±�
						for (j=x+1;j<=i;j++) 
						  { //�жϵڶ���ť�²�ֱ��λ��(i,y)��û�а�ť
							if (grid[j][y]!=0)
							 { 
								k=0; 
								break; 
						 	 } 
							else
							  {
								k=1; 
							  } 
						  } 
						if (k==1)
						  { //�ڶ���ť�²�ֱ��λ��(i,y)û�а�ť
							rowPassOne(); 
						  } 
					 } 
					if (x==i) 
					 { //�ڶ���ť��հ�ťͬ��
						rowPassOne(); 
					 } 
				 } 
				if (k==2)
				  { //ͨ���ڶ�����֤
					if (y0==y) 
					  { //������ťͬ��
						remove(); 
					  } 
					if (y0<y) 
					  { //�ڶ���ť�������ڵ�һ��ť�����е�����
						for (n=y0;n<=y-1;n++)
						  { //�жϿհ�ť�Ҳ�ֱ��λ��(i,y-1)��û�а�ť
							if (grid[i][n]!=0)
							  { //����а�ť����k��ʼ��Ϊ�㣬������ѭ��
								k=0; 
								break; 
							  } 
							if(grid[i][n]==0&&n==y-1)
							  { //�հ�ť�Ҳ�ֱ��λ��(i,y-1)û�а�ť
								remove(); 
							  } 
						  } 
				       } 
					if (y0>y) 
					  {  //�ڶ���ť�������ڵ�һ��ť�����е�����
                    	for (n=y0;n>=y+1;n--) 
                    	  { //�жϿհ�ť���ֱ��λ��(i,y+1)��û�а�ť
		                    if (grid[i][n]!=0)
		                      { 
                    			k=0; 
	                       		break; 
		                      } 
                    		if(grid[i][n]==0&&n==y+1) 
                    		  { //�հ�ť���ֱ��λ��(i,y+1)û�а�ť
			                     remove(); 
		                      } 
	                      } 
			           } 
				} 
			} 
		} 
	}
	
	/**
	 * ��һ��ť��ͬ���д��ڿհ�ť
	 * �ж���ͬһ���пհ�ť���һ����ť֮���λ���Ƿ��а�ť���ڣ��������k=0,����k=2
	 * */
	public void linePassOne()
	  { 
		if (y0>j)
		  { //��һ��ť��ͬ�пհ�ť���ұ� 
			for (i=y0-1;i>=j;i--)
			  { //�жϵ�һ��ťͬ���հ�ť֮����û��ť 
	            if (grid[x0][i]!=0) 
	              { //����а�ť����k��ʼ��Ϊ�㣬������ѭ��
	            	k=0;   
		            break; 
               	  } 
	            else
	              {//���û�а�ť
	            	k=2;//K=2˵��ͨ���˵ڶ�����֤  
	              } 
			  } 
	      } 
		if (y0<j)
		  { //��һ��ť��ͬ�пհ�ť����� 
	         for (i=y0+1;i<=j;i++)
	           { //�жϵ�һ��ťͬ�Ҳ�հ�ť֮����û��ť 
		         if (grid[x0][i]!=0)
		          { //����а�ť����k��ʼ��Ϊ�㣬������ѭ��
			        k=0; 
			        break; 
		          } 
		         else
		          { 
		        	 k=2;
		          } 
		       } 
	       } 
     } 
	
	/**
	 * ��һ��ť��ͬ���д��ڿհ�ť
	 * �ж���ͬһ���пհ�ť���һ����ť֮���λ���Ƿ��а�ť���ڣ��������k=0,����k=2
	 * */
	public void rowPassOne()
	  { 
		if (x0>i) 
		  { //��һ��ť��ͬ�пհ�ť���±�
			for (j=x0-1;j>=i;j--)
			  { //�жϵ�һ��ťͬ�ϲ�հ�ť֮����û��ť
				if (grid[j][y0]!=0) 
				  { //����а�ť����k��ʼ��Ϊ�㣬������ѭ��
					k=0; 
					break; 
				  } 
				else 
				  {//���û�а�ť
	            	k=2;//K=2˵��ͨ���˵ڶ�����֤ 
				  } 
			  } 
	       } 
	if (x0<i)
	  { //��һ��ť��ͬ�пհ�ť���ϱ�
		for (j=x0+1;j<=i;j++) 
		  {  //�жϵ�һ��ťͬ�²�հ�ť֮����û��ť
	         if (grid[j][y0]!=0)
	           { 
		         k=0; 
		         break; 
	           } 
	         else
	           { 
	        	 k=2; 
	           } 
		   } 
	   } 
	  } 
	
	//����ͬ������ť��ȥ������Ϊ���ɼ�
	public void remove()
	  { 
		fristButton.setVisible(false); 
		secondButton.setVisible(false); 
		fraction(); //ÿ��ȥһ�԰�ť���100��
		/**
		 * �������ť��Ϣ��Ϊ��ʼ
		 * ��K�ͱ���ȥ��������ť�������ʼΪ��
	     */
		pressInformation=false; 
	    k=0; 
	    grid[x0][y0]=0; 
	    grid[x][y]=0; 
	  } 
	
	
	
	//ʵ���¼�����
	public void actionPerformed(ActionEvent e) 
	  {   
          if(e.getSource()==newlyButton)
		  {//�����һ�ְ�ť�¼� 
//			t.cancel();
			flag=true;
			int grid[][] = new int[8][7]; 
			this.grid = grid; //��grid�����ʼ��Ϊ��
			randomBuild(); //���»�ȡ15�������1~25������
			//��һ����Ϣ��Ϊ��ʼ
			mainFrame.setVisible(false); 
			pressInformation=false; 
//			fractionLable.setText("0");
			init();
//			t.cancel();
		    timerDemo();
//			time.setVisible(false);
//			timerDemo();
//			time.setVisible(true);
		  } 
		if(e.getSource()==exitButton)
		  {//����˳���ť�¼�
			audioClip.stop(); //����˳�����ֹ��Ƶ����
			System.exit(0); 
		  }
		if(e.getSource()==resetButton) 
			{//������а�ť�¼�
			  reload(); 
			}
		
	    for(int cols = 0;cols < 6;cols++)
	      { 
		    for(int rows = 0;rows < 5;rows++ )
		      { 
			     if(e.getSource()==diamondsButton[cols][rows]) 
				   {//�������ťʱ������estimateEven����
			    	 estimateEven(cols+1,rows+1,diamondsButton[cols][rows]); 
				   }
		      } 
	      } 
     } 

	//ʱ����ʾ
	public void timerDemo()
	     {
//		    final Timer timer=new Timer();
		    /**
		      * schedule(TimerTask task, Date firstTime, long period)
		      * ����ָ����������ָ����ʱ�俪ʼ�����ظ��Ĺ̶��ӳ�ִ��
		      **/
		        t.schedule(new TimerTask(){//����һ���µļ�ʱ������
				int s=300;//��ʱ��Ϊ300��
				public void run()
				  {//�˼�ʱ������Ҫִ�еĲ���
					if(flag==true)
					  {
						this.cancel();//ȡ���˼�ʱ������
						flag=false;
					  }
					if(s==0)
					  {//ʱ������,����Ϸ����
						time.setText("���ź�ʱ�䵽��");
						//this.cancel();
						//��ʱ������ʱ����δ����İ�ť��Ϊ���ɼ�
						for(int i=0;i<6;i++)
						  { 
							for(int j=0;j<5;j++ )
							  { 
								if(grid[i+1][j+1]!=0) 
								diamondsButton[i][j].setVisible(false); 
							  }
						  } 
							
//							JLabel l=new JLabel("Gime Over!",JLabel.CENTER);
//							JPanel Center,North,Sourth;
							
//							centerPanel.add(l);
							resetButton.setVisible(false);
							newlyButton.setVisible(false);
					}
				else
				  {
					time.setText(""+s--);
				  }
					}
					}, 1000,1000);
		       


	     }
}
public class LinkGame
{
	//������
	public static void main(String[] args) 
	  { 

		LianLianKan llk = new LianLianKan(); 
	    llk.randomBuild(); //��ȡ15�������1~25������
	    llk.init();
		llk.timerDemo();
		
	  } 
}


