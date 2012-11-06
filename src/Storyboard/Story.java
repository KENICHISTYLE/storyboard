package Storyboard;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.DimensionUIResource;

import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.sm.StateMachineListener;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class Story extends JFrame implements StateMachineListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Canvas canvas ;
    private SelectionTool selector ;
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
    private BufferedImage preview;
    
    private ActionListener fileselected = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = (JFileChooser) e.getSource();
			File f =fc.getSelectedFile();
			System.out.println(" entrer");
			if(f != null){
				
			prevpath = f.getAbsolutePath();
			prevfile = f;
			is_selected = true;
			loadimage();
			}
			else is_selected = false;
		}
	};
    
    public Story(String title, int width, int height) {
	   super(title) ;

	   Container pane = getContentPane();
	   
	   pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
	   
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
	   JPanel imageprev = new JPanel();
	  
	   // init menu  
	   // image menu
	   JPanel filepanel = new JPanel();
	   ofile = new JFileChooser();
	   ofile.setControlButtonsAreShown(false);
	   ofile.addActionListener(fileselected);
       ofile.setFileSelectionMode(JFileChooser.FILES_ONLY);
              
       filepanel.add(ofile);
       filepanel.setLayout(new BoxLayout(filepanel, BoxLayout.X_AXIS));
       filepanel.setPreferredSize(new Dimension(200, 200));
       filepanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20,0 ));
	   // ofile.set
	   Imagemenu.add(filepanel);	   
	   Imagemenu.setPreferredSize(new Dimension(250,50));
	   Imagemenu.setBorder(BorderFactory.createEmptyBorder(0, 5, 40,20 ));
	   //darw preview 
	   prev = new JPanel();
	   prev.setLayout(new BoxLayout(prev, BoxLayout.X_AXIS));
	   int prevx = 160,prevy = 120;
	   prev.setMaximumSize(new Dimension(prevx+30 ,prevy +50));	   
	   prev.setPreferredSize(new Dimension(prevx,prevy));
	   prev.setMinimumSize(new Dimension(50 ,50));
	   prev.setBackground(Color.WHITE);
	   prev.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	   prev.add(previmage);
	   Imagemenu.add(prev);
	   // draw menu
	   loadtools();
	   // time menu
	   pane.setSize(width, height);
	   // pane.setPreferredSize();
	 
	   
	   canvas = new Canvas((int)(width ),(int)(height)) ;
	   drawsepar.add(canvas) ;

	   dragdrop = new Dragdrop(new ImageIcon(),this.getLocation(),prevx,prevy);
	  
	   
	   selector = new SelectionTool(CStateMachine.BUTTON1, CStateMachine.SHIFT) ;
	   selector.attachTo(canvas) ;
	   
	   
	   CStateMachine tool = new RectangleTool(CStateMachine.BUTTON1, CStateMachine.NOMODIFIER) ;
	   tool.attachTo(canvas) ;
	   tool.addStateMachineListener(this) ;

	   //
	     
	   drawsepar.add(canvas) ;
	   drawsepar.add(Drawmenu) ;
	   
	   timesepar.add(Timeline);
	   timesepar.add(drawsepar); 
	   
	   pane.add(Imagemenu);
	   pane.add(timesepar);
	   
	   final Point curent = new Point(0,0);
	   
	   prev.addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			dragdrop.onDrop();
			
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
	   Container pane = smviz.getContentPane() ;
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
	   pane.add(new JLabel("SelectionTool")) ;
	   pane.add(new StateMachineVisualization(selector)) ;
	   pane.add(new JLabel("RectangleTool")) ;
	   pane.add(new StateMachineVisualization(tool)) ;
	   smviz.pack() ;
	   smviz.setVisible(true) ;
	   	 */	
	   pack() ;
	   setVisible(true) ;
    }

    private void loadimage (){
    	if(is_selected){
    	
		try {
			preview = ImageIO.read(prevfile);
			previmage.setIcon(new ImageIcon( preview ));
			
		} catch (IOException e) {			
			e.printStackTrace();
		}    	
    	}
    }
    
    
    
    private void loadtools(){
      String currentdir = System.getProperty("user.dir");
      String [] tools = {"e210.SelectionTool.png","e210.RectangleTool.png","e210.EllipseTool.png","e210.LineTool.png","e210.PathTool.png"};
      ArrayList<JLabel>  labtools =  new ArrayList<JLabel>(); 
      try{
 		   for (String n : tools){
 			   BufferedImage myPicture = ImageIO.read(new File(currentdir+"/res/"+n));
 			   JLabel m = new JLabel(new ImageIcon( myPicture ));
 			   m.setBorder(BorderFactory.createLineBorder(Color.BLACK));
 			   labtools.add(m);
 			   Drawmenu.add( m );
 		   }
 	   } catch ( Exception e){
 		   
 		   System.out.println(" image not found"+ currentdir);
 	   }
    }
    
    public void eventOccured(EventObject e) {
	   ShapeCreatedEvent csce = (ShapeCreatedEvent)e ;
	   csce.getShape().addTag(selector.getMovableTag()).setFillPaint(Color.white) ;
    }
	
    public static void main(String[] args) {
	   Story editor = new Story("Strory Board",400,600) ;	   
	   editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
    }

}