package Mandelbrot;

public class Mandelbrot {

	private int iterations = 300;
	private Complex complexConstant = new Complex(0, 0);
	private Fractal selectedFractal = Fractal.MANDELBROT;

	
	/**
	 * gibt an ob komplexe zahl c kleiner als 2 bleibt oder nicht.
	 * 
	 * @param currentComplex complex number to check.
	 * @return number of iterations it need to be bigger than two.
	 */
	public int check(Complex currentComplex) {
		Complex result = complexConstant;

		if (selectedFractal == Fractal.MANDELBROT) {
			for (int i = 0; i < iterations; i++) {
				result = f(result, currentComplex);
				if (result.abs() > 2) {
					return i;
				}
			}
			return -1;
		} else {
			result = currentComplex;
			for (int i = 0; i < iterations; i++) {
				result = f(result, complexConstant);
				if (result.abs() > 2) {
					return i;
				}
			}
			return -1;
		}
	}

	private static Complex f(Complex z, Complex c) {
		return z.times(z).plus(c);
	}

	protected void setIterations(int iterations) {
		this.iterations = iterations;
	}
	
	public int getIterations() {
		return this.iterations;
	}
	
	public void setSelectedFractal(Fractal selectedFractal) {
		this.selectedFractal = selectedFractal;
	}

	public void setComplexConstantX(double re) {
		complexConstant = new Complex(re, complexConstant.im());
	}

	public void setComplexConstantY(double im) {
		complexConstant = new Complex(complexConstant.re(), im);
	}

}
