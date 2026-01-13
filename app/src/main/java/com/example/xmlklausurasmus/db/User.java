package com.example.xmlklausurasmus.db;

import java.util.Calendar;
import java.util.Dictionary;

/** Kommentare wichtig, aber werden zum Dokumentieren JavaDoc nicht übernommen
 * JavaDoc erstellen > Tools > generate JavaDoc
 * Kommentieren was Methode macht (getter/setter nicht immer relevant)
 * @author t19 > wer hat erstellt (nimmt bei automatisch angemeldeter Name in Windows)
 * (@)deprecated = veraltet...?
 *
 * erstellt am 04.11.2025
 *
 * Die Klasse User bildet die Tabelle User in unserem Androidprojekt ab.
 */
public class User implements AutoCloseable {
    private String username, pw;
    private Calendar erstelltAm;
    private int aktiv;

    /**
     * Erstellt ein neues Objekt User mit folgenden Eigenschaften:
     *
     * @param username   Name des Benutzers
     * @param pw         Passworthash des Benutzers
     * @param erstelltAm Erstellungsdatum des Benutzers
     * @param aktiv      Aktiver Account JA/NEIN (1/0)
     */
    public User(String username, String pw, Calendar erstelltAm, int aktiv) {
            this.username = username;
            this.pw = pw;
            this.erstelltAm = erstelltAm;
            this.aktiv = aktiv;
    }
    // Konstruktor Einstiegsmethode Klasse mit Zugriffsmodifizierer (NIE private)
    // haben niemals Datentyp
    // Kommentare verschwinden beim Kompilieren

    /**
     * Gibt den Benutzernamen zurück
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setzt den aktuellen Benuternamen
     * @param username Name des Benutzers
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public Calendar getErstelltAm() {
        return erstelltAm;
    }

    public void setErstelltAm(Calendar erstelltAm) {
        this.erstelltAm = erstelltAm;
    }

    public int getAktiv() {
        return aktiv;
    }

    public void setAktiv(int aktiv) {
        this.aktiv = aktiv;
    }


    @Override
    public void close() throws Exception {

    }
}
