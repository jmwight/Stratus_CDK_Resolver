import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        String[] lines;

        // get CDK Part array
        System.out.println("Enter CDK Parts:");
        lines = getLines(scnr);
        Part[] partsCDK = getPartsCDK(lines);
        //System.out.println("Processed CDK Parts List:");
        //System.out.println();
        //Part.printParts(partsCDK);

        // get Stratus Part Array
        System.out.println("Enter Stratus Parts:");
        lines = getLines(scnr);
        Part[] partsStratus = getPartsStratus(lines);
        //System.out.println("Processed Stratus Parts List:");
        //System.out.println();
        //Part.printParts(partsStratus);

        PartsStructure partsStructure = new PartsStructure(partsCDK, partsStratus);


        Part[] intersections = partsStructure.nonZeroIntersectionDiff();
        Part[] cdkDifference = partsStructure.cdkSetDifference();
        Part[] stratusDifference = partsStructure.stratusSetDifference();

        // print the difference on parts in both lists
        System.out.println("Processed Stratus Parts Difference:");
        Part.printParts(intersections);
        System.out.println();

        // print the parts on CDK not on Stratus
        System.out.println("CDK Set Difference:");
        Part.printParts(cdkDifference);
        System.out.println();

        // print the parts on Stratus not on CDK
        System.out.println("Stratus Set Difference:");
        Part.printParts(stratusDifference);
        System.out.println();
    }

    /**
     * Gets all lines and returns a String array each element containing one line
     * @param scnr
     * @return Array of Part classes
     */
    public static String[] getLines(Scanner scnr)
    {
        ArrayList<String> lines = new ArrayList<>();
        String line = "";

        // read through all lines
        line = scnr.nextLine().trim();
        while(line.charAt(0) != '^')
        {
            lines.add(line);
            line = scnr.nextLine().trim();
        }

        return lines.toArray(new String[0]);
    }

    /**
     * Take raw CDK terminal service daily log RO parts list copy and paste String array collected
     * from input and convert into Parts array
     * @param lines
     * @return Parts array of all parts extracted
     */
    public static Part[] getPartsCDK(String[] lines)
    {
        // parse lines into part data classes
        String partName;
        double partTotalPrice;
        int partQuantity;
        int indexFirstSpace;
        int indexSecondSpace;
        String partQuantityStr;
        String partPriceStr;
        int i, j;
        //int partArrayIndex = 0;
        //Part[] parts = new Part[lines.length];
        List<Part> parts = new ArrayList<Part>();

        for(i = 0; i < lines.length; ++i)
        {
            // get part number string
            partName = lines[i].substring(0, lines[i].indexOf(' ')); // parse out part name
            partName = partName.replace("-", ""); // remove any dashes so just pure part number
            partName = partName.toUpperCase(); // probably not necessary, but to ensure upper case 100%

            // get part quantity
            indexFirstSpace = lines[i].indexOf(' ');
            indexSecondSpace = lines[i].indexOf(' ', indexFirstSpace + 1);
            partQuantityStr = lines[i].substring(indexFirstSpace + 1, indexSecondSpace);
            partQuantity = Integer.parseInt(partQuantityStr);

            // get part total price
            partPriceStr = lines[i].substring(lines[i].lastIndexOf(' '));
            partTotalPrice = Double.parseDouble(partPriceStr);

            // load into Part class
            Part newPart = new Part(partName, partQuantity, partTotalPrice);
            Part.addPart(newPart, parts);
        }

        return parts.toArray(new Part[0]);
    }

    /**
     * Take raw Stratus website copy and paste String array collected from input and convert into Parts array
     * @param lines
     * @return Parts array of all parts extracted
     */
    public static Part[] getPartsStratus(String[] lines)
    {
        String partName;
        double partTotalPrice;
        int partQuantity;
        int indexFirstTab;
        int indexSecondTab;
        String partQuantityStr;
        String partPriceStr;
        String partTotalPriceStr;
        int i;
        Part[] parts = new Part[lines.length];

        for(i = 0; i < lines.length; ++i)
        {
            // get part name
            partName = lines[i].substring(0, lines[i].indexOf('\t')).toUpperCase();

            // get part quantity
            indexFirstTab = lines[i].indexOf('\t');
            indexSecondTab = lines[i].indexOf('\t', indexFirstTab + 1);
            partQuantityStr = lines[i].substring(indexFirstTab + 1, indexSecondTab);
            partQuantity = Integer.parseInt(partQuantityStr);

            // get part total price
            partTotalPriceStr = lines[i].substring(lines[i].lastIndexOf('\t') + 2);
            partTotalPrice = Double.parseDouble(partTotalPriceStr);

            parts[i] = new Part(partName, partQuantity, partTotalPrice);
        }

        return parts;
    }
}

