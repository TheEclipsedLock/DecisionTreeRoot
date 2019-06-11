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
    private String splittingAttribute;
    
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
    //and returns whatever majority there is using the entire data set.
    public String classMajority(){
        HashMap<String, Integer> answerBucket = loopKeys('l');
        
        int yes;
        int no;
        try{
            yes = answerBucket.get("yes");
        }
        catch(NullPointerException e){
            //If the yes key is never initialized, we return no.
            //example: 12 yes and 0 no.
            return "no";
        }
        
        try{
            no = answerBucket.get("no");
        }
        catch(NullPointerException e){
            //If the no key is never initialized, we return yes.
            //example: 0 yes and 12 no.
            return "yes";
        }
        
        //example: 4 yes and 2 no
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
    
    //This method loops through the tuples to store unique keys and how
    //many times they appear.
    public HashMap<String, Integer> loopKeys(char attribute){
        HashMap<String, Integer> answerBucket = new HashMap<>();
        for(int i = 0; i < dataSet.size(); i++){
            String key = returnAttribute(attribute, i);
            if(answerBucket.containsKey(key)){
                answerBucket.put(key, answerBucket.get(key) + 1);
            }
            else{
                answerBucket.put(key, 1);
            }
        }
        return answerBucket;
    }
    
    //Helps to return the attribute that we are seeking from the loopKeys method
    private String returnAttribute(char attribute, int index){
        switch (attribute) {
            case 'a':
                return dataSet.get(index).getAge();
            case 'i':
                return dataSet.get(index).getIncome();
            case 's':
                return dataSet.get(index).getStudent();
            case 'c':
                return dataSet.get(index).getCredit();
            default:
                return dataSet.get(index).getLikely();
        }
    }
    
    //This method computes the best attribute possible with information gain
    //to split the data set by.
    public void attributeSelectionMethod(){
        double dataEntropy = entropyOfDataSet();
        double attributeEntropy = selectBestAttribute(dataEntropy);
        
        System.out.println(splittingAttribute + " with information gain of " + attributeEntropy + " is most suited!");
    }
    
    //Using the information gain formula, this calculates and sets the best attribute to split by.
    public double selectBestAttribute(double dataEntropy){
        double mostEntropy = 0.0;
        
        //We want to skip the RID so we choose the first true attribue, Age, and loop from there.
        //We are also not counting the last category, likely buying computer.
        for(int i = 1; i < attributeList.size() - 1; i++){
            char attribute = attributeList.get(i).charAt(0);
            double attributeEntropy = entropyOfAttribute(attribute);
            double compareEntropy = dataEntropy - attributeEntropy;
            if(mostEntropy < compareEntropy){
                mostEntropy = compareEntropy;
                splittingAttribute = attributeList.get(i);
            }
        }
        return mostEntropy;
    }
    
    //This calculates the entropy of the entire data set with the entropy formula.
    public double entropyOfDataSet(){
        double entropy = 0.0;
        HashMap<String, Integer> answerBucket = loopKeys('l');
        if(answerBucket.size() <= 0){
            System.out.println("There is no usable information in the last category!");
            return 0.0;
        }
        
        for(HashMap.Entry<String, Integer> entry : answerBucket.entrySet()){
            double division = (entry.getValue() / (dataSet.size() * 1d));
            double log = (Math.log(division) / (Math.log(2) * 1d));
            entropy = entropy + ((-1) * division * log);
        }
        return entropy;
    }
    
    //This calculates the entropy of a given subsection of the dataset using the entropy formula.
    public double entropyOfDataSet(int numberOfUniqueVals, HashMap<String, Integer> answerBucket){
        double entropy = 0.0;
        
        if(answerBucket.size() <= 0){
            System.out.println("There is no usable information in the last category!");
            return 0.0;
        }
        
        for(HashMap.Entry<String, Integer> entry : answerBucket.entrySet()){
            double division = (entry.getValue() / (numberOfUniqueVals * 1d));
            double log = (Math.log(division) / (Math.log(2) * 1d));
            entropy = entropy + ((-1) * division * log);
        }
        return entropy;
    }
    
    //This method calculates the entropy of an attribute through all of its unique values.
    public double entropyOfAttribute(char attribute){
        double entropy = 0.0;
        
        HashMap<String, Integer> attributeUniqueValueBucket = loopKeys(attribute);
        
        for(HashMap.Entry<String, Integer> entry : attributeUniqueValueBucket.entrySet()){
            String key = entry.getKey();
            entropy = entropy + calculateAttribute(attributeUniqueValueBucket, key, attribute);
        }

        return entropy;
    }
    
    //This method, for a given unique value of an attribute, helps to calculate the amount
    //of yes's and no's for that unique value.
    public double calculateAttribute(HashMap<String, Integer> attributeUniqueValueBucket, String attributeKey, char attribute){
        HashMap<String, Integer> answerBucket = new HashMap<>();
        
        for(int i = 0; i < dataSet.size(); i++){
            if(returnAttribute(attribute, i).equals(attributeKey)){
                String key = returnAttribute('l', i);
                if(answerBucket.containsKey(key)){
                    answerBucket.put(key, answerBucket.get(key) + 1);
                }
                else{
                    answerBucket.put(key, 1);
                }
            }
        }
        double entropyOfSubset = entropyOfDataSet(attributeUniqueValueBucket.get(attributeKey), answerBucket);
        double division = (attributeUniqueValueBucket.get(attributeKey) / (dataSet.size() * 1d));
        return (division * entropyOfSubset);
    }
    
    //Once the discrete-valued attribute is chosen, it is removed from the attribute list.
    public void removeSplittingAttribute(){
        attributeList.remove(splittingAttribute);
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
    
    public ArrayList<Node> getNodePointers(){
        return nodePointers;
    }
    
    public String getSplittingAttribute(){
        return splittingAttribute;
    }
}
