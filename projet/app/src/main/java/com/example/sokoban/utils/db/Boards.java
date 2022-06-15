package com.example.sokoban.utils.db;

public class Boards {

    String board_id;
    String name;
    int rows;
    int columns;

    public Boards() {
    }

    public Boards(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public String getBoard_id() {
        return board_id;
    }


    public Boards(String board_id, String name, int rows, int columns) {
        this.board_id = board_id;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }


    public void setBoard_id(String board_id) {
        this.board_id = board_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
