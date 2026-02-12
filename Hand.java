public class Hand {
    private final String[] cards;
    private final int bid;

    public Hand(String line) {
        cards = (line.split("\\|")[0]).split(",");
        bid = Integer.parseInt(line.split("\\|")[1]);
    }

    public String[] getCards() {
        return cards;
    }

    public int getBid() {
        return bid;
    }
}
