package es.deusto.prog3.cap00.resueltos.edicionSpritesV2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.http.ConnectionManager;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.ImageTag;


public class ProcesaWeb {
	ControladorVentanaSprites controlador;
	static int numImagenes;
	public ProcesaWeb(ControladorVentanaSprites c) {
		controlador=c;
	}
	public static void main (String[]args) {
		numImagenes=0;
		String h= JOptionPane.showInputDialog("Introduce la busqueda");
		String busqueda="https://www.bing.com/images/search?q=".concat(h);
		busqueda=busqueda.concat("&first=1&scenario=ImageBasicHover");
		System.out.println(busqueda);
		procesaWeb(busqueda, new ProcesadoWeb() {
		
		@Override
		public void procesaTagCierre(Tag tag, LinkedList<Tag> pilaTags, boolean enHtml) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void procesaTag(Tag tag, LinkedList<Tag> pilaTags) {
			String s= tag.getText();
			if (tag.getAttribute("src")!=null ) {
				System.out.println(tag.getAttribute("src"));
			}
			
			
			//TODO inspeccionar Tags
		}
		
		@Override
		public void procesaImagen(ImageTag imagen, LinkedList<Tag> pilaTags) {
			try {
				if (numImagenes>30) {
					URL url = new URL (imagen.getImageURL());
					URLConnection urlCon = url.openConnection();
					InputStream in= urlCon.getInputStream();
					OutputStream os =new FileOutputStream( new File(new URI("descarga"+ new Date().getTime()+".jpg")));
					// buffer para ir leyendo.
					byte [] array = new byte[1024];

					// Primera lectura y bucle hasta el final
					int leido = in.read(array);
					while (leido > 0) {
					   os.write(array,0,leido);
					   leido=in.read(array);
					}

					// Cierre de conexion y fichero.
					in.close();
					os.close();
					numImagenes++;
					
				}
			} catch (Exception e) {
			}
			
			//TODO necesidad de URL
			//una vez se tiene el URL se puede descargar la imagen
			//TODO solucionar el problema del nombre (posibilidad asignando nombre y numero de descarga
		}
	});//TODO sacar forma de que el usuario meta palabras y concatenarlas con bing
	URL url;
//	miVentana.mSprites.clear();
}

static LinkedList<Tag> pilaTags;
/** Procesa una web y lanza el método observador con cada uno de sus elementos
 * @param dirWeb	Web que se procesa
 * @param proc	Objeto observador que es llamado con cada elemento de la web
 */
public static void procesaWeb( String dirWeb, ProcesadoWeb proc ) {
	URL url;
	pilaTags = new LinkedList<>();
	try {
		ConnectionManager manager = Page.getConnectionManager();
		manager.getRequestProperties().put( "User-Agent", "Mozilla/4.0" ); // Hace pensar a la web que somos un navegador
		URLConnection connection = manager.openConnection( dirWeb );
		Lexer mLexer =  new Lexer( connection );
		Node n = mLexer.nextNode();
		while (n!=null) {
			
			if (n instanceof Tag) {
				//System.out.println(n.getClass() + "\t" + n);
				Tag t = (Tag) n;
				if (t.isEndTag()) {
					if (pilaTags.get(0).getTagName().equals(t.getTagName())) {  // Tag de cierre
						pilaTags.pop();
						proc.procesaTagCierre( t, pilaTags, true );
					} else {  // El tag que se cierra no es el último que se abrió: error html pero se procesa
						boolean estaEnPila = false;
						for (Tag tag : pilaTags) if (tag.getTagName().equals(t.getTagName())) estaEnPila = true;
						if (estaEnPila) {  // Ese tag está en la pila: quitar todos los niveles hasta él
							while (!pilaTags.get(0).getTagName().equals(t.getTagName())) {
								Tag tag = pilaTags.pop();
								proc.procesaTagCierre( tag, pilaTags, false );
							}
							pilaTags.pop();
							proc.procesaTagCierre( t, pilaTags, true );
						} else { // El tag que se cierra no está en la pila
						}
					}
				} else if (t.getText().endsWith("/")){  // Tag de apertura y cierre
					proc.procesaTag( t, pilaTags );
					proc.procesaTagCierre( t, pilaTags, true );
				} else { // Tag de inicio
					proc.procesaTag( t, pilaTags );
					pilaTags.push( t );
				}
			} else {
				if (n instanceof ImageTag) {
					System.out.println("es imagen");
					proc.procesaImagen( (ImageTag)n, pilaTags );
				} else {
					
				}
			}
			n = mLexer.nextNode();
		}
	}catch (Exception e) {
		
	}
}

public static interface ProcesadoWeb {
	/** Método llamado cuando se procesa un tag de apertura html
	 * @param tag	Tag de apertura con toda la información incluida
	 * @param pilaTags	Pila actual de tags (previa a ese tag)
	 */
	void procesaTag( Tag tag, LinkedList<Tag> pilaTags );
	/** Método llamado cuando se procesa un tag de cierre
	 * @param tag	Tag de cierre
	 * @param pilaTags	Pila actual de tags (posterior a cerrar ese tag)
	 * @param enHtml	true si el tag de cierre es explícito HTML, false si es implícito en el fichero pero no está indicado
	 */
	void procesaTagCierre( Tag tag, LinkedList<Tag> pilaTags, boolean enHtml );
	/** Método llamado cuando se procesa una imagen 
	 * @param imagen	
	 * @param pilaTags	Pila actual de tags donde aparece esa imagen
	 */
	void procesaImagen( ImageTag imagen, LinkedList<Tag> pilaTags );
}
}
