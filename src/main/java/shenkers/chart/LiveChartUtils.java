package shenkers.chart;

import java.awt.Stroke;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.chart.renderer.xy.XYErrorRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeriesCollection;

import shenkers.data.Stats.MomentTracker;
import shenkers.u.Util;
import shenkers.u.Util.Instantiator;
import shenkers.u.Util.MapFactory;
import shenkers.u.Util.MapList;
import shenkers.u.Util.MapSummer;


public class LiveChartUtils {

	public static abstract class LiveChart<S extends Plot, T> extends SwingWorker<Void,T>{
		JFreeChart jfc;
		S plot;
		long t;
		int f;

		public LiveChart(JFreeChart jfc, S p, int update_frequency){
			this.jfc=jfc;
			this.plot=p;
			this.f=update_frequency;
			t=System.currentTimeMillis();
		}

		protected Void doInBackground() throws Exception {
			while(true){
				while (System.currentTimeMillis()-t < f) {
					Thread.yield();
				}
				t=System.currentTimeMillis();
				T xsr = getData();
				publish(xsr);
				if(Thread.interrupted())
					break;
			}
			System.out.println("broke");

			return null;
		}

		protected abstract T getData();

	}

	public static class LiveXYChart extends LiveChart<XYPlot,XYSeriesCollection>{
		MapList<String,Double[]> dat;

		public LiveXYChart(JFreeChart jfc, XYPlot plot, int update_frequency) {
			super(jfc,plot,update_frequency);
			dat = new MapList<String, Double[]>();
		}		

		protected void process(List<XYSeriesCollection> chunks) {
			XYSeriesCollection xsr = chunks.get(chunks.size()-1);
			plot.setDataset(xsr);
			jfc.plotChanged(new PlotChangeEvent(plot));
		}

		protected synchronized XYSeriesCollection getData(){
			return ChartUtils.createXYDataset(dat.getMap());
		}

		public synchronized void addData(String label, double x, double y){
			dat.put(label, new Double[]{x,y});
		}

	}

	public static class LiveCategoryChart extends LiveChart<CategoryPlot,CategoryDataset>{
		Map<String,Double> dat;

		public LiveCategoryChart(JFreeChart jfc, CategoryPlot plot, int update_frequency) {
			super(jfc,plot,update_frequency);
			dat = new HashMap<String, Double>();
		}		
		
		public LiveCategoryChart(JFreeChart jfc, CategoryPlot plot, int update_frequency, Comparator<? super String> keyComparator) {
			super(jfc,plot,update_frequency);
			dat = new TreeMap<String, Double>(keyComparator);
		}		

		protected void process(List<CategoryDataset> chunks) {
			CategoryDataset xsr = chunks.get(chunks.size()-1);
			plot.setDataset(xsr);
			jfc.plotChanged(new PlotChangeEvent(plot));
		}

		protected synchronized CategoryDataset getData(){
			return ChartUtils.createCategoryDataset(dat);
		}

		public synchronized void addData(String label, double x){
			dat.put(label, x);
		}
	}

	public static class LiveCategoryMomentChart extends LiveChart<CategoryPlot,CategoryDataset>{
		MapFactory<String,MomentTracker> dat;
		StatisticalBarRenderer r;

		public LiveCategoryMomentChart(JFreeChart jfc, CategoryPlot plot, int update_frequency) throws SecurityException, NoSuchMethodException {
			super(jfc,plot,update_frequency);
			dat = new MapFactory(new Util.DefaultInstantiator(MomentTracker.class));

			r = new StatisticalBarRenderer();
			r.setShadowVisible(false);
			r.setBarPainter(new StandardBarPainter());

			r.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		}		

		protected void process(List<CategoryDataset> chunks) {
			CategoryDataset xsr = chunks.get(chunks.size()-1);
			plot.setDataset(xsr);
			for(int i=0; i<xsr.getRowCount(); i++){
				plot.setRenderer(i, r);
			}
			jfc.plotChanged(new PlotChangeEvent(plot));
		}

		protected synchronized CategoryDataset getData(){
			return ChartUtils.createStatisticalMomentCategoryDataset(dat.getMap());
		}

		public synchronized void addData(String label, double x){
			dat.get(label).add(x);
		}
	}
	
	public static class LiveOrderedCategoryMomentChart extends LiveCategoryMomentChart{

		List<String> order;
		
		public LiveOrderedCategoryMomentChart(JFreeChart jfc, CategoryPlot plot, int update_frequency) throws SecurityException, NoSuchMethodException {
			super(jfc, plot, update_frequency);
		}
		
