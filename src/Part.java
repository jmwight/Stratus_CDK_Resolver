/**
 * Represents a part with part name (part number), quantity, and price and related methods
 * @author John Morgan Wight
 * @version 1.0
 */

package src;

import java.util.List;
import java.util.LinkedList;

/**
 * A container class with some useful methods to hold information pertinent
 * to a part
 */
public class Part
{
	/** partName is the part number. We are using a String here because parts
	 *  are alphanumeric, not numeric */
	public String partName;
	/** The count of the given part */
	public int quantity;
	/** The price of all parts combined. I.e. quantity * price per part */
	public double totalPrice;

	/**
	 * Constructor for Part class
	 * @param partName		part number
	 * @param quantity		part count
	 * @param totalPrice	The total price of all parts. I.e. quantity * (price / part)
	 */
	public Part(String partName, int quantity, double totalPrice)
	{
		this.partName = partName;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	/**
	 * Print the part to console as text
	 */
	public void printPart()
	{
		System.out.printf("%-12s\t%2d\t$%6.2f\n", partName, quantity, totalPrice);
	}

	/**
	 * Part must have same partName! Returns part difference in quantity and totalPrice.
	 * @param part2 The other part that is subtracting from this part.
	 * @return Part with same name,
	 */
	public Part minus(Part part2)
	{
		Part diffPart;
		int quantityDiff;
		double priceDiff;

		//System.out.printf("%s%n", partName);
		quantityDiff = quantity - part2.quantity;
		//System.out.printf("Part 1 Quantity: %d\tPart 2 Quantity: %d\tquantityDiff: %d%n", quantity, part2.quantity, quantityDiff);
		priceDiff = totalPrice - part2.totalPrice;
		//System.out.printf("Part 1 Price: %.2f\tPart 2 Price: %.2f\tquantityPrice: %.2f%n", totalPrice, part2.totalPrice, priceDiff);

		diffPart = new Part(partName, quantityDiff, priceDiff);
		return diffPart;
	}

	/**
	 * Tests if partName inside this part and the part being compared are the same
	 * @param otherPart The other part we want to compare if it has the same name
	 * @return true if partName is the same and false if not
	 */
	public boolean equals(Part otherPart)
	{
		if(otherPart == null)
			return false;

		return partName.equals(otherPart.partName);
	}
}
