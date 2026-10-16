import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class StratusCDKResolverGUI {
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

        window.add(panel);
        window.setSize(1000, 1000);
        window.setVisible(true);
    }

    public static void main(String args[])
    {
        StratusCDKResolverGUI gui = new StratusCDKResolverGUI();
    }

}
