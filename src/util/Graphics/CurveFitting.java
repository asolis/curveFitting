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
package util.Graphics;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;

import util.CurveFitting.BezierFitting;
import util.CurveFitting.Fitting;
import util.CurveFitting.Interpolation;
import util.CurveFitting.LeastSquareBezier;
import util.CurveFitting.LeastSquareFitting;
import util.CurveFitting.PolygonFitting;
import util.CurveFitting.SmoothFitting;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class CurveFitting extends JFrame {

	private static final long serialVersionUID = 1L;
	private Canvas jContentPane = null;
	private Fitting[] algorithms = {new BezierFitting(), new SmoothFitting(), new LeastSquareFitting(), new PolygonFitting()};
	private int      item   = 0;
	
	
	SmoothFitting bf = null;  //  @jve:decl-index=0:
	
	/**
	 * This is the default constructor
	 */
	public CurveFitting() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(870, 333);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new Canvas();
			jContentPane.setFocusable(true);
			jContentPane.setLayout(new BorderLayout());
			jContentPane.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseReleased(java.awt.event.MouseEvent e) {    
					interpolate();
				}   
				public void mousePressed(java.awt.event.MouseEvent e) {    
					jContentPane.points.clear(); // TODO Auto-generated Event stub mousePressed()
					jContentPane.repaint();
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					 	
					
					 // TODO Auto-generated Event stub mouseClicked()
				}
			});
			jContentPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
				public void mouseDragged(java.awt.event.MouseEvent e) {
					Point2D p = new Point2D.Double(e.getX(),e.getY());
					jContentPane.points.add(p);
					jContentPane.repaint();
					// TODO Auto-generated Event stub mouseDragged()
				}
			});
			jContentPane.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_C)
					item++;
					item = item % algorithms.length;
					interpolate();
				}
			});
		}
		return jContentPane;
	}
	
	
	private void interpolate(){
		LinkedList<Point2D> points = jContentPane.points;
		
		if (points.size()>=2){
			ArrayList<Point2D> tmp =new  ArrayList<Point2D>(points);
				
				try {
					jContentPane.setLabel(algorithms[item].getLabel());
					long start = System.currentTimeMillis();
					jContentPane.shapes = algorithms[item].fitCurve(tmp);					
					long elapsed = System.currentTimeMillis()-start;
					float seconds = elapsed/1000F;
					String label = String.format("%s [points:%d knots:%d seconds:%.4f]",
										algorithms[item].getLabel(),tmp.size(),
										jContentPane.shapes.size()+1,seconds);
					jContentPane.setLabel(label);
					
					
					
					
//					if (algorithms[item] instanceof LeastSquareFitting){
//						LeastSquareFitting lsf = (LeastSquareFitting)algorithms[item];
//						long start2 = System.currentTimeMillis();
//						lsf.fitCurve(tmp,true);
//						long elapsed2 = System.currentTimeMillis()-start2;
//						float seconds2 = elapsed2/1000F;
//						String label2 = String.format("%s [points:%d knots:%d seconds:%.2f]",
//								algorithms[item].getLabel(),tmp.size(),
//								jContentPane.shapes.size()+1,seconds2);
//						jContentPane.setLabel(label+"\n"+label2);
//						
//					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				jContentPane.repaint();
		
		} 
	}
	
	
	
    public static void main(String[] args) {
	    CurveFitting frame = new CurveFitting();
	    frame.setSize(800, 600);     
	   
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	     
	    frame.setVisible(true);
    }
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
