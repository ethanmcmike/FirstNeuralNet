package firstneuralnet;

public class Matrix {
    
    private int N, M;
    private float data[][];
    
    public Matrix(int rows, int cols){
        N = rows;
        M = cols;
        data = new float[rows][cols];
    }
    
    public int getRows(){
        return N;
    }
    
    public int getCols(){
        return M;
    }
    
    public float[][] getData(){
        return data;
    }
    
    public float get(int row, int col){
        return data[row][col];
    }
    
    public void set(int row, int col, float val){
        if(0 > row || row >= N || 0 > col || col >= M){
            System.out.println("Out of bounds exception!");
            return;
        }
        
        data[row][col] = val;
    }
    
    public void set(float[][] data){
        this.data = data;
    }
    
    public void set(float[] data){
        if(N == 1 ^ M == 1)
            this.data[0] = data;
        else
            System.out.println("Matrix has more than one row or col!");
    }
    
    public Matrix times(float a){
        
        Matrix result = new Matrix(N, M);
        
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                float val = data[i][j] * a;
                result.set(i, j, val);
            }
        }
        
        return result;
    }
    
    public Matrix times(Matrix b){
        
        if(M != b.getRows()){
            System.out.println("Incompatable dimensions!");
            return null;
        }
        
        Matrix result = new Matrix(N, b.getCols());
        float[][] dataB = b.getData();
        
        for(int j=0; j<result.getCols(); j++){
            
            for(int i=0; i<result.getRows(); i++){
                
                float sum = 0;

                for(int k=0; k<M; k++){
                    sum += data[i][k] * dataB[k][j];
                }
                
                result.set(i, j, sum);
            }
        }
        
        return result;
    }
    
    public Matrix plus(Matrix b){
        if(N != b.getRows() || M != b.getCols()){
            System.out.println("Matrix dimensions do not match!");
            return null;
        }
        
        Matrix result = new Matrix(N, M);
        
        float[][] dataB = b.getData();
        
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                float val = data[i][j] + dataB[i][j];
                result.set(i, j, val);
            }
        }
        
        return result;
    }
    
    public Matrix transverse(){
        Matrix result = new Matrix(M, N);
        
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                result.set(j, i, data[i][j]);
            }
        }
        
        return result;
    }
    
    public void print(){
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                System.out.print(data[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
