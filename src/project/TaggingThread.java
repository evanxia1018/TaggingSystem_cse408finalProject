package project;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by evanxia on 6/18/17.
 */
public class TaggingThread extends Thread{
    //fields
    private File testingFolder;
    private final static File businessFolder = new File("data/training-data/business/");
    private final static File literatureFolder = new File("data/training-data/literature/");
    private final static File scienceFolder = new File("data/training-data/science/");
    private final static File technologyFolder = new File("data/training-data/technology/");
    private Tag groundTruth;
    private Thread t;
    private String threadName;


    TaggingThread(String name) {
        threadName = name;
        testingFolder = new File("data/testing-data/" + name);
        switch (name){
            case "business": groundTruth = Tag.BUSINESS;
            break;
            case "literature": groundTruth = Tag.LITERATURE;
            break;
            case "science": groundTruth = Tag.SCIENCE;
            break;
            case "technology": groundTruth = Tag.TECHNOLOGY;
        }
        System.out.println("Creating " +  threadName );
    }
    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

    public void run(){
        System.out.println("Running " +  threadName );
        try {
            float numOfTrue = 0;
            float numOfFalse = 0;
            List<String> taggingResults = new ArrayList<String>();
            //read documents from testingFolder.
            for(final File file : testingFolder.listFiles()){
                if(file.getName().equals(".DS_Store")) continue;
                System.out.println("Now Classifying file \" " + file.getPath() + " \"");
                Tag result = getTag(file, 5);
                taggingResults.add(result.toString());
                System.out.println("Tagging result of " + testingFolder.getName() + " is: " + result + "        Ground truth is " + groundTruth);
                if(result.equals(groundTruth)) numOfTrue++;
                else numOfFalse++;
            }
            System.out.println("The tagging results of " + testingFolder.getName() + " are: " + taggingResults);
            float accuracyPct = numOfTrue / (numOfTrue + numOfFalse) * 100;
            System.out.println("\n\nThe accuracy is " + accuracyPct + "%");
        }catch (Exception e) {
            System.out.println("Thread " +  threadName + " interrupted." + "\n" + e.toString());
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    /*
   Getting the tag using KNN
    */
    private Tag getTag(File inputFile, int k) throws Exception{
        List<String[]> similarityVec = new ArrayList<String[]>();
        for(File file : businessFolder.listFiles()){
            similarityVec.add(new String[]{ new DocumentSimilarityCalculator().getSimilarity(inputFile,file)+"",Tag.BUSINESS.toString()});
        }
        for(File file : literatureFolder.listFiles()){
            similarityVec.add(new String[]{ new DocumentSimilarityCalculator().getSimilarity(inputFile,file)+"",Tag.LITERATURE.toString()});
        }
        for(File file : scienceFolder.listFiles()){
            similarityVec.add(new String[]{ new DocumentSimilarityCalculator().getSimilarity(inputFile,file)+"",Tag.SCIENCE.toString()});
        }
        for(File file : technologyFolder.listFiles()){
            similarityVec.add(new String[]{ new DocumentSimilarityCalculator().getSimilarity(inputFile,file)+"",Tag.TECHNOLOGY.toString()});
        }
        sort(similarityVec);
        int numOfBusiness = 0;
        int numOfLiterature = 0;
        int numOfScience = 0;
        int numOfTechnology = 0;
        int numOfOther = 0;
        for(int i = 0; i < k; i++){
            if(Float.parseFloat(similarityVec.get(i)[0]) == 0){
                numOfOther++;
                continue;
            }
            switch (similarityVec.get(i)[1]){
                case "BUSINESS": numOfBusiness++;
                    break;
                case "LITERATURE":numOfLiterature++;
                    break;
                case "SCIENCE":numOfScience++;
                    break;
                case "TECHNOLOGY":numOfTechnology++;
                    break;
            }
        }
        int nums[] = new int[]{numOfBusiness, numOfLiterature, numOfScience, numOfTechnology, numOfOther};
        Arrays.sort(nums);
        int largestNum = nums[4];
        if(largestNum == numOfBusiness) return Tag.BUSINESS;
        if(largestNum == numOfLiterature) return Tag.LITERATURE;
        if(largestNum == numOfScience) return Tag.SCIENCE;
        if(largestNum == numOfTechnology) return Tag.TECHNOLOGY;
        return Tag.OTHER;
    }

    private void sort(List<String[]> list){
        for(int i = 0; i < list.size() - 1; i++){
            for(int j = i + 1; j < list.size(); j++){
                if(Float.parseFloat(list.get(j)[0]) > Float.parseFloat(list.get(i)[0])){
                    //swap
                    String[] temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }
}
