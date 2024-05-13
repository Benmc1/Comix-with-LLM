package Comic;

import java.util.Random;

public class Character {
    private String name;
    private String appearance;
    private String hairColour;
    private String skinColour;
    private String lips;

    //If the name is blank it will create a random character
    public Character(String name) {
        this.name = name;
        generateFeatures();
    }

    public String getName() {
        return name;
    }

    public void setFeatures(String appearance, String hairColour, String skinColour, String lips) {
        this.appearance = appearance;
        this.hairColour = hairColour;
        this.skinColour = skinColour;
        this.lips = lips;
    }
    private void generateFeatures() {
        String[] names = {"John", "Jane", "Bob", "Jack", "Rebecca"};
        String[] hairColours = {"Blonde", "Black", "Blue", "Red", "White", "Yellow"};
        String[] skinColours = {"White", "Yellow", "Orange", "Black"};
        String[] lipColours = {"Red", "White"};

        if (name.isEmpty()) {
            Random rand = new Random();
            name = names[rand.nextInt(names.length)];
            appearance = rand.nextBoolean() ? "male" : "female";
            hairColour = hairColours[rand.nextInt(hairColours.length)];
            skinColour = skinColours[rand.nextInt(skinColours.length)];
            lips = lipColours[rand.nextInt(lipColours.length)];
        }
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", appearance='" + appearance + '\'' +
                ", hairColour='" + hairColour + '\'' +
                ", skinColour='" + skinColour + '\'' +
                ", lips='" + lips + '\'' +
                '}';
    }

    public String toXML() {
        return "<figure>\n" +
                "\t<name>" + name + "</name>\n" +
                "\t<appearance>" + appearance + "</appearance>\n" +
                "\t<hair>" + hairColour + "</hair>\n" +
                "\t<skin>" + skinColour + "</skin>\n" +
                "\t<lips>" + lips + "</lips>\n" +
                "</figure>\n";
    }
}