		public void setOrder(List<String> order){
			this.order=order;
		}
		
		protected synchronized CategoryDataset getData(){
			Map<String, MomentTracker> data = new Util.OrderedMap<String, MomentTracker>();
			for(String key : order)
				data.put(key, dat.get(key));
			return ChartUtils.createStatisticalMomentCategoryDataset(data);
		}
		
	}

	public static class Live2DCategoryMomentChart extends LiveChart<CategoryPlot,CategoryDataset>{
		MapFactory<List<Comparable>,MomentTracker> dat;
		StatisticalBarRenderer r;
		Comparator<List<Comparable>> seriesComparator;

		public Live2DCategoryMomentChart(JFreeChart jfc, CategoryPlot plot, int update_frequency) throws SecurityException, NoSuchMethodException {
			this(jfc,plot,update_frequency, new Comparator<List<Comparable>>(){
				public int compare(List<Comparable> arg0, List<Comparable> arg1) {
					return arg0.get(1).compareTo(arg1.get(1));
				}
			});	
		}

		public Live2DCategoryMomentChart(JFreeChart jfc, CategoryPlot plot, int update_frequency,  Comparator<List<Comparable>> seriesComparator) throws SecurityException, NoSuchMethodException {
			super(jfc,plot,update_frequency);
			dat = new MapFactory(new Util.DefaultInstantiator(MomentTracker.class));

			r = new StatisticalBarRenderer();
			r.setShadowVisible(false);
			r.setBarPainter(new StandardBarPainter());
			r.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
			this.seriesComparator=seriesComparator;
		}		

		protected void process(List<CategoryDataset> chunks) {
			CategoryDataset xsr = chunks.get(chunks.size()-1);
			plot.setDataset(xsr);
			for(int i=0; i<xsr.getRowCount(); i++){
				plot.setRenderer(i, r);
			}
			jfc.plotChanged(new PlotChangeEvent(plot));
		}

		protected synchronized CategoryDataset getData(){
			Map<List<Comparable>,MomentTracker> data = new Util.OrderedMap<List<Comparable>, MomentTracker>();
			List<List<Comparable>> L = new ArrayList<List<Comparable>>(dat.getMap().size());
			for(List<Comparable> l : dat.getMap().keySet()){
				L.add(l);
			}
			Collections.sort(L,seriesComparator);

			for(List<Comparable> l : L){
				data.put(l, dat.get(l));
			}
		
			return ChartUtils.create2DStatisticalMomentCategoryDataset(data);
		}

		public synchronized void addData(Comparable label1, Comparable label2, double x){
			dat.get(Util.list(label1,label2)).add(x);
		}

	}

	public static class Live2DCategorySumChart extends LiveChart<CategoryPlot,CategoryDataset>{
		MapSummer<List<String>> dat;

		public Live2DCategorySumChart(JFreeChart jfc, CategoryPlot plot, int update_frequency) {
			super(jfc,plot,update_frequency);
			dat = new MapSummer<List<String>>();	
		}

		protected void process(List<CategoryDataset> chunks) {
			CategoryDataset xsr = chunks.get(chunks.size()-1);
			plot.setDataset(xsr);
			jfc.plotChanged(new PlotChangeEvent(plot));
		}

		protected synchronized CategoryDataset getData(){
			Map<String[],Double> data = new HashMap<String[], Double>();
			List<List<String>> L = new ArrayList<List<String>>(dat.getMap().size());
			for(List<String> l : dat.keySet()){
				L.add(l);
			}

			for(List<String> l : L){
				data.put(l.toArray(new String[0]), dat.get(l));
			}

			return ChartUtils.create2DCategoryDataset(data);
		}

		public synchronized void addData(String label1, String label2, double x){
			dat.add(Util.list(label1,label2),x);
		}

	}
	
	public static class Live2DOrderedCategorySumChart extends Live2DCategorySumChart{

		List<List<String>> order;
		
		public Live2DOrderedCategorySumChart(JFreeChart jfc, CategoryPlot plot, int update_frequency) throws SecurityException,
				NoSuchMethodException {
			super(jfc, plot, update_frequency);
			// TODO Auto-generated constructor stub
		}
		
		public void setOrder(List<List<String>> order){
			this.order=order;
		}
		
		protected synchronized CategoryDataset getData(){
			Map<String[],Double> data = new Util.OrderedMap<String[], Double>();
				for(List<String> key : order)
				data.put(key.toArray(new String[0]), dat.get(key));
			return ChartUtils.create2DCategoryDataset(data);
		}
	}

