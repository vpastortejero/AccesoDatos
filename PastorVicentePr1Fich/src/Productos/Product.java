package Productos;

import java.util.Map;
import java.util.TreeMap;

public class Product {

	Map<Integer, String> producto = new TreeMap<Integer, String>();
	private String udMedida;
	private int stock;

	public Product(Map<Integer, String> producto, String udMedida, int stock) {
		this.producto = producto;
		this.udMedida = udMedida;
		this.stock = stock;
	}

	public String getUdMedida() {
		return udMedida;
	}

	public void setUdMedida(String udMedida) {
		this.udMedida = udMedida;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
