package com.rem.Animation;

import java.util.ArrayList;

/**
 * Created by Anastasiya on 30.12.2015.
 */
public class Stack {
//add comment
    private int size;
    private ArrayList<String> elements = new ArrayList<String>();

    public Stack (int size) {
        this.size = size;
    }

    public ArrayList<String> allElements() {
         ArrayList<String> all = new ArrayList<String>(this.elements);
         return all;
    }

    public int maxSize () {
        return this.size;
    }

    public void addElement (String element) {
        this.elements.add(element);
        if (this.elements.size() > this.size) {
            this.elements.remove(0);
        }
    }
}
