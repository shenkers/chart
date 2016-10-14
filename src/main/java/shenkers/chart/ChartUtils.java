package shenkers.chart;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Renderer;
import javax.swing.SwingUtilities;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.ext.awt.geom.Polygon2D;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.annotations.CategoryLineAnnotation;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnit;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.PlotEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.chart.renderer.category.ScatterRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.renderer.xy.XYErrorRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.DomainOrder;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.statistics.BoxAndWhiskerCalculator;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerXYDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.statistics.MultiValueCategoryDataset;
import org.jfree.data.statistics.StatisticalCategoryDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ShapeUtilities;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import shenkers.data.DataUtil;
import shenkers.data.Stats.MomentTracker;
import shenkers.u.Util.Criteria;
import shenkers.u.Util.ExtremeTracker;
import shenkers.u.Util.MapList;
import shenkers.u.Util.OrderedMap;
import shenkers.u.Util.Tuple;
import shenkers.u.Util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class ChartUtils {

	static class PathRenderer extends XYLineAndShapeRenderer{

		boolean fillSeries = true;
		double alpha = 1.;

		public PathRenderer(boolean lines, boolean shapes) {
			super(lines, shapes);
		}

		public void drawItem(Graphics2D g2,
				XYItemRendererState state,
				Rectangle2D dataArea,
				PlotRenderingInfo info,
				XYPlot plot,
				ValueAxis domainAxis,
				ValueAxis rangeAxis,
				XYDataset dataset,
				int series,
				int item,
				CrosshairState crosshairState,
				int pass) {

			// do nothing if item is not visible
			//if (!getItemVisible(series, item)) {
			//   return;   
			//}

			// first pass draws the background (lines, for instance)
			//			drawPrimaryLineAsPath(state, g2, plot, dataset, pass, 
			//	                   series, item, domainAxis, rangeAxis, dataArea);
			if (isLinePass(pass)) {
				if (item == 0) {
					if (this.getDrawSeriesLineAsPath()) {
						State s = (State) state;
						s.seriesPath.reset();
						s.setLastPointGood(false);     
					}
				}

				if (getItemLineVisible(series, item)) {
					if (this.getDrawSeriesLineAsPath()) {
						drawPrimaryLineAsPath(state, g2, plot, dataset, pass, 
								series, item, domainAxis, rangeAxis, dataArea);
					}
					else {
						drawPrimaryLine(state, g2, plot, dataset, pass, series, 
								item, domainAxis, rangeAxis, dataArea);
					}
				}
			}
			// second pass adds shapes where the items are ..
			else if (isItemPass(pass)) {

				// setup for collecting optional entity info...
				EntityCollection entities = null;
				if (info != null) {
					entities = info.getOwner().getEntityCollection();
				}

				drawSecondaryPass(g2, plot, dataset, pass, series, item, 
						domainAxis, dataArea, rangeAxis, crosshairState, entities);
			}
		}

		public void cacheShapes(Graphics2D g2, XYDataset dataset, int i, State s, ValueAxis domainAxis,
				ValueAxis rangeAxis, RectangleEdge xAxisLocation, RectangleEdge yAxisLocation,
				Rectangle2D dataArea, XYPlot plot){
			int nSeries = dataset.getSeriesCount();
			//			for (int i = 0; i < nSeries; i++) {
			int nPoints = dataset.getItemCount(i);
			for (int j = 0; j < nPoints; j++) {
				double x1 = dataset.getX(i, j).doubleValue();
				double y1 = dataset.getY(i, j).doubleValue();
				double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
				double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);

				if (!Double.isNaN(transX1) && !Double.isNaN(transY1)) {
					float x = (float) transX1;
					float y = (float) transY1;
					PlotOrientation orientation = plot.getOrientation();
					if (orientation == PlotOrientation.HORIZONTAL) {
						x = (float) transY1;
						y = (float) transX1;
					}
					if (s.isLastPointGood()) {
						s.seriesPath.lineTo(x, y);
					}
					else {
						s.seriesPath.moveTo(x, y);
					}
					s.setLastPointGood(true);
				}
				else {
					s.setLastPointGood(false);
				}
			}

			g2.setStroke(getItemStroke(i, 0));
			g2.setPaint(Util.colora((Color)getItemPaint(i, 0),alpha));
			if(fillSeries)
				g2.fill(s.seriesPath);
			else
				g2.draw(s.seriesPath);
			//			}
			// update path to reflect latest point

		}

		//		List<Double> range = new ArrayList<Double>();
		Map<Integer,List<Double>> range = new HashMap<Integer, List<Double>>();
		@Override
		protected void drawPrimaryLineAsPath(XYItemRendererState state,
				Graphics2D g2, XYPlot plot,
				XYDataset dataset,
				int pass,
				int series,
				int item,
				ValueAxis domainAxis,
				ValueAxis rangeAxis,
				Rectangle2D dataArea) {

			//			System.out.println(Math.random());

			RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
			RectangleEdge yAxisLocation = plot.getRangeAxisEdge();

			List<Double> coords = Util.list(dataArea.getWidth(), dataArea.getHeight(), rangeAxis.getRange().getLowerBound(), rangeAxis.getRange().getUpperBound(),domainAxis.getRange().getLowerBound(), domainAxis.getRange().getUpperBound());
			State s = (State) state;

			if(s.seriesPath.getCurrentPoint()==null || !coords.equals(range.get(series))){
				cacheShapes(g2,dataset, series, s, domainAxis, rangeAxis, xAxisLocation, yAxisLocation, dataArea, plot);		
				range.put(series, coords);
			}
		}

	}

	static class SpreadScatterRenderer extends ScatterRenderer{

		private static final long serialVersionUID = 1L;

		public Range findRangeBounds(CategoryDataset dataset) {
			return DatasetUtilities.findRangeBounds(dataset);
		}

		public void drawItem(Graphics2D g2, CategoryItemRendererState state,
				Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis,
				ValueAxis rangeAxis, CategoryDataset dataset, int row, int column,
				int pass) {
			// do nothing if item is not visible
			if (!getItemVisible(row, column)) {
				return;
			}
			int visibleRow = state.getVisibleSeriesIndex(row);
			if (visibleRow < 0) {
				return;
			}
			int visibleRowCount = state.getVisibleSeriesCount();

			PlotOrientation orientation = plot.getOrientation();

			MultiValueCategoryDataset d = (MultiValueCategoryDataset) dataset;
			List values = d.getValues(row, column);
			if (values == null) {
				return;
			}
			int valueCount = values.size();
			Random r = new Random(0);
			BetaDistribution bd = new BetaDistribution(2, 2);
			// g2.drawRect((int)domainAxis.getCategorySeriesMiddle(column,
			// dataset.getColumnCount(), visibleRow, visibleRowCount,
			// this.getItemMargin(), dataArea, plot.getDomainAxisEdge())-5,
			// (int) rangeAxis.valueToJava2D(0, dataArea,
			// plot.getRangeAxisEdge())-((column+1)*30),
			// 15,
			// (column+1)*30);
			for (int i = 0; i < valueCount; i++) {
				// current data point...
				double x1;
				if (this.getUseSeriesOffset()) {
					x1 = domainAxis.getCategorySeriesMiddle(column,
							dataset.getColumnCount(), visibleRow, visibleRowCount,
							this.getItemMargin(), dataArea, plot.getDomainAxisEdge());
				}
				else {
					x1 = domainAxis.getCategoryMiddle(column, getColumnCount(),
							dataArea, plot.getDomainAxisEdge());
				}
				Number n = (Number) values.get(i);
				double value = n.doubleValue();
				double y1 = rangeAxis.valueToJava2D(value, dataArea,
						plot.getRangeAxisEdge());

				double start = domainAxis.getCategoryStart(column, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
				double end = domainAxis.getCategoryEnd(column, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
				double w = end-start;
				x1 += (bd.inverseCumulativeProbability(r.nextDouble())-.5)*w/dataset.getColumnCount();
				// System.out.println(Util.list(start,x1,end,y1));
				Shape shape = getItemShape(row, column);
				if (orientation == PlotOrientation.HORIZONTAL) {
					shape = ShapeUtilities.createTranslatedShape(shape, y1, x1);
				}
				else if (orientation == PlotOrientation.VERTICAL) {
					shape = ShapeUtilities.createTranslatedShape(shape, x1, y1);
				}
				if (getItemShapeFilled(row, column)) {
					if (this.getUseFillPaint()) {
						g2.setPaint(getItemFillPaint(row, column));
					}
					else {
						g2.setPaint(getItemPaint(row, column));
					}
					g2.fill(shape);
				}
				if (this.getDrawOutlines()) {
					if (this.getUseOutlinePaint()) {
						g2.setPaint(getItemOutlinePaint(row, column));
					}
					else {
						g2.setPaint(getItemPaint(row, column));
					}
					g2.setStroke(getItemOutlineStroke(row, column));
					g2.draw(shape);
				}
			}

		}
	}

	static class ViolinRenderer extends ScatterRenderer{

		private static final long serialVersionUID = 1L;

		public Range findRangeBounds(CategoryDataset dataset) {
			return DatasetUtilities.findRangeBounds(dataset);
		}

		volatile boolean initialized = false;

		Double min = Double.MAX_VALUE;
		Double max = -Double.MAX_VALUE;

		int nBreak = 100;
		List<Double> grid;
		MapList<List<Integer>,Double> density;
		double h = .025;
		boolean linearSpace;


		public ViolinRenderer(int nBreaks, double bandwidth, boolean linearSpace) {
			this.nBreak = nBreaks;
			this.h = bandwidth;
			this.linearSpace = linearSpace;
		}

		public void initialize(CategoryDataset dataset){
			int nCol = dataset.getColumnCount();
			int nRow = dataset.getRowCount();
			for (int i = 0; i < nRow; i++) {
				for (int j = 0; j < nCol; j++) {
					List values = ((MultiValueCategoryDataset) dataset).getValues(i,j);
					min = Math.min(min,((Number)Collections.min(values)).doubleValue());
					max = Math.max(max,((Number)Collections.max(values)).doubleValue());
				}
			}
			grid = linearSpace ? DataUtil.linspace(min, max, nBreak) : DataUtil.logspace(min, max, 10, nBreak);
			density = new MapList<List<Integer>, Double>();
			NormalDistribution nd = new NormalDistribution();
			for (int i = 0; i < nRow; i++) {
				for (int j = 0; j < nCol; j++) {
					List values = ((MultiValueCategoryDataset) dataset).getValues(i,j);
					List<Double> doubles = Util.evaluate(values, new Util.Function<Number,Double>(){
						public Double evaluate(Number s) {
							return s.doubleValue();
						}});
					for(double x : grid){
						density.put(Util.list(i,j),Util.kernelDensity(x, doubles, nd, h, min, max));
					}
					List<Double> list = density.get(Util.list(i,j));
					Double maxDensity = Collections.max(list);
					for(int k=0; k<list.size(); k++)
						list.set(k, list.get(k)/maxDensity);
				}
			}
			initialized = true;
		}

		public void drawItem(Graphics2D g2, CategoryItemRendererState state,
				Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis,
				ValueAxis rangeAxis, CategoryDataset dataset, int row, int column,
				int pass) {

			if(!initialized){
				initialize(dataset);
			}
			// do nothing if item is not visible
			if (!getItemVisible(row, column)) {
				return;
			}
			int visibleRow = state.getVisibleSeriesIndex(row);
			if (visibleRow < 0) {
				return;
			}
			int visibleRowCount = state.getVisibleSeriesCount();

			PlotOrientation orientation = plot.getOrientation();
			//Util.kernelDensity();
			MultiValueCategoryDataset d = (MultiValueCategoryDataset) dataset;
			List values = d.getValues(row, column);
			if (values == null) {
				return;
			}

			List<Double> densities = density.get(Util.list(row,column));
			//			Polygon p = new Polygon(xpoints, ypoints, npoints)
			Path2D path = new Path2D.Double();

			for (int i = 0; i < grid.size(); i++) {
				Double value = grid.get(i);
				Double density = densities.get(i);

				double x1;
				if (this.getUseSeriesOffset()) {
					x1 = domainAxis.getCategorySeriesMiddle(column,
							dataset.getColumnCount(), visibleRow, visibleRowCount,
							this.getItemMargin(), dataArea, plot.getDomainAxisEdge());
				}
				else {
					x1 = domainAxis.getCategoryMiddle(column, getColumnCount(),
							dataArea, plot.getDomainAxisEdge());
				}

				double y1 = rangeAxis.valueToJava2D(value, dataArea,
						plot.getRangeAxisEdge());

				double start = domainAxis.getCategoryStart(column, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
				double end = domainAxis.getCategoryEnd(column, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());

				double w = end-start;

				Double screenX = null;
				Double screenY = null;
				if (orientation == PlotOrientation.HORIZONTAL) {
					screenX = y1; screenY = x1 + (density*w*.5);
				}
				else if (orientation == PlotOrientation.VERTICAL) {
					screenY = y1; screenX = x1 + (density*w*.5);
				}


				if(i==0)
					path.moveTo(screenX, screenY);
				else
					path.lineTo(screenX, screenY);

			}

			for (int i = grid.size()-1; i > -1; i--) {
				Double value = grid.get(i);
				Double density = densities.get(i);

				double x1;
				if (this.getUseSeriesOffset()) {
					x1 = domainAxis.getCategorySeriesMiddle(column,
							dataset.getColumnCount(), visibleRow, visibleRowCount,
							this.getItemMargin(), dataArea, plot.getDomainAxisEdge());
				}
				else {
					x1 = domainAxis.getCategoryMiddle(column, getColumnCount(),
							dataArea, plot.getDomainAxisEdge());
				}

				double y1 = rangeAxis.valueToJava2D(value, dataArea,
						plot.getRangeAxisEdge());

				double start = domainAxis.getCategoryStart(column, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
				double end = domainAxis.getCategoryEnd(column, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());

				double w = end-start;

				Double screenX = null;
				Double screenY = null;
				if (orientation == PlotOrientation.HORIZONTAL) {
					screenX = y1; screenY = x1 + (density*w*.5);
				}
				else if (orientation == PlotOrientation.VERTICAL) {
					screenY = y1; screenX = x1 - (density*w*.5);
				}
				path.lineTo(screenX, screenY);
			}
			path.closePath();

			if (getItemShapeFilled(row, column)) {
				if (this.getUseFillPaint()) {
					g2.setPaint(getItemFillPaint(row, column));
				}
				else {
					g2.setPaint(getItemPaint(row, column));
				}
				g2.fill(path);
			}
			if (this.getDrawOutlines()) {
				if (this.getUseOutlinePaint()) {
					g2.setPaint(getItemOutlinePaint(row, column));
				}
				else {
					g2.setPaint(getItemPaint(row, column));
				}
				g2.setStroke(getItemOutlineStroke(row, column));
				g2.draw(path);
			}
		}
	}

	static class WhiskerIntervalRenderer extends BarRenderer {

		/** For serialization. */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructs a new renderer.
		 */
		double w;

		public WhiskerIntervalRenderer(double w) {
			super();
			this.w = w;
		}

		/**
		 * Returns the range of values from the specified dataset.  For this
		 * renderer, this is equivalent to calling
		 * <code>findRangeBounds(dataset, true)</code>.
		 *
		 * @param dataset  the dataset (<code>null</code> permitted).
		 *
		 * @return The range (or <code>null</code> if the dataset is
		 *         <code>null</code> or empty).
		 */
		@Override
		public Range findRangeBounds(CategoryDataset dataset) {
			return findRangeBounds(dataset, true);
		}

		/**
		 * Draws the bar for a single (series, category) data item.
		 *
		 * @param g2  the graphics device.
		 * @param state  the renderer state.
		 * @param dataArea  the data area.
		 * @param plot  the plot.
		 * @param domainAxis  the domain axis.
		 * @param rangeAxis  the range axis.
		 * @param dataset  the dataset.
		 * @param row  the row index (zero-based).
		 * @param column  the column index (zero-based).
		 * @param pass  the pass index.
		 */
		@Override
		public void drawItem(Graphics2D g2, CategoryItemRendererState state,
				Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis,
				ValueAxis rangeAxis, CategoryDataset dataset, int row, int column,
				int pass) {

			if (dataset instanceof IntervalCategoryDataset) {
				IntervalCategoryDataset d = (IntervalCategoryDataset) dataset;
				drawInterval(g2, state, dataArea, plot, domainAxis, rangeAxis,
						d, row, column);
			}
			else {
				super.drawItem(g2, state, dataArea, plot, domainAxis, rangeAxis,
						dataset, row, column, pass);
			}

		}

		/**
		 * Draws a single interval.
		 *
		 * @param g2  the graphics device.
		 * @param state  the renderer state.
		 * @param dataArea  the data plot area.
		 * @param plot  the plot.
		 * @param domainAxis  the domain axis.
		 * @param rangeAxis  the range axis.
		 * @param dataset  the data.
		 * @param row  the row index (zero-based).
		 * @param column  the column index (zero-based).
		 */
		protected void drawInterval(Graphics2D g2,
				CategoryItemRendererState state,
				Rectangle2D dataArea,
				CategoryPlot plot,
				CategoryAxis domainAxis,
				ValueAxis rangeAxis,
				IntervalCategoryDataset dataset,
				int row,
				int column) {

			int visibleRow = state.getVisibleSeriesIndex(row);
			if (visibleRow < 0) {
				return;
			}

			PlotOrientation orientation = plot.getOrientation();
			double rectX = 0.0;
			double rectY = 0.0;

			RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();

			// Y0
			Number value0 = dataset.getEndValue(row, column);
			if (value0 == null) {
				return;
			}
			double java2dValue0 = rangeAxis.valueToJava2D(value0.doubleValue(),
					dataArea, rangeAxisLocation);

			// Y1
			Number value1 = dataset.getStartValue(row, column);
			if (value1 == null) {
				return;
			}
			double java2dValue1 = rangeAxis.valueToJava2D(
					value1.doubleValue(), dataArea, rangeAxisLocation);

			if (java2dValue1 < java2dValue0) {
				double temp = java2dValue1;
				java2dValue1 = java2dValue0;
				java2dValue0 = temp;
			}

			// BAR WIDTH
			double rectWidth = state.getBarWidth();

			// BAR HEIGHT
			double rectHeight = Math.abs(java2dValue1 - java2dValue0);

			RectangleEdge barBase = RectangleEdge.LEFT;
			if (orientation == PlotOrientation.HORIZONTAL) {
				// BAR Y
				rectX = java2dValue0;
				rectY = calculateBarW0(getPlot(), orientation, dataArea, 
						domainAxis, state, visibleRow, column);
				rectHeight = state.getBarWidth();
				rectWidth = Math.abs(java2dValue1 - java2dValue0);
				barBase = RectangleEdge.LEFT;
			}
			else if (orientation == PlotOrientation.VERTICAL) {
				// BAR X
				rectX = calculateBarW0(getPlot(), orientation, dataArea, 
						domainAxis, state, visibleRow, column);
				rectY = java2dValue0;
				barBase = RectangleEdge.BOTTOM;
			}
			//	        Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth,
			//	                rectHeight);
			double chartX = rectX+(rectWidth/2);

			Line2D[] lines = new Line2D[]{
					new Line2D.Double(chartX, rectY, chartX, rectY+rectHeight),
					new Line2D.Double(rectX+(rectWidth*(w/2)), rectY, rectX+(rectWidth*(1-(w/2))), rectY),
					new Line2D.Double(rectX+(rectWidth*(w/2)), rectY+rectHeight, rectX+(rectWidth*(1-(w/2))), rectY+rectHeight),
			};
			g2.setColor(Color.black);
			for(Line2D bar : lines){
				g2.draw(bar);

				// add an item entity, if this information is being collected
				EntityCollection entities = state.getEntityCollection();
				if (entities != null) {
					addItemEntity(entities, dataset, row, column, bar);
				}
			}

		}

		/**
		 * Tests this renderer for equality with an arbitrary object.
		 *
		 * @param obj  the object (<code>null</code> permitted).
		 *
		 * @return A boolean.
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof IntervalBarRenderer)) {
				return false;
			}
			// there are no fields to check
			return super.equals(obj);
		}

	}



	public static <T extends Number> CategoryDataset createMultiValueCategoryDataset(Map<String, List<T>> dataset) {
		DefaultMultiValueCategoryDataset data = new DefaultMultiValueCategoryDataset();
		List<String> categoryLabels = new ArrayList<String>(dataset.size());
		for(String category : dataset.keySet()){
			categoryLabels.add(category);
		}

		for(String category : categoryLabels){
			List<T> categoryData = dataset.get(category);
			data.add(categoryData, "", category);
		}

		return data;
	}

	public static CategoryDataset createArbitraryMultiValueCategoryDataset(Map<String, List> dataset) {
		DefaultMultiValueCategoryDataset data = new DefaultMultiValueCategoryDataset();
		List<String> categoryLabels = new ArrayList<String>(dataset.size());
		for(String category : dataset.keySet()){
			categoryLabels.add(category);
		}

		for(String category : categoryLabels){
			List categoryData = dataset.get(category);
			data.add(categoryData, "", category);
		}

		return data;
	}

	public static <T extends Number> JFreeChart createCategoryScatterChart(Map<String,List<T>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		CategoryDataset data = createMultiValueCategoryDataset(dataset);

		Range rangeBounds = DatasetUtilities.findRangeBounds(data);
		NumberAxis numberAxis = new NumberAxis(yAxis);
		numberAxis.setRangeWithMargins(rangeBounds);

		CategoryPlot plot = new CategoryPlot(data, new CategoryAxis(xAxis), numberAxis, new SpreadScatterRenderer());

		JFreeChart chart = new JFreeChart(title, plot);

		if(!showLegend)
			chart.removeLegend();
		setCategoryStandardTooltips(chart);

		((CategoryPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((CategoryPlot) chart.getPlot()).setRangeGridlinesVisible(false);;

		chart.setBackgroundPaint(Color.white);
		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static double integrate(List<Double[]> xy){
		double area = 0;
		Double prevX = null;
		Double prevY = null;
		for(int i=0; i<xy.size(); i++){
			Double[] XY = xy.get(i);
			if(i>0){
				double a = (XY[0]-prevX)*((XY[1]+prevY)/2);
				area += a;
			}
			prevX = XY[0];
			prevY = XY[1];
		}
		return area;
	}

	public static <T extends Number> MapList<String, Double[]> createDensityArea(Map<String,List<T>> dataset, int nBreak, double bandwidth, boolean linearSpace){
		double min = ((Number) Collections.min((List) Util.combine(dataset.values()))).doubleValue();
		double max = ((Number) Collections.max((List) Util.combine(dataset.values()))).doubleValue();
		return createDensityArea(dataset, nBreak, bandwidth, linearSpace, min, max);
	}

	public static <T extends Number> MapList<String, Double[]> createDensityArea(Map<String,List<T>> dataset, int nBreak, double bandwidth, boolean linearSpace, Number min, Number max){
		//		Double min = Double.MAX_VALUE;
		//		Double max = -Double.MAX_VALUE;

		min = min == null ? ((Number) Collections.min((List) Util.combine(dataset.values()))).doubleValue() : min;
		max = max == null ? ((Number) Collections.min((List) Util.combine(dataset.values()))).doubleValue() : max;


		List<Double> grid = linearSpace ? DataUtil.linspace(min.doubleValue(), max.doubleValue(), nBreak) : DataUtil.logspace(min.doubleValue(), max.doubleValue(), 10, nBreak);
		MapList<String,Double[]> density = new MapList<String, Double[]>(new Util.OrderedMap());
		NormalDistribution nd = new NormalDistribution();

		for (String key : dataset.keySet()) {
			List<T> values = dataset.get(key);
			List<Double> doubles = Util.evaluate(values, new Util.Function<T,Double>(){
				public Double evaluate(T s) {
					return s.doubleValue();
				}});

			for(double x : grid){
				density.put(key, new Double[]{x, Util.kernelDensity(x, doubles, nd, bandwidth, min.doubleValue(), max.doubleValue())});
			}
		}

		MapList<String,Double[]> scaledDensity = new MapList<String, Double[]>(new Util.OrderedMap());

		for (String key : dataset.keySet()) {
			List<Double[]> densities = density.get(key);
			double area = integrate(densities);
			for(Double[] xy : densities){
				scaledDensity.put(key, new Double[]{xy[0],xy[1]/area});
			}	
		}

		return scaledDensity;
	}

	//	public static <T extends Number> JFreeChart createDensityChart(Map<String,List<T>> dataset, String title, String xAxis, String yAxis, boolean showLegend, int nBreak, double bandwidth, boolean linearSpace, boolean filled){
	//		MapList<String, Double[]> densityArea = createDensityArea(dataset, nBreak, bandwidth, linearSpace);
	//		return filled ? createAreaChart(densityArea.getMap(), title, xAxis, yAxis, showLegend) : createLineChart(densityArea.getMap(), title, xAxis, yAxis, showLegend);
	//	}

	public static MapList<String,Double[]> normalizeDensity(MapList<String,Double[]> density){
		int k = density.size();
		int l = density.values().iterator().next().size();

		MapList<String,Double[]> normalized = new MapList<String, Double[]>(new Util.OrderedMap());
		for(int i=0; i<l; i++){
			double total = 0;
			for(String key : density.keySet()){
				total += density.get(key).get(i)[1];
			}
			for(String key : density.keySet()){
				double scaled = total == 0 ? 1./k : density.get(key).get(i)[1]/total;
				normalized.put(key, new Double[]{density.get(key).get(i)[0], scaled});
			}
		}
		return normalized;
	}

	public static MapList<String,Double[]> stack(MapList<String,Double[]> series){
		int k = series.size();

		MapList<String,Double[]> stacked = new MapList<String, Double[]>(new Util.OrderedMap());

		Util.Function<Double[],Tuple<Double,Double>> fwd = new Util.Function<Double[],Tuple<Double,Double>>(){

			public Tuple<Double, Double> evaluate(Double[] s) {
				return new Tuple<Double,Double>(s[0],s[1]);
			}
		};

		Util.Function<Tuple<Double,Double>,Double[]> inv = new Util.Function<Tuple<Double,Double>,Double[]>(){
			public Double[] evaluate(Tuple<Double, Double> s) {
				// TODO Auto-generated method stub
				return new Double[]{s.key,s.value};
			}
		};

		List<Double> y0 = null;


		List<String> keys = Util.list(series.keySet());

		{
			String key = keys.get(0);
			List<Double[]> list = series.get(key);
			List<Tuple<Double, Double>> evaluate = Util.evaluate(list, fwd);
			Tuple<List<Double>, List<Double>> xy = Util.unzip(evaluate);

			y0 = Util.repeat(0., xy.key.size());
		}

		for(int i=0; i<k; i++){

			String key = keys.get(i);
			List<Double[]> list = series.get(key);
			List<Tuple<Double, Double>> evaluate = Util.evaluate(list, fwd);
			Tuple<List<Double>, List<Double>> xy = Util.unzip(evaluate);

			List<Double[]> bottomPoints = Util.evaluate(Util.zip(xy.key, y0, false),inv);

			List<Double> topRow = Util.evaluate(Util.zip(y0, xy.value, false), new Util.Function<Tuple<Double,Double>, Double>() {
				public Double evaluate(Tuple<Double, Double> s) {
					return s.key + s.value;
				}
			});

			List<Double[]> topPoints = 
					Util.evaluate(Util.zip(xy.key,
							topRow, false), inv);

			y0 = topRow;

			List<Double[]> areaPoints = Util.concatenate(bottomPoints,Util.reverse(topPoints));

			stacked.putAll(key, areaPoints);
		}
		return stacked;
	}

	public static <T extends Number> JFreeChart createStackedDensityChart(Map<String,List<T>> dataset, String title, String xAxis, String yAxis, boolean showLegend, int nBreak, double bandwidth, boolean linearSpace){
		MapList<String, Double[]> densityArea = createDensityArea(dataset, nBreak, bandwidth, linearSpace);
		MapList<String, Double[]> normalizedDensity = normalizeDensity(densityArea);
		MapList<String, Double[]> pathData = stack(normalizedDensity);
		return createPathChart(pathData.getMap(), title, xAxis, yAxis, showLegend);
	}

	public static <T extends Number> JFreeChart createDensityChart(Map<String,List<T>> dataset, String title, String xAxis, String yAxis, boolean showLegend, int nBreak, double bandwidth, boolean linearSpace, boolean filled){
		MapList<String, Double[]> densityArea = createDensityArea(dataset, nBreak, bandwidth, linearSpace);
		MapList<String, Double[]> pathData = new MapList<String, Double[]>(new Util.OrderedMap());
		for(String key : densityArea.keySet()){
			if(filled){
				List<Double[]> head = new ArrayList<Double[]>();
				List<Double[]> tail = new ArrayList<Double[]>();
				double xMin = Collections.min(Util.unzip(Util.asTuples(densityArea.get(key))).key);
				double xMax = Collections.max(Util.unzip(Util.asTuples(densityArea.get(key))).key);
				head.add(new Double[]{xMin, 0.});
				tail.add(new Double[]{xMax, 0.});
				pathData.putAll(key, Util.concatenate(head, densityArea.get(key), tail));
			}
			else{
				pathData.putAll(key, densityArea.get(key));
			}
		}
		JFreeChart jfc = filled ? createPathChart(pathData.getMap(), title, xAxis, yAxis, showLegend) : createLineChart(pathData.getMap(), title, xAxis, yAxis, showLegend);
		if(filled)
			((PathRenderer) jfc.getXYPlot().getRenderer()).alpha = .5;
		return jfc;
	}

	public static <T extends Number> JFreeChart createDensityChart(Map<String,List<T>> dataset, String title, String xAxis, String yAxis, boolean showLegend, int nBreak, double bandwidth, boolean linearSpace, boolean filled, Number min, Number max){
		MapList<String, Double[]> densityArea = createDensityArea(dataset, nBreak, bandwidth, linearSpace, min, max);
		MapList<String, Double[]> pathData = new MapList<String, Double[]>(new Util.OrderedMap());
		for(String key : densityArea.keySet()){
			if(filled){
				List<Double[]> head = new ArrayList<Double[]>();
				List<Double[]> tail = new ArrayList<Double[]>();
				double xMin = Collections.min(Util.unzip(Util.asTuples(densityArea.get(key))).key);
				double xMax = Collections.max(Util.unzip(Util.asTuples(densityArea.get(key))).key);
				head.add(new Double[]{xMin, 0.});
				tail.add(new Double[]{xMax, 0.});
				pathData.putAll(key, Util.concatenate(head, densityArea.get(key), tail));
			}
			else{
				pathData.putAll(key, densityArea.get(key));
			}
		}
		JFreeChart jfc = filled ? createPathChart(pathData.getMap(), title, xAxis, yAxis, showLegend) : createLineChart(pathData.getMap(), title, xAxis, yAxis, showLegend);
		if(filled)
			((PathRenderer) jfc.getXYPlot().getRenderer()).alpha = .5;
		return jfc;
	}

	public static <T extends Number> JFreeChart createCategoryViolinChart(Map<String,List<T>> dataset, String title, String xAxis, String yAxis, boolean showLegend, int nBreak, double bandwidth, boolean linearSpace){
		// Make the PDB series and add it to the predicted strand locations
		CategoryDataset data = createMultiValueCategoryDataset(dataset);

		Range rangeBounds = DatasetUtilities.findRangeBounds(data);
		NumberAxis numberAxis = new NumberAxis(yAxis);
		numberAxis.setRangeWithMargins(rangeBounds);

		CategoryPlot plot = new CategoryPlot(data, new CategoryAxis(xAxis), numberAxis, new ViolinRenderer(nBreak, bandwidth,linearSpace));

		JFreeChart chart = new JFreeChart(title, plot);

		if(!showLegend)
			chart.removeLegend();
		setCategoryStandardTooltips(chart);

		((CategoryPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((CategoryPlot) chart.getPlot()).setRangeGridlinesVisible(false);;

		chart.setBackgroundPaint(Color.white);
		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> CategoryDataset create2DMultiValueCategoryDataset(Map<String[], List<T>> dataset) {
		DefaultMultiValueCategoryDataset data = new DefaultMultiValueCategoryDataset();
		List<String[]> categoryLabels = new ArrayList<String[]>(dataset.size());
		for(String[] category : dataset.keySet()){
			categoryLabels.add(category);
		}

		for(String[] category : categoryLabels){
			List<T> categoryData = dataset.get(category);
			data.add(categoryData, category[0], category[1]);
		}

		return data;
	}

	public static <T extends Number> JFreeChart createCategory2DScatterCategoryChart(Map<String[],List<T>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		CategoryDataset data = create2DMultiValueCategoryDataset(dataset);

		Range rangeBounds = DatasetUtilities.findRangeBounds(data);
		NumberAxis numberAxis = new NumberAxis(yAxis);
		numberAxis.setRangeWithMargins(rangeBounds);

		CategoryPlot plot = new CategoryPlot(data, new CategoryAxis(xAxis), numberAxis, new SpreadScatterRenderer());

		JFreeChart chart = new JFreeChart(title, plot);

		if(!showLegend)
			chart.removeLegend();
		setCategoryStandardTooltips(chart);

		((CategoryPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((CategoryPlot) chart.getPlot()).setRangeGridlinesVisible(false);;

		chart.setBackgroundPaint(Color.white);
		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}


	static float stroke_width=3f;

	public static IntervalXYDataset createIntervalXYDataset(Map<String, List<Double[]>> dataset) {
		YIntervalSeriesCollection data = new YIntervalSeriesCollection();

		for(Entry<String,List<Double[]>> s : dataset.entrySet()){
			String seriesName = s.getKey();
			List<Double[]> seriesData = s.getValue();
			YIntervalSeries series = new YIntervalSeries(seriesName);
			for(Double[] d : seriesData)
				series.add(d[0], d[1], d[1]-d[2], d[1]+d[2]);
			data.addSeries(series);
		}

		return data;
	}

	public static <T extends Number> StatisticalCategoryDataset createStatisticalCategoryDataset(Map<String, List<T>> dataset) {
		DefaultStatisticalCategoryDataset data = new DefaultStatisticalCategoryDataset();

		List<String> categoryLabels = Util.list(dataset.keySet());

		for(String category : categoryLabels){
			List<T> categoryData = dataset.get(category);

			data.add((Number) Util.mean(categoryData), Util.stderr(categoryData), category, 0);
		}

		return data;
	}

	public static CategoryDataset createStatisticalMomentCategoryDataset(Map<String, MomentTracker> dataset) {
		DefaultStatisticalCategoryDataset data = new DefaultStatisticalCategoryDataset();

		List<String> categoryLabels = Util.list(dataset.keySet());

		for(String category : categoryLabels){
			MomentTracker categoryData = dataset.get(category);

			//			data.addValue(categoryData, category[0],category[1]);
			data.add((Number) categoryData.mean, Math.sqrt(categoryData.variance/categoryData.n), category, 0);
		}

		return data;
	}

	public static CategoryDataset create2DStatisticalMomentCategoryDataset(Map<List<Comparable>, MomentTracker> dataset) {
		DefaultStatisticalCategoryDataset data = new DefaultStatisticalCategoryDataset();

		Set<List<Comparable>> categoryLabels = dataset.keySet();

		for(List<Comparable> category : categoryLabels){
			MomentTracker categoryData = dataset.get(category);

			//			data.addValue(categoryData, category[0],category[1]);
			data.add(categoryData.mean, Math.sqrt(categoryData.variance/categoryData.n), category.get(0), category.get(1));
		}

		return data;
	}

	/**
	 * 
	 * @param dataset each category will have a mean and confidence interval at positions 0/1 of the array
	 * @return
	 */
	public static StatisticalCategoryDataset create2DStatisticalCategoryDataset(Map<String[], double[]> dataset) {
		DefaultStatisticalCategoryDataset data = new DefaultStatisticalCategoryDataset();

		Set<String[]> categoryLabels = dataset.keySet();

		for(String[] category : categoryLabels){
			double[] categoryData = dataset.get(category);

			//			data.addValue(categoryData, category[0],category[1]);
			data.add(categoryData[0], categoryData[1], category[0], category[1]);
		}

		return data;
	}

	public static <T extends Number> CategoryDataset createCategoryDataset(Map<String, T> dataset) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();

		List<String> categoryLabels = new ArrayList<String>(dataset.size());
		for(String category : dataset.keySet()){
			categoryLabels.add(category);
		}

		for(String category : categoryLabels){
			Number categoryData = dataset.get(category);

			data.addValue(categoryData, "", category);
		}

		return data;
	}

	public static <T extends Number, S extends Comparable> CategoryDataset create2DCategoryDataset(Map<S[], T> dataset) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();

		List<S[]> categoryLabels = Util.list(dataset.keySet());

		for(S[] category : categoryLabels){
			T categoryData = dataset.get(category);

			data.addValue(categoryData, category[0],category[1]);
		}

		return data;
	}

	private static <T extends Number> IntervalCategoryDataset createIntervalCategoryDataset(Map<String, T[]> dataset) {

		String[] series = new String[]{""};
		String[] categories = new String[dataset.size()];
		Number[][] starts = new Number[1][dataset.size()];
		Number[][] ends = new Number[1][dataset.size()];

		int i=0;
		for(String category : dataset.keySet()){
			categories[i] = category;
			T[] categoryData = dataset.get(category);
			starts[0][i] = categoryData[0];
			ends[0][i] = categoryData[1];
			i++;
		}

		DefaultIntervalCategoryDataset defaultintervalcategorydataset = new DefaultIntervalCategoryDataset(series, categories,starts,ends);
		return defaultintervalcategorydataset;
	}

	private static <T extends Number> IntervalCategoryDataset create2DIntervalCategoryDataset(List<String> series, List<String> categories, Map<List<String>, T[]> dataset) {

		String[] aSeries = series.toArray(new String[0]);
		String[] aCategories = categories.toArray(new String[0]);
		Number[][] starts = new Number[series.size()][categories.size()];
		Number[][] ends = new Number[series.size()][categories.size()];


		for(int i=0;i<series.size();i++){
			for(int j=0;j<categories.size();j++){
				T[] categoryData = dataset.get(Util.list(series.get(i),categories.get(j)));
				starts[i][j] = categoryData[0];
				ends[i][j] = categoryData[1];
			}	
		}


		DefaultIntervalCategoryDataset defaultintervalcategorydataset = new DefaultIntervalCategoryDataset(aSeries, aCategories,starts,ends);
		
		return defaultintervalcategorydataset;
	}


	static class AnnotatedXYSeries extends XYSeries
	{
		private static final long serialVersionUID = 1L;
		List<String> annotation;

		public AnnotatedXYSeries(Comparable key) {
			super(key);
			annotation = new LinkedList<String>();
		}

		public AnnotatedXYSeries(Comparable key, boolean autoSort) {
			super(key, autoSort);
			annotation = new LinkedList<String>();
		}

		public AnnotatedXYSeries(Comparable key, boolean autoSort, boolean allowDuplicateXValues) {
			super(key, autoSort, allowDuplicateXValues);
			annotation = new LinkedList<String>();
		}

		public void add(Number x, Number y, String text) {
			super.add(x, y);
			annotation.add(text);
		}

		public String getLabel(int index) {
			return annotation.get(index);
		}
	}

	static class AnnotatedXYSeriesCollection extends XYSeriesCollection{
		private static final long serialVersionUID = 1L;

		public String getToolTip(int series, int item){
			return ((AnnotatedXYSeries)getSeries(series)).getLabel(item);
		}
	}

	static class AnnotationToolTipGenerator implements XYToolTipGenerator
	{
		public String generateToolTip(XYDataset dataset, int series, int item) {
			return ((AnnotatedXYSeriesCollection)dataset).getToolTip(series, item);
		}
	}


	public static <T extends Number> XYSeriesCollection createXYDataset(Map<? extends Comparable,List<T[]>> data){
		XYSeriesCollection dataset = new XYSeriesCollection();

		for(Entry<? extends Comparable, List<T[]>> s : Util.reverse(Util.toList(data.entrySet()))){
			Comparable seriesName = s.getKey();
			List<T[]> seriesData = s.getValue();
			AnnotatedXYSeries series = new AnnotatedXYSeries(seriesName, false);
			for(T[] d : seriesData)
				series.add(d[0], d[1]);
			dataset.addSeries(series);
		}

		return dataset;
	}

	public static <T extends Number> TableXYDataset createTableXYDataset(Map<? extends Comparable,List<T[]>> data){
		DefaultTableXYDataset dataset = new DefaultTableXYDataset();

		for(Entry<? extends Comparable, List<T[]>> s : Util.reverse(Util.toList(data.entrySet()))){
			Comparable seriesName = s.getKey();
			List<T[]> seriesData = s.getValue();
			AnnotatedXYSeries series = new AnnotatedXYSeries(seriesName, false, false);
			for(T[] d : seriesData)
				series.add(d[0], d[1]);
			dataset.addSeries(series);
		}

		return dataset;
	}

	public static <T extends Number> XYSeriesCollection createAnnotatedXYDataset(Map<String,List<T[]>> data, Map<String,List<String>> annotations){
		AnnotatedXYSeriesCollection dataset = new AnnotatedXYSeriesCollection();

		for(Entry<String, List<T[]>> s : data.entrySet()){
			String seriesName = s.getKey();
			List<T[]> seriesData = s.getValue();
			List<String> annotation = annotations.get(seriesName);
			AnnotatedXYSeries series = new AnnotatedXYSeries(seriesName, false);
			for(int i=0; i<seriesData.size(); i++){
				T[] d = seriesData.get(i);
				String text = annotation==null?"":annotation.get(i);
				series.add(d[0], d[1], text);	
			}

			dataset.addSeries(series);
		}

		return dataset;
	}

	public static <T extends Number> PieDataset createPieDataset(Map<String,T> dataset){
		DefaultPieDataset data = new DefaultPieDataset();


		for(String category : dataset.keySet()){
			T pieData = dataset.get(category);

			data.setValue(category, pieData.doubleValue());
		}

		return data;
	}

	/**
	 * 
	 * @param dataset - each Double[] has an X,Y pair, and the line is connected
	 * in the order they occur in the array
	 * @param title
	 * @param xAxis - X axis label
	 * @param yAxis - Y axis label
	 * @param showLegend 
	 * @return a chart that can be displayed
	 */
	public static <T extends Number> JFreeChart createLineChart(Map<String,List<T[]>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		XYDataset data = createXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createXYLineChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);
		//				chart.getPlot().setBackgroundAlpha(0);
		//				((XYLineAndShapeRenderer) ((XYPlot)chart.getPlot()).getRenderer()).setBaseStroke(new BasicStroke(stroke_width));
		((XYLineAndShapeRenderer) ((XYPlot)chart.getPlot()).getRenderer()).setDrawSeriesLineAsPath(true);
		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	/**
	 * 
	 * @param dataset - each Double[] has an X,Y pair, and the line is connected
	 * in the order they occur in the array
	 * @param title
	 * @param xAxis - X axis label
	 * @param yAxis - Y axis label
	 * @param showLegend 
	 * @return a chart that can be displayed
	 */
	public static <T extends Number> JFreeChart createAreaChart(Map<String,List<T[]>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		XYDataset data = createXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createXYAreaChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);
		//				chart.getPlot().setBackgroundAlpha(0);
		//				((XYLineAndShapeRenderer) ((XYPlot)chart.getPlot()).getRenderer()).setBaseStroke(new BasicStroke(stroke_width));
		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createStackedAreaChart(Map<String,List<T[]>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		TableXYDataset data = createTableXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createStackedXYAreaChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);

		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);
		//				chart.getPlot().setBackgroundAlpha(0);
		//				((XYLineAndShapeRenderer) ((XYPlot)chart.getPlot()).getRenderer()).setBaseStroke(new BasicStroke(stroke_width));
		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static JFreeChart createErrorLineChart(Map<String,List<Double[]>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		IntervalXYDataset data = createIntervalXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createXYLineChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		((XYPlot) chart.getPlot()).setRenderer(new XYErrorRenderer());
		for(int i=0; i<dataset.size(); i++){
			((XYErrorRenderer)((XYPlot) chart.getPlot()).getRenderer()).setSeriesLinesVisible(i, true);
		}

		//		chart.getPlot().setBackgroundAlpha(0);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static JFreeChart createErrorScatterChart(Map<String,List<Double[]>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		IntervalXYDataset data = createIntervalXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createXYLineChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		((XYPlot) chart.getPlot()).setRenderer(new XYErrorRenderer());

		//		chart.getPlot().setBackgroundAlpha(0);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static JFreeChart createDeviationLineChart(Map<String,List<Double[]>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		IntervalXYDataset data = createIntervalXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createXYLineChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);
		XYItemRenderer xyir = chart.getXYPlot().getRenderer();
		DeviationRenderer dr = new DeviationRenderer(true,false);
		((XYPlot) chart.getPlot()).setRenderer(dr);

		for(int i=0; i<dataset.size(); i++){
			dr.setSeriesLinesVisible(i, true);
			dr.setSeriesFillPaint(i, dr.lookupSeriesPaint(i));
			((XYPlot) chart.getPlot()).getRenderer().setSeriesToolTipGenerator(i, new StandardXYToolTipGenerator());
		}
		//		chart.getPlot().setBackgroundAlpha(0);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createErrorCategoryChart(Map<String,List<T>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		StatisticalCategoryDataset data = createStatisticalCategoryDataset(dataset);

		JFreeChart chart =  ChartFactory.createBarChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((CategoryPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((CategoryPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		((CategoryPlot) chart.getPlot()).setRenderer(new StatisticalBarRenderer());

		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setShadowVisible(false);
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setBarPainter(new StandardBarPainter());

		chart.getPlot().setBackgroundPaint(Color.white);

		return chart;
	}

	public static <T extends Number> JFreeChart create2DErrorCategoryChart(Map<String[],double[]> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		StatisticalCategoryDataset data = create2DStatisticalCategoryDataset(dataset);

		JFreeChart chart =  ChartFactory.createBarChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((CategoryPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((CategoryPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		((CategoryPlot) chart.getPlot()).setRenderer(new StatisticalBarRenderer());

		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setShadowVisible(false);
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setBarPainter(new StandardBarPainter());

		chart.getPlot().setBackgroundPaint(Color.white);

		return chart;
	}

	public static <T extends Number> JFreeChart createPieChart(Map<String,T> dataset, String title, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		PieDataset data = createPieDataset(dataset);

		JFreeChart chart =  ChartFactory.createPieChart(
				title,
				data,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createIntervalCategoryChart(Map<String,T[]> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		//		 IntervalBarChartDemo1.java
		IntervalCategoryDataset data = createIntervalCategoryDataset(dataset);
		CategoryAxis domainAxis = new CategoryAxis(xAxis);
		NumberAxis rangeAxis = new NumberAxis(yAxis);
		IntervalBarRenderer renderer = new IntervalBarRenderer();
		CategoryPlot plot = new CategoryPlot(data, domainAxis, rangeAxis, renderer);

		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);;

		renderer.setShadowVisible(false);
		renderer.setBarPainter(new StandardBarPainter());

		plot.setBackgroundPaint(Color.white);

		JFreeChart chart = new JFreeChart(title, plot);
		chart.getLegend().setVisible(false);
		chart.setBackgroundPaint(Color.white);


		return chart;
	}

	public static <T extends Number> JFreeChart createBoxAndWhiskerPlot(Map<String,T[]> boxes, Map<String,T[]> medians, Map<String,T[]> whiskers, String title, String xAxis, String yAxis, boolean showLegend){
		//		 IntervalBarChartDemo1.java
		IntervalCategoryDataset boxData = createIntervalCategoryDataset(boxes);
		IntervalCategoryDataset whiskerData = createIntervalCategoryDataset(whiskers);
		IntervalCategoryDataset medianData = createIntervalCategoryDataset(medians);
		CategoryAxis domainAxis = new CategoryAxis(xAxis);
		NumberAxis rangeAxis = new NumberAxis(yAxis);

		WhiskerIntervalRenderer medianRenderer = new WhiskerIntervalRenderer(0.);
		WhiskerIntervalRenderer whiskerRenderer = new WhiskerIntervalRenderer(.5);
		IntervalBarRenderer barRenderer = new IntervalBarRenderer();

		CategoryPlot plot = new CategoryPlot(medianData, domainAxis, rangeAxis, medianRenderer);
		
		//		CategoryPlot plot = new CategoryPlot(boxData, domainAxis, rangeAxis, barRenderer);
		//		
		//		
		//		
		//		
		plot.setDataset(0, medianData);
		plot.setDataset(1, boxData);
		plot.setDataset(2, whiskerData);

		plot.setRenderer(0, medianRenderer);
		plot.setRenderer(1, barRenderer);
		plot.setRenderer(2, whiskerRenderer);


		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);;

		barRenderer.setShadowVisible(false);
		barRenderer.setBarPainter(new StandardBarPainter());

		plot.setBackgroundPaint(Color.white);

		JFreeChart chart = new JFreeChart(title, plot);
		chart.getLegend().setVisible(false);
		chart.setBackgroundPaint(Color.white);


		return chart;
	}
	
	public static <T extends Number> JFreeChart create2DBoxAndWhiskerPlot(Map<List<String>,T[]> boxes, Map<List<String>,T[]> medians, Map<List<String>,T[]> whiskers, String title, String xAxis, String yAxis, boolean showLegend){
		List<String> series = new ArrayList<String>();
		List<String> categories = new ArrayList<String>();
		for(List<String> key : boxes.keySet()){
			if(!series.contains(key.get(0)))
				series.add(key.get(0));
			if(!categories.contains(key.get(1)))
				categories.add(key.get(1));
		}
		//		 IntervalBarChartDemo1.java
		IntervalCategoryDataset boxData = create2DIntervalCategoryDataset(series, categories, boxes);
		IntervalCategoryDataset whiskerData = create2DIntervalCategoryDataset(series, categories, whiskers);
		IntervalCategoryDataset medianData = create2DIntervalCategoryDataset(series, categories, medians);
		CategoryAxis domainAxis = new CategoryAxis(xAxis);
		NumberAxis rangeAxis = new NumberAxis(yAxis);

		WhiskerIntervalRenderer medianRenderer = new WhiskerIntervalRenderer(0.);
		WhiskerIntervalRenderer whiskerRenderer = new WhiskerIntervalRenderer(.5);
		IntervalBarRenderer barRenderer = new IntervalBarRenderer();

		
	

		//		plot.setDataset(1, whiskerData);
		//		plot.setRenderer(1, whiskerRenderer);

		CategoryPlot plot = new CategoryPlot(medianData, domainAxis, rangeAxis, medianRenderer);
		//		CategoryPlot plot = new CategoryPlot(boxData, domainAxis, rangeAxis, barRenderer);
		//		
		//		
		//		
		//		
		plot.setDataset(0, medianData);
		plot.setDataset(1, boxData);
		plot.setDataset(2, whiskerData);

		plot.setRenderer(0, medianRenderer);
		plot.setRenderer(1, barRenderer);
		plot.setRenderer(2, whiskerRenderer);
		
		plot.getRenderer(0).setBaseSeriesVisibleInLegend(false);
		plot.getRenderer(2).setBaseSeriesVisibleInLegend(false);

		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);;

		barRenderer.setShadowVisible(false);
		barRenderer.setBarPainter(new StandardBarPainter());

		plot.setBackgroundPaint(Color.white);

		JFreeChart chart = new JFreeChart(title, plot);
		chart.getLegend().setVisible(showLegend);
		chart.setBackgroundPaint(Color.white);


		return chart;
	}

	public static <T extends Number> JFreeChart createIntervalCategoryChart(Map<String,T[]> dataset, String title, String xAxis, String yAxis, boolean showLegend, PlotOrientation orientation){
		//		 IntervalBarChartDemo1.java
		IntervalCategoryDataset data = createIntervalCategoryDataset(dataset);
		CategoryAxis domainAxis = new CategoryAxis(xAxis);
		NumberAxis rangeAxis = new NumberAxis(yAxis);
		IntervalBarRenderer renderer = new IntervalBarRenderer();
		CategoryPlot plot = new CategoryPlot(data, domainAxis, rangeAxis, renderer);
		plot.setOrientation(orientation);

		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);;

		renderer.setShadowVisible(false);
		renderer.setBarPainter(new StandardBarPainter());

		plot.setBackgroundPaint(Color.white);

		JFreeChart chart = new JFreeChart(title, plot);
		chart.getLegend().setVisible(false);
		chart.setBackgroundPaint(Color.white);


		return chart;
	}

	public static <T extends Number> JFreeChart createCategoryChart(Map<String,T> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		CategoryDataset data = createCategoryDataset(dataset);

		JFreeChart chart =  ChartFactory.createBarChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((CategoryPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((CategoryPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		//	((CategoryPlot) chart.getPlot()).setRenderer(new BarRenderer());
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setShadowVisible(false);
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setBarPainter(new StandardBarPainter());

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createCategoryChart(Map<String,T> dataset, String title, String xAxis, String yAxis, boolean showLegend, PlotOrientation orientation){
		// Make the PDB series and add it to the predicted strand locations
		CategoryDataset data = createCategoryDataset(dataset);

		JFreeChart chart =  ChartFactory.createBarChart(
				title,
				xAxis,
				yAxis,
				data,
				orientation,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((CategoryPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((CategoryPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		//	((CategoryPlot) chart.getPlot()).setRenderer(new BarRenderer());
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setShadowVisible(false);
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setBarPainter(new StandardBarPainter());

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart create2DCategoryChart(Map<String[],T> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		CategoryDataset data = create2DCategoryDataset(dataset);

		JFreeChart chart =  ChartFactory.createBarChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((CategoryPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((CategoryPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		//	((CategoryPlot) chart.getPlot()).setRenderer(new BarRenderer());
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setShadowVisible(false);
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setBarPainter(new StandardBarPainter());
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setItemMargin(0);

		chart.getPlot().setBackgroundPaint(Color.white);

		return chart;
	}

	public static <T extends Number> JFreeChart create2DCategoryChart(Map<String[],T> dataset, String title, String xAxis, String yAxis, boolean showLegend, PlotOrientation orientation){
		// Make the PDB series and add it to the predicted strand locations
		CategoryDataset data = create2DCategoryDataset(dataset);

		JFreeChart chart =  ChartFactory.createBarChart(
				title,
				xAxis,
				yAxis,
				data,
				orientation,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((CategoryPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((CategoryPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		//	((CategoryPlot) chart.getPlot()).setRenderer(new BarRenderer());
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setShadowVisible(false);
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setBarPainter(new StandardBarPainter());
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setItemMargin(0);

		chart.getPlot().setBackgroundPaint(Color.white);

		return chart;
	}

	public static <T extends Number> JFreeChart create2DStackedCategoryChart(Map<String[],T> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		CategoryDataset data = create2DCategoryDataset(dataset);

		JFreeChart chart =  ChartFactory.createBarChart(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		chart.getCategoryPlot().setRenderer(new StackedBarRenderer());
		chart.getCategoryPlot().getRenderer().setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		((CategoryPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((CategoryPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		//	((CategoryPlot) chart.getPlot()).setRenderer(new BarRenderer());
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setShadowVisible(false);
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setBarPainter(new StandardBarPainter());
		((BarRenderer)((CategoryPlot) chart.getPlot()).getRenderer()).setItemMargin(0);

		chart.getPlot().setBackgroundPaint(Color.white);

		return chart;
	}


	public static <T extends Number> JFreeChart createPathChart(Map<String,List<T[]>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		final XYDataset data = createXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createScatterPlot(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);

		PathRenderer renderer = new PathRenderer(false,false);
		renderer.setDrawSeriesLineAsPath(true);
		for(int i=0 ; i<data.getSeriesCount(); i++){
			renderer.setSeriesLinesVisible(i, true);
			renderer.setSeriesShapesVisible(i, false);
		}
		chart.getXYPlot().setRenderer(renderer);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	/**
	 * 
	 * @param dataset - each Double[] has an X,Y pair, and the line is connected
	 * in the order they occur in the array
	 * @param title
	 * @param xAxis - X axis label
	 * @param yAxis - Y axis label
	 * @return a chart that can be displayed
	 */
	public static <T extends Number> JFreeChart createScatterChart(Map<String,List<T[]>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		final XYDataset data = createXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createScatterPlot(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setDomainMinorGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeMinorGridlinesVisible(false);
		
		
		//		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		//		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		//		for(int i=0 ; i<data.getSeriesCount(); i++){
		//			renderer.setSeriesLinesVisible(i, false);
		//		}
		//		((XYPlot) chart.getPlot()).setRenderer(renderer);
		/*((XYPlot) chart.getPlot()).setRenderer(new XYShapeRenderer(){
			public Paint getItemPaint(int series, int item) { 
				int nSeries = data.getSeriesCount();
				int nItems = data.getItemCount(series);
				return new Color(Color.HSBtoRGB((float)(1.0*series/nSeries), 1f, (float)(1.0*item/nItems)));
			} 
		});*/
		//chart.getXYPlot().setDomainAxis(new LogarithmicAxis(xAxis));
		//	chart.getPlot().setBackgroundAlpha(0);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createAnnotatedScatterChart(Map<String,List<T[]>> dataset, Map<String,List<String>> annotations, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		final XYDataset data = createAnnotatedXYDataset(dataset, annotations);

		JFreeChart chart =  ChartFactory.createScatterPlot(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);

		chart.getXYPlot().getRenderer().setBaseToolTipGenerator(new AnnotationToolTipGenerator());
		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createScatterChart(Map<String,List<T[]>> dataset, final Map<String,Color> colors, String title, String xAxis, String yAxis, boolean showLegend){
		final XYDataset data = createXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createScatterPlot(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);

		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		((XYPlot) chart.getPlot()).setRenderer(new XYShapeRenderer(){
			public Paint getSeriesPaint(int series) {
				return colors.get(data.getSeriesKey(series));
			}
		});

		for(int i=0; i<data.getSeriesCount(); i++){	
			((XYPlot) chart.getPlot()).getRenderer().setSeriesToolTipGenerator(i, new StandardXYToolTipGenerator());
		}

		//	chart.getPlot().setBackgroundAlpha(0);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createScatterColorChart(Map<String,List<T[]>> dataset, final Map<String,List<Color>> colors, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		final XYDataset data = createXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createScatterPlot(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);

		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		((XYPlot) chart.getPlot()).setRenderer(new XYShapeRenderer(){
			public Paint getItemPaint(int series, int item) { 
				//int nSeries = data.getSeriesCount();
				//int nItems = data.getItemCount(series);
				//return new Color(Color.HSBtoRGB((float)(1.0*series/nSeries), 1f, (float)(1.0*item/nItems)));
				//   		data.getItemCount(series);
				return colors.get(data.getSeriesKey(series)).get(item);
			} 
		});

		//	chart.getPlot().setBackgroundAlpha(0);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createLineChart(Map<String,List<T[]>> dataset, final Map<String,Color> colors, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		final XYDataset data = createXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createScatterPlot(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);

		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		chart.getXYPlot().setRenderer(new XYLineAndShapeRenderer(true,false));
		for(int i=0; i<data.getSeriesCount(); i++){
			if(colors.containsKey(data.getSeriesKey(i))){
				chart.getXYPlot().getRenderer().setSeriesPaint(i, colors.get(data.getSeriesKey(i)));
				chart.getXYPlot().getRenderer().setSeriesToolTipGenerator(i, new StandardXYToolTipGenerator());
			}

			//			chart.getXYPlot().getRenderer().setSeriesStroke(i,new BasicStroke(2f));
		}

		//		chart.getXYPlot().getRangeAxis().setMinorTickMarkInsideLength(.1f);

		//		((XYPlot) chart.getPlot()).setRenderer(new XYLineAndShapeRenderer(true,false){
		//			public Paint getItemPaint(int series, int item) { 
		//				//int nSeries = data.getSeriesCount();
		//				//int nItems = data.getItemCount(series);
		//				//return new Color(Color.HSBtoRGB((float)(1.0*series/nSeries), 1f, (float)(1.0*item/nItems)));
		//				//   		data.getItemCount(series);
		//				return colors.get(data.getSeriesKey(series));
		//			} 
		//		});
		//		
		//		(chart.getPlot().getLegendItems().Legend()).setRenderer(new XYLineAndShapeRenderer(true,false){
		//			public Paint getItemPaint(int series, int item) { 
		//				//int nSeries = data.getSeriesCount();
		//				//int nItems = data.getItemCount(series);
		//				//return new Color(Color.HSBtoRGB((float)(1.0*series/nSeries), 1f, (float)(1.0*item/nItems)));
		//				//   		data.getItemCount(series);
		//				return colors.get(data.getSeriesKey(series));
		//			} 
		//		});

		//	chart.getPlot().setBackgroundAlpha(0);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> HistogramDataset createHistogramDataset(Map<String,List<T>> observations, int nbins){
		HistogramDataset dataset = new HistogramDataset();

		Collection<Double> combined = new LinkedList<Double>();
		for(T value : Util.combine(observations.values()))
			combined.add(value.doubleValue());

		double dataMin = combined.size() > 0 ? Collections.min(combined) : 0;
		double dataMax = combined.size() > 0 ? Collections.max(combined) : 0;

		return createHistogramDataset(observations, dataMin, dataMax, nbins, HistogramType.RELATIVE_FREQUENCY);
	}


	private static <T extends Number> BoxAndWhiskerCategoryDataset createBoxWhiskerDataset(Map<String,List<T>> observations) {
		DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

		for(Entry<String,List<T>> observation : observations.entrySet()){

			dataset.add(BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(observation.getValue(),true), "", observation.getKey());
		}

		return dataset;
	}

	public static <T extends Number> HistogramDataset createHistogramDataset(Map<String,List<T>> observations, double dataMin, double dataMax, int nbins, HistogramType histogramType){
		HistogramDataset dataset = new HistogramDataset();

		for(Entry<String,List<T>> observation : observations.entrySet()){
			double[] values = new double[observation.getValue().size()];

			for(int i=0;i<observation.getValue().size(); i++){
				values[i]=observation.getValue().get(i).doubleValue();
			}

			dataset.setType(histogramType);
			dataset.addSeries(observation.getKey(), values, nbins, dataMin, dataMax);

		}
		return dataset;
	}

	public static <T extends Number> JFreeChart createHistogram(Map<String,List<T>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		int nbins = 15;
		return createHistogram(dataset, nbins, title, xAxis, yAxis, showLegend);
	}

	public static <T extends Number> JFreeChart createHistogram(Map<String,List<T>> dataset, int nbins, HistogramType histType, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		Collection<Double> combined = new LinkedList<Double>();
		for(T value : Util.combine(dataset.values()))
			combined.add(value.doubleValue());

		double dataMin = combined.size() > 0 ? Collections.min(combined) : 0;
		double dataMax = combined.size() > 0 ? Collections.max(combined) : 0;

		IntervalXYDataset data = createHistogramDataset(dataset, dataMin, dataMax, nbins, histType);

		JFreeChart chart =  ChartFactory.createHistogram(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		((XYBarRenderer)((XYPlot) chart.getPlot()).getRenderer()).setShadowVisible(false);
		((XYBarRenderer)((XYPlot) chart.getPlot()).getRenderer()).setBarPainter(new StandardXYBarPainter());
		//	chart.getPlot().setBackgroundAlpha(0);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createHistogram(Map<String,List<T>> dataset, int nbins, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		IntervalXYDataset data = createHistogramDataset(dataset, nbins);
		JFreeChart chart =  ChartFactory.createHistogram(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);;
		((XYBarRenderer)((XYPlot) chart.getPlot()).getRenderer()).setShadowVisible(false);
		((XYBarRenderer)((XYPlot) chart.getPlot()).getRenderer()).setBarPainter(new StandardXYBarPainter());
		//	chart.getPlot().setBackgroundAlpha(0);

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createBoxWhisker(Map<String,List<T>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		BoxAndWhiskerCategoryDataset data = createBoxWhiskerDataset(dataset);
		JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(title, xAxis, yAxis, data, showLegend);
		((BoxAndWhiskerRenderer)chart.getCategoryPlot().getRenderer()).setMeanVisible(false);
		return chart;
	}


	public static <T extends Number> JFreeChart createHistogram(Map<String,List<T>> dataset, final Map<String,Color> seriesColors, double dataMin, double dataMax, int nbins, HistogramType histogramType, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		final IntervalXYDataset data = createHistogramDataset(dataset, dataMin, dataMax, nbins, histogramType);
		JFreeChart chart =  ChartFactory.createHistogram(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);
		XYBarRenderer renderer = ((XYBarRenderer)((XYPlot) chart.getPlot()).getRenderer());
		renderer.setShadowVisible(false);
		renderer.setBarPainter(new StandardXYBarPainter());


		for(int i=0; i<data.getSeriesCount(); i++){
			String key = (String) data.getSeriesKey(i);
			if(seriesColors.containsKey(key))
				renderer.setSeriesPaint(i, seriesColors.get(key));
		}

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createHistogram(Map<String,List<T>> dataset, double dataMin, double dataMax, int nbins, HistogramType histogramType, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		final IntervalXYDataset data = createHistogramDataset(dataset, dataMin, dataMax, nbins, histogramType);
		JFreeChart chart =  ChartFactory.createHistogram(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		((XYPlot) chart.getPlot()).setRangeGridlinesVisible(false);
		XYBarRenderer renderer = ((XYBarRenderer)((XYPlot) chart.getPlot()).getRenderer());
		renderer.setShadowVisible(false);
		renderer.setBarPainter(new StandardXYBarPainter());

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static <T extends Number> JFreeChart createHistogram(Map<String,List<T>> dataset, final Map<String,Color> seriesColors, int nbins, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		final IntervalXYDataset data = createHistogramDataset(dataset, nbins);
		JFreeChart chart =  ChartFactory.createHistogram(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		((XYPlot) chart.getPlot()).setDomainGridlinesVisible(false);
		XYBarRenderer renderer = ((XYBarRenderer)((XYPlot) chart.getPlot()).getRenderer());
		renderer.setShadowVisible(false);
		renderer.setBarPainter(new StandardXYBarPainter());


		for(int i=0; i<data.getSeriesCount(); i++){
			String key = (String) data.getSeriesKey(i);
			if(seriesColors.containsKey(key))
				renderer.setSeriesPaint(i, seriesColors.get(key));
		}

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;
	}

	public static void centerWindow(Window window){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		int w = window.getSize().width;
		int h = window.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		window.setLocation(x, y);
	}

	public static ChartPanel showChart(JFreeChart chart, int width, int height){


		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setMinimumDrawHeight(0);
		chartPanel.setMinimumDrawWidth(0);
		chartPanel.setMaximumDrawHeight(10000);
		chartPanel.setMaximumDrawWidth(10000);

		//		chartPanel.getChart().getXYPlot().setDomainPannable(true);
		//		chartPanel.getChart().getXYPlot().setRangePannable(true);

		chartPanel.setPreferredSize(new java.awt.Dimension(width, height));
		//		ApplicationFrame af = new ApplicationFrame(chart.getTitle().getText());

		final JFrame jf = new JFrame(chart.getTitle().getText());
		jf.setContentPane(chartPanel);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.pack();

		SwingUtilities.invokeLater(new Runnable() {	
			public void run() {

				jf.setVisible(true);
				centerWindow(jf);
			}
		});

		if(chart.getPlot() instanceof XYPlot){
			XYStrokeHoverListener l = new XYStrokeHoverListener(chart, chartPanel);
			chartPanel.addChartMouseListener(l);
		}


		return chartPanel;
		/*af.setContentPane(chartPanel);
		af.setDefaultCloseOperation(JFrame.DISPO SE_ON_CLOSE);
		af.pack();
		RefineryUtilities.centerFrameOnScreen(af);
		af.setVisible(true);	*/
	}

	public static ChartPanel showChart(JFreeChart chart){
		return showChart(chart, 300, 300);
		/*af.setContentPane(chartPanel);
		af.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		af.pack();
		RefineryUtilities.centerFrameOnScreen(af);
		af.setVisible(true);	*/
	}

	public static ChartPanel createChartPanel(JFreeChart jfc){
		return createChartPanel(jfc, 300, 300);
	}

	public static ChartPanel createChartPanel(JFreeChart jfc, int width, int height){
		ChartPanel panel = new ChartPanel(jfc,false);
		GraphicsUtils.setPrefferedSize(panel,width,height);
		return panel;
	}



	public static void saveSVG(JFreeChart chart, String fileName, int width, int height) throws IOException{
		ChartUtils.setThemeFont(chart, "arial");
		// Get a DOMImplementation.
		DOMImplementation domImpl =
				GenericDOMImplementation.getDOMImplementation();

		// Create an instance of org.w3c.dom.Document.
		String svgNS = "http://www.w3.org/2000/svg";
		Document document = domImpl.createDocument(svgNS, "svg", null);

		// Create an instance of the SVG Generator.
		SVGGraphics2D g = new SVGGraphics2D(document);

		// Ask the test to render into the SVG Graphics2D implementation.  
		chart.draw(g, new Rectangle2D.Double(0, 0, width, height));
		// Finally, stream out SVG to the standard output using
		// UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
		//   File toSave = new File(fileName);
		Writer out = new FileWriter(fileName);// OutputStreamWriter(System.out, "UTF-8");
		g.stream(out, useCSS);
	}

	public static ChartRenderingInfo savePDF(JFreeChart chart, String fileName, int width, int height) throws IOException{
		ChartUtils.setThemeFont(chart, "arial");
		Rectangle r = new Rectangle(width, height);
		com.itextpdf.text.Document document = new com.itextpdf.text.Document(r);

		try{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();

			PdfContentByte contentByte = writer.getDirectContent();
			PdfTemplate template = contentByte.createTemplate(width, height);
			Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
			Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);

			ChartRenderingInfo cri = new ChartRenderingInfo();
			chart.draw(graphics2d, rectangle2d, cri);

			graphics2d.dispose();
			contentByte.addTemplate(template, 0, 0);		 

			document.close();
			return cri;
		}
		catch (DocumentException e) {
			System.err.println(e.getStackTrace());
		}
		throw new RuntimeException("DocumentException");
	}

	public static ChartRenderingInfo savePDF(JFreeChart chart, String fileName, int width, int height, boolean tooltips) throws IOException {
		if(tooltips){
			try {
				File f = File.createTempFile("tmp", "pdf");
				ChartRenderingInfo cri = savePDF(chart, f.getAbsolutePath(), width, height);

				PdfReader reader = new PdfReader(f.getAbsolutePath());
				PdfStamper stamper;
				stamper = new PdfStamper(reader, new FileOutputStream(fileName));

				for(int i=0; i<cri.getEntityCollection().getEntityCount(); i++){
					ChartEntity ci = cri.getEntityCollection().getEntity(i);
					if(ci.getToolTipText()!=null){
						Shape s = ci.getArea();
						java.awt.Rectangle r = s.getBounds();
						Rectangle rect = new Rectangle((int)r.getMinX(),height-(int)r.getMinY(),(int)r.getMaxX(),height-(int)r.getMaxY());

						PdfAnnotation text = PdfAnnotation.createText(stamper.getWriter(), rect, "", ci.getToolTipText(), false, "");
						PdfAnnotation popup = PdfAnnotation.createPopup(stamper.getWriter(), rect, null, false);
						popup.put(PdfName.PARENT, text.getIndirectReference());
						// Declare the popup annotation as popup for the text
						text.put(PdfName.POPUP, popup.getIndirectReference());
						// Add both annotations
						stamper.addAnnotation(text, 1);
						stamper.addAnnotation(popup, 1);
					}
				}

				stamper.close();
				reader.close();


				return cri;
			} 
			catch (DocumentException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("DocumentException");
		}
		else{
			return savePDF(chart, fileName, width, height);
		}
	}

	public static void savePNG(JFreeChart chart, String fileName, int width, int height) throws IOException{
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		chart.draw((Graphics2D) bi.getGraphics(), new Rectangle2D.Double(0, 0, width, height));
		FileOutputStream fos = new FileOutputStream(fileName);
		ImageIO.write(bi, "PNG", new File(fileName));
		fos.close();
	}

	public static void saveImage(JFreeChart chart, String fileName, String format, int width, int height) throws IOException{
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		chart.draw((Graphics2D) bi.getGraphics(), new Rectangle2D.Double(0, 0, width, height));
		FileOutputStream fos = new FileOutputStream(fileName);
		ImageIO.write(bi, format, fos);
		fos.close();		
	}

	public static <T extends Number & Comparable<T>> JFreeChart createCumulativePlot(Map<String, ? extends Collection<T>> dataset,String title, String xAxis, String yAxis, boolean showLegend, boolean fraction) {
		Map<String,List<Number[]>> data = new Util.OrderedMap<String, List<Number[]>>();
		List<T> steps = new LinkedList<T>();
		for(String key : dataset.keySet()){
			steps.addAll(dataset.get(key));
		}
		steps = Util.list(Util.set(steps));
		Collections.sort(steps);
		for(String key : dataset.keySet()){
			Number previous = 0;
			List<Number[]> orderedData = new LinkedList<Number[]>();
			List<T> items = Util.list(dataset.get(key));
			for(final T step : steps){
				Criteria<T> lessThanStep = new Criteria<T>() {

					public boolean satisfies(T t) {
						return t.compareTo(step) <= 0;
					}
				};			

				double value = Util.filter(items, lessThanStep).size() / (fraction ? 1.*items.size() : 1);
				orderedData.add(new Number[]{step,previous});
				orderedData.add(new Number[]{step,value});
				previous = value;
			}
			data.put(key, orderedData);
		}

		JFreeChart chart = createLineChart(data, title, xAxis, yAxis, showLegend);
		for(int i=0; i<dataset.size(); i++)
			chart.getXYPlot().getRenderer().setSeriesStroke(i,new BasicStroke(stroke_width));

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;		
	}

	public static <T extends Number & Comparable<T>> JFreeChart createCumulativePlot(Map<String, ? extends Collection<T>> dataset,String title, String xAxis, String yAxis, boolean showLegend, boolean fraction, boolean leftToRight) {
		Map<String,List<Number[]>> data = new Util.OrderedMap<String, List<Number[]>>();

		ExtremeTracker<T> et = new ExtremeTracker<T>();
		MapList<String, T> nonNull = new MapList<String, T>();

		for(String key : dataset.keySet()){
			Collection<T> c = dataset.get(key);
			for(T t : c){
				if(t!=null){
					nonNull.put(key, t);
					et.put(t);
				}
			}
		}

		for(String key : dataset.keySet()){
			int n = dataset.get(key).size();
			List<Number[]> orderedData = new LinkedList<Number[]>();

			List<T> values = nonNull.get(key);
			int l = values.size();
			Collections.sort(values);

			if(l==0){
				orderedData.add(new Number[]{et.getMin(), 0});
				orderedData.add(new Number[]{et.getMax(), 0});

			}
			else{
				if(leftToRight){

					int i = 0;
					int previousIndex = i;

					if(values.get(0).compareTo(et.getMin()) > 0){
						orderedData.add(new Number[]{et.getMin(), i / (fraction ? n+0. : 1)});
					}

					Number previousValue = values.get(i);
					i++;
					while(i<l){
						Number nextValue = values.get(i);
						if(!nextValue.equals(previousValue)){
							orderedData.add(new Number[]{previousValue, previousIndex / (fraction ? n+0. : 1)});
							orderedData.add(new Number[]{previousValue, i / (fraction ? n+0. : 1)});
							previousIndex = i;
						}
						previousValue = nextValue;
						i++;
					}
					orderedData.add(new Number[]{previousValue, previousIndex / (fraction ? n+0. : 1)});
					orderedData.add(new Number[]{previousValue, i / (fraction ? n+0. : 1)});

					if(values.get(values.size()-1).compareTo(et.getMax()) < 0){
						orderedData.add(new Number[]{et.getMax(), i / (fraction ? n+0. : 1)});
					}
				}
				else{
					int i = 0;
					int previousIndex = i;

					if(values.get(l-i-1).compareTo(et.getMax()) < 0){
						orderedData.add(new Number[]{et.getMax(), i / (fraction ? n+0. : 1)});
					}

					Number previousValue = values.get(l-i-1);
					i++;
					while(i<l){
						Number nextValue = values.get(l-i-1);
						if(!nextValue.equals(previousValue)){
							orderedData.add(new Number[]{previousValue, previousIndex / (fraction ? n+0. : 1)});
							orderedData.add(new Number[]{previousValue, i / (fraction ? n+0. : 1)});
							previousIndex = i;
						}
						previousValue = nextValue;
						i++;
					}
					orderedData.add(new Number[]{previousValue, previousIndex / (fraction ? n+0. : 1)});
					orderedData.add(new Number[]{previousValue, i / (fraction ? n+0. : 1)});

					if(values.get(0).compareTo(et.getMin()) > 0){
						orderedData.add(new Number[]{et.getMin(), i / (fraction ? n+0. : 1)});
					}
				}
			}

			data.put(key, orderedData);
		}

		JFreeChart chart = createLineChart(data, title, xAxis, yAxis, showLegend);
		for(int i=0; i<dataset.size(); i++)
			chart.getXYPlot().getRenderer().setSeriesStroke(i,new BasicStroke(stroke_width));

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;		
	}

	public static <T extends Number & Comparable<T>> JFreeChart createCumulativeFractionPlot(Map<String, ? extends Collection<T>> dataset,String title, String xAxis, String yAxis, boolean showLegend, boolean integerTicks) {
		Map<String,List<Number[]>> data = new Util.OrderedMap<String, List<Number[]>>();
		List<T> steps = new LinkedList<T>();
		for(String key : dataset.keySet()){
			steps.addAll(dataset.get(key));
		}
		steps = Util.list(Util.set(steps));
		Collections.sort(steps);
		for(String key : dataset.keySet()){
			Number previous = 0;
			List<Number[]> orderedData = new LinkedList<Number[]>();
			List<T> items = Util.list(dataset.get(key));
			for(final T step : steps){
				Criteria<T> lessThanStep = new Criteria<T>() {

					public boolean satisfies(T t) {
						return t.compareTo(step) <= 0;
					}
				};			
				double fractionLessThan = Util.filter(items, lessThanStep).size()*1./items.size();
				orderedData.add(new Number[]{step,previous});
				orderedData.add(new Number[]{step,fractionLessThan});
				previous = fractionLessThan;
			}
			data.put(key, orderedData);
		}

		JFreeChart chart = createLineChart(data, title, xAxis, yAxis, showLegend);
		for(int i=0; i<dataset.size(); i++)
			chart.getXYPlot().getRenderer().setSeriesStroke(i,new BasicStroke(stroke_width));
		if(integerTicks)
			((XYPlot)chart.getPlot()).getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		chart.getPlot().setBackgroundPaint(Color.white);
		return chart;		
	}

	public static Component matrix(Component[][] plots){
		JPanel jp = new JPanel();
		jp.setBackground(Color.white);
		int h = plots.length;
		int w = plots[0].length;
		jp.setLayout(new GridLayout(h,w));
		JPanel[][] panels = new JPanel[h][w];
		for(int i=0; i<h; i++){
			for(int j=0; j<w; j++){
				panels[i][j] = new JPanel(new BorderLayout());
				panels[i][j].setBackground(Color.white);
				jp.add(panels[i][j]);
			}
		}

		for(int i=0; i<h; i++){
			for(int j=0; j<w; j++){
				if(plots[i][j]!=null)
					panels[i][j].add(plots[i][j]);
			}
		}

		return jp;
	}

	public static Component matrix(Component[][] plots, int hgap, int vgap){
		JPanel jp = new JPanel();
		jp.setBackground(Color.white);
		int h = plots.length;
		int w = plots[0].length;
		GridLayout layout = new GridLayout(h,w,hgap,vgap);
		jp.setLayout(layout);
		JPanel[][] panels = new JPanel[h][w];
		for(int i=0; i<h; i++){
			for(int j=0; j<w; j++){
				panels[i][j] = new JPanel(new BorderLayout());
				panels[i][j].setBackground(Color.white);
				jp.add(panels[i][j]);
			}
		}

		for(int i=0; i<h; i++){
			for(int j=0; j<w; j++){
				if(plots[i][j]!=null)
					panels[i][j].add(plots[i][j],BorderLayout.NORTH);
			}
		}

		return jp;
	}

	//	public static Component matrix2(Component[][] plots, int hgap, int vgap){
	//		JPanel jp = new JPanel();
	//		jp.setBackground(Color.white);
	//		int h = plots.length;
	//		int w = plots[0].length;
	//		jp.setLayout(new GridLayout(h,w,hgap,vgap));
	//		JPanel[][] panels = new JPanel[h][w];
	//		for(int i=0; i<h; i++){
	//			for(int j=0; j<w; j++){
	//				panels[i][j] = new JPanel(new BorderLayout());
	//				panels[i][j].setLayout(new GridBagLayout());
	//				GridBagConstraints c = new GridBagConstraints();
	//				c.fill = GridBagConstraints.HORIZONTAL;
	//				panels[i][j].setBackground(Color.white);
	//				jp.add(panels[i][j]);
	//			}
	//		}
	//
	//		for(int i=0; i<h; i++){
	//			for(int j=0; j<w; j++){
	//				if(plots[i][j]!=null)
	//					panels[i][j].add(plots[i][j]);
	//				else{
	//					
	//				}
	//			}
	//		}

	//		return jp;
	//	}


	public static <T extends Number> Component pairs(Map<String,List<T>> data, Map<List<String>,List<Color>> colors, int width, int height){
		//		MapList<String,Integer[]> dataset = new MapList<String, Integer[]>();
		//		dataset.put("a", new Integer[]{1,1});
		//		dataset.put("a", new Integer[]{1,2});
		//		dataset.put("a", new Integer[]{1,3});
		//		dataset.put("b", new Integer[]{2,1});
		//		dataset.put("b", new Integer[]{3,2});
		//		dataset.put("b", new Integer[]{4,3});
		//			
		//		JFreeChart jfc = createScatterChart(dataset.getMap(), "", "", "", false);
		//		JFrame jf = new JFrame();

		List<String> labels = Util.list(data.keySet());
		JPanel jp = new JPanel();
		int n = labels.size();
		jp.setLayout(new GridLayout(n,n));
		JPanel[][] panels = new JPanel[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				panels[i][j] = new JPanel(new BorderLayout());
				jp.add(panels[i][j]);
			}
		}
		for(int i=0; i<n; i++){
			JPanel label = new JPanel();
			//			label.setLayout(new B());
			//			label.add(new JLabel("label"));
			Box vb = Box.createVerticalBox();
			Box hb = Box.createHorizontalBox();
			vb.add(Box.createVerticalGlue());
			vb.add(hb);
			vb.add(Box.createVerticalGlue());
			hb.add(Box.createHorizontalGlue());
			String series = labels.get(i);
			JLabel jl = new JLabel(series);
			Font f = Font.decode("arial-24");
			jl.setFont(f);
			hb.add(jl);
			hb.add(Box.createHorizontalGlue());
			label.add(vb);
			panels[i][i].setBackground(Color.white);
			panels[i][i].add(hb);
		}

		double min_range = .001, max_range = 10;
		MapList<String,Double[]> regression_line_points = new MapList<String, Double[]>();
		regression_line_points.put("line", new Double[]{min_range, min_range});
		regression_line_points.put("line", new Double[]{max_range, max_range});
		XYDataset regression_line = createXYDataset(regression_line_points.getMap());
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(i==j)
					continue;
				MapList<String, T[]> dataset = new MapList<String, T[]>();
				MapList<String, Color> datasetColors = new MapList<String, Color>();
				String labeli = labels.get(i);
				String labelj = labels.get(j);
				List<Color> col = colors.get(Util.list(labeli,labelj));
				List<T> ti = data.get(labeli);
				List<T> tj = data.get(labelj);
				for(int k=0; k<ti.size(); k++){
					T tik = ti.get(k);
					T tjk = tj.get(k);
					T[] t = (T[]) Array.newInstance(tik.getClass(), 2);
					t[0] = tjk;
					t[1] = tik;
					dataset.put(labeli+":"+labelj,  t);
					datasetColors.put(labeli+":"+labelj,  col.get(k));
				}
				JFreeChart jfc = createScatterColorChart(dataset.getMap(), datasetColors.getMap(), "", "", "", false);
				jfc.getXYPlot().setDataset(1, regression_line);
				jfc.getXYPlot().setRenderer(1, new XYLineAndShapeRenderer(true, false){
					public Paint getSeriesPaint(int series) {
						return Color.black;
					}
				});
				jfc.getXYPlot().setDomainAxis(new LogAxis(""));
				jfc.getXYPlot().setRangeAxis(new LogAxis(""));
				jfc.getXYPlot().getRangeAxis().setRange(min_range, max_range);
				jfc.getXYPlot().getDomainAxis().setRange(min_range, max_range);
				jfc.getXYPlot().getDomainAxis().setMinorTickMarksVisible(false);
				jfc.getXYPlot().getRangeAxis().setMinorTickMarksVisible(false);
				// setting use buffer to false fixed the save chart panel as an image problem
				panels[i][j].add(new ChartPanel(jfc,false));
			}
		}

		jp.setPreferredSize(new Dimension(width,height));
		//		JFrame jf = new JFrame();
		//			jf.setContentPane(jp);
		//		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//		jf.pack();
		//		jf.setVisible(true);
		//		centerWindow(jf);	

		return jp;
	}

	//	public static JPanel

	public static Component pairs(Map<List<String>,Component> plots, List<String> order){
		JPanel jp = new JPanel();
		jp.setBackground(Color.white);
		int n = order.size();
		jp.setLayout(new GridLayout(n,n));
		JPanel[][] panels = new JPanel[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				panels[i][j] = new JPanel(new BorderLayout());
				panels[i][j].setBackground(Color.white);
				jp.add(panels[i][j]);
			}
		}
		for(int i=0; i<n; i++){
			JPanel label = new JPanel();
			label.setBackground(Color.white);
			//			label.setLayout(new B());
			//			label.add(new JLabel("label"));
			Box vb = Box.createVerticalBox();
			Box hb = Box.createHorizontalBox();
			vb.add(Box.createVerticalGlue());
			vb.add(hb);
			vb.add(Box.createVerticalGlue());
			hb.add(Box.createHorizontalGlue());
			String series = order.get(i);
			hb.add(new JLabel(series));
			hb.add(Box.createHorizontalGlue());
			label.add(vb);
			panels[i][i].add(hb);
		}
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(i==j)
					continue;
				String labeli = order.get(i);
				String labelj = order.get(j);

				if(plots.containsKey(Util.list(labeli,labelj)))
					panels[i][j].add(plots.get(Util.list(labeli,labelj)));
			}
		}

		//		JFrame jf = new JFrame();
		//		jf.setContentPane(jp);
		//		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//		jf.pack();
		//		jf.setVisible(true);
		//		centerWindow(jf);	

		return jp;
	}

	public static <T extends Number> Component pairs(Map<String,List<T>> data){
		//		MapList<String,Integer[]> dataset = new MapList<String, Integer[]>();
		//		dataset.put("a", new Integer[]{1,1});
		//		dataset.put("a", new Integer[]{1,2});
		//		dataset.put("a", new Integer[]{1,3});
		//		dataset.put("b", new Integer[]{2,1});
		//		dataset.put("b", new Integer[]{3,2});
		//		dataset.put("b", new Integer[]{4,3});
		//			
		//		JFreeChart jfc = createScatterChart(dataset.getMap(), "", "", "", false);
		//		JFrame jf = new JFrame();

		List<String> labels = Util.list(data.keySet());
		JPanel jp = new JPanel();
		int n = labels.size();
		jp.setLayout(new GridLayout(n,n));
		JPanel[][] panels = new JPanel[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				panels[i][j] = new JPanel(new BorderLayout());
				jp.add(panels[i][j]);
			}
		}
		for(int i=0; i<n; i++){
			JPanel label = new JPanel();
			//			label.setLayout(new B());
			//			label.add(new JLabel("label"));
			Box vb = Box.createVerticalBox();
			Box hb = Box.createHorizontalBox();
			vb.add(Box.createVerticalGlue());
			vb.add(hb);
			vb.add(Box.createVerticalGlue());
			hb.add(Box.createHorizontalGlue());
			String series = labels.get(i);
			hb.add(new JLabel(series));
			hb.add(Box.createHorizontalGlue());
			label.add(vb);
			panels[i][i].add(hb);
		}
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(i==j)
					continue;
				MapList<String, T[]> dataset = new MapList<String, T[]>();
				String labeli = labels.get(i);
				String labelj = labels.get(j);
				List<T> ti = data.get(labeli);
				List<T> tj = data.get(labelj);
				for(int k=0; k<ti.size(); k++){
					T tik = ti.get(k);
					T tjk = tj.get(k);
					T[] t = (T[]) Array.newInstance(tik.getClass(), 2);
					t[0] = tjk;
					t[1] = tik;
					dataset.put(labeli+":"+labelj,  t);
				}
				JFreeChart jfc = createScatterChart(dataset.getMap(), "", "", "", false);
				// setting use buffer to false fixed the save chart panel as an image problem
				panels[i][j].add(new ChartPanel(jfc, false));
			}
		}

		return jp;
	}

	public static <T extends Number> void scatterline(Map<String,List<T[]>> dataset, String title, String xAxis, String yAxis, boolean showLegend){
		// Make the PDB series and add it to the predicted strand locations
		final XYDataset data = createXYDataset(dataset);

		JFreeChart chart =  ChartFactory.createScatterPlot(
				title,
				xAxis,
				yAxis,
				data,
				PlotOrientation.VERTICAL,
				showLegend, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);


		chart.getPlot().setBackgroundPaint(Color.white);
		//		chart.getXYPlot().setRenderer(0, null);
		//		chart.getXYPlot().setRenderer(0, (new XYShapeRenderer(){
		//			
		//		}));
		chart.getXYPlot().setDataset(1, data);

		chart.getXYPlot().setRenderer(1, new XYLineAndShapeRenderer(true,false));
		showChart(chart);
	}

	public static void setXYSeriesPaint(Map<String,Color> seriesColors, JFreeChart chart){
		XYDataset data = chart.getXYPlot().getDataset();
		XYBarRenderer renderer = ((XYBarRenderer)((XYPlot) chart.getPlot()).getRenderer());
		renderer.setShadowVisible(false);
		renderer.setBarPainter(new StandardXYBarPainter());


		for(int i=0; i<data.getSeriesCount(); i++){
			String key = (String) data.getSeriesKey(i);
			if(seriesColors.containsKey(key))
				renderer.setSeriesPaint(i, seriesColors.get(key));
		}
	}

	public static class CustomBarRenderer extends BarRenderer{

		Paint[][] paints;

		public CustomBarRenderer(Paint[][] paints) {
			this.paints = paints;
		}


		public Paint getItemPaint(int row, int column) {
			return paints[row][column];
		}
	}
	
	public static class CustomStackedBarRenderer extends StackedBarRenderer{
		Paint[][] paints;

		public CustomStackedBarRenderer(Paint[][] paints) {
			this.paints = paints;
		}


		public Paint getItemPaint(int row, int column) {
			return paints[row][column];
		}
		
	}

	public static void setCategorySeriesPaint(Map<String,Color> seriesColors, JFreeChart chart){
		CategoryDataset data = chart.getCategoryPlot().getDataset();

		Paint[][] paints = new Paint[data.getRowCount()][data.getColumnCount()];

		boolean byRow = data.getRowCount()>1;
		for(int row=0; row<data.getRowCount(); row++){
		for(int i=0; i<data.getColumnCount(); i++){
			String key = (String)(byRow ? data.getRowKey(row) : data.getColumnKey(i));

			if(seriesColors.containsKey(key)){
				
				paints[row][i] = seriesColors.get(key);
				//				renderer.setSeriesPaint(i, seriesColors.get(key));
			}
		}
		}

		
		BarRenderer painter = chart.getCategoryPlot().getRenderer() instanceof StackedBarRenderer ? new CustomStackedBarRenderer(paints) : new CustomBarRenderer(paints);
		painter.setShadowVisible(false);
		painter.setBarPainter(new StandardBarPainter());
		
		//		((BarRenderer) chart.getCategoryPlot().getRenderer()).setBarPainter(painter);
		chart.getCategoryPlot().setRenderer(painter);
		//		System.out.println();
	}

	public static void setSeriesStrokes(Map<String,Stroke> strokes, JFreeChart chart){
		XYDataset data = chart.getXYPlot().getDataset();
		XYItemRenderer renderer = chart.getXYPlot().getRenderer();
		for(int i=0; i<data.getSeriesCount(); i++){
			Stroke stroke = strokes.get(data.getSeriesKey(i));
			if(strokes != null)
				renderer.setSeriesStroke(i, stroke);
		}
	}

	public static void setAllStrokes(JFreeChart chart, Stroke stroke){
		XYDataset data = chart.getXYPlot().getDataset();
		XYItemRenderer renderer = chart.getXYPlot().getRenderer();
		for(int i=0; i<data.getSeriesCount(); i++){
			renderer.setSeriesStroke(i, stroke); 
		}
	}

	public static void setXYSeriesColors(JFreeChart chart, Map<String, ? extends Paint> colors){
		chart.getXYPlot().setRenderer(new XYLineAndShapeRenderer(true,false));
		XYDataset data = chart.getXYPlot().getDataset();
		for(int i=0; i<data.getSeriesCount(); i++){
			if(colors.containsKey(data.getSeriesKey(i))){
				chart.getXYPlot().getRenderer().setSeriesPaint(i, colors.get(data.getSeriesKey(i)));
				((XYPlot) chart.getPlot()).getRenderer().setSeriesToolTipGenerator(i, new StandardXYToolTipGenerator());
			}
		}
	}

	public static void setXYStandardTooltips(JFreeChart chart, Map<String, ? extends Paint> colors){
		XYDataset data = chart.getXYPlot().getDataset();
		XYItemRenderer r = ((XYPlot)chart.getPlot()).getRenderer();
		for(int i=0; i<data.getSeriesCount(); i++){
			r.setSeriesToolTipGenerator(i, new StandardXYToolTipGenerator());	
		}
	}

	public static void setCategoryStandardTooltips(JFreeChart chart){
		CategoryDataset data = chart.getCategoryPlot().getDataset();
		CategoryItemRenderer r = ((CategoryPlot)chart.getPlot()).getRenderer();
		for(int i=0; i<data.getRowCount(); i++){
			r.setSeriesToolTipGenerator(i, new StandardCategoryToolTipGenerator());	
		}
	}

	public static void setXYSeriesColors(JFreeChart chart, Map<String, ? extends Paint> colors, boolean lines, boolean shapes){
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(lines,shapes);
		renderer.setDrawOutlines(false);
		renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

		chart.getXYPlot().setRenderer(renderer);
		XYDataset data = chart.getXYPlot().getDataset();
		for(int i=0; i<data.getSeriesCount(); i++){
			if(colors.containsKey(data.getSeriesKey(i))){
				chart.getXYPlot().getRenderer().setSeriesPaint(i, colors.get(data.getSeriesKey(i)));
			}
		}		
	}

	/**
	 * transforms all series shapes by multiplying their size by scale
	 * @param chart
	 * @param scale
	 */
	public static void setSeriesShapeSize(JFreeChart chart, double scale){
		XYDataset data = chart.getXYPlot().getDataset();
		XYItemRenderer renderer = chart.getXYPlot().getRenderer();
		for(int i=0; i<data.getSeriesCount(); i++){
			//			renderer.setSeriesShape(i, AffineTransform.getScaleInstance(scale, scale).createTransformedShape(renderer.getSeriesShape(i)));
			Shape s = renderer.getSeriesShape(i);
			if(s == null)
				s = renderer.getBaseShape();
			//System.out.println(			AffineTransform.getScaleInstance(scale, scale).createTransformedShape(s));
			renderer.setSeriesShape(i, AffineTransform.getScaleInstance(scale, scale).createTransformedShape(s));

			//			System.out.println("here");
		}
		//		renderer.setBaseShape(AffineTransform.getScaleInstance(scale, scale).createTransformedShape(renderer.getBaseShape()));
		//		renderer.setBaseShape(new Ellipse2D.Double(0,0,scale,scale));
	}

	public static void setBaseShape(JFreeChart chart, Shape shape){
		//		chart.getXYPlot().getRenderer().setBaseShape(shape);
		for(int i=0; i<chart.getXYPlot().getSeriesCount(); i++){
			//			XYItemRenderer renderer = chart.getXYPlot().getRenderer(i);
			//			renderer.setBaseShape(shape);
			chart.getXYPlot().getRenderer().setSeriesShape(i, shape);
		}
	}

	public static void setXYSeriesShape(JFreeChart chart, Shape shape, String series){
		XYDataset data = chart.getXYPlot().getDataset();
		for(int i=0; i<data.getSeriesCount(); i++){
			if(series.equals(data.getSeriesKey(i))){
				chart.getXYPlot().getRenderer().setSeriesShape(i, shape);
			}
		}	
	}

	public static void setXYRangeAxisVisible(JFreeChart chart, boolean visible){
		chart.getXYPlot().getRangeAxis().setVisible(visible);
	}

	public static void setXYDomainAxisVisible(JFreeChart chart, boolean visible){
		chart.getXYPlot().getDomainAxis().setVisible(visible);
	}

	public static void setCategoryRangeAxisVisible(JFreeChart chart, boolean visible){
		chart.getCategoryPlot().getRangeAxis().setVisible(visible);
	}

	public static void setCategoryDomainAxisVisible(JFreeChart chart, boolean visible){
		chart.getCategoryPlot().getDomainAxis().setVisible(visible);
	}

	public static void setCategoryDomainAxisLabelVericalRotation(JFreeChart jfc, boolean up){
		CategoryAxis domainAxis = jfc.getCategoryPlot().getDomainAxis();
		if(up)
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
		else
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
	}

	public static void setCategoryDomainAxisLabelDiagonalRotation(JFreeChart jfc, boolean up){
		CategoryAxis domainAxis = jfc.getCategoryPlot().getDomainAxis();
		if(up)
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		else
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
	}


	public static void setRangeAxisRange(JFreeChart chart, double lower, double upper){
		if(chart.getPlot() instanceof XYPlot){
			chart.getXYPlot().getRangeAxis().setRange(lower, upper);
		}
		else if(chart.getPlot() instanceof CategoryPlot){
			chart.getCategoryPlot().getRangeAxis().setRange(lower, upper);
		}
		else{
			throw new RuntimeException("Plot type is not XY or Category");
		}
	}

	public static void setDomainAxisRange(JFreeChart chart, double lower, double upper){
		chart.getXYPlot().getDomainAxis().setRange(lower, upper);
	}

	public static Range getDomainAxisRange(JFreeChart chart){
		return chart.getXYPlot().getDomainAxis().getRange();
	}

	public static Range getRangeAxisRange(JFreeChart chart){
		return chart.getXYPlot().getRangeAxis().getRange();
	}

	public static void setRangeAxisTickInterval(JFreeChart chart, double d){
		chart.getXYPlot().getRangeAxis().setMinorTickMarkInsideLength((float) d);
	}

	public static void setDomainAxisTickInterval(JFreeChart chart, double d){
		chart.getXYPlot().getDomainAxis().setMinorTickMarkInsideLength((float) d);		
	}

	public static void setAxesLog(JFreeChart chart){
		setDomainAxisLog(chart);
		setRangeAxisLog(chart);
	}
	public static void setDomainAxisLog(JFreeChart chart){
		//		NumberAxis logaxis = new LogarithmicAxis(chart.getXYPlot().getDomainAxis().getLabel());
		LogAxis logaxis = new LogAxis(chart.getXYPlot().getDomainAxis().getLabel());
		logaxis.setNumberFormatOverride(new DecimalFormat("0.#"));
		//		logaxis.sets
		//		logaxis.setMinorTickCount(0);
		chart.getXYPlot().setDomainAxis(logaxis);
	}

	public static void setRangeAxisLog(JFreeChart chart){
		//		NumberAxis logaxis = new LogarithmicAxis(chart.getXYPlot().getRangeAxis().getLabel());
		if(chart.getPlot() instanceof XYPlot){
			LogAxis logaxis = new LogAxis(chart.getXYPlot().getRangeAxis().getLabel());
			logaxis.setNumberFormatOverride(new DecimalFormat("0.#"));
			chart.getXYPlot().setRangeAxis(logaxis);
		}
		else if(chart.getPlot() instanceof CategoryPlot){
			LogAxis logaxis = new LogAxis(chart.getCategoryPlot().getRangeAxis().getLabel());
			logaxis.setNumberFormatOverride(new DecimalFormat("0.#"));
			chart.getCategoryPlot().setRangeAxis(logaxis);	
		}
		else{
			throw new RuntimeException("Plot is not XY or Category");
		}
	}

	public static void addTextAnnotation(XYPlot plot, String text, double x, double y){
		XYTextAnnotation annotation = new XYTextAnnotation(text, x, y);
		annotation.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
		plot.addAnnotation(annotation);
	}

	public static void addTextAnnotation(CategoryPlot plot, String text, String category, double y){
		CategoryTextAnnotation annotation = new CategoryTextAnnotation(text, category, y);
		annotation.setCategoryAnchor(CategoryAnchor.MIDDLE);
		annotation.setPaint(Color.white);
		plot.addAnnotation(annotation);
	}

	public static void addLineAnnotation(CategoryPlot plot, String category1, String category2, double y1, double y2){
		CategoryLineAnnotation annotation = new CategoryLineAnnotation(category1, y1, category2, y2, Color.black, new BasicStroke(1));
		annotation.setPaint(Color.white);
		plot.addAnnotation(annotation);
	}

	public static void addLineAnnotation(XYPlot plot, double x1, double y1, double x2, double y2){
		plot.addAnnotation(new XYLineAnnotation(x1, y1, x2, y2));
	}

	public static void addShapeAnnotation(XYPlot plot, Shape shape){
		plot.addAnnotation(new XYShapeAnnotation(shape));
	}

	public static void setIntegerTicks(ValueAxis axis){
		axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	}

	//	public static TickUnitSource createLogIntegerTickUnits() {
	//		TickUnits units = new TickUnits();
	//		NumberFormat numberFormat = NumberFormat.getNumberInstance();
	//		units.add(new NumberTickUnit(1, numberFormat, 2));
	//		units.add(new NumberTickUnit(10, numberFormat, 2));
	//		units.add(new NumberTickUnit(100, numberFormat, 2));
	//		units.add(new NumberTickUnit(1000, numberFormat, 2));
	//		units.add(new NumberTickUnit(10000, numberFormat, 2));
	//		units.add(new NumberTickUnit(100000, numberFormat, 2));
	//		units.add(new NumberTickUnit(1000000, numberFormat, 2));
	//		units.add(new NumberTickUnit(10000000, numberFormat, 2));
	//		units.add(new NumberTickUnit(100000000, numberFormat, 2));
	//		units.add(new NumberTickUnit(1000000000, numberFormat, 2));
	//		units.add(new NumberTickUnit(10000000000.0, numberFormat, 2));
	//		return units;
	//	}
	//	
	//	public static void setDomainLogTickUnits(JFreeChart chart){
	//		((NumberAxis)chart.getXYPlot().getDomainAxis()).setStandardTickUnits(createLogIntegerTickUnits());
	//	}

	public static StandardChartTheme createTheme(){
		StandardChartTheme chartTheme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();

		chartTheme.setPlotBackgroundPaint(Color.white);
		chartTheme.setBarPainter(new StandardBarPainter());
		chartTheme.setShadowVisible(false);
		Color color= Color.black;

		chartTheme.setAxisLabelPaint(color);
		chartTheme.setLegendItemPaint(color);
		chartTheme.setItemLabelPaint(color);

		return chartTheme;
	}

	/**
	 * 
	 * @param jfc
	 * @param fontName
	 * @param fontSizes - can set XL, L, R, S
	 */
	public static void setThemeFont(JFreeChart jfc, String fontName, Map<String,Integer> fontSizes){
		StandardChartTheme chartTheme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();
		//				(StandardChartTheme) ChartFactory.getChartTheme();


		chartTheme.setPlotBackgroundPaint(Color.white);
		//		chartTheme.setBarPainter(jfc.getCategoryPlot().getRenderer());
		chartTheme.setShadowVisible(false);
		Color color= Color.black;

		chartTheme.setExtraLargeFont(new Font(fontName, Font.PLAIN, chartTheme.getExtraLargeFont().getSize()));
		chartTheme.setLargeFont(new Font(fontName, Font.PLAIN, chartTheme.getLargeFont().getSize()));
		chartTheme.setRegularFont(new Font(fontName, Font.PLAIN, chartTheme.getRegularFont().getSize()));
		chartTheme.setSmallFont(new Font(fontName, Font.PLAIN, chartTheme.getSmallFont().getSize()));

		if(fontSizes.containsKey("XL"))
			chartTheme.setExtraLargeFont(new Font(fontName, Font.PLAIN, fontSizes.get("XL")));
		if(fontSizes.containsKey("L"))
			chartTheme.setLargeFont(new Font(fontName, Font.PLAIN, fontSizes.get("L")));
		if(fontSizes.containsKey("R"))
			chartTheme.setRegularFont(new Font(fontName, Font.PLAIN, fontSizes.get("R")));
		if(fontSizes.containsKey("S"))
			chartTheme.setSmallFont(new Font(fontName, Font.PLAIN, fontSizes.get("S")));

		chartTheme.setAxisLabelPaint(color);
		chartTheme.setLegendItemPaint(color);
		chartTheme.setItemLabelPaint(color);

		chartTheme.apply(jfc);
		
		if(jfc.getPlot() instanceof CategoryPlot){
			CategoryPlot cplot = (CategoryPlot) jfc.getPlot();
			if(cplot.getRenderer() instanceof BarRenderer){
				BarRenderer r = (BarRenderer) cplot.getRenderer();
				r.setShadowVisible(false);
				r.setBarPainter(new StandardBarPainter());
				r.setItemMargin(0);
			}

		}
	}

	public static void setThemeFont(JFreeChart jfc, String fontName){
		setThemeFont(jfc, fontName, new HashMap<String, Integer>());
	}



	public static void testpoly(){
		MapList<String,Double[]> data = new MapList<String, Double[]>();
		data.put("a",new Double[]{1.,0.9});
		data.put("a",new Double[]{2.,2.1});
		data.put("a",new Double[]{3.,2.9});
		data.put("b",new Double[]{1.,1.});
		data.put("b",new Double[]{2.,2.});
		data.put("b",new Double[]{3.,3.});
		JFreeChart jfc = createLineChart(data.getMap(), "", "", "", false);
		jfc.getXYPlot().addAnnotation(new XYShapeAnnotation(new Polygon2D(new float[]{1,3,2}, new float[]{1,1,2.5f}, 3), new BasicStroke(), Util.hsba(0, 1, 1, .05),Util.hsba(0, 1, 1, .25)));
		showChart(jfc);
	}

	static class XYClickReporter implements ChartMouseListener{

		ChartPanel panel;
		PrintStream out;
		XYPlot plot;

		/**
		 * 
		 * @param panel the panel on which the listener will be reporting
		 */
		public XYClickReporter(ChartPanel panel, PrintStream out) {
			this.panel = panel;
			this.out = out;
			plot = panel.getChart().getXYPlot();
		}

		public void chartMouseClicked(ChartMouseEvent e) {
			Point2D.Double p = getChartCoord(e.getTrigger().getPoint());
			out.printf("%e\t%e\n",p.x,p.y);
		}

		public void chartMouseMoved(ChartMouseEvent e) {

		}

		Point2D.Double getChartCoord(Point p){
			Rectangle2D plotArea = panel.getScreenDataArea();
			double x = plot.getDomainAxis().java2DToValue(p.getX(), plotArea, plot.getDomainAxisEdge());
			double y = plot.getRangeAxis().java2DToValue(p.getY(), plotArea, plot.getRangeAxisEdge());
			return new java.awt.geom.Point2D.Double(x,y);
		}

	}
	
	public static Point2D.Double screenToPlot(ChartPanel panel, XYPlot plot, double x, double y){
		Rectangle2D plotArea = panel.getScreenDataArea();
		double px = plot.getDomainAxis().java2DToValue(x, plotArea, plot.getDomainAxisEdge());
		double py = plot.getRangeAxis().java2DToValue(y, plotArea, plot.getRangeAxisEdge());
		return new Point2D.Double(px, py);
	}
	
	public static class SelectionEvent{
		public final double minX,minY,maxX,maxY;

		public SelectionEvent(double minX, double maxX, double minY, double maxY) {
			this.minX = minX;
			this.minY = minY;
			this.maxX = maxX;
			this.maxY = maxY;
		}
	}
	
	public static interface SelectionListener{
		public void notify(SelectionEvent e);
	}
	
	public static class SelectableChartPanel extends ChartPanel{
		boolean triggerSuper = true;
		List<SelectionListener> listeners = new ArrayList<SelectionListener>();
		int x1,y1,x2,y2;
		boolean dragging = true;
		public SelectableChartPanel(JFreeChart jfc){
			super(jfc);
		}
		
		public void setTriggerSuper(boolean triggerSuper){
			this.triggerSuper=triggerSuper;
		}
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			dragging = true;
			super.mouseDragged(arg0);
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {
			x1 = arg0.getX();
			y1 = arg0.getY();
			super.mousePressed(arg0);
		}
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			x2 = arg0.getX();
			y2 = arg0.getY();
			if(dragging && x1<x2 && y1<y2){
				java.awt.geom.Point2D.Double upperLeft = getChartCoord(new Point(x1, y1));
				java.awt.geom.Point2D.Double lowerRight =getChartCoord(new Point(x2, y2));
				SelectionEvent e = new SelectionEvent(upperLeft.getX(), lowerRight.getX(), lowerRight.getY(), upperLeft.getY());
				for(SelectionListener l : listeners){
					l.notify(e);
				}
			}
			dragging = false;
			if(triggerSuper)
				super.mouseReleased(arg0);
		}
		
		Point2D.Double getChartCoord(Point p){
			Rectangle2D plotArea = getScreenDataArea();
			XYPlot plot = getChart().getXYPlot();
			double x = plot.getDomainAxis().java2DToValue(p.getX(), plotArea, plot.getDomainAxisEdge());
			double y = plot.getRangeAxis().java2DToValue(p.getY(), plotArea, plot.getRangeAxisEdge());
			return new java.awt.geom.Point2D.Double(x,y);
		}
		
		public void addListener(SelectionListener l){
			listeners.add(l);
		}
	}
	
	static class XYSelectionReporter implements ChartMouseListener{

		ChartPanel panel;
		XYPlot plot;

		/**
		 * 
		 * @param panel the panel on which the listener will be reporting
		 */
		public XYSelectionReporter(ChartPanel panel) {
			this.panel = panel;
			plot = panel.getChart().getXYPlot();
		}

		public void chartMouseClicked(ChartMouseEvent e) {
			System.out.println(e.getSource());
			Point2D.Double p = getChartCoord(e.getTrigger().getPoint());
		}

		public void chartMouseMoved(ChartMouseEvent e) {

		}

		Point2D.Double getChartCoord(Point p){
			Rectangle2D plotArea = panel.getScreenDataArea();
			double x = plot.getDomainAxis().java2DToValue(p.getX(), plotArea, plot.getDomainAxisEdge());
			double y = plot.getRangeAxis().java2DToValue(p.getY(), plotArea, plot.getRangeAxisEdge());
			return new java.awt.geom.Point2D.Double(x,y);
		}

	}

	public static void setDecimalTickFormat(ValueAxis axis, double tick_size, int n_decimal_places){
		((NumberAxis) axis).setTickUnit(new NumberTickUnit(tick_size,new DecimalFormat("0."+StringUtils.repeat("0", n_decimal_places))));		
	}

	static class XYStrokeHoverListener implements ChartMouseListener{

		Integer selectedSeries = -1;

		Stroke originalStroke;

		double scaleFactor = 3;

		JFreeChart jfc;
		ChartPanel component;
		
		XYShapeAnnotation shape;

		public XYStrokeHoverListener(JFreeChart jfc, ChartPanel component) {
			this.jfc = jfc;
			this.component = component;
		}

		public void chartMouseClicked(ChartMouseEvent arg0) {

		}

		public void chartMouseMoved(ChartMouseEvent arg0) {
			synchronized(selectedSeries){
				
				if(arg0.getEntity() instanceof XYItemEntity){
					XYItemEntity xie = (XYItemEntity) arg0.getEntity();
					int seriesIndex = xie.getSeriesIndex();

					
					
					if(selectedSeries!=-1){
						jfc.getXYPlot().getRenderer().setSeriesStroke(selectedSeries, originalStroke);	
					}

					originalStroke = jfc.getXYPlot().getRenderer().getSeriesStroke(seriesIndex);
					if(originalStroke instanceof BasicStroke){
						jfc.getXYPlot().getRenderer().setSeriesStroke(seriesIndex, new BasicStroke((float) (scaleFactor*((BasicStroke)originalStroke).getLineWidth())));
					}else if(originalStroke==null){
						// do nothing
//						addShapeAnnotation(plot, shape);
						Rectangle2D bounds = xie.getArea().getBounds2D();
						double x1 = bounds.getX();
						double y1 = bounds.getY();
						double x2 = bounds.getX() + (bounds.getWidth());
						double y2 = bounds.getY() + (bounds.getHeight());
						Point2D.Double plot1 = screenToPlot(component, jfc.getXYPlot(), x1, y1);
						Point2D.Double plot2 = screenToPlot(component, jfc.getXYPlot(), x2, y2);
						double px = plot1.getX();
						double py = plot2.getY();
						double pw = plot2.getX()-px;
						double ph = plot1.getY()-py;
						if(shape!=null)
							jfc.getXYPlot().removeAnnotation(shape);
						shape = new XYShapeAnnotation(new Rectangle2D.Double(px,py,pw,ph));						
						jfc.getXYPlot().addAnnotation(shape);
					}
					else{
						System.out.println(originalStroke);
						System.err.println("can't scale, can't determine stroke width, not BasicStroke");
					}

					if(selectedSeries==-1 || selectedSeries.intValue() != seriesIndex){
						SwingUtilities.invokeLater(new Runnable(){
							public void run() {
								component.repaint();									
							}});
					}
					selectedSeries = seriesIndex;
				}
				else{
					if(selectedSeries!=-1){
						jfc.getXYPlot().getRenderer().setSeriesStroke(selectedSeries, originalStroke);
//						if(shape!=null)
//							jfc.getXYPlot().removeAnnotation(shape);
						SwingUtilities.invokeLater(new Runnable(){
							public void run() {
								component.repaint();									
							}});
					}
					selectedSeries = -1;
				}
			}
		}

	}

	public static void main(String[] args) {
		{
			MapList<String,Double[]> data = new MapList<String, Double[]>();
			data.put("a", new Double[]{0.,0.});
			data.put("a", new Double[]{1.,1.});
			data.put("a", new Double[]{2.,2.});
			data.put("a", new Double[]{3.,3.});
			data.put("a", new Double[]{4.,4.});
			data.put("a", new Double[]{5.,5.});

			data.put("b", new Double[]{0.1,-1.});
			data.put("b", new Double[]{1.1,1.});
			data.put("b", new Double[]{2.,2.});
			data.put("b", new Double[]{3.1,4.});
			data.put("b", new Double[]{4.1,5.});
			data.put("b", new Double[]{5.1,6.});
			final JFreeChart jfc = createLineChart(data.getMap(), "", "", "", false);

			final SelectableChartPanel panel = new SelectableChartPanel(jfc);

//			panel.setMouseZoomable(false, true);
			panel.addListener(new SelectionListener() {
				
				public void notify(SelectionEvent e) {
					System.out.println(Util.list(e.minX,e.maxX,e.minY,e.maxY));
					
				}
			});


			GraphicsUtils.show(GraphicsUtils.setPrefferedSize(panel, 300, 300));
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map<List<String>,Double[]> data = new HashMap<List<String>, Double[]>();
			data.put(Util.list("1","A"), new Double[]{0.,3.});
			data.put(Util.list("1","b"), new Double[]{1.,4.});
			data.put(Util.list("2","A"), new Double[]{10.,13.});
			data.put(Util.list("2","b"), new Double[]{11.,14.});
			Map<List<String>,Double[]> data2 = new HashMap<List<String>, Double[]>();
			data2.put(Util.list("1","A"), new Double[]{1.,2.});
			data2.put(Util.list("1","b"), new Double[]{2.,3.});
			data2.put(Util.list("2","A"), new Double[]{11.,12.});
			data2.put(Util.list("2","b"), new Double[]{12.,13.});
			Map<List<String>,Double[]> data3 = new HashMap<List<String>, Double[]>();
			data3.put(Util.list("1","A"), new Double[]{1.5,1.5});
			data3.put(Util.list("1","b"), new Double[]{2.5,2.5});
			data3.put(Util.list("2","A"), new Double[]{11.5,11.5});
			data3.put(Util.list("2","b"), new Double[]{12.5,12.5});
			showChart(create2DBoxAndWhiskerPlot(data2, data3, data, "d", "e", "f", true));
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			MapList<String,Double[]> data = new MapList<String, Double[]>();
			data.put("a", new Double[]{0.,0.});
			data.put("a", new Double[]{1.,1.});
			data.put("a", new Double[]{2.,2.});
			data.put("a", new Double[]{3.,3.});
			data.put("a", new Double[]{4.,4.});
			data.put("a", new Double[]{5.,5.});

			data.put("b", new Double[]{0.1,-1.});
			data.put("b", new Double[]{1.1,1.});
			data.put("b", new Double[]{2.,2.});
			data.put("b", new Double[]{3.1,4.});
			data.put("b", new Double[]{4.1,5.});
			data.put("b", new Double[]{5.1,6.});
			final JFreeChart jfc = createLineChart(data.getMap(), "", "", "", false);

			final ChartPanel panel = createChartPanel(jfc);

			XYStrokeHoverListener l = new XYStrokeHoverListener(jfc,panel);
			panel.addChartMouseListener(l);



			GraphicsUtils.show(GraphicsUtils.setPrefferedSize(panel, 300, 300));
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		{
			MapList<String,Double> data = new MapList<String, Double>();

			data.put("A", 0.1);
			data.put("A", 0.15);
			data.put("A", 0.2);
			data.put("A", .23);
			data.put("b", 0.12);
			data.put("b", 0.13);
			data.put("b", 0.14);
			data.put("b", 0.125);


			{
				JFreeChart jfc = 
						createDensityChart(data.getMap(), "", "", "", false, 300, .025, true,false);
				showChart(jfc);
			}
			JFreeChart jfc = 
					createDensityChart(data.getMap(), "", "", "", false, 100, .025, true, true);


			//			createStackedDensityChart(data.getMap(), "", "", "", false, 300, .025, true);

			showChart(jfc);
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			MapList<String,Double[]> data = new MapList<String, Double[]>();
			data.put("A", new Double[]{0.,3.});
			data.put("A", new Double[]{1.,2.});
			data.put("A", new Double[]{1.5,.5});
			data.put("A", new Double[]{2.,0.});
			data.put("A", new Double[]{2.5,0.});

			data.put("b", new Double[]{0.,0.});
			data.put("b", new Double[]{1.,0.});
			data.put("b", new Double[]{1.5,.5});
			data.put("b", new Double[]{2.,3.});
			data.put("b", new Double[]{2.5,1.5});
			showChart(createPathChart(data.getMap(), "d", "e", "f", false));
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			MapList<String,Double[]> data = new MapList<String, Double[]>();
			data.put("A", new Double[]{0.,3.});
			data.put("A", new Double[]{1.,2.});
			data.put("A", new Double[]{1.5,.5});
			data.put("A", new Double[]{2.,0.});
			data.put("A", new Double[]{2.5,0.});

			data.put("b", new Double[]{0.,0.});
			data.put("b", new Double[]{1.,0.});
			data.put("b", new Double[]{1.5,.5});
			data.put("b", new Double[]{2.,3.});
			data.put("b", new Double[]{2.5,1.5});
			showChart(createStackedAreaChart(data.getMap(), "d", "e", "f", false));
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			MapList<String,Double> data = new MapList<String, Double>();

			data.put("A", 0.1);
			data.put("A", 0.15);
			data.put("A", 0.2);
			data.put("A", .23);
			data.put("b", 0.12);
			data.put("b", 0.13);
			data.put("b", 0.14);
			data.put("b", 0.125);

			JFreeChart jfc = createDensityChart(data.getMap(), "", "", "", false, 300, .005, true, false);

			showChart(jfc);
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			MapList<String,Double[]> data = new MapList<String, Double[]>();
			data.put("A", new Double[]{0.,3.});
			data.put("A", new Double[]{1.,2.});
			data.put("A", new Double[]{1.5,.5});
			data.put("b", new Double[]{1.,4.});
			data.put("b", new Double[]{2.,3.});
			data.put("b", new Double[]{2.5,1.5});
			showChart(createAreaChart(data.getMap(), "d", "e", "f", false));
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			MapList<String,Double> data = new MapList<String, Double>();

			data.put("A", 0.1);
			data.put("A", 0.15);
			data.put("A", 0.2);
			data.put("A", 20000.);
			data.put("b", 0.12);
			data.put("b", 0.13);
			data.put("b", 0.14);
			data.put("b", 0.125);

			JFreeChart jfc = createCategoryViolinChart(data.getMap(), "", "", "", false, 100, .04, true);
			ChartUtils.setRangeAxisLog(jfc);
			showChart(jfc);
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map<String,Double[]> data = new HashMap<String, Double[]>();
			data.put("A", new Double[]{0.,3.});
			data.put("b", new Double[]{1.,4.});
			Map<String,Double[]> data2 = new HashMap<String, Double[]>();
			data2.put("A", new Double[]{1.,2.});
			data2.put("b", new Double[]{2.,3.});
			Map<String,Double[]> data3 = new HashMap<String, Double[]>();
			data3.put("A", new Double[]{1.5,1.5});
			data3.put("b", new Double[]{2.5,2.5});
			showChart(createBoxAndWhiskerPlot(data2, data3, data, "d", "e", "f", false));
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			MapList<String,Double[]> data = new MapList<String, Double[]>();
			data.put("a", new Double[]{0.,0.});
			data.put("a", new Double[]{1.,1.});
			data.put("a", new Double[]{2.,2.});
			data.put("a", new Double[]{3.,3.});
			data.put("a", new Double[]{4.,4.});
			data.put("a", new Double[]{5.,5.});

			data.put("b", new Double[]{0.1,-1.});
			data.put("b", new Double[]{1.1,1.});
			data.put("b", new Double[]{2.1,3.});
			data.put("b", new Double[]{3.1,4.});
			data.put("b", new Double[]{4.1,5.});
			data.put("b", new Double[]{5.1,6.});
			JFreeChart jfc = createLineChart(data.getMap(), "", "", "", false);
			ChartUtils.showChart(jfc);
			jfc.getXYPlot().setRenderer(new XYDifferenceRenderer());
		}
		{
			MapList<String[],Double> ml = new MapList<String[], Double>();
			for(int i=0; i<2; i++){
				for(int j=0; j<2; j++){
					NormalDistribution nd = new NormalDistribution(i*j + i, 1);
					List<Double> l = new ArrayList<Double>();
					for(int k=0; k<100; k++){
						l.add(nd.sample());
					}
					ml.putAll(new String[]{i+"",j+""}, l);
				}	
			}
			//			ml.putAll(new String[]{"a","c"}, Util.list(1.,2.));
			//			ml.putAll(new String[]{"a","d"}, Util.list(3.,4.));
			//			
			//			ml.putAll(new String[]{"b","c"}, Util.list(5.,6.));
			//			ml.putAll(new String[]{"b","d"}, Util.list(7.,8.));

			JFreeChart createScatterCategoryChart = createCategory2DScatterCategoryChart(ml.getMap(), "", "", "", true);
			//			setDecimalTickFormat(createScatterCategoryChart.getCategoryPlot().getRangeAxis(), .1, 3);
			//			createScatterCategoryChart.getCategoryPlot().getRangeAxis().setStandardTickUnits(xyz);
			//			((NumberAxis)createScatterCategoryChart.getCategoryPlot().getRangeAxis()).setNumberFormatOverride(new DecimalFormat("0.00"));
			showChart(createScatterCategoryChart);
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			MapList<String,Integer[]> data = new MapList<String, Integer[]>();
			data.put("A", new Integer[]{1,2});
			data.put("b", new Integer[]{2,3});
			JFreeChart jfc = createScatterChart(data.getMap(), "d", "e", "f", false);
			XYSeriesCollection d = (XYSeriesCollection) jfc.getXYPlot().getDataset();

			showChart(jfc);
			while(true){
				if(false)
					break;
				d.getSeries("A").add(1+Math.random(), 1+Math.random());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		{
			Map<String,Integer[]> data = new HashMap<String, Integer[]>();
			data.put("A", new Integer[]{1,2});
			data.put("b", new Integer[]{2,3});
			showChart(createIntervalCategoryChart(data, "d", "e", "f", false,PlotOrientation.HORIZONTAL));
		}
		{
			Map<String[],Integer> dataset = new HashMap();
			dataset.put(new String[]{"c","a"}, 1);
			dataset.put(new String[]{"d","a"}, 2);
			dataset.put(new String[]{"c","b"}, 2);
			dataset.put(new String[]{"d","b"}, 1);

			JFreeChart jfc = ChartUtils.create2DStackedCategoryChart(dataset, "", "", "", false);
			//			jfc.getCategoryPlot().setRenderer(new StackedBarRenderer());
			showChart(jfc);
		}
		{
			MapList<String,Double> data = new MapList<String, Double>();
			MapList<String,String> annotations = new MapList<String, String>();
			data.put("a",.1);
			data.put("a",.1);
			data.put("a",.1);
			data.put("a",.2);
			data.put("a",.3);
			data.put("a",.3);
			data.put("a",null);
			data.put("a",null);
			data.put("a",null);
			data.put("b",.11);
			data.put("b",.12);
			data.put("b",.13);
			data.put("b",.24);
			data.put("b",.35);
			data.put("b",.36);

			JFreeChart jfc = ChartUtils.createCumulativePlot(data.getMap(), "", "", "", false, false, false);
			ChartUtils.showChart(jfc);
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			MapList<String,Double[]> data = new MapList<String, Double[]>();
			MapList<String,String> annotations = new MapList<String, String>();
			data.put("a",new Double[]{1.,0.9});
			annotations.put("a","1");
			data.put("a",new Double[]{2.,2.1});
			annotations.put("a","2");
			data.put("a",new Double[]{3.,2.9});
			annotations.put("a","1");
			data.put("b",new Double[]{1.,1.});
			annotations.put("b","1");
			data.put("b",new Double[]{2.,2.});
			annotations.put("b","2");
			data.put("b",new Double[]{3.,3.});
			annotations.put("b","3");
			JFreeChart jfc = ChartUtils.createAnnotatedScatterChart(data.getMap(), annotations.getMap(), "", "", "", false);
			ChartUtils.showChart(jfc);
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map<List<String>,Component> data2 = new HashMap<List<String>, Component>();
			MapList<String,Double[]> data = new MapList<String, Double[]>();
			data.put("a",new Double[]{1.,0.9});
			data.put("a",new Double[]{2.,2.1});
			data.put("a",new Double[]{3.,2.9});
			data.put("b",new Double[]{1.,1.});
			data.put("b",new Double[]{2.,2.});
			data.put("b",new Double[]{3.,3.});
			JFreeChart jfc = ChartUtils.createScatterChart(data.getMap(), "", "", "", false);
			for(String s : data.keySet()){
				for(Double[] point : data.get(s))
					jfc.getXYPlot().addAnnotation(new XYTextAnnotation(((int)(Math.random()*10000))+"", point[0], point[1]));	
			}
			data2.put(Util.list("a","b"),new ChartPanel(jfc));
			GraphicsUtils.show(pairs(data2,Util.list("a","b")));
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
		//		testpoly();
		//		try {
		//			System.in.read();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		System.exit(0);
		{
			MapList<String,Double[]> data = new MapList<String, Double[]>();
			data.put("a",new Double[]{1.,0.9});
			data.put("a",new Double[]{2.,2.1});
			data.put("a",new Double[]{3.,2.9});
			data.put("b",new Double[]{1.,1.});
			data.put("b",new Double[]{2.,2.});
			data.put("b",new Double[]{3.,3.});
			scatterline(data.getMap(), "", "", "", true);
		}
		MapList<String,Integer> dataset = new MapList<String, Integer>(new Util.OrderedMap<String,List<Integer>>());
		MapList<List<String>,Color> datasetColor = new MapList<List<String>, Color>();
		//		dataset.put("a", 1);
		//		dataset.put("a", 3);
		//		dataset.put("b", 2);
		//		dataset.put("b", 5);
		for(int i=0; i<10; i++){
			double x = Math.random();
			double y = Math.random();
			double z = Math.random();
			//				System.out.println(x);
			dataset.put("a", (int) (x*100)+1);
			dataset.put("b", (int) (y*100)+1);
			dataset.put("c", (int) (z*100)+1);
			if(x<.25){
				datasetColor.put(Util.list("a","b"), Color.red);
				datasetColor.put(Util.list("b","a"), Color.red);
			}
			else{
				datasetColor.put(Util.list("a","b"), Color.black);
				datasetColor.put(Util.list("b","a"), Color.black);
			}
			if(y<.25){
				datasetColor.put(Util.list("b","c"), Color.red);
				datasetColor.put(Util.list("c","b"), Color.red);
			}
			else{
				datasetColor.put(Util.list("b","c"), Color.black);
				datasetColor.put(Util.list("c","b"), Color.black);
			}
			if(z<.25){
				datasetColor.put(Util.list("a","c"), Color.red);
				datasetColor.put(Util.list("c","a"), Color.red);
			}
			else{
				datasetColor.put(Util.list("a","c"), Color.black);
				datasetColor.put(Util.list("c","a"), Color.black);
			}
		}
		GraphicsUtils.show(pairs(dataset.getMap(),datasetColor.getMap(),300,300));
		try {
			GraphicsUtils.savePDF(pairs(dataset.getMap(),datasetColor.getMap(),300,300), "/home/sol/Desktop/test.pdf", 300, 300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		showChart(createBoxWhisker(Util.map(Util.list("a","b"), Util.enlist(Util.list(0.,1.5,1.75,2.1,2.2,3.05,1.,1.,1.,2.,2.,3.,3.,3.1,6.,12.,24.),Util.list(0.,1.5,1.75,2.1,2.2,3.05,1.,1.,1.,2.,2.,3.,3.,3.1,6.,12.,24.))), "", "", "", false),300,800);
		//		showChart(createCumulativePlot(Util.map(Util.list("a"), Util.enlist(Util.list(0.,1.5,1.75,2.1,2.2,3.05,1.,1.,1.,2.,2.,3.,3.,3.1,6.,12.,24.))), "t", "x", "y", false,true));
		//		showChart(createHistogram(Util.map(Util.list("a"), Util.enlist(Util.list(1.,1.,1.,2.,2.,3.))), Util.map(Util.list("a"),Util.list(Util.hsba(.5,1,1,.5))), 3, "t", "x", "y", false));
		//		Map<String, List<Double[]>> dataset = Util.map(Util.list("a","b"), Util.enlist(Util.enlist(new Double[]{0.,1.},new Double[]{0.05,1.05}),Util.enlist(new Double[]{3.,4.},new Double[]{4.,5.})));
		//		Map<String, List<Color>> colors = Util.map(Util.enlist("a","b"), Util.enlist(Util.list(Util.hsba(0, 1, 1, .15),Util.hsba(0, 1, 1, .15)),Util.list(Color.blue,Color.pink)));
		//		showChart(createScatterChart(dataset, colors, "", "", "", false));
		//		Map<String[],Double> data = Util.map(Util.enlist(new String[]{"a","c"},new String[]{"a","d"},new String[]{"b","c"},new String[]{"b","d"}), Util.list(1.,2.,3.,4.));
		//		showChart(create2DCategoryChart(data, "a", "b", "c", false));
	}

}