package Storyboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * 
 * @author amghar
 * infiniched file selector
 */
public class FileSelector {

	private JComboBox parentdirs;
	private JPanel filelist;
	private JButton back;	
    private JPanel pane;
	private String directory = null;
	private String file = null;

	private ActionListener parentdirsListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {			
			int index = parentdirs.getSelectedIndex();
			
			String path = "/";
			for (int i = parentdirs.getItemCount() - 2; i >= index; i--)
				path = path + parentdirs.getItemAt(i) + File.separator;	
			path = path.replace("\\\\", "\\");
			System.out.println(" the combox path "+path);
			filelist.removeAll();
			filelist.revalidate();
			show(path);
			
		}
	};

   private ActionListener back_list = new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b == back){
			int index = parentdirs.getSelectedIndex();
			/*if (index < 1)
				return;*/
			String path = "/";
			for (int i = parentdirs.getItemCount() - 2; i >= index; i--)
				path = path + parentdirs.getItemAt(i) + File.separator;
			filelist.removeAll();
			filelist.revalidate();		
			path = path.replace("\\\\", "\\");
			String[] s  = path.split(File.separator);
			
			path = "";
			for(int i =0; i < s.length;i++){
			System.out.println(" the back path "+path+ "  p "+s[i]);
			path = path + s[i];
			}
			show(path);			
			}
	}
};

	public JPanel getpane(){
		return pane;
	}
	 
	

	private MouseAdapter mouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			JLabel p = (JLabel) e.getSource();
			String s = p.getText();
			System.out.println(" file selected ");			
			p.setBackground(Color.ORANGE);
			p.revalidate();
			p.repaint();
			if(s.contains("\\")){
				System.out.println(s);			
				filelist.removeAll();
				filelist.revalidate();
				int index = parentdirs.getSelectedIndex();
				//if (index < 1)
					//return;
				String path = "";
				for (int i = parentdirs.getItemCount() - 2; i >= index; i--)
					path = path + parentdirs.getItemAt(i) + File.separator;
				
				System.out.println(" showed path "+path);			
				path = path.replace("\\\\", "\\");
				if(!show(path+s))System.out.println(" pas de sous repertoire ");			
				filelist.repaint();
			}
				
		}
	};

	public FileSelector() {
		

		pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.setMinimumSize(new Dimension(200,100));
		parentdirs = new JComboBox();
		parentdirs.addActionListener(parentdirsListener);
		pane.add(parentdirs, BorderLayout.PAGE_START);

		filelist = new JPanel();
		filelist.setLayout(new GridLayout(0,1));
		//filelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//filelist.setVisibleRowCount(10);
		//filelist.addListSelectionListener(filelistListener);
		//filelist.addMouseListener(mouseListener);
		pane.add(new JScrollPane(filelist), BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
		buttons.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttons.add(Box.createHorizontalGlue());
		back = new JButton(" Back ");
		back.addActionListener(back_list);
		
		buttons.add(back);
		pane.add(buttons, BorderLayout.PAGE_END);

		show(System.getProperty("user.dir"));

	}

	Boolean show(String path) {
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory())
			return false;

		directory = dir.getAbsolutePath();
		file = null;
		
		parentdirs.removeAllItems();
		//filelist.setListData(new Vector<JLabel>());
		String currentdir = System.getProperty("user.dir");
		BufferedImage myfilepic = null;
		BufferedImage myfolderpic = null;
		try {
			myfilepic = ImageIO.read(new File(currentdir+"/res/file.png"));
			myfolderpic = ImageIO.read(new File(currentdir+"/res/folder.png"));
		} catch (IOException e) {					
			e.printStackTrace();
		}
		
		// Update the listbox showing the files in this directory
		String[] files = dir.list();
		int g = files.length;		
		ArrayList<JLabel> l = new ArrayList<JLabel>();
		
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File f = new File(path, files[i]);
				if (f.isDirectory())
					files[i] = files[i] + File.separator;
				    //l.add(new JLabel(files[i]));
				
					JPanel p = new JPanel();
					p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
					
					JLabel b = null; 
					if(f.isDirectory())b= new JLabel(new ImageIcon( myfolderpic ));
					if(f.isFile())b= new JLabel(new ImageIcon( myfilepic ));
					
					
					p.add(b);
					JLabel text = new JLabel(files[i]);
					text.addMouseListener(mouseListener);
					p.add(text);
					filelist.add(p);
				    
			}
			//filelist.setListData(l.toArray());
			
		}

		// Update the combobox showing the list of parent directories
		parentdirs.insertItemAt(File.separator, 0);
		String[] dirs = path.split("/");
		for (String p : dirs) {
			if (p.equals(""))
				continue;
			parentdirs.insertItemAt(p, 0);
		}
		parentdirs.setSelectedIndex(0);
		System.out.println(" parent "+directory);
		return true;
	}

	public String getFilePath() {
		if (directory == null || file == null)
			return null;
		return directory + File.separator + file;
	}

	

}