	public static class Live2DCategoryChart extends LiveChart<CategoryPlot,CategoryDataset>{
		Map<List<Comparable>,Double> dat;
		Comparator<List<Comparable>> seriesComparator;

		public Live2DCategoryChart(JFreeChart jfc, CategoryPlot plot, int update_frequency) throws SecurityException, NoSuchMethodException {
			this(jfc, plot, update_frequency, new Comparator<List<Comparable>>(){
				public int compare(List<Comparable> arg0, List<Comparable> arg1) {
					return arg0.get(1).compareTo(arg1.get(1));
				}
			});

		}

		public Live2DCategoryChart(JFreeChart jfc, CategoryPlot plot, int update_frequency, Comparator<List<Comparable>> seriesComparator) throws SecurityException, NoSuchMethodException {
			super(jfc,plot,update_frequency);
			dat = new HashMap<List<Comparable>, Double>();	
			this.seriesComparator = seriesComparator;
		}

		protected void process(List<CategoryDataset> chunks) {
			CategoryDataset xsr = chunks.get(chunks.size()-1);
			plot.setDataset(xsr);
			jfc.plotChanged(new PlotChangeEvent(plot));
		}

		protected synchronized CategoryDataset getData(){
			Map<Comparable[],Double> data = new Util.OrderedMap<Comparable[], Double>();
			List<List<Comparable>> L = new ArrayList<List<Comparable>>(dat.size());
			for(List<Comparable> l : dat.keySet()){
				L.add(l);
			}
			Collections.sort(L,seriesComparator);

			for(List<Comparable> l : L){
				data.put(l.toArray(new Comparable[0]), dat.get(l));
			}

			return ChartUtils.create2DCategoryDataset(data);
		}

		public synchronized void addData(Comparable label1, Comparable label2, double x){
			dat.put(Util.list(label1,label2),x);
		}

	}

	public static class LiveXYSumChart extends LiveChart<XYPlot,XYSeriesCollection>{
		MapFactory<String,double[]> dat;
		int w;

		class ArrayInstantiator implements Instantiator<double[]>{
			int l;

			public ArrayInstantiator(int l) {
				this.l=l;
			}

			public double[] instantiate(Object... objects) {
				return new double [l];
			}
		}

		public LiveXYSumChart(JFreeChart jfc, XYPlot plot, int update_frequency, int l) {
			super(jfc,plot,update_frequency);
			dat = new MapFactory<String, double[]>(new ArrayInstantiator(l));
			w=0;
		}		

		public LiveXYSumChart(JFreeChart jfc, XYPlot plot, int update_frequency, int l, int w) {
			super(jfc,plot,update_frequency);
			dat = new MapFactory<String, double[]>(new ArrayInstantiator(l));
			this.w=w;
		}		

		protected void process(List<XYSeriesCollection> chunks) {
			XYSeriesCollection xsr = chunks.get(chunks.size()-1);
			plot.setDataset(xsr);
			jfc.plotChanged(new PlotChangeEvent(plot));
		}

		protected synchronized XYSeriesCollection getData(){
			MapList<String,Double[]> d = new MapList<String, Double[]>();

			for(String s : dat.getMap().keySet()){
				double[] dob = dat.get(s);
				for(int i=0; i<dob.length; i++)
					d.put(s, new Double[]{i-w+0.,dob[i]});
			}

			return ChartUtils.createXYDataset(d.getMap());
		}

		public synchronized void addData(String label, int x, double y){
			dat.get(label)[x] += y;
		}

	}

	public static class LiveXYFrequencyChart extends LiveChart<XYPlot,XYSeriesCollection>{
		MapFactory<String,double[]> dat;
		MapSummer<String> n;
		int w;

		class ArrayInstantiator implements Instantiator<double[]>{
			int l;

			public ArrayInstantiator(int l) {
				this.l=l;
			}

			public double[] instantiate(Object... objects) {
				return new double [l];
			}
		}

		public LiveXYFrequencyChart(JFreeChart jfc, XYPlot plot, int update_frequency, int l) {
			super(jfc,plot,update_frequency);
			dat = new MapFactory<String, double[]>(new ArrayInstantiator(l));
			n = new MapSummer<String>();
		}
		
		public LiveXYFrequencyChart(JFreeChart jfc, XYPlot plot, int update_frequency, int l, int w) {
			super(jfc,plot,update_frequency);
			dat = new MapFactory<String, double[]>(new ArrayInstantiator(l));
			n = new MapSummer<String>();
			this.w=w;
		}	

