package project;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Created by evanxia on 6/17/17.
 */
public class Program{
    //fields
    static TaggingThread businessTaggingTest = new TaggingThread( "business");
    static TaggingThread literatureTaggingTest = new TaggingThread( "literature");
    static TaggingThread scienceTaggingTest = new TaggingThread( "science");
    static TaggingThread technologyTaggingTest = new TaggingThread( "technology");
    //static int numOfTrainingFile;
    //static File inputDocument;
    //methods
    public static void main(String args[]) throws Exception{

        System.setErr(new PrintStream(new OutputStream() {public void write(int b) {}}));
        businessTaggingTest.start();
        literatureTaggingTest.start();
        scienceTaggingTest.start();
        technologyTaggingTest.start();
    }



}
