/*
 * @author    Greg Prawdzik
 * @since    5/9/2019
 * Program: Final.java
 * Descr:   This is a JavaFX Banking System that holds a record of users names,
 *          account numbers, balance, and type of account.  Users can withdraw
 *          or deposit money and create new checking and savings accounts.
 */
package pkgfinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Gregory Prawdzik
 */
public class Final extends Application {
    int x;
    /**
     * 
     * @param primaryStage
     * @throws IOException 
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Create input and output for txt file.
        File f = new File("final.txt");//create a File variable
	ArrayList<Account> acctList = new ArrayList<>();
        
	if(f.exists()){// check to see if created file exists
            System.out.println("File exists.");
	}else{
            System.out.println("File not found!");
            File file = new File("final.txt");// initialize variable
			
            try {
		file.createNewFile();// create file if it does not exist
            } catch (IOException e) {
		//  catch block
		e.printStackTrace();
            }
            System.out.println("File created.");		
	}

        try (BufferedReader bReader = new BufferedReader(
                new FileReader("final.txt"))) {
            String line;
            //Looping the read block until all lines in the file are read.
            while ((line = bReader.readLine()) != null) {
                // Splitting the content of tabbed separated line
                String datavalue[] = line.split("\t");

                String fstNm = datavalue[0];
                String lstNm = datavalue[1];
                String acctID = datavalue[2];
                String acctType = datavalue[3];
                String balance = datavalue[4];
                
                acctList.add(new Account(fstNm,lstNm,acctID,acctType, balance));
                // Printing the value read from file to the console
            }
            bReader.close();
        }
    
        x = 0;
        GridPane pane = new GridPane();
        pane.setHgap(5.5);
        pane.setVgap(5.5);
        
        TextField tfRecords = new TextField();
        
        TextField tfName = new TextField(acctList.get(x).getFirstName()+" "+acctList.get(x).getLastName());
        pane.add(new Label("Account Holder"),0,0);
        pane.add(tfName,1,0);
        
        TextField tfSearchName = new TextField();
        pane.add(tfSearchName,2,0);
        Button btSearch = new Button("Search by First Name");
        pane.add(btSearch,3,0);
        btSearch.setOnAction((ActionEvent event) -> {
            String key = tfSearchName.getText();
            for (int i = 0; i < acctList.size(); i++){
                if(key.equals(acctList.get(i).getFirstName())){
                    // display record number of found match
                    tfSearchName.setText("Record number " + (i+1));
                } 
            };
        });
        
        TextField tfID = new TextField(acctList.get(x).getAcctNum());
        pane.add(new Label("Account Number"),0,1);
        pane.add(tfID,1,1);

        TextField tfSearchID = new TextField();
        pane.add(tfSearchID,2,1);
        
        Button btSearchID = new Button("Search by Account Number");
        pane.add(btSearchID,3,1);
        btSearchID.setOnAction((ActionEvent event) -> {
            String key = tfSearchID.getText();
            for (int i = 0; i < acctList.size(); i++){
                if(key.equals(acctList.get(i).getAcctNum())){
                     // display record number of found match
                    tfSearchID.setText("Record number " + (i+1));
                }
            };
        });
        
        TextField tfType = new TextField(acctList.get(x).getAcctType());
        pane.add(new Label("Account Type"),0,2);
        pane.add(tfType,1,2);
        
        TextField tfBalance = new TextField(acctList.get(x).getBalance());
        pane.add(new Label("Balance             $"),0,3);
        pane.add(tfBalance,1,3);
        
        Button btReport = new Button("Print Report");
        pane.add(btReport, 2,2);
        btReport.setOnAction((ActionEvent event) -> {
           
                File r = new File("report.txt");//create a File variable
                
                if(r.exists()){// check to see if created file exists
                    System.out.println("File exists.");
                }else{
                    System.out.println("File not found!");
                    File file = new File("report.txt");// initialize variable
                    
                    try {
                        file.createNewFile();// create file if it does not exist
                    } catch (IOException e) {
                        //  catch block
                        e.printStackTrace();
                    }
                    System.out.println("File created.");
                }
                 try {
                // Creates account report     
                PrintWriter output;
                output = new PrintWriter("report.txt");
                output.println("Name:             "+    acctList.get(x).getFirstName() + " " +
                        acctList.get(x).getLastName());
                output.println("Account Type:     "+  acctList.get(x).getAcctType());
                output.println( "Account Number:   "+ acctList.get(x).getAcctNum());
                output.println("Balance:          $"+acctList.get(x).getBalance());

                output.close();
                ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "report.txt");
                pb.start();
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Final.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Final.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        TextField tfDeposit = new TextField();
        pane.add(new Label("Deposit"),0,6);
        pane.add(tfDeposit,1,6);
        Button btDeposit = new Button("Deposit");
        pane.add(btDeposit, 2,6);
        btDeposit.setOnAction((ActionEvent event) -> {
            try
            {
               //adds value to account
                Double.parseDouble(tfDeposit.getText());
                Double dblDeposit = new Double(tfDeposit.getText());
                
                if(dblDeposit <= 0){
                    tfDeposit.setText("Enter a positive number!!!");
                }else{
                    try { 
                        acctList.get(x).deposit(dblDeposit);
                    } catch (NegativeAmount ex) {
                        Logger.getLogger(Final.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tfBalance.setText(acctList.get(x).getBalance());               
                }
            }
            catch(NumberFormatException e)
            {
                tfDeposit.setText("Enter a  number!!!");
            }
            PrintWriter output = null;
            try {
                output = new PrintWriter("final.txt");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Final.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int i = 0; i < acctList.size(); i++){
                // Writes formated line data to txt file.
                String type;
                if(acctList.get(i).getAcctType()== "Checking"){
                    type = "0";
                }else{
                    type = "1";
                }
                output.println(acctList.get(i).getFirstName() + "\t" +
                       acctList.get(i).getLastName() + "\t" +
                       acctList.get(i).getAcctNum() + "\t" +
                       type +"\t" +
                       acctList.get(i).getBalance());
            }
            output.close();    
        });
        
        TextField tfWithdrawl = new TextField();
        pane.add(new Label("Withdrawl"),0,7);
        pane.add(tfWithdrawl,1,7);
        Button btWithdrawl = new Button("Withdrawl");
        pane.add(btWithdrawl, 2,7);
        btWithdrawl.setOnAction ((ActionEvent event) -> {
            try
            {
                //reduces value of account balance
                Double.parseDouble(tfWithdrawl.getText());
                Double dblBalance = Double.parseDouble(acctList.get(x).getBalance());
                Double dblWithdrawl = new Double(tfWithdrawl.getText());
                DecimalFormat df = new DecimalFormat("#.00");
                df.format(dblWithdrawl);
                df.format(dblBalance);
                if(dblWithdrawl <= 0){
                    tfWithdrawl.setText("Enter a positive number!!!");
                }else{
                    if( dblBalance > dblWithdrawl ){
                        try{
                            acctList.get(x).withdrawl(dblWithdrawl); 
                            tfBalance.setText(acctList.get(x).getBalance()); 
                        } catch (NegativeAmount ex) {
                            Logger.getLogger(Final.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InsufficientFunds ex){
                            ex.getMessage();
                        }
                    }else{
                         tfWithdrawl.setText("You do not have enough money.");
                    }
                }
            }
            catch(NumberFormatException e)
            {
                tfWithdrawl.setText("Enter a  number!!!");
            }
                        PrintWriter output = null;
            try {
                output = new PrintWriter("final.txt");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Final.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int i = 0; i < acctList.size(); i++){
                 //Writes formated line data to txt file. 
                String type;
                if(acctList.get(i).getAcctType()== "Checking"){
                    type = "0";
                }else{
                    type = "1";
                }
                output.println(acctList.get(i).getFirstName() + "\t" +
                       acctList.get(i).getLastName() + "\t" +
                       acctList.get(i).getAcctNum() + "\t" +
                       type +"\t" +
                       acctList.get(i).getBalance());
            }
            output.close();
        });
        
        String recNum;
        recNum =Integer.toString(x + 1);
        TextField tfNUM = new TextField(recNum);
        pane.add(new Label("Record Number" ),0,4);
        pane.add(tfNUM,1,4);
        Integer records =acctList.size() ;
        tfRecords.setText(Integer.toString(records)); 
        pane.add(new Label("of" ),2,4);
        pane.add(tfRecords,3,4);
        
        Button btPrevious = new Button("<-- Previous");
        pane.add(btPrevious, 1,5);
        btPrevious.setOnAction((ActionEvent event) -> {
            if(x > 0){
                x--;
            }
            tfName.setText(acctList.get(x).getFirstName()+" "+acctList.get(x).getLastName());
            tfID.setText(acctList.get(x).getAcctNum());
            tfType.setText(acctList.get(x).getAcctType());
            tfBalance.setText(acctList.get(x).getBalance());
            tfNUM.setText(Integer.toString(x + 1));
        });
        TextField tfNewFirst = new TextField();
        pane.add(new Label("New Account First Name"),0,8);
        pane.add(tfNewFirst,1,8);
        
        TextField tfNewLast = new TextField();
        pane.add(new Label("New Account Last Name"),0,9);
        pane.add(tfNewLast,1,9);
        
        Button btAddChecking = new Button("Add Checking");
        pane.add(btAddChecking, 2,9);
        btAddChecking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if("".equals(tfNewFirst.getText()) | "".equals(tfNewLast.getText()) 
                        | "Enter a name.".equals(tfNewFirst.getText()) |
                        "Enter a name.".equals(tfNewLast.getText()) ){
                    tfNewFirst.setText("Enter a name.");
                    tfNewLast.setText("Enter a name.");
                }else{
                    acctList.add(new Account());

                    x = (acctList.size() - 1);
                    acctList.get(x).setFirstName(tfNewFirst.getText());
                    acctList.get(x).setLastName(tfNewLast.getText());
                    Integer acctNum1;
                    Integer acctNum2;
                    acctNum1 = getRandomInteger(10000, 99999);
                    acctNum2 = getRandomInteger(10000, 99999);
                    String acctNum = acctNum1.toString() + acctNum2.toString();
                    acctList.get(x).setAcctNum(acctNum);
                    acctList.get(x).setAcctType("0");
                    tfName.setText(acctList.get(x).getFirstName()+" "+acctList.get(x).getLastName());
                    tfID.setText(acctList.get(x).getAcctNum());
                    tfType.setText(acctList.get(x).getAcctType());
                    tfBalance.setText(acctList.get(x).getBalance());
                    tfNUM.setText(Integer.toString(x + 1));
                    Integer records = acctList.size();
                    tfRecords.setText(records.toString());
                }
            PrintWriter output = null;
            try {
                output = new PrintWriter("final.txt");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Final.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int i = 0; i < acctList.size(); i++){
                // Writes formated line data to txt file.
                String type;
                if("Checking".equals(acctList.get(i).getAcctType())){
                    type = "0";
                }else{
                    type = "1";
                }
                output.println(acctList.get(i).getFirstName() + "\t" +
                       acctList.get(i).getLastName() + "\t" +
                       acctList.get(i).getAcctNum() + "\t" +
                       type +"\t" +
                       acctList.get(i).getBalance());
            }
            output.close();
            }
            /**
             * 
             * @param i
             * @param i0
             * @return random integer between i and i0
             */
            private int getRandomInteger(int i, int i0) {
                return ((int) (Math.random()*(i0 - i))) + i;
            }
        });
        
        Button btAddSavings = new Button("Add Savings");
        pane.add(btAddSavings, 3,9);
        btAddSavings.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * 
             * @param event 
             */
            @Override
            public void handle(ActionEvent event) {
                if("".equals(tfNewFirst.getText()) | "".equals(tfNewLast.getText()) 
                        | "Enter a name.".equals(tfNewFirst.getText()) |
                        "Enter a name.".equals(tfNewLast.getText()) ){
                    tfNewFirst.setText("Enter a name.");
                    tfNewLast.setText("Enter a name.");
                }else{
                    acctList.add(new Account());
                    x = (acctList.size() - 1);
                    acctList.get(x).setFirstName(tfNewFirst.getText());
                    acctList.get(x).setLastName(tfNewLast.getText());
                    Integer acctNum1;
                    Integer acctNum2;
                    acctNum1 = getRandomInteger(10000, 99999);
                    acctNum2 = getRandomInteger(10000, 99999);
                    String acctNum = acctNum1.toString() + acctNum2.toString();
                    acctList.get(x).setAcctNum(acctNum);
                    acctList.get(x).setAcctType("1");
                    tfName.setText(acctList.get(x).getFirstName() + " " + acctList.get(x).getLastName());
                    tfID.setText(acctList.get(x).getAcctNum());
                    tfType.setText(acctList.get(x).getAcctType());
                    tfBalance.setText(acctList.get(x).getBalance());
                    tfNUM.setText(Integer.toString(x + 1));
                    Integer records = acctList.size();
                    tfRecords.setText(records.toString());
                }
            PrintWriter output = null;
            try {
                output = new PrintWriter("final.txt");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Final.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int i = 0; i < acctList.size(); i++){
                // Writes formated line data to txt file.
                String type;
                if("Checking".equals(acctList.get(i).getAcctType())){
                    type = "0";
                }else{
                    type = "1";
                }
                output.println(acctList.get(i).getFirstName() + "\t" +
                       acctList.get(i).getLastName() + "\t" +
                       acctList.get(i).getAcctNum() + "\t" +
                       type +"\t" +
                       acctList.get(i).getBalance());
            }
            output.close();
            }

            /**
             * 
             * @param i
             * @param i0
             * @return random int between i and i0
             */
            private int getRandomInteger(int i, int i0) {
                return ((int) (Math.random()*(i0 - i))) + i;
            }
        });
        // Updates screen to next record.
        Button btNext = new Button("Next -->");
        pane.add(btNext, 2,5);
        btNext.setOnAction((ActionEvent event) -> {
            if(x < acctList.size()-1){
              x++;  
            }           
            tfName.setText(acctList.get(x).getFirstName()+" "+acctList.get(x).getLastName());
            tfID.setText(acctList.get(x).getAcctNum());
            tfType.setText(acctList.get(x).getAcctType());
            tfBalance.setText(acctList.get(x).getBalance());
            tfNUM.setText(Integer.toString(x + 1));
        });
        // Searches first names for matches and displays record number
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Bank of Gregory");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}