		protected void process(List<XYSeriesCollection> chunks) {
			XYSeriesCollection xsr = chunks.get(chunks.size()-1);
			plot.setDataset(xsr);
			jfc.plotChanged(new PlotChangeEvent(plot));
		}

		protected synchronized XYSeriesCollection getData(){
			MapList<String,Double[]> d = new MapList<String, Double[]>();

			for(String s : dat.getMap().keySet()){
				double N = n.get(s);
				double[] dob = dat.get(s);
				for(int i=0; i<dob.length; i++)
					d.put(s, new Double[]{i+0.-w,dob[i]/N});
			}

			return ChartUtils.createXYDataset(d.getMap());
		}

		public synchronized void addData(String label, int x, double y){
			dat.get(label)[x] += y;
		}

		public synchronized void increment(String label, double amount){
			n.add(label, amount);
		}
		
		public void print()	{
			for(String key : n.keySet()){
				System.out.printf("%s %f\n", key,n.get(key));
			}
		}
	}

	public static class LiveXYMomentChart extends LiveChart<XYPlot,IntervalXYDataset>{
		MapFactory<String,MomentTracker[]> dat;
		XYErrorRenderer r;
		int w;

		class MomentTrackerInstantiator implements Instantiator<MomentTracker[]>{
			int l;

			public MomentTrackerInstantiator(int l) {
				this.l=l;
			}

			public MomentTracker[] instantiate(Object... objects) {
				MomentTracker[] m = new MomentTracker[l];
				for(int i=0; i<l; i++){
					m[i] = new MomentTracker();
				}
				return m;
			}
		}

		public LiveXYMomentChart(JFreeChart jfc, XYPlot plot, int update_frequency, int l) {
			super(jfc,plot,update_frequency);
			dat = new MapFactory<String, MomentTracker[]>(new MomentTrackerInstantiator(l));
			r = new XYErrorRenderer();
			w=0;
			r.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
		}	

		public LiveXYMomentChart(JFreeChart jfc, XYPlot plot, int update_frequency, int l, int w) {
			super(jfc,plot,update_frequency);
			this.w=w;
			dat = new MapFactory<String, MomentTracker[]>(new MomentTrackerInstantiator(l));
			r = new XYErrorRenderer();
			r.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
		}	

		protected void process(List<IntervalXYDataset> chunks) {
			IntervalXYDataset xsr = chunks.get(chunks.size()-1);
			plot.setRenderer(r);
			for(int i=0; i<xsr.getSeriesCount(); i++){
				r.setSeriesLinesVisible(i, true);
			}
			plot.setDataset(xsr);
			jfc.plotChanged(new PlotChangeEvent(plot));
		}

		protected synchronized IntervalXYDataset getData(){
			MapList<String,Double[]> d = new MapList<String, Double[]>();

			for(String s : dat.getMap().keySet()){
				MomentTracker[] dob = dat.get(s);
				for(int i=0; i<dob.length; i++)
					d.put(s, new Double[]{i-w+0.,dob[i].mean,dob[i].variance,dob[i].variance});
			}

			return ChartUtils.createIntervalXYDataset(d.getMap());
		}

		public synchronized void addData(String label, int x, double y){
			dat.get(label)[x].add(y);
		}
		
		public void setDrawError(boolean b){
			r.setDrawYError(false);
		}
		
		public void setBaseShapesVisible(boolean flag){
			r.setBaseShapesVisible(flag);
		}
		
		public void setBaseStroke(Stroke stroke){
			r.setBaseStroke(stroke);
		}

	}

