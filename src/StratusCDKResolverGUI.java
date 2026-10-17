import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StratusCDKResolverGUI implements ActionListener {
    private final String PROGRAM_NAME = "Stratus_CDK_Resolver";
    private JFrame window;
    private JPanel panel;
    private BoxLayout layout;
    private JTextArea stratusTextBox, cdkTextBox, outputTextBox;
    private JLabel stratusTextLabel, cdkTextLabel, outputTextLabel;
    private JButton calculateButton;

    public StratusCDKResolverGUI()
    {
        // create a window and add stuff to it
        window = new JFrame(PROGRAM_NAME);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        // instantiate all components
        stratusTextBox = new JTextArea();
        cdkTextBox = new JTextArea();
        outputTextBox = new JTextArea();
        outputTextBox.setEditable(false);
        stratusTextLabel = new JLabel("Enter Stratus Parts:");
        cdkTextLabel = new JLabel("Enter CDK Parts:", JLabel.LEFT);
        calculateButton = new JButton("Calculate");
        outputTextLabel = new JLabel("Output:", JLabel.LEFT);

        // add all components to panel
        panel.add(stratusTextLabel);
        panel.add(stratusTextBox);
        panel.add(cdkTextLabel);
        panel.add(cdkTextBox);
        panel.add(outputTextLabel);
        panel.add(outputTextBox);
        panel.add(calculateButton);

	   // create ActionEvent and add to calculate button
	   calculateButton.addActionListener(this);

	   // create window and open it
        window.add(panel);
        window.setSize(1000, 1000);
        window.setVisible(true);
    }

    public static void main(String args[])
    {
        StratusCDKResolverGUI gui = new StratusCDKResolverGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	    calculateDiff();
    }

    public void calculateDiff()
    {
        String cdkPartsRawText, stratusPartsRawText, outputString;
        String[] cdkPartsTextLines, stratusPartsTextLines;
        Parts cdkParts, stratusParts;


        cdkPartsRawText = cdkTextBox.getText();
        stratusPartsRawText = stratusTextBox.getText();

        Scanner cdkScnr = new Scanner(cdkPartsRawText);
        Scanner stratusScnr = new Scanner(stratusPartsRawText);

        cdkPartsTextLines = Main.getLines(cdkScnr);
        stratusPartsTextLines = Main.getLines(stratusScnr);

        cdkParts = Main.getPartsCDK(cdkPartsTextLines);
        stratusParts = Main.getPartsStratus(stratusPartsTextLines);

	   PartsListComparator partsStructure = new PartsListComparator(cdkParts, stratusParts);
	   
	   Parts intersections = partsStructure.nonZeroIntersectionDiff();
	   // NOTE: partsList1 = cdk, partsList2 = stratus
	   Parts cdkDifference = partsStructure.partsList1SetDifference();
        Parts stratusDifference = partsStructure.partsList2SetDifference();

	   outputString = "";

        // print the difference on parts in both lists
        outputString += "Processed Stratus Parts Difference:\n";
        outputString += intersections.printAllString();
	   outputString += "\n";

        // print the parts on CDK not on Stratus
        outputString += "CDK Set Difference:\n";
        outputString += cdkDifference.printAllString();
        outputString += "\n";

        // print the parts on Stratus not on CDK
        outputString += "Stratus Set Difference:\n";
        outputString += stratusDifference.printAllString();
        outputString += "\n";

	   outputTextBox.setText(outputString);
    }

}
