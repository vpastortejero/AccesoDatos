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

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		// @author VicentePastor

		if (!checkFile("productos.bin")) {
			try {
				createProductosBin();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("El fichero productos.bin ya existe y no ha sido necesario crearlo");
		}
		printMenu();

	}

	public static boolean checkFile(String fileName) {
		// Comprueba si el fichero existe, sino existe lo crea
		boolean exist = false;

		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			exist = true;
		}

		return exist;
	}

	public static void createProductosBin() throws IOException {
		// Crea el fichero Aleatorio productos.bin

		FileReader fileReader = new FileReader("productos.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		RandomAccessFile randomAccessFile = new RandomAccessFile("productos.bin", "rw");
		TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();

		randomAccessFile.seek(0); // Me posiciono en el principio del fichero

		String readLine = bufferedReader.readLine();
		Integer codeTreeMap = 0;

		while (readLine != null) {
			// Escribimos el código
			String code = readLine;
			StringBuffer stringBufferCode = new StringBuffer(code);
			stringBufferCode.setLength(20); // Limito la longitud máxima a 20
			createIndiceArticulosOb(codeTreeMap, stringBufferCode.toString());
			randomAccessFile.writeChars(stringBufferCode.toString());

			// Escribimos el precio
			readLine = bufferedReader.readLine();
			Double price = Double.parseDouble(readLine);
			randomAccessFile.writeDouble(price);

			// Escribimos la unidad de medida
			readLine = bufferedReader.readLine();
			String unit = readLine;
			StringBuffer stringBufferUnit = new StringBuffer(unit);
			stringBufferUnit.setLength(20); // Limito la longitud máxima a 20
			randomAccessFile.writeChars(stringBufferUnit.toString());

			// Escribimos el stock
			readLine = bufferedReader.readLine();
			int stock = Integer.parseInt(readLine);
			randomAccessFile.writeInt(stock);

			// Leo siguiente línea para salir del bucle o continuar escribiendo
			readLine = bufferedReader.readLine();
			codeTreeMap = codeTreeMap + 52; // Guardamos la referencia donde se ha escrito
		}
	}

	public static void createIndiceArticulosOb(Integer codeTreeMap, String productName) throws IOException {
		// Crea el fichero indiceArticulos.ob a modo de indice con objetos
		TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
		treeMap.put(codeTreeMap, productName);

		FileOutputStream fileOutputStream = new FileOutputStream("indiceArticulos.ob", true);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

		objectOutputStream.writeObject(treeMap);
		objectOutputStream.close();

	}

	public static void printMenu() throws FileNotFoundException, ClassNotFoundException, IOException {
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
				listProducts();
				break;
			
			default:
				System.out.println("La opción no es correcta");
				break;
			}
		} while (option != 0);
	}

	public static void createProduct() {
		// Creación de nuevo producto
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
			save = Leer.pedirCadena("Esta seguro que desea guardad es producto: y/n");
		}
		if (save.equalsIgnoreCase("y")) {
			System.out.println("EL PROUCTO HA SIDO GUARDADO");
		} else {
			System.out.println("EL PROUCTO NO HA SIDO GUARDADO");
		}
	}
	
	public static void listProducts () throws FileNotFoundException, IOException, ClassNotFoundException {
		// String productName = Leer.pedirCadena("Introduce el código de producto que quieres consultar:");
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("indiceArticulos.ob"));
		Object object = objectInputStream.readObject();
		while(object != null) {
			System.out.println(object);
			object = objectInputStream.readObject();
		}
		objectInputStream.close();
	}
}
