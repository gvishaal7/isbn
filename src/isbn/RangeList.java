package isbn;

import java.math.BigInteger;

public class RangeList {
    
    private String[][] range;
    private RangeList prev;
    private RangeList next;
    private final int minList = 10; //min length of a valid isbn number
    
    public RangeList(String[][] range) {
        this.range = range;
        this.prev = null;
        this.prev = null;
    }
    
    /*
     *  a function to balance the range lists from the functions class for easier search.
     */
    public void balance() {
        if((this.range.length >= this.minList + 2) && (this.prev == null) && (this.next == null)) {
            int len = this.range.length - this.minList;
            String[][] temp = new String[len][3]; 
            System.arraycopy(this.range, 0, temp, 0, len);
            this.prev = new RangeList(temp);
            this.prev.balance();
            int last = this.range.length - len;
            temp = new String[last][3];
            System.arraycopy(this.range, last, temp, 0, last);
            this.next = new RangeList(temp);
            this.next.balance();
            System.arraycopy(this.range, len, this.range, 0, last);
        }
    }
    
    /*
     *  a function to search the for a given isbn in the two range lists and returns 
     *  the size of group/publisher part of the isbn string.
     *  as the numbers are large for int,long and double, BigInteger is used to process them.
     */
    public String search(String val) {
        if(val.length() > 0) {
            BigInteger bVal = new BigInteger(val);
            BigInteger first = new BigInteger(this.range[0][0]);
            BigInteger last = new BigInteger(this.range[this.range.length-1][1]);
            if(bVal.compareTo(first) == -1) {
                if(this.prev != null) {
                    return this.prev.search(val);
                }
                else {
                    return "0";
                }
            }
            if(bVal.compareTo(last) == 1) {
                if(this.next != null) {
                    return this.next.search(val);
                }
                else {
                    return "0";
                }
            }
            for (String[] temp : this.range) {
                first = new BigInteger(temp[0]);
                last = new BigInteger(temp[1]);
                if((bVal.compareTo(first) == 1 || bVal.compareTo(first) == 0) && (bVal.compareTo(last) == -1 || bVal.compareTo(last) == 0)) {
                    return temp[2];
                }
            }
        }
        return "0";
    }
}
