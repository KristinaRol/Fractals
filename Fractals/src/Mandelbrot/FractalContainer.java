package Mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FractalContainer extends JPanel {

	public Color innerColor = Color.black;
	/** obere linke und untere rechte ecke **/
	private Complex[] complexBounds;  
	Mandelbrot mandelbrot;
	private double zoom = 1;
	private Complex dragged = new Complex(0, 0);
	private double stepX;
	private double stepY;
	private int[] rgbScaleColor = {20,40,60,50};

	public FractalContainer() {
		mandelbrot = new Mandelbrot();
	}

	public void setR(int r) {
		rgbScaleColor[0] = r;
	}

	public void setG(int g) {
		rgbScaleColor[1] = g;
	}

	public void setB(int b) {
		rgbScaleColor[2] = b;
	}

	public void setScale(int scale) {
		rgbScaleColor[3] = scale;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		setComplexBounds(zoom);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(innerColor);

		// dragged stuff
		Complex z = complexBounds[0];
		stepX = (complexBounds[1].re() - complexBounds[0].re()) / getWidth();
		stepY = (complexBounds[1].im() - complexBounds[0].im()) / getHeight();

		// iteration over every pixel, draw every pixel
		for (int j = 0; j < getHeight(); j++) {
			for (int i = 0; i < getWidth(); i++) {
				int terminatedIterations = mandelbrot.check(z);
				if (terminatedIterations == -1) {
					g2d.fillRect(i, j, 1, 1);
				} else {
					setPixelColor(g2d, terminatedIterations);
					g2d.fillRect(i, j, 1, 1);
					g2d.setColor(innerColor);
				}
				z = new Complex(z.re() + stepX, z.im());
			}
			z = new Complex(complexBounds[0].re(), z.im() + stepY);
		}
	}

	private void setPixelColor(Graphics2D g2d, int terminatedIterations) {
		int r = rgbScaleColor[0] * (int) Math.pow(terminatedIterations, rgbScaleColor[3]/100.0) % 255;
		int g = rgbScaleColor[1] * (int) Math.pow(terminatedIterations, rgbScaleColor[3]/100.0) % 255;
		int b = rgbScaleColor[2] * (int) Math.pow(terminatedIterations, rgbScaleColor[3]/100.0) % 255;
		g2d.setColor(new Color(r, g, b));
	}

	private void setComplexBounds(double zoom) {
		Complex bound = getWidth() > getHeight()
				? new Complex(2.0 + (getWidth() - getHeight()) / (getHeight() * .25), -2)
				: new Complex(2, -2.0 - (getHeight() - getWidth()) / (getWidth() * .25));
		complexBounds = new Complex[] { new Complex(-2, 2), bound };

		Complex a = complexBounds[0].plus(dragged.divides(new Complex(zoom, 0)));
		Complex b = complexBounds[1].plus(dragged.divides(new Complex(zoom, 0)));

		complexBounds[0] = new Complex(a.re() * zoom, a.im() * zoom);
		complexBounds[1] = new Complex(b.re() * zoom, b.im() * zoom);
	}

	public void setDragged(Complex z) {
		dragged = dragged.plus(z);
	}

	public void setZoom(double zoom) {
		this.zoom = 1 / zoom;
	}

	public Complex[] getComplexBounds() {
		return complexBounds;
	}

	public double getStepX() {
		return stepX;
	}

	public double getStepY() {
		return stepY;
	}
}
