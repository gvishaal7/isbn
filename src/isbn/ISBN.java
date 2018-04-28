/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isbn;

import java.util.Scanner;

/**
 *
 * @author vishaalgopalan
 */
public class ISBN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] partPrefix = new String[]{"ISBN-EAN: ", "ISBN-GROUP: ", "ISBN-PUBLISHER: ", "ISBN-TITLE: ","ISBN-CHECK: "};
        System.out.print("ISBN : ");
        Scanner in = new Scanner(System.in);
        Fix fix = new Fix(in.next());
        String hyphenedISBN = fix.hyphenate();
        System.out.println(hyphenedISBN);
        String[] parts = hyphenedISBN.split("-");
        for(int i=0;i<parts.length;i++) {
            System.out.println(partPrefix[i]+parts[i]);
        }
        System.out.println("ISBN10 : "+fix.isbn10());
    }
}
