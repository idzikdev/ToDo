package pl.idzikdev.model;

import java.util.Objects;

public class Note {
    private String name;
    private String date;
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Note(String name, String date, String category) {
        this.name = name;
        this.date = date;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(name, note.name) &&
                Objects.equals(date, note.date) &&
                Objects.equals(category, note.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, category);
    }

    @Override
    public String toString() {
        return "Note{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
