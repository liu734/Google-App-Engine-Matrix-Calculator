package com.example.matrix_calculator.client;

import java.util.*;


  public class Matrix implements Cloneable {
      private double[][] grid = new double[0][0];
    
	  private int rowNumber = 0;

	  private int columnNumber = 0;
      public Matrix() {
	
      }
    
      public Matrix(double[][] grid) {
	  this.grid = grid;
      }
      
      
    public Matrix(int i, int j) {
		  if ((i <= 0) || (j <= 0)) {
		  
			  return;
		  }
		  rowNumber = i;
		  columnNumber = j;
		  grid = new double[rowNumber][columnNumber];
	  }
	  
    
      public Matrix(String matrixString) throws IllegalArgumentException {
	  String[] rows = matrixString.split("\n");
	  rowNumber = rows.length; // rowNumber is for row number
	
	  int[] lengthOfRows = new int[rows.length];
	  for (int i = 0; i < rows.length; i++) {
	      lengthOfRows[i] = (rows[i].split(" ")).length;
	  }
	  for (int i = 0; i < lengthOfRows.length; i++) {
	      for (int j = i + 1; j < lengthOfRows.length; j++) {
		  if (lengthOfRows[i] != lengthOfRows[j])
		      throw new IllegalArgumentException("Every row must contain same number of element");
	      }
	  }
	  columnNumber = lengthOfRows[0]; // columnNumber is for column number
	
	  String[][] element = new String[rowNumber][columnNumber];
	  for(int i = 0; i < rowNumber; i++) {
	      element[i] = rows[i].split(" ");
	  }
	
	  grid = new double[rowNumber][columnNumber];
	  for(int i = 0; i < rowNumber; i++) {
	      for(int j = 0; j < columnNumber; j++) {
		  try {
		      grid[i][j] = Double.parseDouble(element[i][j]);
		  }
		  catch (NumberFormatException ex) {
		      throw new IllegalArgumentException("Every element in matrix must be double number");
		  }
	      }
	  }
      }
    
      	public void setLength(int len) {
		if (len <= 0) {
		
			return;
		}
		rowNumber = len;
		
	}
	public void setWide(int wid) {
		if (wid <= 0) {
			return;
		}
		columnNumber = wid;
	
	}
    
      // return element in m row n column
      public double getElement(int m, int n) {
	  return grid[m][n];
      }
      public double getElement2(int i, int j) {
		return grid[i - 1][j - 1];
	}
    
      public double[][] getGrid() {
	  return grid;
      }
    
      public int getRowNumber() {
	  return grid.length;
      }
    
      public int getColumnNumber() {
	  return grid[0].length;
      }
    
      // calculate addition of two matrix. usage: m1.add(m2) means m1 + m2
      public Matrix add(Matrix secondMatrix) throws IllegalArgumentException {
	  if (getRowNumber() == secondMatrix.getRowNumber()
	      || getColumnNumber() == secondMatrix.getColumnNumber()) {
	      double[][] newGrid = new double[getRowNumber()][getColumnNumber()];
	    
	      for (int i = 0; i < getRowNumber(); i++) {
		  for (int j = 0; j < getColumnNumber(); j++) {
		      newGrid[i][j] = grid[i][j] + secondMatrix.getElement(i, j);
		  }
	      }
	    
	      return new Matrix(newGrid);
	  }
	  else {
	      throw new IllegalArgumentException("Two matrice must have same row number and column number");
	  }
      }
	public Matrix minus(Matrix secondMatrix) throws IllegalArgumentException {
	  if (getRowNumber() == secondMatrix.getRowNumber()
	      || getColumnNumber() == secondMatrix.getColumnNumber()) {
	      double[][] newGrid = new double[getRowNumber()][getColumnNumber()];
	    
	      for (int i = 0; i < getRowNumber(); i++) {
		  for (int j = 0; j < getColumnNumber(); j++) {
		      newGrid[i][j] = grid[i][j] - secondMatrix.getElement(i, j);
		  }
	      }
	    
	      return new Matrix(newGrid);
	  }
	  else {
	      throw new IllegalArgumentException("Two matrice must have same row number and column number");
	  }
      }
    
      // calculate multiplicaton of two matrix. usage: m1.multiply(m2) means m1 * m2
      public Matrix multiply(Matrix secondMatrix) throws IllegalArgumentException {
	  if (getColumnNumber() == secondMatrix.getRowNumber()) {
	      double[][] newGrid = new double[getRowNumber()][secondMatrix.getColumnNumber()];
	    
	      for (int i = 0; i < getRowNumber(); i++) {
		  for (int j = 0; j < secondMatrix.getColumnNumber(); j++) {
		      newGrid[i][j] = 0;
		      for (int x = 0; x < getColumnNumber(); x++) {
			      newGrid[i][j] += getElement(i, x) * secondMatrix.getElement(x, j);
		      }
		  }
	      }
	    
	      return new Matrix(newGrid);
	  }
	  else {
	      throw new IllegalArgumentException("In matrix multiplication, the column number of first matrix must equal the row number of second matrix");
	  }
      }
    
      // use recursive method to calculate determinant of matrix
      public double determinant() throws Exception {
	  if (getRowNumber() == getColumnNumber()) {
	      if (getRowNumber() == 1)
		  return grid[0][0];
	      else {
		  double det = 0;
		
		  for (int i = 0; i < getColumnNumber(); i++) {
		      double[][] detAGrid = new double[getRowNumber() - 1][getColumnNumber() - 1];
		    
		      for (int x = 1; x < getRowNumber(); x++) {
			  for (int y = 0; y < getColumnNumber(); y ++) {
			      if (y < i)
				  detAGrid[x - 1][y] = grid[x][y];
			      else if (y > i)
				  detAGrid[x - 1][y - 1] = grid[x][y];
			  }
		      }
		    
		      Matrix detA = new Matrix(detAGrid);
		      det += Math.pow(-1, i+2) * grid[0][i] * detA.determinant();
		  }
		
		  return det;
	      }
	  }
	  else {
	      throw new Exception("Only matrix with same row and column number can have determinant");
	  }
      }
    
      // get the length of longest element(element's integer part)
      public int getLongestLength() {
	  int maxIntegerPart = (int)getMaxElement();
	  int minIntegerPart = (int)getMinimumElement();
	
	  int maxLength = ((new Integer(maxIntegerPart)).toString()).length();
	  int minLength = ((new Integer(minIntegerPart)).toString()).length();
	
	  if (maxLength >= minLength) {
	      return maxLength;
	  }
	  else {
	      return minLength;
	  }
      }

      // return the largest element in matrix
      public double getMaxElement() {
	  double currentMax = grid[0][0];
	
	  for (int i = 0; i < grid.length; i++) {
	      for (int j = 0; j < grid[0].length; j++) {
		  if (currentMax < grid[i][j])
		      currentMax = grid[i][j];
	      }
	  }
	
	  return currentMax;
      }

      // return the minimum element in matrix
      public double getMinimumElement() {
	  double currentMinimum = grid[0][0];
	
	  for (int i = 0; i < grid.length; i++) {
	      for (int j = 0; j < grid[0].length; j++) {
		  if (currentMinimum > grid[i][j])
		      currentMinimum = grid[i][j];
	      }
	  }
	
	  return currentMinimum;
      }
      
      
      

      public String toString() {
	  String matrixString = "";
	
	  int longestLength = getLongestLength();
	
	  for (int i = 0; i < getRowNumber(); i++) {
	      for (int j = 0; j < getColumnNumber(); j++) {
		  matrixString += Double.toString( grid[i][j]) + " ";
	      }
	      matrixString += "\n";
	  }
	
	  return matrixString;
      }
    
     // public Object clone() throws CloneNotSupportedException {
	 //     return super.clone();
     // }

      public void setElement(double data, int i, int j) {
		  if ((i < 1) || (j < 1) || (i > rowNumber) || (j > columnNumber)) {
			  
			  return;
		  }
		  grid[i - 1][j - 1] = data;

	  }
      public void chgRow(int chgfrom, int chgto) {
		 
		  if ((chgfrom > rowNumber) || (chgto > rowNumber) || (chgfrom < 1)
				  || (chgto < 1)) {
		  
			  return;
		  }
		  
		  
		  double[] temp = new double[columnNumber];
		  for (int i = 0; i < columnNumber; i++) {
			  temp[i] = grid[chgfrom - 1][i];
			  grid[chgfrom - 1][i] = grid[chgto - 1][i];
			  grid[chgto - 1][i] = temp[i];
		  }
	  }
	  
	  
	  
	  
	  
	  
  public Matrix rev(Matrix matrix){
		  int len = matrix.getRowNumber();
		  int wid = matrix.getColumnNumber();
		  if((len <= 0) || (wid <= 0) || (len != wid)){
			  
			  return null;
		  }
	  
		  Matrix matrixA = new Matrix(len,wid*2);
		  for (int i=1; i<=len; i++)
			  for (int j=1; j<=wid; j++){
			  matrixA.setElement(matrix.getElement2(i,j),i,j);
		  }
		  
		  for (int i=1; i<=len; i++)
			  for (int j=wid+1; j<=wid*2; j++){
			  if (i==j-wid){
				  matrixA.setElement(1,i,j);
			  }
			  else 
				  matrixA.setElement(0,i,j);
		  }
	  
		  for (int i=1; i<=len; i++){
			  double max = matrixA.getElement2(i,i);
			  double absmax = Math.abs(max);
			  
			  int maxrow = i;
			  for (int j=i; j<=len; j++){
				  double max2 = matrixA.getElement2(j,i);
				  double absmax2 = Math.abs(max2);
				  if (absmax2>absmax){
					  maxrow = j;
					  max = max2;
					  absmax = absmax2;
				  }
			  }
			  System.out.println(max);
			  if (absmax==0){
			  
				  return null;
			  }
			  matrixA.chgRow(i,maxrow);
			  //return matrixA;
			  
			  for (int k=1; k<=wid*2; k++){
				  double temp = matrixA.getElement2(i,k) / max;
				  matrixA.setElement(temp,i,k);
			  }
		  
			  for (int m=1; m<i; m++){
				  double para = matrixA.getElement2(m,i);
				  if (para!=0){
					  for (int n=1; n<=wid*2; n++){
						  double num = matrixA.getElement2(m,n);
						  num = num - matrixA.getElement2(i,n) * para;
						  matrixA.setElement(num,m,n);
					  }
				  }
			  }

			  
			  for (int m=i+1; m<=len; m++){
				  double para = matrixA.getElement2(m,i);
				  if (para!=0){
					  for (int n=1; n<=wid*2; n++){
						  double num = matrixA.getElement2(m,n);
						  num = num - matrixA.getElement2(i,n) * para;
						  matrixA.setElement(num,m,n);
					  }
				  }
			  }
			  //return matrixA;
		  }
		  
		  Matrix matrixB = new Matrix(len, wid);
		  for (int i = 1; i <= len; i++)
			  for (int j = wid+1; j <= wid*2; j++) {
				  double temp = matrixA.getElement2(i,j);
				  matrixB.setElement(temp,i,j-wid);
			  }
		  return matrixB;
	  }	

      public static Matrix turn(Matrix matrix1) {
		Matrix resultTurn = null;
		int len = matrix1.rowNumber;
		int wid = matrix1.columnNumber;
		if ((len <= 0) || (wid <= 0)) {
		
			return null;
		}
		resultTurn = new Matrix(wid,len);
		resultTurn.setLength(wid);
		resultTurn.setWide(len);
		for (int i = 1; i <= wid; i++) {
			for (int j = 1; j <= len; j++) {
				double temp = matrix1.getElement2(j, i);
				resultTurn.setElement(temp, i, j);
			}
		}
		return resultTurn;
	}
      
      
      
      public static void main(String [] args){
      String a="1 0 0\n0 2 0\n1 0 6";
      String b="1 0 0\n0 2 0\n0 0 6";
      Matrix m=new Matrix(a);
      Matrix n=new Matrix(b);

      Matrix n1=m.minus(n);				//minus	
      Matrix n3=m.add(n);        			//addition
      Matrix n2=m.multiply(n);  			// multiplication
      Matrix n4=m.rev(m);   	  			//reverse;
      Matrix n5=m.turn(m);				//Transpose
       System.out.println(n5.toString());
     System.out.println(n4.toString());
      System.out.println(n2.toString());
      System.out.println(n3.toString());
      System.out.println(n1.toString());
  //  }catch(Exception e){System.out.println("Fail to calculate");}
      try{
      System.out.println(m.determinant());
      }catch(Exception e){;}
      }
    
  }