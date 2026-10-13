import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class StratusCDKResolverGUI {
    private final String PROGRAM_NAME = "Stratus_CDK_Resolver";

    public StratusCDKResolverGUI()
    {
        // create a window and add stuff to it
        JFrame window = new JFrame(PROGRAM_NAME);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        // create components
        JTextArea stratusTextBox, cdkTextBox, outputTextBox;
        JLabel stratusTextLabel, cdkTextLabel, outputTextLabel;
        JButton calculateButton;

        // instantiate all components
        stratusTextBox = new JTextArea();
        cdkTextBox = new JTextArea();
        outputTextBox = new JTextArea();
        outputTextBox.setEditable(false);
        stratusTextLabel = new JLabel("Enter Stratus Parts:", JLabel.CENTER);
        cdkTextLabel = new JLabel("Enter CDK Parts:", JLabel.CENTER);
        calculateButton = new JButton("Calculate");
        outputTextLabel = new JLabel("Output:", JLabel.CENTER);

        // add all components to panel
        panel.add(stratusTextLabel);
        panel.add(stratusTextBox);
        panel.add(cdkTextLabel);
        panel.add(cdkTextBox);
        panel.add(outputTextLabel);
        panel.add(outputTextBox);
        panel.add(calculateButton);

        window.add(panel);
        window.setSize(1000, 1000);
        window.setVisible(true);
    }

    public static void main(String args[])
    {
        StratusCDKResolverGUI gui = new StratusCDKResolverGUI();
    }

}
