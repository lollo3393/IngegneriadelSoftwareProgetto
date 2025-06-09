package Libreria.Modello.Command.SalvaEcarica;

import Libreria.Modello.Genere;
import Libreria.Modello.Libro;
import Libreria.Modello.StatoDiLettura;
import Libreria.Modello.TipiDiOggetto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonManager {

    public static void salva(List<Libro> libri, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write("[\n");  // apertura array

        for (int i = 0; i < libri.size(); i++) {
            Libro l = libri.get(i);

            // apertura oggetto
            fw.write("  {\n");

            // valori "escaped" inline
            String autore    = l.getAutore()
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"");
            String titolo    = l.getTitolo()
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"");
            String isbn      = l.getISBN()
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"");
            fw.write("    \"autore\": \""    + autore    + "\",\n");
            fw.write("    \"titolo\": \""    + titolo    + "\",\n");
            fw.write("    \"ISBN\": \""      + isbn      + "\",\n");
            fw.write("    \"genere\": \""    + l.getGenere().name()       + "\",\n");
            fw.write("    \"tipo\": \""      + l.getTipo().name()         + "\",\n");
            fw.write("    \"stato\": \""     + l.getStatoDiLettura().name() + "\",\n");
            fw.write("    \"valutazione\": " + l.getValutazione()          + ",\n");

            String rec = l.getRecensione();
            if (rec != null) {
                rec = rec.replace("\\", "\\\\")
                        .replace("\"", "\\\"");
                fw.write("    \"recensione\": \"" + rec + "\"\n");
            } else {
                fw.write("    \"recensione\": null\n");
            }

            // chiusura oggetto
            fw.write("  }");

            if (i < libri.size() - 1) {
                fw.write(",");
            }
            fw.write("\n");
        }

        fw.write("]\n");  // chiusura array
        fw.close();
    }

    public List<Libro> carica(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }
        br.close();

        String json = sb.toString();
        // controllo array
        if (!json.startsWith("[") || !json.endsWith("]")) {
            throw new IOException("Formato non valido");
        }
        // rimuovo [ e ]
        json = json.substring(1, json.length() - 1).trim();

        List<Libro> risultato = new ArrayList<Libro>();
        int idx = 0;
        while (idx < json.length()) {
            int o1 = json.indexOf('{', idx);
            if (o1 < 0) break;
            int o2 = json.indexOf('}', o1);
            if (o2 < 0) break;

            // estraggo il contenuto dell'oggetto
            String obj = json.substring(o1 + 1, o2).trim();
            idx = o2 + 1;

            // split in campi
            String[] parts = obj.split(",");

            // variabili di raccolta
            String autore = null, titolo = null, isbn = null;
            String genere = null, tipo = null, stato = null, recensione = null;
            int valutazione = 0;

            for (String p : parts) {
                String[] kv = p.split(":", 2);
                String key = kv[0].trim();
                String val = kv[1].trim();

                // se è una stringa, tolgo virgolette e "unescape"
                if (val.startsWith("\"") && val.endsWith("\"")) {
                    val = val.substring(1, val.length() - 1)
                            .replace("\\\"", "\"")
                            .replace("\\\\", "\\");
                }

                if (key.contains("autore")) {
                    autore = val;
                } else if (key.contains("titolo")) {
                    titolo = val;
                } else if (key.contains("ISBN")) {
                    isbn = val;
                } else if (key.contains("genere")) {
                    genere = val;
                } else if (key.contains("tipo")) {
                    tipo = val;
                } else if (key.contains("stato")) {
                    stato = val;
                } else if (key.contains("valutazione")) {
                    try {
                        valutazione = Integer.parseInt(val);
                    } catch (NumberFormatException ex) {
                        valutazione = 0;
                    }
                } else if (key.contains("recensione")) {
                    if (!"null".equals(val)) {
                        recensione = val;
                    }
                }
            }

            // costruisco il Libro **dopo** aver letto tutti i campi
            Libro.Builder b = new Libro.Builder()
                    .setAutore(autore)
                    .setTitolo(titolo)
                    .setISBN(isbn)
                    .setGenere(Genere.valueOf(genere))
                    .setTipo(TipiDiOggetto.valueOf(tipo))
                    .setStatoDiLettura(StatoDiLettura.valueOf(stato));

            if (StatoDiLettura.LETTO.name().equals(stato)) {
                b.setValutazione(valutazione)
                        .setRecensione(recensione);
            }

            risultato.add(b.build());
        }

        return risultato;
    }
}
