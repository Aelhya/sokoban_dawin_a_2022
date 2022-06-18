package com.example.sokoban.game.db;

public class Boards {


    String _id;
    String name;
    int rows;
    int columns;
    String description;

    public Boards() {
    }

    public Boards(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public Boards(String _id, String name, int rows, int columns) {
        this._id = _id;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public Boards(String _id, String name, int rows, int columns, String description) {
        this._id = _id;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
