import static org.junit.Assert.*;
import java.math.BigDecimal;
import org.junit.Test;

public class listOfBooksTest {

	/*
	 * Tests if initializeBookStore adds books to a listOfBooks
	 */
	@Test
	public void testInitialize(){
		listOfBooks user = new listOfBooks();
		listOfBooks store = new listOfBooks();
		bookStore.initializeBookStore(store);
		
		Book[] userList = user.list("all");
		Book[] StoreList = store.list("all");
		
		assertNotEquals(userList.length,StoreList.length);		
	}
	
	/*
	 * Tests if list returns the correct object (i.e. book)
	 */
	@Test
	public void testList() {
		listOfBooks list = new listOfBooks();
		
		Book testBook = new Book("Kim","Stromberg",new BigDecimal(2.2));
		Book test = new Book("Tim","Strom",new BigDecimal(3.3));
		
		list.add(test, 1);
		
		Book[] returnList = list.list("all");
		
		assertEquals(test, returnList[0]);
		assertNotEquals(testBook, returnList[0]);
	}
	
	/*
	 * Tests so that the function list properly differentiates between
	 * specific query and query for all books
	 */
	@Test
	public void testSpecificAndFullQueryList(){

		listOfBooks list = new listOfBooks();
		
		Book testBook = new Book("Kim","Stromberg",new BigDecimal(2.2));
		Book test = new Book("Tim","Strom",new BigDecimal(3.3));
		
		list.add(test, 4);
		list.add(testBook, 7);
		
		Book[] openQueryResults = list.list("all");
		Book[] specificQueryResults = list.list("Kim Stromberg");
		
		assertNotEquals(openQueryResults.length, specificQueryResults.length);
	}

	/*
	 * Tests if adding books to a list works properly
	 */
	@Test
	public void testAddingBooks() {
		
		listOfBooks emptyCart = new listOfBooks();
		listOfBooks oneCart = new listOfBooks();
		listOfBooks fiveCart = new listOfBooks();
		
		Book testBook = new Book("Kim","Stromberg",new BigDecimal(2.2));
		
		oneCart.add(testBook, 1);
		fiveCart.add(testBook, 5);
		
		Book[] emptyList = emptyCart.list("all");
		Book[] oneList = oneCart.list("all");
		Book[] fiveList = fiveCart.list("all");
		
		assertEquals(0, emptyList.length);
		assertEquals(1, oneList.length);
		assertEquals(5, fiveList.length);

	}
	
	/*
	 * Tests if adding the same book at two different times 
	 * 		counts towards the same total and does not overwrite
	 */
	@Test
	public void testAddingIterative(){
		
		listOfBooks iterativeCart = new listOfBooks();
		Book testBook = new Book("Kim","Stromberg",new BigDecimal(2.2));
		
		iterativeCart.add(testBook, 7);
		Book[] iterativeList = iterativeCart.list("all");
		int firstIteration = iterativeList.length;
		
		assertEquals(7, firstIteration);
		
		iterativeCart.add(testBook, 3);
		Book[] secondIterativeList = iterativeCart.list("all");
		int secondIteration = secondIterativeList.length;

		assertEquals(secondIteration, 10);
		assertNotEquals(secondIteration, firstIteration);
		assertNotEquals(secondIteration, 3);
	}
	
	/*
	 * Tests so that a negative number of books can't be added
	 */
	@Test
	public void testAddingNegative(){
		
		listOfBooks normalAddition = new listOfBooks();
		listOfBooks negativeAddition = new listOfBooks();
		Book testBook = new Book("Kim","Stromberg",new BigDecimal(2.2));
		
		normalAddition.add(testBook, 7);
		negativeAddition.add(testBook, 7);
		negativeAddition.add(testBook, -2);
		
		Book[] normalList = normalAddition.list("all");
		Book[] modifiedList = negativeAddition.list("all");

		assertEquals(normalList.length,modifiedList.length);
	}
	
