import java.awt.Container;

import javax.swing.*;
import javax.swing.border.Border;

public class MainGui extends JFrame{

	private JLabel lblaktsess, currsesstime;
	
	
	private Border border = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder());

	
	public static void main(String[] args) {

		MainGui g = new MainGui();
		
	}
	
	public MainGui() {
		setTitle("Screen-Time Watcher");
		setSize(620,600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		
		Session s = new Session();
		
		Container con = getContentPane();
		
		//Panel Data of current Session
		JPanel aktData = new JPanel();
		aktData.setBounds(10,10,300,200);
		aktData.setBorder(border);
		aktData.setLayout(null);
		
		lblaktsess = new JLabel("Current Session");
		lblaktsess.setBounds(10,5,150,30);
		aktData.add(lblaktsess);
		
		currsesstime = new JLabel("");
		currsesstime.setBounds(150, 5, 130, 30);
		aktData.add(currsesstime);
		
		con.add(aktData);
		
		
		new ThreadCurrentTime(s, currsesstime).start();
		
		setVisible(true);
	}

}
