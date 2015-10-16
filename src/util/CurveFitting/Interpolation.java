// ==========================================================================
// $Id$
// ==========================================================================
// (C)opyright:
//
//   Andres Solis Montero
//   SITE, University of Ottawa
//   800 King Edward Ave.
//   Ottawa, On., K1N 6N5
//   Canada.
//   http://www.site.uottawa.ca
//
// Creator: asolis (Andres Solis Montero)
// Email:   asoli094@uottawa.ca
// ==========================================================================
// $Rev$
// $LastChangedBy$
// $LastChangedDate$
//
// ==========================================================================
package util.CurveFitting;

import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Interpolation {
	
	protected Point2D[] cP;	
    protected ArrayList<Point2D> points;    // data points
    protected LinkedList<Integer> index;    // index to interpolate
 
    protected abstract void compute();
    
	public void setData(ArrayList<Point2D> pts_,
						LinkedList<Integer> idx) throws CurveCreationException {
		this.index = idx;
		this.points = pts_;
		
		if (points == null)
		throw new CurveCreationException("Empty point set to interpolate");
		if (index != null)
		java.util.Collections.sort(index); 
		if (N() < 2)
		throw new CurveCreationException("Two knots requiered to interpolate");
		this.cP = new Point2D[(pts_.size())*2];
		compute();
	
	} 
   
    //Return the ith interpolation point. 
	protected Point2D get(int i) {
		if (i < 0 || i >= N() ) throw 
			new IndexOutOfBoundsException(
					 String.format("Interpolation Class: index %d is not valid" , i));
		
		if (index == null) {
			return points.get(i);
		}
		else {
			int temp = index.get(i);
			if (temp < 0 || temp >= points.size())
				throw new IndexOutOfBoundsException(
						 String.format("Interpolation Class: index %d is not valid." +
						 			   " Index list is not sync" , i));
			return points.get(index.get(i));
		}
			
	}
	
	//Return the number of interpolation points
	protected int N(){
		return (index == null)?points.size() : index.size();
	}
	
	//Add index to interpolation
	public int AddIndex(int i){
		if (i < 0 ) return -1;			
		if (this.index == null){
			this.index = new LinkedList<Integer>();
			this.index.add(0);
			this.index.add(points.size()-1);
		}
		for (int j = 0; j < index.size(); j++){
			if (index.get(j) == i) return -1;
			if (index.get(j) < i) continue;
			else {
				index.add(j, i);
				compute();
				return j;
			}
		}
		return -1;
	}
	//Remove index from interpolation
	public int RemoveIndex(int i){
		if (i < 0 ) return -1;
		if (this.index == null || this.index.size() <3){
			
		}else {
			for (int j = 0; j < index.size(); j ++)
			{
				if (index.get(j)==i) {
					index.remove(j);
					compute();
					return j;
				}
			}
		}
		return -1;
	}
    
	//Return the interpolation points
	public ArrayList<Point2D> getKnots(){
		if (index != null){
			ArrayList<Point2D> tmp = new ArrayList<Point2D>(index.size());
			for (int i = 0 ; i < index.size(); i++)
			{
				tmp.add( get(i));
			}
			return tmp;
		} 
		else 
			return points;
	}
	
	public CubicCurve2D getCurveAt(int i){
		if (i < 0 || i >= N() - 1 ) throw 
		new IndexOutOfBoundsException(
				 String.format("Interpolation Class: cannot " +
				 		       "retrieve curve with index : %d" , i));
		CubicCurve2D.Double cubic = new CubicCurve2D.Double(
													  get(i).getX(),
													  get(i).getY(),
													  cP[2*i].getX(),
													  cP[2*i].getY(),
													  cP[2*i+1].getX(),
													  cP[2*i+1].getY(), 
								                      get(i+1).getX(), 
								                      get(i+1).getY());
		return cubic;
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
    		  CubicCurve2D.Double cubic = new CubicCurve2D.Double(get(i).getX(),
    				  											  get(i).getY(),
    				  											  cP[2*i].getX(),
    															  cP[2*i].getY(),
    															  cP[2*i+1].getX(),
    															  cP[2*i+1].getY(), 
    				                                              get(i+1).getX(), 
    				                                              get(i+1).getY());
    		  s.add(cubic);
    	  }	    	  
            
          return s;
    }
	public int getIndex(int i){
		if (index == null) 
			return i;
		else 
			return index.get(i);
	}
}
