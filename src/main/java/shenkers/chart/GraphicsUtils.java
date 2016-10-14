package shenkers.chart;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.text.AttributeSet.FontAttribute;
import javax.swing.text.JTextComponent;
import javax.swing.text.View;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfConcatenate;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import shenkers.u.Util;


public class GraphicsUtils {

	public static abstract class ComponentAdaptor extends Component{
	
		private static final long serialVersionUID = 1L;
	
		public abstract void paint(Graphics g);
	
	}
	
	public static class FigureBuilder{
		File out;
		List<File> tmp;
		
		public FigureBuilder(File out) {
			this.out = out;
			tmp = new LinkedList<File>();
		}

		public void add(Component c, String desc) throws IOException{
			{
			File f_comp = File.createTempFile("component", "pdf");
			int width = c.getPreferredSize().width;
			int height = c.getPreferredSize().height;
			savePDF(c, f_comp.getAbsolutePath(), width, height);
			tmp.add(f_comp);
			}
			{
				Component d = descriptionComponent(desc);
			File f_desc = File.createTempFile("description", "pdf");
			int width = d.getPreferredSize().width;
			int height = d.getPreferredSize().height;
			savePDF(d, f_desc.getAbsolutePath(), width, height);
			tmp.add(f_desc);
			}
		}
		
		public void add(Component c, String desc_pattern, Object...to_print) throws IOException{
			add(c, Util.sprintf(desc_pattern,to_print));
		}
		
		public void add(Component c) throws IOException{
			{
			File f_comp = File.createTempFile("component", "pdf");
			int width = c.getPreferredSize().width;
			int height = c.getPreferredSize().height;
			savePDF(c, f_comp.getAbsolutePath(), width, height);
			tmp.add(f_comp);
			}
		}
		
		public void add(File pdfFile) throws IOException{
			tmp.add(pdfFile);
		}
		
		public void add(String desc) throws IOException{
			{
				Component d = descriptionComponent(desc);
			File f_desc = File.createTempFile("description", "pdf");
			int width = d.getPreferredSize().width;
			int height = d.getPreferredSize().height;
			savePDF(d, f_desc.getAbsolutePath(), width, height);
			tmp.add(f_desc);
			}
		}
		
		public void close(){
			try{
			concatenatePDFs(out, tmp.toArray(new File[0]));
			}
			catch(Exception e){
				throw new RuntimeException(e);
			}
			for(File f : tmp)
				f.delete();
		}
	}
	
	public static class FigurePainter{
		File out;
		List<File> tmp;
		
		public FigurePainter(File out) {
			this.out = out;
			tmp = new LinkedList<File>();
		}

		public void add(Component c, String desc) throws IOException{
			{
			File f_comp = File.createTempFile("component", "pdf");
			int width = c.getPreferredSize().width;
			int height = c.getPreferredSize().height;
			paintPDF(c, f_comp.getAbsolutePath(), width, height);
			tmp.add(f_comp);
			}
			{
				Component d = descriptionComponent(desc);
			File f_desc = File.createTempFile("description", "pdf");
			int width = d.getPreferredSize().width;
			int height = d.getPreferredSize().height;
			savePDF(d, f_desc.getAbsolutePath(), width, height);
			tmp.add(f_desc);
			}
		}
		
		public void add(Component c, String desc_pattern, Object...to_print) throws IOException{
			add(c, Util.sprintf(desc_pattern,to_print));
		}
		
		public void add(Component c) throws IOException{
			{
			File f_comp = File.createTempFile("component", "pdf");
			int width = c.getPreferredSize().width;
			int height = c.getPreferredSize().height;
			paintPDF(c, f_comp.getAbsolutePath(), width, height);
			tmp.add(f_comp);
			}
		}
		
		public void add(String desc) throws IOException{
			{
				Component d = descriptionComponent(desc);
			File f_desc = File.createTempFile("description", "pdf");
			int width = d.getPreferredSize().width;
			int height = d.getPreferredSize().height;
			savePDF(d, f_desc.getAbsolutePath(), width, height);
			tmp.add(f_desc);
			}
		}
		
		public void close() throws DocumentException, IOException{
			concatenatePDFs(out, tmp.toArray(new File[0]));
			for(File f : tmp)
				f.delete();
		}
	}

	public static Component descriptionComponent(String description){
		JTextArea jta = new JTextArea(description);
		jta.getCaret().setVisible(false);
		jta.setMargin(new Insets(10, 10, 10, 10));
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		jta.setVisible(true);
		jta.setSize(486, 300);
//		FontMetrics fm = jta.getFontMetrics(jta.getFont());
//		float line_height = fm.getHeight()+fm.getDescent();
//		jta.setPreferredSize(GraphicsUtils.dimension(486, (int) (line_height*getWrappedLineCount(jta))));
		return jta;
	}
	
	private static int getWrappedLineCount(JTextComponent textArea) {
	    AttributedString text = new AttributedString(textArea.getText());
	    FontRenderContext frc = textArea.getFontMetrics(textArea.getFont()).getFontRenderContext();
	    AttributedCharacterIterator charIt = text.getIterator();
	    LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);
	    float formatWidth = (float) textArea.getSize().width;
	    lineMeasurer.setPosition(charIt.getBeginIndex());

