package Mandelbrot;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Movement implements MouseListener, MouseMotionListener {

	/** current mouse position **/
	private int x, y;
	private FractalContainer fractalContainer;

	public Movement(JPanel jp, FractalContainer fractalContainer) {
		jp.addMouseListener(this);
		jp.addMouseMotionListener(this);
		this.fractalContainer = fractalContainer;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		e.getComponent().setLocation(e.getX() + e.getComponent().getX() - x, e.getY() + e.getComponent().getY() - y);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point locationOfPanel = e.getComponent().getLocation();
		fractalContainer.setDragged((new Complex(0,0)).minus(pixelToComplex(locationOfPanel)));
		e.getComponent().setLocation(0, 0);
		fractalContainer.repaint();
		
	}
	
	public Complex pixelToComplex(Point pixel) {
		double a = fractalContainer.getStepX() * pixel.getX();
		double b = fractalContainer.getStepY() * pixel.getY();
		return new Complex(a, b);
	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

}
