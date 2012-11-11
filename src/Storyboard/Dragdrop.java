package Storyboard;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;
/**
 * 
 * @author amghar
 * drag image from preview to canvas
 */
public class Dragdrop  {
	
	    private Point2D pPrevious ;
	    private JFrame f ;
	    private JLabel label;
	    private JPanel icon;
	    private CImage img;
	    private Canvas canva;
	    //private Dialog d = new Dialog();
	    
	    public Dragdrop (){
	    	f = new JFrame();
	    	Container pane;
	    	pane = f.getContentPane();
	    	pane.add(new JLabel("SelectionTool")) ;
	    	f.setLocation(20, 580);
	    	f.setUndecorated(true);
	    	f.pack() ;
	 	   	f.setVisible(true) ;
	    }
	    	/**
	    	 * 
	    	 * @param i the image to drag
	    	 * @param c the canvas where to display
	    	 * @param p the point where the cursor is at the grab time
	    	 * @param width of the dragging box
	    	 * @param height of the dragging box
	    	 */
	    public Dragdrop ( ImageIcon i,Canvas c,Point p,int width , int height){
	    	f = new JFrame();
	    	f.setUndecorated(true);
	    	f.setLocation(p);
	    	f.setAlwaysOnTop(true);
	    	
	    	Container pane;
	    	pane = f.getContentPane();
	    	
	    	this.label = new JLabel(i);	    	
	    	this.canva = c;
	    	
	    	icon = new JPanel();
	    	icon.setLayout(new BoxLayout(icon, BoxLayout.X_AXIS));
	    	icon.setPreferredSize(new Dimension(width+30,height));
	    	icon.setMaximumSize(new Dimension(width+30 ,height +50));	   
	    	icon.setMinimumSize(new Dimension(50 ,50));
	    	icon.setBackground(Color.WHITE);
	    	icon.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
	    	icon.add(label);
	    		    	
	    		
	    	icon.add(label);
	    	pane.add(icon);
	    	f.pack() ;
	    }
	    /**
	     * 
	     * @param p image to drag
	     * @param l the position of the mouse
	     */
	    public void onDrag (Image p, Point l) {	
	    	label.setIcon(new ImageIcon(p));
	    	f.setLocation(l);
	    	f.setVisible(true);
	    	
	    }
	    public void onMove (Point p){
	        f.setLocation(p);	
	    }
	    /**
	     * 
	     * @param path the path of the image to draw
	     * @param tag allow to move the image
	     * @param x position to put the image
	     * @param y same as x
	     */
	    public void onDrop (String path,CExtensionalTag tag,double x, double y){
	    	f.setVisible(false);
	    	img = canva.newImage(0, 0, path);
	    	double w = img.getWidth();
	    	double h = img.getHeight();	   
	    	
	    	img.translateTo(x, y);	       
	    	
	    	if(w > 200 && w <1000)img.scaleBy(200/w);
	        if(h > 200 && h <1000)img.scaleBy(200/h);
	        if(w >=1000 && w < 2000)img.scaleBy(400/w);
	        if(h >=1000 && h < 2000)img.scaleBy(400/h);
	        if(w >=2000)img.scaleBy(900/w);
	        if(h >=2000)img.scaleBy(900/h);
	        //  no need to scale cause we have a scale tool     
	        //s.getShape().addTag(tag);
	        img.addTag(tag);
	        
	    }
	    public void close(){
	    	f.dispose();
	    }
}
