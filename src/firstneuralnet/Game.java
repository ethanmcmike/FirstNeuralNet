package firstneuralnet;

public class Game {
    
    private int[] tiles;
    private int player;
    boolean gameOver;
    
    public Game(){
        tiles = new int[9];
        for(int i=0; i<9; i++){
            tiles[i] = 0;
        }
        
        gameOver = false;
    }
    
    public boolean play(int x, int y){
        
        swapPlayer();
        
        if(gameOver)
            return false;
        
        int index = getIndex(x, y);
        
        if(index > tiles.length)
            return false;
        
        if(tiles[index] != 0)
            return false;
        
        tiles[index] = player;
        
        if(getWin() != 0){
            gameOver = true;
            System.out.println("GAME OVER - PLAYER" + player + " WINS!");
        }
        
        return true;
    }
    
    public boolean play(int index){
        int x = index % 3;
        int y = index / 3;
        
        return play(x, y);
    }
    
    public int getIndex(int x, int y){
        return 3*y + x;
    }
    
    private void swapPlayer(){
        player = (player == 1) ? 2 : 1;
    }
    
    public int getPlayer(){
        return player;
    }
    
    public int[] getTiles(){
        return tiles;
    }
    
    public int getTile(int x, int y){
        return tiles[getIndex(x, y)];
    }
    
    public int getTile(int index){
        return tiles[index];
    }
    
    public int getWin(){
        
        if(tiles[0] == tiles[1] && tiles[1] == tiles[2])
            return tiles[0];
        
        if(tiles[0] == tiles[3] && tiles[3] == tiles[6])
            return tiles[0];
        
        if(tiles[0] == tiles[4] && tiles[4] == tiles[8])
            return tiles[0];
        
        if(tiles[3] == tiles[4] && tiles[4] == tiles[5])
            return tiles[3];
        
        if(tiles[6] == tiles[7] && tiles[7] == tiles[8])
            return tiles[6];
        
        if(tiles[1] == tiles[4] && tiles[4] == tiles[7])
            return tiles[1];
        
        if(tiles[2] == tiles[5] && tiles[5] == tiles[8])
            return tiles[2];
        
        if(tiles[2] == tiles[4] && tiles[4] == tiles[6])
            return tiles[2];
        
        return 0;
    }
}
