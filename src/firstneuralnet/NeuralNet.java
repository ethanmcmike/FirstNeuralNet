//package firstneuralnet;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Scanner;
//
//public class NeuralNet {
//    
//    private static final String PATH = "src/firstneuralnet/weights.txt";
//    
//    private final int I, H, O;
//    
//    private Neuron[] hNeurons, oNeurons;
//    
//    public NeuralNet(int numInputs, int numHidden, int numOutputs){
//        I = numInputs;
//        O = numOutputs;
//        H = numHidden;
//        
//        initNeurons();
//        load();
//    }
//    
//    private void initNeurons(){
//        hNeurons = new Neuron[H];
//        for(int i=0; i<H; i++)
//            hNeurons[i] = new Neuron(I);
//        
//        oNeurons = new Neuron[O];
//        for(int i=0; i<O; i++)
//            oNeurons[i] = new Neuron(H);
//    }
//    
//    public int getGuess(float[] input){
//        
//        Matrix inputHidden = new Matrix(1, I);
//        inputHidden.set(input);
//        
//        //Hidden layer
//        Matrix weightMatrix = new Matrix(I, H);
//        
//        for(int i=0; i<H; i++){
//            
//            float[] weights = hNeurons[i].getWeights();
//            
//            for(int j=0; j<I; j++){
//                weightMatrix.set(j, i, weights[j]);
//            }
//        }
//        
//        Matrix resultHidden = inputHidden.times(weightMatrix);
//        
//        //Output layer
//        Matrix hiddenOutput = resultHidden.transverse();
//                
//        weightMatrix = new Matrix(H, O);
//        
//        for(int i=0; i<O; i++){
//            
//            float[] weights = oNeurons[i].getWeights();
//            
//            for(int j=0; j<H; j++){
//                weightMatrix.set(j, i, weights[j]);
//            }
//        }
//        
//        hiddenOutput = hiddenOutput.transverse();
//        
//        float result = hiddenOutput.times(weightMatrix).getData()[0][0];
//        
//        
//    }
//    
//    private void save(){
//        File file = new File(PATH);
//        
//        if(!file.exists()){
//            try{
//                System.out.println("NEW FILE");
//                file.createNewFile();
//            } catch(IOException e){
//                e.printStackTrace();
//            }
//        }
//        
//        try{
//            
//            FileWriter writer = new FileWriter(PATH);
//            BufferedWriter bw = new BufferedWriter(writer);
//            
//            for(int i=0; i<H; i++){
//                
//                float[] weights = hNeurons[i].getWeights();
//                
//                for(int j=0; j<weights.length; j++){
//                    bw.write("H " + i + " " + j + " ");
//                    bw.write(String.valueOf(weights[j]));
//                    bw.newLine();
//                }
//            }
//            
//            for(int i=0; i<H; i++){
//                
//                float[] weights = oNeurons[i].getWeights();
//                
//                for(int j=0; j<weights.length; j++){
//                    bw.write("O " + i + " " + j + " ");
//                    bw.write(String.valueOf(weights[j]));
//                    bw.newLine();
//                }
//            }
//            
//            bw.close();
//            writer.close();
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//    
//    private void load(){
//        File file = new File(PATH);
//        
//        if(!file.exists()){
//            try{
//                System.out.println("NEW FILE");
//                file.createNewFile();
//            } catch(IOException e){
//                e.printStackTrace();
//            }
//        }
//        
//        Scanner sc = null;
//        try{
//            sc = new Scanner(file);
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//        
//        while(sc.hasNext()){
//            
//            for(int i=0; i<H; i++){
//                
//                float[] weights = new float[I];
//         
//                for(int j=0; j<weights.length; j++){
//                    
//                    String type = sc.next();
//                    int nIndex = sc.nextInt();
//                    int wIndex = sc.nextInt();
//                    float weight = sc.nextFloat();
//
//                    weights[j] = weight;
//                }
//                
//                hNeurons[i].setWeights(weights);
//            }
//            
//            for(int i=0; i<O; i++){
//                
//                float[] weights = new float[H];
//         
//                for(int j=0; j<weights.length; j++){
//                    
//                    String type = sc.next();
//                    int nIndex = sc.nextInt();
//                    int wIndex = sc.nextInt();
//                    float weight = sc.nextFloat();
//
//                    weights[j] = weight;
//                }
//                
//                oNeurons[i].setWeights(weights);
//            }
//        }
//    }
//}
