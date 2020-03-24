package Productos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) {
		// @author Vicente Pastor

		Map<Integer, String> product = new TreeMap<Integer, String>();
		LinkedList<Product> listProducts = readFile();
		
		
		Scanner scanner = new Scanner(System.in);
		int option;
		
		for (Product product : listProducts) {
			
		}

		do {
			System.out.println("1. Alta de producto");
			System.out.println("2. Consulta por código de producto");
			System.out.println("0. Salir");
			option = scanner.nextInt();

		} while (option != 0);


	}

	private static LinkedList<Product> readFile() {
		// Metodo para leer fichero productos.txt sino existe el fichero lo crea
		
		LinkedList<Product> listProducts = new LinkedList<Product>();
		
		try {
			Map<Integer, String> treeMap = new TreeMap<Integer, String>();
			int code = 1;
			Double precio;
			String line, udMedida; 
			int stock;
			
			FileReader fileReader;
			fileReader = new FileReader("productos.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				treeMap.put(code, line);
				precio = Double.parseDouble(bufferedReader.readLine());
				udMedida = bufferedReader.readLine();
				stock = Integer.parseInt(bufferedReader.readLine());
				Product product = new Product(treeMap, precio, udMedida, stock);
				System.out.println(precio + " - " + udMedida + " - " + stock);
				listProducts.add(product);
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listProducts;
	}
	
	private static void writeRandomAccessFile(String line) {
		// Escribe los datos de productos.txt en RandomAccessFile 
		try {
			File file = new File("productos.bin");
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			System.out.println(line);
			randomAccessFile.writeChars(line);
			randomAccessFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void createIndiceArticulos(){
		
	}

}
