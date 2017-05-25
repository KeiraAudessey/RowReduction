/*******************************************************************************
    Keira N. Poe
    Math 341 - Linear Algebra
    Term Project

    This program will be given data about the size and contents of matrix, 
    row reduce it, confirming the presence of a determinant and acquiring the 
    inverse if it exists. It will then verify the inverse by multiplying it with
    the original and determining if it results in an appropriate identity matrix.
*******************************************************************************/
package rowreduction;

import javax.swing.JOptionPane;


public class RowReduction
{


    public static void main(String[] args)
    {
        boolean done = false;
        
        while(!done)
        {
            Object[] options = {"Yes", "No"};
            int n = JOptionPane.showOptionDialog(null, 
                    "Shall we evaluate a matrix?", "Continue?", 
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
                    null, options, options[1]);
            
            if(n == 1)
            {
                done = true;
            }
            else
            {
                acquireTheMatrix(); 
            }
        }        
    }
    
    
    
    
    
    /*************************************************************************
        Here we will acquire the details of the matrix, then send it off to be
        evaluated. Once it has been it will receive a boolean expressing whether
        the matrix appears to be invertible.
    *************************************************************************/    
    public static void acquireTheMatrix()
    {
        boolean done = false;
        int v = 0;
        boolean carryOn = true;
        
        int e;


            String value;
            double val;

            Matrix original;
            Matrix swapped;
            Matrix toReduce;
            Matrix inverse;
            Matrix iD;

        
        
        while(!done)
        {
            String vectors = JOptionPane.showInputDialog(
                    "How many vectors are in the matrix?"
                        + " \n(note: entries are also set by this value as "
                        + "the matrix would not be invertible elsewise.)");
            if(vectors != null)
            {
                try
                {
                    v = Integer.parseInt(vectors);
                    done = true;
                }
                catch(Exception nope)
                {
                    System.out.println("Invalid input. Try again.");
                }
            }
            else
            {
                carryOn = false;
            }
        }
        if(carryOn)// Causes the program to start over if cancle is pressed.
        {
            e = v;
            original = new Matrix(v, e);
            toReduce = new Matrix(v, e);
            inverse = new Matrix(v, e);


            for(int i = 0; i < e; i++)
            {
                for(int j = 0; j < v; j++)
                {
                    value = JOptionPane.showInputDialog("Input the value at "
                            + "position " + j + " in row " + i);
                    if(value != null)
                    {
                        try
                        {
                            val = Double.parseDouble(value);
                            original.setAnEntry(i, j, val);
                            toReduce.setAnEntry(i, j, val);
                        }
                        catch( Exception invalid)
                        {
                            System.out.println("Invalid entry. Try another.");
                            j--;
                        }

                    }
                    else
                    {
                        System.out.println("Starting over.");
                        carryOn = false;
                        i = e;
                        j = v;

                    }
                }
            }
            if(carryOn)// Causes the program to start over if cancle is pressed.
            {
                System.out.println("\n\n\n\nYou Entered:\n");
                original.print();


                boolean invertable = 
                        findInverseRR(original, toReduce, inverse);

                if(invertable)
                {
                    System.out.println("The Matrix seems to be invertable!\n");
                }
                else
                {
                    System.out.println("The Matrix is not invertable."
                            + "\n\n\n\n");
                }
            }
            
        }
        
    }

    
    /*************************************************************************
        This function will receive the various Matrices and run an algorithm to 
        row reduce and invert the matrix. 
    *************************************************************************/    
    public static boolean findInverseRR(Matrix original, Matrix toReduce, 
            Matrix inverse)
    {        
        int e = original.getEntries();//entries per vector
        int v = original.getVectors();//vectors in matrix
              
        double[][] reduceIt = toReduce.getTheMatrix();//to row reduce
        double[][] invert = inverse.getTheMatrix();//to turn into inverse

        
        boolean swapThing;

        double mult; //to multiply by inverse of pivot
        double sub; //to multiply for subtracting
        double swapIt; //for swapping row entries
        
        System.out.println("\nBeginnging Row Reductions...");

        for(int diag = 0; diag < e; diag++)//diag is for examining pivots
        {
            System.out.println("\nVeryifying pivot " + diag + " to be a non-"
                    + "zero number..."); 
            if(reduceIt[diag][diag] == 0)
            {
                /*
                    If the should be pivot point here is a 0, look for a later
                    entry that fulfills the requirements to replace it.
                */
                System.out.println("Pivot in current row is Zero..."
                        + " \nSeeking  valid replacement row...");
                swapThing = false; //return false if no valid swap
                
                for(int ent = diag+1; ent < e; ent++)
                {
                    System.out.println("...\n");
                    if(reduceIt[ent][diag]!=0)
                    {//once found, swap all relevant rows in all three matrices
                        System.out.println("Valid replacement found, Swapping."
                                + "\n");
                        for(int ront = 0; ront < v; ront ++)
                        {//ront iterates row entries, aka, vectors
                            swapIt= reduceIt[ent][ront];
                            reduceIt[ent][ront] = reduceIt[diag][ront];
                            reduceIt[diag][ront]= swapIt;
                            
                            swapIt= invert[ent][ront];
                            invert[ent][ront] = invert[diag][ront];
                            invert[diag][ront]= swapIt;
                        }
                        swapThing = true;
                    }
                }
                if(!swapThing)
                {
                    System.out.println("\nNo suitable row was found... "
                            + "\nThere seems to be a free variable.");
                    return false;
                }
                

            }
            else
            {
                System.out.println("Potential Pivot for vector " + diag 
                        + " is a non-zero, attempting row reduction.");
            }
            //Then get this row into eschelon ready form
            

            System.out.println("\nCurrent pivot verified as non-zero.\n"
                    + "Analyzing Row. . . ");
            for(int vect = 0; vect <= diag; vect++)
            {
                System.out.println("...");
                if(vect == diag && reduceIt[diag][vect] != 1 
                        && reduceIt[diag][vect]!= 0)
                { 
                    /*
                        This is the pivot point - once we get here, the 
                        previous entries in the row should all be zero
                        so all that's left is scaling the row so the
                        pivot is 1, and the remaining entries, and whole of
                        the inverse has been properly scaled.
                    */
                    System.out.println("The pivot is: " + reduceIt[diag][vect] 
                            + ". It needs to be 1. Scaling row apropriately.");
                    mult = 1/reduceIt[diag][vect];
                    System.out.println("The scalar will be: " + mult);

                    for(int rect = 0; rect < v; rect++)
                    {
                        reduceIt[diag][rect] = mult * reduceIt[diag][rect];
                        invert[diag][rect] = mult * invert[diag][rect];
                        
                    }
                }
                else if(vect < diag && reduceIt[diag][vect] != 0)
                {
                    /*
                        In practice this is the first of what the 
                        will happen upon starting each new row. Here
                        we will subtract an apropriate multiple of a 
                        previous row from each entry in the current row
                        for both the 'original' matrix and the possible 
                        inverse.
                    */
                    System.out.println("There is a non-zero prior to the "
                            + "pivot point. Subtracting the scalar of an "
                            + "apropriate previous row from the current row.");
                    
                    sub = reduceIt[diag][vect];
                    for(int rent = 0; rent < v; rent ++)
                    {
                        reduceIt[diag][rent] = reduceIt[diag][rent]
                                - (reduceIt[vect][rent] * sub);
                        invert[diag][rent] = invert[diag][rent]
                                - (invert[vect][rent] * sub);
                    }
                }
                else if(vect == diag && reduceIt[diag][vect] == 0 )
                {
                    System.out.println("Previous actions have rendered the "
                            + "pivot zero. \nStepping back.\n");
                    vect = v;
                    diag --;
                }
                else if(vect == diag && reduceIt[diag][vect] == 1 )
                {
                    System.out.println("The pivot is verified as 1.");
                }
                else if(vect < diag && reduceIt[diag][vect] == 0)
                {
                    System.out.println("Pre-Pivot value verified as 0.");
                }
            }
        }
        /*
            Now it's time to take the echelon form matrix and the inverse and 
            modify them such that the 'original' matrix has been transformed
            into the identity matrix.
        */
        System.out.println("The Matrix is now in echelon form:\n");
        toReduce.print();
        
        System.out.println("Inverse Progress:\n");
        inverse.print();
        
        System.out.println("The Matrix is now in echelon form. Pushing on to "
                + "reduced row echelon form.");
        
        /*
            Here we'll finish row reductions to clean up any non-zeros above the
            diagonal. At this point the matrix has passed the necessary hurdles
            that would indicate whether it's invertable. Now it's only a matter
            of finalizing the values of the inverse.
        */
        for(int diag = e -1; diag > 0; diag--)
        {
            for(int ent = diag -1; ent >=0; ent--)
            {
                if(reduceIt[ent][diag] != 0)
                {
                    System.out.println("Modifying row " + ent + " to correct"
                            + " a non-zero, non-pivot, entry.");
                    sub = reduceIt[ent][diag];
                    for(int rent = 0; rent < v; rent ++)
                    {

                        reduceIt[ent][rent] = reduceIt[ent][rent] 
                                - (sub * reduceIt[diag][rent]);

                        invert[ent][rent] = invert[ent][rent] 
                                - (sub * invert[diag][rent]);
                    }
                }
                else
                {
                    System.out.println("\nEntry verified as 0.\n");
                }
            }
        }
        
        System.out.println("\nEvaluations and Operations Complete.\n");
        
        System.out.println("\nProposed Inverse:\n");
        inverse.print();
        
        String toPrint= "Proposed Inverse:\n\t  ";
        
        for(int i = 0; i < e; i++)
        {
            for(int j = 0; j < v; j++)
            {
                toPrint += invert[i][j] + "\t  ";
            }
            toPrint += "\n\t  ";
        }
        
        JOptionPane.showMessageDialog(null, toPrint);
                
        return true;
    }
}
