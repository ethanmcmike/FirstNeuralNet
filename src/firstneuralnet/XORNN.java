package firstneuralnet;

public class XORNN {
    
    private final float alpha = 0.01f;       //Learning rate
    
    private Matrix w1, w2, b1, b2;
    
    public XORNN(int numInput, int numHidden, int numOutput){
        
        w1 = new Matrix(numInput, numHidden);
        w2 = new Matrix(numHidden, numOutput);
        b1 = new Matrix(1, numHidden);
        b2 = new Matrix(1, numOutput);
        
        init();
    }
    
    private void init(){
        fillRandom(w1);
        fillRandom(w2);
        fillRandom(b1);
        fillRandom(b2);
    }
    
    public float[][] compute(float[] input){
        
        Matrix I = new Matrix(1, input.length);
        I.set(input);
        
//        System.out.println("INPUT");
//        I.print();
//        
//        System.out.println("W1");
//        w1.print();
        
        Matrix hiddenOutput = sig(I.times(w1).plus(b1));
        
//        System.out.println("HIDDEN OUTPUT");
//        hiddenOutput.print();
//        
//        System.out.println("W2");
//        w2.print();
        
        Matrix result = sig(hiddenOutput.times(w2).plus(b2));
        
//        System.out.println("RESULT");
//        result.print();
        
        return result.getData();
    }
    
    public void train(float[] data, float[] target){
        
        Matrix I = new Matrix(1, data.length);
        I.set(data);
        
//        System.out.println("INPUT");
//        I.print();
//        
//        System.out.println("W1");
//        w1.print();
        
        Matrix hiddenOutput = sig(I.times(w1).plus(b1));
        
//        System.out.println("HIDDEN OUTPUT");
//        hiddenOutput.print();
//        
//        System.out.println("W2");
//        w2.print();
        
        Matrix guess = sig(hiddenOutput.times(w2).plus(b2));
        
//        System.out.println("GUESS");
//        guess.print();
        
        //Calculate layer 2 gradients
        Matrix outputGrad = new Matrix(1, w2.getCols());
        for(int i=0; i<outputGrad.getCols(); i++){
            float err = target[i] - guess.get(0, i);
            float val = sigder(guess.get(0, i)) * err;
            outputGrad.set(0, i, val);
        }
        
//        System.out.println("LAYER 2 GRAD");
//        outputGrad.print();

        //Calculate layer 2 biases
        b2 = b2.plus(outputGrad.times(alpha));
        
        //Tune layer 2
        for(int i=0; i<w2.getCols(); i++){          //For every output neuron
            for(int j=0; j<w2.getRows(); j++){      //For every hidden neuron
                
                float dw = alpha * hiddenOutput.get(0, i) * outputGrad.get(0, i);
                float w = w2.get(j, i) + dw;
                w2.set(j, i, w);
            }
        }
        
//        System.out.println("W2");
//        w2.print();
        
        //Calculate layer 1 gradients
        Matrix hiddenGrad = new Matrix(1, w1.getCols());
        for(int i=0; i<hiddenGrad.getCols(); i++){          //For every hidden neuron
            for(int j=0; j<outputGrad.getCols(); j++){      //For every output neuron
                float y = hiddenOutput.get(0, i);
                float delta = w2.get(i, j) * outputGrad.get(0, j) * sigder(y);
                hiddenGrad.set(0, i, delta);
            }
        }
        
        //Calculate layer 1 biases
        b1 = b1.plus(hiddenGrad.times(alpha));
        
//        System.out.println("Layer 1 GRAD");
//        hiddenGrad.print();
        
        //Tune layer 1
        for(int i=0; i<w1.getCols(); i++){          //For every hidden neuron
            for(int j=0; j<w1.getRows(); j++){      //For every input neuron
                
                float dw = alpha * I.get(0, j) * hiddenGrad.get(0, i);
                float w = w1.get(j, i) + dw;
                w1.set(j, i, w);
            }
        }
        
//        System.out.println("W1");
//        w1.print();
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