package isbn;

public class Fix {
    
    private String isbn; //stored as an isbn13 number
    private Functions hyphen;
    
    /*
     *  Parameterized constructor to normalize and validate the given isbn number.
     *  Immaterial of the length of the isbn number, it is converted to an isbn number of 13 digits.
     *  If the given isbn is in-valid, then it is set to 0.
     */ 
    public Fix(String isbn) {
        this.isbn = new String();
        String normalisedISBN = normalise(isbn);
        int testLength = normalisedISBN.length();
        /*
         *  checks if the the given isbn number is of the type isbn13 and that the check digit returned from
         *  'checkDigit13' is same as that of the last digit of the given isbn number.
         */
        if((testLength == 13) && (checkDigit13(normalisedISBN.substring(0, testLength-1)) == (normalisedISBN.charAt(testLength-1) - 48))) {
            if(normalisedISBN.substring(0, 3).equals("978") || normalisedISBN.substring(0, 3).equals("979")) {
                this.isbn = normalisedISBN;
            }
            else {
                this.isbn = "0";
            }
        }
        /*
         *  checks if the given isbn number is of the type isbn10.
         *  for isbn10 numbers, if the check digit is '10' then it is assigned as 'X'.
         */
        if(testLength == 10) {
            int check = checkDigit10(normalisedISBN.substring(0, testLength-1));
            if((check == 10) && (normalisedISBN.charAt(testLength - 1) == 'X' || normalisedISBN.charAt(testLength - 1) == 'x')) {
                this.isbn = "978"+ normalisedISBN.substring(0, testLength - 1);
                this.isbn += String.valueOf(checkDigit13(this.isbn)); //computes the new check digit for the new isbn13
            }
            if(check == (normalisedISBN.charAt(testLength - 1) - 48) && (normalisedISBN.charAt(testLength - 1) != 'X' || normalisedISBN.charAt(testLength - 1) != 'x')) {
                this.isbn = "978" + normalisedISBN.substring(0, testLength -1);
                this.isbn += String.valueOf(checkDigit13(this.isbn)); //computes the new check digit for the new isbn13
            }
            assert (this.isbn != null && this.isbn.length() > 0);
        }
    }
    
    /*
     *  converts the isbn from 10 to 13 digits
     */
    public String isbn10() {
        int digit = checkDigit10(this.isbn.substring(3, 12));
        assert (isbn.substring(0, 3).equals("978") || isbn.substring(0, 3).equals("979"));
        if(digit == 10) {
            return this.isbn.substring(3, 12) + "X";
        }
        else {
            return this.isbn.substring(3, 12) + String.valueOf(digit);
        }
    }
    
    /*
     *  returns the isbn number, which is stored an isbn13 number
     */
    public String isbn13() {
        return this.isbn;
    }
    
    /*
     *  hyphenates the given isbn13 number
     */
    public String hyphenate() {
        if(this.hyphen == null) {
            this.hyphen = new Functions();
        }
        return this.hyphen.hyphenformat(this.isbn);
    }
    
    /*
     *  normalises the given isbn number.
     *  removes all the hypens from the number.
     */
    public final String normalise(String isbn) {
        if(isbn.matches("^(\\d(-| )?){9}(x|X|\\d|(\\d(-| )?){3}\\d)$")) {
            return isbn.replaceAll("-", "").toUpperCase();
        }
        return "";
    }
    
    /*
     *  a function that returns the check digit for the isbn10 version of the given isbn number.
     */
    private int checkDigit10(String isbn) {
        int product = 0;
        if(isbn.length() == 9) {
            for(int i=1;i<10;i++) {
                product += (((int)isbn.charAt(i-1) - 48) * i);
            }
        }
        return product %11;
    }
    
    /*
     *  a function that returns the check digit for the isbn13 version of the given isbn number
     */
    private int checkDigit13(String isbn) {
        int product = 0;
        if(isbn.length() == 12) {
            for(int i=0;i<6;i++) {
                product += ((isbn.charAt(2*i) - 48) * 1);
                product += ((isbn.charAt(2*i +1) - 48) * 3);
            }
            product = product % 10;
            if(product != 0) {
                product = 10 - product;  
            }
        }
        return product;
    }
}
