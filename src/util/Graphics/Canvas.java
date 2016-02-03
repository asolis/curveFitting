/**************************************************************************************************
 **************************************************************************************************
 
     BSD 3-Clause License (https://www.tldrlegal.com/l/bsd3)
     
     Copyright (c) 2012 Andrés Solís Montero <http://www.solism.ca>, All rights reserved.
     
     
     Redistribution and use in source and binary forms, with or without modification,
     are permitted provided that the following conditions are met:
     
     1. Redistributions of source code must retain the above copyright notice,
        this list of conditions and the following disclaimer.
     2. Redistributions in binary form must reproduce the above copyright notice,
        this list of conditions and the following disclaimer in the documentation
        and/or other materials provided with the distribution.
     3. Neither the name of the copyright holder nor the names of its contributors
        may be used to endorse or promote products derived from this software
        without specific prior written permission.
     
     THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
     AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
     IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
     ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
     LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
     DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
     LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
     THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
     OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
     OF THE POSSIBILITY OF SUCH DAMAGE.
 
 **************************************************************************************************
 **************************************************************************************************/
package util.Graphics;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Canvas extends JPanel {

	private static final long serialVersionUID = 1L;
    public ArrayList<Shape> shapes = new ArrayList<Shape>();
    public LinkedList<Point2D> points = new LinkedList<Point2D>();
    private String label = "";  //  @jve:decl-index=0:
	private double scale = 1;
	
	
	public void setLabel(String l){
		label = l;
	}
	/**
	 * This is the default constructor
	 */
	public Canvas() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setDoubleBuffered(true);
		this.setLayout(new GridBagLayout());
	}
	
	@Override
	public void paintComponent(Graphics g2){
		
			super.paintComponent(g2);

		    BufferedImage bI = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		    double x = 0;
		    double y = 0;
			for (Point2D p : points){
				x = p.getX();
				y = p.getY();
				if ( (x>0 && x<this.getWidth()) && (y>0 && y<this.getHeight()) )
				bI.setRGB((int)p.getX(), (int)p.getY(), 0x0000ff);
			}
			g2.drawImage(bI, 0,0,null);
		     //g2.clearRect(0, 0, WIDTH, HEIGHT);
		     g2.drawRect(0, 0, this.getWidth(), this.getHeight());
				Color before = g2.getColor();
				g2.setColor(Color.yellow);
				g2.drawString("Ctrl + C ( Change ): "+label, 10, 20);
				g2.setColor(before);
		     Graphics2D g = (Graphics2D)g2;
			 int box = 4;
			
			   for (Shape s2: shapes){
				   
				   Rectangle2D r = s2.getBounds2D();
			
				  final AffineTransform transform2 =
					  		AffineTransform.getScaleInstance(scale , scale);
				  
			      Shape st = transform2.createTransformedShape(s2);
						
				   g.setColor(Color.red);
				   g.setStroke(new BasicStroke(1.5f));
				   g.draw(st);
				   if (s2 instanceof QuadCurve2D){
					   QuadCurve2D q2 = (QuadCurve2D)s2;
					  
					   
					   g.setStroke(new BasicStroke(1.f));
					   g.setColor(Color.blue);
					   
					   g.drawLine((int)(q2.getX1()*scale),(int) (q2.getY1()*scale),(int)( q2.getCtrlX()*scale),(int)( q2.getCtrlY()*scale));
					   g.drawLine((int)(q2.getX2()*scale),(int) (q2.getY2()*scale),(int) (q2.getCtrlX()*scale),(int)( q2.getCtrlY()*scale));
					   g.fillOval((int)((q2.getCtrlX()-box/2)*scale), (int)((q2.getCtrlY()-box/2)*scale),(int)(box*scale) ,(int)(box*scale));
					   
					   g.setColor(Color.green);
					   g.fillRect((int)((q2.getX1()-box/2)*scale), (int)((q2.getY1()-box/2)*scale),(int)(scale*box) ,(int)(scale*box));
					   g.fillRect((int)((q2.getX2()-box/2)*scale), (int)((q2.getY2()-box/2)*scale),(int)(scale*box) ,(int)(scale*box));
					   
				   }
				   if (s2 instanceof CubicCurve2D){
					   CubicCurve2D c2 = (CubicCurve2D)s2;
					   g.setStroke(new BasicStroke(1.f));
					   g.setColor(Color.blue);
					   
					   g.drawLine((int)(c2.getX1()*scale),(int) (c2.getY1()*scale),(int)( c2.getCtrlX1()*scale),(int)( c2.getCtrlY1()*scale));
					   g.drawLine((int)(c2.getX2()*scale),(int) (c2.getY2()*scale),(int) (c2.getCtrlX2()*scale),(int)( c2.getCtrlY2()*scale));
					   g.fillOval((int)((c2.getCtrlX1()-box/2)*scale), (int)((c2.getCtrlY1()-box/2)*scale),(int)(box*scale) ,(int)(box*scale));
					   g.fillOval((int)((c2.getCtrlX2()-box/2)*scale), (int)((c2.getCtrlY2()-box/2)*scale),(int)(box*scale) ,(int)(box*scale));
					   
					   g.setColor(Color.green);
					   g.fillRect((int)((c2.getX1()-box/2)*scale), (int)((c2.getY1()-box/2)*scale),(int)(scale*box) ,(int)(scale*box));
					   g.fillRect((int)((c2.getX2()-box/2)*scale), (int)((c2.getY2()-box/2)*scale),(int)(scale*box) ,(int)(scale*box));
					   
					   
				
				   }
				   if (s2 instanceof Line2D){
					   Line2D l2 = (Line2D) s2;
					   //g.setColor(Color.blue);
					   //g.drawLine((int)(l2.getX1()*scale),(int) (l2.getY1()*scale),(int)( l2.getX2()*scale),(int)( l2.getY2()*scale));
					   
					   g.setColor(Color.green);
					   g.fillRect((int)((l2.getX1()-box/2)*scale), (int)((l2.getY1()-box/2)*scale),(int)(scale*box) ,(int)(scale*box));
					   g.fillRect((int)((l2.getX2()-box/2)*scale), (int)((l2.getY2()-box/2)*scale),(int)(scale*box) ,(int)(scale*box));
					  
				   }

			   
			   }
		
		
	}

}
