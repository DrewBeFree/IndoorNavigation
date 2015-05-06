package android.prototype.trainer;

public class Product 
{
  private Float x;
  private Float y;
  private String name;
  
	public Product(String name, Float x, Float y)
	{
		this.x = x;
		this.y = y;
		this.name = name;
	}
	public Float getX() {
		return x;
	}
	public void setX(Float x) {
		this.x = x;
	}
	public Float getY() {
		return y;
	}
	public void setY(Float y) {
		this.y = y;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
