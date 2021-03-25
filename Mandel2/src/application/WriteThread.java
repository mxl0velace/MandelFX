package application;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class WriteThread extends Thread {
	
	private final int MAXX;
	private final int MAXY;
	private final double XOFFSET;
	private final double YOFFSET;
	private boolean shutdown = false;
	private double scale;
	private int limit;
	private ImageView iv;
	private Label l;
	private boolean useL = false;
	
	public WriteThread(ImageView iv, int maxx, int maxy, double xoffset, double yoffset, int limit, double scale) {
		super();
		this.iv = iv;
		MAXX = maxx;
		MAXY = maxy;
		XOFFSET = xoffset;
		YOFFSET = yoffset;
		this.limit = limit;
		this.scale = scale/maxy;
		
	}
	
	public WriteThread(ImageView iv, int maxx, int maxy, double xoffset, double yoffset, int limit) {
		this(iv,maxx,maxy,xoffset,yoffset,limit,3.0);
	}
	
	public WriteThread(ImageView iv, int maxx, int maxy, double xoffset, double yoffset, int limit, double scale, Label output) {
		this(iv,maxx,maxy,xoffset,yoffset,limit,scale);
		l = output;
		useL = true;
	}
	
	@Override
	public void run() {
		System.out.println("Write Thread Starting!");
		if(useL) {
			setLText("Processing...");
		}
		int x = 0;
		int y = 0;
		WritableImage img = new WritableImage(MAXX+1,MAXY+1);
		PixelWriter pw = img.getPixelWriter();
		while (!shutdown && y < MAXY) {
			if(x > MAXX) {
				//System.out.println("Line: " + y);
				x = 0;
				y += 1;
				/*try {
					if(y % 10 == 0) {
						Thread.sleep(10);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
			double fx = ((double) x) * scale - XOFFSET;
			double fy = ((double) y) * scale - YOFFSET;
			/*if(fx * fy > 0) {
				pw.setColor(x, y, Color.ALICEBLUE);
			} else {
				pw.setColor(x, y, Color.DARKBLUE);
			}*/
			ComplexNumber c = new ComplexNumber(fx,fy);
			ComplexNumber z = new ComplexNumber(0,0);
			for(float i = limit; i >= 0; i--) {
				z = z.multiply(z).add(c);
				if(z.magnitude() > 2) {
					pw.setColor(x, y, Color.color(i/limit, i/limit, i/limit));
					//pw.setColor(x, y, Color.DARKRED);
					break;
				}
				if(i == 0) {
					pw.setColor(x, y, Color.color(i/limit, i/limit, i/limit));
					//pw.setColor(x, y, Color.GREENYELLOW);
				}
			}

			x += 1;
		}
		System.out.println("Writing...");
		iv.setImage(img);
		if(useL) {
			setLText("Finished!");
		}
		System.out.println("Write thread ending!");
	}
	
	public void shutdown() {
		shutdown = true;
	}
	
	private void setLText(String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				l.setText(text);
			}
		});
	}
}
