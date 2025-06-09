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
        FileWriter fw= new FileWriter(file);
        fw.write("[\n");
        for(int i=0 ; i<libri.size();i++){
            Libro l =  libri.get(i);
            fw.write("[\n");
            fw.write("    \"autore\": \"" + togliSlash(l.getAutore()) + "\",\n");
            fw.write("    \"titolo\": \"" + togliSlash(l.getTitolo()) + "\",\n");
            fw.write("    \"ISBN\": \"" + togliSlash(l.getISBN()) + "\",\n");
            fw.write("    \"genere\": \"" + l.getGenere().name() + "\",\n");
            fw.write("    \"tipo\": \"" + l.getTipo().name() + "\",\n");
            fw.write("    \"stato\": \"" + l.getStatoDiLettura().name() + "\",\n");
            fw.write("    \"valutazione\": \"" + l.getValutazione() + "\",\n");

            String recenzione= l.getRecensione();
            if(recenzione!= null){
                fw.write("    \"recensione\": \"" + togliSlash(recenzione) + "\"\n");
            }
            else  fw.write("    \"recensione\": null\n");
            fw.write("  }");
            if (i < libri.size() - 1) fw.write(",");
            fw.write("\n");
        }
        fw.write("]\n");
        fw.close();

    }

    public List<Libro> carica(File file) throws IOException{
        BufferedReader br= new BufferedReader(new FileReader(file));
        StringBuilder sb= new StringBuilder();
        String line;
        while ((line=br.readLine())!= null) {
            sb.append(line.trim());
        }
        br.close();
        String Letto=sb.toString();
        if(!Letto.startsWith("[")||Letto.endsWith("]"))
            throw new IOException("Formato non valido");
        Letto=Letto.substring(1,Letto.length()-1).trim();
        List<Libro> risultato= new ArrayList<Libro>();
        int i=0;
        while(i<Letto.length()) {
            int o1 = Letto.indexOf('{', i);
            if (o1 < 0) break;
            int o2 = Letto.indexOf('}', i);
            if (o2 < 0) break;
            String oggetto = Letto.substring(o1 + 1, o2).trim();
            i = o2 + 1;
            String[] parti = oggetto.split(",");
            String autore = null, titolo = null, isbn = null, genere = null, tipo = null, stato = null, recensione = null;
            int valutazione = 0;
            for (String p : parti) {
                String[] partiInterne = p.split(":", 2);
                String chiave = partiInterne[0].trim();
                String valore = partiInterne[1].trim();

                if (valore.startsWith("\"") && valore.endsWith("\"")) {
                    valore = valore.substring(1, valore.length() - 1);
                    if (chiave.contains("autore")) {
                        autore = mettiSlash(valore);
                    } else if (chiave.contains("titolo")) {
                        titolo = mettiSlash(valore);
                    } else if (chiave.contains("ISBN")) {
                        isbn = mettiSlash(valore);
                    } else if (chiave.contains("genere")) {
                        genere = valore;
                    } else if (chiave.contains("tipo")) {
                        tipo = valore;
                    } else if (chiave.contains("stato")) {
                        stato = valore;
                    } else if (chiave.contains("valutazione")) {
                        try {
                            valutazione = Integer.parseInt(valore);
                        } catch (NumberFormatException ex) {
                            valutazione = 0;
                        }
                    } else if (chiave.contains("recensione")) {
                        if (!valore.equals("null")) {
                            recensione = mettiSlash(valore);
                        }
                    }
                }
                Libro.Builder b= new Libro.Builder().setAutore(autore).setTitolo(titolo).setISBN(isbn).setGenere(Genere.valueOf(genere)).setTipo(TipiDiOggetto.valueOf(tipo)).setStatoDiLettura(StatoDiLettura.valueOf(stato));
                if(stato.equals(StatoDiLettura.LETTO.name())){
                    b.setValutazione(valutazione).setRecensione(recensione);
                }
                risultato.add(b.build());
            }


        }return risultato;
}
private static String togliSlash(String s){
    return s.replace("\\", "\\\\")
            .replace("\"", "\\\"");

}
private static String mettiSlash(String s){
    return s.replace("\\\"", "\"")
            .replace("\\\\", "\\");
}

}
