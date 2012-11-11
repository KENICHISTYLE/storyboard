package Storyboard;

import java.awt.Stroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CElement;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.sm.transitions.*;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Release;
/**
 * 
 * @author amghar
 * the drawing line tool (state machine)
 */
public class Linetool extends CStateMachine{
	private State start,move;	
	private CPolyLine line;	
	private Point2D p;

	/**
	 * 
	 * @param tag used for moving in the canvas
	 * @param button the mouse button pressed 
	 */
	public Linetool(final CExtensionalTag tag, final int button) {
			
			start = new State() {
				
				
				 Transition press = new Press(button, ">> move") {
					    public void action() {
					    	    p = getPoint();
					    	    Canvas canvas = (Canvas)getEvent().getSource() ;
						  		line = canvas.newPolyLine(p); 
						  		
					    }
					} ;
			} ;
			  
			 move = new State() {
				 
				 Transition draw = new Drag(button) {
					    public void action() {
					    	line.reset(p);					    	
					    	line.lineTo(getPoint());

					    }
					} ; 
				
					Transition release =new Release(">> start") {
						 public void action() {
							 line.lineTo(getPoint());
						    }
					};	
					
					
				
			 };
			 
			
	}
}