	public static void main(String[] args) throws SecurityException, NoSuchMethodException {

		class MsgSender1 implements Runnable{
			Live2DCategoryMomentChart aw;
			String n,n2;
			int time;
			long t;

			public MsgSender1(String n,String n2, Live2DCategoryMomentChart aw, int time) {
				this.n=n;
				this.n2=n2;
				this.time=time;
				this.aw = aw;
			}

			public void run() {
				while(true){
					while(System.currentTimeMillis()-t<time)
						Thread.yield();
					double x=Math.random();

					aw.addData(n,n2, x);
					t=System.currentTimeMillis();
				}
			}

		}

		JFreeChart jfcc = ChartUtils.createCategoryChart(new HashMap(), "", "", "", false);
		ChartUtils.showChart(jfcc);
		CategoryPlot cp = jfcc.getCategoryPlot();
		Live2DCategoryMomentChart workerc = new Live2DCategoryMomentChart(jfcc,cp,100);
		workerc.execute();

		cp.getRangeAxis().setRange(0,1);

		System.out.println("Starting A");
		new Thread(new MsgSender1("A","1", workerc,1)).start();
		//		new Thread(new MsgSender1("A","2", workerc,10)).start();
		new Thread(new MsgSender1("B","1", workerc,10)).start();
		new Thread(new MsgSender1("C","1", workerc,100)).start();
		new Thread(new MsgSender1("D","1", workerc,1000)).start();
		new Thread(new MsgSender1("E","1", workerc,10000)).start();
		//		new Thread(new MsgSender1("B","2", workerc,10)).start();
		//		new Thread(new MsgSender1("C", workerc,100)).start();
		//		new Thread(new MsgSender1("D", workerc,100)).start();
		//		new Thread(new MsgSender1("E", workerc,100)).start();
		//		new Thread(new MsgSender1("F", workerc,100)).start();
		//		new Thread(new MsgSender1("G", workerc,100)).start();
		//		new Thread(new MsgSender1("H", workerc,100)).start();
		//		new Thread(new MsgSender1("I", workerc,100)).start();
		//		new Thread(new MsgSender1("J", workerc,100)).start();
		//		new Thread(new MsgSender1("K", workerc,100)).start();

		if(false){

			class MsgSender implements Runnable{
				LiveXYChart aw;
				String n;
				int time;
				long t;

				public MsgSender(String n, LiveXYChart aw, int time) {
					this.n=n;
					this.time=time;
					this.aw = aw;
				}

				public void run() {
					while(true){
						while(System.currentTimeMillis()-t<time)
							Thread.yield();
						double x=Math.random();
						double y=Math.random();
						aw.addData(n, x, y);
						t=System.currentTimeMillis();
					}
				}

			}

			class MsgSender2 implements Runnable{
				LiveXYMomentChart aw;
				String n;
				int time;
				long t;

				public MsgSender2(String n, LiveXYMomentChart aw, int time) {
					this.n=n;
					this.time=time;
					this.aw = aw;
				}

				public void run() {
					while(true){
						while(System.currentTimeMillis()-t<time)
							Thread.yield();
						int x=(int)(Math.random()*101);
						double y=Math.random();
						aw.addData(n, x, y);
						t=System.currentTimeMillis();
					}
				}

			}

			JFreeChart jfc = ChartUtils.createLineChart(new HashMap<String, List<Double[]>>(), "t", "x", "y", true);
			Window w = (Window) ChartUtils.showChart(jfc).getParent().getParent().getParent();
			final LiveXYChart worker = new LiveXYChart(jfc,jfc.getXYPlot(), 100);
			worker.execute();


			w.addWindowListener(new WindowListener() {

				public void windowOpened(WindowEvent arg0) {
					// TODO Auto-generated method stub

				}

				public void windowIconified(WindowEvent arg0) {
					// TODO Auto-generated method stub

				}

				public void windowDeiconified(WindowEvent arg0) {
					// TODO Auto-generated method stub

				}

				public void windowDeactivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("here2");
				}

				public void windowClosing(WindowEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("here1");
					worker.cancel(true);
				}

				public void windowClosed(WindowEvent arg0) {
					worker.cancel(true);

				}

				public void windowActivated(WindowEvent arg0) {
					// TODO Auto-generated method stub

				}
			});

			if(true){
				JFreeChart jfc2 = ChartUtils.createLineChart(new HashMap<String, List<Double[]>>(), "t", "x", "y", true);
				ChartUtils.showChart(jfc2);
				LiveXYChart worker2 = new LiveXYChart(jfc2,jfc2.getXYPlot(),100);
				worker2.execute();

				JFreeChart jfc3 = ChartUtils.createErrorLineChart(new HashMap<String, List<Double[]>>(), "t", "x", "y", true);
				ChartUtils.showChart(jfc3);
				LiveXYMomentChart worker3 = new LiveXYMomentChart(jfc3,jfc3.getXYPlot(),1500, 101, 50);
				worker3.execute();

				worker3.cancel(true);


				System.out.println("Starting A");
				new Thread(new MsgSender("A", worker,1000)).start();
				//		System.out.println("Starting B");
				//		new Thread(new MsgSender("B", worker,1500)).start();
				//		new Thread(new MsgSender("C", worker2,500)).start();
				//		new Thread(new MsgSender("D", worker2,1100)).start();
				//		new Thread(new MsgSender("E", worker2,3100)).start();
				//		new Thread(new MsgSender2("F", worker3,10)).start();
				//		new Thread(new MsgSender2("G", worker3,50)).start();
			}	

		}
	}

}