	    int noLines = 0;
	    while (lineMeasurer.getPosition() < charIt.getEndIndex()) {
	      lineMeasurer.nextLayout(formatWidth);
	      noLines++;
	    }

	    return noLines;
	  }
	
	
	
	public static void savePDF(Component component, String fileName, int width, int height) throws IOException{
		Rectangle r = new Rectangle(width, height);
		com.itextpdf.text.Document document = new com.itextpdf.text.Document(r);
		
//		JWindow jw = new JWindow();
//		jw.add(component);
		component.setSize(width,height);
//		component.setBounds(0, 0, width, height);
//		component.setVisible(true);
//		component.enable();
		component.addNotify();
		component.validate();
//		component.invalidate();
//		component.doLayout();
		
	
//		jw.pack();
//		jw.validate();
//		jw.setVisible(true);
		try{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			
			document.open();

			PdfContentByte contentByte = writer.getDirectContent();
			PdfTemplate template = contentByte.createTemplate(width, height);
			Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());

//			component.paint(graphics2d);
			component.print(graphics2d);
//			component.addNotify();
//			component.validate();
				//			component.addNotify();
			//			component.validate();

//			jw.dispose();
			contentByte.addTemplate(template, 0, 0);		 

			graphics2d.dispose();
			document.close();
			writer.close();
		}
		catch (DocumentException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	public static void paintPDF(Component component, String fileName, int width, int height) throws IOException{
		Rectangle r = new Rectangle(width, height);
		com.itextpdf.text.Document document = new com.itextpdf.text.Document(r);
		
//		JWindow jw = new JWindow();
//		jw.add(component);
		component.setSize(width,height);
//		component.setBounds(0, 0, width, height);
//		component.setVisible(true);
//		component.enable();
		component.addNotify();
		component.validate();
//		component.invalidate();
//		component.doLayout();
		
	
//		jw.pack();
//		jw.validate();
//		jw.setVisible(true);
		try{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			
			document.open();

			PdfContentByte contentByte = writer.getDirectContent();
			PdfTemplate template = contentByte.createTemplate(width, height);
			Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());

			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics big = bi.getGraphics();
			component.paint(big);
			big.dispose();
			graphics2d.drawImage(bi, 0, 0, null);
			
//			component.print(graphics2d);
//			component.addNotify();
//			component.validate();
				//			component.addNotify();
			//			component.validate();

//			jw.dispose();
			contentByte.addTemplate(template, 0, 0);		 

			graphics2d.dispose();
			document.close();
			writer.close();
		}
		catch (DocumentException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	public static void concatenatePDFs(File out, File... files) throws DocumentException, IOException{
		PdfConcatenate pc = new PdfConcatenate(new FileOutputStream(out));
		for(File f : files)
			pc.addPages(new PdfReader(new FileInputStream(f)));
		pc.close();
	}

	public static void saveImage(Component component, String fileName, String formatName, int width, int height) throws IOException{
		JWindow jw = new JWindow();
		jw.add(component);
		jw.pack();
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = img.getGraphics();
		component.paint(img.getGraphics());
		g.dispose();
		jw.dispose();
		ImageIO.write(img, formatName, new File(fileName));
	}

	public static void saveSVG(Component component, String fileName, int width, int height) throws IOException{
		// Get a DOMImplementation.
		DOMImplementation domImpl =
				GenericDOMImplementation.getDOMImplementation();
	
		// Create an instance of org.w3c.dom.Document.
		String svgNS = "http://www.w3.org/2000/svg";
		Document document = domImpl.createDocument(svgNS, "svg", null);
	
		// Create an instance of the SVG Generator.
		SVGGraphics2D g = new SVGGraphics2D(document);
		g.setSVGCanvasSize(new Dimension(width,height));
	
		// Ask the test to render into the SVG Graphics2D implementation.  
		component.paint(g);
	
		// Finally, stream out SVG to the standard output using
		// UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
		//   File toSave = new File(fileName);
		Writer out = new FileWriter(fileName);// OutputStreamWriter(System.out, "UTF-8");
		g.stream(out, useCSS);
		out.close();
	}

	public static JFrame frame(Component c){
		JFrame f = new JFrame();
		f.getContentPane().add(c);
		return f;
	}

	public static void display(JFrame c){
		c.pack();
		c.setResizable(true);
		c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		c.setLocationRelativeTo(null);
		c.setVisible(true);
	}

	public static JFrame show(Component c){
		JFrame f = new JFrame();
		f.getContentPane().add(c);
		f.pack();
		f.setResizable(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		return f;
	}

	public static JFrame show(Component c, boolean resizable){
		JFrame f = new JFrame();
		f.getContentPane().add(c);
		f.pack();
		f.setResizable(resizable);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		return f;
	}

	public static Component setPrefferedSize(Component c, int w, int h) {
		c.setPreferredSize(new Dimension(w,h));
		return c;
	}

	public static Dimension dimension(int width, int height) {
		return new Dimension(width, height);
	}

}
