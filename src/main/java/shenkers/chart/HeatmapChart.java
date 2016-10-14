package shenkers.chart;

import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.data.general.HeatMapDataset;
import org.jfree.data.general.HeatMapUtilities;

import shenkers.u.Util.Tuple;
import shenkers.data.DataUtil;
import shenkers.u.Util;

public class HeatmapChart {

	private HeatmapChart(){}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		HashMap<List<Integer>,Double> m = new HashMap<List<Integer>,Double>();
		for(int i=0; i<6; i++){
			for(int j=0; j<2; j++){
				m.put(Arrays.asList(new Integer[]{i,j}), (i+j)/12.0);
			}
		}
		BufferedImage img =getChart(m,"ABCDEFGHIJKLMNOPQRSTUVWXYZ", Color.white, Color.blue);
		JPanel image = new ImagePanel(img);
		image.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
		f.getContentPane().add(image);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);

		BufferedImage bi = getChart(Util.list("abc","de","f"),Util.list("abc","de","f"), hAxis(Util.list("AAA"), 100), 50);
		ImagePanel ip = new ImagePanel(bi);
		ip.setPreferredSize(new Dimension(bi.getWidth(),bi.getHeight()));
		GraphicsUtils.show(ip);

		VectorImagePanel vip = getVectorImageComponent(m, Util.list(Color.white,Color.blue), 0, 6./12);
		GraphicsUtils.show(vip);
		//		f.getContentPane().add(ip);
		//		f.pack();
		//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		f.setLocationRelativeTo(null);
		//		f.setVisible(true);
	}

	public static Component getPanel(List<String> domain_lables, List<String> range_labels, BufferedImage data, int textHeight){
		BufferedImage bi = getChart(domain_lables, range_labels, data, textHeight);
		ImagePanel ip = new ImagePanel(bi);
		ip.setPreferredSize(new Dimension(bi.getWidth(),bi.getHeight()));
		return ip;
	}

	public static BufferedImage getChart(List<String> domain_lables, List<String> range_labels, BufferedImage data, int textHeight){

		BufferedImage domain_label = vAxis(domain_lables, textHeight);
		BufferedImage range_label = hAxis(range_labels, textHeight);

		int dOffset = range_label.getWidth();
		int rOffset = domain_label.getHeight();

		int width = domain_label.getWidth()+dOffset;
		int height = range_label.getHeight()+rOffset;

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics = img.createGraphics();
		graphics.setBackground(Color.white);
		graphics.setColor(Color.black);
		graphics.clearRect(0, 0, width, height);
		graphics.drawImage(domain_label, dOffset, 0, null);
		graphics.drawImage(range_label, 0, rOffset, null);
		graphics.drawImage(data, dOffset+1, rOffset+1, width-dOffset-1, height-rOffset-1, null);


		return img;
	}


	static BufferedImage axes(String label){
		int dim = label.length()*11 + 11;
		BufferedImage img = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.setBackground(Color.white);
		g.clearRect(0, 0, dim, dim);
		g.drawImage(hAxis(label), 11, label.length()*11 , null);
		g.drawImage(vAxis(label), 0, 0, null);
		return img;
	}

	static BufferedImage axes(List<String> labels, int textHeight){
		int dim = labels.size()*textHeight + textHeight;
		BufferedImage img = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.setBackground(Color.white);
		g.clearRect(0, 0, dim, dim);
		g.drawImage(hAxis(labels,textHeight), textHeight, labels.size()*textHeight , null);
		g.drawImage(vAxis(labels,textHeight), 0, 0, null);
		return img;
	}

	static BufferedImage hAxis(List<String> labels, int textHeight){

		Graphics context = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
		context.setFont(new Font("Monospaced", Font.PLAIN, textHeight));

		int maxLength = 0;
		for(String label : labels){
			maxLength = Math.max(maxLength, (int) context.getFontMetrics().getStringBounds(label, context).getWidth());
		}
		int width = maxLength;
		int height = labels.size()*textHeight;

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		graphics.setFont(new Font("Monospaced", Font.PLAIN, textHeight));
		graphics.setBackground(Color.white);
		graphics.setColor(Color.black);
		graphics.clearRect(0, 0, width, height);
		int vOffset=textHeight;
		for(String label : labels){
			graphics.drawString(label, 0, vOffset);
			vOffset+=textHeight;
		}

		return img;
	}

	static BufferedImage hAxis(String label){
		int dim = label.length()*11;
		BufferedImage img = new BufferedImage(dim, 11, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		graphics.setFont(new Font("Monospaced", Font.PLAIN, 10));
		graphics.setBackground(Color.white);
		graphics.setColor(Color.black);
		graphics.clearRect(0, 0, dim, 11);
		int i = 0;
		for(char c = label.charAt(i); i < label.length(); i++ ){
			c = label.charAt(i);
			graphics.drawString(""+c, (i*11)+3, 9);
		}

		return img;
	}

	static BufferedImage vAxis(List<String> labels, int textHeight){

		Graphics context = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
		context.setFont(new Font("Monospaced", Font.PLAIN, textHeight));

		int maxLength = 0;
		for(String label : labels){
			maxLength = Math.max(maxLength, (int) context.getFontMetrics().getStringBounds(label, context).getWidth());
		}
		int width = maxLength;
		int height = labels.size()*textHeight;

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		graphics.setFont(new Font("Monospaced", Font.PLAIN, textHeight));
		graphics.setBackground(Color.white);
		graphics.setColor(Color.black);

		graphics.clearRect(0, 0, width, height);
		int vOffset=textHeight;
		for(String label : labels){
			graphics.drawString(label, 0, vOffset);
			vOffset+=textHeight;
		}

		BufferedImage rotated = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB); 
		Graphics2D g = rotated.createGraphics();
		g.setFont(new Font("Monospaced", Font.PLAIN, textHeight));
		g.setBackground(Color.white);
		g.setColor(Color.black);
		g.clearRect(0, 0, height, width);
		AffineTransform affineTransform = AffineTransform.getRotateInstance(-Math.PI/2);
		affineTransform.concatenate(AffineTransform.getTranslateInstance(-width, 0));
		g.drawRenderedImage(img, affineTransform);
		return rotated;
	}

	static BufferedImage vAxis(String label){
		int dim = label.length()*11;
		BufferedImage img = new BufferedImage(11, dim, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		graphics.setFont(new Font("Monospaced", Font.PLAIN, 10));
		graphics.setBackground(Color.white);
		graphics.setColor(Color.black);
		graphics.clearRect(0, 0, 11, dim);
		int i = 0;
		for(char c = label.charAt(i); i < label.length(); i++ ){
			c = label.charAt(i);
			graphics.drawString(""+c, 3, 11*(label.length()-i) - 1);
		}

		return img;
	}


	private static BufferedImage scale(BufferedImage image, int newWidth, int newHeight){
		double width = (double) image.getWidth();
		double height = (double) image.getHeight();
		double wScale = newWidth/width;
		double hScale = newHeight/height;
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB); 
		Graphics2D graphics = newImage.createGraphics();
		AffineTransform affineTransform = AffineTransform.getScaleInstance(wScale, hScale);
		graphics.drawRenderedImage(image, affineTransform);
		return newImage;
	}

	public static ImagePanel getHeatmapComponent(Map<List<Integer>,Double> Data, Color base, Color foreground){
		return new ImagePanel(getHeatmapImage(Data, base, foreground));
	}

	public static ImagePanel getHeatmapComponent(Map<List<Integer>,Double> Data, List<Color> scale){
		return new ImagePanel(getHeatmapImage(Data, scale));
	}

	/**
	 * Values below min and above max are truncated
	 * 
	 * @param Data
	 * @param scale
	 * @param min
	 * @param max
	 * @return
	 */
	public static ImagePanel getHeatmapComponent(Map<List<Integer>,Double> Data, List<Color> scale, double min, double max){
		ImageDataset data = new ImageDataset(Data);
		BufferedImage image = HeatMapUtilities.createHeatMapImage(data, new ListPaintScale(min, max, scale));
		return new ImagePanel(image);
	}

	public static Map<List<Integer>, Double> getDensityGrid(List<Tuple<Double,Double>> xy, double bandwidth, double xMin, double xMax, int xBins, double yMin, double yMax, int yBins){
		NormalDistribution nd = new NormalDistribution();

		List<Double> x = DataUtil.linspace(xMin, xMax, xBins);
		List<Double> y = DataUtil.linspace(yMin, yMax, yBins);
		Map<List<Integer>,Double> density = new HashMap();
		for (int i = 0; i < x.size(); i++) {
			for (int j = 0; j < y.size(); j++) {
				density.put(Util.list(i,j), Util.kernelDensity(x.get(i), y.get(j), xy, nd, bandwidth));
			}	
		}
		
		return density;
	}

	public static VectorImagePanel getVectorImageComponent(Map<List<Integer>,Double> Data, List<Color> scale){
		ImageDataset data = new ImageDataset(Data);
		//		System.out.printf("x %d y %d x %d y %d w %d h %d\n", data.xMin, data.yMin, data.xMax, data.yMax, data.getXSampleCount(), data.getYSampleCount());
		VectorImagePanel vip = new VectorImagePanel(data, new ListPaintScale(data.zMin, data.zMax, scale), data.xMin, data.yMin, data.getXSampleCount(), data.getYSampleCount());
		return vip;
	}

	public static VectorImagePanel getVectorImageComponent(Map<List<Integer>,Double> Data, List<Color> scale, double min, double max){
		ImageDataset data = new ImageDataset(Data);
		//		System.out.printf("x %d y %d x %d y %d w %d h %d\n", data.xMin, data.yMin, data.xMax, data.yMax, data.getXSampleCount(), data.getYSampleCount());
		VectorImagePanel vip = new VectorImagePanel(data, new ListPaintScale(min, max, scale), data.xMin, data.yMin, data.getXSampleCount(), data.getYSampleCount());
		return vip;
	}


	public static BufferedImage getHeatmapImage(Map<List<Integer>,Double> Data, Color base, Color foreground){
		ImageDataset data = new ImageDataset(Data);
		BufferedImage image = HeatMapUtilities.createHeatMapImage(data, new ListPaintScale(data.zMin,data.zMax,Arrays.asList(new Color[]{base, foreground})));
		return image;
	}

	public static BufferedImage getHeatmapImage(Map<List<Integer>,Double> Data, List<Color> scale){
		ImageDataset data = new ImageDataset(Data);
		BufferedImage image = HeatMapUtilities.createHeatMapImage(data, new ListPaintScale(data.zMin,data.zMax,scale));
		return image;
	}

	public static BufferedImage getHeatmapImage(Map<List<Integer>,Double> Data, List<Color> scale, double min, double max){
		ImageDataset data = new ImageDataset(Data);
		BufferedImage image = HeatMapUtilities.createHeatMapImage(data, new ListPaintScale(min,max,scale));
		return image;
	}

	public static BufferedImage getChart(Map<List<Integer>,Double> Data, Map<List<Integer>,Double> Data2, Color color1, Color color2){
		Map<List<Integer>,Double> temp = new HashMap<List<Integer>, Double>();

		for(List<Integer> key : Data2.keySet()){
			temp.put(key, 0.0);
		}

		for(Entry<List<Integer>, Double> e : Data.entrySet()){
			temp.put(e.getKey(), e.getValue());
		}

		ImageDataset data = new ImageDataset(temp);

		BufferedImage image = HeatMapUtilities.createHeatMapImage(data, new ListPaintScale(data.zMin,data.zMax,Arrays.asList(new Color[]{Color.black, color1})));

		temp = new HashMap<List<Integer>, Double>();

		for(List<Integer> key : Data.keySet()){
			temp.put(key, 0.0);
		}

		for(Entry<List<Integer>, Double> e : Data2.entrySet()){
			temp.put(e.getKey(), e.getValue());
		}

		ImageDataset data2 = new ImageDataset(temp);

		BufferedImage image2 = HeatMapUtilities.createHeatMapImage(data2, new ListPaintScale(data2.zMin,data2.zMax,Arrays.asList(new Color[]{Color.black, color2})));
		return merge(image,image2);
	}

	public static BufferedImage getChart(Map<List<Integer>,Double> Data, Map<List<Integer>,Double> Data2, String sequence, Color color1, Color color2){
		// get the data
		BufferedImage image = getChart(Data, Data2, color1, color2);
		// get the axes
		BufferedImage plot = axes(sequence);

		// the size of the data
		int dim = sequence.length()*11;

		Graphics2D g = plot.createGraphics();

		//		draw the data offset from the axes
		g.drawImage(scale(image, dim, dim), 11, 0, null);

		return plot;
	}

	public static BufferedImage getChart(Map<List<Integer>,Double> Data, String sequence, Color base, Color foreground){
		BufferedImage image = getHeatmapImage(Data, base, foreground);
		BufferedImage plot = axes(sequence);
		int dim = sequence.length()*11;
		Graphics2D g = plot.createGraphics();
		g.drawImage(scale(image, dim, dim), 11, 0, null);

		return plot;
	}


	public static BufferedImage merge(BufferedImage one, BufferedImage two){
		BufferedImage temp = new BufferedImage(Math.max(one.getWidth(),two.getWidth()),Math.max(one.getHeight(),two.getHeight()),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) temp.getGraphics();
		g.setComposite(new Composite(){

			public CompositeContext createContext(final ColorModel srcColorModel,
					final ColorModel dstColorModel, RenderingHints hints) {

				return new CompositeContext(){
					int ALPHA = 0xFF000000; // alpha mask
					int MASK7Bit = 0xFEFEFF; // mask for additive/subtractive shading


					// the earlier mentioned algorithm
					int add(int color1, int color2) {
						int pixel = (color1 & MASK7Bit) + (color2 & MASK7Bit);
						int overflow = pixel & 0x1010100;
						overflow = overflow - (overflow >> 8);
						return ALPHA | overflow | pixel;
					}

					public void dispose() {
					}

					public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
						Rectangle srcRect = src.getBounds();
						Rectangle dstInRect = dstIn.getBounds();
						Rectangle dstOutRect = dstOut.getBounds();
						int x = 0, y = 0;
						int w = Math.min(Math.min(srcRect.width, dstOutRect.width), dstInRect.width);
						int h = Math.min(Math.min(srcRect.height, dstOutRect.height), dstInRect.height);
						Object srcPix = null, dstPix = null;
						for (y = 0; y < h; y++)
							for (x = 0; x < w; x++) {
								srcPix = src.getDataElements(x + srcRect.x, y + srcRect.y, srcPix);
								dstPix = dstIn.getDataElements(x + dstInRect.x, y + dstInRect.y, dstPix);
								int sp = srcColorModel.getRGB(srcPix);
								int dp = dstColorModel.getRGB(dstPix);
								int rp = add(sp,dp);
								dstOut.setDataElements(x + dstOutRect.x, y + dstOutRect.y, dstColorModel.getDataElements(rp, null));
							}
					}
				};
			}

		});
		g.drawImage(one, 0, temp.getHeight()-one.getHeight(), null);
		g.drawImage(two, 0, temp.getHeight()-two.getHeight(), null);

		return temp;
	}

	public static class ImagePanel extends JPanel{ 

		private BufferedImage image; 

		public ImagePanel(BufferedImage img) { 
			image = img;
		} 

		public void paintComponent(Graphics g) { 
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		} 

	} 

	public static class VectorImagePanel extends JPanel{ 

		ImageDataset data; ListPaintScale cps; int min_x; int min_y; int w; int h;


		public VectorImagePanel(ImageDataset data, ListPaintScale cps, int min_x, int min_y, int w, int h) { 
			this.data=data;
			this.cps=cps;
			this.min_x=min_x;
			this.min_y=min_y;
			this.w=w;
			this.h=h;
		} 

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			double cell_width=getWidth()*1./w;
			double cell_height=getHeight()*1./h;
			g2d.fillRect(0, 0, getWidth(), getHeight());
			for(int i=0; i<w; i++){
				for(int j=h-1; j>-1; j--){
					g2d.setPaint(cps.getPaint(data.getZValue(i, (h-j-1))));
					g2d.fill(new Rectangle2D.Double(i*cell_width, j*cell_height, cell_width, cell_height));
				}
			}

		} 

	} 

	static class ImageDataset implements HeatMapDataset{

		Map<List<Integer>,Double> data;
		int xMin;
		int xMax;
		int yMin;
		int yMax;
		double zMin;
		double zMax;
		public ImageDataset (Map<List<Integer>,Double> Data){
			xMin = Integer.MAX_VALUE;
			yMin = Integer.MAX_VALUE;
			xMax = Integer.MIN_VALUE;
			yMax = Integer.MIN_VALUE;
			zMin = Double.MAX_VALUE;
			zMax = -Double.MAX_VALUE;
			data = Data;
			for(Entry<List<Integer>,Double> e : data.entrySet()){
				List<Integer> coord = e.getKey();
				xMax = Math.max(coord.get(0),xMax);
				xMin = Math.min(coord.get(0),xMin);
				yMax = Math.max(coord.get(1),yMax);
				yMin = Math.min(coord.get(1),yMin);
				zMin = Math.min(e.getValue(), zMin);
				zMax = Math.max(e.getValue(), zMax);
			}
		}

		public double getMinZ(){
			return zMin;
		}

		public double getMaxZ(){
			return zMax;
		}

		public double getMaximumXValue(){
			return xMax;
		}
		public double getMaximumYValue(){
			return yMax;
		}
		public double getMinimumXValue(){
			return xMin;
		}
		public double getMinimumYValue(){
			return yMin;
		}
		public int	getXSampleCount(){
			return xMax-xMin+1;
		}
		public double getXValue(int xIndex){
			return 0;
		}
		public int	getYSampleCount(){
			return yMax-yMin+1;
		}
		public double getYValue(int yIndex){
			return 0;
		}

		public Number getZ(int xIndex, int yIndex){
			return data.get(Arrays.asList(new Integer[]{xIndex+xMin,yIndex+yMin}));
		}

		public double getZValue(int xIndex, int yIndex){
			Double val = data.get(Arrays.asList(new Integer[]{xIndex+xMin,yIndex+yMin}));
			if(val == null)
				return 0;
			else
				return val;
		}
	}

	public static class ListPaintScale implements PaintScale{

		HashMap<Double, Color> scale;
		Double[] keys;
		double min,max;

		public ListPaintScale(double Min, double Max, List<Color> colors){
			min = Min;
			max = Max;
			double inc = (Max-Min)/(colors.size()-1);
			double val = Min;
			scale = new HashMap<Double,Color>();

			for(Color c: colors){
				scale.put(new Double(val),c);
				val += inc;
			}

			keys = scale.keySet().toArray(new Double[]{});
			Arrays.sort(keys);
		}

		public double getLowerBound() {
			return min;
		}

		public Paint getPaint(double value) {
			Double floor = floor(keys, value);
			Double ceiling = ceiling(keys, value);
			if(floor == ceiling) {
				return scale.get(floor);
			}

			double delta = ceiling - floor;
			float rCeilCol = (float) ((value - floor)/delta);
			float rFloorCol = (float) ((ceiling - value)/delta);
			Color Floor = scale.get(floor);
			Color Ceiling  = scale.get(ceiling);
			float[] fRGB = Floor.getRGBComponents(null);
			float[] cRGB = Ceiling.getRGBComponents(null);
			Color mixture = new Color((fRGB[0]*rFloorCol)+(cRGB[0]*rCeilCol),(fRGB[1]*rFloorCol)+(cRGB[1]*rCeilCol),(fRGB[2]*rFloorCol)+(cRGB[2]*rCeilCol),(fRGB[3]*rFloorCol)+(cRGB[3]*rCeilCol));
			return mixture;
		}

		public double getUpperBound() {
			return max;
		}

		public static Double floor(Double[] items, double key) {
			int location = Arrays.binarySearch(items, key);

			if (location >= 0) return items[location];

			location = -(location + 1);

			if (location == 0) {
				return items[0];
			} else if (location == items.length) {
				return items[items.length - 1];
			}

			return items[location - 1];
		}

		public static Double ceiling(Double[] items, double key) {
			int location = Arrays.binarySearch(items, key);

			if (location >= 0) return items[location];

			location = -(location + 1);

			if (location == 0) {
				return items[0];
			} else if (location == items.length) {
				return items[items.length - 1];
			}

			return items[location];
		}

	}

}