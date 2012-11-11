package Storyboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.border.EtchedBorder;



import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.StateMachineListener;


public class Story extends JFrame implements StateMachineListener {

 
	private static final long serialVersionUID = 1L;
	private Canvas canvas ;
    private ArrayList<CStateMachine> tools = new ArrayList<CStateMachine> ();
    private Dragdrop dragdrop ;
    private JFileChooser ofile;
    private JPanel Imagemenu;
    private JPanel Timeline;
    private JPanel Drawmenu;
    private JPanel timesepar;
    private String prevpath;
    private File prevfile;
    private boolean is_selected = false;
    private JLabel previmage = new JLabel();
    private JPanel prev;
    private Image preview;
    private CExtensionalTag movableTag;
    private int canva_w = 600,canva_h =400;
    private String storyname = "Untitled";
    private ArrayList<JButton>  labtools;
    private ArrayList<File> time_canvas = new ArrayList<File>();
    private int nbr =0;
    private int prev_w = 150,prev_h = 150;
    private JButton save = new JButton(" Save ");
	private JButton delete = new JButton(" Delete ");
    private JTextField recuptext;
   
    /**
     * 
     * @param title name of the frame
     * @param width of the frame
     * @param height of the frame
     */
    public Story(String title, int width, int height) {
	   super(title) ;

	   Container pane = getContentPane();
	   
	   pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
	   pane.setMinimumSize(new Dimension(width,height));
	 
	   timesepar = new JPanel();
	   timesepar.setLayout(new BoxLayout(timesepar, BoxLayout.Y_AXIS));
	   JPanel drawsepar = new JPanel();
	   drawsepar.setLayout(new BoxLayout(drawsepar, BoxLayout.X_AXIS));
	   // separation menus
	   Imagemenu = new JPanel();
	   Timeline = new JPanel();
	   Drawmenu = new JPanel();
	   
	   Imagemenu.setLayout(new BoxLayout(Imagemenu, BoxLayout.Y_AXIS));
	   Drawmenu.setLayout(new BoxLayout(Drawmenu, BoxLayout.Y_AXIS));
	   Timeline.setLayout(new BoxLayout(Timeline, BoxLayout.X_AXIS));
	   //
	  
	   // init menu  
	   // image menu
	   JPanel filepanel = new JPanel();
	   ofile = new JFileChooser();
	   ofile.setControlButtonsAreShown(false);
	   ofile.addActionListener(fileselected);
       ofile.setFileSelectionMode(JFileChooser.FILES_ONLY);
       
       // another file selector not acheved
       /*
       FileSelector file = new FileSelector();
       JPanel tem = new JPanel();
       tem.setLayout(new BoxLayout(tem , BoxLayout.Y_AXIS));
       tem.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 200));
       tem.add(file.getpane());*/
       
       filepanel.add(ofile);
       filepanel.setLayout(new BoxLayout(filepanel, BoxLayout.X_AXIS));
       filepanel.setPreferredSize(new Dimension(200, 200));
       filepanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20,20));
	   // ofile.set
	   Imagemenu.add(filepanel);	   
	   Imagemenu.setPreferredSize(new Dimension(300,height+70));
	   Imagemenu.setMaximumSize(new Dimension(300,height+70));
	   Imagemenu.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLineBorder(Color.BLACK)));	   
	   //darw preview 
	   prev = new JPanel();
	   prev.setLayout(new BoxLayout(prev, BoxLayout.X_AXIS));
	   int prevx = 180,prevy = 140;
	   prev.setMaximumSize(new Dimension(prevx+30 ,prevy +50));	   
	   prev.setPreferredSize(new Dimension(prevx,prevy));
	   prev.setMinimumSize(new Dimension(50 ,50));
	   prev.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5),BorderFactory.createLoweredBevelBorder()));
	   JPanel p = new JPanel(new BorderLayout());
	   p.add(previmage,BorderLayout.CENTER);
	   p.setBorder(BorderFactory.createEmptyBorder(20, 2, 20, 2));
	   p.setBackground(Color.WHITE);
	   prev.add(p);
	   Imagemenu.add(prev);
	   // draw menu
	   loadtools();
	   /*recuptext = new JTextField();
	   recuptext.setMaximumSize(new Dimension(300,20));
	   recuptext.setVisible(false);
	   JPanel tp = new JPanel();
	   tp.setLayout(new BoxLayout(tp,BoxLayout.X_AXIS));
	   
	   //tp.add(recuptext);
	   //tp.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 10));
	   //Drawmenu.add(tp);*/
	   // time menu
	   JPanel timebox = new JPanel();
	   timebox.setLayout(new BoxLayout(timebox, BoxLayout.X_AXIS));
	   timebox.setPreferredSize(new Dimension(750,200));
	   timebox.setMinimumSize(new Dimension(750,200));
	   timebox.setMaximumSize(new Dimension(750,200));
	   timebox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20),"Time line"));
	   JPanel temp = new JPanel(new BorderLayout());
	   temp.setBorder(BorderFactory.createLoweredBevelBorder());
	    // the scrolling pane
	   timebox.add(temp);
	   JScrollPane view = new JScrollPane(Timeline);
	   
	   view.setAutoscrolls(true);
	   view.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	   view.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	   // pane.setPreferredSize();
	   pane.setSize(width, height);
	   
	   temp.add(view,BorderLayout.CENTER);
	   // butons to change the time line
	   save.addActionListener(save_canvas) ;
	   delete.addActionListener(save_canvas);
	   JPanel b1 = new JPanel();
	   JPanel b2 = new JPanel();
	   b1.add(save);
	   b1.setBorder(BorderFactory.createEmptyBorder(0, 20, 5 , 20));
	   b2.add(delete);
	   b2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	   JPanel canv_buttons = new JPanel();
	   canv_buttons.setLayout(new BoxLayout(canv_buttons, BoxLayout.Y_AXIS));
	   canv_buttons.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
	   canv_buttons.add(b1);
	   canv_buttons.add(b2);
	   timebox.add(canv_buttons);
	   /* the canvas */
	   canvas = new Canvas(canva_w,canva_h) ;
	   drawsepar.add(canvas) ;
	   
	   	   
	   dragdrop = new Dragdrop(new ImageIcon(),canvas,this.getLocation(),prevx,prevy);
	  
	   tools.add(new SelectionTool(CStateMachine.BUTTON1, CStateMachine.NOMODIFIER)) ;
	   tools.get(0).attachTo(canvas) ;
	   movableTag = ((SelectionTool) tools.get(0)).getMovableTag() ;
	   
	   tools.add(new RectangleTool(CStateMachine.BUTTON1, CStateMachine.NOMODIFIER)) ;
	   tools.get(1).attachTo(canvas) ;
	   tools.get(1).addStateMachineListener(this) ;
	   tools.get(1).setActive(false);
	   
	   tools.add(new Rotationtool(CStateMachine.BUTTON1));
	   tools.get(2).attachTo(canvas) ;
	   tools.get(2).addStateMachineListener(this) ;
	   tools.get(2).setActive(false);
	   
	   tools.add(new Scaletool( CStateMachine.BUTTON1,CStateMachine.SHIFT));
	   tools.get(3).attachTo(canvas) ;
	   tools.get(3).addStateMachineListener(this) ;
	   tools.get(3).setActive(false);
	   
	   tools.add(new Linetool(movableTag, CStateMachine.BUTTON1));
	   tools.get(4).attachTo(canvas) ;
	   tools.get(4).addStateMachineListener(this) ;
	   tools.get(4).setActive(false);
	   	   
	   tools.add(new Texttool(movableTag, "", CStateMachine.BUTTON1));
	   tools.get(5).attachTo(canvas) ;
	   tools.get(5).addStateMachineListener(this) ;
	   tools.get(5).setActive(false);
	   //
	     
	   JPanel	mycanvas = new JPanel();
	   JPanel	myborder = new JPanel();
	   mycanvas.setLayout(new BoxLayout(mycanvas, BoxLayout.X_AXIS));
	   mycanvas.setPreferredSize(new Dimension(canva_w+10,canva_h+10));
	   mycanvas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), storyname));
	   
	   myborder.add(mycanvas);
	   myborder.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 10));
	   
	  
	   mycanvas.add(canvas);
	   drawsepar.add(myborder) ;
	   drawsepar.add(Drawmenu) ;
	   
	   //store_canvas();
	   load_timeline();
	   //view.validate();
	   
	   timesepar.add(timebox);
	   timesepar.add(drawsepar); 
	   
	   pane.add(Imagemenu);
	   pane.add(timesepar);
	   select(0); // select the selection and deplacment tool
	   
	   final Point curent = new Point(0,0);
	   
	   prev.addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			Point p = canvas.getLocationOnScreen();
			dragdrop.onDrop(prevpath,movableTag,e.getXOnScreen() -p.getX() ,e.getYOnScreen() -p.getY());
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			curent.setLocation(e.getPoint());
			Point p = new Point();
			p.setLocation(e.getXOnScreen() - e.getX() , e.getYOnScreen() - e.getY());
			if(is_selected )dragdrop.onDrag(preview,p);
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	});
	   
	   prev.addMouseMotionListener(new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			Point p = new Point();
			p.setLocation(arg0.getXOnScreen() - curent.getX(), arg0.getYOnScreen() - curent.getY() );
			dragdrop.onMove(p);
			}
		
	   });
	   	/*
	   JFrame smviz = new JFrame("StateMachine Viz") ;
	   Container pane2 = smviz.getContentPane() ;
       pane2.setLayout(new BoxLayout(pane2, BoxLayout.PAGE_AXIS));
       
	   pane2.add(new JLabel("Selection Tool")) ;
	   pane2.add(new StateMachineVisualization(tools.get(0))) ;
       
	   smviz.pack() ;
	   smviz.setVisible(true) ;
	   	*/ 
	   pack() ;
	   setVisible(true) ;
    }

    /**
     * loads the canavs stored in the story directory to be viewed in the timeline
     */
    private void load_timeline(){
    	File f = new File("../StroryBoard/Story");
    	Timeline.removeAll();
    	time_canvas.clear();
    	if(f.isDirectory())
    		for(File str : f.listFiles() ){
    			BufferedImage myPicture;
				try {
					myPicture = ImageIO.read(str);
					Image scaledp = myPicture.getScaledInstance(prev_w, prev_h, Image.SCALE_SMOOTH);
					JLabel l = new JLabel(new ImageIcon(scaledp));
					l.setMaximumSize(new Dimension(prev_w,prev_h));
					l.setBorder(BorderFactory.createEmptyBorder(5, 10 , 20, 20));
					time_canvas.add(str);
	  			    Timeline.add(l);
	  			    Timeline.validate();
	  			    Timeline.repaint();
				} catch (IOException e) {
					e.printStackTrace();
				}
    			
    		}
    }
    
    /**
     * store the current canvas in a jpg file in the directory story of the application
     */
    private void store_canvas(){
    	BufferedImage image = new BufferedImage(canva_w, canva_h, BufferedImage.TYPE_INT_RGB);
    	    	
		BufferedOutputStream fos;
		try {
		FileOutputStream f  =	new FileOutputStream("../StroryBoard/Story/"+storyname+nbr+".jpg");
		nbr++;
		fos = new BufferedOutputStream(f);		
		Graphics2D graphics = image.createGraphics();
		canvas.paintAll(graphics);
		image.getGraphics();
		ImageIO.write(image, "jpg", fos);

		fos.close();
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
    }
    /**
     * wait for the selection of a file
     */
 private ActionListener fileselected = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = (JFileChooser) e.getSource();
			
			File f =fc.getSelectedFile();
			System.out.println(" entrer");
			if(f != null ){
			String mimeType = new MimetypesFileTypeMap().getContentType( f );

			if(mimeType.substring(0,5).equalsIgnoreCase("image")){
				prevpath = f.getAbsolutePath();
				prevfile = f;
				is_selected = true;
				loadimage();
				   }
			}
			else is_selected = false;
		}
	};
    
	/**
	 * to save the canvas in a jpg image
	 */
	private ActionListener save_canvas = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b.equals(save)){
				store_canvas();				
			}
			else
				if(b.equals(delete) && !time_canvas.isEmpty()){
				 int i = time_canvas.size()-1;
				 File f = time_canvas.get(i);
				 Timeline.remove(i);
				 if(!f.delete())System.out.println(" not deleted ");				 
				}
			load_timeline();
			Timeline.revalidate();
			Timeline.repaint();
		}
	};
	
	/**
	 * loading the preview
	 */
    private void loadimage (){
    	if(is_selected){
    	Image bf ;
		try {
			preview = ImageIO.read(prevfile);
			bf = preview.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			previmage.setIcon(new ImageIcon( bf ));
			
		} catch (IOException e) {			
			e.printStackTrace();
		}    	
    	}
    }
    
    /**
     * 
     * @param j select the (state machine) tool from tools(j)  
     */
    private void select(int j){
		labtools.get(j).setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		tools.get(j).setActive(true);		
		int i;
		for(i = 0; i<labtools.size();i++){
			if(i != j){
				labtools.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK));
				tools.get(i).setActive(false);
			}
		}
	
	}
    
    public ActionListener modchoser = new ActionListener() {
		
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			System.out.println(b.getName());
			int j = Integer.parseInt(b.getName());				
			select(j);		
			
		}
	};
	
    private void loadtools(){
      String currentdir = System.getProperty("user.dir");
      String [] tools = {"e210.SelectionTool.png","e210.RectangleTool.png","rotation.jpg","scale.jpg","e210.LineTool.png","text.jpg"};
      labtools =  new ArrayList<JButton>(); 
      JPanel toolpane = new JPanel();
      toolpane.setLayout(new BoxLayout(toolpane, BoxLayout.Y_AXIS));
      toolpane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 10, 20, 20), "Tools"));
      try{
    	  int i =0;
 		   for (String n : tools){
 			   BufferedImage myPicture = ImageIO.read(new File(currentdir+"/res/"+n));
 			   JButton b = new JButton(new ImageIcon( myPicture ));
 			   b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
 			   b.addActionListener(modchoser);
 			   b.setName(Integer.toString(i));i++;
 			   labtools.add(b);
 			   toolpane.add(b);
 			   
 		   }
 	   } catch ( Exception e){
 		   
 		   System.out.println(" image not found"+ currentdir);
 	   }
 	  Drawmenu.add(toolpane);
    }
    
    public void eventOccured(EventObject e) {
	   ShapeCreatedEvent csce = (ShapeCreatedEvent)e ;
	   csce.getShape().addTag(((SelectionTool)tools.get(0)).getMovableTag()).setFillPaint(Color.white) ;
    }
	
    public static void main(String[] args) {
	   Story editor = new Story("Strory Board",400,600) ;	   
	   editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
    }

}