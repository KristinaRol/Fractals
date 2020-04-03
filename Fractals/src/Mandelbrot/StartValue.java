package Mandelbrot;

public enum StartValue {
	
	ONE(new Complex(0,0.8),"0 + 0.8i"),
	TWO(new Complex(0.37,0.1),"0.37 + 0.1i"),
	THREE(new Complex(0.355,0.355),"0.355 + 0.355i"),
	FOUR(new Complex(-0.54,0.54),"-0.54 + 0.54i"),
	FIVE(new Complex(-0.4,-0.59),"-0.4 + -0.59i"),
	SIX(new Complex(0.34,-0.05),"0.34 + -0.05i"),
	SEVEN(new Complex(0.355534,- 0.337292),"0.3555 - 0.3372i"),
	EIGHT(new Complex(1.618033-2,0),"-0.38");
	
	Complex complex;
	String name;
	
	private StartValue(Complex complex, String name) {
		this.complex = complex;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
