package Productos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) {
		// @author Vicente Pastor

		int option;
		Scanner scanner = new Scanner(System.in);

		// Leemos el fichero productos.txt
		readFile();

		do {
			System.out.println("1. Alta de producto");
			System.out.println("2. Consulta por código de producto");
			System.out.println("0. Salir");
			option = scanner.nextInt();

		} while (option != 0);

		/*
		 * File file = new File("productos.bin"); RandomAccessFile fichero = new
		 * RandomAccessFile(file, "rw");
		 */
	}

	private static void readFile() {
		// Metodo para leer fichero productos.txt sino existe el fichero lo crea

		try {
			String line;
			FileReader fileReader;
			fileReader = new FileReader("productos.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}

			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
