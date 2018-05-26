package Utility;

/*
 * Map which stores array in 3 dimensions, and is able to "shift" easily
 * shifting is defined as moving currently stored array in a direction, 
 * unloading those which are outside the bounds of the map. New content is loaded to fill
 * the space left after the shift.
 * 
 * Uses  as the primary data structure.
 */

public class ShiftingArray<T> {
	
	private T[][][] array;
	
	private Point shift;
	
	/*
	 * Returns minimum and maximum points within which objects are contained
	 * in format [minimum point, maximum point]
	 */
	@SuppressWarnings("unchecked")
	public ShiftingArray() {
		array = (T[][][]) new Object[0][0][0];
		shift = new Point();
	}
	
	public T get(int x, int y, int z) {
		Point p = getShifted(x, y, z);
		
		if (withinArray(p.x, p.y, p.z)) {
			return array[p.x][p.y][p.z];
		}
		return null;
	}
	
	public void set(T t, int x, int y, int z) {
		Point p = getShifted(x, y, z);
		
		if (withinArray(p.x, p.y, p.z)) {
			array[p.x][p.y][p.z] = t;
		} else {
			stretchArray(p);
			p = getShifted(x, y, z); //recalculate after array is resized
			array[p.x][p.y][p.z] = t;
		}
		
		trimArray();
	}
	
	@SuppressWarnings("unchecked")
	private void trimArray() {
		Point min = getMinPoint();
		Point max = getMaxPoint();
		
		//Check if trim needs to happen
		if (min.x == 0 && min.y == 0 && min.z == 0 
				&& max.x == array.length && max.y == array[0].length && max.z == array[0][0].length) {
			return;
		}
		
		//trim array
		T[][][] arr = (T[][][]) new Object[max.x - min.x][max.y - min.y][max.z - min.z];
		for (int x = 0; x < arr.length; x++) {
			for (int y = 0; y < arr[0].length; y++) {
				for (int z = 0; z < arr[0][0].length; z++) {
					arr[x][y][z] = array[x + min.x][y + min.y][z + min.z];
				}
			}
		}
		//set shift to new position
		shift.x += min.x;
		shift.y += min.y;
		shift.z += min.z;
		
		array = arr;
	}
	
	private void stretchArray(Point pos) {
		Point deltaShift = new Point(0, 0, 0);
		if (pos.x < 0) deltaShift.x = pos.x;
		if (pos.y < 0) deltaShift.y = pos.y;
		if (pos.z < 0) deltaShift.z = pos.z;
		
		Point size = new Point(array.length, array[0].length, array[0][0].length);
		if (pos.x > size.x) size.x = pos.x;
		if (pos.y > size.y) size.y = pos.y;
		if (pos.z > size.z) size.z = pos.z;
		
		expand(size, deltaShift);
		
		shift.x += deltaShift.x;
		shift.y += deltaShift.y;
		shift.z += deltaShift.z;
	}
	
	@SuppressWarnings("unchecked")
	private void expand(Point size, Point deltaShift) {
		T[][][] arr = (T[][][]) new Object[size.x][size.y][size.z];
		for (int x = 0; x < array.length; x++) {
			for (int y = 0; y < array[0].length; y++) {
				for (int z = 0; z < array[0][0].length; z++) {
					int tempX = x - deltaShift.x;
					int tempY = y - deltaShift.y;
					int tempZ = z - deltaShift.z;
					arr[tempX][tempY][tempZ] = array[x][y][z];
				}
			}
		}
		array = arr;
	}
	
	private Point getMaxPoint() {
		int maxX = 0;
		int maxY = 0;
		int maxZ = 0;
		
		for (int x = 0; x < array.length; x++) {
			for (int y = 0; y < array[0].length; y++) {
				for (int z = 0; z < array[0][0].length; z++) {
					if (array[x][y][z] != null) {
						if (x > maxX) maxX = x;
						if (y > maxY) maxY = y;
						if (z > maxZ) maxZ = z;
					}
				}
			}
		}
		
		return new Point(maxX, maxY, maxZ);
	}
	
	private Point getMinPoint() {
		int minX = array.length;
		int minY = array[0].length;
		int minZ = array[0][0].length;
		
		for (int x = 0; x < array.length; x++) {
			for (int y = 0; y < array[0].length; y++) {
				for (int z = 0; z < array[0][0].length; z++) {
					if (array[x][y][z] != null) {
						if (x < minX) minX = x;
						if (y < minY) minY = y;
						if (z < minZ) minZ = z;
					}
				}
			}
		}
		
		return new Point(minX, minY, minZ);
	}
	
	private Point getShifted(int x, int y, int z) {
		return new Point(x - shift.x, y - shift.y, z - shift.z);
	}
	
	private boolean withinArray(int x, int y, int z) {
		return x < 0 || y < 0 || z < 0
				|| x >= array.length || y >= array[0].length || z >= array[0][0].length;
	}
	
	class Point {
		public int x;
		public int y;
		public int z;
		
		public Point() {
			x = 0;
			y = 0;
			z = 0;
		}
		
		public Point(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
