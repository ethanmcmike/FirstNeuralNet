package firstneuralnet;

public class Neuron {
    
    private int numInputs;
    private float[] weights;
    private float thresh;
    
    public Neuron(int numInputs){
        this.numInputs = numInputs;
        weights = new float[numInputs];
        for(int i=0; i<numInputs; i++){
            weights[i] = (float)(Math.random()*2-1);        //Randomize weights [-1,1]
        }
        thresh = (float)(Math.random()*2-1);
    }
    
    public int compute(float[] inputs){
        
        float sum = 0;
        
        for(int i=0; i<numInputs; i++){
            sum += inputs[i] * weights[i];
        }
        
        return activate(sum);
    }
    
    private int activate(float val){
        if(val > 0){
            return 1;
        } else {
            return -1;
        }
    }
    
    public float[] getWeights(){
        return weights;
    }
    
    public void setWeights(float[] weights){
        numInputs = weights.length;
        this.weights = weights;
    }
    
    private double sig(double x){
        return 1 / (1 + Math.exp(-x));
    }
    
    private double sigder(double x){
        double sig = sig(x);
        return sig*(1-sig);
    }
}
