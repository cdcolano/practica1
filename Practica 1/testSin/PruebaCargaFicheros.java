import static org.junit.Assert.*;

import java.io.File;
import java.net.URI;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import org.junit.Test;

import es.deusto.prog3.cap00.resueltos.edicionSpritesV2.VentanaEdicionSprites;

public class PruebaCargaFicheros {
	//TODO preguntar con que valor hay que llamar a acarga ficheros
	@Test
	public void test() {
		VentanaEdicionSprites v = new VentanaEdicionSprites();
		DefaultListModel<File>modelo=(DefaultListModel)v.lSprites.getModel();
		modelo.clear();
		v.setVisible(true);
		try {
		File f=new File ("C:\\Users\\cdcol\\git\\practica1\\Practica 1\\testSin\\spritesheets\\ninja\\png");
		v.getController().cargaFicherosGraficosOrdenados(f);
		
		}catch(Exception e) {
			
		}
		if (v.lSprites.getModel().getSize()!=0) {
			System.out.println(v.lSprites.getModel().getSize());
			fail();
		}
	}

}
