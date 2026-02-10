import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static String[] cardTypes = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    static String[] jackCardTypes = {"Jack", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Queen", "King", "Ace"};
    static String[] handTypes = {"Five of a Kind", "Four of a Kind", "Full House", "Three of a Kind", "Two Pair", "One Pair", "High Card"};

    public static int indexOf(String el, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(el)) {
                return i;
            }
        }
        return -1;
    }

    public static String handType(String[] hand, boolean wildJacks) {
        int[] frequencies = new int[cardTypes.length];
        int max = 0;
        int max2 = 0;
        int jacks = 0;
        for (String card : hand) {
            int index = indexOf(card, cardTypes);
            if (wildJacks && card.equals("Jack")) {
                jacks++;
            } else {
                frequencies[index] += 1;
                if (frequencies[index] > max) {
                    max = frequencies[index];
                } else if (frequencies[index] > max2) {
                    max2 = frequencies[index];
                }
            }
        }
        max += jacks;
        if (max == 5 && max2 == 0) {
            return "Five of a Kind";
        } else if (max == 4 && max2 == 1) {
            return "Four of a Kind";
        } else if (max == 3 && max2 == 2) {
            return "Full House";
        } else if (max == 3 && max2 == 1) {
            return "Three of a Kind";
        } else if (max == 2 && max2 == 2) {
            return "Two Pair";
        } else if (max == 2 && max2 == 1) {
            return "One Pair";
        }
        return "High Card";
    }

    public static boolean beats(Hand hand, Hand other, boolean wildJacks) {
        String[] cards1 = hand.getCards();
        String[] cards2 = other.getCards();
        int type1 = indexOf(handType(cards1, wildJacks), handTypes);
        int type2 = indexOf(handType(cards2, wildJacks), handTypes);
        if (type1 < type2) {
            return true;
        } else if (type2 < type1) {
            return false;
        }
        String[] cardRank = wildJacks ? jackCardTypes : cardTypes;
        for (int i = 0; i < cards1.length; i++) {
            int card1 = indexOf(cards1[i], cardRank);
            int card2 = indexOf(cards2[i], cardRank);
            if (card1 > card2) {
                return true;
            } else if (card2 > card1) {
                return false;
            }
        }
        return true;
    }

    public static int bidTotal(Hand[] hands, boolean wildJacks) {
        int total = 0;
        int rank;
        for (Hand hand : hands) {
            rank = 0;
            for (Hand other: hands) {
                if (beats(hand, other, wildJacks)) {
                    rank++;
                }
            }
            total += rank * hand.getBid();
        }
        return total;
    }

    public static void printAmounts(Hand[] hands, boolean wildJacks) {
        int[] handAmounts = new int[handTypes.length];
        for (Hand hand : hands) {
            String[] cards = hand.getCards();
            String type = handType(cards, wildJacks);
            handAmounts[indexOf(type, handTypes)]++;
        }
        for (int i = 0; i < handAmounts.length; i++) {
            System.out.println("Number of " + handTypes[i] + " hands: " + handAmounts[i]);
        }
    }

    public static void part1(Hand[] hands) {
        printAmounts(hands, false);
    }

    public static void part2(Hand[] hands) {
        part1(hands);
        System.out.println("Total Bid Value: " + bidTotal(hands, false));
    }

    public static void part3(Hand[] hands) {
        part2(hands);
        System.out.println("Total Bid Value with Jacks Wild: " + bidTotal(hands, true));
    }

    public static void main(String[] args) {
        Hand[] data = fileData("src/data");
        System.out.println("PART 1:");
        part1(data);
        System.out.println("\nPART 2:");
        part2(data);
        System.out.println("\nPART 3:");
        part3(data);
        // printAmounts(data, true);
    }

    public static Hand[] fileData(String fileName) {
        String fileData = "";
        try {
            File f = new File("src/data");
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String line = s.nextLine();
                fileData += line + "\n";
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        String[] lines = fileData.split("\n");
        Hand[] hands = new Hand[lines.length];
        for (int i = 0; i < lines.length; i++) {
            hands[i] = new Hand(lines[i]);
        }
        return hands;
    }
}
