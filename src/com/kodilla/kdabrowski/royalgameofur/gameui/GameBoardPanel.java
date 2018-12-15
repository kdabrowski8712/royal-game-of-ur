package com.kodilla.kdabrowski.royalgameofur.gameui;

import com.kodilla.kdabrowski.royalgameofur.settings.BoardConfiguration;
import com.kodilla.kdabrowski.royalgameofur.state.BoardCoordinates;
import com.kodilla.kdabrowski.royalgameofur.state.Piece;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class GameBoardPanel {
    private List<Rectangle> boardFields;
    private List<ImageView> rosettas;
    private BoardConfiguration boardConfiguration;
    private GridPane panel;
    private Label column0Label;
    private Label column1Label;
    private Label column2Label;
    private Label row0Label;
    private Label row1Label;
    private Label row2Label;
    private Label row3Label;
    private Label row4Label;
    private Label row5Label;
    private Label row6Label;
    private Label row7Label;
    private Button newMoveButton;


    public GameBoardPanel(BoardConfiguration boardConfiguration) {
        boardFields = new ArrayList<>();
        rosettas = new ArrayList<>();

        String style = "-fx-font-weight: bold; -fx-font-size: 20";
        column0Label = new Label("0");
        column1Label = new Label("1");
        column2Label = new Label("2");

        row0Label = new Label("0");
        row1Label = new Label("1");
        row2Label = new Label("2");
        row3Label = new Label("3");
        row4Label = new Label("4");
        row5Label = new Label("5");
        row6Label = new Label("6");
        row7Label = new Label("7");

        newMoveButton = new Button("New move");
        newMoveButton.setDisable(true);
        this.boardConfiguration = boardConfiguration;


        column0Label.setStyle(style);
        column1Label.setStyle(style);
        column2Label.setStyle(style);

        row0Label.setStyle(style);
        row1Label.setStyle(style);
        row2Label.setStyle(style);
        row3Label.setStyle(style);
        row4Label.setStyle(style);
        row5Label.setStyle(style);
        row6Label.setStyle(style);
        row7Label.setStyle(style);

    }

    public GridPane generateBoard() {
        GridPane gamePane = new GridPane();
        panel = gamePane;

        String cssLayout3 = "-fx-border-color: black;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 1;\n" +
                "-fx-border-style: solid;\n";

        gamePane.setAlignment(Pos.TOP_CENTER);
        gamePane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        gamePane.setHgap(0);
        gamePane.setVgap(0);
        gamePane.setStyle(cssLayout3);
        gamePane.setPrefWidth(1200);

        for(int j=0; j<8;j++){
            for(int i=0; i<3;i++) {
                Rectangle newRec=null;
                if(boardConfiguration.checkIfOnBattleField(i)) {
                    newRec = generateBoardRectangle(Color.RED,100,100);
                }
                else if(j<2 || j>3) {
                    newRec = generateBoardRectangle(Color.LIGHTGREEN,100,100);
                }
                if(newRec!=null) {
                    boardFields.add(newRec);
                    gamePane.add(newRec, i, j);
                }
            }
        }

        Image rosette = new Image("file:resources/rosette.jpg");

        for(int i=0; i<5; i++) {
            ImageView ros = generateRosettaView(rosette,100,100);
            rosettas.add(ros);
        }

        for(int i=0; i<5; i++) {
            BoardCoordinates rosetePosition = boardConfiguration.getFieldsWithRosette().get(i).getRosettePosition();
            gamePane.add(rosettas.get(i),rosetePosition.getColumn(),rosetePosition.getRow());
        }

        gamePane.add(column0Label,0,8);
        GridPane.setHalignment(column0Label,HPos.CENTER);

        gamePane.add(column1Label,1,8);
        GridPane.setHalignment(column1Label,HPos.CENTER);

        gamePane.add(column2Label,2,8);
        GridPane.setHalignment(column2Label,HPos.CENTER);

        GridPane.setMargin(column0Label,new Insets(10,0,0,0));
        GridPane.setMargin(column1Label,new Insets(10,0,0,0));
        GridPane.setMargin(column2Label,new Insets(10,0,0,0));

        gamePane.add(row0Label,4,0);
        gamePane.add(row1Label,4,1);
        gamePane.add(row2Label,4,2);
        gamePane.add(row3Label,4,3);
        gamePane.add(row4Label,4,4);
        gamePane.add(row5Label,4,5);
        gamePane.add(row6Label,4,6);
        gamePane.add(row7Label,4,7);


        GridPane.setMargin(row0Label,new Insets(0,0,0,10));
        GridPane.setMargin(row1Label,new Insets(0,0,0,10));
        GridPane.setMargin(row2Label,new Insets(0,0,0,10));
        GridPane.setMargin(row3Label,new Insets(0,0,0,10));
        GridPane.setMargin(row4Label,new Insets(0,0,0,10));
        GridPane.setMargin(row5Label,new Insets(0,0,0,10));
        GridPane.setMargin(row6Label,new Insets(0,0,0,10));
        GridPane.setMargin(row7Label,new Insets(0,0,0,10));


        gamePane.add(newMoveButton,0,9);
        GridPane.setColumnSpan(newMoveButton,3);
        GridPane.setHalignment(newMoveButton,HPos.CENTER);
        GridPane.setMargin(newMoveButton, new Insets(10,0,0,0));

        return gamePane;
    }

    private ImageView generateRosettaView (Image image , int width , int height) {
        ImageView res = new ImageView(image);
        res.setFitWidth(width);
        res.setFitHeight(height);
        return res;
    }


    private Rectangle generateBoardRectangle (Color fillColor , int width , int height ) {

        Rectangle r = new Rectangle(width,height);
        r.setFill(fillColor);
        r.setStrokeWidth(2);
        r.setStroke(Color.WHITE);

        return r;

    }

    public void addPiece( Piece p ) {
        this.panel.add(p.getPieceRepresentation(),p.getPiecePositionOnBoard().getColumn(),p.getPiecePositionOnBoard().getRow());
        GridPane.setHalignment(p.getPieceRepresentation(), HPos.CENTER);
    }

    public void movePiece(Piece p, BoardCoordinates newCoordinates) {

        removePiece(p);
        addPiece(p);
    }


    public void  removePiece(Piece p) {
        this.panel.getChildren().remove(p.getPieceRepresentation());
    }


    public void clear() {

        List<Node> nodesToRemove = new ArrayList<>();

        for (Node n : panel.getChildren()) {
            if(n.getClass()==Circle.class) {
                nodesToRemove.add(n);
            }
        }

        panel.getChildren().removeAll(nodesToRemove);
    }

    public Button getNewMoveButton() {
        return newMoveButton;
    }
}
