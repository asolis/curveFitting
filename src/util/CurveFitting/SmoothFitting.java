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




public class SmoothFitting extends Fitting {

	@Override
	public ArrayList<Shape> fitCurve(ArrayList<Point2D> pts) {
		idxs   = new LinkedList<Integer>();
		knots  = new LinkedList<Integer>();
		knots.add(0); knots.add(pts.size()-1);
		points = pts;
		try {
			curve  = new SmoothBezier(points,knots);
		} catch (CurveCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		int index = maxIndex(points, 0, points.size()-1, curve.getCurveAt(0));
		
		while (index != -1){
			curve.AddIndex(index);
			for (int i = 0; i < knots.size()-1; i++){
				index  = maxIndex(points,knots.get(i),knots.get(i+1),curve.getCurveAt(i));
				if (index != -1) break;
			}
		}
		
		removeUnnecessaryPoints();
		return curve.getCurves();
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Smooth Fitting";
	}
}
