// ==========================================================================
// $Id$
// Curve Fitting Application 
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


       
import java.awt.Point;
import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;



        public class Bezier extends Interpolation {

            private static final float AP = 0.5f;
           
    		public Bezier(){
    			cP = new Point2D[0];
    			points = new ArrayList<Point2D>();
    			index  = new LinkedList<Integer>();
    		}
            
            public Bezier(ArrayList<Point2D> pts, LinkedList<Integer> idxs) throws CurveCreationException {
            		setData(pts, idxs);
            }

            protected void compute(){
            	 int n = N();
            	 if (n == 2) {
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
                 
                 cP = new Point2D[2 * n -2];
                 double paX, paY;
                 double pbX = get(0).getX();
                 double pbY = get(0).getY();
                 double pcX = get(1).getX();
                 double pcY = get(1).getY();
                 double p2X = 0, p2Y=0, 
                 		abX, abY,
                 		acX, acY,
                 		lac,
                 		proj,
                 		apX, apY,
                 		p1X, p1Y,
                 		cbX, cbY;
                 
                 for (int i = 1; i < n - 1; i++) {
                     paX = pbX;
                     paY = pbY;
                     pbX = pcX;
                     pbY = pcY;
                     pcX = get(i+1).getX();
                     pcY = get(i+1).getY();
                     abX = pbX - paX;
                     abY = pbY - paY;
                     acX = pcX - paX;
                     acY = pcY - paY;
                     lac = Math.sqrt(acX * acX + acY * acY);
                     acX = acX / lac;
                     acY = acY / lac;

                     proj = abX * acX + abY * acY;
                     proj = proj < 0 ? -proj : proj;
                     apX = proj * acX;
                     apY = proj * acY;

                     p1X = pbX - AP * apX;
                     p1Y = pbY - AP * apY;
                     if (i == 1)
                     	cP[0] = new Point((int) p1X, (int) p1Y);
                     cP[2*i-1] = new Point((int) p1X, (int) p1Y);

                     acX = -acX;
                     acY = -acY;
                     cbX = pbX - pcX;
                     cbY = pbY - pcY;
                     proj = cbX * acX + cbY * acY;
                     proj = proj < 0 ? -proj : proj;
                     apX = proj * acX;
                     apY = proj * acY;

                     p2X = pbX - AP * apX;
                     p2Y = pbY - AP * apY;
                     cP[2*i] = new Point((int) p2X, (int) p2Y);
                 }
                 cP[cP.length-1]= new Point((int) p2X, (int) p2Y);
            }

	       

        }
