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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;


public class SmoothBezier extends Interpolation
{
		//First derivative and second derivative are equals.
		public SmoothBezier(){
			cP = new Point2D[0];
			points = new ArrayList<Point2D>();
			index  = new LinkedList<Integer>();
		}
		
		public SmoothBezier(ArrayList<Point2D> points,
						    LinkedList<Integer> index) throws CurveCreationException
		{			
			setData(points, index);
		}
		
		protected void compute() {				
			int n = N()-1;
			if (n == 1)
			{ 
				cP  = new Point2D[2];
				// 3P1 = 2P0 + P3
				cP[0] = new Point2D.Double(
						                (2 * get(0).getX() + get(1).getX()) / 3,
						                (2 * get(0).getY() + get(1).getY()) / 3);
				// P2 = 2P1 + P0
				cP[1] = new Point2D.Double(
						                 (2 * cP[0].getX() - get(0).getX()),
						                 (2 * cP[0].getY() - get(0).getY()));
				return;
			}

			cP = new Point2D[2*n];
			cP[0] = new Point2D.Double(get(0).getX()+2*get(1).getX(),
									   get(0).getY()+2*get(1).getY());
			for (int i = 1; i < n-1 ; ++i ){
				cP[2*i]= new Point2D.Double(4 * get(i).getX() + 2 * get(i+1).getX(),
											4 * get(i).getY() + 2 * get(i+1).getY());
			}
			cP[2*(n-1)] = new Point2D.Double((8 * get(n-1).getX() + get(n).getX()) / 2.0,
											 (8 * get(n-1).getY() + get(n).getY()) / 2.0 );
			
			//Compute first right end points
			getControlPoints(cP);
			for (int i = 0; i < n; ++i)
			{
				if (i < n - 1)
					cP[2*i+1] = new Point2D.Double(2 * get(i+1).getX() - cP[2*(i + 1)].getX(),
											       2 * get(i+1).getY() - cP[2*(i + 1)].getY());
				else
					cP[2*i+1] = new Point2D.Double((get(n).getX() + cP[2*(n - 1)].getX()) / 2,
												   (get(n).getY() + cP[2*(n - 1)].getY()) / 2);
			}
			
		}
		
		private void getControlPoints(Point2D[] data){
			int n        = data.length/2;
			double[] tmp = new double[n];
			double     b = 2.0;
			data[0].setLocation(data[0].getX() / b,
					            data[0].getY() / b);
			for (int i = 1; i < n; i++){
				tmp[i]    = 1 / b;
				b         = ( i < n-1 ? 4.0 : 3.5) - tmp[i];
				data[2*i].setLocation( (data[2*i].getX() - data[2*(i-1)].getX())/b,
									   (data[2*i].getY() - data[2*(i-1)].getY())/b);
			}
			for (int i = 1; i < n; i ++){
				
				data[2*(n-i-1)].setLocation(data[2*(n-i-1)].getX() - tmp[n-i] * data[2*(n-i)].getX(),
											data[2*(n-i-1)].getY() - tmp[n-i] * data[2*(n-i)].getY());
			}
		}

		
}

