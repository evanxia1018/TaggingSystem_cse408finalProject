package project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by evanxia on 6/17/17.
 */
public class DocumentSimilarityCalculator {
    //fields
    private List<String> sentances1;
    private List<String> sentances2;
    private final int numOfDocToCompare = 30000;
    //methods

    public float getSimilarity(File file1, File file2) throws Exception{
        Scanner scanner1 = new Scanner(file1,"UTF-8");
        Scanner scanner2 = new Scanner(file2,"UTF-8");
        List<String> lineList1 = new ArrayList<String>();
        List<String> lineList2 = new ArrayList<String>();
        while(scanner1.hasNextLine()){
            lineList1.add(scanner1.nextLine());
        }
        while(scanner2.hasNextLine()){
            lineList2.add(scanner2.nextLine());
        }
        scanner1.close();
        scanner2.close();
        String[] lineArray1 = lineList1.toArray(new String[lineList1.size()]);
        String[] lineArray2 = lineList2.toArray(new String[lineList2.size()]);

        sentances1 = new ArrayList<>();
        sentances2 = new ArrayList<>();
        for(int i = 0; i < lineArray1.length; i++){
            for(String str : lineArray1[i].split("\\.")) {
                if(str.length() > 3 && sentances1.size() <= numOfDocToCompare) sentances1.add(str);
            }
        }
        for(int i = 0; i < lineArray2.length; i++){
            for(String str : lineArray2[i].split("\\.")) {
                if(str.length() > 3  && sentances2.size() <= numOfDocToCompare) sentances2.add(str);
            }
        }

        float result = 0;
        int index = 0;
        int size = Math.min(sentances1.size(),sentances2.size());
        while(index < size){
            result = result + new SentenceSimilarityCalculator().getSimilarity(sentances1.get((int )(Math.random() * (size - 1))),sentances2.get((int )(Math.random() * (size - 1))));
            index++;
        }
        return result;
    }

}
