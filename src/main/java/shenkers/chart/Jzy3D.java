package shenkers.chart;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.chart.SwingChart;
import org.jzy3d.chart.factories.SwingChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.IColorMappable;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.colors.colormaps.IColorMap;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.axes.AxeBox;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;
import org.jzy3d.plot3d.text.drawable.DrawableTextBillboard;

import shenkers.u.IO.LineTokenizer;
import shenkers.u.Util;
import shenkers.u.Util.Function;
import shenkers.u.Util.MapList;

public class Jzy3D {
	public static class ColorHashMapper extends ColorMapper{

		Map<Coord3d,Color> colorMap;

		public ColorHashMapper(IColorMap colormap, float zmin, float zmax) {
			super(colormap, zmin, zmax);
			throw new RuntimeException("This constructor is not defined");
		}

		public ColorHashMapper(IColorMap colormap, float zmin, float zmax, Color factor) {
			super(colormap, zmin, zmax, factor);
			throw new RuntimeException("This constructor is not defined");
		}

		public ColorHashMapper(ColorMapper colormapper, Color color) {
			super(colormapper, color);
			throw new RuntimeException("This constructor is not defined");
		}

		public ColorHashMapper(Map<Coord3d,Color> colorMap) {
			super(null,0,0);
			this.colorMap=colorMap;
		}

		public Color getColor(Coord3d coord){
			Color c = colorMap.get(coord);
			return c;
		}


	}

	public static void plot(){
		Map<Coord3d, Color> colorMap = new HashMap<Coord3d, Color>();
		List<Coord3d> coords = new LinkedList<Coord3d>();
		
		for(int i=0; i<50; i++){
			for(int j=0; j<50; j++){
				for(int k=0; k<50; k++){
					if(Math.random()<.5){
								Coord3d c = new Coord3d(i,j,k);
			colorMap.put(c, new Color(0, 0, 0, (i+j+k)/300.f));
			coords.add(c);
					}
				}
			}
		}
		System.out.println(coords.size());
		
		ColorMapper cm = new ColorMapper(new ColorMapRainbow(),0,100);
		Scatter scatter = new Scatter(coords.toArray(new Coord3d[0]), Util.evaluate(coords, new Function<Coord3d,Color>(){
			public Color evaluate(Coord3d s) {
				return new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
			}}).toArray(new Color[0]));// new ColorHashMapper(colorMap));
		scatter.setWidth(25.0f);

		// IMPORTANT!! NEED TO DISABLE DEPTH FOR TRANSPARENCY TO WORK PROPERLY
		Chart chart = new Chart(new Quality(false, true, true, false, false, false, false));
		chart.getView().setBackgroundColor(Color.WHITE);
		chart.getScene().add(scatter);
		
		ChartLauncher.openChart(chart);
	}
	
	public static Chart createScatter3d(List<double[]> points){
		List<Coord3d> coords = new LinkedList<Coord3d>();
		List<java.awt.Color> colors = Util.repeat(java.awt.Color.black, points.size());
		return createScatter3d(points, colors, 3f);
	}
	
	public static Chart createScatter3d(List<double[]> points, List<java.awt.Color> colors, float pointWidth){
		List<Coord3d> coords = new LinkedList<Coord3d>();
		
		Scatter scatter = new Scatter( coords.toArray(new Coord3d[0]), colors.toArray(new Color[0]));
		scatter.setWidth(pointWidth);

		Chart chart = new Chart(new Quality(false, true, true, true, false, false, false));
		chart.getView().setBackgroundColor(Color.WHITE);
		chart.getScene().add(scatter);
		
		return chart;
	}
	
