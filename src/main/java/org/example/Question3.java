package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Name: Cheryl Kong
 * Class Group: SD2B
 */
public class Question3 { // Nested HTML (Stack)

    public static boolean validate(String filename) throws FileNotFoundException {
        String[] selfClosingTags = { "<br>", "<img>", "<hr>", "<input>", "<meta>", "<link>" };

        Scanner sc = new Scanner(new File(filename));
        Stack<String> tagStack = new Stack<>();

        while (sc.hasNext()) {
            String tag = sc.next();

            // check if it's a self-closing tag
            boolean isSelfClosing = false;
            for (String selfClosingTag : selfClosingTags) {
                if (tag.equals(selfClosingTag)) {
                    isSelfClosing = true;
                    break;
                }
            }
            // if it's a self-closing tag, skip the iteration
            if (isSelfClosing) {
                continue;
            }
            if (tag.startsWith("</")) {
                if (tagStack.isEmpty()) {
                    sc.close();
                    return false;
                }
                // pop the top element
                String lastAccessedTag = tagStack.pop();
                // </a> != </ + li> invalid example
                // </a> == </ + a> valid example
                if (!tag.equals("</" + lastAccessedTag.substring(1))) {
                    sc.close();
                    return false;
                }
            } else if (tag.startsWith("<") && tag.endsWith(">")) {
                tagStack.push(tag);
            }
        }
        sc.close();
        // the stack should be empty if all tags are matched because they have been popped
        return tagStack.isEmpty();
    }

    /*
     * This function tests the files in the files array to see if
     * they are valid.
     * tags_valid.txt should return true;
     * tags_invalid.txt should output as invalid;
     * 
     * 
     */
    public static void main(String[] args) throws FileNotFoundException {
        String[] files = { "tags_valid.txt", "tags_invalid.txt" };
        for (String fName : files) {
            System.out.print(fName + ": ");
            if (validate(fName)) {
                System.out.println("This file is valid");
            } else {
                System.out.println("This file is invalid");
            }
        }
    }

}
