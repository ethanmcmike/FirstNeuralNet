package firstneuralnet;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Renderer {
    
    private Game game;
    private GraphicsContext gc;
    private double width, height, size;
    
    public Renderer(Game game){
        this.game = game;
    }
    
    public void render(Canvas canvas){
        
        gc = canvas.getGraphicsContext2D();
        
        initDimens(canvas);
        
        clear();
        
        drawGrid();
        
        drawTiles();
    }
    
    private void initDimens(Canvas canvas){
        width = canvas.getWidth();
        height = canvas.getHeight();
        if(width < height)
            size = width / 3f;
        else
            size = height / 3f;
    }
    
    private void clear(){
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
    }
    
    private void drawGrid(){
        
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(5);
        
        gc.save();
        gc.translate(width/2f, height/2f);
        gc.translate(-size*1.5f, -size*1.5f);
        
        gc.strokeLine(size, 0, size, 3*size);
        gc.strokeLine(2*size, 0, 2*size, 3*size);
        gc.strokeLine(0, size, 3*size, size);
        gc.strokeLine(0, 2*size, 3*size, 2*size);
        
        gc.restore();
    }
    
    public void drawTiles(){
        
        gc.setFill(Color.RED);
        gc.setFont(new Font(100));
        
        String text = "";
        
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                int tile = game.getTile(i, j);
                
                switch (tile) {
                    case 1:
                        text = "X";
                        break;
                    case 2:
                        text = "O";
                        break;
                    default:
                        text = "";
                        break;
                }
                
                gc.fillText(text, i*size + size*0.25, (j+1)*size - size*0.25);
            }
        }
    }
}
