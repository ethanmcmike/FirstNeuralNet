package firstneuralnet;

public class XORNN {
    
    private final float alpha = 0.01f;       //Learning rate
    
    private final Matrix[] weight, bias;
    
    public XORNN(int numInput, int[] hidden, int numOutput){
        
        int numHidden = hidden.length;
        
        weight = new Matrix[numHidden+1];
        bias = new Matrix[numHidden+1];
        
        //Input layer
        weight[0] = new Matrix(hidden[0], numInput);
        bias[0] = new Matrix(hidden[0], 1);
        
        //Hidden layers
        for(int i=1; i<numHidden; i++){
            weight[i] = new Matrix(hidden[i], hidden[i-1]);   
            bias[i] = new Matrix(hidden[i], 1);
        }
        
        //Output layer
        weight[numHidden] = new Matrix(numOutput, hidden[numHidden-1]);    
        bias[numHidden] = new Matrix(numOutput, 1);
        
        //Initialize as random values
        for(int i=0; i<weight.length; i++){
            fillRandom(weight[i]);
            fillRandom(bias[i]);
        }
    }
    
    public Matrix compute(float[] data){
        
        Matrix[] output = new Matrix[weight.length];
        
        //Input layer forward propegation
        Matrix input = new Matrix(data.length, 1);
        input.set(data);
        output[0] = sig(weight[0].times(input).plus(bias[0]));
        
        //Hidden layer forward propegation
        for(int i=1; i<output.length; i++){
            output[i] = sig(weight[i].times(output[i-1]).plus(bias[i]));
        }
        
        return output[output.length-1];
    }
    
    public void train(float[] data, float[] solution){
        
        if(data.length != weight[0].getCols() || solution.length != weight[weight.length-1].getRows()){
            System.out.println("Training data wrong dimensions!");
            return;
        }
        
        Matrix[] output = new Matrix[weight.length];
        Matrix[] gradient = new Matrix[weight.length];
        
        //Input layer forward propegation
        Matrix input = new Matrix(data.length, 1);
        input.set(data);
        output[0] = sig(weight[0].times(input).plus(bias[0]));
        
        //Hidden layer forward propegation
        for(int i=1; i<output.length; i++){
            output[i] = sig(weight[i].times(output[i-1]).plus(bias[i]));
        }
        
        //Output layer gradients
        Matrix target = new Matrix(solution.length, 1);
        target.set(solution);
        
        int lastLayer = weight.length-1;
        
        gradient[lastLayer] = target.minus(output[lastLayer]);
        
        for(int i=0; i<gradient[lastLayer].getRows(); i++){
            float val = gradient[lastLayer].get(i, 0) * sigder(output[lastLayer].get(i, 0));
            gradient[lastLayer].set(i, 0, val);
        }
        
        //Hidden layer gradients
        for(int i=lastLayer-1; i>=0; i--){
            gradient[i] = weight[i+1].transpose().times(gradient[i+1]);
            
            for(int j=0; j<gradient[i].getRows(); j++){
                float val = gradient[i].get(i, 0) * sigder(output[i].get(j, 0));
                gradient[i].set(j, 0, val);
            }
        }
        
        //Tune biases
        for(int i=bias.length-1; i>=0; i--){
            bias[i] = bias[i].plus(gradient[i].times(alpha));
        }
        
        //Tune weights
        for(int i=weight.length-1; i>0; i--){
            weight[i] = weight[i].plus(gradient[i].times(output[i-1].transpose()).times(alpha));
        }
    }
    
    private void fillRandom(Matrix m){
        //Fills matrix with random values [-1,1]
        for(int i=0; i<m.getRows(); i++){
            for(int j=0; j<m.getCols(); j++){
                m.set(i, j, (float)(Math.random()*2-1));
            }
        }
    }
    
    private float sig(float x){
//        return (float)(1 / (1 + Math.exp(-x)));
        return (float)Math.tanh(x);
    }
    
    private Matrix sig(Matrix m){
        
        Matrix result = new Matrix(m.getRows(), m.getCols());
        
        for(int i=0; i<m.getRows(); i++){
            for(int j=0; j<m.getCols(); j++){
                float val = sig(m.get(i, j));
                result.set(i, j, val);
            }
        }
        
        return result;
    }
    
    private float sigder(float x){
//        return x*(1-x);
        return (float)(1 - Math.pow(Math.tanh(x), 2));
    }
}