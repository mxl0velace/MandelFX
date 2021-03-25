package application;

public class ComplexNumber {
	
	protected double real;
	protected double img;

	public ComplexNumber(double real, double img) {
		this.real = real;
		this.img = img;
	}
	
	public ComplexNumber multiply(ComplexNumber other) {
		double r = other.real * this.real - other.img * this.img;
		double i = other.real * this.img + other.img * this.real;
		return new ComplexNumber(r,i);
	}
	
	public ComplexNumber add(ComplexNumber other) {
		double r = other.real + this.real;
		double i = other.img + this.img;
		return new ComplexNumber(r,i);
	}
	
	public double magnitude() {
		return Math.sqrt(real * real + img * img);
	}
	
	public double getReal() {
		return real;
	}
	
	public double getImg() {
		return img;
	}

}
