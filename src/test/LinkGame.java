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
	//主面板
	JFrame mainFrame;	  
	//面板容器
	Container thisContainer; 

	//子面板 
	JPanel centerPanel,southPanel,northPanel,westPanel,eastPanel;
	
	//游戏按钮数组
	JButton diamondsButton[][] = new JButton[6][5];
	
	//开始，退出，重列，重新开始按钮
	JButton exitButton,resetButton,newlyButton;
    //	JButton startButton;
	//分数标签
	JLabel fractionLable=new JLabel("0");
	//时间标签
	JLabel time=new JLabel("");
	
	//分别记录两次被选中的按钮
    JButton fristButton,secondButton;
    
    //储存游戏按钮位置 
    int grid[][] = new int[8][7]; 
    
    //判断是否有按钮被击中
    static boolean pressInformation=false;
    
    //被选中的两个游戏按钮的位置坐标(x0,y0),(x,y)
    int x0=0,y0=0,x=0,y=0;
    //两个被选中按钮上相应的数字
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
			audioClip = Applet.newAudioClip(new URL("file:琵琶语.wav"));
			audioClip.loop();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//初始化
	public void init()
	  { //创建主面板，提示"欢迎来到锋哥连连看！"
		mainFrame=new JFrame("欢迎来到锋哥连连看！"); 	
		thisContainer = mainFrame.getContentPane(); 
		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );//关闭窗口，结束程序
		mainFrame.setResizable(false); //设置窗口大小为不可改变
//		thisContainer.setBackground(c);
		/**
		 * 把thisContainer划分为Center、South、North三个区域
		 * North区域添加分数
		 * Center区域添加游戏按钮
		 * South区域添加退出、重列、下一局等按钮
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
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));//设置左对齐
		JPanel panel2=new JPanel(new GridLayout(1,4) );

		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(g);
		JMenu choosingMenu = new JMenu("模式选择");
		choosingMenu.setForeground(Color.white);  //设置字体的颜色
		JMenu settingMenu = new JMenu("设置");
		settingMenu .setForeground(Color.white);
		JMenu helpMenu = new JMenu("帮助");
		helpMenu.setForeground(Color.white);
		JMenuItem aboutMenu = new JMenu("关于");
		aboutMenu.setForeground(Color.white);
        
        menubar.add(choosingMenu);  //一级菜单
        JMenuItem pattern1=new JMenuItem("基本模式"); //二级菜单
        JMenuItem pattern2=new JMenuItem("高手模式");
        choosingMenu.add(pattern1); 
        choosingMenu.add(pattern2);
        
        menubar.add(settingMenu);
        JMenuItem on=new JMenuItem("音乐开");
        JMenuItem off=new JMenuItem("音乐关");
        settingMenu.add(on);
        settingMenu.add(off);
        menubar.add(helpMenu);
        menubar.add(aboutMenu);
        
        choosingMenu.setFont(new Font("宋体",Font.BOLD,14));//设置菜单字体和大小
        settingMenu.setFont(new Font("宋体",Font.BOLD,14)); 
        helpMenu.setFont(new Font("宋体",Font.BOLD,14)); 
        aboutMenu.setFont(new Font("宋体",Font.BOLD,14)); 
        
	    northPanel.add(panel1);
	    panel1.setBackground(g);
	    panel1.add(menubar);
	    northPanel.add(panel2,"South");
	    panel2.add(BorderLayout.WEST,new JLabel("      剩余时间:"));
	    panel2.add(BorderLayout.EAST,time);
	    time.setFont(new Font("宋体",Font.BOLD,20));
	    panel2.add(BorderLayout.CENTER,new JLabel("您的得分:"));
	    panel2.add(BorderLayout.EAST,fractionLable);
	    panel2.add(fractionLable,"Center");
	    fractionLable.setFont(new Font("宋体",Font.BOLD,20));
	    panel2.setBackground(m);
        
        on.addActionListener(new ActionListener(){  //将on加入到监听事件中

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			
				   audioClip.loop(); //点击"音乐开"菜单，音频循环播放
				}
        	});
        off.addActionListener(new ActionListener(){//将off加入到监听事件中

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				audioClip.stop(); 
				}
        	});
        helpMenu.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null,"点击“模式选择”进行模式选择\n"
						+ "点击“设置”可进行打开、关闭声音的切换\n"
						+ "当遇到死局时，点击“重列”进行重排\n"
						+ "点击“退出”结束游戏，点击“下一局”继续游戏"
						," 帮助", fristMsg);
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

				JOptionPane.showMessageDialog(null,"    欢迎来到锋哥连连看！锋哥连连看1.0版"
						+ "由计算机科学与技术学院\n软件zy1302班学生锋哥开发，"
						+ "界面追求简约风格，易于操作！\n"
						+ "     Copyright@ 2014 Li Jinfeng,All Rights Resarved","关于", fristMsg);        
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
        
   
		//把CenterPanel区域设置为网格布局
		centerPanel.setLayout(new GridLayout(6,5)); 
		for(int cols = 0;cols < 6;cols++)
		  { 
			for(int rows = 0;rows < 5;rows++ )
			  { 
				if(grid[cols+1][rows+1]!=0)
				  {//给指定按钮添加图片
					diamondsButton[cols][rows]=createImgBtn("./grid/"+grid[cols+1][rows+1]+".jpg", String.valueOf(grid[cols+1][rows+1]));
				  }
				else
				  {//当指定按钮为空时传空字符串即照片为传的照片为空
				    diamondsButton[cols][rows]=createImgBtn("",String.valueOf(grid[cols+1][rows+1]));
				  }
         
				diamondsButton[cols][rows].addActionListener(this); 
				centerPanel.add(diamondsButton[cols][rows]); 
			  } 
		   }
//		startButton=new JButton("开始");
//		startButton.addActionListener(this);
		exitButton=new JButton("退出"); 
		exitButton.addActionListener(this); 
		resetButton=new JButton("重列"); 
		resetButton.addActionListener(this); 
		newlyButton=new JButton("下一局"); 
		newlyButton.addActionListener(this);
//		southPanel.add(startButton);
		southPanel.add(exitButton); 
		southPanel.add(resetButton); 
		southPanel.add(newlyButton); 
		/**
		*为该标签设置一个文本字符串
		*该字符串为标签字符串所显示的文本字符串作为有符号的十进制整数为参数的字符串
		*/
		fractionLable.setText(String.valueOf(Integer.parseInt(fractionLable.getText()))); 
		mainFrame.setBounds(180,10,700,700);//设置主面板的位置和大小 
	//	mainFrame.setVisible(false);
		mainFrame.setVisible(true); 
