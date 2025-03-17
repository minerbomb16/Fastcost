package com.fastcost.operations;

import com.fastcost.tableViews.InformationTableView;
import javafx.scene.control.Alert;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;

import static com.fastcost.tableViews.DataTableView.dataTableView;

public class PDFcreator {
    private int pageNumber = 50;
    private int pageCounter = 0;
    private int cellHeight = 18;
    private int[] cellWidthFirst = {125, 220};
    private int cellWidthSecond[] = {27, 22, 45, 220, 45, 70, 70, 64};
    private int[] firstTableSize = {2, 6};
    private int secondTableSize[] = {8, dataTableView.getItems().size()};
    private int x = 0, y = 0;
    private PDType0Font font;
    private int bonusCellHight = 0;
    private int fontsize = 10;
    private int currentBonusCellHight = 0;
    private int xStart = 16;
    private int yStart = 0;
    private int secondTableProgress = 0;
    private PDImageXObject pdImage;
    private String[] header = {"Lp", "Fn", "ID", "Nazwa elementu", "Szt", "Cena j. [zł]", "Cena ł. [zł]", "Uwagi"};
    public PDFcreator() {}

    public void create(String path) {
        PDDocument document = new PDDocument();
        String[] split;

        try{
            pdImage = PDImageXObject.createFromFile("data/logo.png", document);
            PDPage[] page = new PDPage[pageNumber];
            PDPageContentStream[] contentStream = new PDPageContentStream[pageNumber];

            page[0] = new PDPage(PDRectangle.A4);
            int pageWidth = 595;
            int pageHeight = 841;

            yStart = pageHeight - 12;
            x = xStart;
            y = yStart;
            font = PDType0Font.load(document, new File("data/Arimo-Regular.ttf"));

            document.addPage(page[0]);
            contentStream[0] = new PDPageContentStream(document, page[0]);

            contentStream[pageCounter].drawImage(pdImage, pageWidth-180, pageHeight-80);
            makeFirstTableBackground(contentStream[pageCounter]);
            addHeaderBackGrund(contentStream[pageCounter]);
            makeSecondTableBackground(contentStream, page, document, pageHeight, pageWidth);

            bonusCellHight = 0;
            y = yStart;
            makeFirstTable(contentStream[pageCounter], pageWidth);
            addHeader(contentStream[pageCounter]);
            makeSecondTable(contentStream, page, document, pageHeight, pageWidth);

            contentStream[pageCounter].stroke();
            contentStream[pageCounter].close();
            document.save(path);
            document.close();
            AlertCreator.showWindow("Plik PDF został stworzony", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            AlertCreator.showWindow("Nie można stworzyć pliku PDF", Alert.AlertType.ERROR);
        }
    }

    private void makeFirstTable(PDPageContentStream contentStream, int pageWidth) {
        addPageNumber(contentStream, pageWidth);
        String[] split;
        for(int i = 0; i < firstTableSize[1]; i++){
            boolean nextPage = false;
            for(int j = 0; j < firstTableSize[0]; j++) {
                split = wordSplit(InformationTableView.getTable().getColumns().get(j).getCellObservableValue(i).getValue().toString());
                if (contentCheck(split, cellWidthFirst, j)) {
                    nextPage = contentCheck(split, cellWidthFirst, j);
                }
            }
            for(int j = 0; j < firstTableSize[0]; j++){
                split = wordSplit(InformationTableView.getTable().getColumns().get(j).getCellObservableValue(i).getValue().toString());
                putWords(split, contentStream, cellWidthFirst, j);
                if(j < firstTableSize[0]-1) {
                    x+=cellWidthFirst[j];
                }
            }
            x = xStart;
            y -= cellHeight + bonusCellHight;
            bonusCellHight = 0;
            currentBonusCellHight = 0;
        }
        y -= cellHeight;
    }

    private void makeFirstTableBackground(PDPageContentStream contentStream) {
        try {
            String[] split;
            for(int i = 0; i < firstTableSize[1]; i++){
                for(int j = 0; j < firstTableSize[0]; j++) {
                    split = wordSplit(InformationTableView.getTable().getColumns().get(j).getCellObservableValue(i).getValue().toString());
                    contentCheck(split, cellWidthFirst, j);
                }
                if(i%2 == 0) {
                    contentStream.setNonStrokingColor(225);
                } else {
                    contentStream.setNonStrokingColor(255);
                }
                contentStream.fillRect(x, y, 345, -(cellHeight+bonusCellHight));
                x = xStart;
                y -= cellHeight + bonusCellHight;
                bonusCellHight = 0;
                currentBonusCellHight = 0;
            }
            y -= cellHeight;
            contentStream.setNonStrokingColor(0);
        } catch (Exception e) {}
    }

    private int makeSecondTable(PDPageContentStream[] contentStream, PDPage[] page, PDDocument document, int pageHeight, int pageWidth) {
        String split[];
        for(; secondTableProgress < secondTableSize[1]; secondTableProgress++){
            boolean nextPage = false;
            for(int j = 0; j < secondTableSize[0]; j++) {
                split = wordSplit(dataTableView.getColumns().get(j).getCellObservableValue(secondTableProgress).getValue().toString());
                if (contentCheck(split, cellWidthSecond, j)) {
                    nextPage = true;
                }
            }
            int tmpBonus = bonusCellHight;
            if(nextPage) {
                return 0;
            }
            bonusCellHight = tmpBonus;
            for(int j = 0; j < secondTableSize[0]; j++){
                split = wordSplit(dataTableView.getColumns().get(j).getCellObservableValue(secondTableProgress).getValue().toString());
                putWords(split, contentStream[pageCounter], cellWidthSecond, j);
                if(j < secondTableSize[0]-1) {
                    x+=cellWidthSecond[j];
                }
            }
            x = xStart;
            y -= cellHeight + bonusCellHight;
            bonusCellHight = 0;
            currentBonusCellHight = 0;
        }
        return 0;
    }

    private void makeSecondTableBackground(PDPageContentStream[] contentStream, PDPage[] page, PDDocument document, int pageHeight, int pageWidth) {
        String[] split;
        for(int i = 0; i < secondTableSize[1]; i++){
            boolean nextPage = false;
            for(int j = 0; j < secondTableSize[0]; j++) {
                split = wordSplit(dataTableView.getColumns().get(j).getCellObservableValue(i).getValue().toString());
                if (contentCheck(split, cellWidthSecond, j)) {
                    nextPage = true;
                }
            }
            int tmpBonus = bonusCellHight;
            if(nextPage) {
                try{
                    bonusCellHight = 0;
                    y = yStart;
                    makeFirstTable(contentStream[pageCounter], pageWidth);
                    addHeader(contentStream[pageCounter]);
                    makeSecondTable(contentStream, page, document, pageHeight, pageWidth);
                    contentStream[pageCounter].stroke();
                    contentStream[pageCounter].close();
                    pageCounter++;
                    page[pageCounter] = new PDPage(PDRectangle.A4);
                    document.addPage(page[pageCounter]);
                    contentStream[pageCounter] = new PDPageContentStream(document, page[pageCounter]);
                    contentStream[pageCounter].drawImage(pdImage, pageWidth-180, pageHeight-80);
                    y = yStart;
                    bonusCellHight = 0;
                    makeFirstTableBackground(contentStream[pageCounter]);
                    addHeaderBackGrund(contentStream[pageCounter]);
                } catch (Exception e) {}
            }
            bonusCellHight = tmpBonus;
            try {
                if(i%2 == 0) {
                    contentStream[pageCounter].setNonStrokingColor(225);
                } else {
                    contentStream[pageCounter].setNonStrokingColor(255);
                }
                if(dataTableView.getColumns().get(1).getCellObservableValue(i).getValue().toString().equals("t")) {
                    contentStream[pageCounter].setNonStrokingColor(78, 184, 209);
                }
                contentStream[pageCounter].fillRect(x, y, 563, -(cellHeight+bonusCellHight));
                contentStream[pageCounter].setNonStrokingColor(0);
            } catch (Exception e) {}
            x = xStart;
            y -= cellHeight + bonusCellHight;
            bonusCellHight = 0;
            currentBonusCellHight = 0;
        }
    }

    private String[] wordSplit(String words) {
        return words.split("\\s+");
    }

    private boolean contentCheck(String[] words, int[] table, int col) {
        try {
            float wordsLength = 0;
            String text = "";
            for (int i = 0; i < words.length; i++) {
                text += words[i] + " ";
                wordsLength = (font.getStringWidth(text) / 1000 * fontsize);
                if (wordsLength >= table[col] - 5 && i != 0) {
                    text = "";
                    bonusCellHight += 12;
                    text += words[i] + " ";
                }
            }
            if(y - 12 - bonusCellHight < 40) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private void putWords(String[] words, PDPageContentStream contentStream, int[]cellWidth,  int col) {
        try {
            float wordsLength = 0;
            String text = "";
            contentStream.beginText();
            contentStream.newLineAtOffset(x + 5, y - 12);
            contentStream.setFont(font, fontsize);
            for (int i = 0; i < words.length; i++) {
                text += words[i] + " ";
                wordsLength = (font.getStringWidth(text) / 1000 * fontsize);
                if (wordsLength < cellWidth[col] - 5 || i == 0) {
                    contentStream.showText(words[i] + " ");
                } else {
                    text = "";
                    currentBonusCellHight+=12;
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.newLineAtOffset(x + 5, y - 12 - currentBonusCellHight);
                    contentStream.setFont(font, fontsize);
                    contentStream.showText(words[i] + " ");
                    text += words[i] + " ";
                }
            }
            currentBonusCellHight = 0;
            contentStream.endText();
            contentStream.addRect(x, y, cellWidth[col], -(cellHeight+bonusCellHight));
        } catch (Exception e) {}
    }

    private void addHeader(PDPageContentStream contentStream) {
        try {
            for(int i = 0; i < secondTableSize[0]; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(x + 5, y - 12);
                contentStream.setFont(font, fontsize);
                contentStream.showText(header[i]);
                contentStream.endText();
                contentStream.addRect(x, y, cellWidthSecond[i], -cellHeight);
                x+=cellWidthSecond[i];
            }
            x = xStart;
            y -= cellHeight;
        } catch (Exception e) {}
    }

    private void addHeaderBackGrund(PDPageContentStream contentStream) {
        try {
            contentStream.setNonStrokingColor(185);
            contentStream.fillRect(x, y, 563, -cellHeight);
            contentStream.setNonStrokingColor(0);
            x = xStart;
            y -= cellHeight;
        } catch (Exception e) {}
    }

    private void addPageNumber(PDPageContentStream contentStream, int pageWidth) {
        try {
            contentStream.beginText();
            contentStream.newLineAtOffset(pageWidth - 30, 16);
            contentStream.setFont(font, fontsize);
            contentStream.showText(String.valueOf(pageCounter+1));
            contentStream.endText();
        } catch (Exception e) {}
    }
}