
/**
 * Program Name: HashSC.java
 * 
 * @author Roei Burstein 
 * Current Date: Wednesday, March 11, 2018 
 * Computer Operating System: Mac OS High Sierra 
 * Version 10.13.2 
 * Compiler: Eclipse Oxygen 4.7.0
 * <p>
 * Description: This program is the separate chaining implementation of a HashTable.
 * <p>
 */

import java.util.*;

public class HashSC<E> extends HashTable<E> {
	static final int INIT_TABLE_SIZE = 97;// Initial size of HashTable
	static final double INIT_MAX_LAMBDA = 1.5; // Initial ration between items in array and arraySize

	protected LList<E>[] mLists;
	protected int mSize; // number of items in HashTable
	protected int mTableSize; // size of the array
	protected double mMaxLambda; // maximum lambda value

	/**
	 * HashSC Constructor with table size, hasher object, and comparator object
	 * 
	 * @param tableSize
	 *            size of initial HashTable
	 * @param hasher
	 *            Hasher object (representing specific hashing methods)
	 * @param comparator
	 *            Comparator object
	 */
	public HashSC(int tableSize, Hasher<E> hasher, Comparator<E> comparator) {
		super(hasher, comparator);

		if (tableSize < INIT_TABLE_SIZE)
			mTableSize = INIT_TABLE_SIZE;
		else
			mTableSize = nextPrime(tableSize);

		allocateMListArray(); // uses mTableSize;
		mMaxLambda = INIT_MAX_LAMBDA;
	}

	/**
	 * Default constructor, runs constructor with initial table size
	 * 
	 * @param hasher
	 *            Hasher object (representing specific hashing methods)
	 * @param comparator
	 *            Comparator object
	 */
	public HashSC(Hasher<E> hasher, Comparator<E> comparator) {
		this(INIT_TABLE_SIZE, hasher, comparator);
	}

	/**
	 * Gets the entry in the HashTable
	 * 
	 * @param target
	 *            item to be retrieved in the HashTable
	 * @return data of target entry
	 */
	@Override
	public E getEntry(E target) {
		LList<E> theList = mLists[myHash(target)];
		Iterator<E> iter = theList.iterator();
		E currElem;

		for (int i = 0; iter.hasNext(); ++i) {
			currElem = iter.next();
			if (comparator.compare(currElem, target) == 0) {
				return currElem;
			}
			numCollisions++;
		}

		// not found
		return null;
	}

	/**
	 * Checks if HashTable contains parameter element
	 * 
	 * @param x
	 *            item to be checked in the HashTable
	 * @return true if item is in HashTable, false if item is not in HashTable
	 */
	public boolean contains(E x) {
		return (getEntry(x) != null);
	}

	/**
	 * Makes the HashTable empty
	 */
	public void makeEmpty() {
		int k, size = mLists.length;

		for (k = 0; k < size; k++)
			mLists[k].clear();
		mSize = 0;
	}

	/**
	 * Inserts item into HashTable
	 * 
	 * @param x
	 *            item to be pushed into HashTable
	 * @return true if insertion was successful, false otherwise
	 */
	public boolean insert(E x) {
		if (contains(x))
			return false;

		// not found so we insert
		LList<E> theList = mLists[myHash(x)];
		theList.add(x);

		longestCollisionRun = Math.max(longestCollisionRun, theList.getLength());

		// check load factor
		if (++mSize > mMaxLambda * mTableSize)
			rehash();

		return true;
	}

	/**
	 * Removes item from HashTable
	 * 
	 * @param x
	 *            item to be removed
	 * @return true if removal was successful, false otherwise
	 */
	public boolean remove(E x) {
		LList<E> theList = mLists[myHash(x)];
		Iterator<E> iter = theList.iterator();
		E currElem;

		for (int i = 0; iter.hasNext(); ++i) {
			currElem = iter.next();
			if (comparator.compare(currElem, x) == 0) {
				theList.remove(i + 1);
				--mSize;
				return true;
			}
			++numCollisions;
		}

		// not found
		return false;
	}