//		centerPanel.setVisible(false);
//		timerDemo();
	  } 
	
	//创建带有图片的按钮
	public JButton createImgBtn(String ing,String txt)
	  {//根据给定名称的资源创建一个 ImageIcon。
		ImageIcon image = new ImageIcon(getClass().getResource(ing));
		JButton button = new JButton(txt,image);
		//设置文本（即数字）相对于图标的垂直位置为底及水平位置为中心
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setHorizontalTextPosition(JButton.CENTER);
		return button;
	  }
	
	/**
	 *产生游戏中的随机数字
	 *数字至少两两相同
	 */
	public void randomBuild() 
	  { 
		int randoms,cols,rows; 
		for(int twins=1;twins<=15;twins++) 
		  { 
			randoms=(int)(Math.random()*25+1);//随机产生一个1~25的数字 
			for(int alike=1;alike<=2;alike++) 
			  { //产生两个随机的坐标来存放同一个数字
				cols=(int)(Math.random()*6+1); 
				rows=(int)(Math.random()*5+1); 
				while(grid[cols][rows]!=0)//避免出现重复的坐标
				  { 
					cols=(int)(Math.random()*6+1); 
					rows=(int)(Math.random()*5+1); 
				  } 
				this.grid[cols][rows]=randoms; 
			   } 
		   } 
	  } 
	
	//计算得分
	public void fraction()
	{ 
		fractionLable.setText(String.valueOf(Integer.parseInt(fractionLable.getText())+100)); 
	} 
	
	//重列
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
				  { //将未消去的图片的数字放在save数组中
					save[n]=this.grid[i][j]; 
					n++; 
				  } 
			  } 
		   } 
		n=n-1; 
		this.grid=grid; //把grid数组全部初始化为零
		while(n>=0)
		  { //将剩下的所有图片数字重新随机放在数组grid中
			cols=(int)(Math.random()*6+1); 
			rows=(int)(Math.random()*5+1); 
			while(grid[cols][rows]!=0)//避免出现重复的坐标
			  { 
				cols=(int)(Math.random()*6+1); 
				rows=(int)(Math.random()*5+1); 
			  } 
			this.grid[cols][rows]=save[n]; 
			n--; 
		  } 
		mainFrame.setVisible(false); 
		mainFrame.setResizable(false);
		//将按钮点击信息归为初始 
		pressInformation=false;
		init(); 
		for(int i = 0;i < 6;i++)
		  { 
	        for(int j = 0;j < 5;j++ )
	          { 
		        if(grid[i+1][j+1]==0)
		          {//将消去的按钮设置为不可见 
			         diamondsButton[i][j].setVisible(false); 
			      } 
		      }
		  } 
	  }

	//选中按钮信息的存储与操作
	public void estimateEven(int placeX,int placeY,JButton bz) 
	  {
		if(pressInformation==false)
		  {//如果第一个按钮未被击中，则将传来的按钮坐标赋给(x,y) 
	         x=placeX; 
	         y=placeY; 
	         fristMsg=grid[x][y];//将按钮上的数字赋给fristMsg
          	 fristButton=bz;//将(placeX，placey)位置上的按钮bz赋给fristButton
	         pressInformation=true;//将按钮点击信息设置为true 
		  }
		else
		  { /**
		    *如果第一个按钮被击中 ,则将第一个按钮坐标赋给(x0,y0)
		    *将fristButton按钮赋给secondButton按钮
		    *将传来的按钮坐标赋给坐标（x，y）
		    *如果当两个不同的按钮上的数字相等时则调用remove()函数消去
		    */
			x0=x; 
			y0=y; 
			secondMsg=fristMsg;//将击中的第一个按钮上的数字赋给第二个按钮
			secondButton=fristButton;//将第一个按钮赋给第二个按钮
			x=placeX; 
			y=placeY; 
			fristMsg=grid[x][y];//将传过来的按钮上的数字赋给fristMsg
			fristButton=bz;//将传过来的按钮赋给fristButton 
			if(fristMsg==secondMsg && secondButton!=fristButton)
			  { //如果当两个不同的按钮上的数字相等时则消去
				xiao(); 
			  } 
		  } 
	  } 
	
	//消去
	public void xiao() 
	  { 
		if((x0==x &&(y0==y+1||y0==y-1)) || ((x0==x+1||x0==x-1)&&(y0==y)))
		  { //如果两个按钮相邻，则消去 
		     remove(); 
	      } 
		else
		  { //如果两个按钮不相邻
			//判断与第一按钮同行的情况
			for (j=0;j<7;j++ ) 
			  { //判断第一个按钮同行哪个按钮为空
				if (grid[x0][j]==0)
				  { //如果同行有空按钮
					if (y>j) 
					  { //如果第二个按钮的y坐标大于空按钮的j坐标说明空按钮在第二按钮左边 
						for (i=y-1;i>=j;i--)
						  { //判断第二按钮左侧直到位置(x,j)有没有按钮 
							//即判断与空按钮同列、与第二按钮同行的位置到第二按钮的左侧为止有没有按钮
							if (grid[x][i]!=0) 
							  {//如果有按钮，则将k初始化为零，并将跳出循环 
								k=0; 
								break; 
							  }	 
							else
							  { //如果没有按钮
								k=1;//K=1说明通过了第一次验证  
							  } 
						   } 
						if (k==1)
						  { //k==1说明横坐标为x,纵坐标从j到(y-1)的位置都没有按钮
							//即说明与空按钮同列、与第二按钮同行的位置到第二按钮的左侧为止没有按钮
							linePassOne(); 
						  } 
					   } 
					if (y<j)
					  { //如果第二个按钮的y坐标小于空按钮的j坐标说明空按钮在第二按钮右边 
						for (i=y+1;i<=j;i++)
						  { //判断第二按钮右侧直到位置(x,j)有没有按钮 
							if (grid[x][i]!=0)
							  { //如果有按钮，则将k初始化为零，并将跳出循环
								k=0; 
								break; 
							  } 
							else 
							  {//如果没有按钮
								k=1;
							  } 
						  } 
						if (k==1)
						  { //通过第一次验证，即第二按钮右侧直到位置(x,j)没有按钮
							linePassOne(); 
						  } 
					  } 
					if (y==j ) 
					  { //第二个按钮与空按钮同列，即第二按钮与第一按钮同行
						linePassOne(); 
					  } 
				  } 
				if (k==2) 
				  {//通过第二验证
					if (x0==x) 
					  { //两个按钮在同一行
						remove(); 
					  } 
					if (x0<x) 
					  { //第二个按钮所在行在第一按钮所在行的下面
						for (n=x0;n<=x-1;n++)
						  { //判断空按钮下侧直到位置(x-1,j)有没有按钮
							if (grid[n][j]!=0)
							  { //如果有按钮，将k初始化为零，并跳出循环
								k=0; 
								break; 
							  } 
							if(grid[n][j]==0&&n==x-1) 
							  { //如果直到位置(x-1,j)没有按钮
								remove(); 
							  } 
						  } 
					  } 
					if (x0>x) 
					  { //第二个按钮所在行在第一按钮所在行的上面
						for (n=x0;n>=x+1;n--)
						  { //判断空按钮上侧直到位置(x+1,j)有没有按钮
							if (grid[n][j]!=0) 
							  { //如果有按钮，将k初始化为零，并跳出循环
								k=0; 
								break; 
							  } 
							if(grid[n][j]==0&&n==x+1) 
							  { //如果直到位置(x+1,j)没有按钮
								remove(); 
							  } 
						   } 
					   } 
				   } 
			  } 
			//判断与第一按钮同列情况
			for (i=0;i<8;i++)
			  { //判断第一个按钮同列哪个按钮为空
				if (grid[i][y0]==0)
				  { //同列有空按钮
					if (x>i)
					  { //如果第二个按钮的x坐标大于空按钮的i坐标说明空按钮在第二按钮上边
						for(j=x-1;j>=i;j--) 
						  {//判断第二按钮上侧直到位置(i,y)有没有按钮 
							if (grid[j][y]!=0)
							  { //如果有按钮，将k初始化为零，并跳出循环
								k=0; 
								break; 
							  } 
							else
							  { //如果没有按钮
								 k=1; //说明通过第一次验证
							  } 
						   } 
						if (k==1) 
						  { //第二按钮上侧直到位置(i,y)没有按钮
							rowPassOne(); 
						  } 
					} 
					if (x<i)
					  { //空按钮在第二按钮下边
						for (j=x+1;j<=i;j++) 
						  { //判断第二按钮下侧直到位置(i,y)有没有按钮
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
						  { //第二按钮下侧直到位置(i,y)没有按钮
							rowPassOne(); 
						  } 
					 } 
					if (x==i) 
					 { //第二按钮与空按钮同行
						rowPassOne(); 
					 } 
				 } 
				if (k==2)
				  { //通过第二次验证
					if (y0==y) 
					  { //两个按钮同列
						remove(); 
					  } 
					if (y0<y) 
					  { //第二按钮所在行在第一按钮所在行的下面
						for (n=y0;n<=y-1;n++)
						  { //判断空按钮右侧直到位置(i,y-1)有没有按钮
							if (grid[i][n]!=0)
							  { //如果有按钮，将k初始化为零，并跳出循环
								k=0; 
								break; 
							  } 
							if(grid[i][n]==0&&n==y-1)
							  { //空按钮右侧直到位置(i,y-1)没有按钮
								remove(); 
							  } 
						  } 
				       } 
					if (y0>y) 
					  {  //第二按钮所在行在第一按钮所在行的上面
                    	for (n=y0;n>=y+1;n--) 
                    	  { //判断空按钮左侧直到位置(i,y+1)有没有按钮
		                    if (grid[i][n]!=0)
		                      { 
                    			k=0; 
	                       		break; 
		                      } 
                    		if(grid[i][n]==0&&n==y+1) 
                    		  { //空按钮左侧直到位置(i,y+1)没有按钮
			                     remove(); 
		                      } 
	                      } 
			           } 
				} 
			} 
		} 
	}
	
	/**
	 * 第一按钮的同行中存在空按钮
	 * 判断在同一行中空按钮与第一个按钮之间的位置是否有按钮存在，如果有则k=0,否则k=2
	 * */
	public void linePassOne()
	  { 
		if (y0>j)
		  { //第一按钮在同行空按钮的右边 
			for (i=y0-1;i>=j;i--)
			  { //判断第一按钮同左侧空按钮之间有没按钮 
	            if (grid[x0][i]!=0) 
	              { //如果有按钮，将k初始化为零，并跳出循环
	            	k=0;   
		            break; 
               	  } 
	            else
	              {//如果没有按钮
	            	k=2;//K=2说明通过了第二次验证  
	              } 
			  } 
	      } 
		if (y0<j)
		  { //第一按钮在同行空按钮的左边 
	         for (i=y0+1;i<=j;i++)
	           { //判断第一按钮同右侧空按钮之间有没按钮 
		         if (grid[x0][i]!=0)
		          { //如果有按钮，将k初始化为零，并跳出循环
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
	 * 第一按钮的同列中存在空按钮
	 * 判断在同一列中空按钮与第一个按钮之间的位置是否有按钮存在，如果有则k=0,否则k=2
	 * */
	public void rowPassOne()
	  { 
		if (x0>i) 
		  { //第一按钮在同列空按钮的下边
			for (j=x0-1;j>=i;j--)
			  { //判断第一按钮同上侧空按钮之间有没按钮
				if (grid[j][y0]!=0) 
				  { //如果有按钮，将k初始化为零，并跳出循环
					k=0; 
					break; 
				  } 
				else 
				  {//如果没有按钮
	            	k=2;//K=2说明通过了第二次验证 
				  } 
			  } 
	       } 
	if (x0<i)
	  { //第一按钮在同列空按钮的上边
		for (j=x0+1;j<=i;j++) 
		  {  //判断第一按钮同下侧空按钮之间有没按钮
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
	
	//将相同两个按钮消去，即设为不可见
	public void remove()
	  { 
		fristButton.setVisible(false); 
		secondButton.setVisible(false); 
		fraction(); //每消去一对按钮则加100分
		/**
		 * 将点击按钮信息归为初始
		 * 将K和被消去的两个按钮的坐标初始为零
	     */
		pressInformation=false; 
	    k=0; 
	    grid[x0][y0]=0; 
	    grid[x][y]=0; 
	  } 
	
	
	
	//实现事件监听
	public void actionPerformed(ActionEvent e) 
	  {   
          if(e.getSource()==newlyButton)
		  {//点击下一局按钮事件 
//			t.cancel();
			flag=true;
			int grid[][] = new int[8][7]; 
			this.grid = grid; //将grid数组初始化为零
			randomBuild(); //重新获取15个随机的1~25的数字
			//将一切信息归为初始
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
		  {//点击退出按钮事件
			audioClip.stop(); //点击退出，终止音频播放
			System.exit(0); 
		  }
		if(e.getSource()==resetButton) 
			{//点击重列按钮事件
			  reload(); 
			}
		
	    for(int cols = 0;cols < 6;cols++)
	      { 
		    for(int rows = 0;rows < 5;rows++ )
		      { 
			     if(e.getSource()==diamondsButton[cols][rows]) 
				   {//当点击按钮时，调用estimateEven方法
			    	 estimateEven(cols+1,rows+1,diamondsButton[cols][rows]); 
				   }
		      } 
	      } 
     } 

	//时间提示
	public void timerDemo()
	     {
//		    final Timer timer=new Timer();
		    /**
		      * schedule(TimerTask task, Date firstTime, long period)
		      * 安排指定的任务在指定的时间开始进行重复的固定延迟执行
		      **/
		        t.schedule(new TimerTask(){//创建一个新的计时器任务
				int s=300;//总时间为300秒
				public void run()
				  {//此计时器任务要执行的操作
					if(flag==true)
					  {
						this.cancel();//取消此计时器任务
						flag=false;
					  }
					if(s==0)
					  {//时间跑完,则游戏结束
						time.setText("很遗憾时间到！");
						//this.cancel();
						//当时间跑完时，将未点击的按钮设为不可见
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
	//主方法
	public static void main(String[] args) 
	  { 

		LianLianKan llk = new LianLianKan(); 
	    llk.randomBuild(); //获取15个随机的1~25的数字
	    llk.init();
		llk.timerDemo();
		
	  } 
}


