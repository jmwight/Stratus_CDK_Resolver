package src;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//TODO: come up with a better name for this class
class PartListComparator
{
    private Parts partsList1;
    private Parts partsList2;
    private Parts intersectionDiff = null;
    private boolean listUpdated = true;

    /* constructors */
    PartListComparator()
    {
        this.partsList1 = new Parts();
        this.partsList2 = new Parts();
    }

    PartListComparator(Parts partsList1, Parts partsList2)
    {
        this.partsList1 = partsList1;
        this.partsList2 = partsList2;
        listUpdated = true;
    }

    public Parts getPartsList1() {return partsList1;}

    public Parts getPartsList2() {return partsList2;}

    public void setPartsList1(Parts partsList1)
    {
        this.partsList1 = partsList1;
        listUpdated = true;
    }

    public void setPartsList2(Parts partsList2)
    {
        this.partsList2 = partsList2;
        listUpdated = true;
    }

    PartListComparator(Part[] partsArray1, Part[] partsArray2)
    {
        this.partsList1 = new Parts(Arrays.asList(partsArray1));
        this.partsList2 = new Parts(Arrays.asList(partsArray2));
        intersectionDiff();
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
     * Gets the intersection if updated  of (cdkPartsList ∩ stratusPartsList)
     * @return  (cdkPartsList ∩ stratusPartsList)
     */
    protected List<Part> intersectionDiff() //intersectionDiff(List<Part> partsList1, List<Part> partsList2)
    {
        if(listUpdated || intersectionDiff == null)
        {
            /**
             * Generates a Part array of all Parts with matching name and difference in
             * quantity and price.
             * @param partsList1  set A
             * @param partsList2 set B
             * @return                  List of all parts (A ∩ B) intersecting on partName
             */
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
     * @return  (cdkPartsList ∩ stratusPartsList) where quantity > 0 and totalPrice > 0
     */
    public Part[] nonZeroIntersectionDiff()
    {
        intersectionDiff();
        return Part.nonZeroIntersectionDiff(intersectionDiff.toArray(new Part[0]));
    }

    /**
     * Makes an array containing the set cdkList - (cdkList ∩ stratusList)
     * @return  Array containing set cdkList - (cdkList ∩ stratusList)
     */
    public Part[] partsList1SetDifference()
    {
        intersectionDiff();
        return setDifference(partsList1, intersectionDiff);
    }

    /**
     * Makes an array containing the set stratusList - (stratusList ∩ cdkList)
     * @return  Array containing set stratusList - (stratusList ∩ cdkList)
     */
    public Part[] partsList2SetDifference()
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
    private Part[] setDifference(List<Part> setA, List<Part> setB)
    {
        return Part.setDifference(setA, setB, intersectionDiff);
    }
}