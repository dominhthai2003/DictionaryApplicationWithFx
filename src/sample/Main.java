package sample;

import Fx.Dictionary;
import Fx.Word;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    Stage stage;
    TextField textWord;
    Label meaningLabel;
    Button searchButton;
//    VBox mainPanel;
    VBox mainPanel;

    Dictionary dictionary;

    Button removeAWord;
    Button addAWord;
    List<String> wordList;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;

         dictionary = new Dictionary();
          dictionary.importFromFile();
           dictionary.sortWords();

         textWord = new TextField();
          textWord.setPromptText("Insert your word");

          //Tim kiem tu Button
        searchButton = new Button("FIND");
           searchButton.setOnAction(e -> buttonFind());

           //Them tu button
        addAWord = new Button();
        addAWord.setText("Add");
        addAWord.setOnAction(event -> buttonAdd());

        //Xoa tu Button
        removeAWord = new Button();
        removeAWord.setText("Remove");
        removeAWord.setOnAction(event -> removeAdd());

           meaningLabel = new Label("Meaning field");
           meaningLabel.setAlignment(Pos.CENTER);
           meaningLabel.setPrefWidth(425);
           meaningLabel.setPrefHeight(350);


        ScrollPane sp = new ScrollPane();
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setContent(meaningLabel);

        VBox textAndButton = new VBox(15);
        textAndButton.getChildren().addAll(textWord, searchButton, addAWord, removeAWord);

        HBox input = new HBox(15);
        input.getChildren().addAll(textAndButton, sp);


        Label title = new Label("DICTIONARY");
        title.setTextFill(Color.BLUEVIOLET);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));;
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-background-color: #f8f8f8");

        //    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        mainPanel = new VBox(60);
        mainPanel.getChildren().addAll(title, input);
        primaryStage.setTitle("My Dictionary");
        primaryStage.setScene(new Scene(mainPanel, 600, 400));
        primaryStage.show();
    }

    //SetAction tuong ung cho ham Find
    private void buttonFind() {
        String insertWord = textWord.getText();
        int i = dictionary.dictionaryLookUp(insertWord);
    if (i != -1) {
            meaningLabel.setText(dictionary.getWordList().get(i).getDefinition());
        }
    else {
        meaningLabel.setText("Not found,maybe you wrote it wrong");
    }
    }

    //SetAction for add button
    private void buttonAdd() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add a word");

        Label inputWordLabel = new Label();
        inputWordLabel.setText("Enter a word");

        TextField inputWord = new TextField();
        inputWord.setPromptText("Enter a word you want to add");

        Label phonetic = new Label();
        phonetic.setText("Phonetics");

        TextField phoneticInput = new TextField();
        phoneticInput.setPromptText("Enter the word's phonetic");

        Label detailLabel = new Label();
        detailLabel.setText("Detail");

        TextArea wordDetail = new TextArea();

        Button addConfirm = new Button();
        addConfirm.setText("Add");

        VBox addPane = new VBox();
        addPane.getChildren().addAll(inputWordLabel, inputWord, phonetic, phoneticInput, detailLabel, wordDetail, addConfirm);

        addConfirm.setOnAction(event -> {
            String s = inputWord.getText();
         //   StringBuilder s2 = new StringBuilder(wordDetail.getText());
            String s2 = phoneticInput.getText();
            String s3 = wordDetail.getText();

            int i = dictionary.addWord(new Word(s, s2, s3));
            if (i != -1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("MESSAGE");
                alert.setContentText("Word add successfully");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("MESSAGE");
                alert.setContentText("Word already exist");
                alert.show();
            }
        });
        Scene scene = new Scene(addPane, 300, 500);
        stage.setScene(scene);
        stage.setMinWidth(300);
        stage.show();
    }

    //Xoa tu, lam tuong tu add
    public void removeAdd() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Remove a word");

        Label inputWordLabel = new Label();
        inputWordLabel.setText("Enter the word");

        TextField inputWord = new TextField();
        inputWord.setPromptText("Enter a word you want to remove");

        Button removeConfirm = new Button();
        removeConfirm.setText("Remove");

        VBox removePane = new VBox();
        removePane.getChildren().addAll(inputWordLabel, inputWord, removeConfirm);

        removeConfirm.setOnAction(event -> {
            String s = inputWord.getText();

            int i = dictionary.removeWord(s);
            if (i != -1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("MESSAGE");
                alert.setContentText("Word remove successfully");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("MESSAGE");
                alert.setContentText("Word not found in dictionary");
                alert.show();
            }
        });
        Scene scene = new Scene(removePane, 300, 500);
        stage.setScene(scene);
        stage.setMinWidth(300);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
