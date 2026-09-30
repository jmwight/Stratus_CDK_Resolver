/**
 * Represents a part with part name (part number), quantity, and price and related methods
 * @author John Morgan Wight
 * @version 1.0
 */

import java.util.List;
import java.util.LinkedList;

public class Part
{
    String partName;
    int quantity;
    double totalPrice;
    Part(String partName, int quantity, double totalPrice)
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
     * Print an array of parts to console as text.
     * @param parts Array of parts to print
     */
    public static void printParts(Part[] parts)
    {
        System.out.println("Parts\tQuantity\tPrice");
        for(Part part : parts)
        {
            System.out.printf("%-12s\t%2d\t$%6.2f\n", part.partName, part.quantity, part.totalPrice);
        }

        System.out.println();
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
     * @return
     */
    public boolean equals(Part otherPart)
    {
        if(otherPart == null)
            return false;

        return partName.equals(otherPart.partName);
    }

    /**
     * Adds the part to the given parts list. If the part is already in the
     * given list, it adds the quantity and totalPrice of the matching part
     * in the list.
     * @param partToAdd Part you would like to add to the list
     * @param partsList List of parts you would like to add it to.
     * @return  returns false if part was already in the list and true if it
     * is a new part.
     */
    public static boolean addPart(Part partToAdd, List<Part> partsList)
    {
        for(Part partElement : partsList)
        {
            if(partToAdd.equals(partElement))
            {
                partElement.quantity += partToAdd.quantity;
                partElement.totalPrice += partToAdd.totalPrice;
                return false;
            }
        }
        partsList.add(partToAdd);
        return true;
    }

    /**
     * Generates a Part array of all Parts with matching name and difference in
     * quantity and price.
     * @param firstPartArr  set A
     * @param secondPartArr set B
     * @return                  Array of all parts (A ∩ B) intersecting on partName
     */
    public static Part[] intersectionDiff(Part[] firstPartArr, Part[] secondPartArr) {
        List<Part> partDiff = new LinkedList<Part>();

        int i, j;

        // iterate through the shortest list. Cross out
        Part[] shortArr = firstPartArr.length < secondPartArr.length ?
                firstPartArr : secondPartArr; // shortest list to iterate through

        Part[] longArr = firstPartArr.length > secondPartArr.length ?
                firstPartArr : secondPartArr; // shortest list to iterate through

        // keep a Linked List we can keep removing elements we already matched from other list, will make it faster
        List<Integer> longPartIndexList = new LinkedList<Integer>();
        // make linked list of other list we can cross out to make faster
        for(i = 0; i < longArr.length; ++i)
            longPartIndexList.add(i);

        int longPartIndexListIndex;
        for(i = 0; i < shortArr.length; ++i)
        {
            for(j = 0; j < longPartIndexList.size(); ++j)
            {
                longPartIndexListIndex = longPartIndexList.get(j);
                if(shortArr[i].partName.equals(longArr[longPartIndexListIndex].partName))
                {
                    Part diffPart = shortArr[i].minus(longArr[longPartIndexListIndex]);
                    partDiff.add(diffPart);
                    // remove that index on linked list so we don't have to check it again for match, we already matched it
                    longPartIndexList.remove(j);
                    break;
                }
            }
        }

        return partDiff.toArray(new Part[0]);
    }

    /**
     * Generates a Part array of all Parts with matching name and difference in
     * quantity and price.
     * @param firstPartList  set A
     * @param secondPartList set B
     * @return                  List of all parts (A ∩ B) intersecting on partName
     */
    public static List<Part> intersectionDiff(List<Part> firstPartList, List<Part> secondPartList)
    {
        List<Part> partDiff = new LinkedList<Part>();

        int i, j;

        // iterate through the shortest list. Cross out
        List<Part> shortList = firstPartList.size() < secondPartList.size() ?
                firstPartList : secondPartList; // shortest list to iterate through

        List<Part> longList = firstPartList.size() > secondPartList.size() ?
                firstPartList : secondPartList; // shortest list to iterate through

        // keep a Linked List we can keep removing elements we already matched from other list, will make it faster
        List<Integer> longPartIndexList = new LinkedList<Integer>();
        // make linked list of other list we can cross out to make faster
        for (i = 0; i < longList.size(); ++i)
            longPartIndexList.add(i);

        int longPartIndexListIndex;
        for (i = 0; i < shortList.size(); ++i) {
            for (j = 0; j < longPartIndexList.size(); ++j) {
                longPartIndexListIndex = longPartIndexList.get(j);
                if (shortList.get(i).partName.equals(longList.get(longPartIndexListIndex).partName)) {
                    Part diffPart = shortList.get(i).minus(longList.get(longPartIndexListIndex));
                    partDiff.add(diffPart);
                    // remove that index on linked list so we don't have to check it again for match, we already matched it
                    longPartIndexList.remove(j);
                    break;
                }
            }
        }

        return partDiff;
    }

    /**
     * Generates a Part array of all Parts with matching name and difference in
     * quantity and price.
     * @param intersectionDiff  Array of Set of parts (A ∩ B) intersecting on partName
     * @return                  Array of all parts (A ∩ B) where quantity > 0 and totalPrice > 0
     */
    public static Part[] nonZeroIntersectionDiff(Part[] intersectionDiff)
    {
        List<Part> nonZeroPartDiffList = new LinkedList<Part>();
        for(Part partElement : intersectionDiff)
        {
            if(partElement.totalPrice != 0 && partElement.quantity != 0)
                nonZeroPartDiffList.add(partElement);
        }

        return nonZeroPartDiffList.toArray(new Part[0]);
    }

    /**
     * Generates a Part array of all Parts with matching name and difference in
     * quantity and price.
     * @param intersectionDiff  List of Set of parts (A ∩ B) intersecting on partName
     * @return  List of all parts (A ∩ B) intersecting on partName where quantity > 0 and totalPrice > 0
     */
    public static List<Part> nonZeroIntersectionDiff(List<Part> intersectionDiff)
    {
        List<Part> nonZeroPartDiffList = new LinkedList<Part>();
        for(Part partElement : intersectionDiff)
        {
            if(partElement.totalPrice != 0 && partElement.quantity != 0)
                nonZeroPartDiffList.add(partElement);
        }

        return nonZeroPartDiffList;
    }

    /**
     * Remove the partToRemove from partsList
     * @param partToRemove  Part to remove from the list
     * @param partsList     List to remove it from
     * @return              True if part was successfully found and removed. False if it was not found.
     */
    public static boolean removePart(Part partToRemove, List<Part> partsList)
    {
        int i;
        for(i = 0; i < partsList.size(); ++i) {
            if (partsList.get(i).equals(partToRemove)) {
                partsList.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Makes an array containing the set A - (A ∩ B)
     * @param setA  set A
     * @param setB  set B
     * @param intersectionAB    set (A ∩ B) intersecting on partName
     * @return  A - (A ∩ B) on partName
     */
    public static Part[] setDifference(List<Part> setA, List<Part> setB, List<Part> intersectionAB)
    {
        List<Integer> indexListSetA = new LinkedList<Integer>();
        List<Integer> indexListSetB = new LinkedList<Integer>();
        Part[] setDifference;
        int i, j;

        for(i = 0; i < setB.size(); ++i)
            indexListSetB.add(i);

        for(i = 0; i < setA.size(); ++i)
        {
            Part setAElement = setA.get(i);
            boolean matchFound = false;
            for(j = 0; j < indexListSetB.size(); ++j)
            {
                Part intersectionSetElement = intersectionAB.get(indexListSetB.get(j));
                if(intersectionSetElement.equals(setAElement))
                {
                    indexListSetB.remove(j);
                    matchFound = true;
                    break;
                }
            }
            if(!matchFound)
            {
                indexListSetA.add(i);
            }
        }

        // generate parts numbers from index that are left on setA
        setDifference = new Part[indexListSetA.size()];
        for(i = 0; i < indexListSetA.size(); ++i)
        {
            setDifference[i] = setA.get(indexListSetA.get(i));
        }

        return setDifference;
    }
}
