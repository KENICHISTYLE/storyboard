package Storyboard;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CElement;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.CStateMachine.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.KeyPress;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
/**
 * 
 * @author amghar
 * rotate object in canvas tool (state machine)
 */
public class Rotationtool extends CStateMachine{
	
private State start,move;	
private CElement rotated;	
private Point2D p;

/**
 * 
 * @param button mouse button
 */
public Rotationtool( final int button) {
		
		start = new State() {
			
			
			 Transition press = new PressOnShape(button, ">> move") {
				    public void action() {
				    	    p = getPoint();
					  		rotated = getShape();   
				    }
				} ;
		} ;
		  
		 move = new State() {
			 
			 Transition draw = new Drag(button) {
				    public void action() {	
				    	if(p.getX() > getPoint().getX())
				    	rotated.rotateBy(0.02);
				    	else
				    		rotated.rotateBy(-0.02);
				    		p = getPoint();
				    }
				} ; 
			
				Transition release =new Release(">> start") {
					 public void action() {
						 
					    }
				};	
				
				
			
		 };
		 
		
}
}