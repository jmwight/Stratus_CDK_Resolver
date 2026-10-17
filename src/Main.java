import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;


class Main {
	public static void main(String[] args)
	{
		Scanner scnr = new Scanner(System.in);

		String[] lines;

		// get CDK Part array
		System.out.println("Enter CDK Parts:");
		lines = getLines(scnr);
		Parts partsCDK = getPartsCDK(lines);
		//System.out.println("Processed CDK Parts List:");
		//System.out.println();
		//Part.printParts(partsCDK);

		// get Stratus Part Array
		System.out.println("Enter Stratus Parts:");
		lines = getLines(scnr);
		Parts partsStratus = getPartsStratus(lines);
		//System.out.println("Processed Stratus Parts List:");
		//System.out.println();
		//Part.printParts(partsStratus);

		PartsListComparator partsStructure = new PartsListComparator(partsCDK, partsStratus);


		Parts intersections = partsStructure.nonZeroIntersectionDiff();
		// NOTE: partsList1 = cdk, partsList2 = stratus
		Parts cdkDifference = partsStructure.partsList1SetDifference();
		Parts stratusDifference = partsStructure.partsList2SetDifference();

		// print the difference on parts in both lists
		System.out.println("Processed Stratus Parts Difference:");
		intersections.printAll();
		System.out.println();

		// print the parts on CDK not on Stratus
		System.out.println("CDK Set Difference:");
		cdkDifference.printAll();
		System.out.println();

		// print the parts on Stratus not on CDK
		System.out.println("Stratus Set Difference:");
		stratusDifference.printAll();
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

		while(scnr.hasNextLine())
		{
			line = scnr.nextLine().trim();
			if(line.charAt(0) == '^')
				break;
			lines.add(line);
		}
		/*line = scnr.nextLine().trim();
		while(line.charAt(0) != '^')
		{
			lines.add(line);
			line = scnr.nextLine().trim();
		}*/

		return lines.toArray(new String[0]);
	}

	/**
	 * Take raw CDK terminal service daily log RO parts list copy and paste String array collected
	 * from input and convert into Parts array
	 * @param lines
	 * @return Parts array of all parts extracted
	 */
	public static Parts getPartsCDK(String[] lines)
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
		Parts parts = new Parts(lines.length + 5);

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
			parts.add(newPart);
		}

		return parts;
	}

	/**
	 * Take raw Stratus website copy and paste String array collected from input and convert into Parts array
	 * @param lines
	 * @return Parts array of all parts extracted
	 */
	public static Parts getPartsStratus(String[] lines)
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
		Parts parts = new Parts(lines.length + 5);

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

			parts.add(new Part(partName, partQuantity, partTotalPrice));
		}

		return parts;
	}
}
