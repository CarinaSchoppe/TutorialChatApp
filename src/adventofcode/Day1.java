package adventofcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Day1 {


  /**
   * The main method of the Day1 class.
   * <p>
   * This method reads a file containing two columns of integers, each row representing a link and a right side.
   * It stores these values in two separate ArrayLists, sorts them in ascending order, and then calculates the absolute difference between the two columns.
   * The result is then printed to the console.
   *
   * @throws IOException if an I/O error occurs while reading the file.
   */
  static void main() throws IOException {

    var file = new File("files/day1.txt");
    var reader = new BufferedReader(new FileReader(file));

    var lines = new ArrayList<String>();
    var links = new ArrayList<Integer>();
    var rechts = new ArrayList<Integer>();

    var row = "";
    while ((row = reader.readLine()) != null) {
      lines.add(row);
    }
    reader.close(); //<- optional, einfach nett

    for (var line : lines) {
      var split = line.split("   ");
      links.add(Integer.parseInt(split[0]));
      rechts.add(Integer.parseInt(split[1]));
    }

    links.sort(Comparator.naturalOrder());
    rechts.sort(Comparator.naturalOrder());

    var diff = 0;

    for (var slot = 0; slot < links.size(); slot++) {
      var itemLeft = links.get(slot);
      var itemRight = rechts.get(slot);

      diff += Math.abs(itemLeft - itemRight);


    }

    long total = 0;
    for (var linksZahl : links) {
      var anzahl =
          rechts.stream().filter(rechtsZahl -> Objects.equals(rechtsZahl, linksZahl)).count();
      System.out.println(linksZahl + " " + anzahl);
      total += linksZahl * anzahl;
    }

    System.out.println(total);
  }
}