import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class Frame extends JFrame {
    //JPanel to locate components------------------Main
    private JPanel panelMain;
    private int width = 400;
    private int height = 600;

    //JPanel for input components------------------Input
    private JPanel panelInput;

    //JButton for submission
    private JButton button;
    String input;

    //JTextfield for input
    private JTextField textInput;

    //JList, JModel & JScroll to display output----Output
    private JList<String> listOutput;
    DefaultListModel<String> modelOutput;
    private JScrollPane scrollList;

    //JLabel to display
    JLabel labelMessage;
    private String labelText;

    //Fonts
    private Font textFont = new Font("Dialog", Font.PLAIN, 50);     //for JTextField
    private Font buttonFont = new Font("Dialog", Font.PLAIN, 21);   //for JButton
    private Font labelFont = new Font("Dialog", Font.PLAIN, 20);    //for JLabel
    private Font listFont = new Font("Monospaced", Font.BOLD, 20);  //for JList

    private Frame() {
        //Set up components
        initialise();

        //Set up frame
        pack();
        setTitle("Word Builder");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initialise(){
        //Main panel with components
        panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(width, height));
        panelMain.setBackground(Color.BLACK);
        panelMain.setLayout(new BorderLayout());
        add(panelMain);


        //Input panel with textfield & button
        panelInput = new JPanel();
        panelInput.setPreferredSize(new Dimension(width, 100));
        panelInput.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelMain.add(panelInput, BorderLayout.SOUTH);


        //Label to display message---------------------output
        labelMessage = new JLabel("Default Message", SwingConstants.CENTER);
        labelMessage.setPreferredSize(new Dimension(width, 100));
        //Text & design settings
        labelMessage.setOpaque(true);
        labelMessage.setBackground(Color.BLACK);
        labelMessage.setForeground(Color.GRAY);
        labelMessage.setFont(labelFont);
        labelText = "Enter your letters. More than 8 letters may cause program to slow down.";
        labelMessage.setText("<html><div style = 'text-align: center;'>" + labelText + "</html>");
        //add
        panelMain.add(labelMessage, BorderLayout.NORTH);


        //JList, Model, & JScroll Pane
        modelOutput = new DefaultListModel<>();
        listOutput = new JList<>(modelOutput);
        scrollList = new JScrollPane(listOutput);
        //Text
        listOutput.setFont(listFont);
        //add
        panelMain.add(scrollList, BorderLayout.CENTER);


        //Text field for input-------------------------input
        textInput = new JTextField();
        textInput.setPreferredSize(new Dimension(250, 100));
        //Text
        textInput.setFont(textFont);
        //add
        panelInput.add(textInput);


        //Button for submission
        button = new JButton("Submit");
        //Design
        button.setFont(buttonFont);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.GRAY);
        button.setPreferredSize(new Dimension(150, 100));
        //Submission
        button.addActionListener(actionEvent -> {
            if (isValidInput(textInput.getText()) && textInput.getText().length() > 2) {
                input = textInput.getText();
                textInput.setBackground(new Color(0, 204, 0));
                //labelMessage.setText("<html><div style = 'text-align: center;'>" + "You entered "+ input.length() + " words. Wait, the program is running..." + "</html>");
                labelMessage.setText("You entered * words. Wait...");
                labelMessage.setText("words: " + input.length());
                System.out.println(input.length());

                Finder find = new Finder(this);
                find.run(input.toLowerCase());
                repaint();
            } else {
                labelMessage.setText("Invalid input.");
                textInput.setBackground(new Color(204, 0, 0));
            }
        });
        //add
        panelInput.add(button);
    }

    private boolean isValidInput(String input){
        return input.length() < 20 && Pattern.matches("[а-яa-z]+", input);
    }

    public static void main(String[] args) {
        //Call frame
        new Frame();
    }
}