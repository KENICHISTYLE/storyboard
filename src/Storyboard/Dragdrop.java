package Storyboard;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.lri.swingstates.canvas.CElement;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Release;

public class Dragdrop  {
	
	    private Point2D pPrevious ;
	    private JFrame f ;
	    private JLabel label;
	    private JPanel icon;
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
	    	
	    public Dragdrop ( ImageIcon i,Point p,int width , int height){
	    	f = new JFrame();
	    	f.setUndecorated(true);
	    	f.setLocation(p);
	    	f.setAlwaysOnTop(true);
	    	
	    	Container pane;
	    	pane = f.getContentPane();
	    	
	    	this.label = new JLabel(i);
	    	
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
	    
	    public void onDrag (BufferedImage p, Point l) {	
	    	label.setIcon(new ImageIcon(p));
	    	f.setLocation(l);
	    	f.setVisible(true);
	    }
	    public void onMove (Point p){
	        f.setLocation(p);	
	    }
	    public void onDrop (){
	    	f.setVisible(false);
	    }
	    public void close(){
	    	f.dispose();
	    }
}