//TODO: come up with a better name for this class
class PartsStructure
{
    private List<Part> cdkList;
    private List<Part> stratusList;
    private List<Part> intersectionDiff = null;
    private boolean listUpdated = false;

    /* constructors */
    PartsStructure()
    {
        cdkList = new ArrayList<>();
        stratusList = new ArrayList<>();
    }

    PartsStructure(List<Part> cdkParts, List<Part> stratusParts)
    {
        cdkList = cdkParts;
        stratusList = stratusParts;
        intersectionDiff();
    }

    PartsStructure(Part[] cdkParts, Part[] stratusParts)
    {
        cdkList = Arrays.asList(cdkParts);
        stratusList = Arrays.asList(stratusParts);
        intersectionDiff();
    }

    /**
     * Adds part to CDKPart
     * @param partToAdd
     * @return true if successful, false if not
     */
    public boolean addCDKPart(Part partToAdd)
    {
        boolean added = Part.addPart(partToAdd, cdkList);
        listUpdated = true;
        return added;
    }

    /**
     * Adds part to CDKPart
     * @param partToAdd
     * @return true if successful, false if not
     */
    public boolean addStratusPart(Part partToAdd)
    {
        boolean added = Part.addPart(partToAdd, stratusList);
        listUpdated = true;
        return added;
    }

    /**
     * Removes a given part from CDK Parts list
     * @param partToRemove  Part to remove from CDK Parts list
     * @return  true if part found and removed, false if part not found in list
     */
    public boolean removeCDKPart(Part partToRemove)
    {
        listUpdated = true;
        return Part.removePart(partToRemove, cdkList);
    }

    /**
     * Removes a given part from CDK Parts list
     * @param partToRemove  Part to remove from CDK Parts list
     * @return  true if part found and removed, false if part not found in list
     */
    public boolean removeStratusPart(Part partToRemove)
    {
        listUpdated = true;
        return Part.removePart(partToRemove, stratusList);
    }

    /**
     * Gets the intersection if updated  of (cdkPartsList ∩ stratusPartsList)
     * @return  (cdkPartsList ∩ stratusPartsList)
     */
    protected List<Part> intersectionDiff() //intersectionDiff(List<Part> firstPartList, List<Part> secondPartList)
    {
        if(listUpdated || intersectionDiff == null) {
            /*List<Part> partDiff = new LinkedList<Part>();

            int i, j;

            // iterate through the shortest list. Cross out
            List<Part> shortList = stratusList.size() < cdkList.size() ?
                    stratusList : cdkList; // shortest list to iterate through

            List<Part> longList = stratusList.size() > cdkList.size() ?
                    stratusList : cdkList; // shortest list to iterate through

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

            intersectionDiff = partDiff;
            return partDiff;*/
            listUpdated = false;
            return intersectionDiff = Part.intersectionDiff(stratusList, cdkList);
        }
        else
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
    public Part[] cdkSetDifference()
    {
        intersectionDiff();
        return setDifference(cdkList, intersectionDiff);
    }

    /**
     * Makes an array containing the set stratusList - (stratusList ∩ cdkList)
     * @return  Array containing set stratusList - (stratusList ∩ cdkList)
     */
    public Part[] stratusSetDifference()
    {
        intersectionDiff();
        return setDifference(stratusList, intersectionDiff);
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
