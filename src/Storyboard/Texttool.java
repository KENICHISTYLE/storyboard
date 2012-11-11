package Storyboard;


import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.security.KeyFactory;
import java.security.KeyFactorySpi;
import java.security.KeyStore;

import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;
import javax.swing.text.DefaultFormatter;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.CWidget;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.CStateMachine ;
import fr.lri.swingstates.canvas.CExtensionalTag ;
import fr.lri.swingstates.canvas.CElement ;
import fr.lri.swingstates.sm.State ;
import fr.lri.swingstates.sm.Transition ;
import fr.lri.swingstates.sm.transitions.* ;

/**
 * 
 * @author amghar
 * add text to canvas tool (state machine)
 */
public class Texttool extends CStateMachine {
	
	private CText mytext; 	
	private CRectangle rect,tempr ;
	private State start,edit,move;
	private Point2D p1;
	
    /**
     * 		
     * @param tag allow movement with the selection tool
     * @param str the text that will appear on the canvas when puting the element
     * @param button
     */
	public Texttool(final CExtensionalTag tag, final String str,final int button) {
		
		start = new State() {
			
			
			 Transition press = new Press(button, ">> edit") {
				    public void action() {
					   Canvas canvas = (Canvas)getEvent().getSource() ;
					   p1 = getPoint() ;					  
					   rect = canvas.newRectangle(p1.getX()-3,p1.getY()-3, 1, 25);
					   rect.setFillPaint(Color.WHITE);
					   mytext = canvas.newText(p1, str);
					   mytext.addTag(tag);	
					   mytext.addChild(rect);
					   mytext.setFont(Font.decode("Arial-BOLD-16"));
					   tempr = rect;
					  
					   mytext.setDrawable(true);					   
				    }
				} ;
				Transition enterpress = new KeyPress(13,">> edit") {
					 public void action() {
						 
					    }
				}; 
		  } ;
		  
		 move = new State() {
			 
			 Transition draw = new Drag(button) {
				    public void action() {
				    	p1 = getPoint();
				    	mytext.translateTo(p1.getX(),p1.getY());
				    	rect.setDiagonal(mytext.getMinX()-3,mytext.getMinY()-3,mytext.getMaxX()+10,mytext.getMaxY()+5);
				    	rect.translateTo(mytext.getCenterX(), mytext.getCenterY());
				    }
				} ; 
			
				Transition release =new Release(">> edit") {
					 public void action() {
						 
					    }
				};	
				
				/**
				 * valeur pour entrer
				 */
				Transition enterpress = new KeyPress(13,">> edit") {
					 public void action() {
						 
					    }
				}; 
				
			
		 };
		 
		 edit = new State() {
			 
			 Transition enterpress = new KeyPress(13,">> edit") {
				 public void action() {
					 Canvas canvas = (Canvas)getEvent().getSource() ;
					 CRectangle r = canvas.newRectangle(tempr.getMinX(), tempr.getMaxY(), 1, 22);
					 rect.setDiagonal(mytext.getMinX()-3,mytext.getMinY()-3,mytext.getMaxX()+10,mytext.getMaxY()+5);
					 rect.translateTo(mytext.getCenterX()+2, mytext.getCenterY());
					 tempr = r;
					 rect.addChild(tempr);
					 System.out.println(" am in edit mode");
					
				    }
			}; 
			 Transition keypress =new KeyPress() {
				 public void action() {	
					 char c = getChar();
					if((c >='A' && c<='Z')||(c >='a' && c<='z')||(c >='0' && c<='9')|| c==' ')
					{
					 String s = mytext.getText()+getChar();
					 rect.setDiagonal(mytext.getMinX()-3,mytext.getMinY()-3,mytext.getMaxX()+10,mytext.getMaxY()+5);
				    	rect.translateTo(mytext.getCenterX()+2, mytext.getCenterY());
					 mytext.setText(s);
					 
					}
				 	}
			};
			
			Transition presstag = new PressOnShape(button, ">> edit") {
			    public void action() {
			    	rect.setDiagonal(mytext.getMinX()-3,mytext.getMinY()-3,mytext.getMaxX()+10,mytext.getMaxY()+5);
			    	rect.translateTo(mytext.getCenterX()+2, mytext.getCenterY());
			    }
			} ;
			
			Transition press = new Press(button, ">> start") {
			    public void action() {
				  
			    }
			} ;
			
		 };
	}
	
	
	  
	
	
		   
}
