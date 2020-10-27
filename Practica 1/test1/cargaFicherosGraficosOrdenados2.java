import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;

import javax.swing.DefaultListModel;

import org.junit.Test;

import es.deusto.prog3.cap00.resueltos.edicionSpritesV2.*;

public class cargaFicherosGraficosOrdenados2 {
	//TODO preguntar a que se refiere con que la lista tenga la misma canidad de png que elementos tiene el modelo
	@Test
	public void test() {
		VentanaEdicionSprites v = new VentanaEdicionSprites();
		DefaultListModel<File>modelo=(DefaultListModel)v.lSprites.getModel();
		modelo.clear();
		try {
		File f= new File(new URI("C:/Users/cdcol/eclipse-workspace/Practica%201/test1/spritesheets/ninja/png"));
		v.getController().cargaFicherosGraficosOrdenados(f);
		if (v.lSprites.getModel().getSize()==0) {
			fail("error en la carga de fichero");
		}else {
			int resultado=0;
			for (int i=0;i<v.lSprites.getModel().getSize();i++) {
				v.lSprites.setSelectedIndex(i);	//cojo elemento por elemento toda la lista
				if (v.lSprites.getSelectedValue().getName().endsWith(".png") ) { //compruebo si acaba con png
				//if (v.lSprites.getModel().getElementAt(i).getName().endsWith(".png")) {//si el elemento del modelo acaba con png saldra en lSprites
					resultado++;
				}
				if (v.lSprites.getModel().getSize()-1==i) {
					if (v.lSprites.getLastVisibleIndex()!=i)
						fail("lista no es igual de larga");
				}
			}
			assertEquals(v.lSprites.getModel().getSize(), resultado);
			boolean correct=true;
			for (int i=0;i<v.lSprites.getModel().getSize()-1;i++) {
				if (v.lSprites.getModel().getElementAt(i).getName().compareTo( v.lSprites.getModel().getElementAt(i+1).getName())>0) {
					correct=false;
				}
			}
			assertEquals(true, correct);
		}
		}catch(Exception e) {
			
		}
		v.getController().cargaFicherosGraficosOrdenados(null);
		if (v.lSprites.getModel().getSize()!=0) {
			fail("error en la carga de ficheros null");
		}
		
	}

}
