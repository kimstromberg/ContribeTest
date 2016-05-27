
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class listOfBooks implements BookList{

	HashMap<Book,Integer> bookList = new HashMap<Book,Integer>();
	
	/*
	 * function that has two modes of operation.
	 * 1) look up all books in a listOfBooks.
	 * 2) look up all specific instances with a certain
	 * 		title and author.
	 * Mode 1 is called by providing starting query with the word "all".
	 * Mode 2 is called by providing the name of author and the title
	 * 		separated by a space.
	 */
	@Override
	public Book[] list(String searchString) {
		
		String[] input = searchString.split(" ");
		Book[] bookArray = null;
		boolean multipleInstances = false;
		
		if(input.length > 1){
			for(Map.Entry<Book,Integer> entry : bookList.entrySet()){
				if(entry.getKey().getTitle().equals(input[0]) && entry.getKey().getAuthor().equals(input[1])){
					if(multipleInstances){
						System.out.println("Error, duplications with different prices");
						System.out.println("Fix this issue then try again");
						return null;
					}
					int nrBooksInList = entry.getValue();
					bookArray = new Book[nrBooksInList];
					for(int i=0; i<nrBooksInList;i++){
						bookArray[i] = entry.getKey();
					}
					multipleInstances = true;
				}
			}
		}else if(input[0].equals("all")){
			int nrBooksInList = 0;
			for(int i : bookList.values()){
				nrBooksInList += i;
			}
			bookArray = new Book[nrBooksInList];
			int c = 0;
			int nrOfCopies;
			for(Map.Entry<Book, Integer> entry : bookList.entrySet()){
				nrOfCopies = entry.getValue();
				for(int i = 0; i<nrOfCopies; i++){
					bookArray[c] = entry.getKey();
					c++;
				}
			}
			
		}else{
			System.out.println("Provide query: all or 'title author'");
		}
		return bookArray;
	}

	/*
	 * function that adds a non-negative number of books
	 * 		to a list of books.
	 */
	@Override
	public boolean add(Book book, int quantity){
		if(quantity < 0){
			return false;
		}else{
			Integer nrOfCopies = bookList.get(book);
			if(nrOfCopies == null){
				bookList.put(book,quantity);
			}else{
				bookList.put(book,(quantity+nrOfCopies));
			}
			return true;
		}		
	}
	
	/*
	 * Attempts to buy a list of books
	 * Returns a list of integers depending on 
	 * the result:
	 * 		0 - OK
	 * 		1 - NOT_IN_STOCK
	 * 		2 - DOES_NOT_EXIST
	 */
	@Override
	public int[] buy(Book... books) {
		
		int nrOfEntries = books.length;
		int[] returnList = new int[nrOfEntries];
		
		for(int i = 0; i<books.length; i++){
			if(bookList.containsKey(books[i])){
				if(bookList.get( books[i]) == 0 ){
					returnList[i] = 1;
				}else{
					returnList[i] = 0;
					bookList.put( books[i], bookList.get(books[i])-1 );
				}
			}else{
				returnList[i] = 2;
			}
		}	
		return returnList;
	}
	
	/*
	 * function that returns the price of a list of books
	 */
	public BigDecimal totalPrice(Book...books){
		BigDecimal totalPrice = new BigDecimal(0.0);
		for(int i = 0; i<books.length; i++){
			totalPrice = totalPrice.add(books[i].getPrice());
		}
		return totalPrice;	
	}
	
	/*
	 * function that tries to remove a specified number of
	 * 		books from a list of books.
	 * Returns false and does nothing if not enough
	 *		books are available to be removed
	 */
	public boolean remove(Book book, int quantity){
		Integer nrOfCopies = bookList.get(book);
		
		if(nrOfCopies.equals(null) || nrOfCopies < quantity){
			return false;
		}
		else{
			bookList.put( book, nrOfCopies-quantity );
			return true;
		}
	}
	
	/*
	 * Deletes a book from the list of books
	 */
	public boolean deleteBook(Book book){
		if(bookList.get(book).equals(null)){
			return false;
		}else{
			bookList.remove(book);
			return true;
		}
	}	
}
