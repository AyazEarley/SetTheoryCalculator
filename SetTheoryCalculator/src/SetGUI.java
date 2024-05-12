import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.jar.JarEntry;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;


public class SetGUI extends JFrame implements ActionListener {
    /*If you're wondering why I used such a uneccessary amount of JPanels,
    it's because it was the only way I could make the calculator arrange in 
    the way I wanted. Most of them only exist for tiny reasons like centering objects,
    or grouping a couple together, then grouping groups together.
     */
    private JPanel lower = new JPanel(); //contains input, transpose, invert

    //components for basic calculation
    private JPanel inputPanel = new JPanel();
    private JLabel inputLabel = new JLabel("Input set:");
    private TextField inputField = new TextField();
    private JButton calcButton = new JButton("Input");

    //components for transposition
    private JPanel transposePanel = new JPanel();
    private JLabel transposeLabel = new JLabel("Transpose set:");
    private String[] boxList = {"T1", "T2","T3","T4","T5","T6","T7","T8","T9","T10","T11"};
    private JComboBox transposeBox = new JComboBox<>(boxList);
    private JButton transposeButton = new JButton("Transpose");

    //components for inversion
    private JPanel invertPanel = new JPanel();
    private JLabel invertLabel = new JLabel("Invert set:");
    private String[] invboxList = {"T1I", "T2I","T3I","T4I","T5I","T6I","T7I","T8I","T9I","T10I","T11I"};
    private JComboBox invBox = new JComboBox<>(invboxList);
    private JButton invButton = new JButton("Invert");

    //panel contains the output panel and the instructions
    private JPanel centerPan = new JPanel();

    //panel contains all the output
    private JPanel output = new JPanel();

    private JPanel normPrime = new JPanel(); // panel contains just normal and prime form output
    private JPanel transInv = new JPanel(); // panel contains just transposition and inversion output

    //Output for normal form
    private JPanel normPanel = new JPanel();
    private JLabel normLabel = new JLabel("Normal Form:");
    private JLabel normOutput = new JLabel("   ");

    //Output for prime form
    private JPanel primePanel = new JPanel();
    private JLabel primeLabel = new JLabel("Prime form:");
    private JLabel primeOutput = new JLabel("   ");

    //Output for Transposition
    private JPanel transPanel = new JPanel();
    private JLabel transLabel = new JLabel("Transposed Set:");
    private JLabel transOutput = new JLabel("   ");

    //Output for Inversion
    private JPanel invPanel = new JPanel();
    private JLabel invLabel = new JLabel("  Inverted Set:");
    private JLabel invOutput = new JLabel("   ");

    //Title Label
    private JLabel title = new JLabel("PC Set Theory Calculator", SwingConstants.CENTER); // make sure it centers

    //Instructions
    private JPanel textCont = new JPanel();
    private JLabel instruct = new JLabel("Input sets separated by commas or spaces. You can use numbers, [10,11,0],  ", SwingConstants.CENTER);
    private JLabel instruct2 = new JLabel();


    private Set current;
    private Set transposed;
    private Set inverted;


    

