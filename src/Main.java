import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import com.google.gson.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Collections;

import static java.util.stream.Collectors.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int opcio = 0;
        int numCasats = 0, numNoCasats = 0;
        long votsActual = 0;
        int rankingActual = 0, rankingNou = 0;
        int[] genreAuxiliar;
        HashMap<Integer, Long> votsGenres = new HashMap<Integer, Long>();
        HashMap<String, Integer> rankingSeries = new HashMap<String, Integer>();
        Map<Integer, Long> sortedGenres;
        Map<String, Integer> sortedSeries;
        Scanner sc = new Scanner(System.in);
        Serie topSerie;
        Serie nomMesLlarg;

        // Carreguem JSON
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\Material_AC3\\series.json");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(bufferedReader, JsonObject.class);
        JsonElement series_j = json.get("series");
        JsonElement genres_j = json.get("genres");
        Serie[] series = gson.fromJson(series_j, Serie[].class);
        Genre[] genres = gson.fromJson(genres_j, Genre[].class);

        // Per reduir el cost temporal del programa, he calculat el numero de casats, la top sèrie amb més rating
        // i l'actor amb el nom més llarg en un mateix bucle
        topSerie = series[0];
        nomMesLlarg = series[0];

        // Posem els genres en un HashMap
        for (int j = 1; j <= genres.length; j++) {
            votsGenres.put(j, (long) 0);
        }

        // Posem les series en un HashMap
        for (int k = 0; k < series.length; k++) {
            rankingSeries.put(series[k].getTitle(), 0);
        }

        // Mirem cas per cas que es compleixen les condicions per a les consultes
        for (int i = 0; i < series.length; i++) {
            // Només incrementem el nombre de casats si és true
            if (series[i].getActor().isMarried()) {
                numCasats++;
            }

            // Sumem els vots de la sèrie al total de cada genre
            genreAuxiliar = series[i].getGenres();
            for (int k = 0; k < series[i].getGenres().length; k++) {
                // Agafem els vots actuals del genre i després sumem els vots del gènere següent
                votsActual = votsGenres.get(genreAuxiliar[k]);
                votsGenres.replace(genreAuxiliar[k], votsActual + series[i].getVotes());

                // Calculem la puntuacio de les sèries a partir del ranking dels gèneres
                rankingActual = rankingSeries.get(series[i].getTitle());
                // Controlem que la puntuacio es 0 si el ranking es major que 15
                if (genres[genreAuxiliar[k] - 1].getRanking() > 15) {
                    rankingNou = 0;
                } else {
                    rankingNou = (16 - genres[genreAuxiliar[k] - 1].getRanking()) * 10;
                }
                rankingSeries.replace(series[i].getTitle(), rankingActual + rankingNou);
            }

            // Filtrem les series amb 500 vots o més
            if (series[i].getVotes() >= 500) {
                // Després mirem si té el més rating
                if (series[i].getRating() > topSerie.getRating()) {
                    topSerie = series[i];
                }

            }

            // Per cada sèrie, mirem el número de caracters que té el nom de l'actor
            if (series[i].getActor().getName().length() > nomMesLlarg.getActor().getName().length()) {
                nomMesLlarg = series[i];
            }
        }

        // Entrem en el bucle del menú
        while (opcio != 6) {
            System.out.println("1. Consultar la quantitat d'actors/es casats i no casats");
            System.out.println("2. Top3 gèneres amb més vots");
            System.out.println("3. Consultar la sèrie (amb 500 vots o més) amb més rating");
            System.out.println("4. Consultar l'actor amb nom més llarg");
            System.out.println("5. Top3 de les sèries amb més punts segons el gènere");
            System.out.println("6. Sortir");
            System.out.println(" ");
            System.out.print("Selecciona una opcio: ");
            opcio = sc.nextInt();

            switch (opcio) {
                case 1:
                    numNoCasats = series.length - numCasats;
                    System.out.println("Quantitat de casats: " + numCasats);
                    System.out.println("Quantitat de no casats: " + numNoCasats);
                    break;
                case 2:
                    int i = 1;
                    Set<Integer> keys;

                    // Ordenar el HashMap utilitzant un Map auxiliar
                    sortedGenres = votsGenres
                            .entrySet()
                            .stream()
                            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
                    keys = sortedGenres.keySet();
                    for (Integer key : keys) {
                        System.out.println(genres[key].getName());

                        // Imprimeix només el top 3
                        if (i == 3)
                            break;
                        i++;
                    }
                    break;
                case 3:
                    System.out.println("La serie amb més rating: ");
                    topSerie.printAllDetails();
                    break;
                case 4:
                    System.out.println("L'actor amb el nom més llarg:");
                    nomMesLlarg.printAllDetails();
                    break;
                case 5:
                    int j = 1;

                    // Ordenar el HashMap utilitzant el Map
                    sortedSeries = rankingSeries
                            .entrySet()
                            .stream()
                            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
                    System.out.println("Nom - Puntuacio");
                    for (Map.Entry<String, Integer> entry : sortedSeries.entrySet()) {
                        String seriesTitle = entry.getKey();
                        Integer puntacio = entry.getValue();
                        System.out.println(seriesTitle + " - " + puntacio);
                        if (j == 3)
                            break;
                        j++;
                    }
                    break;
                case 6:
                    System.out.println("Gracies per utilitzar el programa!");
                    break;
                default:
                    System.out.println("Opcio incorrecta!");
            }
        }
    }
}
