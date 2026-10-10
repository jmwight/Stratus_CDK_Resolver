package src;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//TODO: come up with a better name for this class
class PartsListComparator
{
    private Parts partsList1;
    private Parts partsList2;
    private Parts intersectionDiff = null;
    private boolean listUpdated = true;

    /* constructors */
    PartsListComparator()
    {
        this.partsList1 = new Parts();
        this.partsList2 = new Parts();
    }

    PartsListComparator(Parts partsList1, Parts partsList2)
    {
        this.partsList1 = partsList1;
        this.partsList2 = partsList2;
        listUpdated = true;
    }

    PartsListComparator(Part[] partsArray1, Part[] partsArray2)
    {
        this.partsList1 = new Parts(Arrays.asList(partsArray1));
        this.partsList2 = new Parts(Arrays.asList(partsArray2));
        listUpdated = true;
    }

    /**
     * Gets partsList1 in class
     * @return partsList1
     */
    public Parts getPartsList1() {return partsList1;}

    /**
     * Gets partsList2 in class
     * @return partsList2
     */
    public Parts getPartsList2() {return partsList2;}

    /**
     * Change the Parts partsList1 to another Parts list
     * @param partsList1 new Parts partsList1
     */
    public void setPartsList1(Parts partsList1)
    {
        this.partsList1 = partsList1;
        listUpdated = true;
    }

    /**
     * Change the Parts partsList1 to another Parts list
     * @param partsList2 new Parts partsList1
     */
    public void setPartsList2(Parts partsList2)
    {
        this.partsList2 = partsList2;
        listUpdated = true;
    }

    /**
     * Adds part to CDKPart
     * @param partToAdd
     * @return true if successful, false if not
     */
    public boolean addPartList1(Part partToAdd)
    {
        boolean added = partsList1.add(partToAdd);
        listUpdated = true;
        return added;
    }

    /**
     * Adds part to CDKPart
     * @param partToAdd
     * @return true if successful, false if not
     */
    public boolean addPartList2(Part partToAdd)
    {
        boolean added = partsList2.add(partToAdd);
        listUpdated = true;
        return added;
    }

    /**
     * Removes a given part from CDK Parts list
     * @param partToRemove  Part to remove from CDK Parts list
     * @return  true if part found and removed, false if part not found in list
     */
    public boolean removePartList1(Part partToRemove)
    {
        listUpdated = true;
        return partsList1.remove(partToRemove);
    }

    /**
     * Removes a given part from CDK Parts list
     * @param partToRemove  Part to remove from CDK Parts list
     * @return  true if part found and removed, false if part not found in list
     */
    public boolean removeStratusPart(Part partToRemove)
    {
        listUpdated = true;
        return partsList2.remove(partToRemove);
    }


    /**
     * Generates a Parts list of all Parts with matching name and a difference in
     * quantity and price.
     * @return               List of all parts (A ∩ B) intersecting on partName and their difference in quantity and price
     */
    protected Parts intersectionDiff()
    {
        if(listUpdated || intersectionDiff == null)
        {
            List<Part> partDiff = new LinkedList<Part>();

            int i, j;

            // iterate through the shortest list. Cross out
            List<Part> shortList = partsList1.size() < partsList2.size() ?
                    partsList1 : partsList2; // shortest list to iterate through

            List<Part> longList = partsList1.size() > partsList2.size() ?
                    partsList1 : partsList2; // shortest list to iterate through

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

            listUpdated = false;
            intersectionDiff = new Parts(partDiff);
        }

        return intersectionDiff;
    }

    /**
     * Gets the intersection if updated  of (cdkPartsList ∩ stratusPartsList) non-zero
     * @return  (partsList1 ∩ partsList2) where quantity > 0 and totalPrice > 0
     */
    public Parts nonZeroIntersectionDiff()
    {
        intersectionDiff();
        Parts zeroIntersectionDiff = new Parts(intersectionDiff.size());
        for(Part part : intersectionDiff)
        {
            if(part.quantity != 0 || part.totalPrice != 0.0)
                zeroIntersectionDiff.add(part);
        }
        return zeroIntersectionDiff;
    }

    /**
     * Makes an array containing the set cdkList - (cdkList ∩ stratusList)
     * @return  Array containing set cdkList - (cdkList ∩ stratusList)
     */
    public Parts partsList1SetDifference()
    {
        intersectionDiff();
        return setDifference(partsList1, intersectionDiff);
    }

    /**
     * Makes an array containing the set stratusList - (stratusList ∩ cdkList)
     * @return  Array containing set stratusList - (stratusList ∩ cdkList)
     */
    public Parts partsList2SetDifference()
    {
        intersectionDiff();
        return setDifference(partsList2, intersectionDiff);
    }

    /**
     * Makes an array containing the set A - (A ∩ B)
     * @param setA  set A (either stratusList or cdkList)
     * @param setB  set B (either cdkList or stratusList)
     * @return  A - (A ∩ B) on partName
     */
    private Parts setDifference(List<Part> setA, List<Part> setB)
    {
        //return Part.setDifference(setA, setB, intersectionDiff);

        List<Integer> indexListSetA = new LinkedList<Integer>();
        List<Integer> indexListSetB = new LinkedList<Integer>();
        Parts setDifference;
        int i, j;

        for(i = 0; i < setB.size(); ++i)
            indexListSetB.add(i);

        for(i = 0; i < setA.size(); ++i)
        {
            Part setAElement = setA.get(i);
            boolean matchFound = false;
            for(j = 0; j < indexListSetB.size(); ++j)
            {
                Part intersectionSetElement = intersectionDiff.get(indexListSetB.get(j));
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
        setDifference = new Parts(indexListSetA.size());
        for(i = 0; i < indexListSetA.size(); ++i)
        {
            setDifference.add(setA.get(indexListSetA.get(i)));
        }

        return setDifference;
    }
}