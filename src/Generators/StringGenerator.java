package Generators;

/**
 * Created by shiva on 2/24/18.
 */
public class StringGenerator implements IGenerator{
    private int length= 52;
    private char[] min  = new char[length];
    private char[] max  = new char[length];
    public StringGenerator(){setMinAndMax();}
    public StringGenerator(int length){
        this.length=length;
        min  = new char[length];
        max  = new char[length];
        setMinAndMax();
    }
    private void setMinAndMax(){
        for(int i=0;i<length;i++) {
            min[i] = (i < 7) ? 'A' : 'X';
            max[i] = (i < 7) ? 'Z' : 'X';
        }
    }
    public String next(int seed){
        char[] temp = {'A','A','A','A','A','A','A'};
        char[] field = min.clone();
        int i,j,rem,cnt;
        i=6; cnt=0;
        while(seed > 0 && i>=0){
            rem = seed % 26;
            temp[i] = (char) ('A'+rem);
            seed = seed/26;
            i--;cnt++;
        }
        if(i <0) i=0;
        for(j=0;j<cnt;j++,i++){
                    field[j]=temp[i];
        }

        return String.valueOf(field);
    }

}
