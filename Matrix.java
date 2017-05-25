/*************************************************************************
 *   Keira N. Poe
 *   Matrix Object Class for holding and passing Matrix data efficiently.
 *************************************************************************/
package rowreduction;

/**
 *
 * @author kpoe
 */
public class Matrix
{
    private int vectors;
    private int entries;
    private double[][] theMatrix;
   
    /*************************************************************************
        The default constructor. Generates an Identity Matrix. Can be modified
        by using the setAnEntry function.
    *************************************************************************/
    public Matrix(int v, int e)
    {
        vectors = v;
        entries = e;
        
        theMatrix = new double [entries][vectors];
        
        int max = v;

        if(e<v)
        {
            max = e;
        }
        
        for(int i = 0; i < e; i++)
        {
            for(int j = 0; j < v; j++)
            {
                theMatrix[i][j] = 0;
            }
        }

        for(int i = 0; i < max; i++)
        {
            theMatrix[i][i] = 1;
        }

    }
    
    
    /*************************************************************************
        Only for use with Matrices that will Not be altered.
    *************************************************************************/    
    public Matrix(int e, int v, double[][] mat)
    {
        entries = e;
        vectors = v;
        theMatrix = mat;
    }

    /*************************************************************************
        Use with EXTREME caution. Modifications to the matrix in the outside
        program WILL modify theMatrix. Do not use unless this is agreeable.
    *************************************************************************/
    public double[][] getTheMatrix()
    {
        return theMatrix;
    }
    /*
        Probably unnecessary - definitely deceptive. Don't need it unless you're
        putting in a Completely original and Utterly new matrix.
    */
    public void setTheMatrix(double[][] aMatrix)
    {
        theMatrix = aMatrix;
    }
    
    
    /*************************************************************************
        For the rest of these, the function name says it all.
    *************************************************************************/
    
    public void print()
    {
        String toPrint = "\n";
        
        for(int i = 0; i < entries; i++)
        {
            for(int j = 0; j < vectors; j++)
            {
                toPrint += theMatrix[i][j] + " ";
            }
            toPrint += "\n";
        }
        
        System.out.println(toPrint);
    }
    
    public int getVectors()
    {
        return vectors;
    }
    
    public int getEntries()
    {
        return entries;
    }
    
    public double getAnEntry(int en, int ve)
    {
        return theMatrix[en][ve];
    }
    
    public void setAnEntry(int en, int ve, double newVal)
    {
        theMatrix[en][ve] = newVal;
    }
    
}
