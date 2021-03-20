package pt.isel.ls.model;

import java.util.HashMap;

public class NumberRatingsByValue implements Result {
    public HashMap<Integer, Integer> mapVotes;// todo
    public Movie movie;


    public NumberRatingsByValue() {
        this.mapVotes = new HashMap<>(5);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Movie Id = " + movie.id + '\n'
                + "Average = " + this.average() + "\n\n").append("Votes\tNumberVotes\n");
        for (int i = 1; i <= 5; i++) {
            s.append(mapVotes.get(i));
        }
        return s.toString();
    }

    public void add(int vote, int numberVote) {
        mapVotes.put(vote, numberVote);
    }

    public double average() {
        double r1 = mapVotes.get(1);
        double r2 = mapVotes.get(2);
        double r3 = mapVotes.get(3);
        double r4 = mapVotes.get(4);
        double r5 = mapVotes.get(5);

        double numerator = 1 * r1 + 2 * r2 + 3 * r3 + 4 * r4 + 5 * r5;
        double denominator = r1 + r2 + r3 + r4 + r5;

        return denominator == 0 ? 0 : numerator / denominator;
    }

    public boolean isEmpty() {
        return mapVotes.isEmpty();
    }
}
