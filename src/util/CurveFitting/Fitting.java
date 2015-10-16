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

public abstract class Fitting {
	double THRESHOLD = 25;
	double PIXEL_STEPS = 5;
	public Interpolation curve; 
	
	protected LinkedList<Integer> idxs = new LinkedList<Integer>();
	protected LinkedList<Integer> knots = new LinkedList<Integer>();
	protected ArrayList<Point2D>  points ;
	
	public abstract String getLabel();
	public abstract ArrayList<Shape> fitCurve(ArrayList<Point2D> pts);
	
	protected int maxIndex(ArrayList<Point2D> p,
			 int init,
			 int end, 
			 CubicCurve2D curve) {
		int index = -1;
		if (init     >  end)         return index;
		if (end-init <= PIXEL_STEPS) return index;
		double max=0;
		for (int i = init+1; i < end; i++){
			Point2D pn = new Point2D.Double();
			double sqDis = NearestPoint.onCurve(curve,p.get(i),pn );
			if (sqDis > max){
				max = sqDis;
				index = i;
			}
		}
		return (max > THRESHOLD)?index: -1;
	}
	protected int maxIndex(ArrayList<Point2D> p,
			 int init,
			 int end, 
			 Line2D line) {
		int index = -1;
		if (init     >  end)         return index;
		if (end-init <= PIXEL_STEPS) return index;
		double max=0;
		for (int i = init+1; i < end; i++){
			Point2D pn = new Point2D.Double();
			double sqDis = NearestPoint.onLine(line.getP1(),line.getP2(),p.get(i),pn );
			if (sqDis > max){
				max = sqDis;
				index = i;
			}
		}
		return (max > THRESHOLD)?index: -1;
	}
	private boolean check() {
		
		for (int j = 0; j < knots.size()-1; j++){
			if (maxIndex(points,knots.get(j),knots.get(j+1),curve.getCurveAt(j))!=-1) return false;
		}
		return true;
	}
	
	protected void removeUnnecessaryPoints() {
		int index = 0;
		for (int j =1; j < knots.size()-1; j++){
			index = knots.get(j);
			curve.RemoveIndex(knots.get(j));
			if (check()){
				j--;
			}else {
				curve.AddIndex(index);
			}
		}
	}
}
