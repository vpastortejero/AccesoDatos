package Productos;

import java.util.Map;
import java.util.TreeMap;

public class Product {

	Map<Integer, String> product = new TreeMap<Integer, String>();
	private Double precio;
	private String udMedida;
	private int stock;

	public Product(Map<Integer, String> product, Double precio, String udMedida, int stock) {
		this.product = product;
		this.precio = precio;
		this.udMedida = udMedida;
		this.stock = stock;
	}
	
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
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
