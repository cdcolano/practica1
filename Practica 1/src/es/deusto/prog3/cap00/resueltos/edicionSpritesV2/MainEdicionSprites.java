package es.deusto.prog3.cap00.resueltos.edicionSpritesV2;

import java.io.File;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.UIManager;  // Para usar look and feels distintos al estándar

import es.deusto.prog3.cap00.resueltos.edicionSpritesV2.ControladorVentanaSprites.SpriteSec;

/** Clase principal de edición de sprites<br/>
 * Enlace a un zip con gráficos para sprites de ejemplo:
 * <a href="https://drive.google.com/file/d/1UhqJT1zh_aYzcCgKa_6eRUdQvnqP8k0v/view?usp=sharing">link a fichero comprimido</a>
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class MainEdicionSprites {
	static Logger log;

	/** Método principal, crea una ventana de edición y la lanza 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			log= Logger.getLogger("loger-spries");
			log.addHandler(new FileHandler("edicionSprites.xml"));
		}catch(Exception e) {
			
		}
		try { // Cambiamos el look and feel (se tiene que hacer antes de crear la GUI
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) { } // Si Nimbus no está disponible, se usa el l&f por defecto
		VentanaEdicionSprites v = new VentanaEdicionSprites();
		
		
		// TODO Sentencias de prueba
		// Estas tres líneas inicializan la secuencia con tres gráficos de ejemplos (sustituir los paths por los gráficos que se deseen)
		// (Para hacer pruebas en cualquier ventana a veces es conveniente inicializar componentes a mano
		// y así se pueden probar cosas sin tener que hacer todos los pasos. Luego se quitan cuando las 
		// pruebas se han acabado)
	/*	try{
	
		v.getController().anyadirSpriteASecuencia( new File(new URI("file:///C:/Users/cdcol/eclipse-workspace/Practica%201/src/spritesheets/ninja/png/Attack__000.png")));
		v.getController().anyadirSpriteASecuencia( new java.io.File((new URI("file:///C:/Users/cdcol/eclipse-workspace/Practica%201/src/spritesheets/ninja/png/Attack__001.png"))) );
		v.getController().anyadirSpriteASecuencia( new java.io.File( (new URI("file:///C:/Users/cdcol/eclipse-workspace/Practica%201/src/spritesheets/ninja/png/Attack__002.png")) ) );
		v.getController().anyadirSpriteASecuencia( new java.io.File( (new URI("file:///C:/Users/cdcol/eclipse-workspace/Practica%201/src/spritesheets/ninja/png/Attack__003.png")) ) );
		v.getController().anyadirSpriteASecuencia( new java.io.File( (new URI("file:///C:/Users/cdcol/eclipse-workspace/Practica%201/src/spritesheets/ninja/png/Attack__004.png")) ) );
		}catch(Exception e) {}	
		*/
		
		v.setVisible( true );
	}
	
	
	private static String HOST="localhost";
	private static int PUERTO=4000;
	private void online (VentanaEdicionSprites servidor, ArrayList<VentanaEdicionSprites>clientes) {
		VentanaEdicionSprites vServidor=servidor;
		new Thread(()-> {
			//	lanzaServidor();	
			});
		for (VentanaEdicionSprites cliente:clientes) {
			new Thread(()-> {
				//lanzaCLiente();
			});
			cliente.setEnabled(false);
		}
	
	}
	
	
	private void lanzaServidor(VentanaEdicionSprites miVentana) {
		try(ServerSocket serverSocket= new ServerSocket(PUERTO);) {
			Socket socket=serverSocket.accept();
			PrintWriter outputACliente=new PrintWriter(socket.getOutputStream(),true);
			while (!miVentana.finComunicacion) {
				
				outputACliente.println (miVentana.getController().ultimaCarpeta.getAbsolutePath());
				String secuencia= new String();
				String datosSecuencia= new String();
				if (miVentana.mSecuencia.getSize()!=0) {
					for (int i=0; i < miVentana.mSecuencia.getSize();i++) {
						File f= miVentana.mSecuencia.getElementAt(i);
						SpriteSec sprite= miVentana.getController().datosSecuencia.get(i);
						if (i!=miVentana.mSecuencia.getSize()-1) {
							datosSecuencia= datosSecuencia + sprite.duracionMsegs + ";" + sprite.offsetX + ";" + sprite.offsetY + ";" + sprite.rotacion+ ";" 
						+ sprite.zoom + ",";
							secuencia= secuencia + f.toURI() + ",";
						}else {
							datosSecuencia= datosSecuencia + sprite.duracionMsegs + ";" + sprite.offsetX + ";" + sprite.offsetY + ";" + sprite.rotacion+ ";" 
									+ sprite.zoom;
							secuencia= secuencia + f.toURI();
						}
					}
				}
				outputACliente.println ( datosSecuencia);
				outputACliente.println (secuencia );		
				outputACliente.println (""+ miVentana.slZoom.getValue() );
				outputACliente.println ("" + miVentana.slZoomAnim.getValue());
				outputACliente.println (""+ miVentana.slRotacion.getValue());
				outputACliente.println (""+miVentana.slRotacionAnim.getValue());
				outputACliente.println (""+ miVentana.slVelocidad.getValue());
				outputACliente.println ( ""+miVentana.slAngulo.getValue());
				outputACliente.println (""+ miVentana.slGravedad.getValue());
				
				
			}
		}catch (Exception e) {
			
		}
	}
	
	public void lanzaCliente() {
		
	}

}
