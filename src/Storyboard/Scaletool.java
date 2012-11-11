package Storyboard;

import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CElement;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CStateMachine.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Release;
/**
 * 
 * @author amghar
 * scale object in canvas tool (state machine)
 */
public class Scaletool extends CStateMachine {
	private State start,move;	
	private CElement scaled;	
	private Point2D p;
    private double x,y;
	private boolean is_stable =false;
    
	/**
	 * 
	 * @param button mouse button
	 * @param modifier modifier
	 */
	public Scaletool( final int button,final int modifier ) {
			
			start = new State() {
				
				Transition pressshift = new PressOnShape(button,modifier, ">> move") {
				    public void action() {
				    	    p = getPoint();
					  		scaled = getShape();
					   		x = 1; y =1;
					  		is_stable = true;
				    }
				} ;
				
				 Transition press = new PressOnShape(button, ">> move") {
					    public void action() {
					    	    p = getPoint();
						  		scaled = getShape(); 
						  		x = 1; y =1;
					    }
					} ;
					
					
			} ;
			  
			 move = new State() {
				 
				 Transition draw = new Drag(button) {
					    public void action() {	
					    	if(p.getX() < getPoint().getX())
					    	x +=0.007;
					    	else
					    		x -=0.007; 
					    	if (is_stable)y=x;
					    	else
					    	if(p.getY() > getPoint().getY())
					    		y+=0.007;
						    	else
						    		y-=0.007;
					    	
					    	if(x <=0)x = 0.007;
					    	if(x >2 )x = 2;
					    	if(y <=0)y = 0.007;
					    	if(y > 2)y = 2;
					    	 scaled.scaleTo(x,y);
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