	/**
	 * Returns the number of elements in the HashTable
	 * 
	 * @return number of items in HashTable
	 */
	public int size() {
		return mSize;
	}

	/**
	 * Sets the maximum lambda variable
	 * 
	 * @param lam
	 *            lambda value to replace maximum lambda
	 * @return true if value is in range, false otherwise
	 */
	public boolean setMaxLambda(double lam) {
		if (lam < .1 || lam > 100.)
			return false;
		mMaxLambda = lam;
		return true;
	}

	/**
	 * Additional tester statistics
	 */
	public void displayStatistics() {
		System.out.println("\nIn the HashSC class:\n");
		System.out.println("Table Size = " + mTableSize);
		;
		System.out.println("Number of entries = " + mSize);
		System.out.println("Load factor = " + (double) mSize / mTableSize);
		System.out.println("Number of collisions = " + this.numCollisions);
		System.out.println("Longest Linked List = " + this.longestCollisionRun);
	}

	/**
	 * Change the size of HashTable array
	 */
	protected void rehash() {
		numCollisions = 0;
		longestCollisionRun = 0;

		// we save old list and size then we can reallocate freely
		LList<E>[] oldLists = mLists;
		int k, oldTableSize = mTableSize;
		Iterator<E> iter;

		mTableSize = nextPrime(2 * oldTableSize);

		// allocate a larger, empty array
		allocateMListArray(); // uses mTableSize;

		// use the insert() algorithm to re-enter old data
		mSize = 0;
		for (k = 0; k < oldTableSize; k++)
			for (iter = oldLists[k].iterator(); iter.hasNext();)
				insert(iter.next());
	}

	/**
	 * Hashing function for HashSC
	 * 
	 * @param x
	 *            item whose hash code is being returned
	 * @return hash code of parameter object
	 */
	protected int myHash(E x) {
		int hashVal;
		hashVal = hasher.hash(x) % mTableSize;

		if (hashVal < 0)
			hashVal += mTableSize;

		return hashVal;
	}

	/**
	 * Gets the next prime number
	 * 
	 * @param n
	 *            number whose next prime is being calculated
	 * @return prime number after the input one
	 */
	protected static int nextPrime(int n) {
		int k, candidate, loopLim;

		// loop doesn't work for 2 or 3
		if (n <= 2)
			return 2;
		else if (n == 3)
			return 3;

		for (candidate = (n % 2 == 0) ? n + 1 : n; true; candidate += 2) {
			// all primes > 3 are of the form 6k +/- 1
			loopLim = (int) ((Math.sqrt((double) candidate) + 1) / 6);

			// we know it is odd. check for divisibility by 3
			if (candidate % 3 == 0)
				continue;

			// now we can check for divisibility of 6k +/- 1 up to sqrt
			for (k = 1; k <= loopLim; k++) {
				if (candidate % (6 * k - 1) == 0)
					break;
				if (candidate % (6 * k + 1) == 0)
					break;
			}
			if (k > loopLim)
				return candidate;
		}
	}

	/**
	 * Allocates the array, fills HashTable array with blank HashEntry<E> objects
	 */
	private void allocateMListArray() {
		int k;

		mLists = new LList[mTableSize];
		for (k = 0; k < mTableSize; k++)
			mLists[k] = new LList<E>();
	}

	/**
	 * Prints out the table in a readable format
	 */
	@Override
	public void displayTable() {
		for (int i = 0; i < mLists.length; i++) {
			System.out.print(i + ": ");
			if (mLists[i].isEmpty()) {
				System.out.print("----");
			} else {
				LList<E> theList = mLists[i];
				Iterator<E> iter = theList.iterator();
				E currElem;

				for (int j = 0; iter.hasNext(); ++j) {
					currElem = iter.next();
					System.out.print(currElem);
					if (iter.hasNext())
						System.out.print(" -> ");
				}
			}
			System.out.println();
		}
	}

}