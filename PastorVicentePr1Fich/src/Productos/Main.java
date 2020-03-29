package Productos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException, InterruptedException {
		// @author VicentePastor
		TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();

		if (!checkFile("productos.bin")) {
			try {
				treeMap = createProductosBin(treeMap);
				createIndiceArticulosOb(treeMap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("El fichero productos.bin ya existe y no ha sido necesario crearlo");
		}
		printMenu(treeMap);

	}

	private static boolean checkFile(String fileName) {
		// Comprueba si el fichero existe, sino existe lo crea
		boolean exist = false;

		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			exist = true;
		}

		return exist;
	}

	private static TreeMap<String, Integer> createProductosBin(TreeMap<String, Integer> treeMap) throws IOException {
		// Crea el fichero Aleatorio productos.bin

		FileReader fileReader = new FileReader("productos.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		RandomAccessFile randomAccessFile = new RandomAccessFile("productos.bin", "rw");

		randomAccessFile.seek(0); // Me posiciono en el principio del fichero

		String readLine = bufferedReader.readLine();
		Integer codeTreeMap = 0;

		while (readLine != null) {
			// Escribimos el código
			String code = readLine;

			StringBuffer stringBufferCode = new StringBuffer(code);
			stringBufferCode.setLength(20); // Limito la longitud máxima a 20
			treeMap.put(stringBufferCode.toString(), codeTreeMap); // Añadimos el código y la posición del dato
			randomAccessFile.writeChars(stringBufferCode.toString()); // 40 bytes
			
			// Escribimos el precio
			readLine = bufferedReader.readLine();
			Double price = Double.parseDouble(readLine);
			randomAccessFile.writeDouble(price); // 8 bytes

			// Escribimos la unidad de medida
			readLine = bufferedReader.readLine();
			String unit = readLine;
			StringBuffer stringBufferUnit = new StringBuffer(unit);
			stringBufferUnit.setLength(20); // Limito la longitud máxima a 20
			randomAccessFile.writeChars(stringBufferUnit.toString()); // 40 bytes

			// Escribimos el stock
			readLine = bufferedReader.readLine();
			int stock = Integer.parseInt(readLine);
			randomAccessFile.writeInt(stock); // 4 bytes

			// Leo siguiente línea para salir del bucle o continuar escribiendo
			readLine = bufferedReader.readLine();
			codeTreeMap = codeTreeMap + 92; // Guardamos la referencia donde se ha escrito
		}
		fileReader.close();
		bufferedReader.close();
		randomAccessFile.close();
		return treeMap;
	}

	private static void createIndiceArticulosOb(TreeMap<String, Integer> treeMap) throws IOException {
		// Crea el fichero indiceArticulos.ob a modo de indice con objetos
		File file = new File("indiceArticulos.ob");
		file.delete();
		
		FileOutputStream fileOutputStream = new FileOutputStream("indiceArticulos.ob", true);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		
		objectOutputStream.writeObject(treeMap);
		fileOutputStream.close();
		objectOutputStream.close();

	}

	private static void printMenu(TreeMap<String, Integer> treeMap) throws FileNotFoundException, ClassNotFoundException, IOException, InterruptedException {
		// Menu desde el que manejamos la aplicación
		int option;
		do {
			System.out.println("");
			System.out.println("------------- MENU --------------");
			System.out.println("1. Alta de producto");
			System.out.println("2. Consulta por código de producto");
			System.out.println("0. Salir");
			System.out.println("---------------------------------");
			option = Leer.pedirEntero("");

			switch (option) {
			case 1:
				createProduct();
				break;

			case 2:
				listProduct();
				break;

			case 0:
				System.out.println("Fin del programa");
				System.exit(0);
				break;
			default:
				System.out.println("La opción no es correcta");
				break;
			}
		} while (option != 0);
	}

	private static void createProduct() throws IOException, ClassNotFoundException {
		// Creación de nuevo producto
		TreeMap<String, Integer> treeMap = readIndiceArticuloOb();	
		String code = null, unit = null;
		Double price = null;
		Integer stock = null;

		code = Leer.pedirCadena("Introduce el nombre del producto:");
		while (code.length() > 20) {
			System.out.println("El nombre introducido excede el tamaño máximo, vuelve a introducirlo:");
			code = Leer.pedirCadena("Introduce el nombre del producto:");
		}
		price = Leer.pedirDouble("Introduce el precio:");
		unit = Leer.pedirCadena("Introduce la unidad de medida:");
		while (unit.length() > 20) {
			System.out.println("La unidad introducida excede el tamaño máximo, vuelve a introducirla:");
			code = Leer.pedirCadena("Introduce la unidad de medida:");
		}
		stock = Leer.pedirEntero("Introduce el stock:");

		System.out.println("-------------------");
		System.out.println("Código: " + code);
		System.out.println("Precio: " + price);
		System.out.println("Unidad de medidad: " + unit);
		System.out.println("Stock: " + stock);
		String save = Leer.pedirCadena("Esta seguro que desea guardad es producto: y/n");
		while (!save.equalsIgnoreCase("y") && !save.equalsIgnoreCase("n")) {
			System.out.println("¡Opción incorrecta!");
			save = Leer.pedirCadena("Esta seguro que desea guardar el producto: y/n");
		}
		if (checkProduct(code)) {
			if (save.equalsIgnoreCase("y")) {
				RandomAccessFile randomAccessFile = new RandomAccessFile("productos.bin", "rw");
				randomAccessFile.seek(randomAccessFile.length()); // Nos posicionamos al final para no sobreescribir
						
				
				// Escribo el código
				StringBuffer stringBufferCode = new StringBuffer(code);
				stringBufferCode.setLength(20); // Limito la longitud máxima a 20
				treeMap.put(stringBufferCode.toString(), (int)randomAccessFile.length());
				randomAccessFile.writeChars(stringBufferCode.toString()); // 40 bytes
				
				// Escribimos el precio
				randomAccessFile.writeDouble(price);
				
				//Escribo la unidad de medida
				stringBufferCode = new StringBuffer(unit);
				stringBufferCode.setLength(20); // Limito la longitud máxima a 20
				randomAccessFile.writeChars(stringBufferCode.toString()); // 40 bytes
				
				// Escribo el stock
				randomAccessFile.writeInt(stock);
				
			} else {
				System.out.println("El producto no ha sido guardado");
			}
			System.out.println("El producto ha sido guardado con éxito");
			createIndiceArticulosOb(treeMap);
		}else {
			System.out.println("El producto introducido ya existe");
		}
	
	}

	private static TreeMap<String, Integer> readIndiceArticuloOb() throws FileNotFoundException, IOException, ClassNotFoundException {
		TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("indiceArticulos.ob"));
		treeMap = (TreeMap<String, Integer>) objectInputStream.readObject();
		
		objectInputStream.close();
		return treeMap;
	}
	
	private static void listProduct() throws FileNotFoundException, IOException, ClassNotFoundException, InterruptedException {

		TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("indiceArticulos.ob"));
		treeMap = (TreeMap<String, Integer>) objectInputStream.readObject();

		String productName = Leer.pedirCadena("Introduce el código de producto que quieres mostrar:");
		StringBuffer stringBufferCode = new StringBuffer(productName);
		stringBufferCode.setLength(20); // Limito la longitud máxima a 20
		
		if (treeMap.get(stringBufferCode.toString()) != null) {
			// Leemos del fichero aleatorio
			RandomAccessFile randomAccessFile = new RandomAccessFile("productos.bin", "r");
			char code [] = new char [20], aux;
			Double price;
			char unit [] = new char [20];
			int stock;
			
			randomAccessFile.seek(treeMap.get(stringBufferCode.toString())); // me posiciono en el valor que queremos leer
			
			// Leemos el codigo 
			for (int i = 0; i < code.length; i++) {
				aux = randomAccessFile.readChar();
				code[i] = aux;
			}
			// Leemos el precio
			price = randomAccessFile.readDouble();
			// Leemos la unidad de medida
			for (int i = 0; i < unit.length; i++) {
				aux = randomAccessFile.readChar();
				unit[i] = aux;
			}
			// Leemos el stock
			stock = randomAccessFile.readInt();
			
			//Muestra toda la información recopilada del producto
			System.out.println("");
			System.out.println("---------------------------------");
			System.out.println("Código: " + String.valueOf(code));
			System.out.println("Precio: " + price);
			System.out.println("Unidad de medida: " + String.valueOf(unit));
			System.out.println("Stock: " + stock);
			System.out.println("---------------------------------");
			
			randomAccessFile.close();
			Thread.sleep(3000); // Detengo el programa durante 3 segundos
		} else {
			System.out.println("El producto introducido no existe");
		}
		objectInputStream.close();
	}

	private static boolean checkProduct(String codeProduct) throws FileNotFoundException, ClassNotFoundException, IOException {
		// Comprueba si el producto esta en el treeMap (existe)
		TreeMap<String, Integer>treeMap = new TreeMap<String, Integer>();
		boolean check = true;
		treeMap = readIndiceArticuloOb();
		StringBuffer stringBufferCode = new StringBuffer(codeProduct);
		stringBufferCode.setLength(20); // Limito la longitud máxima a 20
		if(treeMap.get(stringBufferCode.toString()) != null) {
			check = false;
		}
		return check;
	}
}
