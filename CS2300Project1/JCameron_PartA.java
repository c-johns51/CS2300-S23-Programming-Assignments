import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.Math;

public class JCameron_PartA {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File inputFile = new File("test_input.txt");
		
		double[] eyeLocation = new double[3];
		double[] lightDirection = new double[3];
		
		readFileValues(inputFile, eyeLocation, lightDirection);
		
		Triangle[] trianglesList = cullTriangles(inputFile, numTriangles(inputFile), eyeLocation, lightDirection);
		
		
		
		
		
	}
	
	public static void readFileValues(File inputFile, double[] eyeLocation, double[] lightDirection) throws IOException {
		
		Scanner readInput = new Scanner(inputFile);
		
		// For loop that reads in the first three doubles for the eye location
		for(int i = 0; i < eyeLocation.length; i++) {
			
			eyeLocation[i] = readInput.nextDouble();
			
		}
		
		// For loop that reads in the second three doubles for the light direction
		for(int i = 0; i < lightDirection.length; i++) {
			
			lightDirection[i] = readInput.nextDouble();
			
		}
		
		readInput.close();
		
	}
	
	public static int numTriangles(File inputFile) throws IOException {
		
		int numTriangles = 0;
		int linesSkipped = 0;
		
		Scanner readLines = new Scanner(inputFile);
		
		while(readLines.hasNext()) {
			
			readLines.nextLine();
			
			if(linesSkipped < 1) {
				
				linesSkipped++;
				
			}
			else {
				
				numTriangles++;
				
			}
			
		}
		
		readLines.close();
		
		
		
		return numTriangles;
		
		
	}
		
	public static Triangle[] cullTriangles(File inputFile, int numTriangles, double[] eyeLocation, double[] lightDirection) throws IOException {
		
		Triangle[] trianglesToBeCulled = new Triangle[numTriangles];
		int currentTriangle = 0;
		
		Scanner readTriangles = new Scanner(inputFile);
		
		// Skip first line of file
		readTriangles.nextLine();
		
		while(readTriangles.hasNext()) {
			
			Triangle triangle = new Triangle();
			
			// Create first point of triangle (p)
			double[] pTri = new double[3];
			pTri[0] = readTriangles.nextDouble();
			pTri[1] = readTriangles.nextDouble();
			pTri[2] = readTriangles.nextDouble();
			
			triangle.setP(pTri);
			
			// Creates second point of triangle (q)
			double[] qTri = new double[3];
			qTri[0] = readTriangles.nextDouble();
			qTri[1] = readTriangles.nextDouble();
			qTri[2] = readTriangles.nextDouble();
			
			triangle.setQ(qTri);
			
			// Creates third point of triangle (r)
			double[] rTri = new double[3];
			rTri[0] = readTriangles.nextDouble();
			rTri[1] = readTriangles.nextDouble();
			rTri[2] = readTriangles.nextDouble();
			
			triangle.setR(rTri);
			
			// readTriangles.nextLine();
			
			// Creates the centroid of the triangle based on the triangles given p, q, and r
			double[] centroid = new double[3];
			centroid[0] = (pTri[0] + qTri[0] + rTri[0]) / 3;
			centroid[1] = (pTri[1] + qTri[1] + rTri[1]) / 3;
			centroid[2] = (pTri[2] + qTri[2] + rTri[2]) / 3;
			
			double[] viewVector = new double[3];
			
			// Creates a vector produced from taking the eyeLocation vector and subtracting the centroid vector from it (e-c)
			double[] eyeMinusCentroid = {eyeLocation[0] - centroid[0], eyeLocation[1] - centroid[1], eyeLocation[2] - centroid[2]};
			// Finds the length of the resulting vector from (eyeLocation - centroid) ||e-c||
			double eyeMinusCentroidLength = Math.sqrt(Math.pow(eyeMinusCentroid[0], 2) + Math.pow(eyeMinusCentroid[1], 2) + Math.pow(eyeMinusCentroid[2], 2));
			
			// Creates a vector resulting from the eyeMinusCentroid vector divided by its length (e-c)/||e-c||
			viewVector[0] = eyeMinusCentroid[0] / eyeMinusCentroidLength;
			viewVector[1] = eyeMinusCentroid[1] / eyeMinusCentroidLength;
			viewVector[2] = eyeMinusCentroid[2] / eyeMinusCentroidLength;
			
			// Creates vectors u and w to be used in a cross product, u = q - p    w = r - p
			double[] vectorU = {qTri[0] - pTri[0], qTri[1] - pTri[1], qTri[2] - pTri[2]};
			double[] vectorW = {rTri[0] - pTri[0], rTri[1] - pTri[1], rTri[2] - pTri[2]};
			
			// Finds the vector produced by the cross product of u and w (u ^ w)
			double[] crossProduct = {(vectorU[1] * vectorW[2]) - (vectorW[1] * vectorU[2]), (vectorU[0] * vectorW[2]) 
					- (vectorW[0] * vectorU[2]), (vectorU[0] * vectorW[1]) - (vectorW[0] * vectorU[1])};
			
			// Finds the length of the u ^ w vector
			double crossProductLength = Math.sqrt(Math.pow(crossProduct[0], 2) + Math.pow(crossProduct[1], 2) + Math.pow(crossProduct[2], 2));
			
			// Creates the vector normal to the triangle (u ^ w)/||u ^ w||
			double[] normalVector = {crossProduct[0] / crossProductLength, crossProduct[1] / crossProductLength, crossProduct[2] / crossProductLength};
			
			triangle.setNorm(normalVector);
			
			// Calculates the dot product of the normal vector and the view vector (n . v)
			double dotProduct = (normalVector[0] * viewVector[0]) + (normalVector[1] * viewVector[1]) + (normalVector[2] * viewVector[2]);
			
			// If the dot product is less than or equal to 0, the triangle is front facing
			if(dotProduct <= 0) {
				
				triangle.setCull(0);
				
			}
			// Otherwise, the triangle is back facing
			else {
				
				triangle.setCull(1);
				
			}
			
			trianglesToBeCulled[currentTriangle] = triangle;
			
			currentTriangle++;
			
		}
		
		readTriangles.close();
		return trianglesToBeCulled;
		
	}
	
	public static double[] calculateIntensity(Triangle[] triangles, double[] lightDirection) {
		
		double[] lightIntensities = new double[triangles.length];
		
		double[] direction = {lightDirection[0] * -1, lightDirection[1] * -1, lightDirection[2] * -1};
		
		double directionLength = Math.sqrt(Math.pow(direction[0], 2) + Math.pow(direction[1], 2) + Math.pow(direction[2], 2));
		
		for(int i = 0; i < triangles.length; i++) {
			
			double dotProduct = (direction[0] * triangles[i].getNorm(0)) + (direction[1] * triangles[i].getNorm(1)) + (direction[2] * triangles[i].getNorm(2));
			
			double triangleNormLength = Math.sqrt(Math.pow(triangles[i].getNorm(0), 2) + Math.pow(triangles[i].getNorm(1), 2) + Math.pow(triangles[i].getNorm(2), 2));
			
			double intensity = dotProduct / (triangleNormLength * directionLength);
			
			lightIntensities[i] = intensity;
			
		}
		
		return lightIntensities;
		
	}
}

class Triangle {
	
	private double[] pPos;
	private double[] qPos;
	private double[] rPos;
	private double[] normVec;
	private int isCulled = 0;
	
	public Triangle() {
		
		
	}
	
	public void setP(double[] pPos) {
		
		this.pPos = pPos;
		
	}
	
	public void setQ(double[] qPos) {
		
		this.qPos = qPos;
		
	}
	
	public void setR(double[] rPos) {
		
		this.rPos = rPos;
		
	}
	
	public void setNorm(double[] normVec) {
		
		this.normVec = normVec;
		
	}
	
	public void setCull(int isCulled) {
		
		this.isCulled = isCulled;
		
	}
	
	public double getP(int index) {
		
		return pPos[index];
		
	}
	
	public double getQ(int index) {
		
		return qPos[index];
		
	}
	
	public double getR(int index) {
		
		return rPos[index];
		
	}
	
	public double getNorm(int index) {
		
		return normVec[index];
		
	}
	
	public boolean getCull() {
		
		boolean result = false;
		
		if(isCulled == 0) {
			
			result = true;
			
		}
		
		return result;
		
	}
	
}


