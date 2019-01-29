package firstneuralnet;

import java.util.Scanner;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main {
    
    private Game game;
    private Canvas canvas;
    private Renderer renderer;
    
    private NeuralNet net;

    public static void main(String[] args) {
        
        float[][] p = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
        };
        
        float[][] t = {
            {0},
            {1},
            {1},
            {0}
        };

//        float[][] p = {
//            {0, 0, 0},
//            {0, 0, 1},
//            {0, 1, 0},
//            {0, 1, 1},
//            {1, 0, 0},
//            {1, 0, 1},
//            {1, 1, 0},
//            {1, 1, 1}
//        };
//        
//        float[][] t = {
//            {1, 1, 0, 0},
//            {1, 0, 0, 1},
//            {1, 0, 0, 1},
//            {0, 1, 1, 0},
//            {1, 0, 0, 1},
//            {0, 1, 1, 0},
//            {0, 1, 1, 0},
//            {0, 0, 1, 1}
//        };

        int[] hidden = {5, 5};
        XORNN net = new XORNN(2, hidden, 1);
        
        System.out.println("TRAINING...");
        for(int i=0; i<50000; i++){
            int index = (int)(Math.random() * p.length);
            net.train(p[index], t[index]);
        }
        
        float test[] = new float[p[0].length];
        
        Scanner sc = new Scanner(System.in);
        while(true){
            
            System.out.println("--------");
            
            for(int i=0; i<test.length; i++){
                System.out.print("Enter Number " + i + ": ");
                test[i] = sc.nextInt();
            }
            
            Matrix answer = net.compute(test);
            answer.print();
            
//            for(int i=0; i<answer.getRows; i++){
//                
//                if(answer[i][0] > 0.5)
//                    System.out.print(1);
//                else
//                    System.out.print(0);
//                
////                System.out.print(answer[0][i]);
//            }
//            System.out.println();
        }
    }

    public void start(Stage stage) {
        initModels();
        initViews(stage);
        initControllers();
    }
    
    private void initModels(){
        game = new Game();
        net = new NeuralNet(20, 10, 4);
    }
    
    private void initViews(Stage stage){
        
        canvas = new Canvas(400, 400);
        canvas.setOnMouseClicked(click);
        
        BorderPane bp = new BorderPane();
        bp.setCenter(canvas);
        
        Scene scene = new Scene(bp);
        scene.setFill(Color.BLACK);
        
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setResizable(false);
        stage.show();
    }
    
    private void initControllers(){
        renderer = new Renderer(game);
        renderer.render(canvas);
    }
    
    private EventHandler<MouseEvent> click = new EventHandler(){
        @Override
        public void handle(Event event) {
            
            int x = (int)(((MouseEvent)event).getX() / canvas.getWidth() * 3);
            int y = (int)(((MouseEvent)event).getY() / canvas.getHeight() * 3);
            
            int player = game.getPlayer();
            boolean success = game.play(x, y);
            
            display(x, y, player, success);
            
            float[] input = new float[20];
            int index = 0;
            for(int i=0; i<9; i++){
                int tile = game.getTile(i);
                switch(tile){
                    case 1:
                        input[index+0] = 1;
                        input[index+1] = 0;
                        break;
                    case 2:
                        input[index+0] = 0;
                        input[index+1] = 1;
                    default:
                        input[index+0] = 0;
                        input[index+1] = 0;
                }
                
                index += 2;
            }
            
            input[18] = 1;
            input[19] = 1;
            
            int guess = net.getGuess(input);
            player = game.getPlayer();
            success = game.play(guess);
            
            display(guess%3, guess/3, player, success);
            
            renderer.render(canvas);
        }
    };
    
    private void display(int x, int y, int player, boolean success){
        if(success){
                System.out.println("Player " + player + " successfully played at " + x + " " + y);
            } else {
                System.out.println("Player " + player + " failed to play at " + x + " " + y);
            }
    }
}
