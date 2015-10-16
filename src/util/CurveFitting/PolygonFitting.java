package util.CurveFitting;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class PolygonFitting extends Fitting {
	
	@Override
	public ArrayList<Shape> fitCurve(ArrayList<Point2D> pts) {
		THRESHOLD = 50;
		idxs   = new LinkedList<Integer>();
		knots  = new LinkedList<Integer>();
		knots.add(0); knots.add(pts.size()-1);
		points = pts;
		try {
			curve  = new LeastSquareLine(points,knots);
		} catch (CurveCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		int index = maxIndex(points, 0, points.size()-1, curve.getLineAt(0));
		if (index != -1) idxs.add(index);
		
		while (!idxs.isEmpty()){
			
			int j  = curve.AddIndex(idxs.poll());
				
			index = maxIndex(points,knots.get(j-1),knots.get(j),curve.getLineAt(j-1));
			if (index != -1) idxs.add(index);
			
			index = maxIndex(points,knots.get(j),knots.get(j+1),curve.getLineAt(j));

			if (index != -1)idxs.add(index);
			
		}
		
		removeUnnecessaryPoints();
		return curve.getCurves();
	}
	
	private boolean check() {
		
		for (int j = 0; j < knots.size()-1; j++){
			if (maxIndex(points,knots.get(j),knots.get(j+1),curve.getLineAt(j))!=-1) return false;
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
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Polygon Fitting";
	}
}