	public static Chart createScatter3d(List<double[]> points, List<java.awt.Color> colors, float pointWidth, boolean alphaActivated){
		List<Coord3d> coords = new LinkedList<Coord3d>();
		Map<Coord3d,Color> colorMap = new HashMap<Coord3d, Color>();
		
		Coord3d[] coordinates = new Coord3d[points.size()];
		Color[] Colors = new Color[points.size()];
			
		for(int i=0; i<points.size(); i++){
			double[] p = points.get(i);
			java.awt.Color paint = colors.get(i);
		
			Coord3d c = new Coord3d(p[0],p[1],p[2]);
			coordinates[i] = c;
			
//			java.awt.Color col = Util.hsba(h, s, b, .5);
			Color color = new Color(paint.getRed(),paint.getGreen(),paint.getBlue(),paint.getAlpha());
			Colors[i] = color;
			colorMap.put(c, color);
			coords.add(c);
		}
		org.jzy3d.plot3d.primitives.Scatter scatter = new Scatter(coordinates, Colors);
//		MultiColorScatter scatter = new MultiColorScatter( coords.toArray(new Coord3d[0]), new ColorHashMapper(colorMap));
		scatter.setWidth(pointWidth);

		Chart chart = new Chart(new Quality(false, alphaActivated, false, true, false, false, false));
		chart.getView().setBackgroundColor(Color.WHITE);
		chart.getScene().add(scatter);
		
		return chart;
	}
	
	/**
	 * the X Y and Z are polar coordinate rotation
	 * @param chart
	 */
	public static void setView(Chart chart, double x, double y, double z){
		chart.getView().setViewPoint(new Coord3d(x,y,z),true);
	}
	
	public static void setAxisLabels(Chart chart, String label_x, String label_y, String label_z){
		chart.getAxeLayout().setXAxeLabel(label_x);
		chart.getAxeLayout().setYAxeLabel(label_y);
		chart.getAxeLayout().setZAxeLabel(label_z);
	}
	
	public static void showChart(Chart chart){
		ChartLauncher.openChart(chart);
	}


	public static void main(String[] args) throws FileNotFoundException {
//		plotCells();
		if(true){
//			plot();
//					System.exit(0);
			int size = 3000;
			float x;
			float y;
			float z;
			float a;
			Coord3d[] points = new Coord3d[size];
			Color[] cols = new Color[size];

			Map<Coord3d,Color> colorMap = new HashMap<Coord3d, Color>();

			// Create scatter points
			for(int i=0; i<size; i++){
				x = (float)Math.random();
				y = (float)Math.random();
				z = (float)Math.random();
				a = (float)Math.random();
				points[i] = new Coord3d(x, y, z);
				//			System.out.println(Util.list(x,y,z));
				java.awt.Color c = Util.hsba(x, y, z, .5);
				//			System.out.println(Util.list(c.getRGBComponents(null)));
				Color cc = new Color(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
				//			cc.alphaSelf(.5f);
				cols[i] = cc;
			} 

			Color factor = new Color(1f, 1f, 1f, .5f);
			ColorMapper cm = new ColorMapper( new ColorMapRainbow(), -0.5f, 0.5f ,factor);
			Scatter scatter = new Scatter( points, cols);
			//		MultiColorScatter scatter = new MultiColorScatter( points, cm);
			scatter.setWidth(5);

			// Create a chart and add scatter

			//	TODO IMPORTANT!! QUALITY IS HOW YOU ACTIVATE TRANSPARENCY!!!
			Chart chart = new SwingChart(new Quality(true, true, false, false, false, false, false));
			//	chart.getAxeLayout().setMainColor(Color.WHITE);
			//	chart.getView().setBackgroundColor(Color.BLACK);
			chart.getScene().add(scatter);
			org.jzy3d.plot3d.primitives.Point p = new Point(new Coord3d(.5,.5,.5));
			//		p.setColor(new Color(0f, 0f, 0f,.5f));
			//		p.setWidth(40);

			//		chart.getScene().add(p);


			//		Settings.getInstance().getGLCapabilities().getGLProfile().get
			ChartLauncher.openChart(chart);

			double X = 0;
			while(true){
			chart.getView().setViewPoint(new Coord3d(X, 0, 0),true);
			X+=.05;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			//		GL2 gl = chart.getView().getCurrentGL();
			//		gl.glEnable(GL2.GL_BLEND);
			//		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		}
	}
}
