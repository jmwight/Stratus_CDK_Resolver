import java.util.ArrayList;
import java.util.List;

/**
 * A List container that has modifications specifically for the Part class
 * @author John Morgan Wight
 * @version 2.0
 */
public class Parts extends ArrayList<Part> implements List<Part>
{
	private static final int INITIAL_CAPACITY = 40;

	/**
	 * Parts class default constructor
	 */
	public Parts()
	{
		super(INITIAL_CAPACITY);
	}

	/**
	 * Parts class constructor to use if you know how large the list will be in advance
	 * @param	initCapacity	The starting number of elements of Parts
	 */
	public Parts(int initCapacity) {super(initCapacity);}

	/**
	 * Parts class constructor use if we already have a List of Part
	 * @param	parts	List of Part
	 */
	public Parts(List<Part> parts)
	{
		super(parts);
	}

	/**
	 * Adds the part to the given parts list. If the part is already in the
	 * given list, it adds the quantity and totalPrice of the matching part
	 * in the list.
	 * @param partToAdd Part you would like to add to the list
	 * @return  returns false if part was already in the list and true if it
	 * is a new part.
	 */
	@Override
	public boolean add(Part partToAdd)
	{
		for(Part partElement : this)
		{
			if(partToAdd.equals(partElement))
			{
				partElement.quantity += partToAdd.quantity;
				partElement.totalPrice += partToAdd.totalPrice;
				return false;
			}
		}
		super.add(partToAdd);
		return true;
	}

	/**
	 * Remove the partToRemove from partsList
	 * @param partToRemove  Part to remove from the list
	 * @return			  True if part was successfully found and removed. False if it was not found.
	 */
	public boolean remove(Part partToRemove)
	{
		int i;
		for(i = 0; i < this.size(); ++i) {
			if (get(i).equals(partToRemove)) {
				remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the last index of a given part.
	 * @param partNameToFind	Part to find (the part of the matching name)
	 * @return			  Index in Parts
	 */
	public int indexOf(String partNameToFind)
	{
		int i;
		for(i = 0; i < size(); ++i)
		{
			if(get(i).partName.equals(partNameToFind))
				return i;
		}
		return -1;
	}

	/**
	 * Get the last index of a given part.
	 * @param partToFind	Part to find (the part of the matching name)
	 * @return			  Index in Parts
	 */
	public int indexOf(Part partToFind)
	{
		return indexOf(partToFind.partName);
	}

	/**
	 * Get the last index of a given part.
	 * @param partNameToFind	Part name string to find (the part of the matching name)
	 * @return				  Index in Parts
	 */
	public int lastIndexOf(String partNameToFind)
	{
		int i;
		for(i = size() - 1; i >= 0; --i)
		{
			if(get(i).partName.equals(partNameToFind))
				return i;
		}
		return -1;
	}

	/**
	 * Get the index of a given part.
	 * @param partToFind	Part to find (the part of the matching name)
	 * @return			  Index in Parts
	 */
	public int lastIndexOf(Part partToFind)
	{
		return lastIndexOf(partToFind.partName);
	}

	/**
	 * Check if Parts contains a given part.
	 * @param	partNameToSearch	The string represening the part number to check.
	 * @return				True if found, false if not found.
	 */
	public boolean contains(String partNameToSearch)
	{
		return indexOf(partNameToSearch) != -1;
	}

	/**
	 * Print all parts in Parts to console as text.
	 */
	public void printAll()
	{
		int i;

		System.out.println("Parts\tQuantity\tPrice");
		for(Part part : this)
		{
			System.out.printf("%-12s\t%2d\t$%6.2f\n", part.partName, part.quantity, part.totalPrice);
		}

		System.out.println();
	}

	//TODO: add comparators for part quantity, part total price and part name
}
