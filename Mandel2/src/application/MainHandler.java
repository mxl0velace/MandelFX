package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MainHandler {

	@FXML
	private ImageView iv;
	@FXML
	private TextField xField;
	@FXML
	private TextField yField;
	@FXML
	private TextField sField;
	@FXML
	private TextField lField;
	@FXML
	private Label debug;
	
	final int MAXX = Main.MAXX;
	final int MAXY = Main.MAXY;
	final int XOFFINIT = 3;
	final int YOFFINIT = 2;
	final double SCALEINIT = 4;
	final int LIMITINIT = 16;
	double xOffset = XOFFINIT;
	double yOffset = YOFFINIT;
	double xOffish = xOffset;
	double yOffish = yOffset;
	double scale = SCALEINIT;
	int limit = LIMITINIT;
	WriteThread wt;
	private double xstart;
	private double ystart;
	private boolean dragging;
	
	@FXML
	public void initialize() {
		WriteThread wt = new WriteThread(iv, MAXX, MAXY, xOffset, yOffset, limit, scale,debug);
		wt.start();
		resetFields();
	}
	
	public void onClick(MouseEvent event) {
		dragging = true;
		xstart = (event.getX() / MAXY) * scale - xOffset;
		ystart = (event.getY() / MAXY) * scale - yOffset;
		System.out.println(xstart + "," + ystart);
		
		xOffish = -xstart;
		yOffish = -ystart;
	}
	
	public void onRelease(MouseEvent event) {
		if((!event.isStillSincePress()) && dragging) {
			dragging = false;
			double xend = (event.getX() / MAXY) * scale - xOffset;
			double yend = (event.getY() / MAXY) * scale - yOffset;
			scale = Math.abs(ystart - yend);
			xOffset = xOffish;
			yOffset = yOffish;
			new WriteThread(iv,MAXX,MAXY,xOffset,yOffset,limit,scale,debug).start();
			resetFields();
		}
	}
	
	public void onButton(ActionEvent event) {
		try {
			double xoff = Double.parseDouble(xField.getText());
			double yoff = Double.parseDouble(yField.getText());
			double scl = Double.parseDouble(sField.getText());
			int lim = Integer.parseInt(lField.getText());
			xOffset = xoff;
			yOffset = yoff;
			scale = scl;
			limit = lim;
			new WriteThread(iv,MAXX,MAXY,xOffset,yOffset,limit,scale,debug).start();
		} catch(NumberFormatException e) {
			System.out.println("Bad number format!");
			debug.setText("Bad input!");
		}
	}
	
	public void resetFields() {
		xField.setText(Double.toString(xOffset));
		yField.setText(Double.toString(yOffset));
		sField.setText(Double.toString(scale));
		lField.setText(Integer.toString(limit));
	}
	
	public void resetValues() {
		xOffset = XOFFINIT;
		yOffset = YOFFINIT;
		scale = SCALEINIT;
		limit = LIMITINIT;
		new WriteThread(iv,MAXX,MAXY,xOffset,yOffset,limit,scale,debug).start();
		resetFields();
	}

}
