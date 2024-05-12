import java.util.Arrays;
import java.util.*;

public class Set {
    public int[] contents; 
    public int[] normalForm;
    private int[] primeForm;

    public Set(int[] passed){ 
        contents = new int[passed.length];

        int smallest = 50;
        int smallestIndex = 0;
        for (int i = 0; i < passed.length; i++){ //These loops place the set in order of highest to lowest (NOT NORMAL FORM YET)
            for (int j = 0; j < passed.length; j++){
                if(passed[j] < smallest){
                    smallest = passed[j];
                    smallestIndex = j;
                }
            }
            contents[i] = smallest;
            passed[smallestIndex] = 50;
            smallest = 50;
            
        }
        CreateNormalForm();
        //We are not calling the createPrime() in the constructor, because some sets are generated just for calculations, and don't need their primes.
        
        
        
    }
    public void printContents(){ //prints the contents of the set to console, mostly for testing
        System.out.print("[");
        for(int i = 0; i < contents.length; i++){
            
            System.out.print(contents[i]);
            if(i != contents.length - 1){
                System.out.print(",");
            }
        }
        System.out.println("]");
    }
    public void CreateNormalForm(){ // Creates the normal form instance variable (this is a travesty)
        normalForm = new int[contents.length];
        int index;
        int[][] posArray = new int[contents.length][contents.length];
        for (int i = 0; i < posArray[0].length; i++){ //Creates a 2d Array of all possible orders for the set
            for(int j = 0; j < posArray[0].length; j++){
                index = i+j;
                if(index >= posArray[0].length){
                    index = index - posArray[0].length;
                }
                posArray[i][index] = contents[j];
            }
        }
        //printing grid (Testing)
        //System.out.println("All possible orderings:");
        for(int i = 0; i <posArray[0].length; i++){
            for(int j = 0; j < posArray[0].length; j++){
                
                //System.out.print(posArray[i][j]);
            }
            //System.out.println();
        }

        //will store the interval contents of all the possible orderings
        int[][] intArray = new int[contents.length][contents.length];

        for(int i = 0; i < contents.length; i++){
            for(int j = 0; j <contents.length; j++){

                //compare each index to the one after, but if it's the last one compare it to the first element of the set 
                if(j+1 != contents.length){
                    intArray[i][j] = interval(posArray[i][j],posArray[i][j+1] );
                }
                else{
                    intArray[i][j] = interval(posArray[i][j],posArray[i][0] );
                }


            }
        }
        
        //Printing interval array
        //System.out.println("Interval content:");
        for(int i = 0; i <intArray[0].length; i++){
            for(int j = 0; j < intArray[0].length; j++){
                
                //System.out.print(intArray[i][j]);
            }
            //System.out.println();
        }

        if(Arrays.equals(intArray[0], intArray[1])){ //If the interval content is the same, then the set is equidistant and our normal form is the first one
            normalForm = posArray[0];
            
        }
        else{
            //This determines which possible ordering is most normal (programming this made me realize how stupid normal form is)
            int length = intArray.length;
            int largest = -1;
            int largestInd = -100;
            boolean cont = true;
            int[] whiteList = new int[] {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
            for(int outer = 0; outer < posArray[0].length && cont; outer++){ 
                cont = false;
                largest = -1; 
                largestInd = -1;
                for(int inner = 0; inner < length; inner++){
                    
                    if(outer == 0){
                        if(intArray[inner][length - outer - 1] > largest){
                            largest = intArray[inner][length - outer - 1];
                            largestInd = inner; 
                        }
                        
                    }
                    else if(outer != 0 && isWhitelist(whiteList, inner) ){
                        if(intArray[inner][length - outer - 1] > largest){
                            largest = intArray[inner][length - outer - 1];
                            largestInd = inner; 
                        }
                    }

                }
                int count = 0;
                for (int second = 0; second < length; second++){
                    if(intArray[second][length - outer - 1] == largest){
                        count++;
                    }
                }
                if(count > 1){
                    cont =true;
                }
                for(int inner = 0; inner < length; inner++){
                    if(intArray[inner][length - outer - 1] == largest){
                        whiteList[inner] = inner;
                    }
                    else if(intArray[inner][length - outer - 1] != largest){
                        whiteList[inner] = -1;
                    }
                }
            }
            
            if(multiWhiteList(whiteList)){
                //System.out.println(Arrays.toString(whiteList));
                int smallestInd = 100;
                int smallest = 100;
                
                for(int i = 0; i < posArray.length; i++){
                    
                    if(isWhitelist(whiteList, i) && posArray[i][0] < smallest){
                        
                        smallestInd = i;
                        smallest = posArray[i][0];
                        
                    }
                }
                
                normalForm = posArray[smallestInd];
                //System.out.println(Arrays.toString( normalForm) + " normal");
            }
            else{
                normalForm = posArray[largestInd];
                
               //System.out.println(Arrays.toString( normalForm) + " normal");
            }
            
            
        }
        
    
    }
    public boolean multiWhiteList(int[] whiteList){ // Checks if the whitelist has more than one set
        int count = 0;
        for(int i = 0; i < whiteList.length; i ++){
            if(whiteList[i] != -1){
                count++;
            }
        }
        if(count > 1){
            return true;
        }
        return false;
    }

    public void printNorm(){
        System.out.println(Arrays.toString(normalForm));
    }
    public void printPrime(){
        System.out.println(Arrays.toString(primeForm));
    }
    public static void main(String args[]){
        
        int[] array2 = new int[]{3,10,7,1};
        Set b = new Set(array2);
        System.out.println("normal:");
        b.printNorm();
        System.out.println("prime:");
        b.createPrime();
        b.printPrime();

        
    }

    public int[] getNormalForm(){
        return normalForm;
    }

    public void createPrime(){ //generates the prime form instance variable
    
        Set set1 = transpose(12-normalForm[0]);
        
        int len = normalForm.length;
        
        int lastInd = set1.getNormalForm()[len - 1]; 
        Set set2 = set1.invert(lastInd);
        
        //set1 is the normal form transposed to start on 0, and set2 is it's inversion, one of these has to be the prime form, we just need to figure out which

        if(Arrays.equals(set1.getNormalForm(), set2.getNormalForm())){ // if the set is symettrical the two sets are equal, so we can use either.
            primeForm = Arrays.copyOf(set1.getNormalForm(), len);
        }
        else{
            for(int i = 0; i < normalForm.length; i++){ // loop backwards and compare the sets until we find the prime
                if(set1.getNormalForm()[len - 2 - i] == set2.getNormalForm()[len - 2 - i]){
                    continue;
                }
                else if(set1.getNormalForm()[len - 2 - i] > set2.getNormalForm()[len - 2 - i]){ //if one set has a lower number, it must be the prime
                    primeForm = Arrays.copyOf(set2.getNormalForm(), len);
                    break; //once the prime is found, there is no need to keep going.
                }
                else if(set1.getNormalForm()[len - 2 - i] < set2.getNormalForm()[len - 2 - i]){ 
                    primeForm = Arrays.copyOf(set1.getNormalForm(), len);
                    break;
                }  
            }
        }
        
    }

    public int[] getPrimeForm(){
        return primeForm;
    }
    public boolean isWhitelist(int[] list, int num){ // checks if a given set is on the whitelist
        
        for(int i = 0; i < list.length; i++){
            if(list[i] == num){
                return true;
            }
        }
        return false;
    }

    public int interval(int pitchOne, int pitchTwo){ //returns the ordered distance between two pitches. (circle)
        int count = 0;
        while(pitchOne != pitchTwo){
            pitchOne++;
            if(pitchOne == 12){
                pitchOne = 0;
            }
            count++;
        }
        return count;
    }

    

    public int[] normalForm(){
        return normalForm;
    }

    public static int addInterval(int pitchOne, int add){ //adds two pitches together (mod 12)
        int pitchTwo = pitchOne;
        for(int i = 0; i < add; i++){
            pitchTwo++;
            if(pitchTwo == 12){
                pitchTwo =0;
            }
        }
        return pitchTwo;
    }

    
    public Set transpose(int t){ // transposes the given set by t
        int[] copyNorm = Arrays.copyOf(normalForm, normalForm.length);
        for (int i = 0; i < copyNorm.length; i++){
            copyNorm[i] = addInterval(copyNorm[i], t);
        }
        Set retVal = new Set(copyNorm);
        return retVal;
    }

    public Set invert(int num){ // inverts the set by t
        int[] copyNorm = Arrays.copyOf(normalForm, normalForm.length);
        
        for(int i = 0; i < copyNorm.length; i++){
            if(copyNorm[i] != 0){
                copyNorm[i] = 12 - copyNorm[i];
            }
        }
        
        Set tempSet = new Set(copyNorm);
        return tempSet.transpose(num);
    }

    
}
