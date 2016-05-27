import java.math.BigDecimal;

public class Book{
	private String title;
	private String author;
	private BigDecimal price;
	
	public Book(String title, String author, BigDecimal price){
		
		this.title = title;
		this.author = author;
		this.price = price;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public BigDecimal getPrice(){
		return price;
	}
	
}
