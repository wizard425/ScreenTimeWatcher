import java.awt.Container;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.border.Border;

public class MainGui extends JFrame {

	private JLabel lblaktsess, currsesstime;
	private JEditorPane protokoll;

	private HashMap<Date, Long> days = new HashMap<Date, Long>();

	private Border border = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder());

	public static void main(String[] args) {

		MainGui g = new MainGui();

	}

	public MainGui() {
		setTitle("Screen-Time Watcher");
		setSize(620, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		Session s = new Session();

		Container con = getContentPane();

		// Panel Data of current Session
		JPanel aktData = new JPanel();
		aktData.setBounds(10, 10, 300, 200);
		aktData.setBorder(border);
		aktData.setLayout(null);

		lblaktsess = new JLabel("Current Session");
		lblaktsess.setBounds(10, 5, 150, 30);
		aktData.add(lblaktsess);

		currsesstime = new JLabel("");
		currsesstime.setBounds(150, 5, 130, 30);
		aktData.add(currsesstime);

		con.add(aktData);

		protokoll = new JEditorPane();
		//protokoll.setBounds(320, 10, 280, 300);
		protokoll.setEditable(false);
		
		JScrollPane sc = new JScrollPane(protokoll);
		sc.setBounds(320, 10, 280, 300);
		sc.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 0, 0, 0)));

		con.add(sc);

		// Gets the saved Sessions from the CSV-File
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					BufferedReader r = new BufferedReader(
							new FileReader("/home/samuel/eclipse-workspace/TimeController/src/sessions.csv"));
					String line = "";
					while ((line = r.readLine()) != null) {
						String[] items = line.split(";");
						Date sessionFromCSVDate = new SimpleDateFormat("dd/MM/yyyy").parse(items[0]);
						// if date already exists
						if (days.containsKey(sessionFromCSVDate)) {
							days.put(sessionFromCSVDate, days.get(sessionFromCSVDate) + Long.parseLong(items[1]));
						}else {
							days.put(sessionFromCSVDate,Long.parseLong(items[1]));
						}
					}

					Map<Date, Long> m1 = new TreeMap(days);
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

					// output on the GUI-protocollfield
					for (Map.Entry<Date, Long> entry : m1.entrySet()) {
						if(protokoll.getText().length() == 0) {
						protokoll.setText(protokoll.getText() + df.format(entry.getKey()) + ": "
								+ secToTime(entry.getValue()));
						}else {
							protokoll.setText(protokoll.getText() +"\n" + df.format(entry.getKey()) + ": "
									+ secToTime(entry.getValue()));
						}
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
		}).start();

		new ThreadCurrentTime(s, currsesstime).start();

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				// this is executed on shut-down. put whatever.
				s.endSession();
			}
		}));

		setVisible(true);
	}

	/**
	 * Converts the given secs to String with HH:MM:SS
	 * 
	 * @param sec the sec to convert
	 * @return String with HH:MM:SS-Format
	 */
	public String secToTime(long sec) {
		String ret = "";
		long s, m, h;
		s = sec % 60;
		m = (sec / 60) % 60;

		h = (sec / 60) / 60;
		if (s < 10 && m < 10) {
			ret = h + ":0" + m + ":0" + s;
		} else if (s < 10) {
			ret = h + ":" + m + ":0" + s;
		} else if (m < 10) {
			ret = h + ":0" + m + ":" + s;
		} else {
			ret = h + ":" + m + ":" + s;
		}
		return ret;
	}

}