	/*
	 * Tests so that books can be removed
	 */
	@Test
	public void testRemove() {
		
		listOfBooks nonRemoved = new listOfBooks();
		listOfBooks removed = new listOfBooks();
		Book testBook = new Book("Kim","Stromberg",new BigDecimal(2.2));
		
		nonRemoved.add(testBook, 7);
		removed.add(testBook, 7);
		removed.remove(testBook, 2);
		
		Book[] normalList = nonRemoved.list("all");
		Book[] reducedList = removed.list("all");

		assertNotEquals(normalList.length,reducedList.length);
		
		removed.remove(testBook, 5);
		Book[] zeroList = removed.list("all");
		
		assertEquals(zeroList.length,0);
	}
	
	/*
	 * Tests so that one can't remove more books than are available
	 */
	@Test
	public void testNegativeRemove(){
		
		listOfBooks list = new listOfBooks();
		Book testBook = new Book("Kim", "Stromberg", new BigDecimal(2.2));
		list.add(testBook, 2);
		list.remove(testBook, 7);
		Book[] testList = list.list("all");

		assertEquals(testList.length, 2);
	}
	
	/*
	 * Tests so that a book can be deleted from a listOfBooks
	 */
	@Test
	public void testDeleteBook() {
		listOfBooks deletedCart = new listOfBooks();
		listOfBooks notDeletedCart = new listOfBooks();
		
		Book testBook = new Book("Kim","Stromberg",new BigDecimal(2.2));
		
		deletedCart.add(testBook, 2);
		notDeletedCart.add(testBook, 2);
		deletedCart.deleteBook(testBook);
		
		Book[] emptyList = deletedCart.list("Kim Stromberg");
		Book[] oneList = notDeletedCart.list("Kim Stromberg");
		
		assertNull(emptyList);
		assertNotNull(oneList);
	}

	/*
	 * Tests so that buy returns the correct values depending on the
	 * 		state that occurs when a purchase is attempted.
	 */
	@Test
	public void testBuyResults() {

		listOfBooks buyer = new listOfBooks();
		listOfBooks seller = new listOfBooks();
		int[] results = new int[1];
		
		Book canBeBought = new Book("Kim","Stromberg",new BigDecimal(2.2));
		Book outOfStock = new Book("Tim", "Strom", new BigDecimal(2.3));
		Book doesNotExist = new Book("Jim", "Berg", new BigDecimal(3.2));
		
		buyer.add(canBeBought, 1);
		buyer.add(outOfStock, 1);
		buyer.add(doesNotExist, 1);
		seller.add(canBeBought, 1);
		seller.add(outOfStock, 0);
		
		Book[] bookCanBeBought = buyer.list("Kim Stromberg");
		Book[] bookOutOfStock = buyer.list("Tim Strom");
		Book[] bookDoesNotExist = buyer.list("Jim Berg");
		
		results = seller.buy(bookCanBeBought);
		assertEquals(results[0],0);
		
		results = seller.buy(bookOutOfStock);
		assertEquals(results[0],1);
		
		results = seller.buy(bookDoesNotExist);
		assertEquals(results[0],2);
	}
	
	/*
	 * Tests so that books go out of stock if purchased.
	 */
	@Test
	public void testBuyMultiplePurchase() {

		listOfBooks buyer = new listOfBooks();
		listOfBooks seller = new listOfBooks();
		int[] results = new int[3];
		
		Book canBeBought = new Book("Kim","Stromberg",new BigDecimal(2.2));
		
		buyer.add(canBeBought, 3);
		seller.add(canBeBought, 2);
		
		Book[] bookCanBeBought = buyer.list("Kim Stromberg");
		results = seller.buy(bookCanBeBought);
		
		assertEquals(results[0],0);
		assertEquals(results[1],0);
		assertEquals(results[2],1);
	}

	/*
	 * Tests so that totalPrice returns the correct value
	 */
	@Test
	public void testTotalPrice() {
		
		listOfBooks addingPrice = new listOfBooks();
		BigDecimal testValue = new BigDecimal(2.2);
		
		Book testBook = new Book("Kim","Stromberg", testValue);
		Book[] arrayOfBooks = {testBook, testBook};
		
		BigDecimal postTotalPrice = new BigDecimal(0.0);
		postTotalPrice = addingPrice.totalPrice(arrayOfBooks);
		testValue = testValue.add(testValue);
		
		assertEquals(postTotalPrice,testValue);	
		
	}

}
