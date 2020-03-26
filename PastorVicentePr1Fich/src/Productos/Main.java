package Productos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {

	public static void main(String[] args) {
		// @author Vicente Pastor
		
		if (!checkFile()) {
			
		}
		
	}

	public static boolean checkFile() {
	// Comprueba si el fichero existe, sino existe lo crea
		boolean exist =  false;
		
		File file = new File("productos.bin");
		if(file.exists() && file.isFile()) {
			exist = true;
		}
		
		return exist;
	}
	
	public static RandomAccessFile createProductosBin () throws IOException {
	// Crea el fichero Aleatorio productos.bin 
		
		FileReader fileReader = new FileReader("productos.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		RandomAccessFile randomAccessFile = new RandomAccessFile("productos.bin", "rw");
		
		randomAccessFile.seek(0); // Me posiciono en el principio del fichero
		
		String readLine = bufferedReader.readLine();
		
		while(readLine != null) {
			// Escribimos el código
			String codigo = readLine;
			StringBuffer stringBuffer = new StringBuffer(codigo);
			stringBuffer.setLength(20); // Limito la longitud máxima a 20
			randomAccessFile.writeChars(stringBuffer.toString());
			
			//
		}
		
		
		return randomAccessFile;
		
	}
	
}
