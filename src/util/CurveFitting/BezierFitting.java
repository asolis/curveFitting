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



public class BezierFitting  extends Fitting{
	

	@Override
	public ArrayList<Shape> fitCurve(ArrayList<Point2D> pts) {
		idxs   = new LinkedList<Integer>();
		knots  = new LinkedList<Integer>();
		knots.add(0); knots.add(pts.size()-1);
		points = pts;
		try {
			curve  = new Bezier(points,knots);
		} catch (CurveCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		// index of the point with max distance to the bezier curve
		int index = maxIndex(points, 0, points.size()-1, curve.getCurveAt(0));
		if (index != -1) idxs.add(index);
		while (!idxs.isEmpty()){
			int j  = curve.AddIndex(idxs.poll());
			
			index = maxIndex(points,knots.get(j-1),knots.get(j),curve.getCurveAt(j-1));
			if (index != -1) idxs.add(index);
			index = maxIndex(points,knots.get(j),knots.get(j+1),curve.getCurveAt(j));
			if (index != -1) idxs.add(index);
		}	
		// TODO Auto-generated method stub
		return curve.getCurves();
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Quad + Cubic Bezier Curves";
	}
}
