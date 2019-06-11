package project.sourcecode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ivan Brenes
 */
public class Node {
    
    //Necessary data for the Node
    private String classLabel;
    private ArrayList<String> attributeList;
    private ArrayList<Tuple> dataSet;
    private ArrayList<Node> nodePointers;
    
    //We grab the input of attributes and the data set
    //And we initialize the class label and node pointers.
    public Node(ArrayList<String> attribute, ArrayList<Tuple> set){
        classLabel = "unknown";
        attributeList = attribute;
        dataSet = set;
        nodePointers = new ArrayList<>();
    }
    
    //Using the first element as a comparison,
    //If all of the elements are of the same class, then it returns true.
    public boolean checkTupleClass(){
        String firstElement = dataSet.get(0).getLikely();
        for(int i = 1; i < dataSet.size(); i++){
            if(!(dataSet.get(i).getLikely().equals(firstElement))){
                return false;
            }
        }
        return true;
    }
    
    //This goes through whatever the majority of answers are for the class
    //and returns whatever majority there is.
    //In this project, we will be only concerned with booleans.
    public String classMajority(){
        HashMap<String, Integer> answerBucket = new HashMap<>();
        
        for(int i = 0; i < dataSet.size(); i++){
            String key = dataSet.get(i).getLikely();
            if(answerBucket.containsKey(key)){
                answerBucket.put(key, answerBucket.get(key) + 1);
            }
            else{
                answerBucket.put(key, 1);
            }
        }
        
        //example: 4 yes and 2 no
        int yes = answerBucket.get("yes");
        int no = answerBucket.get("no");
        if(yes > no){
            return "yes";
        }
        
        //example: 3 yes and 3 no 
        //OR 
        //1 yes and 12 no
        else{
            return "no";
        }
    }
    
    //FIXME
    public void attributeSelectionMethod(){
        
    }
    
    //The rest of these are getters and setters for the instance variables.
    public String getLabel(){
        return classLabel;
    }
    
    public void setClass(String label){
        classLabel = label;
    }
    
    public int getAttributeListCount(){
        return attributeList.size();
    }
    
    public ArrayList<String> getAttributeList(){
        return attributeList;
    }
    
    public ArrayList<Tuple> getDataSet(){
        return dataSet;
    }
    
    public ArrayList<Node> nodePointers(){
        return nodePointers;
    }
}
