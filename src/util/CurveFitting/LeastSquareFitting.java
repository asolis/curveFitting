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
import java.util.Arrays;
import java.util.LinkedList;




public class LeastSquareFitting extends Fitting {
	
	
	@Override
	public ArrayList<Shape> fitCurve(ArrayList<Point2D> pts){
		idxs   = new LinkedList<Integer>();
		knots  = new LinkedList<Integer>();
		knots.add(0); knots.add(pts.size()-1);
		points = pts;
		try {
			curve  = new LeastSquareBezier(points,knots);
		} catch (CurveCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		int index = maxIndex(points, 0, points.size()-1, curve.getCurveAt(0));
		if (index != -1) idxs.add(index);
		
		while (!idxs.isEmpty()){
			
			int j  = curve.AddIndex(idxs.poll());
				
			index = maxIndex(points,knots.get(j-1),knots.get(j),curve.getCurveAt(j-1));
			if (index != -1) idxs.add(index);
			
			index = maxIndex(points,knots.get(j),knots.get(j+1),curve.getCurveAt(j));

			if (index != -1)idxs.add(index);
			
		}
		
		removeUnnecessaryPoints();
		return curve.getCurves();
	}
	
	private boolean check(int j) {
		
		return (maxIndex(points,knots.get(j),knots.get(j+1),curve.getCurveAt(j))==-1);
	}
	
	@Override
	protected void removeUnnecessaryPoints() {
		int index = 0;
		for (int j =1; j < knots.size()-1; j++){
			index = knots.get(j);
			
			Point2D pj   = curve.cP[2*curve.getIndex(j)];    
			Point2D pj_2 = curve.cP[2*curve.getIndex(j) + 1]; 
			
			Point2D pj_1   = curve.cP[2*curve.getIndex(j-1)];    
			Point2D pj_2_1 = curve.cP[2*curve.getIndex(j-1) + 1]; 
		
			
			curve.RemoveIndex(knots.get(j));
			if (check(j-1)){
				j--;
			}else {
				((LeastSquareBezier)curve).AddIndex(index,pj,pj_2,pj_1,pj_2_1);
				
			}
		}
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Least Square Fitting";
	}
}
