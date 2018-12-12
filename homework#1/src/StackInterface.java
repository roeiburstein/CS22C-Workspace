/** * Program Name: StackInterface.java * @author Roei Burstein * Current Date: Tuesday, January 23, 2018 * Computer Operating System: Mac OS High Sierra Version 10.13.2 * Compiler: Eclipse Oxygen 4.7.0 * <p> * Description: This program contains the stack interface for the stack object used in both ArrayStack and LinkedStack. * <p> */public interface StackInterface<T>{   /** Adds a new entry to the top of this stack.       @param newEntry  An object to be added to the stack. */   public boolean push(T newEntry);   /** Removes and returns this stack's top entry.       @return  The object at the top of the stack.                or null if the stack is empty*/   public T pop();   /** Retrieves this stack's top entry (without removing).       @return  The object at the top of the stack.                or null if the stack is empty. */   public T peek();   /** Detects whether this stack is empty.       @return  True if the stack is empty. */   public boolean isEmpty();
   /** Returns number of items in this stack
    @return: Number of items */
   public int size();
} // end StackInterface