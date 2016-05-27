import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Scanner;

public class bookStore {
	
	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		listOfBooks theBookStore = new listOfBooks();
		listOfBooks user = new listOfBooks();
		initializeBookStore(theBookStore);
		
		BigDecimal shoppingCartCost = new BigDecimal(0.0);
		int ioInput, quantity;
		String bt, an, searchString;
		boolean terminateSession = false;
		
		do {
			System.out.println("1) Add a new book to the store, or add more copies");
			System.out.println("2) List all the books in store");
			System.out.println("3) List specific book in store");
			System.out.println("4) Add book to shoppingCart");
			System.out.println("5) Buy available items in shoppingCart");
			System.out.println("6) Remove book from shoppingCart");
			System.out.println("7) Delete book from Store");
			System.out.println("8) List all books in shoppingCart");
			System.out.println("9) Cost of Current Cart");
			System.out.println("10) terminateSession");
			System.out.print("Enter choice [1-10]: ");
			ioInput = s.nextInt();
			switch (ioInput) {
				case 1: //Add a new book to the store, or add more copies
					System.out.println("Enter book title");
					bt = s.next();
					System.out.println("Enter author name");
					an = s.next();
					searchString = bt+" "+an;
					Book[] addQuery = theBookStore.list(searchString);
					if (addQuery == null){
						System.out.println("Book doesn't appear in registry");
						System.out.println("Enter new books price");
						String p = s.next();
						BigDecimal price = new BigDecimal(p.replaceAll( "," , "" ));
	                	System.out.println("Enter how many you would like to add");
	                	quantity = s.nextInt();
	                	theBookStore.add(new Book(bt,an,price), quantity);
	                }
	                else{
	                	System.out.println("Enter how many you would like to add");
	                	quantity = s.nextInt();
	                	theBookStore.add(addQuery[0], quantity);
	                    } 
	                break;
	            case 2: //List all the books in store
	        		Book[] listAllInStoreQuery = theBookStore.list("all");
	        		for(int i = 0; i<listAllInStoreQuery.length;i++){
	                	System.out.println(listAllInStoreQuery[i].getTitle()+";"+listAllInStoreQuery[i].getAuthor()+";"+listAllInStoreQuery[i].getPrice());
	                }
	                break;
	            case 3: //List specific book in store
	            	System.out.println("Enter book title");
	                bt = s.next();
	                System.out.println("Enter author name");
	                an = s.next();
	                searchString = bt+" "+an;
	                Book[] specificBookQuery = theBookStore.list(searchString);
	                if(specificBookQuery == null){
	                	System.out.println("The store does not have that book, thank you come again");
	                }else{
	                	System.out.println(specificBookQuery[0].getTitle()+";"+specificBookQuery[0].getAuthor()+";"+specificBookQuery[0].getPrice()+";"+specificBookQuery.length);
	                }
	                break;
	            case 4: //Add book to shoppingCart
	            	System.out.println("Enter book title");
	            	bt = s.next();
	            	System.out.println("Enter author name");
	            	an = s.next();
	            	searchString = bt+" "+an;
	            	Book[] addToCartQuery = theBookStore.list(searchString);
	            	if(addToCartQuery == null){
	            		System.out.println("That book is unfortunately not available");
	            	}else{
	            		System.out.println("Enter quantity");
	            		quantity = s.nextInt();
	            		user.add(addToCartQuery[0], quantity);
	            		BigDecimal itemCost = addToCartQuery[0].getPrice().multiply(new BigDecimal(quantity));
	            		shoppingCartCost = shoppingCartCost.add(itemCost);
	            	}
	            	break;
	            case 5: //Buy available items in shoppingCart
	            	Book[] fullCartQuery = user.list("all");
	            	int availableBooksInStore[] = theBookStore.buy(fullCartQuery);
	            	for(int i = 0; i<availableBooksInStore.length;i++){
	            		if( availableBooksInStore[i] == 0 ){
	            			shoppingCartCost = shoppingCartCost.subtract(fullCartQuery[i].getPrice());
	            			user.remove(fullCartQuery[i], 1);
	            		}else if( availableBooksInStore[i] == 1 ){
	            			System.out.println("That book is unfortunately out of stock, thank you come again");
	            		}else{
	            			System.out.println("That book does not exist in this shop");
	            		}
	            	}
	            	break;
	            case 6: //Remove book from shoppingCart
	            	System.out.println("Enter book title");
	                bt = s.next();
	                System.out.println("Enter author name");
	                an = s.next();
	                searchString = bt+" "+an;
	                Book[] removeFromCartQuery = theBookStore.list(searchString);
	                if(removeFromCartQuery == null){
	                	System.out.println("Book does not exist in cart");
	                }else{
	                	System.out.println("Enter quantity");
	                	quantity = s.nextInt();
	                	user.remove(removeFromCartQuery[0], quantity);
	                	BigDecimal moneyReturned = removeFromCartQuery[0].getPrice().multiply(new BigDecimal(quantity));
	                	shoppingCartCost = shoppingCartCost.subtract(moneyReturned);
	                }
	            case 7: //Delete book from Store
	            	System.out.println("Enter book title");
	            	bt = s.next();
	            	System.out.println("Enter author name");
	            	an = s.next();
	            	searchString = bt+" "+an;
	            	Book[] storeDeleteQuery = theBookStore.list(searchString);
	            	if(storeDeleteQuery == null){
	            		System.out.println("Target book does not exist prior to query");
	            	}else{
	            		theBookStore.deleteBook(storeDeleteQuery[0]);
	            	}
	            	break;
	            case 8: //List all books in shoppingCart
	            	Book[] listAllInCartQuery = user.list("all");
	                for(int i = 0; i<listAllInCartQuery.length;i++){
	                	System.out.println(listAllInCartQuery[i].getTitle()+";"+listAllInCartQuery[i].getAuthor()+";"+listAllInCartQuery[i].getPrice());
	                }
	                break;
	            case 9: //Cost of Current Cart
	            	System.out.println(shoppingCartCost.doubleValue());
	            	break;
	            case 10: //terminateSession
	            	System.out.println("Session Terminated");
	            	terminateSession = true;
	                break;
	            default: System.out.println("\nInvalid Choice");
	        }
	    }
	    while (!terminateSession);
		s.close();
	}
	
	/*
	 * Downloads a database and adds it to a list of books.
	 */
	public static void initializeBookStore(listOfBooks store){
		try{
			URL url = new URL("http://www.contribe.se/bookstoredata/bookstoredata.txt");
			Scanner s = new Scanner(url.openStream());
			
			while(s.hasNextLine()){
				String line[] = s.nextLine().split(";");
				BigDecimal price = new BigDecimal(line[2].replaceAll( "," , "" ));
				Book book = new Book(line[0],line[1],price);
				int quantity = Integer.parseInt(line[3]);
				store.add(book,quantity);
			}
			s.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
