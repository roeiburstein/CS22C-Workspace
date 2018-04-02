package exercise2;/** * A driver that demonstrates the using an implementation of a Stack. *  * @author Frank M. Carrano * @author Timothy M. Henry * @version 4.0 UPDATED by C. Lee-Klawender */public class StackDriver {	public static void main(String[] args) {		System.out.println("Create a stack: ");		StackInterface<String> myArrayStack = new ArrayStack<String>(); // FILL IN FOR EX. 2.1		StackInterface<String> myLinkedStack = new LinkedStack<String>();		// System.out.println("Results of testing the FIRST stack (Array Stack):\n");		// testStackOperations(myArrayStack);		System.out.println("Results of testing the SECOND stack (Linked List Stack):\n");		testStackOperations(myLinkedStack);		System.out.println("\n\nDone.");	} // end main	public static void testStackOperations(StackInterface<String> myStack) {		System.out.println("isEmpty() returns " + myStack.isEmpty());		System.out.println("\nAdd to stack to get\n" + "Joe Jane Jill Jess Jim");		String[] strArray = { "Jim", "Jess", "Jill", "Jane", "Joe", "Jack" };		for (int i = 0; i < strArray.length; ++i) {			if (myStack.push(strArray[i]))				System.out.println("Pushed " + strArray[i] + " successfully");			else				System.out.println("UNABLE TO PUSH " + strArray[i]);		}		System.out.println("\nisEmpty() returns " + myStack.isEmpty());		// FOR LAB EXERCISE 2.1, ADD CODE HERE TO GET THE SIZE FROM THE Stack AND		// DISPLAY IT		System.out.println("\nThe Stack Size is: " + myStack.size());		System.out.println("\nTesting peek and pop:");		while (!myStack.isEmpty()) {			String top = myStack.peek();			System.out.println("\n" + top + " is at the top of the stack.");			top = myStack.pop();			System.out.println(top + " is removed from the stack.");		} // end while		System.out.print("\nThe stack should be empty: ");		System.out.println("isEmpty() returns " + myStack.isEmpty());		System.out.println("\n myStack.peek() returns ");		System.out.println(myStack.peek());		System.out.println("\n myStack.pop()  returns ");		System.out.println(myStack.pop());		System.out.println("\nEnd of Stack Test\n");	} // end testStackOperations	/*	 * EXECUTION RESULTS: Create a stack: Results of testing the FIRST stack:	 * 	 * isEmpty() returns true	 * 	 * Add to stack to get Joe Jane Jill Jess Jim Pushed Jim successfully Pushed	 * Jess successfully Pushed Jill successfully Pushed Jane successfully Pushed	 * Joe successfully UNABLE TO PUSH Jack	 * 	 * isEmpty() returns false	 * 	 * The Stack Size is: 5	 * 	 * Testing peek and pop:	 * 	 * Joe is at the top of the stack. Joe is removed from the stack.	 * 	 * Jane is at the top of the stack. Jane is removed from the stack.	 * 	 * Jill is at the top of the stack. Jill is removed from the stack.	 * 	 * Jess is at the top of the stack. Jess is removed from the stack.	 * 	 * Jim is at the top of the stack. Jim is removed from the stack.	 * 	 * The stack should be empty: isEmpty() returns true	 * 	 * myStack.peek() returns null	 * 	 * myStack.pop() returns null	 * 	 * End of Stack Test	 * 	 * 	 * 	 * Done.	 */} // end Driver