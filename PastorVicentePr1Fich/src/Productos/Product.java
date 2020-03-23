package Productos;

public class Product {

	private int codigo;
	private double precio;
	private String udMedida;
	private int stock;
	
	public Product(int codigo, double precio, String udMedida, int stock) {
		super();
		this.codigo = codigo;
		this.precio = precio;
		this.udMedida = udMedida;
		this.stock = stock;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
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
