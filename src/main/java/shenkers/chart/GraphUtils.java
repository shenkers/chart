package shenkers.chart;

import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.collections.Transformer;

import com.google.common.base.Function;

import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.EdgeIndexFunction;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.PluggableRenderContext;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.BasicRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.LensTransformer;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;

public class GraphUtils {

	public static void main(String[] args) {
		Graph g = new DirectedSparseGraph();
		g.addVertex("a");
		g.addVertex("b");
		g.addVertex("c");
		g.addEdge("d", "a", "b");
		g.addEdge("e", "b", "c");

		DAGLayout layout = new DAGLayout(g);

		VisualizationViewer vv = new VisualizationViewer(layout,new Dimension(300, 300));

		vv.getRenderContext().setVertexShapeTransformer(
				new Function() {
					public Object apply(Object arg0) {
						return new Rectangle2D.Double(0, 0, 5, 10);
					}
				}
				);

		/*		
lass br<V, E> extends BasicRenderer<V, E>{

			@Override
			public void renderEdge(RenderContext<V, E> rc, Layout<V, E> layout, E e) {
				paintEdge(rc, layout, e);
			}

			public void paintEdge(RenderContext<V,E> rc, Layout<V, E> layout, E e) {
		        GraphicsDecorator g2d = rc.getGraphicsContext();
		        Graph<V,E> graph = layout.getGraph();
		        if (!rc.getEdgeIncludePredicate().apply(Context.<Graph<V,E>,E>getInstance(graph,e)))
		            return;

		        // don't draw edge if either incident vertex is not drawn
		        Pair<V> endpoints = graph.getEndpoints(e);
		        V v1 = endpoints.getFirst();
		        V v2 = endpoints.getSecond();
		        if (!rc.getVertexIncludePredicate().apply(Context.<Graph<V,E>,V>getInstance(graph,v1)) || 
		            !rc.getVertexIncludePredicate().apply(Context.<Graph<V,E>,V>getInstance(graph,v2)))
		            return;

		        Stroke new_stroke = rc.getEdgeStrokeTransformer().apply(e);
		        Stroke old_stroke = g2d.getStroke();
		        if (new_stroke != null)
		            g2d.setStroke(new_stroke);

		        drawSimpleEdge(rc, layout, e);

		        // restore paint and stroke
		        if (new_stroke != null)
		            g2d.setStroke(old_stroke);

		    }

		    /**
		 * Draws the edge <code>e</code>, whose endpoints are at <code>(x1,y1)</code>
		 * and <code>(x2,y2)</code>, on the graphics context <code>g</code>.
		 * The <code>Shape</code> provided by the <code>EdgeShapeFunction</code> instance
		 * is scaled in the x-direction so that its width is equal to the distance between
		 * <code>(x1,y1)</code> and <code>(x2,y2)</code>.
		 
		@SuppressWarnings("unchecked")
		protected void drawSimpleEdge(RenderContext<V,E> rc, Layout<V,E> layout, E e) {

			GraphicsDecorator g = rc.getGraphicsContext();
			Graph<V,E> graph = layout.getGraph();
			Pair<V> endpoints = graph.getEndpoints(e);
			V v1 = endpoints.getFirst();
			V v2 = endpoints.getSecond();

			Point2D p1 = layout.apply(v1);
			Point2D p2 = layout.apply(v2);
			p1 = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, p1);
			p2 = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, p2);
			float x1 = (float) p1.getX();
			float y1 = (float) p1.getY();
			float x2 = (float) p2.getX();
			float y2 = (float) p2.getY();

			boolean isLoop = v1.equals(v2);
			Shape s2 = rc.getVertexShapeTransformer().apply(v2);
			Shape edgeShape = rc.getEdgeShapeTransformer().apply(Context.<Graph<V,E>,E>getInstance(graph, e).graph.);

			boolean edgeHit = true;
			boolean arrowHit = true;
			Rectangle deviceRectangle = null;
			JComponent vv = rc.getScreenDevice();
			if(vv != null) {
				Dimension d = vv.getSize();
				deviceRectangle = new Rectangle(0,0,d.width,d.height);
			}

			AffineTransform xform = AffineTransform.getTranslateInstance(x1, y1);

			if(isLoop) {
				// this is a self-loop. scale it is larger than the vertex
				// it decorates and translate it so that its nadir is
				// at the center of the vertex.
				Rectangle2D s2Bounds = s2.getBounds2D();
				xform.scale(s2Bounds.getWidth(),s2Bounds.getHeight());
				xform.translate(0, -edgeShape.getBounds2D().getWidth()/2);
			} else if(rc.getEdgeShapeTransformer() instanceof EdgeShape.Orthogonal) {
				float dx = x2-x1;
				float dy = y2-y1;
				int index = 0;
				if(rc.getEdgeShapeTransformer() instanceof IndexedRendering) {
					EdgeIndexFunction<V,E> peif = 
							((IndexedRendering<V,E>)rc.getEdgeShapeTransformer()).getEdgeIndexFunction();
					index = peif.getIndex(graph, e);
					index *= 20;
				}
				GeneralPath gp = new GeneralPath();
				gp.moveTo(0,0);// the xform will do the translation to x1,y1
				if(x1 > x2) {
					if(y1 > y2) {
						gp.lineTo(0, index);
						gp.lineTo(dx-index, index);
						gp.lineTo(dx-index, dy);
						gp.lineTo(dx, dy);
					} else {
						gp.lineTo(0, -index);
						gp.lineTo(dx-index, -index);
						gp.lineTo(dx-index, dy);
						gp.lineTo(dx, dy);
					}

				} else {
					if(y1 > y2) {
						gp.lineTo(0, index);
						gp.lineTo(dx+index, index);
						gp.lineTo(dx+index, dy);
						gp.lineTo(dx, dy);

					} else {
						gp.lineTo(0, -index);
						gp.lineTo(dx+index, -index);
						gp.lineTo(dx+index, dy);
						gp.lineTo(dx, dy);

					}

				}

				edgeShape = gp;

			} else {
				// this is a normal edge. Rotate it to the angle between
				// vertex endpoints, then scale it to the distance between
				// the vertices
				float dx = x2-x1;
				float dy = y2-y1;
				float thetaRadians = (float) Math.atan2(dy, dx);
				xform.rotate(thetaRadians);
				float dist = (float) Math.sqrt(dx*dx + dy*dy);
				xform.scale(dist, 1.0);
			}

			edgeShape = xform.createTransformedShape(edgeShape);

			MutableTransformer vt = rc.getMultiLayerTransformer().getTransformer(Layer.VIEW);
			if(vt instanceof LensTransformer) {
				vt = ((LensTransformer)vt).getDelegate();
			}
			edgeHit = vt.transform(edgeShape).intersects(deviceRectangle);

			if(edgeHit == true) {

				Paint oldPaint = g.getPaint();

				// get Paints for filling and drawing
				// (filling is done first so that drawing and label use same Paint)
				Paint fill_paint = rc.getEdgeFillPaintTransformer().apply(e); 
				if (fill_paint != null)
				{
					g.setPaint(fill_paint);
					g.fill(edgeShape);
				}
				Paint draw_paint = rc.getEdgeDrawPaintTransformer().apply(e);
				if (draw_paint != null)
				{
					g.setPaint(draw_paint);
					g.draw(edgeShape);

					AffineTransform xform2 = AffineTransform.getTranslateInstance(-5, -5);
					Shape edgeShape2 = xform2.createTransformedShape(edgeShape);
					g.draw(edgeShape2);

				}

				float scalex = (float)g.getTransform().getScaleX();
				float scaley = (float)g.getTransform().getScaleY();
				// see if arrows are too small to bother drawing
				if(scalex < .3 || scaley < .3) return;

				//		            if (rc.getEdgeArrowPredicate().apply(Context.<Graph<V,E>,E>getInstance(graph, e))) {
				//		            	
				//		                Stroke new_stroke = rc.getEdgeArrowStrokeTransformer().apply(e);
				//		                Stroke old_stroke = g.getStroke();
				//		                if (new_stroke != null)
				//		                    g.setStroke(new_stroke);
				//
				//		                
				//		                Shape destVertexShape = 
				//		                    rc.getVertexShapeTransformer().apply(graph.getEndpoints(e).getSecond());
				//
				//		                AffineTransform xf = AffineTransform.getTranslateInstance(x2, y2);
				//		                destVertexShape = xf.createTransformedShape(destVertexShape);
				//		                
				//		                arrowHit = rc.getMultiLayerTransformer().getTransformer(Layer.VIEW).apply(destVertexShape).intersects(deviceRectangle);
				//		                if(arrowHit) {
				//		                    
				//		                    AffineTransform at = 
				//		                        edgeArrowRenderingSupport.getArrowTransform(rc, edgeShape, destVertexShape);
				//		                    if(at == null) return;
				//		                    Shape arrow = rc.getEdgeArrowTransformer().apply(Context.<Graph<V,E>,E>getInstance(graph, e));
				//		                    arrow = at.createTransformedShape(arrow);
				//		                    g.setPaint(rc.getArrowFillPaintTransformer().apply(e));
				//		                    g.fill(arrow);
				//		                    g.setPaint(rc.getArrowDrawPaintTransformer().apply(e));
				//		                    g.draw(arrow);
				//		                }
				//		                if (graph.getEdgeType(e) == EdgeType.UNDIRECTED) {
				//		                    Shape vertexShape = 
				//		                        rc.getVertexShapeTransformer().apply(graph.getEndpoints(e).getFirst());
				//		                    xf = AffineTransform.getTranslateInstance(x1, y1);
				//		                    vertexShape = xf.createTransformedShape(vertexShape);
				//		                    
				//		                    arrowHit = rc.getMultiLayerTransformer().getTransformer(Layer.VIEW).apply(vertexShape).intersects(deviceRectangle);
				//		                    
				//		                    if(arrowHit) {
				//		                        AffineTransform at = edgeArrowRenderingSupport.getReverseArrowTransform(rc, edgeShape, vertexShape, !isLoop);
				//		                        if(at == null) return;
				//		                        Shape arrow = rc.getEdgeArrowTransformer().apply(Context.<Graph<V,E>,E>getInstance(graph, e));
				//		                        arrow = at.createTransformedShape(arrow);
				//		                        g.setPaint(rc.getArrowFillPaintTransformer().apply(e));
				//		                        g.fill(arrow);
				//		                        g.setPaint(rc.getArrowDrawPaintTransformer().apply(e));
				//		                        g.draw(arrow);
				//		                    }
				//		                }
				//		                // restore paint and stroke
				//		                if (new_stroke != null)
				//		                    g.setStroke(old_stroke);
				//
				//		            }

				// restore old paint
				g.setPaint(oldPaint);
			}
		}
	}*/

	BasicRenderer br = new BasicRenderer();
	//		vv.getRenderContext().setEdgeShapeTransformer(new Function<F, T>() {
	//
	//			public T apply(F arg0) {
	//				// TODO Auto-generated method stub
	//		new EdgeShape.Line()
	//				return null;
	//			}
	//		});
	vv.setRenderer(br);

	GraphZoomScrollPane pane = new GraphZoomScrollPane(vv);

	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	frame.getContentPane().add(vv);
	frame.pack();
	frame.setVisible(true);
}
}
