package util.CurveFitting;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class LeastSquareLine extends Interpolation {

		
		public LeastSquareLine(ArrayList<Point2D> pts_, LinkedList<Integer> index) throws CurveCreationException{
			setData(pts_, index);
		}
		protected void compute(){
			//no need for approximation right now
			
//			this.cP = new Point2D[(N()-1)];
//			for (int i = 0; i < N()-1; i ++){
//				cP[2*i]     = A(getIndex(i),getIndex(i+1));
//			}	
		}	

		// Least square estimation of a ( y  = ax + b)
		// A = (sum(y)*sum(x^2)-sum(x)sum(x*y))/ (n*sum(x^2)-sum(x)^2);
		// B = (n*sum(x*y)-sum(x)*sum(y))/(n*sum(x^2)-sum(x)^2);
		private Point2D A(int init,int end){
			
			double A = sumY(init,end)*sumXSq(init,end);
				   A -= sumX(init,end)*sumXY(init,end);
			double den= (end-init+1)*sumXSq(init,end)-Math.pow(sumX(init,end), 2.0);
				   A /= den;
			double B = (end-init+1)*sumXY(init,end);
				   B -= sumX(init,end)*sumY(init,end);
				   B /= den;	   
			 
			return new Point2D.Double(A,B);		
		}
	
		private double sumX(int init,int end){
			int sum =0;
			for (int i = init; i <= end; i++){
				sum+=points.get(i).getX();
			}
			return sum;
		}
		private double sumXY(int init,int end){
			int sum =0;
			for (int i = init; i <= end; i++){
				sum+=points.get(i).getX()*points.get(i).getY();
			}
			return sum;
		}
		private double sumY(int init,int end){
			int sum =0;
			for (int i = init; i <= end; i++){
				sum+=points.get(i).getY();
			}
			return sum;
		}
		private double sumXSq(int init,int end){
			int sum =0;
			for (int i = init; i <= end; i++){
				sum+=Math.pow(points.get(i).getX(),2.0);
			}
			return sum;
		}
		private double sumYSq(int init,int end){
			int sum =0;
			for (int i = init; i <= end; i++){
				sum+=Math.pow(points.get(i).getY(),2.0);
			}
			return sum;
		}
	
		
		public Line2D getLineAt(int i){
			if (i < 0 || i >= N() - 1 ) throw 
			new IndexOutOfBoundsException(
					 String.format("Interpolation Class: cannot " +
					 		       "retrieve line with index : %d" , i));
			Line2D.Double line = new Line2D.Double(
														  get(i).getX(),
														  get(i).getY(),
														  get(i+1).getX(), 
									                      get(i+1).getY());
			return line;
		}
		
		public ArrayList<Shape> getCurves(){
	    	  ArrayList<Shape> s = new ArrayList<Shape>();
	    	
	    	  for (int i = 0; i < N()-1; i++)
	    	  {        	
	    		  Line2D.Double line = new Line2D.Double(get(i).getX(),
	    				  								  get(i).getY(),
	    				                                  get(i+1).getX(), 
	    				                                  get(i+1).getY());
	    		  s.add(line);
	    	  }	    	  
	            
	          return s;
	    }
		
}