    public SetGUI(){
        getContentPane().setLayout(new BorderLayout());
        lower.setLayout(new BorderLayout());
        

        //Constructs the panel containing the transposition button
        transposePanel.add(transposeLabel);
        transposePanel.add(transposeBox);
        transposePanel.add(transposeButton);
        transposeButton.addActionListener(this);
        lower.add(transposePanel, "Center");
        transposePanel.setBackground(Color.LIGHT_GRAY);

        // constructs the panel containing the basic calculation button
        inputPanel.add(inputLabel);
        inputField.setPreferredSize(new Dimension(150, 20));
        inputPanel.add(inputField);
        inputPanel.add(calcButton);
        calcButton.addActionListener(this);
        lower.add(inputPanel, "North");  
        inputPanel.setBackground(Color.LIGHT_GRAY);

        //constructs the panel containing inversion button
        invertPanel.add(invertLabel);
        invertPanel.add(invBox);
        invertPanel.add(invButton);
        invButton.addActionListener(this);
        lower.add(invertPanel,"South");
        invertPanel.setBackground(Color.LIGHT_GRAY);
        
        this.setTitle("PC Set Calculator");
        getContentPane().add(lower, "South");


        //All the output stuff 
        output.setLayout(new FlowLayout()); 
        normPanel.setLayout(new FlowLayout());  // normal form stuff
        normPrime.setLayout(new BorderLayout());
        normPanel.add(normLabel);
        normPanel.add(normOutput);
        normPrime.add(normPanel, "Center");
        normOutput.setText("          ");
        
        

        primePanel.setLayout(new FlowLayout()); // prime form stuff
        primePanel.add(primeLabel);
        primePanel.add(primeOutput);
        normPrime.add(primePanel, "South");
        primeOutput.setText("          ");

        
        centerPan.setLayout(new BorderLayout());
        output.add(normPrime);
        centerPan.add(output, "North");

        
        centerPan.add(instruct, "Center"); //add instructions
        instruct.setText("Input a set and press input to begin!");
        getContentPane().add(centerPan, "Center");

        transPanel.setLayout(new FlowLayout()); // transposition stuff
        transInv.setLayout(new BorderLayout()); 
        transPanel.add(transLabel);
        transPanel.add(transOutput);
        transOutput.setText("         ");

        
        invPanel.setLayout(new FlowLayout()); //inversion stuff
        invPanel.add(invLabel);
        invPanel.add(invLabel);
        invPanel.add(invOutput);
        invOutput.setText("        ");
        
        
        transInv.add(transPanel, "Center");
        transInv.add(invPanel, "South");
        output.add(transInv);
        //title stuff
        title.setFont(new Font("Courier", Font.BOLD, 24));
        getContentPane().add(title, "North");

        //Font f = new Font("Courier", Font.PLAIN, 20);
        //normLabel.setFont(f);
    }
    public void actionPerformed(ActionEvent e){

        String input = "";
        if(e.getSource() == calcButton){
            try{
                current = new Set(toArray(inputField.getText()));
                instruct.setText("Use the dropdown menus to transpose or invert the set");
            }catch(Exception f){ 
                instruct.setText("Something went wrong with you input, please try again :(");
                return;
            }
            
            normOutput.setText(Arrays.toString(current.getNormalForm()));
            current.createPrime();
            primeOutput.setText(Arrays.toString(current.getPrimeForm()));

            //remove the inversion and transposition output from the previous set the user inputted.
            transOutput.setText("       ");
            invOutput.setText("       ");
        }
        try{ //this try catch block is for if the user tries to transpose/invert the set before inputting it.

            //transposition combo box code
            if(e.getSource() == transposeButton){
                switch(transposeBox.getSelectedIndex()){
                    case 0: 
                        transposed = current.transpose(1);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break;
                    case 1:
                        transposed = current.transpose(2);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break;   
                    case 2:
                        transposed = current.transpose(3);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break;    
                    case 3:
                        transposed = current.transpose(4);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break;    
                    case 4:
                        transposed = current.transpose(5);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break;  
                    case 5:
                        transposed = current.transpose(6);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break; 
                    case 6:
                        transposed = current.transpose(7);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break;       
                    case 7:
                        transposed = current.transpose(8);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break;              
                    case 8:
                        transposed = current.transpose(9);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break;    
                    case 9:
                        transposed = current.transpose(10);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break;    
                    case 10:
                        transposed = current.transpose(11);
                        transOutput.setText(Arrays.toString(transposed.getNormalForm()));
                        break;
                }
                
            }
            //Inversion combo box code
            else if(e.getSource() == invButton){
                switch(invBox.getSelectedIndex()){
                    case 0:
                        inverted = current.invert(1);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;
                    case 1:
                        inverted = current.invert(2);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;
                    case 2:
                        inverted = current.invert(3);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;
                    case 3: 
                        inverted = current.invert(4);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;
                    case 4:
                        inverted = current.invert(5);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;
                    case 5:
                        inverted = current.invert(6);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;   
                    case 6:
                        inverted = current.invert(7);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;
                    case 7:
                        inverted = current.invert(8);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;
                    case 8:
                        inverted = current.invert(9);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;
                    case 9:
                        inverted = current.invert(10);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;
                    case 10:
                        inverted = current.invert(11);
                        invOutput.setText(Arrays.toString(inverted.getNormalForm()));
                        break;    
                }
                
            }
        }catch(Exception f){
            instruct.setText("You must input a set before trying to transpose/invert it.");
        }
        


    }
    public static int[] toArray(String s){ //Takes the string inputted by user and makes it an array
        
        
        s = s.replaceAll(",", " "); //ignore commas
        s = s.replaceAll("t", "10" ); // t = 10 and e = 11, these lines account for that
        s = s.replaceAll("e", "11" );

         //each set of three lines replaces a letter name with a number name, and its flat and sharp accidental
        s = s.replaceAll("C#", "1");
        s = s.replaceAll("Cb", "11");
        s = s.replaceAll("C", "0");

        
        s = s.replaceAll("Db", "1");
        s = s.replaceAll("D#", "3");
        s = s.replaceAll("D", "2"); 

        
        s = s.replaceAll("Eb", "3");
        s = s.replaceAll("E#", "5");
        s = s.replaceAll("E", "4"); 

        
        s = s.replaceAll("Fb", "4");
        s = s.replaceAll("F#", "6");
        s = s.replaceAll("F", "5"); 

        
        s = s.replaceAll("Gb", "6");
        s = s.replaceAll("G#", "8");
        s = s.replaceAll("G", "7"); 

         
        s = s.replaceAll("Ab", "8");
        s = s.replaceAll("A#", "10");
        s = s.replaceAll("A", "9");

        
        s = s.replaceAll("Bb", "10");
        s = s.replaceAll("B#", "0");
        s = s.replaceAll("B", "11"); 

        ArrayList<Integer> temp = new ArrayList<Integer>();
        int current;
        Scanner scan = new Scanner(s);
        while(scan.hasNext()){
            current = scan.nextInt();
            if(!(temp.contains(current))){ // don't add a number if it's already in the arraylist (repeated pitches are useless)
                temp.add(current);
            }
            
        }
        scan.close();

        
        
        int[] ret = new int[temp.size()]; //Takes the arraylist and puts it into an array of the appropriate size
        for(int i = 0; i < temp.size(); i++){ 
            ret[i] = temp.get(i);
        }
        


        return ret;
 
    }
    
    public static boolean contained(int[] array, int num){ // takes an array and a number, checks if it is contained 

        for( int i = 0; i < array.length; i++){
            if(array[i] == num){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args){
        SetGUI gui = new SetGUI();
        gui.setVisible(true);
        gui.setSize(512,384);

        
    }
}
