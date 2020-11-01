package es.deusto.prog3.cap00.resueltos.edicionSpritesV2;

import java.util.*;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.plaf.SliderUI;

public class PruebasVarias {
	static ArrayList<String>lista;
	
	public static void main (String[]args) {
		try {
		VentanaEdicionSprites v= new VentanaEdicionSprites();
		v.setVisible(true);
		ControladorVentanaSprites c= new ControladorVentanaSprites(v);
		Class clases[]= {JSlider.class, JTextField.class};
		c.getClass().getMethod("sliderStateChanged", clases);
		}catch (Exception e) {
			
		}
	}
}
