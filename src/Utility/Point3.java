package Utility;

public class Point3 {
	public double x, y, z;
	public Point3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double magnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public String toString() {
		double tempx = x - x%0.01;
		double tempy = y - y%0.01;
		double tempz = z - z%0.01;
		return "(" + tempx + ", " + tempy + ", " + tempz + ")";	
	}
}
