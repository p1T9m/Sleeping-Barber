public class Barber {
  private static int nextId = 1;
  private int id;
  private String name;

  public Barber(String name) {
      this.id = nextId++;
      this.name = name;
  }

  public String getName() {
      return name;
  }
}